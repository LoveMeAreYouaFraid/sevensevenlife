package com.sevensevenlife.view.Order;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.sevensevenlife.http.MyRequest;
import com.sevensevenlife.interfaces.MyHttpCallBack;
import com.sevensevenlife.model.httpmodel.AilPayMode;
import com.sevensevenlife.model.httpmodel.addCustOrderMode;
import com.sevensevenlife.model.KEY;
import com.sevensevenlife.utils.LogUtils;
import com.sevensevenlife.utils.MyApplication;
import com.sevensevenlife.utils.PayUtils;
import com.sevensevenlife.utils.TextViewParser;
import com.sevensevenlife.utils.ToastUtils;
import com.sevensevenlife.view.custumview.BaseActivity;
import com.alipay.sdk.pay.PayDemoActivity;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.PayActivityLayoutBinding;
import com.sevensevenlife.utils.MemoryBean;

import static com.sevensevenlife.http.RequestUtils.GET;
import static com.sevensevenlife.http.RequestUtils.URI.DOWN_ORDER;

/**
 * Created by Administrator on 2017/4/13 0013.
 */

public final class PayActivity extends BaseActivity implements View.OnClickListener, MyHttpCallBack {
    private PayActivityLayoutBinding binding;

    private addCustOrderMode mode;
    private double CouponNum = 0.0;
    public static boolean btPaycK = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.pay_activity_layout);
        binding.title.imgBack.setOnClickListener(this);
        binding.layoutWx.setOnClickListener(this);
        binding.layoutZfb.setOnClickListener(this);
        binding.btPay.setOnClickListener(this);
        if (getIntent().hasExtra(KEY.ORDER_MODE)) {
            try {
                mode = (addCustOrderMode) getIntent().getSerializableExtra(KEY.ORDER_MODE);
                CouponNum = Double.valueOf(mode.getRows().getOrgi_price()) - Double.valueOf(mode.getRows().getPrice());
                if (CouponNum != 0) {
                    binding.couponLayout.setVisibility(View.VISIBLE);
                    binding.tvCoupon.setVisibility(View.VISIBLE);
                    binding.tvCouponNum.setText("￥" + CouponNum + "");
                    binding.price.setVisibility(View.VISIBLE);
                    new TextViewParser()
                            .setTxt("七七" + mode.getRows().getProjectName() + "优惠卷\n", 48, R.color.color_717171)
                            .setTxt("长期\n", 30, R.color.c0c0c0)
                            .setTxt(mode.getRows().getProjectName() + "项专用\n", 30, R.color.c0c0c0)
                            .parse(binding.tvCouponDetail);
                    new TextViewParser()
                            .setTxt("￥" + mode.getRows().getOrgi_price() + "\n", 48, R.color.style_color)
                            .setTxt("￥" + mode.getRows().getPrice(), 48, R.color.style_color)
                            .parse(binding.payNum);
                } else {
                    new TextViewParser()
                            .setTxt("￥" + mode.getRows().getPrice(), 48, R.color.style_color)
                            .parse(binding.payNum);
                }

//                ToastUtils.show(mode.getRows().getPrice());
            } catch (Exception e) {
                LogUtils.e(123, e.getMessage());
            }
        }

    }

    @Override
    public void onClick(View v) {
        try {

            switch (v.getId()) {
                case R.id.img_back:
                    finish();
                    break;
                case R.id.layout_wx:
                    binding.ckWx.setChecked(true);
                    binding.ckZfb.setChecked(false);
                    break;
                case R.id.layout_zfb:
                    binding.ckWx.setChecked(false);
                    binding.ckZfb.setChecked(true);
                    break;
                case R.id.bt_pay:
                    if (!btPaycK) {
                        btPaycK = true;
                        if (binding.ckWx.isChecked()) {
                            if (!MyApplication.getInstance().isLoding()) {
                                ToastUtils.show("请等待片刻");
//                        ToastUtils.show(mode.getRows().getOrderNo());
                                if (mode == null || mode.getRows() == null) {
                                    ToastUtils.show("订单获取失败，请稍后重试");
                                    return;
                                }
                                PayUtils.goPAY(mContext, mode.getRows().getOrderNo());
                                MyApplication.getInstance().setLoding(false);
                            }
                        } else {
                            if (!MyApplication.getInstance().isLoding()) {
                                MyRequest.POST(GET, new String[]{"sessionId", "orderNo", "payType"}, new String[]{
                                        MyApplication.getInstance().getUserInfo().getRows().getSessionId(),
                                        mode.getRows().getOrderNo(), "1"
                                }, DOWN_ORDER, AilPayMode.class.getName(), 111, new MyHttpCallBack() {
                                    @Override
                                    public <T> void ok(T backMode, final String jsonString, int httpTY) {
                                        Intent intent = new Intent(mContext, PayDemoActivity.class);
                                        intent.putExtra("orderNo", mode.getRows().getOrderNo());
                                        intent.putExtra("notifyUrl", mode.getRows().getNotifyUrl());
                                        intent.putExtra("paotuiName", mode.getRows().getProjectName());
                                        intent.putExtra("couponMoney", mode.getRows().getOrgi_price());
                                        MemoryBean.prepayment = mode.getRows().getPrice();
                                        startActivity(intent);
                                        PayActivity.btPaycK = false;
                                    }

                                    @Override
                                    public void error(String e, int uriType) {
                                        ToastUtils.show(e);
                                    }
                                });

//                        new PayAliUtils().goPAY(mContext, mode.getRows().getOrderNo(), this);
                            }
                        }

                    }

                    break;
            }
        } catch (Exception e) {
            LogUtils.e(123, e.getMessage());
        }
    }

    @Override
    public <T> void ok(T backMode, String jsonString, int httpTY) {
        switch (httpTY) {
            case 99:

                break;
        }
    }

    @Override
    public void error(String e, int uriType) {

    }
}
