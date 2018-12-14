package com.sevensevenlife.utils;

import android.widget.Toast;

/**
 * Created by Administrator on 2016/12/26 0026.
 */

public class ToastUtils {
    public static void show(Object info) {
        Toast.makeText(MyApplication.getInstance(), info + "", Toast.LENGTH_SHORT).show();
    }

    public static void showLong(Object info) {
        Toast.makeText(MyApplication.getInstance(), info + "", Toast.LENGTH_LONG).show();
    }

}
