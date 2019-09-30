package com.lxb.mvvmproject.util.image;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;
import android.provider.MediaStore;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.FileProvider;
import android.util.Log;


import com.lxb.mvvmproject.app.BaseApplication;

import java.io.File;

import io.reactivex.functions.Consumer;

/**
 * 相机相册
 */

public class ImageSelect {
    public static final int TAKE_PHOTO = 0x00;
    public static final int CHOOSE_PHOTO = 0x01;

    /**
     * 相机
     */
    @SuppressLint("CheckResult")
    public static void doOpenCamera(Activity context, String img) {
        File imgFile = new File(img);
        Uri imgUri = null;
        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {//7.0及以上
            imgUri = FileProvider.getUriForFile(context, BaseApplication.getContext().getPackageName() + ".fileprovider", imgFile);//fileprovider
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
            intent.addFlags(Intent.FLAG_GRANT_WRITE_URI_PERMISSION);
        } else {
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imgUri);
        }
        context.startActivityForResult(intent, TAKE_PHOTO);
    }

    /**
     * 相册
     */
    @SuppressLint("CheckResult")
    public static void doOpenAlbum(final Activity context) {
        Intent intent = new Intent(Intent.ACTION_PICK, MediaStore.Images.Media.EXTERNAL_CONTENT_URI);
        intent.setDataAndType(MediaStore.Images.Media.EXTERNAL_CONTENT_URI, "image/*");
        context.startActivityForResult(intent, CHOOSE_PHOTO);
    }
}
