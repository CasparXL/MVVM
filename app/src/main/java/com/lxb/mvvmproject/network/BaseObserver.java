package com.lxb.mvvmproject.network;

import com.google.gson.JsonParseException;
import com.lxb.mvvmproject.network.util.NetException;

import org.json.JSONException;

import java.io.InterruptedIOException;
import java.net.ConnectException;
import java.net.UnknownHostException;
import java.text.ParseException;

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
        if (e instanceof retrofit2.HttpException) {
            //HTTP错误
            resource.setMsg(NetException.BAD_NETWORK);
        } else if (e instanceof ConnectException || e instanceof UnknownHostException) {
            //连接错误
            resource.setMsg(NetException.CONNECT_ERROR);
        } else if (e instanceof InterruptedIOException) {
            //连接超时
            resource.setMsg(NetException.CONNECT_TIMEOUT);
        } else if (e instanceof JsonParseException || e instanceof JSONException || e instanceof ParseException) {
            //解析错误
            resource.setMsg(NetException.PARSE_ERROR);
        } else {
            //其他错误
            resource.setMsg(NetException.UNKNOWN_ERROR+":"+e.getLocalizedMessage());
        }
        resource.setCode(-1);
        onFail((T) resource);
    }



    @Override
    public void onComplete() {

    }
}
