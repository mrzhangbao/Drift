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
 * 首页的Fragment
 * 实现了SwipeRefresh下拉刷新接口
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

    /**
     * 处理业务逻辑
     * @param view
     */
    @Override
    protected void initEvent(View view) {
        setHasOptionsMenu(true);
        mRecyclerView = (RecyclerView) view.findViewById(R.id.id_world_recyclerView);
        mRefreshLayout = (SwipeRefreshLayout) view.findViewById(R.id.swipeRefreshLayout);
        initDatas();
        //设置下拉刷新过程中的颜色变化
        mRefreshLayout.setColorSchemeResources(R.color.color1,R.color.color2,R.color.color3,R.color.color4);
        mRefreshLayout.setOnRefreshListener(this);
        LinearLayoutManager m = new LinearLayoutManager(getActivity());
        mRecyclerView.setLayoutManager(m);
        mAdapter = new DriftMessageAdapter(getContext(),mList);
        mRecyclerView.setAdapter(mAdapter);//设置RecyclerView的适配器

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
        //下拉刷新后内容的添加
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

    /**
     * 通过异步任务从服务器获得消息数据
     */
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
