package com.lxb.mvvmproject.ui.activity.crash;

import android.content.ClipData;
import android.content.ClipboardManager;
import android.content.Context;
import android.os.Bundle;
import android.util.TypedValue;
import android.view.View;
import android.widget.TextView;

import androidx.appcompat.app.AlertDialog;

import com.lxb.mvvmproject.R;
import com.lxb.mvvmproject.aop.SingleClick;
import com.lxb.mvvmproject.base.BaseActivity;
import com.lxb.mvvmproject.base.NoViewModel;
import com.lxb.mvvmproject.databinding.ActivityCrashBinding;
import com.lxb.mvvmproject.util.annotations.ContentView;


/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2019/06/27
 *    desc   : 崩溃捕捉界面
 */
@ContentView(R.layout.activity_crash)
public final class CrashActivity extends BaseActivity<NoViewModel, ActivityCrashBinding> {
    
    private CaocConfig mConfig;

    private AlertDialog mDialog;

    @SingleClick
    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.btn_crash_restart:
                CustomActivityOnCrash.restartApplication(CrashActivity.this, mConfig);
                break;
            case R.id.btn_crash_log:
                if (mDialog == null) {
                    mDialog = new AlertDialog.Builder(CrashActivity.this)
                            .setTitle(R.string.crash_error_details)
                            .setMessage(CustomActivityOnCrash.getAllErrorDetailsFromIntent(CrashActivity.this, getIntent()))
                            .setPositiveButton(R.string.crash_close, null)
                            .setNeutralButton(R.string.crash_copy_log, (dialog, which) -> copyErrorToClipboard())
                            .create();
                }
                mDialog.show();
                TextView textView = mDialog.findViewById(android.R.id.message);
                if (textView != null) {
                    textView.setTextSize(TypedValue.COMPLEX_UNIT_SP, 12);
                }
                break;
            default:
                break;
        }
    }

    /**
     * 复制报错信息到剪贴板
     */
    private void copyErrorToClipboard() {
        String errorInformation = CustomActivityOnCrash.getAllErrorDetailsFromIntent(CrashActivity.this, getIntent());
        ((ClipboardManager) getSystemService(Context.CLIPBOARD_SERVICE)).setPrimaryClip(ClipData.newPlainText(getString(R.string.crash_error_info), errorInformation));
    }

    @Override
    protected void initIntent() {
        mConfig = CustomActivityOnCrash.getConfigFromIntent(getIntent());
        if (mConfig == null) {
            // 这种情况永远不会发生，只要完成该活动就可以避免递归崩溃
            finish();
        }
    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        setOnClickListener(bindingView.btnCrashLog,bindingView.btnCrashRestart);
    }
}