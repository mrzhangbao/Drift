package com.kevin.drift;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.AsyncTask;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.text.TextUtils;
import android.util.Log;
import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.kevin.drift.Activity.HomeActivity;
import com.kevin.drift.Activity.RegisterActivity;
import com.kevin.drift.Entity.User;
import com.kevin.drift.Utils.AESUtil;
import com.kevin.drift.Utils.CustomDialog.KevinLoadingView;
import com.kevin.drift.Utils.DBUtils.DBUserManager;
import com.kevin.drift.Utils.JsonBean;
import com.kevin.drift.Utils.URLManager;
import com.kevin.drift.Utils.OkHttpManager;
import com.kevin.drift.Utils.ToRoundBitmap;

import java.io.IOException;
import java.lang.reflect.Type;

/**
 * Edited here by Kevin at 2016/6/5 13:16;
 * 登陆界面的Activity
 */
public class LoginActivity extends AppCompatActivity {
    private static final String TAG ="LoginActivity";
    private long exitTime = 0;
    private Button mLoginButton; //登陆按钮
    private Button mRegister;//注册按钮
    private EditText mAccountEt;//用户账户
    private EditText mPasswordEt;//用户密码
    private ImageView mUserIconImg;
    private String userAccount;
    private String password;
    private KevinLoadingView mView;//自定义Dialog
    private JsonBean bean;//自定义Gson解析bean


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设定当前Activity视图布局文件
        setContentView(R.layout.login_activity);
        //初始化布局里面的控件
        mView = new KevinLoadingView();
        initWidget();
        initEvent();//初始化事件


    }


    /**
     * 各个控件的实例化
     */
    private void initWidget() {
        //控件的查找
        mUserIconImg = (ImageView) this.findViewById(R.id.id_userIcon_imgv);
        mLoginButton = (Button) this.findViewById(R.id.id_login_bt);
        mRegister = (Button) this.findViewById(R.id.login_register_bt);
        mPasswordEt = (EditText) this.findViewById(R.id.id_password_et);
        mAccountEt = (EditText) this.findViewById(R.id.id_account_et);

        /**
         * 获取drawable资源文件下的图片，转换成bitmap格式
         * 调用ToRoundBitmap方法，将图片圆形化
         * 将修改的图片显示
         */
        Bitmap bitmap = BitmapFactory.decodeResource(getResources(),R.drawable.ceshi01);
        mUserIconImg.setImageBitmap(new ToRoundBitmap().toRoundBitmap(bitmap));
    }

    private void initEvent() {
        //通过匿名内部类的方式来设置登陆按钮的点击监听事件
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //登陆业务逻辑处理方法
                checkUser();

            }
        });

        //通过匿名内部类的方式来设置注册按钮的点击监听事件
        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                //通过意图来实现Activity的跳转，这里跳转到注册的Activity
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(intent);
                //使用Activity跳转动画
                overridePendingTransition(R.anim.activity_in_from_right,R.anim.activity_out_to_left);
            }
        });

    }

    /**
     * 登陆的业务逻辑处理
     */
    private void checkUser() {
        //获取用户输入的数据信息
        userAccount = mAccountEt.getText().toString();
        password = mPasswordEt.getText().toString();
        //非空判断，若为空，则提示用户错误信息
        if (isEmpty(userAccount)){
            mAccountEt.setError("账号不能为空");
            return;
        }
        if (isEmpty(password)){
            mPasswordEt.setError("密码不能为空");
            return;
        }
        /**
         * 通过轻量级的异步任务来处理网络数据交互的耗时操作，
         * 不能在主线程中进行网络数据交互，不然会抛出异常
         * 调用AES辅助类对用户输入的密码进行加密处理
         */
        new LoginAsyncTask(userAccount, AESUtil.encrypt(AESUtil.KEY,password)).execute(URLManager.USER_LOGIN);

    }

    /**
     * 非空判断，返回一个boolean
     * @param s
     * @return
     */
    private boolean isEmpty(String s){
        if (TextUtils.isEmpty(s)){
            return true;
        }else {
            return  false;
        }
    }

    /**
     * 在2s内按了两次返回键，则退出程序
     * @param keyCode
     * @param event
     * @return
     */
    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK && event.getAction() == KeyEvent.ACTION_DOWN){
            if ((System.currentTimeMillis()-exitTime)>2000){
                Toast.makeText(getApplicationContext(),"再按一次退出程序",Toast.LENGTH_SHORT).show();
                exitTime = System.currentTimeMillis();
            }else {
                finish();
                System.exit(0);
            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    /**
     * 登陆成功后，会将服务器返回的用户数据进行保存
     * 保存到SQLite数据库中
     * @param bean 自定义Gson解析json数据类型
     */
    public void saveUser(JsonBean bean){
        Log.i(TAG,"保存用户数据");
        User u = new User();
        u.setId(1);
        u.setUserAccount(bean.getUser().getUserAccount());
        u.setUsername(bean.getUser().getUsername());
        u.setUserIcon(bean.getUser().getUserIcon());
        u.setUserIntroduce(bean.getUser().getUserIntroduce());
        u.setUserFansNumber(bean.getUser().getUserFansNumbers());
        u.setUserFocusNumbers(bean.getUser().getUserFocusNumbers());
        /***
         * DBUserManager 为SQLite的管理类
         * 通过调用addUser方法，保存当前登录用户数据
         */
        DBUserManager manager = new DBUserManager(this);
        boolean b =  manager.addUser(u);
        if (b){
            for (int i =0;i<5;i++){
                Log.i(TAG,"保存用户数据成功");
                Log.i(TAG,"查询："+manager.query());
            }
        }else {
            for (int i =0;i<5;i++){
                Log.i(TAG,"失败了");
            }
        }
    }


    class LoginAsyncTask extends AsyncTask<String,Void ,String> {
        private String account;
        private String pwd;

        public LoginAsyncTask(String account,String pwd){
            this.account = account;
            this.pwd = pwd;
        }

        @Override
        protected void onPreExecute() {
            super.onPreExecute();
            mView.show(getSupportFragmentManager(),"登陆中");
        }

        /**
         * 处理网络通讯的耗时操作的方法
         * @param strings
         * @return
         */
        @Override
        protected String doInBackground(String... strings) {
            String s ="";
            try {
                //通过OkHttp3的辅助工具类的login将用户信息提交到服务器进行数据校验
                s = OkHttpManager.login(strings[0],account,pwd);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i(TAG,"登陆用户信息："+s);
            //对服务器返回的数据信息进行解析
            Gson g = new Gson();
            Type type = new TypeToken<JsonBean>(){}.getType();
            bean = g.fromJson(s,type);
            Log.i(TAG,"解析用户信息："+bean.getMessageInfo().toString());
            Log.i(TAG,"解析用户信息："+bean.getUser().getUserAccount().toString());
            if (bean!=null){
                saveUser(bean);//保存登陆用户的数据信息到SQLite数据库中
            }
            return s;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            if ("用户名不存在".equals(bean.getMessageInfo())){
                Toast.makeText(LoginActivity.this,"账号密码错误",Toast.LENGTH_SHORT).show();

                return;
            }else {
                //通过线程的方式来执行该方法
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        try {
                            Thread.sleep(2000);
                            mView.dismiss();
                            Thread.sleep(200);
                            //通过意图的方式来实现Activity之间的跳转，这里跳转到HomeActivity
                            Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                            LoginActivity.this.startActivity(intent);
                            //使用Activity跳转动画
                            overridePendingTransition(R.anim.activity_in_from_right,R.anim.activity_out_to_left);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                    }
                }).start();
            }
        }
    }
}
