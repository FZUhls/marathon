package com.example.henry.marathon.utils;

import android.util.Log;

import okhttp3.*;
public class HttpUtil {

    public static void HttpGet(String address, Callback callback){
        OkHttpClient client = new OkHttpClient();
        Request request = new Request.Builder().url(address).build();
        client.newCall(request).enqueue(callback);
    }
    public static void HttpPost(String address,String json, Callback callback) {
        MediaType JSON = MediaType.parse("application/json");
        OkHttpClient client =   new OkHttpClient();
        RequestBody body = RequestBody.create(JSON,json);
        Log.d("JSONDDDD", "HttpPost: "+body.toString());
        //todo
        Request request = new Request.Builder().post(body).url(address).build();
        client.newCall(request).enqueue(callback);
    }
}
