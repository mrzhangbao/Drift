package com.kevin.drift.Friends;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.support.v4.content.ContextCompat;
import android.text.Editable;
import android.text.TextWatcher;
import android.util.AttributeSet;
import android.view.MotionEvent;
import android.view.View;
import android.view.View.OnFocusChangeListener;
import android.view.animation.Animation;
import android.view.animation.CycleInterpolator;
import android.view.animation.TranslateAnimation;
import android.widget.EditText;

import com.kevin.drift.R;


public class ClearEditText extends EditText implements TextWatcher, OnFocusChangeListener {

    private Drawable mClearDrawable;//删除按钮的引用
    private boolean hasFocus;//判断控件是否有焦点
    private Context mContext;

    public ClearEditText(Context context) {
        this(context, null);
        mContext = context;
    }

    public ClearEditText(Context context, AttributeSet attrs) {
        this(context, attrs, R.attr.editTextStyle);
        mContext = context;
    }

    public ClearEditText(Context context, AttributeSet attrs, int defStyleAttr) {
        super(context, attrs, defStyleAttr);
        mContext = context;
        init();
    }

    private void init() {
        //获取EditText的DrawableRight,假如没有我们就使用默认图片
        mClearDrawable = getCompoundDrawables()[2];
        if (mClearDrawable == null) {
            //mClearDrawable = getResources().getDrawable(R.mipmap.ic_clear_search_api_holo_light);该方法过时
            mClearDrawable = ContextCompat.getDrawable(mContext, R.drawable.search_bar_edit_pressed);
        }

        mClearDrawable.setBounds(0, 0, mClearDrawable.getIntrinsicWidth(), mClearDrawable.getIntrinsicHeight());
        setClearIconVisible(false);//默认设置ClearIcon隐藏状态
        setOnFocusChangeListener(this);//设置焦点改变的监听
        addTextChangedListener(this);//设置输入框内容发生改变的监听
    }

    /**
     * 因为不能直接给EditText设置点击事件，所以我们用记住我们按下的位置
     * 来去模拟点击事件，当我们按下的位置
     * EditText的宽度-图标到控件右边的间距-图标宽度
     * EditText的宽度-图标到控件右边的间距之间就算点击了图标，
     * 竖直方向不考虑
     *
     */
    @Override
    public boolean onTouchEvent(MotionEvent event) {
        if (getCompoundDrawables()[2] !=null){
            if (event.getAction() == MotionEvent.ACTION_UP) {
                boolean touchable = event.getX() > (getWidth() - getPaddingRight()-mClearDrawable.getIntrinsicWidth())
                        && (event.getX() < ((getWidth() - getPaddingRight())));
                if (touchable){
                    this.setText("");
                }
            }
        }
        return super.onTouchEvent(event);
    }

    /**
     * 当ClearEditText焦点发生变化的时候，判断里面的字符长度设置ClearIcon的显示和隐藏
     * @param view
     * @param b
     */
    @Override
    public void onFocusChange(View view, boolean b) {
        hasFocus = b;
        if (hasFocus){
            setClearIconVisible(getText().length() > 0);
        }else{
            setClearIconVisible(false);
        }
    }

    /**
     * 当输入框中的内容发生变化的时候回调的方法
     */
    @Override
    public void onTextChanged(CharSequence text, int start, int lengthBefore, int lengthAfter) {
        if (hasFocus){
            setClearIconVisible(text.length() > 0);
        }
    }

    @Override
    public void beforeTextChanged(CharSequence charSequence, int i, int i1, int i2) {

    }

    @Override
    public void afterTextChanged(Editable editable) {

    }


    /**
     * 设置清除图标的显示和隐藏
     * 通过setCompoundDrawables()将图标绘制到EditText上
     */
    public void setClearIconVisible(boolean visible) {
        Drawable right = visible ? mClearDrawable : null;
        setCompoundDrawables(getCompoundDrawables()[0], getCompoundDrawables()[1], right, getCompoundDrawables()[3]);
    }

    /**
     * 设置晃动动画
     */
    public void setShakeAnimation(){
        this.setAnimation(shakeAnimation(5));
    }

    /**
     *
     * @param counts 为一秒晃动的次数
     * @return
     */
    public static Animation shakeAnimation(int counts){
        Animation animation = new TranslateAnimation(0,10,0,0);
        animation.setInterpolator(new CycleInterpolator(counts));
        animation.setDuration(1000);
        return animation;
    }
}
