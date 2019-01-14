package com.sevensevenlife.view.Find;

import android.content.ContentResolver;
import android.content.Context;
import android.database.Cursor;
import android.net.Uri;
import android.provider.ContactsContract.CommonDataKinds.Phone;
import android.provider.ContactsContract.CommonDataKinds.Photo;
import android.text.TextUtils;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/4/20 0020.
 */

public class GetuUserNumUtils {
    Context mContext = null;

    /**
     * 电话号码
     **/
    private static final int PHONES_NUMBER_INDEX = 1;

    private ArrayList<String> mContactsNumber = new ArrayList<>();
    /**
     * 获取库Phon表字段
     **/
    private static final String[] PHONES_PROJECTION = new String[]{
            Phone.DISPLAY_NAME, Phone.NUMBER, Photo.PHOTO_ID, Phone.CONTACT_ID};

    public List<String> getPhoneContacts(Context mContext) {
        this.mContext = mContext;
        ContentResolver resolver = mContext.getContentResolver();
        Cursor phoneCursor = resolver.query(Phone.CONTENT_URI, PHONES_PROJECTION, null, null, null);
        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {
                String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
                if (TextUtils.isEmpty(phoneNumber))
                    continue;
                mContactsNumber.add(phoneNumber);
            }
            phoneCursor.close();
            getSIMContacts();
        }
        return mContactsNumber;
    }

    private void getSIMContacts() {
        ContentResolver resolver = mContext.getContentResolver();
        Uri uri = Uri.parse("content://icc/adn");
        Cursor phoneCursor = resolver.query(uri, PHONES_PROJECTION, null, null, null);
        if (phoneCursor != null) {
            while (phoneCursor.moveToNext()) {
                String phoneNumber = phoneCursor.getString(PHONES_NUMBER_INDEX);
                if (TextUtils.isEmpty(phoneNumber))
                    continue;
                mContactsNumber.add(phoneNumber);
            }
            phoneCursor.close();
        }

    }
}
