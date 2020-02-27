package com.lxb.mvvmproject.network.util;


import com.lxb.mvvmproject.R;
import com.lxb.mvvmproject.app.BaseApplication;

/**
 * "浪小白" 创建 2019/10/23.
 * 界面名称以及功能:
 */
public class NetException {
    //网络连接失败,请检查网络
    public static final String CONNECT_ERROR = BaseApplication.getContext().getString(R.string.connect_error);
    //连接超时,请稍后再试
    public static final String CONNECT_TIMEOUT = BaseApplication.getContext().getString(R.string.connect_timeout);
    //服务器异常
    public static final String BAD_NETWORK = BaseApplication.getContext().getString(R.string.bad_network);
    //解析服务器响应数据失败
    public static final String PARSE_ERROR = BaseApplication.getContext().getString(R.string.parse_error);
    //未知错误
    public static final String UNKNOWN_ERROR = BaseApplication.getContext().getString(R.string.unknown_error);
    //服务器返回数据失败
    public static final String RESPONSE_RETURN_ERROR = BaseApplication.getContext().getString(R.string.response_return_error);

}
