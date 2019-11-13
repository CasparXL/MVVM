package com.lxb.mvvmproject.ui.fragment.home;

import android.arch.lifecycle.Observer;
import android.support.annotation.Nullable;
import android.support.v7.widget.LinearLayoutManager;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.lxb.mvvmproject.R;
import com.lxb.mvvmproject.base.BaseFragment;
import com.lxb.mvvmproject.databinding.FragmentHomeBinding;
import com.lxb.mvvmproject.network.Resource;
import com.lxb.mvvmproject.ui.adapter.HomeAdapter;
import com.lxb.mvvmproject.util.annotations.ContentView;
import com.lxb.mvvmproject.util.listener.ListFactory;

import java.util.ArrayList;
import java.util.List;

@ContentView(R.layout.fragment_home)
public class HomeFragment extends BaseFragment<HomeViewModel, FragmentHomeBinding> implements BaseQuickAdapter.OnItemClickListener {

    HomeAdapter adapter;

    @Override
    public void initView() {
        adapter = new HomeAdapter(viewModel.listMutableLiveData);
        bindingView.rvList.setLayoutManager(new LinearLayoutManager(getActivity()));
        adapter.setOnItemClickListener(this);
        bindingView.rvList.setAdapter(adapter);
        viewModel.listMutableLiveData.addOnListChangedCallback(new ListFactory<String>().getCallback(adapter));
        viewModel.getData().observe(this, new Observer<Resource<String>>() {//模拟网络请求
            @Override
            public void onChanged(@Nullable Resource<String> s) {
                if (s != null) {
                    if (s.isSuccess()) {
                        toast(s.getData());
                    } else {
                        toast(s.getMsg());
                    }
                }
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
