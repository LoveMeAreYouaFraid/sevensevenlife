package com.sevensevenlife.view.User;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.sevensevenlife.model.KEY;
import com.sevensevenlife.utils.LogUtils;
import com.sevensevenlife.utils.MyApplication;
import com.sevensevenlife.utils.PreferencesUtil;
import com.sevensevenlife.view.custumview.BaseActivity;
import com.sevensevenlife.view.custumview.WebViewInfoActivity;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.ActivityShezhiBinding;


import static com.sevensevenlife.http.RequestUtils.QEQUEST_URL;
import static com.sevensevenlife.model.KEY.REG_ID;
import static com.sevensevenlife.model.KEY.STAROKEN;

public class SheZhiActivity extends BaseActivity implements View.OnClickListener {
    private ActivityShezhiBinding binding;
    private static String TAG = SheZhiActivity.class.getName();
    private String IDString;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_shezhi);
        binding.title.imgBack.setOnClickListener(this);
        binding.title.title.setText("设置");
        binding.exit.setOnClickListener(this);
        binding.one.setOnClickListener(this);
        binding.two.setOnClickListener(this);
        binding.tvClearCache.setOnClickListener(this);
        binding.tvClearCache.setText("清除缓存   " + PreferencesUtil.getTotalCacheSize(mContext));
        if (!TextUtils.isEmpty(PreferencesUtil.getString(REG_ID))) {
            IDString = PreferencesUtil.getString(REG_ID);
        }
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.tv_clear_cache:
                PreferencesUtil.clearAll();
                PreferencesUtil.putString(REG_ID, IDString);
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.exit:

                try {
                    PreferencesUtil.clearAll();
                    PreferencesUtil.removeKey(KEY.USER);
                    PreferencesUtil.removeKey(STAROKEN);
                    PreferencesUtil.putString(REG_ID, IDString);
                } catch (Exception e) {
                    LogUtils.e(TAG, e.toString());
                } finally {
                    MyApplication.getInstance().setLogin(false);
                    MyApplication.getInstance().setUserInfo(null);
                    startActivity(new Intent(mContext, LoginActivity.class));
                    PreferencesUtil.putString(REG_ID, IDString);
                    finish();
                }

                break;
            case R.id.one:
                Intent intent = new Intent(this, AboutOurActivity.class);
                startActivity(intent);
                break;
            case R.id.two:
                Intent intents = new Intent(this, WebViewInfoActivity.class);
                intents.putExtra("UrlAddress", QEQUEST_URL + "yxsh-api/html/protocol.html");
                startActivity(intents);
                break;
        }
    }
}
