package com.lxb.mvvmproject.bean;

import java.io.Serializable;

/**
 * 这个类是泛型类，可根据后端的返回字段修改
 */
public class Resource implements Serializable {
    public static final int RESULT_SUCCESS = 200;

    private int code;
    private String msg;


    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }

    public boolean isSuccess() {
        return RESULT_SUCCESS == code;
    }

    @Override
    public String toString() {
        return "Resource{" +
                "code=" + code +
                ", msg='" + msg + '\'' +
                '}';
    }
}