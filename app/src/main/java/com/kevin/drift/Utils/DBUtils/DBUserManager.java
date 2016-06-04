package com.kevin.drift.Utils.DBUtils;

import android.content.ContentValues;
import android.content.Context;
import android.database.Cursor;
import android.database.sqlite.SQLiteDatabase;
import android.util.Log;

import com.kevin.drift.Entity.User;

/**
 * Created by Benson_Tom on 2016/5/26.
 */
public class DBUserManager {
    private static final String TAG = "DBUserManager";
    private Context mContext;
    private DatabaseHelper databaseHelper;


    public DBUserManager(Context context){
        mContext = context;
        databaseHelper = new DatabaseHelper(context);
    }

    public boolean addUser(User u){
        boolean b = false;
        SQLiteDatabase db = null;
        Log.i(TAG,"用户："+u.toString());
        deleteUser();
        try {
            db = databaseHelper.getWritableDatabase();
//            db.beginTransaction();
            ContentValues values = new ContentValues();
            values.put("id",u.getId());
            values.put("userAccount",u.getUserAccount());
            values.put("username",u.getUsername());
            values.put("userIcon",u.getUserIcon());
            values.put("userIntroduce",u.getUserIntroduce());
            values.put("userFansNumbers",u.getUserFansNumber());
            values.put("userFocusNumbers",u.getUserFocusNumbers());
            db.insert("user",null,values);
            Log.i(TAG,"values:"+values.toString());
            b = true;
        }catch (Exception e){
        b=false;
        }finally {
//            db.endTransaction();
            db.close();
        }
        return b;
    }

    public User query(){
        User user = null;
        SQLiteDatabase db = null;
        db = databaseHelper.getWritableDatabase();
        Cursor c = db.query("user",null,null,null,null,null,null);
        if (c.moveToFirst()){
            user = new User();
            user.setId(c.getInt(c.getColumnIndex("id")));
            user.setUserAccount(c.getString(c.getColumnIndex("userAccount")));
            user.setUsername(c.getString(c.getColumnIndex("username")));
            user.setUserIcon(c.getString(c.getColumnIndex("userIcon")));
            user.setUserIntroduce(c.getString(c.getColumnIndex("userIntroduce")));
            user.setUserFansNumber(c.getString(c.getColumnIndex("userFansNumbers")));
            user.setUserFocusNumbers(c.getString(c.getColumnIndex("userFocusNumbers")));
        }
        c.close();
        db.close();
        return  user;
    }

    public void deleteUser(){
        SQLiteDatabase db = databaseHelper.getWritableDatabase();
        Log.i(TAG,"删除了SQLite数据");
        db.execSQL("delete from user");
    }

}
