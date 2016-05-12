package com.kevin.drift;

import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;

import android.view.KeyEvent;
import android.view.View;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageView;
import android.widget.Toast;

import com.kevin.drift.Activity.HomeActivity;
import com.kevin.drift.Activity.RegisterActivity;
import com.kevin.drift.Utils.ToRoundBitmap;


public class LoginActivity extends AppCompatActivity {
    private long exitTime = 0;
    private Button mLoginButton;
    private Button mRegister;
    private EditText mAccountEt;
    private EditText mPasswordEt;
    private ImageView mUserIconImg;



    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.login_activity);
        //初始化布局里面的控件
        initWidget();
        initEvent();

    }


    private void initWidget() {
        mUserIconImg = (ImageView) this.findViewById(R.id.id_userIcon_imgv);
        mLoginButton = (Button) this.findViewById(R.id.id_login_bt);
        mRegister = (Button) this.findViewById(R.id.id_register_bt);
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
                Intent intent = new Intent(LoginActivity.this, HomeActivity.class);
                LoginActivity.this.startActivity(intent);
            }
        });

        mRegister.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                Intent intent = new Intent(LoginActivity.this, RegisterActivity.class);
                LoginActivity.this.startActivity(intent);
            }
        });

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
}
