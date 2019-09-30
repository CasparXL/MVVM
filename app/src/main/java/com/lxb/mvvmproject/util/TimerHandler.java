package com.lxb.mvvmproject.util;

import android.os.Handler;
import android.os.Message;

import com.lxb.mvvmproject.ui.activity.fingerprint.FingerPrintActivity;

import java.lang.ref.WeakReference;

//定义定时器
class TimerHandler extends Handler {
    // SoftReference<Activity> 也可以使用软应用 只有在内存不足的时候才会被回收
    private final WeakReference<FingerPrintActivity> mActivity;

    private TimerHandler(FingerPrintActivity activity) {
        mActivity = new WeakReference<>(activity);
    }

    @Override
    public void handleMessage(Message msg) {
        super.handleMessage(msg);
    }
}