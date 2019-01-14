package com.sevensevenlife.view.Home.AppMsg;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.view.View;

import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.AppMsgActivityBinding;

import java.util.ArrayList;
import java.util.List;

public class AppMsgActivity extends AppCompatActivity implements View.OnClickListener {
    private AppMsgActivityBinding binding;
    private List<Fragment> list = new ArrayList<>();
    private Context mContext;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.app_msg_activity);
        mContext = this;
        binding.imgBack.setOnClickListener(this);
        binding.carMsg.setOnClickListener(this);
        binding.systemMsg.setOnClickListener(this);
        list.add(new CarMsgListFragment());
        list.add(new SystemMsgListFragment());
        binding.lpViewPager.setAdapter(new FragmentPagerAdapter(getSupportFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return list.get(position);
            }

            @Override
            public int getCount() {
                return list.size();
            }
        });
    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.car_msg:
                binding.carMsg.setBackgroundResource(R.drawable.bt_ddxiaoxi);
                binding.carMsg.setTextColor(ContextCompat.getColor(mContext, R.color.color_47_47_47));
                binding.systemMsg.setBackgroundResource(R.color.transparent);
                binding.systemMsg.setTextColor(ContextCompat.getColor(mContext, R.color.color_111111));
                binding.lpViewPager.setCurrentItem(0);
                break;
            case R.id.system_msg:
                binding.systemMsg.setBackgroundResource(R.drawable.bt_xtxiaoxi);
                binding.systemMsg.setTextColor(ContextCompat.getColor(mContext, R.color.color_47_47_47));
                binding.carMsg.setBackgroundResource(R.color.transparent);
                binding.carMsg.setTextColor(ContextCompat.getColor(mContext, R.color.color_111111));
                binding.lpViewPager.setCurrentItem(1);
                break;
        }
    }
}
