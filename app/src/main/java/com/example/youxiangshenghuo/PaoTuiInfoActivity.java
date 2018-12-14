package com.example.youxiangshenghuo;


import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.sevensevenlife.http.MyRequest;
import com.sevensevenlife.interfaces.MyHttpCallBack;
import com.sevensevenlife.model.httpmodel.PublicMode;
import com.sevensevenlife.model.httpmodel.ServiceDeatil;
import com.sevensevenlife.utils.LogUtils;
import com.sevensevenlife.utils.MyApplication;
import com.sevensevenlife.utils.ToastUtils;
import com.sevensevenlife.view.custumview.BaseActivity;
import com.sevensevenlife.view.Home.CommentListActivity;
import com.sevensevenlife.view.User.LoginActivity;
import com.example.youxiangshenghuo.databinding.ActivityPaotuiInfoBinding;
import com.umeng.analytics.MobclickAgent;
import com.sevensevenlife.utils.MemoryBean;
import com.sevensevenlife.view.custumview.PaoTuiInfoView;

import java.util.HashMap;
import java.util.Map;

import static com.sevensevenlife.http.RequestUtils.GET;
import static com.sevensevenlife.http.RequestUtils.POST;
import static com.sevensevenlife.http.RequestUtils.URI.APP_PHONE_ORDER;
import static com.sevensevenlife.http.RequestUtils.URI.GET_SERVICE_DETAIL;

public class PaoTuiInfoActivity extends BaseActivity implements MyHttpCallBack, View.OnClickListener {
    private PaoTuiInfoView ptiv;
    private String paotuiName;
    private ServiceDeatil deatil;
    private ActivityPaotuiInfoBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_paotui_info);
        binding.title.imgBack.setOnClickListener(this);
        binding.title.title.setText("详情页");
        binding.goCall.setOnClickListener(this);
        MyRequest.POST(GET, new String[]{"serviceId", "longitude", "latitude"}, new String[]{
                getIntent().getStringExtra("id"),
                MemoryBean.Longitude + "",
                MemoryBean.Latitude + ""
        }, GET_SERVICE_DETAIL, ServiceDeatil.class.getName(), 101, this);
        ptiv = new PaoTuiInfoView(binding);
        binding.bottom.fwzsLinearlayout.setVisibility(View.VISIBLE);

    }


    public void goPingLunList(View v) {
        Intent intent = new Intent(this, CommentListActivity.class);
        startActivity(intent);
    }

    public void FuwuLiebiao(View v) {
        findViewById(R.id.fwlb_id).setBackgroundResource(R.drawable.bt_huatiao_xm);
        findViewById(R.id.fwzs_id).setBackgroundResource(R.drawable.huatiao_fw);
        binding.bottom.fwzsLinearlayout.setVisibility(View.GONE);
    }

    public void FuwuZhanShi(View v) {
        findViewById(R.id.fwlb_id).setBackgroundResource(R.drawable.huatiao_xm);
        findViewById(R.id.fwzs_id).setBackgroundResource(R.drawable.bt_huatiao_fw);
        binding.bottom.fwzsLinearlayout.setVisibility(View.VISIBLE);
    }


    public void goYuYue(View v) {
        Intent intent;
        if (MyApplication.getInstance().isLogin()) {
            intent = new Intent(this, YuYueActivity.class);
            intent.putExtra("paotuiName", paotuiName);
            Map<String, String> map_value = new HashMap<>();
            map_value.put("type", paotuiName);
            MobclickAgent.onEvent(mContext, "11", map_value);
        } else {
            intent = new Intent(this, LoginActivity.class);
        }
        startActivity(intent);
    }

    public void goXiaDan(View v) {
        Map<String, String> map_value = new HashMap<>();
        map_value.put("type", paotuiName);
        MobclickAgent.onEvent(mContext, "12", map_value);
        if (getIntent().getStringExtra("workStatus").equals("1")) {
            Intent intent;
            if (MyApplication.getInstance().isLogin()) {
                intent = new Intent(this, YuYueActivity.class);
                intent.putExtra("paotuiName", paotuiName);
                intent.putExtra("isXiaDan", "true");
            } else {
                intent = new Intent(this, LoginActivity.class);
            }
            startActivity(intent);
        } else {
            ToastUtils.show("服务人员正忙，请选择其他空闲服务人员或预约");
        }
    }

    @Override
    public <T> void ok(T backMode, String jsonString, int httpTY) {
        try {
            switch (httpTY) {
                case 101:
                    try {
                        deatil = (ServiceDeatil) backMode;
                        paotuiName = deatil.getRows().getRealName();
                        ptiv.setTopBottomValue(mContext, deatil);
                        ptiv.setCenterValue(mContext, deatil);
                        ptiv.initGalleryView(mContext, deatil.getRows().getIntroductPhotos());
                    } catch (Exception e) {
                        e.printStackTrace();
                    }
                    break;
                case 19:

                    break;
            }
        } catch (Exception e) {
            LogUtils.e(123, e.getMessage());
        }
    }

    @Override
    public void error(String e, int uriType) {
        ToastUtils.show(e);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.go_call:
                Intent intent = new Intent();
                intent.setAction("android.intent.action.DIAL");
                intent.setData(Uri.parse("tel:" + getIntent().getStringExtra("phone")));
                startActivity(intent);
                MyRequest.POST(POST, new String[]{"sessionId", "serviceId", "projectId"}, new String[]{
                        MyApplication.getInstance().getUserInfo().getRows().getSessionId(),
                        getIntent().getStringExtra("id"),
                        deatil.getRows().getAppraisalList().get(0).getId() + ""
                }, APP_PHONE_ORDER, PublicMode.class.getName(), 19, this);
                break;
        }
    }
}
