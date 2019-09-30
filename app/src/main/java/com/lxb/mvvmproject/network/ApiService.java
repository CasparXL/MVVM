package com.lxb.mvvmproject.network;



import com.lxb.mvvmproject.bean.Bean;

import io.reactivex.Observable;
import okhttp3.RequestBody;
import retrofit2.http.Body;
import retrofit2.http.GET;
import retrofit2.http.Headers;
import retrofit2.http.POST;
import retrofit2.http.Query;

/**
 * "浪小白" 创建 2019/7/1.
 * 界面名称以及功能:
 */

public interface ApiService {
    @Headers("Content-type:application/x-www-form-urlencoded;charset=UTF-8;")
    @POST("/")
    Observable<String> getStartData(@Body String s);
    //用户协议
    @GET("user/getUserAgreement")
    Observable<Bean> getUserAgreement();


}
