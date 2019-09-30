package com.lxb.mvvmproject.ui.fragment.home;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.support.annotation.NonNull;
import android.util.Log;

import com.lxb.mvvmproject.bean.Bean;
import com.lxb.mvvmproject.config.Config;
import com.lxb.mvvmproject.network.Api;
import com.lxb.mvvmproject.network.BaseObserver;
import com.lxb.mvvmproject.network.RxSchedulers;
import com.lxb.mvvmproject.util.rxjava.HttpManager;

import java.util.Arrays;
import java.util.List;

import io.reactivex.disposables.Disposable;

public class HomeViewModel extends AndroidViewModel {
    //首页列表数据源
    private MutableLiveData<List<String>> listMutableLiveData=new MutableLiveData<>();
    //模拟接口数据
    private MutableLiveData<Bean> mData=new MutableLiveData<>();

    public HomeViewModel(@NonNull Application application) {
        super(application);
    }

    public MutableLiveData<List<String>> getListMutableLiveData() {
        return listMutableLiveData;
    }

    public MutableLiveData<Bean> getData() {
        return mData;
    }

    //初始化适配器数据
    public void initAdapter() {
        getListMutableLiveData().postValue(Arrays.asList(Config.FUNCTION));
    }

    //初始化适配器数据
    public void http() {
       Api.getInstance().getApiService().getUserAgreement().compose(RxSchedulers.applySchedulers()).subscribe(new BaseObserver<Bean>() {
            @Override
            public void onSuccess(Bean s) {
                getData().postValue(s);
            }

            @Override
            public void onFail(Throwable e) {
                Log.e("浪", e.getLocalizedMessage());
            }

            @Override
            public void onSubscribes(Disposable e) {
                HttpManager.getInstanceBus().addSubscription(HomeViewModel.this, e);
            }
        });
    }

    public void clear() {
        HttpManager.getInstanceBus().unSubscribe(HomeViewModel.this);
    }
}
