package com.sevensevenlife.view.Home.MyCommunity;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.View;

import com.sevensevenlife.http.MyRequest;
import com.sevensevenlife.interfaces.BindViewInterface;
import com.sevensevenlife.interfaces.MyHttpCallBack;
import com.sevensevenlife.model.httpmodel.AllCommunityInfoList;
import com.sevensevenlife.model.httpmodel.CommunityInfo;
import com.sevensevenlife.model.KEY;
import com.sevensevenlife.utils.MyApplication;
import com.sevensevenlife.utils.PreferencesUtil;
import com.sevensevenlife.utils.RecyclerViewUtils.PublicAdapter;
import com.sevensevenlife.utils.RecyclerViewUtils.RViewUtils;
import com.sevensevenlife.utils.ToastUtils;
import com.sevensevenlife.view.custumview.BaseActivity;
import com.sevensevenlife.view.DiaLog.MyProgressDialog;
import com.andview.refreshview.XRefreshView;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.MyCommunityListActivityLayoutBinding;
import com.example.youxiangshenghuo.databinding.MyCommunityListItemBinding;

import java.util.ArrayList;
import java.util.List;

import static com.sevensevenlife.http.RequestUtils.GET;
import static com.sevensevenlife.http.RequestUtils.URI.GET_MY_COMMUNITY;

/**
 * Created by Administrator on 2017/11/20 0020.
 */

public class MyCommunityListActivity extends BaseActivity implements View.OnClickListener, MyHttpCallBack
        , BindViewInterface {
    private MyCommunityListActivityLayoutBinding binding;
    private List<CommunityInfo> list = new ArrayList<>();
    private PublicAdapter adapter;
    private String name = "";

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.my_community_list_activity_layout);
        binding.title.title.setText("我的小区");
        binding.title.imgBack.setOnClickListener(this);
        binding.addHome.setOnClickListener(this);

        getMyCommunity();

        adapter = new PublicAdapter<>(list, R.layout.my_community_list_item, this);
        new RViewUtils(mContext, binding.list)
                .setAdapter(adapter);
        binding.list.recyclerView.setBackgroundResource(R.color.white);
        binding.list.xRefreshView.setPullRefreshEnable(true);
        binding.list.xRefreshView.setPullLoadEnable(false);
        binding.list.xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh() {
                super.onRefresh();
                getMyCommunity();
            }
        });
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.add_home:
                startActivityForResult(new Intent(mContext, OtherCommunityListActivity.class)
                        .putExtra(KEY.NAME, name), 102);
                break;

        }

    }

    @Override
    public void bandView(ViewDataBinding binding, final int position) {
        MyCommunityListItemBinding bin = (MyCommunityListItemBinding) binding;
        bin.name.setText(list.get(position).getCommunity_name());
        bin.item.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                //MyApplication.getInstance().setCommunityId(list.get(position).getId());
                PreferencesUtil.putString("communityId", list.get(position).getId());
                startActivity(new Intent(mContext, CommunityHomeActivity.class));
                finish();
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 102:
                    getMyCommunity();
                    break;
            }
        }
    }

    private void getMyCommunity() {
        MyProgressDialog.getInstance().show(mContext, "Loading...");
        MyRequest.POST(GET,
                new String[]{"sessionId"},
                new String[]{MyApplication.getInstance().getUserInfo().getRows().getSessionId()},
                GET_MY_COMMUNITY,
                AllCommunityInfoList.class.getName(),
                61598, this);
    }

    @Override
    public <T> void ok(T backMode, String jsonString, int httpTY) {
        binding.list.xRefreshView.stopRefresh();
        switch (httpTY) {
            case 61598:

                MyProgressDialog.getInstance().cancel();
                try {
                    AllCommunityInfoList mos = (AllCommunityInfoList) backMode;
                    MyApplication.getInstance().setCommunityInfos(mos.getRows());
                    for (int i = 0; i < mos.getRows().size(); i++) {
                        if (i == 0) {
                            name = mos.getRows().get(i).getCommunity_name();
                        } else {
                            name = name + "," + mos.getRows().get(i).getCommunity_name();
                        }
                    }
                    list.clear();
                    list.addAll(MyApplication.getInstance().getCommunityInfos());
                    adapter.notifyDataSetChanged();

                } catch (Exception e) {
                    ToastUtils.show("数据异常，请稍后重试");
                }

                break;
        }
    }

    @Override
    public void error(String e, int uriType) {
        MyProgressDialog.getInstance().cancel();
        ToastUtils.show(e);
        binding.list.xRefreshView.stopRefresh();
    }
}

