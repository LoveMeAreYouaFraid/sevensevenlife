package com.sevensevenlife.utils;

import android.text.TextUtils;

import com.sevensevenlife.http.MyNewRequest;
import com.sevensevenlife.http.OkHttpUtils;
import com.sevensevenlife.http.RequestUtils;
import com.sevensevenlife.interfaces.CheckUserTokenInterFace;
import com.sevensevenlife.interfaces.HttpCallBack;
import com.sevensevenlife.interfaces.MyHttpCallBack;
import com.sevensevenlife.model.httpmodel.CheckUserInfo;
import com.sevensevenlife.model.httpmodel.TokonMode;

import static com.sevensevenlife.http.RequestUtils.ZIXINGCHE_URL;
import static com.sevensevenlife.model.KEY.PID;
import static com.sevensevenlife.model.KEY.STAROKEN;

/**
 * Created by Administrator on 2017/7/17 0017.
 */

public class BicycleGetUserTokeUtils {

    private CheckUserInfo checkUserInfo;

    private TokonMode tokonMode;


    private String datetime = TimeUtils.yyyymmddhhmmss();


    public void Check(final CheckUserTokenInterFace interFace) {
        if (!TextUtils.isEmpty(PreferencesUtil.getString(STAROKEN))) {
            CheckUserInfo(interFace);
        } else {
            getUserToken(interFace);
        }

    }

    private void CheckUserInfo(final CheckUserTokenInterFace interFace) {
        String sign = BicycleGetSignUtils.getSign(
                PreferencesUtil.getString(STAROKEN)
                        + datetime
                        + PID);
        MyNewRequest.getInstance()
                .setUri(ZIXINGCHE_URL)
                .setApiUrl("/H5.V2/User/CheckUserInfo"
                        + "?strToken=" + PreferencesUtil.getString(STAROKEN)
                        + "&datetime=" + datetime
                        + "&datetime=" + PID
                        + "&sign=" + sign)
                .setKey("")
                .setValue("")
                .setCallBacks(new MyHttpCallBack() {
                    @Override
                    public <T> void ok(T backMode, String jsonString, int httpTY) {
                        checkUserInfo = new JsonUtil<CheckUserInfo>().json2Bean(jsonString, CheckUserInfo.class.getName());
                        if (checkUserInfo.isTrue()) {
                            PreferencesUtil.removeKey(STAROKEN);
                            PreferencesUtil.putString(STAROKEN, checkUserInfo.getMessage());
                            interFace.True(checkUserInfo.getMessage());
                        } else {
                            getUserToken(interFace);
                        }
                    }

                    @Override
                    public void error(String e, int uriType) {
                        interFace.False();
                    }
                })
                .setClassName(CheckUserInfo.class.getName())
                .setHttpType(12)
                .POST();
    }

    private void getUserToken(final CheckUserTokenInterFace interFace) {
        String datetime = TimeUtils.yyyymmddhhmmss();
        String sign = BicycleGetSignUtils.getSign(PID + MyApplication.getInstance().getUserInfo().getRows().getPhone() + datetime);
        new OkHttpUtils().OkHttpCompletely(RequestUtils.POST,
                ZIXINGCHE_URL + "/H5.V2/User/getusertoken" +
                        "?Pid=" + PID +
                        "&datetime=" + datetime +
                        "&Telephone=" + MyApplication.getInstance().getUserInfo().getRows().getPhone() +
                        "&sign=" + sign
                , new String[]{},
                new String[]{}, new HttpCallBack() {
                    @Override
                    public void ok(String backString) {
                        tokonMode = new JsonUtil<TokonMode>().json2Bean(backString, TokonMode.class.getName());
                        try {
                            if (tokonMode != null && tokonMode.isTrue()) {
                                if (tokonMode.getMessage() != null) {
                                    PreferencesUtil.removeKey(STAROKEN);
                                    PreferencesUtil.putString(STAROKEN, tokonMode.getMessage());
                                    interFace.True(tokonMode.getMessage());
                                } else {
                                    interFace.False();
                                }
                            } else {
                                interFace.False();
                            }
                        } catch (Exception x) {
                            LogUtils.e(123,x.getMessage());
                        }
                    }

                    @Override
                    public void error(String e) {
                        ToastUtils.show("Tokon获取失败");
                        interFace.False();
                    }
                }, false);
    }
}
