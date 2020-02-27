package com.lxb.mvvmproject.viewmodel;

import android.app.Application;

import androidx.annotation.NonNull;
import androidx.lifecycle.AndroidViewModel;
import androidx.lifecycle.MutableLiveData;

import com.lxb.mvvmproject.repository.TestRepository;

/**
 * "浪小白" 创建 2020/2/27.
 * 界面名称以及功能:
 */
public class TestViewMode extends AndroidViewModel {
    private TestRepository repository;
    public TestViewMode(@NonNull Application application) {
        super(application);
        repository=new TestRepository();
    }
    public void  setData(){
        repository.setData();
    }
    public MutableLiveData<String> getData() {
        return repository.getData();
    }

    @Override
    protected void onCleared() {
        super.onCleared();
        if (repository!=null){
            repository.onClear();
            repository=null;
        }
    }
}
