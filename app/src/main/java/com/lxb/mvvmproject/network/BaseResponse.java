package com.lxb.mvvmproject.network;

import java.io.Serializable;

/**
 * "浪小白" 创建 2019/10/15.
 * 界面名称以及功能:
 */
public class BaseResponse<T> implements Serializable{
    private int errorCode;
    private String errorMsg;
    private T data;

    public int getErrorCode() {
        return errorCode;
    }

    public void setErrorCode(int errorCode) {
        this.errorCode = errorCode;
    }

    public String getErrorMsg() {
        return errorMsg;
    }

    public void setErrorMsg(String errorMsg) {
        this.errorMsg = errorMsg;
    }

    public T getData() {
        return data;
    }

    public void setData(T data) {
        this.data = data;
    }
}
