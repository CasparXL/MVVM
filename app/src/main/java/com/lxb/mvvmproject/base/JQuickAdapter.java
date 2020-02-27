package com.lxb.mvvmproject.base;


import androidx.annotation.Nullable;
import androidx.databinding.DataBindingUtil;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;

import java.util.List;

/**
 * 自定义适配器
 *
 * @param <T> 实体类
 */
public abstract class JQuickAdapter<T> extends BaseQuickAdapter<T, BaseViewHolder> {

    public JQuickAdapter(int layoutResId) {
        super(layoutResId);
    }

    public JQuickAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    @Override
    protected void onItemViewHolderCreated(BaseViewHolder viewHolder, int viewType) {
        DataBindingUtil.bind(viewHolder.itemView);
    }

}
