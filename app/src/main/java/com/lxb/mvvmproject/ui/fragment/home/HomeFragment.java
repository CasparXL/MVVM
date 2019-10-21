package com.lxb.mvvmproject.ui.fragment.home;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.design.widget.Snackbar;
import android.support.v7.widget.LinearLayoutManager;
import android.util.Log;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxb.mvvmproject.R;
import com.lxb.mvvmproject.base.BaseFragment;
import com.lxb.mvvmproject.bean.Bean;
import com.lxb.mvvmproject.databinding.FragmentHomeBinding;
import com.lxb.mvvmproject.ui.activity.bluetooth.BlueToothActivity;
import com.lxb.mvvmproject.ui.activity.camera.CameraActivity;
import com.lxb.mvvmproject.ui.activity.crash.CrashActivity;
import com.lxb.mvvmproject.ui.activity.custom.CustomActivity;
import com.lxb.mvvmproject.ui.activity.fingerprint.FingerPrintActivity;
import com.lxb.mvvmproject.ui.activity.photograph.PhotographActivity;
import com.lxb.mvvmproject.ui.activity.skeleton.SkeletonActivity;
import com.lxb.mvvmproject.ui.adapter.HomeAdapter;
import com.lxb.mvvmproject.util.LogUtil;
import com.lxb.mvvmproject.util.annotations.ContentView;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.fragment_home)
public class HomeFragment extends BaseFragment<HomeViewModel, FragmentHomeBinding> implements BaseQuickAdapter.OnItemClickListener {

    HomeAdapter adapter;
    List<String> mList;

    @Override
    public void initView() {
        mList = new ArrayList<>();
        adapter = new HomeAdapter(mList);
        bindingView.rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.setOnItemClickListener(this);
        bindingView.rvList.setAdapter(adapter);
        viewModel.getListMutableLiveData().observe(this, new Observer<List<String>>() {
            @Override
            public void onChanged(@Nullable List<String> strings) {
                if (strings != null) {
                    mList.addAll(strings);
                    adapter.notifyDataSetChanged();
                }
            }
        });
        viewModel.getData().observe(this, new Observer<Bean>() {
            @Override
            public void onChanged(@Nullable Bean s) {
                if (s != null)
                    toast(s.getData());
            }
        });
        viewModel.initAdapter();
    }


    //功能点击事件
    @Override
    public void onItemClick(BaseQuickAdapter adapter, View view, int position) {
        viewModel.start(position, getActivity());
    }



}
