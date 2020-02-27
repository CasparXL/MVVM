package com.lxb.mvvmproject.app;

import android.app.Application;
import android.content.Context;
import android.view.Gravity;

import androidx.annotation.NonNull;
import androidx.appcompat.app.AppCompatDelegate;
import androidx.multidex.MultiDexApplication;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hjq.toast.ToastUtils;
import com.hjq.toast.style.ToastQQStyle;
import com.lxb.mvvmproject.BuildConfig;
import com.lxb.mvvmproject.R;
import com.lxb.mvvmproject.helper.ActivityStackManager;
import com.lxb.mvvmproject.helper.MMKVUtil;
import com.lxb.mvvmproject.ui.MainActivity;
import com.lxb.mvvmproject.ui.activity.crash.CaocConfig;
import com.lxb.mvvmproject.ui.activity.crash.CrashActivity;
import com.lxb.mvvmproject.util.LogUtil;
import com.scwang.smart.refresh.header.MaterialHeader;
import com.scwang.smart.refresh.layout.SmartRefreshLayout;
import com.scwang.smart.refresh.layout.api.RefreshHeader;
import com.scwang.smart.refresh.layout.api.RefreshLayout;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshHeaderCreator;
import com.scwang.smart.refresh.layout.listener.DefaultRefreshInitializer;

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
        init();
    }
    //第三方框架或本地工具类初始化
    private void init() {
        //打印日志初始化,打正式包将不再打印日志
        LogUtil.init(BuildConfig.LOG_ENABLE, "浪");
        //微信MMKV，用于存储数据，跟SharedPreference效果一样甚至更强大
        MMKVUtil.init(this);
        //Toast弹框初始化
        ToastUtils.init(context);
        ToastUtils.initStyle(new ToastQQStyle(context));
        ToastUtils.setGravity(Gravity.BOTTOM, 0, 100);
        //全局堆栈管理初始化
        ActivityStackManager.getInstance().init(context);
        //阿里路由跳转初始化，这里要注意，若打签名包，除第一次签名包外，以后的都要修改项目的versionCode和VersionName，否则新增界面可能会无法生效[阿里框架的机制]
        if (BuildConfig.LOG_ENABLE) {           // These two lines must be written before init, otherwise these configurations will be invalid in the init process
            ARouter.openLog();     // Print log
            ARouter.openDebug();   // Turn on debugging mode (If you are running in InstantRun mode, you must turn on debug mode! Online version needs to be closed, otherwise there is a security risk)
        }
        ARouter.init(this);
        // Crash 捕捉界面
        CaocConfig.Builder.create()
                .backgroundMode(CaocConfig.BACKGROUND_MODE_SHOW_CUSTOM)
                .enabled(true)
                .trackActivities(true)
                .minTimeBetweenCrashesMs(2000)
                // 重启的 Activity
                .restartActivity(MainActivity.class)
                // 错误的 Activity
                .errorActivity(CrashActivity.class)
                // 设置监听器
                //.eventListener(new YourCustomEventListener())
                .apply();
    }

    public static Application getContext() {
        return context;
    }
    static {
        //启用矢量图兼容
        AppCompatDelegate.setCompatVectorFromResourcesEnabled(true);
        //设置全局默认配置（优先级最低，会被其他设置覆盖）
        SmartRefreshLayout.setDefaultRefreshInitializer(new DefaultRefreshInitializer() {
            @Override
            public void initialize(@NonNull Context context, @NonNull RefreshLayout layout) {
                //全局设置（优先级最低）
                layout.setEnableAutoLoadMore(true);
                layout.setEnableOverScrollDrag(false);
                layout.setEnableOverScrollBounce(true);
                layout.setEnableLoadMoreWhenContentNotFull(true);
                layout.setEnableScrollContentWhenRefreshed(true);
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);
            }
        });
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @NonNull
            @Override
            public RefreshHeader createRefreshHeader(@NonNull Context context, @NonNull RefreshLayout layout) {
                //全局设置主题颜色（优先级第二低，可以覆盖 DefaultRefreshInitializer 的配置，与下面的ClassicsHeader绑定）
                return new MaterialHeader(context);
            }
        });
    }

}
