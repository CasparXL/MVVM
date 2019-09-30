package com.lxb.mvvmproject.app;

import android.app.Application;
import android.support.multidex.MultiDexApplication;

import com.hjq.toast.ToastUtils;
import com.lxb.mvvmproject.BuildConfig;
import com.lxb.mvvmproject.R;
import com.lxb.mvvmproject.ui.MainActivity;
import com.lxb.mvvmproject.ui.SplashActivity;
import com.lxb.mvvmproject.ui.activity.error.CaocConfig;
import com.lxb.mvvmproject.util.LogUtil;
import com.lxb.mvvmproject.util.SP;
import com.squareup.leakcanary.LeakCanary;

/**
 * "浪小白" 创建 2019/8/13.
 * 界面名称以及功能: APP初始化
 */
public class BaseApplication extends MultiDexApplication {
    private static Application context;

    @Override
    public void onCreate() {
        super.onCreate();
        context = this;
        initCrash();
        //内存泄漏检测
        if (!LeakCanary.isInAnalyzerProcess(this)) {
            LeakCanary.install(this);
        }
        SP.init(this);
        ToastUtils.init(this);
        LogUtil.init(BuildConfig.LOG_ENABLE, "浪");
    }

    private void initCrash() {
        CaocConfig.Builder.create()
                .backgroundMode(CaocConfig.BACKGROUND_MODE_SILENT) //背景模式,开启沉浸式
                .enabled(true) //是否启动全局异常捕获
                .showErrorDetails(true) //是否显示错误详细信息
                .showRestartButton(true) //是否显示重启按钮
                .trackActivities(true) //是否跟踪Activity
                .minTimeBetweenCrashesMs(2000) //崩溃的间隔时间(毫秒)
//                .errorDrawable(R.mipmap.ic_launcher) //错误图标
                .restartActivity(MainActivity.class) //重新启动后的activity
//                .errorActivity(YourCustomErrorActivity.class) //崩溃后的错误activity
//                .eventListener(new YourCustomEventListener()) //崩溃后的错误监听
                .apply();
    }

    public static Application getContext() {
        return context;
    }
}
