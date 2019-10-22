package com.lxb.mvvmproject.ui.activity.mediarecorder;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.net.Uri;
import android.os.Handler;
import android.os.Message;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;
import android.widget.SeekBar;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxb.mvvmproject.R;
import com.lxb.mvvmproject.base.BaseActivity;
import com.lxb.mvvmproject.bean.MusicBean;
import com.lxb.mvvmproject.config.Config;
import com.lxb.mvvmproject.databinding.ActivityMediaRecorderBinding;
import com.lxb.mvvmproject.ui.adapter.MediaAdapter;
import com.lxb.mvvmproject.util.Utils;
import com.lxb.mvvmproject.util.annotations.ContentView;
import com.lxb.mvvmproject.util.image.ImageLoaderManager;

import java.io.File;
import java.io.IOException;
import java.lang.ref.WeakReference;
import java.util.ArrayList;
import java.util.List;

import okhttp3.internal.Util;

@ContentView(R.layout.activity_media_recorder)
public class MediaRecorderActivity extends BaseActivity<MediaRecorderModel, ActivityMediaRecorderBinding> implements MediaPlayer.OnErrorListener, MediaPlayer.OnCompletionListener, MediaPlayer.OnPreparedListener {
    private MediaPlayer mediaPlayer;
    List<MusicBean> mList;
    MediaAdapter adapter;
    private boolean hasPrepared;
    MediaRecorderModel.TimeHandler handler;

    Runnable timeRunnable = new Runnable() {
        @Override
        public void run() {
            Log.e("浪", "播放中:当前进度" + mediaPlayer.getCurrentPosition() + ",总进度:" + mediaPlayer.getDuration() + ",百分比:" + (int) (((float) mediaPlayer.getCurrentPosition() / (float) mediaPlayer.getDuration()) * 100f));
            if (mediaPlayer != null) {
                bindingView.sbProgress.setProgress((int) (((float) mediaPlayer.getCurrentPosition() / (float) mediaPlayer.getDuration()) * 100f));
                bindingView.tvStart.setText(Utils.formatTime(mediaPlayer.getCurrentPosition()) + "");
            }
            if (handler != null)
                handler.postDelayed(this, 500);
        }
    };

    public static void start(Context context) {
        Intent intent = new Intent(context, MediaRecorderActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initIntent() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        handler = new MediaRecorderModel.TimeHandler(this);
        bindingView.include.tvTitle.setText(Config.FUNCTION[7]);
        bindingView.include.tvBack.setOnClickListener(v -> finish());
        mediaPlayer = new MediaPlayer();
        initAdapter();
        bindingView.sbProgress.setOnSeekBarChangeListener(new SeekBar.OnSeekBarChangeListener() {
            @Override
            public void onProgressChanged(SeekBar seekBar, int progress, boolean fromUser) {

            }

            @Override
            public void onStartTrackingTouch(SeekBar seekBar) {

            }

            @Override
            public void onStopTrackingTouch(SeekBar seekBar) {
                seekTo((int) (mediaPlayer.getDuration() * (seekBar.getProgress() / 100f)));
            }
        });
    }

    //初始化适配器
    private void initAdapter() {
        mList = new ArrayList<>();
        adapter = new MediaAdapter(mList);
        bindingView.rvList.setLayoutManager(new LinearLayoutManager(this));
        bindingView.rvList.setAdapter(adapter);
        mList.addAll(viewModel.getMusic(this));
        adapter.notifyDataSetChanged();
        adapter.setOnItemClickListener(new BaseQuickAdapter.OnItemClickListener() {
            @Override
            public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
                play(mList.get(position).getUrl());
                ImageLoaderManager.loadImage(MediaRecorderActivity.this, Utils.getMusicBitmap(MediaRecorderActivity.this, mList.get(position).getId(), mList.get(position).getAlbum_id()), bindingView.ivImg);
                bindingView.tvEnd.setText(Utils.formatTime((int) mList.get(position).getDuration()) + "");
            }
        });
    }

    //初始化播放器
    private void initIfNecessary() {
        if (null == mediaPlayer) {
            mediaPlayer = new MediaPlayer();
        }
    }

    //初始化播放器准备
    public void play(String url) {
        bindingView.sbProgress.setProgress(0);
        hasPrepared = false; // 开始播放前讲Flag置为不可操作
        initIfNecessary(); // 如果是第一次播放/player已经释放了，就会重新创建、初始化
        try {
            mediaPlayer.reset();
            File file = new File(url);
            mediaPlayer.setDataSource(file.getPath()); // 设置曲目资源
            mediaPlayer.prepareAsync(); // 异步的准备方法
            mediaPlayer.setOnErrorListener(this);
            mediaPlayer.setOnCompletionListener(this);
            mediaPlayer.setOnPreparedListener(this);
        } catch (IOException e) {
            e.printStackTrace();
        }
    }

    //播放
    public void start() {
        // release()会释放player、将player置空，所以这里需要判断一下
        if (null != mediaPlayer && hasPrepared) {
            mediaPlayer.start();
        }
    }

    //暂停
    public void pause() {
        if (null != mediaPlayer && hasPrepared) {
            mediaPlayer.pause();
        }
    }

    //播放进度
    public void seekTo(int position) {
        if (null != mediaPlayer && hasPrepared) {
            mediaPlayer.seekTo(position);
        }
    }

    public void release() {
        hasPrepared = false;
        mediaPlayer.stop();
        mediaPlayer.release();
        mediaPlayer = null;
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isFinishing()) {
            if (handler != null) {
                handler.removeCallbacksAndMessages(null);
                handler = null;
            }
            release();
//            ImageLoaderManager.clear();
        }
    }

    //MediaPlayer的三个回调，分别是错误回调，完成回调以及状态回调
    @Override
    public boolean onError(MediaPlayer mp, int what, int extra) {
        Log.e("浪", "出错了,what:" + what + ",extra:" + extra);
        hasPrepared = false;
        return false;
    }

    @Override
    public void onCompletion(MediaPlayer mp) {
        Log.e("浪", "播放完成");
        hasPrepared = false;
        handler.removeCallbacksAndMessages(null);
        // 通知调用处，调用play()方法进行下一个曲目的播放
    }

    @Override
    public void onPrepared(MediaPlayer mp) {
        hasPrepared = true; // 准备完成后回调到这里
        start();
        handler.postDelayed(timeRunnable, 500);
    }
}
