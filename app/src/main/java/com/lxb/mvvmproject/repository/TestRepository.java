package com.lxb.mvvmproject.repository;

import androidx.lifecycle.MutableLiveData;

import com.lxb.mvvmproject.base.BaseRepository;
import com.lxb.mvvmproject.network.Api;
import com.lxb.mvvmproject.network.RxSchedulers;

import java.util.HashMap;

import io.reactivex.Observer;
import io.reactivex.disposables.Disposable;

/**
 * "浪小白" 创建 2020/2/27.
 * 界面名称以及功能:
 */
public class TestRepository extends BaseRepository {

    public MutableLiveData<String> data;

    public MutableLiveData<String> getData() {
        if (data==null){
            data=new MutableLiveData<>();
        }
        return data;
    }

    public void setData(){
        HashMap<String,Object> objectHashMap=new HashMap<>();

        Api.getInstance().getApiService().getStartData(objectHashMap).compose(RxSchedulers.applySchedulers()).subscribe(new Observer<String>() {
            @Override
            public void onSubscribe(Disposable d) {
                compositeDisposable.add(d);
            }

            @Override
            public void onNext(String s) {
                getData().setValue(s);
            }

            @Override
            public void onError(Throwable e) {

            }

            @Override
            public void onComplete() {

            }
        });
    }

}
