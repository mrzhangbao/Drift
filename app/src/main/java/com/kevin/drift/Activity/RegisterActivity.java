package com.kevin.drift.Activity;

import android.content.Intent;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kevin.drift.Entity.User;
import com.kevin.drift.LoginActivity;
import com.kevin.drift.R;
import com.kevin.drift.Utils.AESUtil;
import com.kevin.drift.Utils.OkHttpManager;
import com.kevin.drift.Utils.RegexValidateUtil;
import com.kevin.drift.Utils.URLManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * Created by Benson_Tom on 2016/5/11.
 * 注册界面的Activity
 * 实现了控件点击监听的接口
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String TAG ="RegisterActivity";
    private EditText mUsername; //用户名的控件
    private EditText mUserAccount;//用户账号的控件
    private EditText mPassword;//获取用户密码的控件
    private Button mRegister;//注册按钮
    private ImageButton mBack;//返回按钮

    private String username;
    private String password;
    private String phone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        //设定当前Activity内容布局文件
        setContentView(R.layout.register_activity);
        initWidget();
    }

    /**
     * 实例化控件
     */
    private void initWidget() {
        //控件的查找，根据控件Id 绑定控件
        mBack = (ImageButton) this.findViewById(R.id.register_back);
        mUsername = (EditText) this.findViewById(R.id.register_username);
        mUserAccount = (EditText) this.findViewById(R.id.register_phone);
        mPassword = (EditText) this.findViewById(R.id.register_password);
        mRegister = (Button) this.findViewById(R.id.register_bt);

        //返回，注册按钮的监听事件的注册
        mBack.setOnClickListener(this);
        mRegister.setOnClickListener(this);



    }

    /**
     * 实现了控件点击监听接口
     * 重写点击事件处理方法
     * @param view
     */
    @Override
    public void onClick(View view) {
        //根据控件Id来处理点击事件
        switch (view.getId()){
            case R.id.register_back:
                //使用意图的方式，跳转到登陆的Activity
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                RegisterActivity.this.startActivity(intent);
                RegisterActivity.this.finish();//结束当前的Activity
                //Activity的跳转动画
                overridePendingTransition(R.anim.back_in_to_left,R.anim.back_out_to_right);
                break;
            case R.id.register_bt:
                Log.i(TAG,"点击了注册");
                //获取各个控件的输入数据
                username = mUsername.getText().toString();
                password = mPassword.getText().toString();
                phone = mUserAccount.getText().toString();
                //判断username数据是否为空，若为空，则显示错误信息
                if (TextUtils.isEmpty(username)){
                    mUsername.setError("Username isEmpty");
                    return;
                }
                //判断账号数据是否为空，若为空，则显示错误信息
                if (TextUtils.isEmpty(phone)){
                    mUserAccount.setError("Phone isEmpty");
                    return;
                }
                //判断手机号码格式是否正确，若不正确，则显示错误信息
                if (!RegexValidateUtil.isMobileNO(phone)){
                    mUserAccount.setError("Format error");
                    return;
                }
                //判断密码数据是否为空，若为空，则显示错误信息
                if (TextUtils.isEmpty(password)){
                    mPassword.setError("Password isEmpty");
                    return;
                }
                //用户注册信息设置
                User u = new User();
                u.setUserAccount(phone);
                u.setUsername(username);
                u.setPassword(AESUtil.encrypt(AESUtil.KEY,password));//使用了AES的加密算法进行密码的加密
               //使用Json的数据格式上传数据到服务器
                Gson g = new Gson();
                Map<String,Object> map = new HashMap<>();
                map.put("user",u);
                new RegisterAsyncTask(u,g.toJson(map)).execute(URLManager.USER_REGISTER);
//                new RegisterAsyncTask(new Gson().toJson(list)).execute(URL);
                break;
            default:
                break;
        }

    }

    /**
     * 轻量级的异步任务
     */
    class RegisterAsyncTask extends AsyncTask<String,Void,String>{
        private User u;
        private String json;

        //构造方法
        public RegisterAsyncTask(User u,String json){
            this.u=u;
            this.json = json;
            Log.i(TAG,"注册的Json:"+json);

        }

        /**
         * 耗时操作必须在这个方法里面实现，防止线程阻塞，抛出异常
         * @param strings
         * @return
         */
        @Override
        protected String doInBackground(String... strings) {
            String s ="";
            try {
                //通过OkHttpManager的register方法提交数据
                s= OkHttpManager.register(strings[0],u);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i(TAG,"注册信息："+s);
            return s;
        }

        /**
         * 执行耗时操作后会执行该方法，即处理注册后的逻辑事件
         * @param s
         */
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            //使用意图的方式将Activity条状到登陆界面
            Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
            RegisterActivity.this.startActivity(intent);
            //显示登陆成功弹窗信息
            Toast.makeText(RegisterActivity.this,"注册成功",Toast.LENGTH_SHORT).show();
            //结束当前Activity
            RegisterActivity.this.finish();
            //使用Activity过度动画
            overridePendingTransition(R.anim.activity_in_from_right,R.anim.activity_out_to_left);


        }
    }

}
