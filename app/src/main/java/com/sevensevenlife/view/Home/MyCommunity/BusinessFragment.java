package com.sevensevenlife.view.Home.MyCommunity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.example.youxiangshenghuo.R;
import com.sevensevenlife.model.KEY;
import com.sevensevenlife.utils.MyApplication;
import com.sevensevenlife.view.Home.MyHome.CommuntySuggestioinActivity;
import com.sevensevenlife.view.Home.MyHome.StaffListActivity;
import com.sevensevenlife.view.Home.MyHome.UpRepairActivity;
import com.sevensevenlife.view.Home.MyHome.VoteListActivity;
import com.example.youxiangshenghuo.databinding.CommunityBusinessFragmentLayoutBinding;

/**
 * Created by Administrator on 2017/11/14 0014.
 */

public class BusinessFragment extends Fragment implements View.OnClickListener {
    private CommunityBusinessFragmentLayoutBinding binding;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        binding = DataBindingUtil.inflate(inflater, R.layout.community_business_fragment_layout, container, false);
        binding.communityPhoto.setOnClickListener(this);
        binding.notice.setOnClickListener(this);
        binding.vote.setOnClickListener(this);
        binding.security.setOnClickListener(this);
        binding.repair.setOnClickListener(this);
        binding.suggestion.setOnClickListener(this);
        binding.noticeTitle.setText("停电通知");
        binding.noticeTime.setText("2017-06-16 11:53:53");
        binding.noticeContent.setText("因物业施工，5月23日上午10点断电通知因物业施工，5月23日上午。电通知因物业施工，5电通知因物业施工，5电通知因物业施工，5");
        binding.bbsData.setText(String.format(getString(R.string.community_base_data_format),89));
        binding.voteData.setText(String.format(getString(R.string.community_base_data_format),56));
        binding.securityData.setText(String.format(getString(R.string.community_security_data_format),8));
        binding.repairData.setText(String.format(getString(R.string.community_repair_data_format),23));
        binding.suggestionData.setText(String.format(getString(R.string.community_suggestion_data_format),17));
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.community_photo:
                //startActivity(new Intent(mContext, OnlineFaceDemo.class));
                break;
            case R.id.notice:
                startActivity(new Intent(mContext, NoticeListActivity.class).putExtra(KEY.ID, MyApplication.getInstance().getCommunityId()));
                break;
            case R.id.vote:
                startActivity(new Intent(mContext, VoteListActivity.class).putExtra(KEY.ID, MyApplication.getInstance().getCommunityId()));
                break;
            case R.id.security:
                startActivity(new Intent(mContext, StaffListActivity.class).putExtra(KEY.ID, MyApplication.getInstance().getCommunityId()));
                break;
            case R.id.repair:
                startActivity(new Intent(mContext, UpRepairActivity.class).putExtra(KEY.ID, MyApplication.getInstance().getCommunityId()));
                break;
            case R.id.suggestion:
                startActivity(new Intent(mContext, CommuntySuggestioinActivity.class).putExtra(KEY.ID, MyApplication.getInstance().getCommunityId()));
                break;
        }
    }
}
