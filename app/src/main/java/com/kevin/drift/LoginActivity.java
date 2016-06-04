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
import com.kevin.drift.Utils.CustomDialog.KevinLoadingView;
import com.kevin.drift.Utils.DBUtils.DBUserManager;
import com.kevin.drift.Utils.JsonBean;
import com.kevin.drift.Utils.URLManager;
import com.kevin.drift.Utils.OkHttpManager;
import com.kevin.drift.Utils.ToRoundBitmap;

import java.io.IOException;
import java.lang.reflect.Type;


public class LoginActivity extends AppCompatActivity {
    private static final String TAG ="LoginActivity";
    private long exitTime = 0;
    private Button mLoginButton;
    private Button mRegister;
    private EditText mAccountEt;
    private EditText mPasswordEt;
    private ImageView mUserIconImg;
    private String userAccount;
    private String password;
    private KevinLoadingView mView;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        //初始化布局里面的控件
        mView = new KevinLoadingView();
        initWidget();
        initEvent();


    }


    private void initWidget() {
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
        mLoginButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {




                saveUser();
                checkUser();

            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(intent);
                //使用Activity过度动画
                overridePendingTransition(R.anim.activity_in_from_right,R.anim.activity_out_to_left);
            }
        });

    }

    private void checkUser() {
        userAccount = mAccountEt.getText().toString();
        password = mPasswordEt.getText().toString();
        if (isEmpty(userAccount)){
            mAccountEt.setError("账号不能为空");
            return;
        }
        if (isEmpty(password)){
            mPasswordEt.setError("密码不能为空");
            return;
        }
        new LoginAsyncTask(userAccount,password).execute(URLManager.USER_LOGIN);

    }
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

    public void saveUser(){
        Log.i(TAG,"保存用户数据");
        User u = new User();
        u.setId(1);
        u.setUserAccount("1578888");
        u.setUsername("kevin");
        u.setUserIcon("http.jpg");
        u.setUserIntroduce("猪");
        u.setUserFansNumber("15");
        u.setUserFocusNumbers("32");
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

        @Override
        protected String doInBackground(String... strings) {
            String s ="";
            try {
                s = OkHttpManager.login(strings[0],account,pwd);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i(TAG,"登陆用户信息："+s);
            Gson g = new Gson();
            Type type = new TypeToken<JsonBean>(){}.getType();
            JsonBean bean = g.fromJson(s,type);
            Log.i(TAG,"解析用户信息："+bean.user);
            return s;
        }


        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            new Thread(new Runnable() {
                @Override
                public void run() {
                    try {
                        Thread.sleep(2000);
                        mView.dismiss();
                        Thread.sleep(200);
                        Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                        LoginActivity.this.startActivity(intent);
                        //使用Activity过度动画
                        overridePendingTransition(R.anim.activity_in_from_right,R.anim.activity_out_to_left);
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                }
            }).start();

        }
    }
}
