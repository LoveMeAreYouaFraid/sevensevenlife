package com.sevensevenlife.view.User;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.View;

import com.sevensevenlife.http.MyNewRequest;
import com.sevensevenlife.http.MyRequest;
import com.sevensevenlife.interfaces.BindViewInterface;
import com.sevensevenlife.interfaces.DialogListener;
import com.sevensevenlife.interfaces.MyHttpCallBack;
import com.sevensevenlife.model.httpmodel.MyCouponListMode;
import com.sevensevenlife.model.httpmodel.PublicMode;
import com.sevensevenlife.utils.LogUtils;
import com.sevensevenlife.utils.MyApplication;
import com.sevensevenlife.utils.RecyclerViewUtils.PublicAdapter;
import com.sevensevenlife.utils.RecyclerViewUtils.RViewUtils;
import com.sevensevenlife.utils.ToastUtils;
import com.sevensevenlife.view.custumview.BaseActivity;
import com.sevensevenlife.view.DiaLog.DialogUtils;
import com.sevensevenlife.view.DiaLog.MyProgressDialog;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.CouponListItemBinding;
import com.example.youxiangshenghuo.databinding.ListActivityBinding;

import java.util.ArrayList;
import java.util.List;

import static com.sevensevenlife.http.RequestUtils.POST;
import static com.sevensevenlife.http.RequestUtils.URI.GET_MY_COUPON_LIST;
import static com.sevensevenlife.http.RequestUtils.URI.USE_COUPON;

/**
 * Created by Administrator on 2017/3/20 0020.
 */

public class CouponListActivity extends BaseActivity implements MyHttpCallBack, View.OnClickListener
        , BindViewInterface {
    private ListActivityBinding binding;
    private List<MyCouponListMode.RowsBean> list;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.list_activity);
        binding.title.imgBack.setOnClickListener(this);
        binding.title.title.setText("优惠卷");
        list = new ArrayList<>();
        if (isLogin) {
            MyProgressDialog.getInstance().show(mContext, "Loading....");
            MyRequest.POST(POST, new String[]{"sessionId"}, new String[]{
                            MyApplication.getInstance().getUserInfo().getRows().getSessionId()},
                    GET_MY_COUPON_LIST, MyCouponListMode.class.getName(), 10, this);
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }

    @Override
    public <T> void ok(T backMode, String jsonString, int httpTY) {
        try {
            MyProgressDialog.getInstance().cancel();
            MyCouponListMode mode = (MyCouponListMode) backMode;
            switch (httpTY) {
                case 10:
                    list.clear();
                    list.addAll(mode.getRows());
                    new RViewUtils(mContext, binding.list.recyclerView, binding.list.xRefreshView)
                            .setAdapter(new PublicAdapter<>(list, R.layout.coupon_list_item, this));
                    if (list.size() == 0) {
                        ToastUtils.show("暂无优惠卷");
                    }
                    break;

            }
        } catch (Exception e) {
            LogUtils.e(123, e.getMessage());
        }
    }

    @Override
    public void error(String e, int uriType) {
        ToastUtils.show(e);
        MyProgressDialog.getInstance().cancel();
    }

    @Override
    public void bandView(ViewDataBinding binding, final int position) {
        CouponListItemBinding binding1 = (CouponListItemBinding) binding;
        binding1.couponName.setText(list.get(position).getCoupon_name());
        binding1.shopName.setText("店名：" + list.get(position).getShop_name());
        binding1.couponDesc.setText(list.get(position).getCoupon_desc());
        binding1.endTime.setText("过期时间：" + list.get(position).getEnd_time());
        binding1.couponCode.setText("优惠码：" + list.get(position).getCoupon_code());
        if (list.get(position).getIs_used().equals("0")) {
            binding1.tvApply.setVisibility(View.VISIBLE);
        } else {
            binding1.tvApply.setVisibility(View.GONE);
        }
        binding1.tvApply.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.init(mContext)
                        .setTitle("是否使用?")
                        .setOne("使用", new DialogListener() {
                            @Override
                            public void buttType(int ButtType) {
                                MyNewRequest.getInstance()
                                        .setApiUrl(USE_COUPON)
                                        .setClassName(PublicMode.class.getName())
                                        .setKey("sessionId", "id")
                                        .setValue(MyApplication.getInstance().getUserInfo().getRows().getSessionId()
                                                , list.get(position).getId() + "")
                                        .setCache(false)
                                        .setCallBacks(new MyHttpCallBack() {
                                            @Override
                                            public <T> void ok(T backMode, String jsonString, int httpTY) {
                                                ToastUtils.show("使用成功！");
                                                MyProgressDialog.getInstance().show(mContext, "Loading....");
                                                MyRequest.POST(POST, new String[]{"sessionId"}, new String[]{
                                                                MyApplication.getInstance().getUserInfo().getRows().getSessionId()},
                                                        GET_MY_COUPON_LIST, MyCouponListMode.class.getName(), 10, this);
                                            }

                                            @Override
                                            public void error(String e, int uriType) {
                                                ToastUtils.show(e);
                                            }
                                        })
                                        .POST();
                            }
                        }).setTwo("取消", null);


            }
        });


        binding1.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {

            }
        });
    }
}
