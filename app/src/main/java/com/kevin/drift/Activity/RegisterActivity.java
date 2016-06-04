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

import com.google.gson.Gson;
import com.kevin.drift.Entity.RegisterEntity;
import com.kevin.drift.LoginActivity;
import com.kevin.drift.R;
import com.kevin.drift.Utils.OkHttpManager;
import com.kevin.drift.Utils.RegexValidateUtil;
import com.kevin.drift.Utils.URLManager;

import java.io.IOException;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Benson_Tom on 2016/5/11.
 */
public class RegisterActivity extends AppCompatActivity implements View.OnClickListener{
    private static final String URL="http://119.124.20.43:8080/WebServer/TEST";
    private static final String TAG ="RegisterActivity";
    private EditText mUsername;
    private EditText mPhone;
    private EditText mPassword;
    private Button mRegister;
    private ImageButton mBack;

    private String username;
    private String password;
    private String phone;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.register_activity);
        initWidget();
    }
    private void initWidget() {
        mBack = (ImageButton) this.findViewById(R.id.register_back);
        mUsername = (EditText) this.findViewById(R.id.register_username);
        mPhone = (EditText) this.findViewById(R.id.register_phone);
        mPassword = (EditText) this.findViewById(R.id.register_password);
        mRegister = (Button) this.findViewById(R.id.register_bt);


        mBack.setOnClickListener(this);
        mRegister.setOnClickListener(this);
        mUsername.setOnClickListener(this);


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()){
            case R.id.register_back:
                Intent intent = new Intent(RegisterActivity.this, LoginActivity.class);
                RegisterActivity.this.startActivity(intent);
                RegisterActivity.this.finish();
                overridePendingTransition(R.anim.back_in_to_left,R.anim.back_out_to_right);
                break;
            case R.id.register_bt:
                Log.i(TAG,"点击了注册");
                username = mUsername.getText().toString();
                password = mPassword.getText().toString();
                phone = mPhone.getText().toString();
                if (TextUtils.isEmpty(username)){
                    mUsername.setError("Username isEmpty");
                    return;
                }
                if (TextUtils.isEmpty(phone)){
                    mPhone.setError("Phone isEmpty");
                    return;
                }
                if (!RegexValidateUtil.isMobileNO(phone)){
                    mPhone.setError("Format error");
                    return;
                }
                if (TextUtils.isEmpty(password)){
                    mPassword.setError("Password isEmpty");
                    return;
                }
                RegisterEntity r = new RegisterEntity();
                r.setUsername(username);
                r.setPhone(phone);
                r.setPassword(password);

                List<RegisterEntity> list = new ArrayList<>();
                for (int i = 0; i<10;i++){
                    RegisterEntity ss  = new RegisterEntity();
                    ss.setUsername(username+":"+i);
                    ss.setPhone(phone+":"+i);
                    ss.setPassword(password+":"+i);
                    list.add(ss);
                }

                new RegisterAsyncTask(new Gson().toJson(r)).execute(URLManager.USER_REGISTER);
//                new RegisterAsyncTask(new Gson().toJson(list)).execute(URL);
                break;

            case R.id.register_username:
                Log.i("TAG","1234456789");
                break;
            default:
                break;
        }

    }

    class RegisterAsyncTask extends AsyncTask<String,Void,String>{
        private String json="";

        public RegisterAsyncTask(String json){
            this.json=json;
        }

        @Override
        protected String doInBackground(String... strings) {
            String s ="";
            try {
                s= OkHttpManager.Post(strings[0],json);
            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i(TAG,"注册信息："+s);
            return s;
        }

        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
            Log.i("TAG","注册信息"+s);
        }
    }

}
