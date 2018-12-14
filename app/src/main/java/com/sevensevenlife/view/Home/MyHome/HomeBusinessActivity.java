package com.sevensevenlife.view.Home.MyHome;

import com.sevensevenlife.model.KEY;
import com.sevensevenlife.utils.DPIUtil;
import com.sevensevenlife.utils.TextViewParser;
import com.sevensevenlife.utils.ToastUtils;
import com.sevensevenlife.view.custumview.BaseActivity;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.HomeBusinessActivityLayoutBinding;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;

/**
 * Created by Administrator on 2017/4/6 0006.
 */

public class HomeBusinessActivity extends BaseActivity implements View.OnClickListener {
    private HomeBusinessActivityLayoutBinding binding;
    private String name = "", id;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.home_business_activity_layout);
        binding.title.imgBack.setOnClickListener(this);
        binding.title.title.setText("小区业务");
        binding.homeMsg.setOnClickListener(this);
        binding.homeOpinion.setOnClickListener(this);
        binding.homeRepair.setOnClickListener(this);
        binding.homeStaff.setOnClickListener(this);
        binding.homeVote.setOnClickListener(this);
        binding.homePost.setOnClickListener(this);
        if (!TextUtils.isEmpty(getIntent().getStringExtra(KEY.NAME))) {
            name = getIntent().getStringExtra(KEY.NAME);
        }
        if (!TextUtils.isEmpty(getIntent().getStringExtra(KEY.ID))) {
            id = getIntent().getStringExtra(KEY.ID);
        }
        initView();
    }

    private void initView() {
        TextViewParser textHomeMsg = new TextViewParser();
        binding.homeName.setText(name);
        textHomeMsg.append("小区公告\n", DPIUtil.dip2px(16), ContextCompat.getColor(mContext, R.color.txt_black));
        textHomeMsg.append("查看小区最新公告",  DPIUtil.dip2px(16), ContextCompat.getColor(mContext, R.color.style_color));
        textHomeMsg.parse(binding.homeMsg);
        TextViewParser textOpinion = new TextViewParser();
        textOpinion.append("意见箱\n", DPIUtil.dip2px(16), ContextCompat.getColor(mContext, R.color.txt_black));
        textOpinion.append("对小区提出意见",  DPIUtil.dip2px(16), ContextCompat.getColor(mContext, R.color.style_color));
        textOpinion.parse(binding.homeOpinion);
        TextViewParser textRepair = new TextViewParser();
        textRepair.append("物业报修\n", DPIUtil.dip2px(16), ContextCompat.getColor(mContext, R.color.txt_black));
        textRepair.append("家里水电有问题?迅速报修",  DPIUtil.dip2px(16), ContextCompat.getColor(mContext, R.color.style_color));
        textRepair.parse(binding.homeRepair);
        TextViewParser textStaff = new TextViewParser();
        textStaff.append("小区保安\n", DPIUtil.dip2px(16), ContextCompat.getColor(mContext, R.color.txt_black));
        textStaff.append("紧急情况，呼叫保安",  DPIUtil.dip2px(16), ContextCompat.getColor(mContext, R.color.style_color));
        textStaff.parse(binding.homeStaff);
        TextViewParser textVote = new TextViewParser();
        textVote.append("业主投票\n", DPIUtil.dip2px(16), ContextCompat.getColor(mContext, R.color.txt_black));
        textVote.append("参与小区工作决策",  DPIUtil.dip2px(16), ContextCompat.getColor(mContext, R.color.style_color));
        textVote.parse(binding.homeVote);
        TextViewParser textPost = new TextViewParser();
        textPost.append("小区BBS\n", DPIUtil.dip2px(16), ContextCompat.getColor(mContext, R.color.txt_black));
        textPost.append("发表你的看法",  DPIUtil.dip2px(16), ContextCompat.getColor(mContext, R.color.style_color));
        textPost.parse(binding.homePost);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.home_msg:
                if (!TextUtils.isEmpty(id)) {
                    startActivity(new Intent(mContext, MsgActivity.class).putExtra(KEY.ID, id));
                } else {
                    ToastUtils.show("获取数据失败，请稍后重试");
                }

                break;
            case R.id.home_vote:
                startActivity(new Intent(mContext, VoteListActivity.class).putExtra(KEY.ID, id));
                break;
            case R.id.home_repair:
                startActivity(new Intent(mContext, UpRepairActivity.class).putExtra(KEY.ID, id));
                break;
            case R.id.home_staff:
                startActivity(new Intent(mContext, StaffListActivity.class).putExtra(KEY.ID, id));
                break;
            case R.id.home_opinion:
                startActivity(new Intent(mContext, CommuntySuggestioinActivity.class).putExtra(KEY.ID, id));
                break;
            case R.id.home_post:
                startActivity(new Intent(mContext, BbsListActivity.class).putExtra(KEY.ID, id));
                break;
        }
    }
}
