package com.lxb.mvvmproject.ui.activity.skeleton;

import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;

import com.ethanhua.skeleton.Skeleton;
import com.ethanhua.skeleton.SkeletonScreen;
import com.lxb.mvvmproject.R;
import com.lxb.mvvmproject.base.BaseActivity;
import com.lxb.mvvmproject.base.NoViewModel;
import com.lxb.mvvmproject.config.Config;
import com.lxb.mvvmproject.databinding.ActivitySkeletonBinding;
import com.lxb.mvvmproject.ui.adapter.HomeAdapter;
import com.lxb.mvvmproject.ui.adapter.SkeletonAdapter;
import com.lxb.mvvmproject.util.annotations.ContentView;

import java.util.ArrayList;
import java.util.List;
@ContentView(R.layout.activity_skeleton)
public class SkeletonActivity extends BaseActivity<NoViewModel, ActivitySkeletonBinding> {
    public static void start(Context context) {
        Intent intent = new Intent(context, SkeletonActivity.class);
        context.startActivity(intent);
    }

    SkeletonAdapter adapter;
    List<String> mList;

    @Override
    protected void initIntent() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        bindingView.include.tvTitle.setText(Config.FUNCTION[1]);
        bindingView.include.tvBack.setOnClickListener(v -> finish());
        mList = new ArrayList<>();
        for (int i = 0; i < 10; i++) {
            mList.add("");
        }
        adapter = new SkeletonAdapter(mList);
        bindingView.rvList.setLayoutManager(new LinearLayoutManager(this));
        SkeletonScreen skeletonScreen = Skeleton.bind(bindingView.rvList)
                .adapter(adapter)
                .shimmer(true)
                .angle(20)
                .frozen(false)
                .duration(1200)
                .count(10)
                .load(R.layout.item_skeleton_news)
                .show(); //default count is 10
        bindingView.rvList.postDelayed(new Runnable() {
            @Override
            public void run() {
                skeletonScreen.hide();
            }
        }, 3000);
    }
}
