package com.lxb.mvvmproject.ui.activity.bluetooth;

import android.app.Application;
import android.arch.lifecycle.AndroidViewModel;
import android.arch.lifecycle.MutableLiveData;
import android.databinding.ObservableArrayList;
import android.databinding.ObservableField;
import android.databinding.ObservableList;
import android.support.annotation.NonNull;
import android.util.Log;
import android.view.View;

import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleScanCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.scan.BleScanRuleConfig;
import com.lxb.mvvmproject.base.BaseViewModel;
import com.lxb.mvvmproject.bean.Bean;
import com.lxb.mvvmproject.network.Api;
import com.lxb.mvvmproject.network.BaseObserver;
import com.lxb.mvvmproject.network.RxSchedulers;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;

import io.reactivex.disposables.Disposable;

public class BlueToothViewModel extends BaseViewModel {

    public ObservableList<BleDevice> listMutableLiveData;

    public BlueToothViewModel(@NonNull Application application) {
        super(application);
        listMutableLiveData = new ObservableArrayList<>();
    }

    //配置扫描参数
    public void setScanRule() {
        BleScanRuleConfig scanRuleConfig = new BleScanRuleConfig.Builder()
                .setServiceUuids(null)      // 只扫描指定的服务的设备，可选
                .setDeviceName(true, "")   // 只扫描指定广播名的设备，可选
                .setDeviceMac("")                  // 只扫描指定mac的设备，可选
                .setAutoConnect(false)      // 连接时的autoConnect参数，可选，默认false
                .setScanTimeOut(10000)              // 扫描超时时间，可选，默认10秒
                .build();
        BleManager.getInstance().initScanRule(scanRuleConfig);
    }


}
