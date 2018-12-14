package com.sevensevenlife.utils;

import android.util.Log;

import com.example.youxiangshenghuo.BuildConfig;

import static com.sevensevenlife.http.RequestUtils.FORMAL_URL;
import static com.sevensevenlife.http.RequestUtils.QEQUEST_URL;

/**
 * Created by Administrator on 2016/12/26 0026.
 */

public class LogUtils {
    public static int e(Object tag, Object string) {
        String str = string + "";
        String mTag = tag + "";
        str = str.trim();
        if (str.length() > 4000) {
            for (int i = 0; i < str.length(); i += 4000) {
                if (i + 4000 < str.length()) {
                    if (BuildConfig.DEBUG ) {// && QEQUEST_URL != FORMAL_URL
                        Log.e(mTag + i, str.substring(i, i + 4000));
                    }
                } else {
                    if (BuildConfig.DEBUG ) {
                        Log.e(mTag + i, str.substring(i, str.length()));
                    }
                }
            }
        } else {
            if (BuildConfig.DEBUG && QEQUEST_URL != FORMAL_URL) {
                Log.e(mTag, str);
            }
        }
        return 0;
    }

}
