package com.sevensevenlife.utils;

import android.content.Context;
import android.content.SharedPreferences;

/**
 * Created by Administrator on 2017/1/16 0016.
 */

public class OldPreferencesUtils {

    private static SharedPreferences getPreference() {
        return MyApplication.getInstance().getSharedPreferences("MY_INFO", Context.MODE_PRIVATE);

    }

    public static String getString(String keyString) {
        if (keyString == null)
            return null;
        LogUtils.e("OldPreferencesUtils", getPreference().getAll());
        return getPreference().getString(keyString, null);
    }

    public static void putString(String keyString, String valueString) {
        if (keyString != null)
            getPreference().edit().putString(keyString, valueString).commit();
    }
}
