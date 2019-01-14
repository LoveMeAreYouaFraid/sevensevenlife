package com.example.youxiangshenghuo;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.sevensevenlife.http.MyRequest;
import com.sevensevenlife.interfaces.DialogListener;
import com.sevensevenlife.interfaces.MyHttpCallBack;
import com.sevensevenlife.model.httpmodel.PublicMode;
import com.sevensevenlife.utils.MyApplication;
import com.sevensevenlife.utils.ToastUtils;
import com.sevensevenlife.view.custumview.BaseActivity;
import com.sevensevenlife.view.DiaLog.DialogUtils;
import com.example.youxiangshenghuo.databinding.ActivityCancleorderBinding;

import org.json.JSONObject;

import static com.sevensevenlife.http.RequestUtils.POST;
import static com.sevensevenlife.http.RequestUtils.URI.CANCLE_ORDER;

public class CancleOrderActivity extends BaseActivity implements MyHttpCallBack, View.OnClickListener {

    private ActivityCancleorderBinding binding;
    private String[] list = new String[]{"规定时间内，无理由退款", "决定改服务人员", "已经不再需要服务"};

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_cancleorder);
        binding.comment.setOnClickListener(this);
        binding.title.imgBack.setOnClickListener(this);
        binding.reason.setOnClickListener(this);
        binding.serviceName.setText(getIntent().getStringExtra("service_name"));
        binding.prepayment.setText(getIntent().getStringExtra("prepaymentD") + "元");


    }


    @Override
    public <T> void ok(T backMode, String jsonString, int httpTY) {
        switch (httpTY) {
            case 100:
                try {
                    JSONObject jo = new JSONObject(jsonString);
                    JSONObject jo2 = new JSONObject(jo.getString("header"));
                    if (jo2.getString("message").equals("OK")) {
                        ToastUtils.show("退单成功");
                    } else {
                        ToastUtils.show(jo2.getString("message"));
                    }
                    finish();
                } catch (Exception e) {
                    e.getMessage();
                }
                break;
        }
    }

    @Override
    public void error(String e, int uriType) {
        ToastUtils.show(e);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.comment:
                MyRequest.POST(POST, new String[]{"sessionId", "orderNo", "projectId", "remark"},
                        new String[]{MyApplication.getInstance().getUserInfo().getRows().getSessionId(),
                                getIntent().getStringExtra("order_no"),
                                getIntent().getStringExtra("project_id"),
                                binding.reason.getText().toString()}, CANCLE_ORDER, PublicMode.class.getName(), 100, this);
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.reason:
                 DialogUtils.init(mContext).setTitle(list, new DialogListener() {
                    @Override
                    public void buttType(int ButtType) {
                        binding.reason.setText(list[ButtType]);
                    }
                });

                break;
        }
    }
}
