package com.kevin.drift.Utils;

import android.util.Log;

import com.kevin.drift.Entity.DriftMessageInfo;
import com.kevin.drift.Entity.User;
import com.kevin.drift.Utils.RandomImageUrl;

import java.util.ArrayList;
import java.util.List;
import java.util.Random;

/**
 * Created by Benson_Tom on 2016/6/4.
 * 随机匹配消息数据
 */
public class RandomMessage {
    private static final String TAG = "RandomMessage";
    private static String address;
    private static String content;

    public static List<DriftMessageInfo> GetMessage(){
        List<DriftMessageInfo> list = new ArrayList<>();
        DriftMessageInfo m ;
        for (int i = 0; i < 15; i++){
            m = new DriftMessageInfo();
            m.setUserID(getRandomUserId());
            m.setDriftImg(getRandomImageUrl(i));
            m.setDriftAddress(address);
            m.setDriftContent(content);
            list.add(m);
        }
        return list;
    }

    public static List<DriftMessageInfo> ReflashMessage(List<DriftMessageInfo> list){
        DriftMessageInfo m ;
        Random r = new Random();
        for (int i = 0; i < 3; i++){
            m = new DriftMessageInfo();
            m.setUserID(getRandomUserId());
            m.setDriftImg(getRandomImageUrl(r.nextInt(10)+1));
            m.setDriftAddress(address);
            m.setDriftContent("刷新数据："+content);
            list.add(0,m);
        }
        return list;
    }

    public static String getRandomUserId(){
        Random r = new Random();
        return ""+(r.nextInt(5) + 1);
    }

    public static String getRandomImageUrl(int i){
        String mImgUrl = null;
        if (i == 1  || i == 14) {
            Log.i(TAG, "a");
            mImgUrl = RandomImageUrl.a;
            address="惠州";
            content=" 醉酒惜花音，欲问梦何处";
            return mImgUrl;
        }
        if (i == 2 ||  i == 13) {
            Log.i(TAG, "b");
            mImgUrl = RandomImageUrl.b;
            address="上海";
            content="朝朝暮，云雨定何如";
            return mImgUrl;
        }
        if (i == 7) {
            Log.i(TAG, "b");
            mImgUrl = RandomImageUrl.j;
            address="上海";
            content="朝朝暮，云雨定何如";
            return mImgUrl;
        }
        if ( i == 7 || i == 12) {
            Log.i(TAG, "c");
            mImgUrl = RandomImageUrl.c;
            address="北京";
            content="今岁花时深院，尽日东风，荡飏茶烟。";
            return mImgUrl;
        }
        if (i == 4 || i == 8 || i == 11) {
            Log.i(TAG, "d");
            mImgUrl = RandomImageUrl.d;
            address="广州";
            content="梦为远别啼难唤，书被催成墨未浓。";
            return mImgUrl;
        }
        if (i == 5 || i == 9 ) {
            Log.i(TAG, "e");
            mImgUrl = RandomImageUrl.e;
            address="深圳";
            content="残花若梦，花落满天";
            return mImgUrl;
        }
        if ( i == 0 || i == 10){
            Log.i(TAG, "e");
            mImgUrl = RandomImageUrl.h;
            address="深圳";
            content="残花若梦，花落满天";
            return mImgUrl;
        }
        if ( i == 3 ){
            Log.i(TAG, "e");
            mImgUrl = RandomImageUrl.i;
            address="深圳";
            content="残花若梦，花落满天";
            return mImgUrl;
        }
        if (mImgUrl==null){
            mImgUrl = RandomImageUrl.a;
            address="深圳";
            content="秋风，穿尘而过，云水间，静无言";
        }
        return mImgUrl;
    }

    public static User getUser(String id){
        User u = new User();
        if ("1".equals(id)){
            u.setId(1);
            u.setUsername("飞翔的小鸟");
            u.setUserIcon(RandomImageUrl.o);
            return u;
        }
        if ("2".equals(id)){
            u.setId(2);
            u.setUsername("安静的牧羊人");
            u.setUserIcon(RandomImageUrl.p);
            return u;
        }
        if ("3".equals(id)){
            u.setId(3);
            u.setUsername("帅气的拖鞋");
            u.setUserIcon(RandomImageUrl.q);
            return u;
        }
        if ("4".equals(id)){
            u.setId(4);
            u.setUsername("笨笨的西瓜");
            u.setUserIcon(RandomImageUrl.q);
            return u;
        }
        if ("5".equals(id)){
            u.setId(5);
            u.setUsername("阳光的小哥");
            u.setUserIcon(RandomImageUrl.x);
            return u;
        }
        u.setId(1);
        u.setUsername("萌萌的小鱼");
        u.setUserIcon(RandomImageUrl.y );
        return u;

    }


}
