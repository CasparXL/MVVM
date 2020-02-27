package com.lxb.mvvmproject.action;

import androidx.annotation.StringRes;

import com.hjq.toast.ToastUtils;


/**
 * 在需要的地方实现该接口即可，简单轻便
 * author : Android 轮子哥
 * github : https://github.com/getActivity/AndroidProject
 * time   : 2019/12/08
 * desc   : 吐司意图
 */
public interface ToastAction {

    default void toast(CharSequence text) {
        ToastUtils.show(text);
    }

    default void toast(@StringRes int id) {
        ToastUtils.show(id);
    }

    default void toast(Object object) {
        ToastUtils.show(object);
    }
}