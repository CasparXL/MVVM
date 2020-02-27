package com.lxb.mvvmproject.ui;


import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;

import com.lxb.mvvmproject.R;
import com.lxb.mvvmproject.base.BaseActivity;
import com.lxb.mvvmproject.base.NoViewModel;
import com.lxb.mvvmproject.databinding.ActivitySplashBinding;
import com.lxb.mvvmproject.util.annotations.ContentView;

import java.lang.ref.WeakReference;

@ContentView(R.layout.activity_splash)
public class SplashActivity extends BaseActivity<NoViewModel, ActivitySplashBinding> {
    SplashHandler handler;

    static class SplashHandler extends Handler {
        // SoftReference<Activity> 也可以使用软应用 只有在内存不足的时候才会被回收
        private final WeakReference<SplashActivity> mActivity;

        private SplashHandler(SplashActivity activity) {
            mActivity = new WeakReference<>(activity);
        }

        @Override
        public void handleMessage(Message msg) {
            Activity activity = mActivity.get();
            if (activity != null) {
                mActivity.get().run();
            }
            super.handleMessage(msg);

        }
    }

    @Override
    protected void initIntent() {
        // 后台返回时可能启动这个页面 http://blog.csdn.net/jianiuqi/article/details/54091181
        if ((getIntent().getFlags() & Intent.FLAG_ACTIVITY_BROUGHT_TO_FRONT) != 0) {
            finish();
            return;
        }
        handler = new SplashHandler(this);
        handler.sendEmptyMessageDelayed(1, 2000);
    }

    @Override
    protected void initView(Bundle a) {

    }

    public void run() {
        startActivity(new Intent(SplashActivity.this, MainActivity.class));
        finish();
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        if (handler != null) {
            handler.removeCallbacksAndMessages(null);
            handler = null;
        }
    }
}
