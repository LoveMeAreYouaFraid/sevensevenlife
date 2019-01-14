package com.sevensevenlife.view.Home.MyHome;

import com.sevensevenlife.view.custumview.BaseActivity;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.MsgDetailsLayoutBinding;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

/**
 * Created by Administrator on 2017/4/7 0007.
 */

public class MsgDetailsActivity extends BaseActivity implements View.OnClickListener {
    private MsgDetailsLayoutBinding binding;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.msg_details_layout);
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
