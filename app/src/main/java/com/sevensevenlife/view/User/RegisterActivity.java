package com.sevensevenlife.view.User;

import android.app.Activity;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.text.TextUtils;
import android.view.View;

import com.sevensevenlife.http.MyRequest;
import com.sevensevenlife.interfaces.MyHttpCallBack;
import com.sevensevenlife.model.httpmodel.PublicMode;
import com.sevensevenlife.model.httpmodel.UserInfo;
import com.sevensevenlife.utils.JsonUtil;
import com.sevensevenlife.utils.MyApplication;
import com.sevensevenlife.utils.PreferencesUtil;
import com.sevensevenlife.utils.ToastUtils;
import com.sevensevenlife.utils.TxtUtils;
import com.sevensevenlife.view.custumview.WebViewInfoActivity;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.ActivityRegisterBinding;

import static com.sevensevenlife.http.RequestUtils.GET;
import static com.sevensevenlife.http.RequestUtils.POST;
import static com.sevensevenlife.http.RequestUtils.QEQUEST_URL;
import static com.sevensevenlife.http.RequestUtils.URI.CHECK_PHONE_NUMBER;
import static com.sevensevenlife.http.RequestUtils.URI.GET_VERIFY_CODE_V2;
import static com.sevensevenlife.http.RequestUtils.URI.REGISTER;
import static com.sevensevenlife.model.KEY.USER;

public class RegisterActivity extends Activity implements MyHttpCallBack, View.OnClickListener {
    private TimeCount time;

    private ActivityRegisterBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_register);
        binding.title.title.setText("注册");
        binding.title.imgBack.setOnClickListener(this);
        binding.comment.setOnClickListener(this);
        binding.sendCode.setOnClickListener(this);
        binding.agreement.setOnClickListener(this);
        time = new TimeCount(60000, 1000);
    }


    @Override
    public <T> void ok(T backMode, String jsonString, int httpTY) {
        switch (httpTY) {
            case 101:
                String times = System.currentTimeMillis() + "";
                MyRequest.POST(GET, new String[]{"phone", "type", "time", "sign"},
                        new String[]{binding.phoneNum.getText() + "",
                                "1",
                                times,
                                TxtUtils.Md5(binding.phoneNum.getText().toString() + "1" + times + "chuheridangwu12345")
                        },
                        GET_VERIFY_CODE_V2, PublicMode.class.getName(), 100, this);
                break;
            case 100:
                time.start();
                ToastUtils.show("请注意查看手机");
                break;
            case 99:
                PreferencesUtil.putString(USER, jsonString);
                UserInfo info = new JsonUtil<UserInfo>().json2Bean(jsonString, UserInfo.class.getName());
                MyApplication.getInstance().setLogin(true);
                MyApplication.getInstance().setUserInfo(info);
                finish();
                ToastUtils.show("注册成功");
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
            case R.id.img_back:
                finish();
                break;
            case R.id.send_code:
                if (TextUtils.isEmpty(binding.phoneNum.getText() + "")) {
                    ToastUtils.show("请输入手机号");
                    return;
                }
                if (binding.phoneNum.getText().length() != 11) {
                    ToastUtils.show("请输入11位手机号");
                    return;
                }
                MyRequest.POST(GET, new String[]{"phone"}, new String[]{binding.phoneNum.getText() + ""},
                        CHECK_PHONE_NUMBER, PublicMode.class.getName(), 101, this);
                break;
            case R.id.comment:
                if (TextUtils.isEmpty(binding.phoneNum.getText() + "")) {
                    ToastUtils.show("请输入手机号");
                    return;
                }
                if (binding.phoneNum.getText().length() != 11) {
                    ToastUtils.show("请输入11位手机号");
                    return;
                }
                if (binding.code.getText().length() != 6) {
                    ToastUtils.show("请输入6位验证码");
                    return;
                }
                if (binding.passWord.getText().length() < 6) {
                    return;
                }
                MyRequest.POST(POST, new String[]{"phone", "password", "verifycode"},
                        new String[]{binding.phoneNum.getText() + "",
                                binding.passWord.getText() + "",
                                binding.code.getText() + ""}, REGISTER,
                        PublicMode.class.getName(), 99, this);
                break;
            case R.id.agreement:
                Intent intent = new Intent(this, WebViewInfoActivity.class);
                intent.putExtra("UrlAddress", QEQUEST_URL + "yxsh-api/html/protocol.html");
                startActivity(intent);
                break;
        }
    }

    class TimeCount extends CountDownTimer {// 计时器

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            binding.sendCode.setText("短信验证码");
            binding.sendCode.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            binding.sendCode.setClickable(false);
            binding.sendCode.setText(millisUntilFinished / 1000 + "秒");
        }

    }
}
