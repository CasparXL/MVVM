package com.lxb.mvvmproject.util;


import android.util.Log;


/**
 * User: Tenz Liu
 * Date: 2017-08-21
 * Time: 12-10
 * Description: Log日志打印工具类
 */

public class LogUtil {

    public static boolean debug = false;
    public static String Tag = "";

    /**
     * 初始化
     *
     * @param debug
     */
    public static void init(boolean debug, String Tag) {
        LogUtil.debug = debug;
        LogUtil.Tag = Tag;
    }

    /**
     * 打印日志(Verbose)
     *
     * @param msg 内容
     */
    public static void v(String msg) {
        if (debug) {
            Log.v(Tag, "" + msg);
        }
    }

    /**
     * 打印日志(Debug)
     *
     * @param msg 内容
     */
    public static void d(String msg) {
        if (debug) {
            Log.d(Tag, "" + msg);
        }
    }


    /**
     * 打印日志(Info)
     *
     * @param msg 内容
     */
    public static void i(String msg) {
        if (debug) {
            Log.i(Tag, "" + msg);
        }
    }

    /**
     * 打印日志(Warm)
     *
     * @param msg 内容
     */
    public static void w(String msg) {
        if (debug) {
            Log.w(Tag, "" + msg);
        }
    }

    /**
     * 打印日志(wtf)
     *
     * @param msg 内容
     */
    public static void wtf(String msg) {
        if (debug) {
            Log.wtf(Tag, "" + msg);
        }
    }


    /**
     * 打印日志(Error)
     *
     * @param msg 内容
     */
    public static void e(String msg) {
        if (debug) {
            Log.e(Tag, "" + msg);
        }
    }


}
