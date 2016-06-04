package com.kevin.drift.Utils;

import android.os.Environment;
import android.util.Log;

import com.google.gson.Gson;
import com.kevin.drift.Entity.RegisterEntity;
import com.kevin.drift.Entity.User;
import com.zhy.http.okhttp.OkHttpUtils;
import com.zhy.http.okhttp.callback.Callback;
import com.zhy.http.okhttp.callback.StringCallback;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.Call;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Benson_Tom on 2016/5/25.
 */
public class OkHttpManager {
    private static final String TAG = "OkHttpManager";
    /**
     * 静态实例
     */
    private static OkHttpManager okHttpManager;

    /**
     * OkHttpClient实例
     */
    private static final OkHttpClient client = new OkHttpClient();

    private static OkHttpManager getInstance(){
        if (okHttpManager == null){
            okHttpManager = new OkHttpManager();
        }
        return okHttpManager;
    }
    public static final MediaType JSON = MediaType.parse("application/json; charset=utf-8");

    public OkHttpManager() {
        //配置okHttpClient的参数
        client.newBuilder().connectTimeout(10, TimeUnit.SECONDS);
        client.newBuilder().readTimeout(10, TimeUnit.SECONDS);
        client.newBuilder().writeTimeout(10, TimeUnit.SECONDS);
        //设置缓存信息的处理：创建缓存对象，构造方法用于控制缓存位置及最大缓存大小【单位是Byte】
        Cache cache = new Cache(new File(Environment.getExternalStorageDirectory().getPath()), 10 * 1024 * 1024);
        client.newBuilder().cache(cache);
    }

    public static String Get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static String Post(String url,String json) throws IOException {
        RequestBody body = RequestBody.create(JSON,json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    public static String login(String url,String userAccount,String password) throws IOException {
        FormBody body = new FormBody.Builder()
                .add("userAccount",userAccount)
                .add("password",password)
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return  response.body().string();
    }

    public static String register(String url, User u) throws IOException {
        Log.i(TAG,"RegisterInformation："+u.toString());
        FormBody body = new FormBody.Builder()
                .add("userAccount",u.getUserAccount())
                .add("password",u.getPassword())
                .add("username",u.getUsername())
                .build();
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return  response.body().string();
    }


    public static String Kevin(String url,String userAccount,String password){
        final String s=null;
        RegisterEntity r = new RegisterEntity();
        r.setUsername("hello");
        r.setPassword("123456");
        r.setPhone("55555");
        Gson gson = new Gson();
        String k = gson.toJson(r);
        Log.i("TAG","*********Json:"+k);
        OkHttpUtils.post().url("http://119.124.20.43:8080/WebServer/TEST")
                .addParams("username","haha")
                .addParams("phone","123")
                .addParams("password",k)
                .build()
                .execute(new Callback() {
                    @Override
                    public Object parseNetworkResponse(Response response) throws Exception {
                        String s =response.body().string();
                        for (int i =0;i<5;i++){
                            Log.i("TAG","***********:"+s);
                        }
                        return s;
                    }

                    @Override
                    public void onError(Call call, Exception e) {

                    }

                    @Override
                    public void onResponse(Object response) {

                    }
                });


        return null;
    }
}
