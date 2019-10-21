package com.lxb.mvvmproject.ui.activity.custom;

import android.app.Application;
import android.databinding.ObservableField;
import android.support.annotation.NonNull;

import com.lxb.mvvmproject.base.BaseViewModel;

public class CustomViewModel extends BaseViewModel {
    private ObservableField<String[]> mList = new ObservableField<>();

    public ObservableField<String[]> getList() {
        return mList==null?new ObservableField<>():mList;
    }

    public CustomViewModel(@NonNull Application application) {
        super(application);
        mList.set(new String[]{"自定义控件入门","画板"});
    }


}
