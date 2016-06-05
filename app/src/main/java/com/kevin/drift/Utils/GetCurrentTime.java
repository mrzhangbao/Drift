package com.kevin.drift.Utils;

import java.text.SimpleDateFormat;
import java.util.Date;

/**
 * Created by Benson_Tom on 2016/6/1.
 * 获取当前系统时间
 */
public class GetCurrentTime {
    public static String time(){
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return df.format(date);
    }
    public static String timeHHMM(){
        Date date = new Date();
        SimpleDateFormat df = new SimpleDateFormat("HH:mm");
        return df.format(date);
    }
}
