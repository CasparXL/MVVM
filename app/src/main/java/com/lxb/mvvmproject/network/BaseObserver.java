package com.lxb.mvvmproject.network;

import android.util.Log;

import com.google.gson.Gson;
import com.lxb.mvvmproject.R;
import com.lxb.mvvmproject.network.util.GsonUtils;
import com.lxb.mvvmproject.util.LogUtil;

import io.reactivex.Observer;
import io.reactivex.annotations.NonNull;
import io.reactivex.disposables.Disposable;


public abstract class BaseObserver<T> implements Observer<T> {

    public abstract void onSuccess(T t);

    public abstract void onFail(T e);

    public abstract void onSubscribes(Disposable e);

    @Override
    public void onSubscribe(@NonNull Disposable d) {
        onSubscribes(d);
    }

    @Override
    public void onNext(@NonNull T t) {
        if (t instanceof Resource){
            Resource resource= (Resource) t;
            if (resource.isSuccess()){
                onSuccess(t);
            }else {
                onFail(t);
            }
        }
    }

    @Override
    public void onError(@NonNull Throwable e) {
        Resource resource= new Resource();
        resource.setCode(-1);
        resource.setMsg(e.getLocalizedMessage());
        onFail((T) resource);
    }

    @Override
    public void onComplete() {

    }
}
