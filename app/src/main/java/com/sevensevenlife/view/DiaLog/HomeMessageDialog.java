package com.sevensevenlife.view.DiaLog;

import android.app.Dialog;
import android.content.Context;
import android.content.DialogInterface;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;

import com.sevensevenlife.http.MyNewRequest;
import com.sevensevenlife.interfaces.MyHttpCallBack;
import com.sevensevenlife.model.httpmodel.AddMessageMode;
import com.sevensevenlife.model.httpmodel.PublicMode;
import com.sevensevenlife.model.KEY;
import com.sevensevenlife.utils.LogUtils;
import com.sevensevenlife.utils.MemoryBean;
import com.sevensevenlife.utils.MyApplication;
import com.sevensevenlife.view.custumview.PushWebActivity;
import com.bumptech.glide.Glide;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.HomeMessageDialogLayoutBinding;

import jp.wasabeef.glide.transformations.RoundedCornersTransformation;

import static com.sevensevenlife.http.RequestUtils.URI.READ_AD_MESSAGE;

/**
 * Created by Administrator on 2017/8/22 0022.
 */

public class HomeMessageDialog {
    private Context mContext;
    private AddMessageMode mode;

    public HomeMessageDialog init(Context mContext) {
        this.mContext = mContext;
        return this;
    }

    public HomeMessageDialog setAddMessageMode(AddMessageMode mode) {
        this.mode = mode;
        return this;
    }

    public void show() {
        if (mContext == null) {
            return;
        }
        if (mode == null) {
            return;
        }
        HomeMessageDialogLayoutBinding binding;
        binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.home_message_dialog_layout, null, false);
        final Dialog dialog = new Dialog(mContext, R.style.transparent_dialog);
        dialog.setContentView(binding.getRoot());

        binding.imgX.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ReadMsg();
                dialog.dismiss();
            }
        });
        dialog.setOnKeyListener(new DialogInterface.OnKeyListener() {
            @Override
            public boolean onKey(DialogInterface dialog, int keyCode, KeyEvent event) {

                if (keyCode == KeyEvent.KEYCODE_BACK && event.getRepeatCount() == 0) {
                    ReadMsg();
                }
                return true;
            }
        });
        binding.linearLayout.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                MemoryBean.push_Uri = mode.getRows().getUrl();
                mContext.startActivity(new Intent(mContext, PushWebActivity.class)
                        .putExtra(KEY.PushURL, mode.getRows().getUrl()));
                LogUtils.e(123, mode.getRows().getUrl());
                ReadMsg();
                dialog.dismiss();
            }
        });
        Glide.with(mContext)
                .load(mode.getRows().getImage())
                .bitmapTransform(new RoundedCornersTransformation(mContext, 36, 0,
                        RoundedCornersTransformation.CornerType.TOP))
                .into(binding.imgImg);
        binding.tvTitle.setText(mode.getRows().getTitle());
        binding.tvContext.setText(mode.getRows().getContent());
        dialog.show();


    }

    private void ReadMsg() {
        MyNewRequest.getInstance()
                .setCache(false)
                .setClassName(PublicMode.class.getName())
                .setApiUrl(READ_AD_MESSAGE)
                .setCallBacks(new MyHttpCallBack() {
                    @Override
                    public <T> void ok(T backMode, String jsonString, int httpTY) {
                        LogUtils.e(123, jsonString);
                    }

                    @Override
                    public void error(String e, int uriType) {
                        LogUtils.e(123, e);
                    }
                })
                .setKey("sessionId", "messageId")
                .setValue(MyApplication.getInstance().getUserInfo().getRows().getSessionId(),
                        mode.getRows().getId() + "")
                .GET();
    }
}
