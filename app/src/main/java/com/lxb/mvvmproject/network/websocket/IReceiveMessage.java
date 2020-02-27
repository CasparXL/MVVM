package com.lxb.mvvmproject.network.websocket;

/**
 * Socket通信接口
 */
public interface IReceiveMessage {
    void onConnectSuccess();// 连接成功

    void onConnectFailed(String max);// 连接失败

    void onClose(); // 关闭
}