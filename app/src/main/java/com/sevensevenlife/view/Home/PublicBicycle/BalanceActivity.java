package com.sevensevenlife.view.Home.PublicBicycle;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.view.View;

import com.sevensevenlife.http.MyRequest;
import com.sevensevenlife.interfaces.MyHttpCallBack;
import com.sevensevenlife.model.httpmodel.Balance;
import com.sevensevenlife.utils.MyApplication;
import com.sevensevenlife.utils.ToastUtils;
import com.sevensevenlife.view.custumview.BaseActivity;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.BalanceActivityLayoutBinding;

import static com.sevensevenlife.http.RequestUtils.GET;
import static com.sevensevenlife.http.RequestUtils.URI.GET_BALANCE;
import static com.sevensevenlife.model.KEY.PHONE;

/**
 * 用户余额
 * Created by 10237 on 2016/12/25.
 */

public class BalanceActivity extends BaseActivity implements View.OnClickListener, MyHttpCallBack {
    private BalanceActivityLayoutBinding binding;
    private Balance balance;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.balance_activity_layout);
        binding.title.imgBack.setOnClickListener(this);
        binding.title.title.setText("租车卡余额");
        new MyRequest().POST(GET, new String[]{}, new String[]{}, GET_BALANCE + "?" + PHONE + "=" +
                MyApplication.getInstance().getUserInfo().getRows().getPhone(), Balance.class.getName(), 0, this);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;

        }

    }

    @Override
    public <T> void ok(T backMode, String jsonString, int httpTY) {
        balance = (Balance) backMode;
        try {
            if (balance.getRows() == null) {
                binding.tvBalance.setText("¥ " + "0.00");
            } else {
                Double totalAmount = Double.valueOf(balance.getRows().getBalance()) +
                        Double.valueOf(balance.getRows().getDeposit());
                binding.tvBalance.setText("¥ " + totalAmount + "");
            }
        } catch (Exception e) {
            binding.tvBalance.setText("查询失败");
        }

    }

    @Override
    public void error(String e, int uriType) {
        ToastUtils.show(e);
    }
}
