package com.example.youxiangshenghuo;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.View;

import com.sevensevenlife.model.httpmodel.OrderListItemMode;
import com.sevensevenlife.utils.ImgLoadUtils;
import com.sevensevenlife.utils.LogUtils;
import com.sevensevenlife.utils.ToastUtils;
import com.sevensevenlife.view.custumview.BaseActivity;
import com.sevensevenlife.view.Order.PayActivity;
import com.sevensevenlife.view.Order.PingFenActivity;
import com.alipay.sdk.pay.PayDemoActivity;
import com.example.youxiangshenghuo.databinding.ActivityDingdanInfoBinding;
import com.sevensevenlife.utils.MemoryBean;

public class DingDanInfo2Activity extends BaseActivity implements View.OnClickListener {


    private final int YIJIEDAN = 0, YISONGDA = 1, YIZHIFU = 2;
    private int status = YIJIEDAN;
    private String[] orderStrings = new String[]{"待付款", "执行中", "待评价", "待付款", "待评价", "已评价", "爽约", "取消"};

    /**
     * 退款金额
     **/
    private String tuiKuanJinE = "";

    private ActivityDingdanInfoBinding binding;

    private OrderListItemMode mode;

//    private OrderDetailMode detailMode;

    private String OrderNo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_dingdan_info);
        binding.title.title.setText("订单详情");
        binding.title.imgBack.setOnClickListener(this);
        binding.quxiaoyuyue.setOnClickListener(this);
        mode = (OrderListItemMode) getIntent().getSerializableExtra("orderInfo");
        if (getIntent().hasExtra("OrderNo")) {
            OrderNo = getIntent().getStringExtra("OrderNo");
        } else {
            ToastUtils.show("数据获取失败，请稍后重试。");
            return;
        }

        if (mode.getFinish_status().equals("-1")) {
            mode.setFinish_status("6");
        }
        if (mode.getFinish_status().equals("-2")) {
            mode.setFinish_status("7");
        }
        binding.finishStatus.setText("订单状态：" + orderStrings[Integer.valueOf(mode.getFinish_status())]);
        typeView();

        if (mode.getFinish_status().equals("0")) {
            binding.quxiaoyuyue.setVisibility(View.VISIBLE);
            binding.tvPay.setVisibility(View.VISIBLE);
            binding.typeOne.tvTxt.setTextColor(ContextCompat.getColor(mContext, R.color.style_color));
            binding.typeOne.viewYuan.setBackgroundResource(R.drawable.bg_hui_fillet);
        } else if (mode.getFinish_status().equals("1")) {
            binding.quxiaoyuyue.setVisibility(View.VISIBLE);
            binding.cuidan.setVisibility(View.VISIBLE);
            binding.typeTwo.tvTxt.setTextColor(ContextCompat.getColor(mContext, R.color.style_color));
            binding.typeTwo.tvTxt.setText("执行中");
            binding.typeTwo.viewYuan.setBackgroundResource(R.drawable.bg_hui_fillet);
        } else {
            binding.pingjiaAnniu.setVisibility(View.VISIBLE);
            binding.typeThree.tvTxt.setTextColor(ContextCompat.getColor(mContext, R.color.style_color));
            binding.typeThree.viewYuan.setBackgroundResource(R.drawable.bg_hui_fillet);
            binding.typeThree.tvTxt.setText("已完成");
        }

        MemoryBean.projectName = mode.getProject_name();
        binding.layoutThree.pickPhone2.setText("联系电话：" + mode.getPick_phpne());
        binding.layoutThree.contactPhone2.setText("联系电话：" + mode.getContact_phone());
        binding.layoutThree.pickAddress2.setText("取件地址：" + mode.getPick_address());
        binding.layoutThree.deliverAddress2.setText("收件地址：" + mode.getDeliver_address());
        binding.createDate.setText(mode.getCreate_date());
        binding.layoutTwo.distance.setText("距离：" + mode.getDistance());
        binding.layoutTwo.projectName.setText("服务类型：" + mode.getProject_name());
        binding.layoutTwo.paoduiName.setText(mode.getService_name());
        binding.layoutTwo.prepayment.setText(mode.getPrice());
        if (!TextUtils.isEmpty(mode.getService_pic())) {
            ImgLoadUtils.Load(mContext, mode.getService_pic(), binding.layoutTwo.topImage, true);
        }

    }


    public void goPayDialog(View v) {
        if (status == YISONGDA) {
            startActivity(new Intent(mContext, PayActivity.class));
        } else {
            ToastUtils.show("请等待送达");
        }
    }


    public void goAliay() {
        Intent intent = new Intent(this, PayDemoActivity.class);
        intent.putExtra("orderNo", mode.getOrder_no());
        intent.putExtra("notifyUrl", mode.getPre_notify_Url());
        intent.putExtra("paotuiName", mode.getService_name());
        intent.putExtra("couponMoney", "0");
        startActivity(intent);
        finish();
    }

    public void pingFen(View v) {
        Intent intent = new Intent(mContext, PingFenActivity.class);
        Bundle bundle = new Bundle();
        bundle.putSerializable("orderInfo", mode);
        intent.putExtras(bundle);
        mContext.startActivity(intent);
        finish();
    }


    public void lianxiShangJia(View v) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.CALL");
        intent.setData(Uri.parse("tel:" + mode.getContact_phone()));
        startActivity(intent);//方法内部自动为intent添加类别android.intent.category.DEFAULT
    }

    public void cuidan(View v) {
        sendMessage(mode.getContact_phone(),
                getResources().getString(R.string.contact_phone_content));
    }

    public void sendMessage(String number, String message) {
        Uri smsToUri = Uri.parse("smsto:" + number);
        Intent intent = new Intent(android.content.Intent.ACTION_SENDTO, smsToUri);
        intent.putExtra("sms_body", message);
        startActivity(intent);
    }

    private void typeView() {
        binding.typeOne.tvTxt.setTextColor(ContextCompat.getColor(mContext, R.color.txt_black));
        binding.typeOne.viewYuan.setBackgroundResource(R.drawable.bg_hui_fillet);

        binding.typeTwo.tvTxt.setTextColor(ContextCompat.getColor(mContext, R.color.txt_black));
        binding.typeTwo.tvTxt.setText("执行中");
        binding.typeTwo.viewYuan.setBackgroundResource(R.drawable.bg_hui_fillet);

        binding.typeThree.tvTxt.setTextColor(ContextCompat.getColor(mContext, R.color.txt_black));
        binding.typeThree.viewYuan.setBackgroundResource(R.drawable.bg_hui_fillet);
        binding.typeThree.tvTxt.setText("已完成");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.quxiaoyuyue:
                LogUtils.e(111, "mode.getService_name()=" + mode.getService_name() + "\n"
                        + "order_no=" + mode.getOrder_no() + "\n" +
                        "project_id=" + mode.getProject_id() + "\n" +
                        "prepaymentD=" + tuiKuanJinE);
                Intent intent = new Intent(this, CancleOrderActivity.class);
                intent.putExtra("service_name", mode.getService_name());
                intent.putExtra("order_no", mode.getOrder_no());

                intent.putExtra("project_id", mode.getProject_id());
                intent.putExtra("prepaymentD", tuiKuanJinE);

                startActivity(intent);
                break;
        }
    }
}
