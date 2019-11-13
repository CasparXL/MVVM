package com.lxb.mvvmproject.ui.activity.bluetooth;

import android.bluetooth.BluetoothAdapter;
import android.bluetooth.BluetoothGatt;
import android.bluetooth.BluetoothManager;
import android.content.Context;
import android.content.Intent;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.view.animation.Animation;
import android.view.animation.AnimationUtils;
import android.view.animation.LinearInterpolator;
import android.widget.Toast;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.clj.fastble.BleManager;
import com.clj.fastble.callback.BleGattCallback;
import com.clj.fastble.callback.BleScanCallback;
import com.clj.fastble.data.BleDevice;
import com.clj.fastble.data.BleScanState;
import com.clj.fastble.exception.BleException;
import com.clj.fastble.scan.BleScanRuleConfig;
import com.lxb.mvvmproject.R;
import com.lxb.mvvmproject.base.BaseActivity;
import com.lxb.mvvmproject.base.NoViewModel;
import com.lxb.mvvmproject.config.Config;
import com.lxb.mvvmproject.databinding.ActivityBlueToothBinding;
import com.lxb.mvvmproject.ui.adapter.DeviceAdapter;
import com.lxb.mvvmproject.util.annotations.ContentView;
import com.lxb.mvvmproject.util.comm.ObserverManager;
import com.lxb.mvvmproject.util.listener.ListFactory;
import com.lxb.mvvmproject.util.permissions.OnPermission;
import com.lxb.mvvmproject.util.permissions.Permission;
import com.lxb.mvvmproject.util.permissions.XXPermissions;

import java.util.ArrayList;
import java.util.List;
import java.util.Objects;
import java.util.UUID;

/**
 * 蓝牙
 */
@ContentView(R.layout.activity_blue_tooth)
public class BlueToothActivity extends BaseActivity<BlueToothViewModel, ActivityBlueToothBinding> {
    private Animation operatingAnim;
    private DeviceAdapter adapter;

    public static void start(Context context) {
        Intent intent = new Intent(context, BlueToothActivity.class);
        context.startActivity(intent);
    }

    @Override
    protected void initIntent() {

    }

    @Override
    protected void initView(Bundle savedInstanceState) {
        bindingView.include.tvTitle.setText(Config.FUNCTION[6]);
        bindingView.include.tvBack.setOnClickListener(v -> finish());
        operatingAnim = AnimationUtils.loadAnimation(this, R.anim.rotate);
        operatingAnim.setInterpolator(new LinearInterpolator());
        initAdapter();
        bindingView.btnSearch.setOnClickListener(v -> {
            if (bindingView.btnSearch.getText().equals("开始扫描")) {
                checkPermissions();
            } else {
                bindingView.btnSearch.setText("开始扫描");
                BleManager.getInstance().cancelScan();
            }
        });
    }


    @Override
    protected void onStop() {
        super.onStop();
        if (BleManager.getInstance().getScanSate() == BleScanState.STATE_SCANNING) {//如果正在扫描，则取消扫描
            BleManager.getInstance().cancelScan();
        }
        BleManager.getInstance().disconnectAllDevice();
    }


    /**
     * 初始化适配器
     */
    private void initAdapter() {
        adapter = new DeviceAdapter(viewModel.listMutableLiveData);
        bindingView.rvList.setLayoutManager(new LinearLayoutManager(this));
        bindingView.rvList.setAdapter(adapter);
        adapter.setOnItemChildClickListener(new BaseQuickAdapter.OnItemChildClickListener() {
            @Override
            public void onItemChildClick(BaseQuickAdapter adapter, View view, int position) {
                BleDevice bleDevice = viewModel.listMutableLiveData.get(position);
                if (view.getId() == R.id.btn_connect) {//连接蓝牙
                    if (!BleManager.getInstance().isConnected(bleDevice)) {
                        BleManager.getInstance().cancelScan();
                        connect(bleDevice);
                    }
                } else if (view.getId() == R.id.btn_disconnect) {//断开连接
                    if (BleManager.getInstance().isConnected(bleDevice)) {
                        BleManager.getInstance().disconnect(bleDevice);
                    }
                } else if (view.getId() == R.id.btn_detail) {//蓝牙操作

                }
            }
        });
        viewModel.listMutableLiveData.addOnListChangedCallback(new ListFactory<BleDevice>().getCallback(adapter));
//        viewModel.listMutableLiveData.addOnListChangedCallback(ListFactory.getListChangedCallback(adapter));
    }

    private void checkPermissions() {
        XXPermissions.with(this).permission(Permission.Group.LOCATION).constantRequest().request(new OnPermission() {
            @Override
            public void hasPermission(List<String> granted, boolean isAll) {
                if (isAll) {
                    BluetoothAdapter bluetoothAdapter = BluetoothAdapter.getDefaultAdapter();
                    if (!bluetoothAdapter.isEnabled()) {
                        toast("请开启蓝牙并重新搜索");
                        return;
                    }
                    viewModel.setScanRule();
                    startScan();
                } else {
                    toast("请开启定位权限");
                }
            }

            @Override
            public void noPermission(List<String> denied, boolean quick) {
                toast("请开启定位权限");
            }
        });

    }

    BleScanCallback bleScanCallback = new BleScanCallback() {
        @Override
        public void onScanStarted(boolean success) {
            if (isFinishing()) return;
            //开始扫描
            viewModel.listMutableLiveData.clear();
            bindingView.ivLoad.startAnimation(operatingAnim);
            bindingView.ivLoad.setVisibility(View.VISIBLE);
            bindingView.btnSearch.setText("停止扫描");
        }

        @Override
        public void onLeScan(BleDevice bleDevice) {
            super.onLeScan(bleDevice);
        }

        @Override
        public void onScanning(BleDevice bleDevice) {
            if (isFinishing()) return;
            addDevice(bleDevice);
            if (viewModel.listMutableLiveData.size() % 5 == 0) {
//                adapter.notifyDataSetChanged();
            }
            //有新数据加入
            Log.e("浪", "加入蓝牙数据,名称:" + bleDevice.getName() + ",Mac:" + bleDevice.getMac() + ",信号:" + bleDevice.getRssi());
        }

        @Override
        public void onScanFinished(List<BleDevice> scanResultList) {
            if (isFinishing()) return;
            Log.e("浪", "加入蓝牙数据完成,总数据：" + viewModel.listMutableLiveData.size());
//            adapter.notifyDataSetChanged();
            //搜索完成
            bindingView.ivLoad.clearAnimation();
            bindingView.ivLoad.setVisibility(View.GONE);
            bindingView.btnSearch.setText("开始扫描");
        }
    };

    //开始扫描
    private void startScan() {
        BleManager.getInstance().scan(bleScanCallback);
    }

    private void connect(final BleDevice bleDevice) {
        BleManager.getInstance().connect(bleDevice, new BleGattCallback() {
            @Override
            public void onStartConnect() {
            }

            @Override
            public void onConnectFail(BleDevice bleDevice, BleException exception) {
                toast("连接失败");
            }

            @Override
            public void onConnectSuccess(BleDevice bleDevice, BluetoothGatt gatt, int status) {
                if (isFinishing()) return;
                viewModel.listMutableLiveData.add(bleDevice);
                addDevice(bleDevice);
//                adapter.notifyDataSetChanged();
                toast("连接成功");
            }

            @Override
            public void onDisConnected(boolean isActiveDisConnected, BleDevice bleDevice, BluetoothGatt gatt, int status) {
                if (isFinishing()) return;
                addDevice(bleDevice);
                removeDevice(bleDevice);
//                adapter.notifyDataSetChanged();

                if (isActiveDisConnected) {
                    toast("断开了");
                } else {
                    toast("连接断开");
                    ObserverManager.getInstance().notifyObserver(bleDevice);
                }

            }
        });
    }

    public void addDevice(BleDevice bleDevice) {
        removeDevice(bleDevice);
        viewModel.listMutableLiveData.add(bleDevice);
    }

    public void removeDevice(BleDevice bleDevice) {
        for (int i = 0; i < viewModel.listMutableLiveData.size(); i++) {
            BleDevice device = viewModel.listMutableLiveData.get(i);
            if (bleDevice.getKey().equals(device.getKey())) {
                viewModel.listMutableLiveData.remove(i);
                i--;
            }
        }
    }


}
