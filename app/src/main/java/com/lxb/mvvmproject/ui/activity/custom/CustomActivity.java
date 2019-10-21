package com.lxb.mvvmproject.ui.activity.custom;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxb.mvvmproject.R;
import com.lxb.mvvmproject.base.BaseActivity;
import com.lxb.mvvmproject.config.Config;
import com.lxb.mvvmproject.databinding.ActivityCustomBinding;
import com.lxb.mvvmproject.ui.activity.custom.customlist.canvas.CanvasActivity;
import com.lxb.mvvmproject.ui.activity.custom.customlist.introduction.IntroductionActivity;
import com.lxb.mvvmproject.ui.adapter.HomeAdapter;
import com.lxb.mvvmproject.util.annotations.ContentView;

import java.util.Arrays;

/**
 * 自定义控件
 */
@ContentView(R.layout.activity_custom)
public class CustomActivity extends BaseActivity<CustomViewModel, ActivityCustomBinding> implements BaseQuickAdapter.OnItemClickListener {
    HomeAdapter adapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, CustomActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initIntent() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        bindingView.include.tvBack.setOnClickListener(v -> finish());
        bindingView.include.tvTitle.setText(Config.FUNCTION[5]);
        adapter = new HomeAdapter(Arrays.asList(viewModel.getList().get()));
        bindingView.rvList.setLayoutManager(new LinearLayoutManager(this));
        adapter.setOnItemClickListener(this);
        bindingView.rvList.setAdapter(adapter);
    }

    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        if (position==0){
            IntroductionActivity.start(this);
        }else if (position==1){
            CanvasActivity.start(this);
        }
    }
}
