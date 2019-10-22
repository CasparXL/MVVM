package com.lxb.mvvmproject.bean;

import java.io.Serializable;

/**
 * "浪小白" 创建 2019/9/26.
 * 界面名称以及功能:
 */
public class Bean implements Serializable {

    private String data;
    private int code;
    private String msg;
    private Object totalPage;
    private Object totalRecord;
    private Object pageNo;

    public String getData() {
        return data;
    }

    public void setData(String data) {
        this.data = data;
    }

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

    public Object getTotalPage() {
        return totalPage;
    }

    public void setTotalPage(Object totalPage) {
        this.totalPage = totalPage;
    }

    public Object getTotalRecord() {
        return totalRecord;
    }

    public void setTotalRecord(Object totalRecord) {
        this.totalRecord = totalRecord;
    }

    public Object getPageNo() {
        return pageNo;
    }

    public void setPageNo(Object pageNo) {
        this.pageNo = pageNo;
    }
}
