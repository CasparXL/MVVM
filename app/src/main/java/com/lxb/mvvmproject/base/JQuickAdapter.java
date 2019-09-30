package com.lxb.mvvmproject.base;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.support.annotation.Nullable;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.View;
import android.view.ViewGroup;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.lxb.mvvmproject.R;

import java.util.List;

/**
 * 自定义适配器
 *
 * @param <T> 实体类
 * @param <B> ViewBinding
 */
public abstract class JQuickAdapter<T, B extends ViewDataBinding> extends BaseQuickAdapter<T, BaseViewHolder> {
    private B binding;

    public JQuickAdapter(int layoutResId) {
        super(layoutResId);
    }

    public JQuickAdapter(@Nullable List<T> data) {
        super(data);
    }

    public JQuickAdapter(int layoutResId, @Nullable List<T> data) {
        super(layoutResId, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, T item) {
        binding = (B) helper.itemView.getTag(R.id.BaseQuickAdapter_databinding_support);
        binding.executePendingBindings();
        convert(helper, binding, item);
    }

    @Override
    protected View getItemView(int layoutResId, ViewGroup parent) {
        if (layoutResId != mLayoutResId) {
            return super.getItemView(layoutResId, parent);
        }
        binding = DataBindingUtil.inflate(mLayoutInflater, layoutResId, parent, false);
        View view = binding.getRoot();
        view.setTag(R.id.BaseQuickAdapter_databinding_support, binding);
        return view;
    }

    protected abstract void convert(BaseViewHolder helper, B binding, T item);

    /**
     * 绑定RecyclerView
     */
    public void bindToRecyclerView(RecyclerView recyclerView) {
        super.bindToRecyclerView(recyclerView);
    }


}
