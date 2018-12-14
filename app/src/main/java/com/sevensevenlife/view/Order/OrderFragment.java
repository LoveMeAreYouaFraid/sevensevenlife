package com.sevensevenlife.view.Order;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentPagerAdapter;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sevensevenlife.interfaces.DialogListener;
import com.sevensevenlife.utils.MyApplication;
import com.sevensevenlife.view.DiaLog.DialogUtils;
import com.sevensevenlife.view.User.LoginActivity;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.OrderFragmentViewBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/25 0025.
 */

public class OrderFragment extends Fragment {


    private OrderFragmentViewBinding binding;

    private List<Fragment> fragmentList;

    private List<String> mDatas;


    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.order_fragment_view, container, false);
        initData();
        if (!MyApplication.getInstance().isLogin()) {
            DialogUtils.init(getActivity())
                    .setTitle("尚未登陆，是否登录?")
                    .setOne("登录", new DialogListener() {
                        @Override
                        public void buttType(int ButtType) {
                            startActivityForResult(new Intent(getActivity(), LoginActivity.class), 99);
                        }
                    });
        }

        return binding.getRoot();
    }

    private void initData() {
        fragmentList = new ArrayList<>();
        mDatas = new ArrayList<>();
        fragmentList.add(OrderFragmentItemNoPay.getInstance("0"));
        fragmentList.add(OrderFragmentItems.getInstance("1"));
        fragmentList.add(OrderFragmentItemComplete.getInstance("2"));
        mDatas.add("待付款");
        mDatas.add("执行中");
        mDatas.add("已完成");

        binding.idIndicator.setTabItemTitles(mDatas);

        binding.mViewPager.setAdapter(new FragmentPagerAdapter(getChildFragmentManager()) {
            @Override
            public Fragment getItem(int position) {
                return fragmentList.get(position);
            }

            @Override
            public int getCount() {
                return fragmentList.size();
            }
        });
        binding.idIndicator.setViewPager(binding.mViewPager, 0);
        binding.mViewPager.setOffscreenPageLimit(3);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            if (requestCode == 99) {
                initData();
            }
        }
    }
}
