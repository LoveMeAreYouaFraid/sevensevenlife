package com.sevensevenlife.http;

import android.os.Handler;
import android.os.Message;


import com.sevensevenlife.interfaces.HttpCallBack;
import com.sevensevenlife.interfaces.MyHttpCallBack;
import com.sevensevenlife.utils.LogUtils;

import java.io.File;

/**
 * Created by 10237 on 2016/11/19.
 * 图片上传类
 */


public class UpImg {
    private static mHandler mHandler;

    private static mHandlers mHandlers;

    private static HttpCallBack callBacks;

    private static MyHttpCallBack myHttpCallBack;

    private static String okStrings;

    private static String errorStrings;


    public static void POST(File file, HttpCallBack callBack) {
        mHandler = new mHandler();
        callBacks = callBack;
        OkHttpUtils.getInstance().PostImg(file,
                new HttpCallBack() {
                    @Override
                    public void ok(String backString) {
                        okStrings = backString;
                        mHandler.sendEmptyMessage(951);
                        LogUtils.e("backString=", backString);


                    }

                    @Override
                    public void error(String e) {
                        errorStrings = e;
                        mHandler.sendEmptyMessage(950);
                    }
                });
    }

    public static void POST(File file, MyHttpCallBack callBack) {
        mHandlers = new mHandlers();
        myHttpCallBack = callBack;
        OkHttpUtils.getInstance().PostImg(file,
                new HttpCallBack() {
                    @Override
                    public void ok(String backString) {
                        okStrings = backString;
                        mHandlers.sendEmptyMessage(951);
                        LogUtils.e("backString=", backString);


                    }

                    @Override
                    public void error(String e) {
                        errorStrings = e;
                        mHandlers.sendEmptyMessage(950);
                    }
                });
    }

    static class mHandler extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 951:
                    callBacks.ok(okStrings);
                    break;
                case 950:
                    callBacks.error(errorStrings);
                    break;
            }
        }
    }

    static class mHandlers extends Handler {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case 951:
                    myHttpCallBack.ok(null, okStrings, 0);
                    break;
                case 950:
                    myHttpCallBack.error(errorStrings, 0);
                    break;
            }
        }
    }
}
