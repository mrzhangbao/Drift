package com.kevin.drift;

import android.app.Activity;
import android.content.Intent;
import android.os.Bundle;
import android.os.Handler;
import android.view.Window;

/**
 * Created by Benson_Tom on 2016/4/22.
 * 软件加载图片
 */
public class SplashActivity extends Activity{

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        requestWindowFeature(Window.FEATURE_NO_TITLE);
        setContentView(R.layout.splash);
        Handler handler = new Handler();
        handler.postDelayed(new splashHandler(),3000);
    }

    class splashHandler implements Runnable{

        @Override
        public void run() {
            startActivity(new Intent(getApplication(),LoginActivity.class));
            SplashActivity.this.finish();
        }
    }
}
