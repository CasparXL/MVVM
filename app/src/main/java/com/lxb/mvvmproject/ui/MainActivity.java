package com.lxb.mvvmproject.ui;


import android.os.Bundle;
import android.view.KeyEvent;

import androidx.lifecycle.Observer;

import com.lxb.mvvmproject.R;
import com.lxb.mvvmproject.base.BaseActivity;
import com.lxb.mvvmproject.base.NoViewModel;
import com.lxb.mvvmproject.databinding.ActivityMainBinding;
import com.lxb.mvvmproject.util.LogUtil;
import com.lxb.mvvmproject.util.annotations.ContentView;
import com.lxb.mvvmproject.viewmodel.TestViewMode;

/**
 * app主页板块
 */
@ContentView(R.layout.activity_main)
public class MainActivity extends BaseActivity<TestViewMode, ActivityMainBinding> {

    @Override
    protected void initIntent() {

    }

    @Override
    protected void initView(Bundle a) {
        viewModel.getData().observe(this, new Observer<String>() {
            @Override
            public void onChanged(String s) {
                LogUtil.e(s);
            }
        });
    }

    @Override
    protected void onResume() {
        super.onResume();
        viewModel.setData();
    }

    @Override
    protected void onStop() {
        super.onStop();
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            // 不退出程序，进入后台
            moveTaskToBack(true);
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

}
