package com.kevin.drift.Utils;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.provider.ContactsContract;

import com.kevin.drift.Entity.MobileContacts;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Benson_Tom on 2016/6/4.
 */
public class GetContacts {
    private static final String A="content://com.android.contacts/raw_contacts";
    private static final int ZERO=0;
    private static final String NUMBER= ContactsContract.CommonDataKinds.Phone.NUMBER;
    private static final String PHOTO_ID= ContactsContract.CommonDataKinds.Phone.PHOTO_ID;
    private static final String CONTACT_ID= ContactsContract.CommonDataKinds.Phone.CONTACT_ID;
    private static final String DISPLAY_NAME= ContactsContract.CommonDataKinds.Phone.DISPLAY_NAME;

    private static  String s[] = {CONTACT_ID, DISPLAY_NAME, NUMBER, PHOTO_ID};

    public static List<MobileContacts> getLocalContacts(Context context){
        ContentResolver r = context.getContentResolver();
        List<MobileContacts> list = new ArrayList<>();
        Cursor c = r.query(ContactsContract.CommonDataKinds.Phone.CONTENT_URI,
                s,null,null,null);
        MobileContacts m =null;
        if (c != null){
            while (c.moveToNext()){
                m = new MobileContacts();
                m.setPhone(c.getString(c.getColumnIndex(NUMBER)));
                m.setName(c.getString(c.getColumnIndex(DISPLAY_NAME)));
                m.setId(c.getLong(c.getColumnIndex(CONTACT_ID)));
                list.add(m);
            }
        }
        c.close();
        return list;
    }
}
