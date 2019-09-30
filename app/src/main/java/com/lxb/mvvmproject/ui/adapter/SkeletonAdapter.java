package com.lxb.mvvmproject.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.lxb.mvvmproject.R;
import com.lxb.mvvmproject.base.JQuickAdapter;
import com.lxb.mvvmproject.databinding.ItemHomeTextBinding;
import com.lxb.mvvmproject.databinding.ItemSkeletonBinding;

import java.util.List;

/**
 * "浪小白" 创建 2019/9/2.
 * 界面名称以及功能: 骨架界面
 */
public class SkeletonAdapter extends JQuickAdapter<String, ItemSkeletonBinding> {
    public SkeletonAdapter(@Nullable List<String> data) {
        super(R.layout.item_skeleton, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ItemSkeletonBinding binding, String item) {
    }

}
