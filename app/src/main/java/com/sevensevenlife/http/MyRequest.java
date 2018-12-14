package com.sevensevenlife.http;

import android.os.Handler;
import android.os.Message;

import com.sevensevenlife.interfaces.HttpCallBack;
import com.sevensevenlife.interfaces.MyHttpCallBack;
import com.sevensevenlife.model.httpmodel.PublicMode;
import com.sevensevenlife.utils.JsonUtil;


/**
 * 公共请求类
 * Created by 10237 on 2016/11/11.
 * <p>
 * 网络请求类 （老）（不建议使用）
 */

public class MyRequest {

    private static mHandler mHandler;

    private static MyHttpCallBack callBacks;

    private static String errorStrings;

    private static String jsonString = "";

    public static Object clazz;

    private static int hpType;

    /**
     * @param Request_Mode
     * @param key
     * @param value
     * @param url
     * @param className
     * @param httpType
     * @param callBack
     */
    public static void POST(int Request_Mode, String[] key, String[] value, final String url,
                            final String className, int httpType, MyHttpCallBack callBack) {
        mHandler = new mHandler();
        callBacks = callBack;
        hpType = httpType;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        OkHttpUtils.getInstance().OkHttpRequest(Request_Mode, url, key, value, new HttpCallBack() {

            @Override
            public void ok(final String backString) {
                jsonString = backString;
                clazz = new JsonUtil<PublicMode>().json2Bean(backString, className);
                mHandler.sendEmptyMessage(0);


            }

            @Override
            public void error(String e) {
                errorStrings = e;
                mHandler.sendEmptyMessage(1);
            }

        }, false);
    }

    /**
     * @param Request_Mode
     * @param key
     * @param value
     * @param url
     * @param className
     * @param httpType
     * @param callBack
     * @param isCache
     */
    public static void POST(int Request_Mode, String[] key, String[] value, final String url,
                            final String className, int httpType, MyHttpCallBack callBack, boolean isCache) {
        mHandler = new mHandler();
        callBacks = callBack;
        hpType = httpType;
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        OkHttpUtils.getInstance().OkHttpRequest(Request_Mode, url, key, value, new HttpCallBack() {

            @Override
            public void ok(final String backString) {
                jsonString = backString;
                clazz = new JsonUtil<PublicMode>().json2Bean(backString, className);
                mHandler.sendEmptyMessage(0);


            }

            @Override
            public void error(String e) {
                errorStrings = e;
                mHandler.sendEmptyMessage(1);
            }

        }, isCache);
    }

    static class mHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 0:
                    callBacks.ok(clazz, jsonString, hpType);
                    break;
                case 1:
                    callBacks.error(errorStrings, hpType);
                    break;
            }
        }
    }
}
