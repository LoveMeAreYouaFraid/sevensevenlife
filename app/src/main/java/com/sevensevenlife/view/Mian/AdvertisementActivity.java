package com.sevensevenlife.view.Mian;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.graphics.Color;
import android.os.Build;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import com.sevensevenlife.utils.ImgLoadUtils;
import com.sevensevenlife.view.custumview.BaseActivity;
import com.sevensevenlife.view.custumview.WebViewInfoActivity;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.AdvertisementActivityLayoutBinding;

/**
 * Created by Administrator on 2017/1/4 0004.
 */

public class AdvertisementActivity extends BaseActivity implements View.OnClickListener {
    private AdvertisementActivityLayoutBinding binding;
    private TimeCount time;
    private Context mContext;
    private boolean isTimeJup = true;
    private String PicUrl = "", LinkValue = "";

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        binding = DataBindingUtil.setContentView(this, R.layout.advertisement_activity_layout);
        binding.tvJup.setOnClickListener(this);
        binding.viewAdvertisement.setOnClickListener(this);
        if (Build.VERSION.SDK_INT >= 21) {
            View decorView = getWindow().getDecorView();
            int option = View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                    | View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                    | View.SYSTEM_UI_FLAG_LAYOUT_STABLE;
            decorView.setSystemUiVisibility(option);
            getWindow().setNavigationBarColor(Color.TRANSPARENT);
            getWindow().setStatusBarColor(Color.TRANSPARENT);
        }
        PicUrl = getIntent().getStringExtra("PicUrl");
        if (getIntent().hasExtra("LinkValue")) {
            LinkValue = getIntent().getStringExtra("LinkValue");
        }
        ImgLoadUtils.Load(mContext, PicUrl, binding.viewAdvertisement, false);
        time = new TimeCount(4000, 1000);
        time.start();


    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_jup:
                jup();
                break;
            case R.id.view_advertisement:
                if (LinkValue.length() > 1) {
                    Intent intent = new Intent(mContext, WebViewInfoActivity.class);
                    intent.putExtra("Advertisement", LinkValue);
                    startActivity(intent);
                    isTimeJup = false;
                    finish();
                }
                break;
        }
    }


    private class TimeCount extends CountDownTimer {// 计时器

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            if (isTimeJup) {
                jup();
            }
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            binding.tvJup.setText("  跳过 " + ((millisUntilFinished / 1000) - 1) + " ");
        }

    }

    private void jup() {
        isTimeJup = false;
        startActivity(new Intent(mContext, MainActivityLp.class));
        finish();
    }
}
