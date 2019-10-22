package com.lxb.mvvmproject.base;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.support.annotation.NonNull;


import io.reactivex.disposables.CompositeDisposable;

/**
 * Created by leo
 * on 2019/10/15.
 */

public abstract class BaseViewModel extends AndroidViewModel {
    //这个是为了退出页面，取消请求的
    public CompositeDisposable compositeDisposable;

    public BaseViewModel(@NonNull Application application) {
        super(application);
        compositeDisposable = new CompositeDisposable();
    }



    @Override
    protected void onCleared() {
        super.onCleared();
        //销毁后，取消当前页所有在执行的网络请求。
        if (compositeDisposable != null) {
            compositeDisposable.clear();
        }
    }
}
