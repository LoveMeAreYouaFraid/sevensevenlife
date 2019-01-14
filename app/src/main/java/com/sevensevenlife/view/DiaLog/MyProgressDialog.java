package com.sevensevenlife.view.DiaLog;

import android.app.Activity;
import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.DialogInterface.OnCancelListener;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import com.example.youxiangshenghuo.R;


/**
 * Copyright (©) 2014
 * <p/>
 * 正在处理的半透明对话框
 *
 * @author Dongxiang
 * @version 1.0, 6/13/14 14:30
 * @since 5/13/14
 */
public class MyProgressDialog {


    private static MyProgressDialog instance = new MyProgressDialog();

    private MyProgressDialog() {
    }

    /**
     * @return ProgressDialog
     */
    public static MyProgressDialog getInstance() {
        return instance;
    }

    private Dialog mProgressDialog;

    private Context mContext;

    /**
     * @param context
     * @param msg
     */
    public void show(Context context, String msg) {

        show(context, msg, true);

    }

    public void showLoding(Context context) {

        show(context, "数据获取中...", true);

    }

    /**
     * @param context
     * @param msg
     */
    public void show(Context context, String msg, boolean isCancelable) {

        mContext = context;

        if (!isValidContext(mContext)) {
            return;
        }

        if (mProgressDialog != null) {
            mProgressDialog.cancel();
        }

        mProgressDialog = new Dialog(context, R.style.dialog);

        View view = LayoutInflater.from(context).inflate(
                R.layout.progress_dialog_layout, null);

        TextView tvInfo = (TextView) view.findViewById(R.id.tv_info);
        tvInfo.setText(msg);

        mProgressDialog.setContentView(view);
        if (context != null) {
            mProgressDialog.show();
        }
        mProgressDialog.setCanceledOnTouchOutside(false);
        mProgressDialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {
                return true;
            }
        });

    }

    /**
     * @param listener
     */
    public void setOnCancelListener(OnCancelListener listener) {
        mProgressDialog.setOnCancelListener(listener);
    }

    /**
     * dismiss
     */
    public void cancel() {

        if (mContext != null && mProgressDialog != null && mProgressDialog.isShowing()) {
            mProgressDialog.dismiss();
        }
    }

    private boolean isValidContext(Context c) {

        Activity a = (Activity) c;

        if (a == null || a.isFinishing()) {
            return false;
        } else {
            return true;
        }
    }

}

