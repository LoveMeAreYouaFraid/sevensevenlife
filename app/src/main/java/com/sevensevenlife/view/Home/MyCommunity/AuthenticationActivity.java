package com.sevensevenlife.view.Home.MyCommunity;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.sevensevenlife.view.custumview.BaseActivity;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.CommunityAuthenticationActivityLayoutBinding;

/**
 * Created by Administrator on 2017/11/15 0015.
 */

public class AuthenticationActivity extends BaseActivity {
    private CommunityAuthenticationActivityLayoutBinding binding;
    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.community_authentication_activity_layout);
        binding.title.title.setText(getResources().getText(R.string.community_authentication_title));
        binding.title.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
    }
}
