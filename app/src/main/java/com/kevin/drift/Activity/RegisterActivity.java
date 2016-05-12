package com.kevin.drift.Activity;

import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.widget.Button;
import android.widget.EditText;
import android.widget.ImageButton;

import com.kevin.drift.R;

/**
 * Created by Benson_Tom on 2016/5/11.
 */
public class RegisterActivity extends AppCompatActivity{
    private EditText mUsername;
    private EditText mPhone;
    private EditText mPassword;
    private Button mRegister;
    private ImageButton mBack;

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
        mRegister = (Button) this.findViewById(R.id.id_register_bt);
    }
}
