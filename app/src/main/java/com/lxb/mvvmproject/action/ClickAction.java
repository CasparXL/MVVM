package com.lxb.mvvmproject.action;

import android.view.View;

import androidx.annotation.IdRes;

/**
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * desc   : 点击事件意图
 */
public interface ClickAction extends View.OnClickListener {

    //用于获取View
    <V extends View> V findViewById(@IdRes int id);


    @Override
    default void onClick(View v) {
        // 默认不实现，让子类实现
    }

    //配合Databinding使用
    default void setOnClickListener(View... ids) {
        for (View id : ids) {
            if (id != null)
                id.setOnClickListener(this);
        }
    }

    //配合普通弹窗获取视图使用
    default void setOnClickListener(@IdRes int... ids) {
        for (int id : ids) {
            findViewById(id).setOnClickListener(this);
        }
    }
}