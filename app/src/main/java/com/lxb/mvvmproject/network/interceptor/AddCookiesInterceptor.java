package com.lxb.mvvmproject.network.interceptor;


import com.lxb.mvvmproject.helper.MMKVUtil;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AddCookiesInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request.Builder builder = chain.request().newBuilder();
        String cookie = MMKVUtil.getInstance().decodeString("cookie");
        builder.addHeader("Cookie", cookie);
        return chain.proceed(builder.build());
    }
}