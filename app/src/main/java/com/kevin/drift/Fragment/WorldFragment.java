package com.kevin.drift.Fragment;


import android.os.AsyncTask;
import android.os.Bundle;
import android.os.Handler;
import android.support.annotation.Nullable;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.util.Log;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kevin.drift.Adapter.DriftMessageAdapter;
import com.kevin.drift.Entity.DriftMessageInfo;
import com.kevin.drift.R;
import com.kevin.drift.Utils.RandomMessage;
import com.kevin.drift.Utils.JsonBean;
import com.kevin.drift.Utils.OkHttpManager;
import com.kevin.drift.Utils.URLManager;

import java.io.IOException;
import java.lang.reflect.Type;
import java.util.List;

/**
 * Created by Benson_Tom on 2016/4/22.
 */
public class WorldFragment extends BaseFragment implements SwipeRefreshLayout.OnRefreshListener{
    private static final String TAG ="WorldFragment";
    private RecyclerView mRecyclerView;
    private DriftMessageAdapter mAdapter;
    private List<DriftMessageInfo> mList;
    private SwipeRefreshLayout mRefreshLayout;

    

    @Override
    protected int setLayout() {
        return R.layout.fragment_world;
    }

    @Override
    public void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);


    }

    @Override
    protected void initEvent(View view) {
        setHasOptionsMenu(true);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.id_world_recyclerView);
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        initDatas();
        mRefreshLayout.setColorSchemeResources(R.color.color1,R.color.color2,R.color.color3,R.color.color4);
        mRefreshLayout.setOnRefreshListener(this);
        LinearLayoutManager m = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(m);
        mAdapter = new DriftMessageAdapter(getContext(),mList);
        mRecyclerView.setAdapter(mAdapter);

    }


    @Override
    public void onCreateOptionsMenu(Menu menu, MenuInflater inflater) {
        menu.clear();
        inflater.inflate(R.menu.world_actionbar_menu, menu);

    }

    private void initDatas() {
        DriftMessageInfo d = null;
        mList = RandomMessage.GetMessage();

        new DriftAsyncTask().execute(URLManager.GET_MESSAGE);
    }

    @Override
    public void onRefresh() {
        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                mList = RandomMessage.ReflashMessage(mList);
                mAdapter.notifyDataSetChanged();
                mRefreshLayout.setRefreshing(false);
            }
        }, 3000);
    }

    class DriftAsyncTask extends AsyncTask<String,Void,String> {

        @Override
        protected String doInBackground(String... strings) {
            String s="";
            JsonBean bean = null;
            try {
                s= OkHttpManager.Get(strings[0]);
                Gson g = new Gson();
                Type type = new TypeToken<JsonBean>(){}.getType();
                bean = g.fromJson(s,type);
            } catch (IOException e) {
                e.printStackTrace();
            }
//            try {
////                s=OkHttpManager.Post("http://119.124.20.43:8080/WebServer/TEST",k);
//                for (int i =0; i< 5;i++){
////                    Log.i(TAG,"登陆信息:"+OkHttpManager.login("http://119.29.230.231:8080/tx/user_login","7788","hahal"));
////                    Log.i(TAG,"注册信息:"+bb.toString());
////                    Log.i(TAG,"注册信息:"+OkHttpManager.Post("http://119.29.230.231:8080/tx/user_register",bb));
////                    Log.i(TAG,"注册信息:"+OkHttpManager.register("http://119.29.230.231:8080/tx/user_register",u));
//                }
//
//            } catch (IOException e) {
//                e.printStackTrace();
//            }
            Log.i(TAG,"消息结果:"+bean.messageInfo);
            Log.i(TAG,"服务器获取信息:"+bean.user);
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
