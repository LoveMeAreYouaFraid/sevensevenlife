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
import com.example.youxiangshenghuo.databinding.CommunityAuthorityFragmentLayoutBinding;

/**
 * Created by Administrator on 2017/11/14 0014.
 */

public class AuthorityFragment extends Fragment {
    private CommunityAuthorityFragmentLayoutBinding binding;
    private Context mContext;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        mContext = getActivity();
        binding = DataBindingUtil.inflate(inflater, R.layout.community_authority_fragment_layout, container, false);
        binding.authorityBtn.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, AuthenticationActivity.class));
            }
        });
        binding.communityManage.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, MyCommunityListActivity.class));
            }
        });
        binding.authorityData.setText(String.format(getString(R.string.community_authority_data_format),"业主"));
        binding.messageNum.setText("13");
        binding.addBbsData.setText(getString(R.string.community_add_bbs_data));
        binding.addVoteData.setText(getString(R.string.community_add_vote_data));
        binding.myBbsData.setText(String.format(getString(R.string.community_base_data_format),12));
        binding.myVoteData.setText(String.format(getString(R.string.community_my_vote_data_format),5,21));
        binding.communityManageData.setText(String.format(getString(R.string.community_manage_data_format),2));
        return binding.getRoot();
    }


}
