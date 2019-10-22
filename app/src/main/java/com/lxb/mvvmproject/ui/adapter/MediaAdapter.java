package com.lxb.mvvmproject.ui.adapter;

import android.content.ContentUris;
import android.content.Context;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.net.Uri;
import android.os.ParcelFileDescriptor;
import android.support.annotation.Nullable;
import android.util.Log;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.clj.fastble.BleManager;
import com.clj.fastble.data.BleDevice;
import com.lxb.mvvmproject.R;
import com.lxb.mvvmproject.bean.MusicBean;
import com.lxb.mvvmproject.util.Utils;
import com.lxb.mvvmproject.util.image.ImageLoaderManager;

import java.io.FileDescriptor;
import java.io.FileNotFoundException;
import java.util.List;

/**
 * "浪小白" 创建 2019/10/17.
 * 界面名称以及功能:
 */
public class MediaAdapter extends BaseQuickAdapter<MusicBean, BaseViewHolder> {
    public MediaAdapter(@Nullable List<MusicBean> data) {
        super(R.layout.item_media, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, MusicBean item) {
        ImageLoaderManager.loadImage(mContext, Utils.getMusicBitmap(mContext, item.getId(), item.getAlbum_id()), helper.getView(R.id.iv_img));
        helper.setText(R.id.tv_title, "歌曲名称: " + item.getTitle());
        helper.setText(R.id.tv_name, "歌\t\t\t\t\t手: " + item.getArtist());
        helper.setText(R.id.tv_time, "时\t\t\t\t\t长: " + Utils.formatTime((int) item.getDuration()));
    }

}
