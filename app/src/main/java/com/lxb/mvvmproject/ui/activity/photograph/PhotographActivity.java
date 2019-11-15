package com.lxb.mvvmproject.ui.activity.photograph;

import android.arch.lifecycle.Observer;
import android.content.Context;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.text.TextUtils;
import android.util.Log;

import com.lxb.mvvmproject.R;
import com.lxb.mvvmproject.base.BaseActivity;
import com.lxb.mvvmproject.base.NoViewModel;
import com.lxb.mvvmproject.config.Config;
import com.lxb.mvvmproject.databinding.ActivityPhotographBinding;
import com.lxb.mvvmproject.network.Resource;
import com.lxb.mvvmproject.util.LogUtil;
import com.lxb.mvvmproject.util.annotations.ContentView;
import com.lxb.mvvmproject.util.image.FileUtil;
import com.lxb.mvvmproject.util.image.ImageLoaderManager;
import com.lxb.mvvmproject.util.image.ImageSelect;
import com.lxb.mvvmproject.util.image.RealPathFromUriUtils;
import com.lxb.mvvmproject.util.permissions.OnPermission;
import com.lxb.mvvmproject.util.permissions.Permission;
import com.lxb.mvvmproject.util.permissions.XXPermissions;

import java.io.File;
import java.util.HashMap;
import java.util.List;

import top.zibin.luban.CompressionPredicate;
import top.zibin.luban.Luban;
import top.zibin.luban.OnCompressListener;
@ContentView(R.layout.activity_photograph)
public class PhotographActivity extends BaseActivity<PhotographModel, ActivityPhotographBinding> {

    String imgPath = "";

    public static void start(Context context) {
        Intent intent = new Intent(context, PhotographActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initIntent() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        bindingView.include.tvTitle.setText(Config.FUNCTION[3]);
        bindingView.btnSelectImg.setOnClickListener(v -> permission(1));
        bindingView.btnOpenAlbum.setOnClickListener(v -> permission(2));
        bindingView.include.tvBack.setOnClickListener(v -> finish());
        viewModel.mData.observe(this, new Observer<Resource<String>>() {//模拟网络请求
            @Override
            public void onChanged(@Nullable Resource<String> s) {
                if (s != null) {
                    if (s.isSuccess()) {
                        toast(s.getData());
                    } else {
                        toast(s.getMsg());
                    }
                }
            }
        });
        viewModel.http();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {//调用图片选择处理成功
            switch (requestCode) {
                case ImageSelect.TAKE_PHOTO:// 拍照后在这里回调
                    open(imgPath);
                    break;
                case ImageSelect.CHOOSE_PHOTO://选择相册的回调
                    if (data != null) {
                        open(RealPathFromUriUtils.getRealPathFromUri(this, data.getData()));
                    }
                    break;
            }
        }

    }

    public void open(String position) {
        LogUtil.e(position);
        Luban.with(this)
                .load(position)
                .ignoreBy(100)
                .setTargetDir(FileUtil.getAppDir())
                .filter(new CompressionPredicate() {
                    @Override
                    public boolean apply(String path) {
                        return !(TextUtils.isEmpty(path) || path.toLowerCase().endsWith(".gif"));
                    }
                })
                .setCompressListener(new OnCompressListener() {
                    @Override
                    public void onStart() {
                        // TODO 压缩开始前调用，可以在方法内启动 loading UI
                        Log.e("浪", "开始压缩");
                        File file = new File(position);
                        if (file.exists()) {
                            ImageLoaderManager.loadImage(PhotographActivity.this, file.getPath(), bindingView.ivStart);
                            String s = "原图片路径:" + file.getPath() + "\n" + "原图片大小:" + file.length() / 1024 + "KB\n";
                            bindingView.tvStart.setText(s);
                        }

                    }

                    @Override
                    public void onSuccess(File file) {
                        // TODO 压缩成功后调用，返回压缩后的图片文件
                        Log.e("浪", "压缩成功,检测图片是否存在:" + file.exists());
                        if (file.exists()) {
                            ImageLoaderManager.loadImage(PhotographActivity.this, file.getPath(), bindingView.ivEnd);
                            String s = "压缩后图片路径:" + file.getPath() + "MB\n" + "压缩后图片大小:" + file.length() / 1024 + "KB\n";
                            bindingView.tvStart.setText(bindingView.tvStart.getText() + s);
                        }


                    }

                    @Override
                    public void onError(Throwable e) {
                        // TODO 当压缩过程出现问题时调用
                        Log.e("浪", "压缩失败" + e.getLocalizedMessage());
                    }
                }).launch();

    }

    public void permission(int position) {
        XXPermissions.with(this).constantRequest().permission(Permission.Group.CAMERA).request(new OnPermission() {
            @Override
            public void hasPermission(List<String> granted, boolean isAll) {
                if (isAll) {
                    if (position == 1) {
                        ImageSelect.doOpenAlbum(PhotographActivity.this);
                    } else {
                        imgPath = FileUtil.generateImgePathInStoragePath();
                        Log.e("浪", imgPath + "地址");
                        ImageSelect.doOpenCamera(PhotographActivity.this, imgPath);
                    }
                } else {
                    toast("请先开启权限");
                }
            }

            @Override
            public void noPermission(List<String> denied, boolean quick) {
                if (quick) {
                    toast("请先开启权限");
                }
            }
        });
    }
}
