package com.kevin.drift.Fragment;

import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kevin.drift.R;

/**
 * Created by Benson_Tom on 2016/4/27.
 */
public class ProFileFragment extends BaseFragment implements View.OnClickListener{
    private View mPhotoAlbum;
    private View mShopping;
    private View mWallet;
    private View mEmotion;
    private View mSetting;

    private ImageView mIcon;
    private TextView mName;

    private static String[] itemsName= {"商城","钱包","表情","设置"};
    private static int[] itemsImage = {R.mipmap.profile_shopping,R.mipmap.profile_wallet,
    R.mipmap.profile_emotion,R.mipmap.profile_setting};

    @Override
    protected int setLayout() {
        return R.layout.fragment_profile;
    }

    @Override
    protected void initEvent(View view) {
        initWidget(view);
    }



    private void initWidget(View view) {
        mPhotoAlbum = view.findViewById(R.id.profile_item_photoAlbum);
        mShopping = view.findViewById(R.id.profile_item_shopping);
        mWallet = view.findViewById(R.id.profile_item_wallet);
        mEmotion = view.findViewById(R.id.profile_item_emotion);
        mSetting = view.findViewById(R.id.profile_item_setting);
        setViewResource(mShopping,0);
        setViewResource(mWallet,1);
        setViewResource(mEmotion,2);
        setViewResource(mSetting,3);
        mPhotoAlbum.setOnClickListener(this);
        mShopping.setOnClickListener(this);
        mWallet.setOnClickListener(this);
        mEmotion.setOnClickListener(this);
        mSetting.setOnClickListener(this);

    }

    private void setViewResource(View view,int i) {
        mIcon = (ImageView) view.findViewById(R.id.profile_item_icon);
        mName = (TextView) view.findViewById(R.id.profile_item_name);
        mIcon.setImageResource(itemsImage[i]);
        mName.setText(itemsName[i]);
    }

    @Override
    public void onClick(View view) {

    }
}
