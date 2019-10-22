package com.lxb.mvvmproject.network;


import android.annotation.SuppressLint;

import com.lxb.mvvmproject.BuildConfig;
import com.lxb.mvvmproject.app.BaseApplication;
import com.lxb.mvvmproject.network.interceptor.AddCacheInterceptor;
import com.lxb.mvvmproject.network.interceptor.AddCookiesInterceptor;
import com.lxb.mvvmproject.network.interceptor.HttpHeadInterceptor;
import com.lxb.mvvmproject.network.interceptor.ReceivedCookiesInterceptor;

import java.io.File;
import java.security.SecureRandom;
import java.security.cert.CertificateException;
import java.security.cert.X509Certificate;
import java.util.concurrent.TimeUnit;

import javax.net.ssl.HostnameVerifier;
import javax.net.ssl.SSLContext;
import javax.net.ssl.SSLSession;
import javax.net.ssl.SSLSocketFactory;
import javax.net.ssl.TrustManager;
import javax.net.ssl.X509TrustManager;

import okhttp3.Cache;
import okhttp3.OkHttpClient;
import okhttp3.internal.cache.CacheInterceptor;
import okhttp3.logging.HttpLoggingInterceptor;
import retrofit2.Retrofit;
import retrofit2.adapter.rxjava2.RxJava2CallAdapterFactory;
import retrofit2.converter.gson.GsonConverterFactory;

/**
 * "浪小白" 创建 2019/7/1.
 * 界面名称以及功能:
 */

public class Api {
    private static Api mApi;
    private ApiService apiServer;
    private static final int DEFAULT_TIMEOUT = 30;

    private OkHttpClient getUnsafeOkHttpClient() {
        try {
            // Install the all-trusting trust manager TLS
            SSLContext sslContext = SSLContext.getInstance("TLS");
            sslContext.init(null, trustAllCerts, new SecureRandom());
            //cache url
            File httpCacheDirectory = new File(BaseApplication.getContext().getCacheDir(), "responses");
            // 50 MiB
            int cacheSize = 50 * 1024 * 1024;
            Cache cache = new Cache(httpCacheDirectory, cacheSize);
            // Create an ssl socket factory with our all-trusting manager
            SSLSocketFactory sslSocketFactory = sslContext.getSocketFactory();
            OkHttpClient.Builder okBuilder = new OkHttpClient.Builder();
            okBuilder.sslSocketFactory(sslSocketFactory, (X509TrustManager) trustAllCerts[0]);
            okBuilder.readTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            okBuilder.connectTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            okBuilder.writeTimeout(DEFAULT_TIMEOUT, TimeUnit.SECONDS);
            okBuilder.addInterceptor(new HttpHeadInterceptor());
            // 持久化cookie
            okBuilder.addInterceptor(new ReceivedCookiesInterceptor());
            okBuilder.addInterceptor(new AddCookiesInterceptor());
            // 添加缓存，无网访问时会拿缓存,只会缓存get请求
            okBuilder.addInterceptor(new AddCacheInterceptor());
            okBuilder.cache(cache);
            okBuilder.addInterceptor(new HttpLoggingInterceptor()
                    .setLevel(BuildConfig.DEBUG ?
                            HttpLoggingInterceptor.Level.BODY : HttpLoggingInterceptor.Level.NONE));
            okBuilder.hostnameVerifier(new HostnameVerifier() {
                @SuppressLint("BadHostnameVerifier")
                @Override
                public boolean verify(String hostname, SSLSession session) {
                    return true;
                }
            });
            return okBuilder.build();
        } catch (Exception e) {
            throw new RuntimeException(e);
        }
    }

    private Api() {
        //支持RxJava2
        Retrofit retrofit = new Retrofit.Builder()
                .baseUrl(ApiConstants.BaseUrl)
                .addConverterFactory(GsonConverterFactory.create())
                //支持RxJava2
                .addCallAdapterFactory(RxJava2CallAdapterFactory.create())
                .client(getUnsafeOkHttpClient())
                .build();
        apiServer = retrofit.create(ApiService.class);
    }

    public static Api getInstance() {
        if (mApi == null) {
            synchronized (Object.class) {
                if (mApi == null) {
                    mApi = new Api();
                }
            }
        }
        return mApi;
    }

    public ApiService getApiService() {
        return apiServer;
    }

    private TrustManager[] trustAllCerts = new TrustManager[]{new X509TrustManager() {
        @Override
        public void checkClientTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public void checkServerTrusted(X509Certificate[] chain, String authType) throws CertificateException {
        }

        @Override
        public X509Certificate[] getAcceptedIssuers() {
            return new X509Certificate[]{};
        }
    }};
}
