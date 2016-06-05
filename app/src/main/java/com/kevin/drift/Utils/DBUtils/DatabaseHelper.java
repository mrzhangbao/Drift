package com.kevin.drift.Utils.DBUtils;

import android.content.Context;
import android.database.sqlite.SQLiteDatabase;
import android.database.sqlite.SQLiteOpenHelper;

/**
 * Created by Benson_Tom on 2016/5/26.
 * SQLite的帮助类
 */
public class DatabaseHelper extends SQLiteOpenHelper{


    private static final String DB_NAME="user.db";//数据库名称
    private static final int VERSION=1;//数据库版本

    public DatabaseHelper(Context context) {
        super(context, DB_NAME, null, VERSION);
    }

    @Override
    public void onCreate(SQLiteDatabase db) {
        //创建SQLite数据库中的user表
        db.execSQL("CREATE TABLE user(id integer primary key,userAccount varchar(20),username varchar(20)," +
                "userIcon varchar(30),userIntroduce varchar(60),userFansNumbers varchar(20),userFocusNumbers varchar(20))");

    }

    @Override
    public void onUpgrade(SQLiteDatabase db, int i, int i1) {
        db.execSQL("drop if table exists user");
        onCreate(db);
    }
}
