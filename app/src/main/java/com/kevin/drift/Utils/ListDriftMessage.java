package com.kevin.drift.Utils;

import com.google.gson.Gson;
import com.kevin.drift.Entity.DriftMessageInfo;
import com.zhy.http.okhttp.callback.Callback;

import java.util.List;

import okhttp3.Response;

/**
 * Created by Benson_Tom on 2016/5/14.
 */
public abstract class ListDriftMessage extends Callback<List<DriftMessageInfo>>{
    @Override
    public List<DriftMessageInfo> parseNetworkResponse(Response response) throws Exception {
        String s = response.body().string();
        List<DriftMessageInfo> list = new Gson().fromJson(s,List.class);
        return list;
    }
}
