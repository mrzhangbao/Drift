package com.kevin.drift.Activity;

import android.graphics.Color;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentManager;
import android.support.v4.app.FragmentTransaction;
import android.support.v7.app.AppCompatActivity;
import android.view.View;
import android.view.ViewConfiguration;
import android.widget.ImageButton;
import android.widget.TextView;

import com.kevin.drift.Fragment.FriendsFragment;
import com.kevin.drift.Fragment.MessageFragment;
import com.kevin.drift.Fragment.ProFileFragment;
import com.kevin.drift.Fragment.WorldFragment;
import com.kevin.drift.PopWindow.MoreWindow;
import com.kevin.drift.R;
import com.zhy.android.percent.support.PercentLinearLayout;

import java.lang.reflect.Field;

/**
 * Created by Benson_Tom on 2016/4/27.
 * 首页的Activity，用于加载各个界面的Fragment，并且处理
 * Fragment界面之间的切换，实现了点击监听的方法
 */
public class HomeActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG= "HomeActivity";
    //底部菜单单个Tab的布局
    private PercentLinearLayout mTabWorld;
    private PercentLinearLayout mTabFriends;
    private PercentLinearLayout mTabMessage;
    private PercentLinearLayout mTabProfile;

    //底部菜单图片控件
    private ImageButton mImgBtWord;
    private ImageButton mImgBtFriends;
    private ImageButton mImgBtMessage;
    private ImageButton mImgBtProfile;

    //底部菜单文字的控件
    private TextView mTabTextWorld;
    private TextView mTabTextFriends;
    private TextView mTabTextMessage;
    private TextView mTabTextProfile;

    //Fragment 的定义
    private Fragment mWorldFragment;
    private Fragment mFriendsFragment;
    private Fragment mMessageFragment;
    private Fragment mProfileFragment;

    //添加消息按钮
    private ImageButton mImgBtAdd;
    //弹出菜单
    private MoreWindow mMoreWindow;



    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.home_activity);//设定布局文件
        setOverflowShowingAlways();//利用反射机制自定义ActionBar
        initWidget();//初始化控件
        initEvent();//初始化控件的处理事件
        setSelect(0);//默认选定当前界面为第一个界面
        // SQLite数据查询
//        DBUserManager manager = new DBUserManager(this);
//        for (int i =0;i<5;i++){
//            Log.i(TAG,"当前用户查询："+manager.query());
//        }



    }

    /**
     * 初始化控件
     */
    private void initWidget() {
        /**
         * 底部布局Tab的查找
         */
        mTabWorld = (PercentLinearLayout) this.findViewById(R.id.id_tabBarWorld);
        mTabFriends = (PercentLinearLayout) this.findViewById(R.id.id_tabBarFriends);
        mTabMessage = (PercentLinearLayout) this.findViewById(R.id.id_tabBarMessage);
        mTabProfile = (PercentLinearLayout) this.findViewById(R.id.id_tabBarProfile);

        //底部菜单Tab图片按钮查找
        mImgBtWord = (ImageButton) this.findViewById(R.id.id_tabBarWorld_img);
        mImgBtFriends = (ImageButton) this.findViewById(R.id.id_tabBarFriends_img);
        mImgBtMessage = (ImageButton) this.findViewById(R.id.id_tabBarMessage_img);
        mImgBtProfile = (ImageButton) this.findViewById(R.id.id_tabBarProfile_img);
        mImgBtAdd = (ImageButton) this.findViewById(R.id.id_tabBarAdd_img);

        //底部菜单Tab图片按钮设置监听事件，通过匿名内部类的方式实现
        mImgBtAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //显示更多菜单的布局
                showMoreWindow(view);
            }
        });



        mTabTextWorld = (TextView) this.findViewById(R.id.id_tabBarWorld_tv);
        mTabTextFriends = (TextView) this.findViewById(R.id.id_tabBarFriends_tv);
        mTabTextMessage = (TextView) this.findViewById(R.id.id_tabBarMessage_tv);
        mTabTextProfile = (TextView) this.findViewById(R.id.id_tabBarProfile_tv);


    }

    private void initEvent() {
        //设置监听
        mTabWorld.setOnClickListener(this);
        mTabFriends.setOnClickListener(this);
        mTabMessage.setOnClickListener(this);
        mTabProfile.setOnClickListener(this);
    }

    /**
     * 选择当前界面
     * @param i
     */
    private void setSelect(int i){
        //获取FragmentManager对象
        FragmentManager fm = getSupportFragmentManager();
        FragmentTransaction transaction = fm.beginTransaction();
        /**
         * 在现实新的界面的时候，先隐藏之前的界面
         * 通过Switch判断
         */
        hideFragment(transaction);

        switch (i) {
            case 0:
                /**
                 * 先判断当前布局是否存在，再进行界面绘制，防止内存浪费
                 * 1.当前视图不存在，就创建
                 * 2.视图存在，则直接显示
                 * 其他case 也一样
                 */
                if (mWorldFragment == null) {
                    mWorldFragment = new WorldFragment();


                    transaction.add(R.id.id_frameLayout, mWorldFragment);
                }else {
                    transaction.show(mWorldFragment);
                }
                //选择了该界面，则对应的图标换成有颜色的,文字也改为对应的颜色
                mImgBtWord.setImageResource(R.mipmap.tabbar_world_selected);
                mTabTextWorld.setTextColor(Color.parseColor("#FF8E29"));
                break;
            case 1:
                if (mFriendsFragment == null) {
                    mFriendsFragment = new FriendsFragment();
                    transaction.add(R.id.id_frameLayout, mFriendsFragment);

                }else {
                    transaction.show(mFriendsFragment);
                }

                mImgBtFriends.setImageResource(R.mipmap.friends364);
                mTabTextFriends.setTextColor(Color.parseColor("#FF8E29"));
                break;

            case 2:
                if (mMessageFragment == null) {
                    mMessageFragment = new MessageFragment();
                    transaction.add(R.id.id_frameLayout, mMessageFragment);
                }else {
                    transaction.show(mMessageFragment);

                }
                mImgBtMessage.setImageResource(R.mipmap.tabbar_message_center_highlighted);
                mTabTextMessage.setTextColor(Color.parseColor("#FF8E29"));
                break;

            case 3:
                if (mProfileFragment == null) {
                    mProfileFragment = new ProFileFragment();
                    transaction.add(R.id.id_frameLayout, mProfileFragment);

                }else {
                    transaction.show(mProfileFragment);
                }
                mImgBtProfile.setImageResource(R.mipmap.tabbar_profile_highlighted);
                mTabTextProfile.setTextColor(Color.parseColor("#FF8E29"));
                break;


            default:
                break;
        }
        //提交事务
        transaction.commit();
    }

    /**
     * 拥有隐藏Fragment，防止界面重叠
     * @param transaction
     */
    private void hideFragment(FragmentTransaction transaction)
    {
        /**
         * 判断当前显示界面是否为空，不为空就需要隐藏，空表示该界面并未显示，不需隐藏操作
         */
        if (mWorldFragment != null)
        {
            transaction.hide(mWorldFragment);
        }
        if (mFriendsFragment != null)
        {
            transaction.hide(mFriendsFragment);
        }
        if (mMessageFragment != null)
        {
            transaction.hide(mMessageFragment);
        }
        if (mProfileFragment != null)
        {
            transaction.hide(mProfileFragment);
        }
    }

    /**
     * 每次选定当前界面的时候，需要调用该方法
     * 用于界面底部Tab各个tab的图标、文字初始化，恢复成无颜色状态
     */
    private void resetImgs(){
        mImgBtWord.setImageResource(R.mipmap.tabbar_worlds);
        mImgBtFriends.setImageResource(R.mipmap.tabbar_friends64_nor);
        mImgBtMessage.setImageResource(R.mipmap.tabbar_message_center);
        mImgBtProfile.setImageResource(R.mipmap.tabbar_profile);

        mTabTextWorld.setTextColor(Color.BLACK);
        mTabTextFriends.setTextColor(Color.BLACK);
        mTabTextMessage.setTextColor(Color.BLACK);
        mTabTextProfile.setTextColor(Color.BLACK);

    }





    /**
     * 监听事件的处理，根据点击的按钮，来选定要显示的界面
     */
    @Override
    public void onClick(View v) {
        resetImgs();//初始化各个图标的颜色，先将各个图标设置为无颜色图标
        switch (v.getId()) {
            case R.id.id_tabBarWorld:
                setSelect(0);
                break;
            case R.id.id_tabBarFriends:
                setSelect(1);
                break;
            case R.id.id_tabBarMessage:
                setSelect(2);
                break;
            case R.id.id_tabBarProfile:
                setSelect(3);
                break;
            default:
                break;
        }

    }

    private void showMoreWindow(View view) {
        if (null == mMoreWindow) {
            mMoreWindow = new MoreWindow(this);//实例化弹出菜单的对象
            mMoreWindow.init();
        }
        //调用MoreWindow的showMoreWindow传入一个view,和边距
        mMoreWindow.showMoreWindow(view,100);
    }

    /**
     * 利用反射机制自定义ActionBar布局的显示
     */
    private void setOverflowShowingAlways() {
        try {
            ViewConfiguration config = ViewConfiguration.get(this);
            Field menuKeyField = ViewConfiguration.class
                    .getDeclaredField("sHasPermanentMenuKey");
            menuKeyField.setAccessible(true);
            menuKeyField.setBoolean(config, false);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


}
