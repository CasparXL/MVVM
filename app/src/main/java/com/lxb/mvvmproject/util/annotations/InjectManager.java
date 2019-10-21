package com.lxb.mvvmproject.util.annotations;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.support.v4.app.Fragment;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.Window;
import android.view.WindowManager;


import com.lxb.mvvmproject.R;
import com.lxb.mvvmproject.base.BaseFragment;
import com.lxb.mvvmproject.util.LogUtil;

import java.lang.reflect.Method;

/**
 * @ describe:注入管理
 * @ author: Martin
 * @ createTime: 2019/3/27 20:55
 * @ version: 1.0
 */
public class InjectManager {

    /**
     * activity注入
     *
     * @param activity activity
     */
    public static int inject(Activity activity) {
        // 布局注入
        return injectActivityLayout(activity);
    }

    /**
     * fragment注入
     *
     * @param fragment fragment
     */
    public static int inject(BaseFragment fragment) {
        // 布局注入
        return injectLayout(fragment);
    }

    private static int injectActivityLayout(Activity activity) {
        // 获取类
        Class<? extends Activity> clazz = activity.getClass();

        // 获取类的注解
        ContentView contentView = clazz.getAnnotation(ContentView.class);
        if (contentView != null) {
            // 获取布局的值
            int layoutId = contentView.value();
            if (layoutId == ResId.DEFAULT_VALUE) {
                log(clazz,"Error Activity" + "(" + getClassName(clazz) + ".java:" + 1 + "):\n"+activity.getString(R.string.layout_id_error));
                throw new RuntimeException(getClassName(clazz) + activity.getString(R.string.layout_id_error));
            } else {
                // 第一种方法
//                activity.setContentView(layoutId);
//                try {
//                    Method method = clazz.getMethod("setContentView", int.class);
//                    method.invoke(activity, layoutId);
//                } catch (Exception e) {
//                    e.printStackTrace();
//                }
            }
            return layoutId;
        } else {
            log(clazz,"Error Activity" + "(" + getClassName(clazz) + ".java:" + 1 + "):\n"+activity.getString(R.string.layout_id_error));
            throw new NullPointerException(getClassName(clazz) + activity.getString(R.string.content_view_empty));
        }
    }

    private static int injectLayout(BaseFragment fragment) {
        // 获取类
        Class<? extends Fragment> clazz = fragment.getClass();

        // 获取类的注解
        ContentView contentView = clazz.getAnnotation(ContentView.class);
        if (contentView != null) {
            // 获取布局的值
            int layoutId = contentView.value();
            if (layoutId == ResId.DEFAULT_VALUE) {
                log(clazz,"Error Activity" + "(" + getClassName(clazz) + ".java:" + 1 + "):\n"+fragment.getString(R.string.layout_id_error));
                throw new RuntimeException(getClassName(clazz) + fragment.getString(R.string.layout_id_error));
            }
            // 第一种方法
            return layoutId;
//            fragment.setLayoutId(layoutId);
        } else {
            log(clazz,"Error Activity" + "(" + getClassName(clazz) + ".java:" + 1 + "):\n"+fragment.getString(R.string.layout_id_error));
            throw new NullPointerException(getClassName(clazz) + fragment.getString(R.string.content_view_empty));
        }
    }


    private static void log(Class<?> clazz,String s) {
        //生成指向java的字符串 加入到TAG标签里面
//        StackTraceElement[] s = Thread.currentThread().getStackTrace();
//        for (StackTraceElement value : s) {
//            if (value.getClassName().startsWith("lambda")) {
//                return;
//            }
//        }

        LogUtil.e(s);
    }

    private static String getClassName(Class clazz) {
        String className = clazz.getName();
        if (className.contains("$")) { //用于内部类的名字解析
            className = className.substring(className.lastIndexOf(".") + 1, className.indexOf("$"));
        } else {
            className = className.substring(className.lastIndexOf(".") + 1, className.length());
        }
        return className;
    }
}
