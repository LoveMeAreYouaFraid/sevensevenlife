package com.sevensevenlife.view.Find;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Typeface;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sevensevenlife.http.OkGetBanner;
import com.sevensevenlife.interfaces.BindViewInterface;
import com.sevensevenlife.interfaces.MyHttpCallBack;
import com.sevensevenlife.model.httpmodel.BannerInfo;
import com.sevensevenlife.model.httpmodel.GetBannerMode;
import com.sevensevenlife.model.KEY;
import com.sevensevenlife.utils.ImgLoadUtils;
import com.sevensevenlife.utils.LogUtils;
import com.sevensevenlife.utils.MyApplication;
import com.sevensevenlife.utils.RecyclerViewUtils.PublicAdapter;
import com.sevensevenlife.utils.RecyclerViewUtils.RViewUtils;
import com.sevensevenlife.utils.ToastUtils;
import com.sevensevenlife.view.custumview.LpWebActivity;
import com.sevensevenlife.view.User.LoginActivity;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.FindFragmentBinding;
import com.example.youxiangshenghuo.databinding.FindListItemBinding;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/1/25 0025.
 */

public class FindFragment extends Fragment implements View.OnClickListener, MyHttpCallBack, BindViewInterface {

    private FindFragmentBinding binding;
    private boolean isCache = true;
    private Context mContext;
    private List<BannerInfo> rows;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.find_fragment, container, false);
        mContext = getActivity();
        rows = new ArrayList<>();
        binding.one.setOnClickListener(this);
        binding.two.setOnClickListener(this);
        binding.three.setOnClickListener(this);
        binding.leavingMessage.setOnClickListener(this);
        binding.list.nullDataView.setVisibility(View.GONE);
        Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(), "fonts/pm.ttf");

        binding.lockTitle.setTypeface(typeFace);
        binding.comprehensiveTitle.setTypeface(typeFace);
        if (MyApplication.getInstance().isLogin()) {
            OkGetBanner.get(4, this, isCache);
        }
        return binding.getRoot();
    }

    @Override
    public void onClick(View v) {
        if (!MyApplication.getInstance().isLogin()) {
            ToastUtils.show("请先登录");
            startActivity(new Intent(getActivity(), LoginActivity.class));
            return;
        }
        switch (v.getId()) {
            case R.id.one:
                startActivity(new Intent(getActivity(), CircleOfFriendsActivity.class));
                break;
            case R.id.two:
                startActivity(new Intent(getActivity(), InvitationCourtesyActivity.class));
                break;
            case R.id.three:
                startActivity(new Intent(getActivity(), RidingRankingActivity.class));
                break;
            case R.id.comprehensive:
                startActivity(new Intent(getActivity(), ComprehensiveActivity.class));
                break;
            case R.id.leaving_message:
                startActivity(new Intent(getActivity(), CdyeeMessageListActivity.class));
                break;
        }
    }

    @Override
    public <T> void ok(T backMode, String jsonString, int httpTY) {
        switch (httpTY) {
            case 4:
                try {
                    GetBannerMode mode = (GetBannerMode) backMode;
                    rows.addAll(mode.getRows());
                    new RViewUtils(mContext, binding.list.recyclerView, binding.list.xRefreshView)
                            .setAdapter(new PublicAdapter<>(rows, R.layout.find_list_item, this));
                } catch (Exception e) {
                    LogUtils.e(123, e.getMessage());
                }

                break;
        }
    }

    @Override
    public void error(String e, int uriType) {
        ToastUtils.show(e);
    }

    @Override
    public void bandView(ViewDataBinding binding, final int position) {
        FindListItemBinding binding1 = (FindListItemBinding) binding;
        binding1.date.setText(rows.get(position).getUpdate_date());
        binding1.title.setText(rows.get(position).getTitle());
        ImgLoadUtils.Load(mContext, rows.get(position).getPic_url(), binding1.comprehensive, false);
        binding1.comprehensive.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, LpWebActivity.class)
                        .putExtra(KEY.URL, rows.get(position).getLink_value()));
            }
        });
    }
}
