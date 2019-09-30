package com.lxb.mvvmproject.ui.activity.crash;

import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;

import com.lxb.mvvmproject.R;
import com.lxb.mvvmproject.base.BaseActivity;
import com.lxb.mvvmproject.base.NoViewModel;
import com.lxb.mvvmproject.config.Config;
import com.lxb.mvvmproject.databinding.ActivityCrashBinding;
import com.lxb.mvvmproject.util.annotations.ContentView;
import com.lxb.mvvmproject.util.listener.PerfectClickListener;

/**
 * 测试抛出异常并捕获跳转错误页面
 */
@ContentView(R.layout.activity_crash)
public class CrashActivity extends BaseActivity<NoViewModel, ActivityCrashBinding> {

    public static void start(Context context) {
        Intent intent = new Intent(context, CrashActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initIntent() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        bindingView.include.tvTitle.setText(Config.FUNCTION[0]);
        bindingView.btnMine.setOnClickListener(perfectClickListener);
        bindingView.btnNull.setOnClickListener(perfectClickListener);
        bindingView.include.tvBack.setOnClickListener(perfectClickListener);

    }



    PerfectClickListener perfectClickListener = new PerfectClickListener() {
        @Override
        protected void onNoDoubleClick(View v) {
            if (v.getId() == R.id.btn_mine) {
                throw new RuntimeException("我是一个自定义异常");
            } else if (v.getId() == R.id.btn_null) {
                throw new NullPointerException("我是一个空指针");
            } else if (v.getId() == R.id.tv_back) {
                finish();
            }
        }
    };
}
