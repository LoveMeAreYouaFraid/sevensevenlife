package com.sevensevenlife.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.os.Environment;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import java.io.File;
import java.math.BigDecimal;

/**
 * Copyright (©) 2014
 * <p/>
 * 配置文件读写工具类
 *
 * @author eastonc
 * @version 1.0, 14-7-28 13:43
 * @since 14-7-28
 */
public class PreferencesUtil {

    private static SharedPreferences getPreference(Context paramContext, String preferencesName) {
        return paramContext.getSharedPreferences(preferencesName, Context.MODE_PRIVATE);

    }

    /**
     * 清除指定名称的配置文件内数据
     *
     * @param
     * @param
     */
    public static void clearAll() {
        getPreference(MyApplication.getInstance(),
                MyApplication.getInstance().getPackageName() + "_preferences").edit().clear().commit();
    }

    /**
     * 获取缓存大小
     *
     * @param
     * @param
     */
    public static String getTotalCacheSize(Context context) {
        long cacheSize = 0;
        try {
            cacheSize = getFolderSize(context.getCacheDir());
        } catch (Exception e) {
            e.printStackTrace();
        }
        if (Environment.getExternalStorageState().equals(Environment.MEDIA_MOUNTED)) {
            try {
                cacheSize += getFolderSize(context.getCacheDir());
            } catch (Exception e) {
                e.printStackTrace();
            }
        }
        return getFormatSize(cacheSize);
    }

    /**
     * 计算缓存大小
     *
     * @param file
     * @return
     * @throws Exception
     */
    public static long getFolderSize(File file) throws Exception {
        long size = 0;
        try {
            File[] fileList = file.listFiles();
            for (int i = 0; i < fileList.length; i++) {
                // 如果下面还有文件
                if (fileList[i].isDirectory()) {
                    size = size + getFolderSize(fileList[i]);
                } else {
                    size = size + fileList[i].length();
                }
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return size;
    }

    /**
     * 获取String类型的键值
     *
     * @param paramContext
     * @param preferencesName
     * @param keyString
     * @return Key value
     */
    public static String getString(Context paramContext, String preferencesName, String keyString) {
        if (keyString == null)
            return null;
        return getPreference(paramContext, preferencesName).getString(keyString, null);
    }

    public static void putString(String keyString, String valueString) {
        if (keyString != null)
            getDefaultSharedPreferences().edit().putString(keyString, valueString).commit();
    }

    /**
     * 存储String类型的键值对
     *
     * @param paramContext
     * @param preferencesName
     * @param keyString
     * @param valueString
     */
    public static void putString(Context paramContext, String preferencesName, String keyString,
                                 String valueString) {
        if (keyString != null)
            getPreference(paramContext, preferencesName).edit().putString(keyString, valueString)
                    .commit();
    }

    public static void putInt(String keyString, int valueString) {
        if (keyString != null)
            getDefaultSharedPreferences().edit().putInt(keyString, valueString).commit();
    }

    /**
     * @return
     */
    private static SharedPreferences getDefaultSharedPreferences() {
        return PreferenceManager.getDefaultSharedPreferences(MyApplication.getInstance());
    }

    /**
     * 获取String类型的键值
     *
     * @param
     * @param
     * @param keyString
     * @return Key value
     */
    public static String getString(String keyString) {
        if (keyString == null)
            return null;
        return getDefaultSharedPreferences().getString(keyString, null);
    }

    public static int getInt(String keyString) {
        return getDefaultSharedPreferences().getInt(keyString, 0);
    }


    /**
     * 清空键名为 keyString 的键值对
     *
     * @param keyString
     */
    public static void clearKey(String keyString) {
        if (!TextUtils.isEmpty(keyString))
            getDefaultSharedPreferences().edit().remove(keyString).commit();
//            getDefaultSharedPreferences().edit().clear().commit();
    }

    /**
     * 移除键名为 keyString 的键值对
     *
     * @param
     * @param
     * @param keyString
     */
    public static void removeKey(String keyString) {
        if (keyString != null) {
            if (!TextUtils.isEmpty(getString(keyString))) {
                getDefaultSharedPreferences().edit().remove(keyString).commit();
            }
        }
    }


    public static String getFormatSize(double size) {
        double kiloByte = size / 1024;
        if (kiloByte < 1) {
            return "";
        }

        double megaByte = kiloByte / 1024;
        if (megaByte < 1) {
            BigDecimal result1 = new BigDecimal(Double.toString(kiloByte));
            return result1.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "K";
        }

        double gigaByte = megaByte / 1024;
        if (gigaByte < 1) {
            BigDecimal result2 = new BigDecimal(Double.toString(megaByte));
            return result2.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "M";
        }

        double teraBytes = gigaByte / 1024;
        if (teraBytes < 1) {
            BigDecimal result3 = new BigDecimal(Double.toString(gigaByte));
            return result3.setScale(2, BigDecimal.ROUND_HALF_UP)
                    .toPlainString() + "GB";
        }
        BigDecimal result4 = new BigDecimal(teraBytes);
        return result4.setScale(2, BigDecimal.ROUND_HALF_UP).toPlainString()
                + "TB";
    }
}
