package com.lxb.mvvmproject.ui.activity.custom.customlist.introduction;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;

import com.lxb.mvvmproject.R;
import com.lxb.mvvmproject.base.BaseActivity;
import com.lxb.mvvmproject.base.NoViewModel;
import com.lxb.mvvmproject.databinding.ActivityIntroductionBinding;
import com.lxb.mvvmproject.util.annotations.ContentView;

/**
 * 入门级自定义控件
 */
@ContentView(R.layout.activity_introduction)
public class IntroductionActivity extends BaseActivity<NoViewModel, ActivityIntroductionBinding> {

    public static void start(Context context){
        Intent intent = new Intent(context,IntroductionActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initIntent() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        bindingView.include.tvBack.setOnClickListener(v -> finish());
        bindingView.include.tvTitle.setText("自定义控件写字");
    }
}
