package com.example.henry.marathon.utils;

import okhttp3.*;
public class HttpUtil {

    public static void HttpGet(String address, Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
    public static void HttpPost(String address, Object object, Callback callback) {
        OkHttpClient client =   new OkHttpClient();
        //todo
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
