package com.sevensevenlife.http;

import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.sevensevenlife.interfaces.HttpCallBack;
import com.sevensevenlife.interfaces.MyHttpCallBack;
import com.sevensevenlife.utils.ToastUtils;

import static com.sevensevenlife.http.RequestUtils.GET;
import static com.sevensevenlife.http.RequestUtils.POST;
import static com.sevensevenlife.http.RequestUtils.QEQUEST_URL;


/**
 * 公共请求类
 * Created by 10237 on 2016/11/11.
 * * <p>
 * 网络请求类 （新）
 * <p>
 * 调用顺序  按照代码从上至下就行   第一步调用* getInstance 最后调用 .get 或.post
 */

public class MyNewRequest {
    private static MyNewRequest request;

    private static mHandler mHandler;

    private static MyHttpCallBack callBacks;

    private static String errorStrings;

    private static String jsonString = "";

    public static Object clazz;

    private static String url;

    private static String apiUrl;

    private static String[] key;

    private static String[] value;

    private static String className;

    private static int httpType;

    private boolean isCache = false;


    public static MyNewRequest getInstance() {//new出自己 必须先调用
        request = new MyNewRequest();
        mHandler = new mHandler();
        return request;
    }

    public MyNewRequest setApiUrl(String apiUrl) {//
        MyNewRequest.apiUrl = apiUrl;
        return this;
    }

    public MyNewRequest setUri(String url) {
        MyNewRequest.url = url;
        return this;
    }

    public MyNewRequest setCallBacks(MyHttpCallBack callBacks) {
        MyNewRequest.callBacks = callBacks;
        return this;
    }

    public MyNewRequest setKey(String... key) {
        MyNewRequest.key = key;
        return this;
    }

    public MyNewRequest setValue(String... value) {
        MyNewRequest.value = value;
        return this;
    }

    public MyNewRequest setClassName(String className) {
        MyNewRequest.className = className;
        return this;
    }

    public MyNewRequest setHttpType(int httpType) {
        MyNewRequest.httpType = httpType;
        return this;
    }

    public MyNewRequest setCache(boolean cache) {
        isCache = cache;
        return this;
    }

    public MyNewRequest POST() {
        Request(POST);
        return this;
    }

    public MyNewRequest GET() {
        Request(GET);
        return this;
    }

    /**
     * 无需调用
     *
     * @param Request_Mode
     */
    private void Request(int Request_Mode) {
        try {
            clazz = Class.forName(className);
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        }
        if (TextUtils.isEmpty(url)) {
            url = QEQUEST_URL;
        }
        if (key == null) {
            ToastUtils.show("不能为空！");
            return;
        }
        if (value == null) {
            ToastUtils.show("不能为空！");
            return;
        }

        OkHttpUtils.getInstance().OkHttpCompletely(Request_Mode, url + apiUrl, key, value, new HttpCallBack() {

            @Override
            public void ok(final String backString) {
                jsonString = backString;
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
                    callBacks.ok(clazz, jsonString, httpType);
                    break;
                case 1:
                    callBacks.error(errorStrings, httpType);
                    break;
            }
        }
    }
}
