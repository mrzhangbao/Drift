package com.kevin.drift.Fragment;

import android.text.Editable;
import android.text.TextUtils;
import android.text.TextWatcher;
import android.view.LayoutInflater;

import android.view.View;
import android.widget.AdapterView;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.promeg.pinyinhelper.Pinyin;
import com.kevin.drift.Friends.ClearEditText;
import com.kevin.drift.Friends.PinyinComparator;
import com.kevin.drift.Friends.SideBar;
import com.kevin.drift.Friends.FriendsAdapter;
import com.kevin.drift.R;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Benson_Tom on 2016/4/22.
 */
public class FriendsFragment extends BaseFragment{
    private ListView mListView;
    private SideBar mSideBar;

    private TextView mDialog;
    private FriendsAdapter mAdapter;

    private List<SortModel> mList;
    private View headerView;

    private View mGroupChat;
    private View mAddFriends;
    private View mPublicMsg;

    /**
     * 根据拼音来排序ListView里面的数据类
     * savedInstanceState
     */
    private PinyinComparator pinyinComparator;

    private ClearEditText mClearEditText;

    @Override
    protected int setLayout() {
        return R.layout.fragment_friends;
    }

    @Override
    protected void initEvent(View view) {

        initView(view);

    }

    private void initView(View v) {

        pinyinComparator = new PinyinComparator();

        mSideBar = (SideBar) v.findViewById(R.id.id_sideBar_CustomView);
        mDialog = (TextView) v.findViewById(R.id.id_dialog_textView);
        mSideBar.setText(mDialog);
        /**
         * 设置右侧触摸监听事件
         */
        mSideBar.setOnTouchingLetterChangedListener(new SideBar.OnTouchingLetterChangedListener() {
            @Override
            public void onTouchingLetterChanged(String s) {
                //该字母首次出现的位置
                int position = mAdapter.getPositionForSection(s.charAt(0));
                if (position != -1) {
                    mListView.setSelection(position);
                }
            }
        });
        mListView = (ListView) v.findViewById(R.id.id_content_listView);

        //Edited here by Kevin at 2016/4/5 15:57;
        //使用模拟数据
        mList = filledDate(getResources().getStringArray(R.array.date));

//        LayoutInflater inflater = (LayoutInflater) getSystemService(Context.LAYOUT_INFLATER_SERVICE);
        LayoutInflater inflater = (LayoutInflater) LayoutInflater.from(getContext());
        headerView = inflater.inflate(R.layout.contacts_header,mListView,false);
        mListView.addHeaderView(headerView);
        setHeaderClick(headerView);

        /**
         * 根据a-z进行排序源数据
         */
        Collections.sort(mList, pinyinComparator);
        mAdapter = new FriendsAdapter(mList, getActivity());
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(getActivity(),"信息："+mList.get(i).getUsername(),Toast.LENGTH_SHORT).show();
            }
        });

        mClearEditText = (ClearEditText) v.findViewById(R.id.id_search_clear);

        mClearEditText.addTextChangedListener(new TextWatcher() {
            @Override
            public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

            }

            @Override
            public void onTextChanged(CharSequence charSequence, int i, int i1, int i2) {
                //当输入框为空的时候更新为原来的列表，否则过滤数据
                filterData(charSequence.toString());
            }

            @Override
            public void afterTextChanged(Editable editable) {

            }
        });

    }

    private void setHeaderClick(View headerView) {
        mAddFriends = headerView.findViewById(R.id.id_addFriends_layout);
        mGroupChat = headerView.findViewById(R.id.id_group_layout);
        mPublicMsg = headerView.findViewById(R.id.id_publicMsg_layout);

        mAddFriends.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"新朋友",Toast.LENGTH_SHORT).show();
            }
        });
        mGroupChat.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"群聊",Toast.LENGTH_SHORT).show();
            }
        });
        mPublicMsg.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Toast.makeText(getActivity(),"系统消息",Toast.LENGTH_SHORT).show();
            }
        });

    }
    /**
     * 为ListView填充数据
     * date
     */
    private List<SortModel> filledDate(String[] date) {
        List<SortModel> mSortList = new ArrayList<>();

        for (int i = 0; i < date.length; i++) {
            SortModel friendsEntity = new SortModel();
            friendsEntity.setUsername(date[i]);
            //汉字转换成拼音,截取拼音字母
            char[] chars = date[i].toCharArray();
            String pinyin = Pinyin.toPinyin(chars[0]);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            //正则表达式，判断首字母是否为英文字母
            if (sortString.matches("[A-Z]")) {
                friendsEntity.setUserFirstLetter(sortString.toUpperCase());
            } else {
                friendsEntity.setUserFirstLetter("#");
            }
            mSortList.add(friendsEntity);
        }
        return mSortList;
    }
    //用于将查询的数据进行重新排序
    private void filterData(String filterStr) {
        List<SortModel> filterDataList = new ArrayList<>();
        if (TextUtils.isEmpty(filterStr)) {
            filterDataList = mList;
        } else {
            filterDataList.clear();
            for (SortModel friendsEntity : mList) {
                String userName = friendsEntity.getUsername();
                char[] c  = userName.toCharArray();
                //先判断字符串是否为英文，是的话直接导入，不是因为，则将字符转换成拼音，并换成大写进行比较，
                //满足条件就加入到ListView之中
                if (userName.toUpperCase().indexOf(filterStr.toString().toUpperCase()) != -1 ||
                        Pinyin.toPinyin(c[0]).toUpperCase().startsWith(filterStr.toString().toUpperCase())) {
                    filterDataList.add(friendsEntity);
                }
            }
        }
        Collections.sort(filterDataList, pinyinComparator);
        mAdapter.updateListView(filterDataList);
    }


}
