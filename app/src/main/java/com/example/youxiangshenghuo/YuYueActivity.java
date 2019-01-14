package com.example.youxiangshenghuo;

import java.io.File;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.List;

import com.sevensevenlife.http.MyRequest;
import com.sevensevenlife.http.UpImg;
import com.sevensevenlife.interfaces.HttpCallBack;
import com.sevensevenlife.interfaces.MyHttpCallBack;
import com.sevensevenlife.model.httpmodel.addCustOrderMode;
import com.sevensevenlife.model.KEY;
import com.sevensevenlife.utils.ImgChoiceUtils;
import com.sevensevenlife.utils.ImgLoadUtils;
import com.sevensevenlife.utils.MyApplication;
import com.sevensevenlife.utils.ToastUtils;
import com.sevensevenlife.view.custumview.BaseActivity;
import com.sevensevenlife.view.Order.PayActivity;
import com.example.youxiangshenghuo.databinding.ActivityYuyueBinding;
import com.sevensevenlife.view.custumview.tools.ScreenInfo;
import com.sevensevenlife.view.custumview.tools.WheelMain;
import com.sevensevenlife.utils.MemoryBean;

import android.app.Dialog;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.TextView;

import static com.sevensevenlife.http.RequestUtils.POST;
import static com.sevensevenlife.http.RequestUtils.URI.ADD_CUST_ORDER;

public class YuYueActivity extends BaseActivity implements View.OnClickListener, MyHttpCallBack {

    WheelMain wheelMain;
    int year, month, day, hour, min;
    LayoutInflater inflater;
    Dialog dialog;
    private boolean isShowFuWuAddress = false;

    private ActivityYuyueBinding binding;

    private ImgChoiceUtils imgChoiceUtils;

    private int picNum = 0;

    private List<String> pics = new ArrayList<>();

//    private UserCouponMode userCouponMode;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_yuyue);
        imgChoiceUtils = ImgChoiceUtils.isNew(this);
        imgChoiceUtils.isCamera(true);
        inflater = LayoutInflater.from(this);
        binding.layoutTwo.imageA.setOnClickListener(this);
        binding.layoutTwo.imageB.setOnClickListener(this);
        binding.layoutTwo.imageC.setOnClickListener(this);
        binding.title.imgBack.setOnClickListener(this);
        binding.title.title.setText("订单");
        binding.prepayment.setText(MemoryBean.prepayment);
        String phone = MyApplication.getInstance().getUserInfo().getRows().getPhone();
        binding.layoutOne.pickPhpne.setText(phone);
        binding.layoutOne.contactPhone.setText(phone);
        binding.yuyue.setOnClickListener(this);


        if (getIntent().hasExtra("isXiaDan")) {
            if (getIntent().getStringExtra("isXiaDan").equals("true")) {
                binding.layoutOne.serviceDateId.setVisibility(View.GONE);
                binding.yuyue.setText("下单");
            }
        }


        String typecode = MemoryBean.typecode;
        if (typecode.equals("0102")
                || typecode.equals("0201")
                || typecode.equals("0202")) {

        } else {
            findViewById(R.id.fwdz_linearlayout).setVisibility(View.VISIBLE);
            findViewById(R.id.sjdz_linearlayout).setVisibility(View.GONE);
            findViewById(R.id.sjdz_linearlayout_line).setVisibility(View.GONE);

            findViewById(R.id.songjdz_linearlayout).setVisibility(View.GONE);
            findViewById(R.id.songjdz_linearlayout_line).setVisibility(View.GONE);

            findViewById(R.id.shoujiandianhua_linearlayout).setVisibility(View.GONE);
            findViewById(R.id.shoujiandianhua_linearlayout_line).setVisibility(View.GONE);


            ((TextView) findViewById(R.id.songjiandianhua)).setText("联系电话：");
        }
        initDate();
    }

    public void initDate() {
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH);
        day = calendar.get(Calendar.DAY_OF_MONTH);
        hour = calendar.get(Calendar.HOUR_OF_DAY);
        min = calendar.get(Calendar.MINUTE);
        binding.layoutOne.serviceDate.setText(year + "." + (month + 1) + "." + day + " " + hour + ":" + min);
    }


    public void showTime(View v) {

        View timepickerview = inflater.inflate(
                R.layout.dialog_timepicker, null);
        ScreenInfo screenInfo = new ScreenInfo(this);

        wheelMain = new WheelMain(timepickerview, 0);
        wheelMain.screenheight = screenInfo.getHeight();
        wheelMain.initDateTimePicker(year, month, day, hour, min);

        dialog = new Dialog(this, R.style.FullHeightDialog);
        dialog.setContentView(timepickerview);
        dialog.show();
    }


    public void submitThis(View v) {
        binding.layoutOne.serviceDate.setText(wheelMain.getTime());
        dialog.dismiss();
    }

    public void cancalThis(View v) {
        dialog.dismiss();
    }


    public void getCoupon() {
        String deliverAddress;
        if (isShowFuWuAddress) {
            deliverAddress = binding.layoutOne.fwAddress.getText().toString();
        } else {
            deliverAddress = binding.layoutOne.deliverAddress.getText().toString();
        }
        for (int i = 0; i < pics.size(); i++) {
            if (i == 0) {
                imageUrl = pics.get(0);
            } else {
                imageUrl = imageUrl + "," + pics.get(i);
            }
        }
        if (TextUtils.isEmpty(MemoryBean.prepayment)) {
            ToastUtils.show("数据获取失败，请稍后再试");
            return;
        }
        MyRequest.POST(POST, new String[]{
                        "sessionId",
                        "serviceId",
                        "projectId",
                        "deliverAddress",
                        "contactPhone",
                        "pickAddress",
                        "pickPhpne",
                        "description",
                        "photo",
                        "reserveTime",
                        "prepayment"
                }, new String[]{
                        MyApplication.getInstance().getUserInfo().getRows().getSessionId(),
                        MemoryBean.serviceID,
                        MemoryBean.Child_projectID,
                        deliverAddress,
                        binding.layoutOne.contactPhone.getText() + "",
                        binding.layoutOne.pickAddress.getText() + "",
                        binding.layoutOne.pickPhpne.getText() + "",
                        binding.layoutTwo.description.getText() + "",
                        imageUrl,
                        binding.layoutOne.serviceDate.getText() + "",
                        MemoryBean.prepayment

                }, ADD_CUST_ORDER,
                addCustOrderMode.class.getName(), 101, this);
//        MyRequest.POST(GET, new String[]{"sessionId", "projectId"}, new String[]{
//                MyApplication.getInstance().getUserInfo().getRows().getSessionId(),
//                MemoryBean.projectID}, GET_USER_COUPON, UserCouponMode.class.getName(), 100, this);
    }


    String imageUrl = "";


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.image_a:
                picNum = 0;
                if (pics.size() >= 1) {
                    pics.remove(0);
                }
                imgChoiceUtils.show();
                break;
            case R.id.image_b:
                picNum = 1;
                if (pics.size() >= 2) {
                    pics.remove(1);
                }
                imgChoiceUtils.show();
                break;
            case R.id.image_c:
                picNum = 2;
                if (pics.size() >= 3) {
                    pics.remove(2);
                }
                imgChoiceUtils.show();
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.yuyue:
                //服务地址显示
                if (binding.layoutOne.fwdzLinearlayout.getVisibility() == View.VISIBLE) {
                    isShowFuWuAddress = true;
                    if (binding.layoutOne.fwAddress.getText().toString().equals("")
                            || binding.layoutOne.contactPhone.getText().toString().equals("")
                            ) {
                        ToastUtils.show("不能为空");
                    } else {
                        getCoupon();
                    }
                } else {
                    //取件地址，收件地址
                    if (binding.layoutOne.pickAddress.getText().toString().equals("") ||
                            binding.layoutOne.deliverAddress.getText().toString().equals("") ||
                            binding.layoutOne.contactPhone.getText().toString().equals("") ||
                            binding.layoutOne.pickPhpne.getText().toString().equals("")) {
                        ToastUtils.show("不能为空");
                    } else {
                        getCoupon();
                    }
                }
                break;
        }

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        imgChoiceUtils.onPermissions(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        File file = imgChoiceUtils.onActivityResult(requestCode, resultCode, data);
        if (file != null) {
            upimg(file);
        }

    }


    @Override
    public <T> void ok(T backMode, String jsonString, int httpTY) {
        switch (httpTY) {
            case 100:
//                userCouponMode = (UserCouponMode) backMode;
//                if (!TextUtils.isEmpty(userCouponMode.getRows().getCouponId())) {
//                }
                break;
            case 101:
                addCustOrderMode mode = (addCustOrderMode) backMode;
                Intent intent = new Intent(this, PayActivity.class);//PayDemoActivity
                intent.putExtra(KEY.ORDER_MODE, mode);
                startActivity(intent);
                finish();
                break;
        }

    }

    @Override
    public void error(String e, int uriType) {
        ToastUtils.show(e);
    }


    private void upimg(final File file) {
        UpImg.POST(file, new HttpCallBack() {
            @Override
            public void ok(String backString) {
                pics.add(backString);
                switch (picNum) {
                    case 0:
                        ImgLoadUtils.Load(mContext, backString, binding.layoutTwo.imageA, false);
                        binding.layoutTwo.imageA.setBackgroundResource(R.color.white);
                        break;
                    case 1:
                        ImgLoadUtils.Load(mContext, backString, binding.layoutTwo.imageB, false);
                        binding.layoutTwo.imageB.setBackgroundResource(R.color.white);
                        break;
                    case 2:
                        ImgLoadUtils.Load(mContext, backString, binding.layoutTwo.imageC, false);
                        binding.layoutTwo.imageC.setBackgroundResource(R.color.white);
                        break;
                }
            }

            @Override
            public void error(String e) {
                ToastUtils.show("上传失败");
            }
        });
    }
}
