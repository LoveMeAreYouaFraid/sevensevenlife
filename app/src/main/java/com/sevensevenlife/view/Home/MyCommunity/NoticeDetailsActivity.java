package com.sevensevenlife.view.Home.MyCommunity;

import com.sevensevenlife.view.custumview.BaseActivity;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.CommunityNoticeDetailsActivityLayoutBinding;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Administrator on 2017/11/24 0024.
 */

public class NoticeDetailsActivity extends BaseActivity implements View.OnClickListener {
    private CommunityNoticeDetailsActivityLayoutBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.community_notice_details_activity_layout);
        binding.title.title.setText(getIntent().getStringExtra("title"));
        binding.msgDetail.setText(getIntent().getStringExtra("msgDetail"));
        binding.time.setText(getIntent().getStringExtra("times"));
        binding.title.imgBack.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
        }

    }
}
