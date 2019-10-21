package com.lxb.mvvmproject.network.interceptor;

import android.content.Context;
import android.content.SharedPreferences;
import android.text.TextUtils;
import android.util.Log;

import com.lxb.mvvmproject.app.BaseApplication;
import com.lxb.mvvmproject.util.LogUtil;
import com.lxb.mvvmproject.util.SP;

import java.io.IOException;
import java.util.Collection;
import java.util.HashMap;
import java.util.List;

import okhttp3.Interceptor;
import okhttp3.Response;

public class ReceivedCookiesInterceptor implements Interceptor {

    @Override
    public Response intercept(Interceptor.Chain chain) throws IOException {
        Response originalResponse = chain.proceed(chain.request());
        //这里获取请求返回的cookie
        if (!originalResponse.headers("Set-Cookie").isEmpty()) {
            List<String> d = originalResponse.headers("Set-Cookie");
            LogUtil.e("------------得到的 cookies:" + d.toString());
            // 返回cookie
            if (!TextUtils.isEmpty(d.toString())) {
                String oldCookie = SP.get("cookie", "");
                HashMap<String, String> stringStringHashMap = new HashMap<>();

                // 之前存过cookie
                if (!TextUtils.isEmpty(oldCookie)) {
                    String[] substring = oldCookie.split(";");
                    for (String aSubstring : substring) {
                        if (aSubstring.contains("=")) {
                            String[] split = aSubstring.split("=");
                            stringStringHashMap.put(split[0], split[1]);
                        } else {
                            stringStringHashMap.put(aSubstring, "");
                        }
                    }
                }
                String join = join(d, ";");
                String[] split = join.split(";");

                // 存到Map里
                for (String aSplit : split) {
                    String[] split1 = aSplit.split("=");
                    if (split1.length == 2) {
                        stringStringHashMap.put(split1[0], split1[1]);
                    } else {
                        stringStringHashMap.put(split1[0], "");
                    }
                }

                // 取出来
                StringBuilder stringBuilder = new StringBuilder();
                if (stringStringHashMap.size() > 0) {
                    for (String key : stringStringHashMap.keySet()) {
                        stringBuilder.append(key);
                        String value = stringStringHashMap.get(key);
                        if (!TextUtils.isEmpty(value)) {
                            stringBuilder.append("=");
                            stringBuilder.append(value);
                        }
                        stringBuilder.append(";");
                    }
                }
                SP.put("config", stringBuilder.toString());

//                    Log.e("jing", "------------处理后的 cookies:" + stringBuilder.toString());
            }
        }

        return originalResponse;
    }

    private static String join(Collection collection, String separator) {
        return collection == null ? null : join((Collection) collection.iterator(), separator);
    }
}
