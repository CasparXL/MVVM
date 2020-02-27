package com.lxb.mvvmproject.aop;

import android.util.Log;
import android.view.View;

import org.aspectj.lang.ProceedingJoinPoint;
import org.aspectj.lang.annotation.Around;
import org.aspectj.lang.annotation.Aspect;
import org.aspectj.lang.annotation.Pointcut;

import java.util.Calendar;

/**
 *    author : Android 轮子哥
 *    github : https://github.com/getActivity/AndroidProject
 *    time   : 2019/12/06
 *    desc   : 防重复点击处理
 */
@Aspect
public class SingleClickAspect {

    /**
     * 最近一次点击的时间
     */
    private long sLastClickTime;
    /**
     * 最近一次点击的控件ID
     */
    private int sLastClickViewId;

    /**
     * 方法切入点
     */
    @Pointcut("execution(@com.lxb.mvvmproject.aop.SingleClick * *(..))")
    public void method() {}

    /**
     * 在连接点进行方法替换
     */
    @Around("method() && @annotation(singleClick)")
    public void aroundJoinPoint(ProceedingJoinPoint joinPoint, SingleClick singleClick) throws Throwable {
        View view = null;
        for (Object arg : joinPoint.getArgs()) {
            if (arg instanceof View) {
                view = (View) arg;
            }
        }
        if (view != null) {
            long currentTime = Calendar.getInstance().getTimeInMillis();
            if (currentTime - sLastClickTime < singleClick.value() && view.getId()
                    == sLastClickViewId) {
                Log.i("SingleClick", "发生快速点击");
                return;
            }
            sLastClickTime = currentTime;
            sLastClickViewId = view.getId();
            //执行原方法
            joinPoint.proceed();
        }
    }
}