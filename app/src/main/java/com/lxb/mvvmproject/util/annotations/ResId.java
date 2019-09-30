package com.lxb.mvvmproject.util.annotations;

/**
 * @ describe:
 * @ author: Martin
 * @ createTime: 2019/3/25 22:07
 * @ version: 1.0
 */
public abstract class ResId {

    /**
     * 这个值是客户端没有定义一个资源的资源id
     */
    public static final int DEFAULT_VALUE = -1;

    /**
     * 设置实用工具类不能实例化
     */
    private ResId() {
    }

}
