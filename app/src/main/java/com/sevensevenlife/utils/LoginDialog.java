package com.sevensevenlife.utils;

import android.content.Context;
import android.content.Intent;

import com.sevensevenlife.interfaces.DialogListener;
import com.sevensevenlife.view.DiaLog.DialogUtils;
import com.sevensevenlife.view.User.LoginActivity;

/**
 * Created by Administrator on 2017/4/19 0019.
 */

public class LoginDialog {
    public LoginDialog(final Context mContext) {
        DialogUtils.init(mContext)
                .setTitle("您尚未登录是否马上登录？")
                .setOne("登录")
                .setTwo("取消")
                .setDialogListener(new DialogListener() {
                    @Override
                    public void buttType(int ButtType) {
                        if (ButtType == 1) {
                            mContext.startActivity(new Intent(mContext, LoginActivity.class));
                        }
                    }
                });
    }
}
