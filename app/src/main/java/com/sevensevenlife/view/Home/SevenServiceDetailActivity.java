package com.sevensevenlife.view.Home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.sevensevenlife.view.custumview.BaseActivity;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.SevenServiceDetailActivityLayoutBinding;
import com.sevensevenlife.utils.MemoryBean;

/**
 * Created by Administrator on 2017/4/5 0005.
 */

public class SevenServiceDetailActivity extends BaseActivity {
    private SevenServiceDetailActivityLayoutBinding bin;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        bin = DataBindingUtil.setContentView(this, R.layout.seven_service_detail_activity_layout);
        bin.detail.loadDataWithBaseURL(null, MemoryBean.typeDesc, "text/html", "utf-8", null);
        bin.title.title.setText("详情");
        bin.title.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
