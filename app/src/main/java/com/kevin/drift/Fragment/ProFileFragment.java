package com.kevin.drift.Fragment;

import android.util.Log;
import android.view.View;
import android.widget.ImageView;
import android.widget.TextView;

import com.kevin.drift.Entity.User;
import com.kevin.drift.R;
import com.kevin.drift.Utils.CircleTransform;
import com.kevin.drift.Utils.DBUtils.DBUserManager;
import com.kevin.drift.Utils.RandomImageUrl;
import com.squareup.picasso.Picasso;

/**
 * Created by Benson_Tom on 2016/4/27.
 */
public class ProFileFragment extends BaseFragment implements View.OnClickListener{
    private static final String TAG ="ProFileFragment";
    private View mPhotoAlbum;
    private View mShopping;
    private View mWallet;
    private View mEmotion;
    private View mSetting;

    private ImageView mIcon;
    private TextView mName;

    private TextView mUsername;
    private TextView mUserAccount;
    private ImageView mUserIcon;

    private User user;

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
        initEvent();
    }
    private void initEvent() {
        DBUserManager manager = new DBUserManager(getActivity());
        user=manager.query();
        Log.i(TAG,"个人界面信息："+manager.query());
        mUsername.setText(user.getUsername());
        mUserAccount.setText(user.getUserAccount());
        Picasso.with(getContext()).load(RandomImageUrl.b).placeholder(R.drawable.ic_weixin_login_normal).transform(new CircleTransform()).into(mUserIcon);


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

        mUsername = (TextView) view.findViewById(R.id.my_username);
        mUserAccount = (TextView) view.findViewById(R.id.my_userAccount);
        mUserIcon = (ImageView) view.findViewById(R.id.my_userIcon);



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
