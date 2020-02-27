package com.lxb.mvvmproject.base;


import java.util.ArrayList;
import java.util.List;
import java.util.UUID;

import io.reactivex.disposables.CompositeDisposable;

/**
 * 数据仓库的基类，用于管理网络请求的操作
 */
public abstract class BaseRepository {
    //发送的每一条命令对应的uuid

    public CompositeDisposable compositeDisposable;

    public BaseRepository() {
        compositeDisposable = new CompositeDisposable();
    }

    public void onClear() {
        if (compositeDisposable != null) {
            compositeDisposable.clear();
            compositeDisposable = null;
        }
    }

}
