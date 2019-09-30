package com.lxb.mvvmproject.network.interceptor;

import android.content.Context;
import android.content.SharedPreferences;

import com.lxb.mvvmproject.app.BaseApplication;
import com.lxb.mvvmproject.util.SP;

import java.io.IOException;

import okhttp3.Interceptor;
import okhttp3.Request;
import okhttp3.Response;

public class AddCookiesInterceptor implements Interceptor {

    @Override
    public Response intercept(Chain chain) throws IOException {
        final Request.Builder builder = chain.request().newBuilder();
        String cookie = SP.get("cookie", "");
        builder.addHeader("Cookie", cookie);
        return chain.proceed(builder.build());
    }
}