package com.sevensevenlife.view.Home.MyCommunity;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;
import android.widget.LinearLayout;

import com.sevensevenlife.http.MyRequest;
import com.sevensevenlife.interfaces.BindViewInterface;
import com.sevensevenlife.interfaces.DialogListener;
import com.sevensevenlife.interfaces.MyHttpCallBack;
import com.sevensevenlife.model.httpmodel.AllCommunityInfoList;
import com.sevensevenlife.model.httpmodel.CommunityInfo;
import com.sevensevenlife.model.httpmodel.PublicMode;
import com.sevensevenlife.model.KEY;
import com.sevensevenlife.utils.MyApplication;
import com.sevensevenlife.utils.RecyclerViewUtils.PublicAdapter;
import com.sevensevenlife.utils.RecyclerViewUtils.RViewUtils;
import com.sevensevenlife.utils.ToastUtils;
import com.sevensevenlife.view.custumview.BaseActivity;
import com.sevensevenlife.view.DiaLog.MyProgressDialog;
import com.sevensevenlife.view.DiaLog.TitleDialog;
import com.andview.refreshview.XRefreshView;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.OtherCommunityListActivityLayoutBinding;
import com.example.youxiangshenghuo.databinding.OtherCommunityListItemBinding;

import java.util.ArrayList;
import java.util.List;

import static com.sevensevenlife.http.RequestUtils.GET;
import static com.sevensevenlife.http.RequestUtils.POST;
import static com.sevensevenlife.http.RequestUtils.URI.ADD_COMMUNITY;
import static com.sevensevenlife.http.RequestUtils.URI.GET_ALL_COMMUNITY;

/**
 * Created by Administrator on 2017/11/20 0020.
 */

public class OtherCommunityListActivity extends BaseActivity implements MyHttpCallBack, View.OnClickListener,
        BindViewInterface, DialogListener {
    private OtherCommunityListActivityLayoutBinding binding;
    private List<CommunityInfo> homeNameList = new ArrayList<>();
    private PublicAdapter adapter;
    private int positions = 0;
    private String communityId = "";
    private String searchWord = "";
    private boolean onModify = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.other_community_list_activity_layout);
        binding.imgBack.setOnClickListener(this);
        binding.myHome.setOnClickListener(this);
        binding.ssBt.setOnClickListener(this);
        binding.list.recyclerView.setBackgroundResource(R.color.white);

        if (getIntent().hasExtra(KEY.NAME)) {
            binding.openHomeValue.setText(getIntent().getStringExtra(KEY.NAME));
        }

        adapter = new PublicAdapter<>(homeNameList, R.layout.other_community_list_item, this);
        new RViewUtils(mContext, binding.list)
                .setLayoutMg(new StaggeredGridLayoutManager(1, LinearLayout.VERTICAL))
                .IsRefreshOrLoad(true)
                .setAdapter(adapter);

        binding.list.xRefreshView.setPullRefreshEnable(true);

        getData();
        binding.list.xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh() {
                super.onRefresh();
                searchWord = "";
                getData();
            }


        });
    }

    @Override
    public <T> void ok(T backMode, String jsonString, int httpTY) {
        MyProgressDialog.getInstance().cancel();
        switch (httpTY) {
            case 12://获取全部小区
                AllCommunityInfoList mo = (AllCommunityInfoList) backMode;
                if (mo.getRows().size() == 0) {
                    ToastUtils.show("暂无数据");
                }

                homeNameList.clear();
                homeNameList.addAll(mo.getRows());
                binding.list.xRefreshView.stopRefresh();

                adapter.notifyDataSetChanged();

                break;

            case 14://添加小区
                onModify = true;
                PublicMode publicMode = (PublicMode) backMode;
                ToastUtils.show(publicMode.getHeader().getMessage());
                break;
        }
    }

    @Override
    public void error(String e, int uriType) {
        binding.list.xRefreshView.stopRefresh();
        MyProgressDialog.getInstance().cancel();
        ToastUtils.show(e);
    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if (onModify) {
                setResult(RESULT_OK);
            }
        }
        return super.onKeyDown(keyCode, event);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                if (onModify) {
                    setResult(RESULT_OK);
                }
                finish();
                break;
            case R.id.ss_bt:
                if (TextUtils.isEmpty(binding.ssEd.getText() + "")) {
                    ToastUtils.show("请输入要搜索的小区");
                    return;
                }
                searchWord = binding.ssEd.getText().toString();
                getData();
                break;
            case R.id.my_home:
                finish();
                break;
        }
    }

    @Override
    public void bandView(ViewDataBinding binding, final int position) {
        OtherCommunityListItemBinding bin = (OtherCommunityListItemBinding) binding;
        bin.homeName.setText(homeNameList.get(position).getCommunity_name());
        bin.homeAdd.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                positions = position;
                new TitleDialog()
                        .SHOW(mContext, new String[]{"确定添加" + homeNameList.get(position).getCommunity_name() + "为默认小区?",
                                "确定", "取消"}, OtherCommunityListActivity.this, true);
            }
        });
    }

    @Override
    public void buttType(int ButtType) {
        if (ButtType == 0) {
            communityId = homeNameList.get(positions).getId();
            MyProgressDialog.getInstance().show(mContext, "Loading...");
            MyRequest.POST(POST,
                    new String[]{"sessionId", "communityId"},
                    new String[]{
                            MyApplication.getInstance().getUserInfo().getRows().getSessionId(),
                            communityId
                    },
                    ADD_COMMUNITY,
                    PublicMode.class.getName(),
                    14,
                    this);
        }

    }


    public void getData() {
        MyProgressDialog.getInstance().show(mContext, "Loading...");
        MyRequest.POST(GET, new String[]{"searchWord"}, new String[]{searchWord},
                GET_ALL_COMMUNITY, AllCommunityInfoList.class.getName(), 12, this);
    }
}
