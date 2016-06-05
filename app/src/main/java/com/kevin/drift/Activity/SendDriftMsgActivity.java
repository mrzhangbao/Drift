package com.kevin.drift.Activity;

import android.content.Context;
import android.content.Intent;
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
import com.kevin.drift.LoginActivity;
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
 * 发送消息的Activity
 */
public class SendDriftMsgActivity extends AppCompatActivity implements View.OnClickListener {
    private static final String TAG = "SendDriftMsgActivity";
    //控件定义
    private EditText mMsgContent;
    private ImageView mMsgImage;
    private TextView mChangeImg;
    private TextView mSend;
    private ImageButton mBack;
    private final Context context = this;
    private String mImgUrl="";
    private DriftMessageInfo d;
    //获取随机数对象
    private  Random r = new Random();

    @Override
    protected void onCreate(@Nullable Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        setContentView(R.layout.activity_send_driftmessage);
        initWidget();//实例化控件


    }

    /**
     * 实例化控件和处理逻辑事件
     */
    private void initWidget() {
        //控件的查找
        mMsgContent = (EditText) this.findViewById(R.id.sendDrift_msgContent);
        mMsgImage = (ImageView) this.findViewById(R.id.sendDrift_msgImage);
        mChangeImg = (TextView) this.findViewById(R.id.sendDrift_msgChangeImg);
        mSend = (TextView) this.findViewById(R.id.sendDrift_send);
        mBack = (ImageButton) this.findViewById(R.id.sendDrift_back);

        //控件监听的注册
        mChangeImg.setOnClickListener(this);
        mSend.setOnClickListener(this);
        mBack.setOnClickListener(this);

        //通过Picasso图片加载框架进行图片的异步加载和显示
        Picasso.with(this).load(RandomMessage.getRandomImageUrl(r.nextInt(12)+1)).into(mMsgImage);

    }


    /**
     * 重写监听事件的点击方法
     * @param view
     */
    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.sendDrift_msgChangeImg:
                //改变图片，使用Picasso图片加载框架
                Picasso.with(context).load(RandomMessage.getRandomImageUrl(r.nextInt(12)+1)).into(mMsgImage);
                break;
            case R.id.sendDrift_back:
                //返回之前界面
                finish();
//                overridePendingTransition(R.anim.activity_out_to_left,R.anim.activity_in_from_right);
                break;
            case R.id.sendDrift_send:
                Toast.makeText(this,"内容："+mMsgContent.getText().toString(),Toast.LENGTH_SHORT).show();
                Log.i(TAG, "链接："+mImgUrl);
                show();
                Intent intent = new Intent(SendDriftMsgActivity.this, HomeActivity.class);
                SendDriftMsgActivity.this.startActivity(intent);
                //显示成功弹窗信息
                Toast.makeText(SendDriftMsgActivity.this,"已发送",Toast.LENGTH_SHORT).show();
                //结束当前Activity
                SendDriftMsgActivity.this.finish();
                //使用Activity过度动画
                overridePendingTransition(R.anim.zoomin,R.anim.zoomout);
                break;
            default:
                break;
        }
    }

    /**
     * 用于发送当前写好的消息
     * @return
     */
    private DriftMessageInfo show(){
        String content = mMsgContent.getText().toString();
        d = new DriftMessageInfo();
        //非空判断
        if ("".equals(content)){
            return null;
        }
        if ("".equals(mImgUrl)){
            d.setDriftImg(RandomMessage.getRandomImageUrl(r.nextInt(12)+1));
        }else {
            d.setDriftImg(mImgUrl);
        }

        d.setDriftContent(content);
        //通过SQLite查找当前登录的用户，并获得用户的数据
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
        /**
         * 通过Json的数据格式将数据上传到服务器，这里使用了Gson封装数据
         * 通过异步任务来处理网络数据交互的耗时操作
         */
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
                //通过OkHttpManager的Post方法提交数据
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
