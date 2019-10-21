package com.lxb.mvvmproject.network.util;

import com.google.gson.Gson;

import okhttp3.RequestBody;

/**
 * Post请求Json数据转换
 */
public class PostJson {
    public static RequestBody toRequestBody(String json) {
        return RequestBody.create(okhttp3.MediaType.parse("application/json; charset=utf-8"), json);
    }

    public static String toJsonString(Object json) {
        return new Gson().toJson(json);
    }
}
