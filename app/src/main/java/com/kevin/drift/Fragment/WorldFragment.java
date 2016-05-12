package com.kevin.drift.Fragment;


import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.ActionBar;
import android.support.v7.widget.LinearLayoutManager;
import android.support.v7.widget.RecyclerView;
import android.view.Menu;
import android.view.MenuInflater;
import android.view.View;

import com.kevin.drift.Adapter.DriftMessageAdapter;
import com.kevin.drift.Entity.DriftMessageInfo;
import com.kevin.drift.R;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Benson_Tom on 2016/4/22.
 */
public class WorldFragment extends BaseFragment{
    private RecyclerView mRecyclerView;
    private DriftMessageAdapter mAdapter;
    private List<DriftMessageInfo> mList;

    

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
        initDatas();
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
        mList = new ArrayList<>();
        for (int i = 0; i< 25; i++){
            d = new DriftMessageInfo();
            d.setUserIcon("haha");
            d.setUserName("Kevin编号"+i);
            d.setUserAddress("北京");
            d.setDriftTime("5-07");
            d.setDriftImg("hha");
            d.setDriftContent("我很无聊，因为我是大写的测试数据，编号是"+"00"+i);
            mList.add(d);
        }
    }
}
