package com.lxb.mvvmproject.ui.activity.mediarecorder;

import android.content.Context;
import android.content.Intent;
import android.media.MediaPlayer;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import com.lxb.mvvmproject.R;
import com.lxb.mvvmproject.base.BaseActivity;
import com.lxb.mvvmproject.config.Config;
import com.lxb.mvvmproject.databinding.ActivityMediaRecorderBinding;
import com.lxb.mvvmproject.util.annotations.ContentView;

import java.io.File;

@ContentView(R.layout.activity_media_recorder)
public class MediaRecorderActivity extends BaseActivity<MediaRecorderModel, ActivityMediaRecorderBinding> {
    private MediaPlayer mediaPlayer;

    boolean isPlay;
    boolean isRecordPlay;

    public static void start(Context context) {
        Intent intent = new Intent(context, MediaRecorderActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initIntent() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        bindingView.include.tvTitle.setText(Config.FUNCTION[7]);
        bindingView.include.tvBack.setOnClickListener(v -> finish());
        mediaPlayer = new MediaPlayer();
        bindingView.ivPlay.setOnClickListener(v -> {
            toast("功能暂未完善");
            /*if (!isPlay) {
                mediaPlayer.start();
                bindingView.ivPlay.setImageResource(android.R.drawable.ic_media_pause);
            } else {
                mediaPlayer.stop();
                bindingView.ivPlay.setImageResource(android.R.drawable.ic_media_play);
            }
            isPlay = !isPlay;*/
        });
        bindingView.ivRecordPlay.setOnClickListener(v -> {
            toast("功能暂未完善");
           /* if (!isRecordPlay) {
                viewModel.startRecord();
                bindingView.ivRecordPlay.setImageResource(android.R.drawable.ic_media_pause);
            } else {
                viewModel.stopRecord();
                bindingView.ivRecordPlay.setImageResource(android.R.drawable.ic_media_play);
            }
            isRecordPlay = !isRecordPlay;*/
        });
    }

    private void initMediaPlayer() {
        try {
            File file = new File("");
            mediaPlayer.setDataSource(file.getPath()); //指定音频文件的路径
            mediaPlayer.prepareAsync(); //让MediaPlayer进入到准备状态
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    @Override
    protected void onStop() {
        super.onStop();
        if (isFinishing()) {
           /* if (mediaPlayer != null) {
                if (mediaPlayer.isPlaying()) {
                    mediaPlayer.stop();
                }
                mediaPlayer.release();
                mediaPlayer=null;
            }*/
        }
    }
}
