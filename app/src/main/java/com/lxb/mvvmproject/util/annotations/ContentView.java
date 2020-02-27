package com.lxb.mvvmproject.util.annotations;


import androidx.annotation.LayoutRes;

import java.lang.annotation.ElementType;
import java.lang.annotation.Retention;
import java.lang.annotation.RetentionPolicy;
import java.lang.annotation.Target;

/**
 * ElementType.METHOD // 该注解作用在方法上
 * 
 * @ describe: 自动setContentView注解
 * @ author: Martin
 * @ createTime: 2019/3/25 15:30
 * @ version: 1.0
 */
@Target(ElementType.TYPE) // 该注解作用在类上
@Retention(RetentionPolicy.RUNTIME) // jvm运行时通过反射机制获取该注解的值
public @interface ContentView {
    @LayoutRes
    int value() default ResId.DEFAULT_VALUE;

}
