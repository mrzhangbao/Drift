package com.kevin.drift.Utils;

import android.os.Environment;
import android.util.Log;

import com.kevin.drift.Entity.User;

import java.io.File;
import java.io.IOException;
import java.util.concurrent.TimeUnit;

import okhttp3.Cache;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

/**
 * Created by Benson_Tom on 2016/5/25.
 * OkHttp3的管理辅助类
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

    /**
     * 根据URL获取服务器上的数据
     * @param url
     * @return
     * @throws IOException
     */
    public static String Get(String url) throws IOException {
        Request request = new Request.Builder()
                .url(url)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    /**
     * 通过URL来提交Json格式的数据信息到服务器中
     * @param url
     * @param json
     * @return
     * @throws IOException
     */
    public static String Post(String url,String json) throws IOException {
        RequestBody body = RequestBody.create(JSON,json);
        Request request = new Request.Builder()
                .url(url)
                .post(body)
                .build();
        Response response = client.newCall(request).execute();
        return response.body().string();
    }

    /**
     * 通过提交键值对的形式，向服务器提交数据
     * @param url 服务器URL
     * @param userAccount
     * @param password
     * @return
     * @throws IOException
     */
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

    /**
     * 通过提交键值对的形式，向服务器提交数据
     * @param url
     * @param u
     * @return
     * @throws IOException
     */
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
}
