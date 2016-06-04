package com.kevin.drift.Activity;

import android.content.Context;
import android.os.AsyncTask;
import android.os.Bundle;
import android.support.annotation.Nullable;
import android.support.v7.app.AppCompatActivity;
import android.util.Log;
import android.view.View;
import android.widget.EditText;
import android.widget.ImageButton;
import android.widget.ImageView;
import android.widget.TextView;
import android.widget.Toast;

import com.google.gson.Gson;
import com.kevin.drift.Entity.DriftMessageInfo;
import com.kevin.drift.Entity.User;
import com.kevin.drift.R;
import com.kevin.drift.Utils.DBUtils.DBUserManager;
import com.kevin.drift.Utils.GetCurrentTime;
import com.kevin.drift.Utils.OkHttpManager;
import com.kevin.drift.Utils.RandomImageUrl;
import com.kevin.drift.Utils.RandomMessage;
import com.kevin.drift.Utils.URLManager;
import com.squareup.picasso.Picasso;

import java.io.IOException;
import java.util.Random;

/**
 * Created by Benson_Tom on 2016/5/12.
 */
public class SendDriftMsgActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "SendDriftMsgActivity";
    private static final String URL = "";
    private EditText mMsgContent;
    private ImageView mMsgImage;
    private TextView mChangeImg;
    private TextView mSend;
    private ImageButton mBack;
    private final Context context = this;
    private String mImgUrl="";
    private DriftMessageInfo d;

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_driftmessage);
        initWidget();


    }

    private void initWidget() {
        mMsgContent = (EditText) this.findViewById(R.id.sendDrift_msgContent);
        mMsgImage = (ImageView) this.findViewById(R.id.sendDrift_msgImage);
        mChangeImg = (TextView) this.findViewById(R.id.sendDrift_msgChangeImg);
        mSend = (TextView) this.findViewById(R.id.sendDrift_send);
        mBack = (ImageButton) this.findViewById(R.id.sendDrift_back);

        mChangeImg.setOnClickListener(this);
        mSend.setOnClickListener(this);
        mBack.setOnClickListener(this);
        Random r = new Random();
        Picasso.with(this).load(RandomMessage.getRandomImageUrl(r.nextInt(12)+1)).into(mMsgImage);

    }

    private String getImageUrl() {
        Random r = new Random();
        if ((r.nextInt(5) + 1) == 1) {
            Log.i(TAG, "a");
            mImgUrl = RandomImageUrl.a;
            return mImgUrl;
        }
        if ((r.nextInt(5) + 1) == 2) {
            Log.i(TAG, "b");
            mImgUrl = RandomImageUrl.b;
            return mImgUrl;
        }
        if ((r.nextInt(5) + 1) == 3) {
            Log.i(TAG, "c");
            mImgUrl = RandomImageUrl.c;
            return mImgUrl;
        }
        if ((r.nextInt(5) + 1) == 4) {
            Log.i(TAG, "d");
            mImgUrl = RandomImageUrl.d;
            return mImgUrl;
        }
        if ((r.nextInt(5) + 1) == 5) {
            Log.i(TAG, "e");
            mImgUrl = RandomImageUrl.e;
            return mImgUrl;
        }
        if (mImgUrl==null){
            mImgUrl = RandomImageUrl.a;
        }

        return mImgUrl;
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sendDrift_msgChangeImg:
                Picasso.with(context).load(getImageUrl()).into(mMsgImage);
                break;
            case R.id.sendDrift_back:
                finish();
//                overridePendingTransition(R.anim.activity_out_to_left,R.anim.activity_in_from_right);
                break;
            case R.id.sendDrift_send:
                Toast.makeText(this,"内容："+mMsgContent.getText().toString(),Toast.LENGTH_SHORT).show();
                Log.i(TAG, "链接："+mImgUrl);
                show();
                break;
            default:
                break;
        }
    }

    private DriftMessageInfo show(){
        String content = mMsgContent.getText().toString();
        d = new DriftMessageInfo();
        if ("".equals(content)){
            return null;
        }
        if ("".equals(mImgUrl)){
            d.setDriftImg(getImageUrl());
        }else {
            d.setDriftImg(mImgUrl);
        }

        d.setDriftContent(content);
        DBUserManager manager = new DBUserManager(this);
        User u = manager.query();
        d.setDriftTime(GetCurrentTime.time());
        d.setUserID(""+u.getId());
        d.setDriftAddress("北京");
        for (int i=0;i<5;i++){
            Log.i(TAG,"Drift:"+d.toString());
            Log.i(TAG,"User:"+u.toString());
        }
        Gson g = new Gson();
        new SendAsyncTask(g.toJson(d)).execute(URLManager.SEND_MESSAGE);
        return d;
    }

    class SendAsyncTask extends AsyncTask<String,Void ,String>{
        private String json;

        public SendAsyncTask(String json){
            this.json=json;
        }

        @Override
        protected String doInBackground(String... strings) {

            String s = "";
            try {
                s = OkHttpManager.Post(strings[0],json);

            } catch (IOException e) {
                e.printStackTrace();
            }
            Log.i(TAG,"发送数据："+d);
            Log.i(TAG,"返回状态："+s);
            return s;
        }
        @Override
        protected void onPostExecute(String s) {
            super.onPostExecute(s);
        }
    }
}
