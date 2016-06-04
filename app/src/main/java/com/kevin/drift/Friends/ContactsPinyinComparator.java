package com.kevin.drift.Friends;

import com.kevin.drift.Entity.MobileContacts;

import java.util.Comparator;

/**
 * Created by Benson_Tom on 2016/4/3.
 * 这个类主要是用来对ListView中的数据根据A-Z进行排序，前面的两个if判断主要是将不是以
 * 汉字开头的数据放在后面
 */
public class ContactsPinyinComparator implements Comparator<MobileContacts>{
    @Override
    public int compare(MobileContacts s1, MobileContacts s2) {
        if (s2.getUserFirstLetter().equals("#")|| s2.getUserFirstLetter().equals("@"))
        {
            return  -1;
        }else if (s1.getUserFirstLetter().equals("#")|| s2.getUserFirstLetter().equals("@"))
        {
            return 1;
        }else{
            return s1.getUserFirstLetter().compareTo(s2.getUserFirstLetter());
        }
    }
}
