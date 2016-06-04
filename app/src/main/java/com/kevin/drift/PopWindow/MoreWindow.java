package com.kevin.drift.PopWindow;

import android.animation.Animator;
import android.animation.ObjectAnimator;
import android.animation.ValueAnimator;
import android.app.Activity;
import android.content.Intent;
import android.graphics.Bitmap;
import android.graphics.Canvas;
import android.graphics.Paint;
import android.graphics.Rect;
import android.graphics.drawable.BitmapDrawable;
import android.os.Handler;
import android.util.DisplayMetrics;
import android.util.Log;
import android.view.Gravity;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.Window;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.TranslateAnimation;
import android.widget.ImageButton;
import android.widget.LinearLayout;
import android.widget.PopupWindow;

import com.kevin.drift.Activity.SendDriftMsgActivity;
import com.kevin.drift.R;
import com.zhy.android.percent.support.PercentRelativeLayout;

/**
 * Created by Benson_Tom on 2016/4/30.
 */
public class MoreWindow extends PopupWindow implements View.OnClickListener {

    private String TAG = MoreWindow.class.getSimpleName();
    Activity mContext;
    private int mWidth;
    private int mHeight;
    private int statusBarHeight;
    private Bitmap mBitmap = null;
    private Bitmap overlay = null;

    private Handler mHandler = new Handler();

    public MoreWindow(Activity context) {
        mContext = context;
    }

    public void init() {
        Rect frame = new Rect();
        mContext.getWindow().getDecorView().getWindowVisibleDisplayFrame(frame);
        statusBarHeight = frame.top;
        DisplayMetrics metrics = new DisplayMetrics();
        mContext.getWindowManager().getDefaultDisplay()
                .getMetrics(metrics);
        mWidth = metrics.widthPixels;
        mHeight = metrics.heightPixels;

        setWidth(mWidth);
        setHeight(mHeight);
    }

    private Bitmap blur() {
        if (null != overlay) {
            return overlay;
        }
        long startMs = System.currentTimeMillis();

        View view = mContext.getWindow().findViewById(Window.ID_ANDROID_CONTENT);
//        View view = mContext.getWindow().getDecorView();
        Log.i(TAG, "height:" + view.getHeight());
        Log.i(TAG, "width:" + view.getWidth());
        view.setDrawingCacheEnabled(true);
        view.buildDrawingCache(true);
        mBitmap = view.getDrawingCache();

        float scaleFactor = 8;//图片缩放比例；
        float radius = 10;//模糊程度
        int width = mBitmap.getWidth();
        int height = mBitmap.getHeight();

        overlay = Bitmap.createBitmap((int) (width / scaleFactor), (int) (height / scaleFactor), Bitmap.Config.ARGB_8888);
        Canvas canvas = new Canvas(overlay);
        canvas.scale(1 / scaleFactor, 1 / scaleFactor);
        Paint paint = new Paint();
        paint.setFlags(Paint.FILTER_BITMAP_FLAG);
        canvas.drawBitmap(mBitmap, 0, 0, paint);

        overlay = FastBlur.doBlur(overlay, (int) radius, true);
        Log.i(TAG, "blur time is:" + (System.currentTimeMillis() - startMs));
        return overlay;
    }

    @SuppressWarnings("unused")
    private Animation showAnimation1(final View view, int fromY, int toY) {
        AnimationSet set = new AnimationSet(true);
        TranslateAnimation go = new TranslateAnimation(0, 0, fromY, toY);
        go.setDuration(300);
        TranslateAnimation go1 = new TranslateAnimation(0, 0, -10, 2);
        go1.setDuration(100);
        go1.setStartOffset(250);
        set.addAnimation(go1);
        set.addAnimation(go);

        set.setAnimationListener(new Animation.AnimationListener() {

            @Override
            public void onAnimationEnd(Animation animation) {
            }

            @Override
            public void onAnimationRepeat(Animation animation) {

            }

            @Override
            public void onAnimationStart(Animation animation) {

            }

        });
        return set;
    }


    public void showMoreWindow(View anchor, int bottomMargin) {
        final PercentRelativeLayout layout = (PercentRelativeLayout) LayoutInflater.from(mContext).inflate(R.layout.center_menu_all, null);
        setContentView(layout);

        final LinearLayout closeLayout = (LinearLayout) layout.findViewById(R.id.window_menu_close_layout);
        ImageButton close = (ImageButton) closeLayout.findViewById(R.id.window_menu_close_img);
//        PercentRelativeLayout.LayoutParams params =new PercentRelativeLayout.LayoutParams(ViewGroup.LayoutParams.WRAP_CONTENT, ViewGroup.LayoutParams.WRAP_CONTENT);
//        params.bottomMargin = bottomMargin;
//        params.addRule(PercentRelativeLayout.BELOW, R.id.window_menu_comment);
//        params.addRule(PercentRelativeLayout.RIGHT_OF, R.id.window_menu_edit);
////        params.topMargin = 300;
////        params.leftMargin = 180;
//        close.setLayoutParams(params);

        close.setOnClickListener(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                if (isShowing()) {
                    closeAnimation(layout);

                }
            }

        });

        showAnimation(layout);
        setBackgroundDrawable(new BitmapDrawable(mContext.getResources(), blur()));
        setOutsideTouchable(true);
        setFocusable(true);
        showAtLocation(anchor, Gravity.BOTTOM, 0, statusBarHeight);
    }

    private void showAnimation(ViewGroup layout) {
        layout.findViewById(R.id.window_menu_friend).setOnClickListener(this);
        layout.findViewById(R.id.window_menu_camera).setOnClickListener(this);
        layout.findViewById(R.id.window_menu_music).setOnClickListener(this);
        layout.findViewById(R.id.window_menu_edit).setOnClickListener(this);
        layout.findViewById(R.id.window_menu_title).setOnClickListener(this);
        layout.findViewById(R.id.window_menu_photo).setOnClickListener(this);
        layout.findViewById(R.id.window_menu_video).setOnClickListener(this);
        layout.findViewById(R.id.window_menu_redpacket).setOnClickListener(this);
        layout.findViewById(R.id.window_menu_wallet).setOnClickListener(this);
        for (int i = 0; i < layout.getChildCount(); i++) {
            final View child = layout.getChildAt(i);
            if (child.getId() == R.id.window_menu_close_img) {
                continue;
            }
            child.setOnClickListener(this);
            child.setVisibility(View.INVISIBLE);
            mHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    child.setVisibility(View.VISIBLE);
                    ValueAnimator fadeAnim = ObjectAnimator.ofFloat(child, "translationY", 600, 0);
                    //设置Window弹出动画过程持续时间
                    fadeAnim.setDuration(1500);
                    ClickBackAnimator kickAnimator = new ClickBackAnimator();
                    kickAnimator.setDuration(150);
                    fadeAnim.setEvaluator(kickAnimator);
                    fadeAnim.start();
                }
            }, i * 50);
        }

    }

    private void closeAnimation(ViewGroup layout) {
        for (int i = 0; i < layout.getChildCount(); i++) {
            final View child = layout.getChildAt(i);
            if (child.getId() == R.id.window_menu_close_img) {
                continue;
            }
            child.setOnClickListener(this);
            mHandler.postDelayed(new Runnable() {

                @Override
                public void run() {
                    child.setVisibility(View.VISIBLE);
                    ValueAnimator fadeAnim = ObjectAnimator.ofFloat(child, "translationY", 0, 600);
                    //设置退出动画持续时间
                    fadeAnim.setDuration(100);
                    ClickBackAnimator kickAnimator = new ClickBackAnimator();
                    kickAnimator.setDuration(100);
                    fadeAnim.setEvaluator(kickAnimator);
                    fadeAnim.start();
                    fadeAnim.addListener(new Animator.AnimatorListener() {

                        @Override
                        public void onAnimationStart(Animator animation) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void onAnimationRepeat(Animator animation) {
                            // TODO Auto-generated method stub

                        }

                        @Override
                        public void onAnimationEnd(Animator animation) {
                            child.setVisibility(View.INVISIBLE);
                            dismiss();
                        }

                        @Override
                        public void onAnimationCancel(Animator animation) {
                            // TODO Auto-generated method stub

                        }
                    });
                }
            }, (layout.getChildCount() - i - 1) * 30);

            if (child.getId() == R.id.window_menu_friend) {
                mHandler.postDelayed(new Runnable() {

                    @Override
                    public void run() {
                        dismiss();
                    }
                }, (layout.getChildCount() - i) * 30 + 80);
            }
        }

    }

    @Override
    public void onClick(View v) {

        switch (v.getId()) {
            case R.id.window_menu_friend:
                Log.i(TAG, "点击了好友");
//                Intent intent = new Intent(mContext,SecondActivity.class);
//                mContext.startActivity(intent);
                dismiss();
                mContext.finish();
                break;
            case R.id.window_menu_camera:
                Log.i(TAG, "点击了相机");
                break;
            case R.id.window_menu_music:
                Log.i(TAG, "点击了音乐");
                break;
            case R.id.window_menu_edit:
                Log.i(TAG, "点击了文字");
                Intent intent = new Intent(mContext,SendDriftMsgActivity.class);
                mContext.startActivity(intent);
                break;
            case R.id.window_menu_title:
                Log.i(TAG, "点击了头条");
                break;
            case R.id.window_menu_photo:
                Log.i(TAG, "点击了照片");
                break;
            case R.id.window_menu_video:
                Log.i(TAG, "点击了视频");
                break;
            case R.id.window_menu_redpacket:
                Log.i(TAG, "点击了红包");
                break;
            case R.id.window_menu_wallet:
                Log.i(TAG, "点击了钱包");
                break;

            default:
                break;
        }
    }

    public void destroy() {
        if (null != overlay) {
            overlay.recycle();
            overlay = null;
            System.gc();
        }
        if (null != mBitmap) {
            mBitmap.recycle();
            mBitmap = null;
            System.gc();
        }
    }


}

