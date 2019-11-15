package com.lxb.mvvmproject.ui.activity.photograph;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;

import com.lxb.mvvmproject.base.BaseViewModel;
import com.lxb.mvvmproject.config.Config;
import com.lxb.mvvmproject.network.Api;
import com.lxb.mvvmproject.network.BaseObserver;
import com.lxb.mvvmproject.network.Resource;
import com.lxb.mvvmproject.network.RxSchedulers;
import com.lxb.mvvmproject.util.image.ImageSelect;

import java.util.Arrays;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class PhotographModel extends BaseViewModel {
    MutableLiveData<Resource<String>> mData;

    public PhotographModel(@NonNull Application application) {
        super(application);
        mData=new MutableLiveData<>();
    }


    //模拟网络请求方法，直接调用即可
    public void http() {
        Api.getInstance().getApiService().getUserAgreement().compose(RxSchedulers.applySchedulers()).subscribe(new BaseObserver<Resource<String>>() {
            @Override
            public void onSuccess(Resource<String> s) {
                mData.postValue(s);
            }

            @Override
            public void onFail(Resource<String> e) {
                mData.postValue(e);
            }

            @Override
            public void onSubscribes(Disposable e) {
                compositeDisposable.add(e);
            }
        });
    }

}
