package com.lxb.mvvmproject.ui.adapter;

import android.support.annotation.Nullable;

import com.chad.library.adapter.base.BaseViewHolder;
import com.lxb.mvvmproject.R;
import com.lxb.mvvmproject.base.JQuickAdapter;
import com.lxb.mvvmproject.databinding.ItemHomeTextBinding;

import java.util.List;

/**
 * "浪小白" 创建 2019/9/2.
 * 界面名称以及功能: 首页界面
 */
public class HomeAdapter extends JQuickAdapter<String, ItemHomeTextBinding> {
    public HomeAdapter(@Nullable List<String> data) {
        super(R.layout.item_home_text, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, ItemHomeTextBinding binding, String item) {
        binding.tvItem.setText(item);
    }

}
