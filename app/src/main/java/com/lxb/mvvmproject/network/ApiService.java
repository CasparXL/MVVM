package com.lxb.mvvmproject.network;

import java.util.HashMap;

import io.reactivex.Observable;
import retrofit2.http.Body;
import retrofit2.http.FieldMap;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.QueryMap;

/**
 * "浪小白" 创建 2019/7/1.
 * 界面名称以及功能:
 */

public interface ApiService {
    @POST("xxxxxx")
    Observable<String> getStartData(@QueryMap HashMap<String,Object> objectHashMap);


}
