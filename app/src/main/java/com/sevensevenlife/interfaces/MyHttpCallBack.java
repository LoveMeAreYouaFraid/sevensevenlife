package com.sevensevenlife.interfaces;

/**
 * Created by 10237 on 2016/11/11.
 */

public interface MyHttpCallBack {
    <T> void ok(T backMode, String jsonString, int httpTY);

    void error(String e, int uriType);
}
