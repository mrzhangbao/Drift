package com.kevin.drift.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.widget.AdapterView;
import android.widget.ImageButton;
import android.widget.ListView;
import android.widget.TextView;
import android.widget.Toast;

import com.github.promeg.pinyinhelper.Pinyin;
import com.kevin.drift.Adapter.ContactsAdapter;
import com.kevin.drift.Entity.MobileContacts;
import com.kevin.drift.Friends.ContactsPinyinComparator;
import com.kevin.drift.Friends.SideBar;
import com.kevin.drift.R;
import com.kevin.drift.Utils.GetContacts;

import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

/**
 * Created by Benson_Tom on 2016/6/4.
 */
public class MobileContactsActivity extends AppCompatActivity{
    private static final String TAG="MobileContactsActivity";
    private ListView mListView;
    private SideBar mSideBar;

    private TextView mDialog;
    private ContactsAdapter mAdapter;

    private List<MobileContacts> mList;
    private ImageButton mBack;


    /**
     * 根据拼音来排序ListView里面的数据类
     * savedInstanceState
     */
    private ContactsPinyinComparator pinyinComparator;


    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.mobile_contacts_activity);
        initView();
    }

    private void initView() {

        pinyinComparator = new ContactsPinyinComparator();

        mSideBar = (SideBar) findViewById(R.id.id_sideBar_CustomView);
        mDialog = (TextView) findViewById(R.id.id_dialog_textView);
        mBack = (ImageButton) findViewById(R.id.mobile_contacts_back);
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
        mListView = (ListView) findViewById(R.id.id_content_listView);

        //Edited here by Kevin at 2016/4/5 15:57;
        //使用模拟数据
        mList = filledDate(GetContacts.getLocalContacts(this));

        /**
         * 根据a-z进行排序源数据
         */
        Collections.sort(mList, pinyinComparator);
        mAdapter = new ContactsAdapter(mList, this);
        mListView.setAdapter(mAdapter);

        mListView.setOnItemClickListener(new AdapterView.OnItemClickListener() {
            @Override
            public void onItemClick(AdapterView<?> adapterView, View view, int i, long l) {
                Toast.makeText(MobileContactsActivity.this,"信息："+mList.get(i).getName(),Toast.LENGTH_SHORT).show();
            }
        });
        //点击返回按钮后返回到之前的界面
        mBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                MobileContactsActivity.this.finish();
                overridePendingTransition(R.anim.back_in_to_left,R.anim.back_out_to_right);
            }
        });

    }
    /**
     * 为ListView填充数据
     * date
     */
    private List<MobileContacts> filledDate(List<MobileContacts> l) {
        List<MobileContacts> mSortList = new ArrayList<>();

        for (int i = 0; i < l.size(); i++) {
            MobileContacts f = new MobileContacts();
            f.setName(l.get(i).getName());
            //汉字转换成拼音,截取拼音字母
            char[] chars = l.get(i).getName().toCharArray();
            String pinyin = Pinyin.toPinyin(chars[0]);
            String sortString = pinyin.substring(0, 1).toUpperCase();

            //正则表达式，判断首字母是否为英文字母
            if (sortString.matches("[A-Z]")) {
                f.setUserFirstLetter(sortString.toUpperCase());
            } else {
                f.setUserFirstLetter("#");
            }
            mSortList.add(f);
        }
        return mSortList;
    }

}
