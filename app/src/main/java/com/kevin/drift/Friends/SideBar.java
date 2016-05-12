package com.kevin.drift.Friends;

import android.content.Context;
import android.graphics.Canvas;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.graphics.drawable.ColorDrawable;
import android.util.AttributeSet;
import android.util.Log;
import android.view.MotionEvent;
import android.view.View;
import android.widget.TextView;

/**
 * Created by Benson_Tom on 2016/4/3.
 * SideBar 是ListView右侧的索引View，通过setTextView（）的方法来
 * 设置当前按下字母的Dialog
 * 通过setOnTouchingLetterChangeListener方法来设置回调接口，
 * 通过onTounchingLetterChanged(String s) 来处理不同操作
 */
public class SideBar extends View{
    private static final  String TAG = "SideBar";
    private static final int CUT_HEIGHT=200;

    public static String[] letter = { "A", "B", "C", "D", "E", "F", "G", "H", "I",
            "J", "K", "L", "M", "N", "O", "P", "Q", "R", "S", "T", "U", "V",
            "W", "X", "Y", "Z", "#"};

    private int chooseLetter = -1;

    private TextView mTextDialog;//当点击某个字母时，显示在屏幕中

    private Paint mPaint = new Paint();

    private OnTouchingLetterChangedListener onTouchingLetterChangedListener;

    public SideBar(Context context) {
        super(context);
    }

    public SideBar(Context context, AttributeSet attrs) {
        super(context, attrs);
    }

    public SideBar(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
    }

    /**
     *暴露出为SideBar显示字母的View的方法
     * @param showLetterView
     */
    public void setText(TextView showLetterView)
    {
        mTextDialog = showLetterView;
    }
    /**
     * 重写onDraw
     * @param canvas
     */
    @Override
    protected void onDraw(Canvas canvas) {
        super.onDraw(canvas);
        /**
         * 获取高度和宽度
         */
        final int height = getMeasuredHeight()-CUT_HEIGHT;


        Log.i(TAG,"height:"+height);
        final int width  = getWidth();
        Log.i(TAG,"width:"+height);
        /**
         * 右侧索引条单个字母的高度
         */
        int singleHeight = height/letter.length;

        //将letter中的每个字母绘制出来
        for (int i =0 ; i < letter.length ; i++ )
        {
            mPaint.setColor(Color.parseColor("#000000"));
//            mPaint.setColor(Color.rgb(33,65,98));
            mPaint.setTypeface(Typeface.DEFAULT);
            mPaint.setAntiAlias(true);
            mPaint.setTextSize(30);
            //如果该字母为选中字母
            if (i == chooseLetter)
            {
                mPaint.setColor(Color.parseColor("#1DE9B6"));
                mPaint.setFakeBoldText(true);
            }
            float xPos = width/2 - mPaint.measureText(letter[i])/2;
            //每绘制一次就往下加多一个字母位置
            float yPos = singleHeight*i + singleHeight;
            //绘制Letters
            canvas.drawText(letter[i],xPos,yPos,mPaint);
            mPaint.reset();//重置画笔
        }


    }

    @Override
    public boolean dispatchTouchEvent(MotionEvent event) {
        final int action = event.getAction();
        final float y = event.getY();
        final int oldChooseLetter = chooseLetter;
        final OnTouchingLetterChangedListener listener = onTouchingLetterChangedListener;
        //点击了某个位置y，点击y坐标所占总高度的比例*letter数组的长度就等于点击letter中的第几个.
        final int c = (int) (y/(getHeight()-CUT_HEIGHT) * letter.length);

        switch (action){
            case  MotionEvent.ACTION_UP:
             //  setBackgroundDrawable(new ColorDrawable(0x00000000)); deprecated
                setBackground(new ColorDrawable(0x00000000));
                chooseLetter = -1;
                invalidate();//刷新当前的View
                if (mTextDialog != null)
                {
                    mTextDialog.setVisibility(View.INVISIBLE);
                }
                break;
            default:
                //设置右侧SideView的背景颜色
                setBackgroundColor(Color.parseColor("#00000000"));
                if (oldChooseLetter != c)
                {
                    if (c >= 0 && c < letter.length)
                    {
                        if (listener != null)
                        {
                            listener.onTouchingLetterChanged(letter[c]);
                        }
                        if (mTextDialog != null)
                        {
                            mTextDialog.setText(letter[c]);
                            mTextDialog.setVisibility(View.VISIBLE);
                        }
                        chooseLetter = c ;
                        invalidate();
                    }
                }
                break;
        }

        return true;
    }

    /**
     * 向外暴露的方法
     * @param onTouchingLetterChangedListener
     */
    public void setOnTouchingLetterChangedListener(OnTouchingLetterChangedListener onTouchingLetterChangedListener){
        this.onTouchingLetterChangedListener = onTouchingLetterChangedListener;
    }


    public interface OnTouchingLetterChangedListener{
        public void onTouchingLetterChanged(String s);
    }

}
