package com.kevin.drift.Fragment;

import android.view.View;
import android.widget.ImageView;

import com.kevin.drift.R;
import com.kevin.drift.Utils.CircleTransform;
import com.kevin.drift.Utils.RandomMessage;
import com.squareup.picasso.Picasso;

import java.util.Random;

/**
 * Created by Benson_Tom on 2016/4/27.
 * 消息界面
 */
public class MessageFragment extends BaseFragment{
    private ImageView mImage;
    private Random r = new Random();
    @Override
    protected int setLayout() {
        return R.layout.fragment_message;
    }

    @Override
    protected void initEvent(View view) {
        mImage = (ImageView) view.findViewById(R.id.message_img);
        Picasso.with(getContext())
                .load(RandomMessage.getRandomImageUrl(r.nextInt(12)+1))
                .placeholder(R.drawable.ic_weixin_login_normal)
                .transform(new CircleTransform())
                .into(mImage);


    }
}
