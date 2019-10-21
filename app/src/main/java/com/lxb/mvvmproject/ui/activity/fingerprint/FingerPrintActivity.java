package com.lxb.mvvmproject.ui.activity.fingerprint;


import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.view.View;


import com.lxb.mvvmproject.R;
import com.lxb.mvvmproject.base.BaseActivity;
import com.lxb.mvvmproject.base.NoViewModel;
import com.lxb.mvvmproject.config.Config;
import com.lxb.mvvmproject.databinding.ActivityFingerPrintBinding;
import com.lxb.mvvmproject.util.annotations.ContentView;
import com.lxb.mvvmproject.util.fingerprint.BiometricPromptManager;

/**
 * 指纹解锁
 * 貌似有内存溢出的操作，暂时没找到问题根源
 * 示例地址：https://github.com/gaoyangcr7/BiometricPromptDemo
 */
@ContentView(R.layout.activity_finger_print)
public class FingerPrintActivity extends BaseActivity<NoViewModel, ActivityFingerPrintBinding> {

    private BiometricPromptManager mManager;

    public static void start(Context context) {
        Intent intent = new Intent(context, FingerPrintActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initIntent() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        bindingView.include.tvTitle.setText(Config.FUNCTION[2]);

        mManager = new BiometricPromptManager(this);
        StringBuilder stringBuilder = new StringBuilder();
        stringBuilder.append("SDK 版本 ").append(Build.VERSION.SDK_INT);
        stringBuilder.append("\n");
        stringBuilder.append("系统硬件是否支持指纹识别 : ").append(mManager.isHardwareDetected());
        stringBuilder.append("\n");
        stringBuilder.append("设备在系统设置里面是否设置了指纹 : ").append(mManager.hasEnrolledFingerprints());
        stringBuilder.append("\n");
        stringBuilder.append("系统有没有设置锁屏 : ").append(mManager.isKeyguardSecure());
        stringBuilder.append("\n");
        bindingView.textView.setText(stringBuilder.toString());
        bindingView.button.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                if (mManager.isBiometricPromptEnable()) {
                    mManager.authenticate(new BiometricPromptManager.OnBiometricIdentifyCallback() {
                        @Override
                        public void onUsePassword() {
                            toast("onUsePassword");
                        }

                        @Override
                        public void onSucceeded() {
                            toast("onSucceeded");

                        }

                        @Override
                        public void onFailed() {
                            toast("onFailed");

                        }

                        @Override
                        public void onError(int code, String reason) {
                            toast("onError:" + code + "," + reason);

                        }

                        @Override
                        public void onCancel() {
                            toast("onCancel");
                        }
                    });
                } else {
                    toast("设备不支持或者未存入指纹");
                }
            }
        });
        bindingView.include.tvBack.setOnClickListener(v -> finish());
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (isFinishing())
            if (mManager != null) {
                mManager = null;
            }
    }
}
