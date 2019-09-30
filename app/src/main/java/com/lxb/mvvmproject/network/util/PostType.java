package com.lxb.mvvmproject.network.util;

import com.google.gson.Gson;

import java.io.File;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.RequestBody;

/**
 * Retrofit的Post请求工具类
 */
public class PostType {
    /**
     * @param value
     * @return
     */
    public static RequestBody toStringRequestBody(String value) {
        if (value != null)
        return RequestBody.create(MediaType.parse("text/plain"), value);
        else
            return null;
    }

    /**
     * 上传图片类型
     *
     * @param value
     * @return
     */
    public static RequestBody toFileRequestBody(File value) {
        if (value != null)
            return RequestBody.create(MediaType.parse("multipart/form-data"), value);
        else
            return null;
    }

    /**
     * @param requestDataMap 用于Retrofit的Post请求form表单数据过多可以使用该请求
     * @return
     */
    public static Map<String, RequestBody> toMapRequestBody(Map<String, String> requestDataMap) {
        Map<String, RequestBody> requestBodyMap = new HashMap<>();
        for (String key : requestDataMap.keySet()) {
            RequestBody requestBody = RequestBody.create(MediaType.parse("multipart/form-data"),
                    requestDataMap.get(key) == null ? "" : requestDataMap.get(key));
            requestBodyMap.put(key, requestBody);
        }
        return requestBodyMap;
    }

    /**
     * @param files
     * @return
     */
    public static List<MultipartBody.Part> filesToMultipartBodyParts(List<File> files) {
        List<MultipartBody.Part> parts = new ArrayList<>(files.size());
        for (File file : files) {
            // TODO: 16-4-2  这里为了简单起见，没有判断file的类型
            RequestBody requestBody = RequestBody.create(MediaType.parse("image/png"), file);
            MultipartBody.Part part = MultipartBody.Part.createFormData("file", file.getName(), requestBody);
            parts.add(part);
        }
        return parts;
    }
    /**
     * @param Files 用于Retrofit的Post请求form表单数据过多可以使用该请求
     * @return
     */
    public static Map<String, RequestBody> toMapRequestBody(List<File> Files) {
        Gson gson = new Gson();
        RequestBody info = RequestBody.create(MultipartBody.FORM, gson.toJson(Files));
        HashMap<String, RequestBody> map = new HashMap<>();
        for (File file : Files) {
            RequestBody requestFile = RequestBody.create(MediaType.parse("image/*"), file);
            map.put("pictures\";filename=\"" + file.getName(), requestFile);
        }
        return map;
    }

}
