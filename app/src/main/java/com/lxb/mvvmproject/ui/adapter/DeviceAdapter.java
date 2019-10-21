package com.lxb.mvvmproject.ui.adapter;

import android.support.annotation.Nullable;
import android.view.View;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.BaseViewHolder;
import com.clj.fastble.BleManager;
import com.clj.fastble.data.BleDevice;
import com.lxb.mvvmproject.R;

import java.util.List;

/**
 * "浪小白" 创建 2019/10/17.
 * 界面名称以及功能:
 */
public class DeviceAdapter extends BaseQuickAdapter<BleDevice, BaseViewHolder> {
    public DeviceAdapter(@Nullable List<BleDevice> data) {
        super(R.layout.adapter_device, data);
    }

    @Override
    protected void convert(BaseViewHolder helper, BleDevice item) {
        helper.addOnClickListener(R.id.btn_connect).addOnClickListener(R.id.btn_disconnect).addOnClickListener(R.id.btn_detail);
        boolean isConnected = BleManager.getInstance().isConnected(item);

        if (isConnected) {
            helper.setImageResource(R.id.img_blue, R.mipmap.ic_blue_connected);
            helper.setTextColor(R.id.txt_name,0xFF1DE9B6);
            helper.setTextColor(R.id.txt_mac,0xFF1DE9B6);
            helper.setGone(R.id.layout_idle,false);
            helper.setGone(R.id.layout_connected,true);
        }else {
            helper.setImageResource(R.id.img_blue, R.mipmap.ic_blue_remote);
            helper.setTextColor(R.id.txt_name,0xFF000000);
            helper.setTextColor(R.id.txt_mac,0xFF000000);
            helper.setGone(R.id.layout_idle,true);
            helper.setGone(R.id.layout_connected,false);
        }

        helper.setText(R.id.txt_name, item.getName() + "");
        helper.setText(R.id.txt_mac, item.getMac() + "");
        helper.setText(R.id.txt_rssi, item.getRssi() + "");
    }

}
