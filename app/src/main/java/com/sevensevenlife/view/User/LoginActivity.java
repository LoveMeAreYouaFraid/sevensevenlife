package com.sevensevenlife.view.User;

import android.app.Activity;
import android.content.Context;
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
import com.sevensevenlife.model.KEY;
import com.sevensevenlife.utils.LogUtils;
import com.sevensevenlife.utils.MyApplication;
import com.sevensevenlife.utils.PreferencesUtil;
import com.sevensevenlife.utils.ToastUtils;
import com.sevensevenlife.utils.TxtUtils;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.ActivityLoginBinding;

import static com.sevensevenlife.http.RequestUtils.GET;
import static com.sevensevenlife.http.RequestUtils.POST;
import static com.sevensevenlife.http.RequestUtils.URI.GET_VERIFY_CODE_V2;
import static com.sevensevenlife.http.RequestUtils.URI.USER_LOGIN;
import static com.sevensevenlife.model.KEY.JPUSHID;
import static com.sevensevenlife.model.KEY.PASS_WORD;
import static com.sevensevenlife.model.KEY.PHONE;
import static com.sevensevenlife.model.KEY.REG_ID;

public class LoginActivity extends Activity implements View.OnClickListener, MyHttpCallBack {

    private ActivityLoginBinding binding;
    private Context mContext;
    private UserInfo userInfo;
    private TimeCount time;
    private boolean isCode = true;
    private long times;
    private int num = 0;
    private int allNum;
    private boolean oneNum = false;
    private boolean TwoNum = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        binding = DataBindingUtil.setContentView(this, R.layout.activity_login);
        time = new TimeCount(60000, 1000);
        binding.title.title.setText("登录");
        binding.title.title.setOnClickListener(this);
        binding.title.imgBack.setOnClickListener(this);
        binding.findPassword.setOnClickListener(this);
        binding.tvLogin.setOnClickListener(this);
        binding.tvTranslate.setOnClickListener(this);
        binding.sendSms.setOnClickListener(this);
        binding.mima.setOnFocusChangeListener(new android.view.View.OnFocusChangeListener() {
            @Override
            public void onFocusChange(View v, boolean hasFocus) {
                if (hasFocus) {
                    binding.scrollView.smoothScrollTo(0, 250);
                } else {
                }
            }
        });

    }


    private void login() {
        MyRequest.POST(POST, new String[]{PHONE, PASS_WORD, JPUSHID, "verifycode"},
                new String[]{binding.shoujihao.getText() + "",
                        binding.mima.getText() + "",
                        PreferencesUtil.getString(REG_ID),
                        binding.code.getText() + ""},
                USER_LOGIN, UserInfo.class.getName(), 0, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.title:
                allNum++;
                LogUtils.e(123, "System" + (System.currentTimeMillis() - times));
                if (num == 0) {
                    times = System.currentTimeMillis();
                }

                if (num == 0 || num < 3) {
                    if ((System.currentTimeMillis() - times) < 300) {
                        num++;
                    }
                    if (num == 3 && allNum == num) {
                        oneNum = true;
                    } else {
                        oneNum = false;
                    }
                } else if (num >= 3 || num < 7) {
                    if ((System.currentTimeMillis() - times) > 1000) {
                        num++;
                    }
                }
                if (num == 6 && allNum == num) {
                    TwoNum = true;
                } else {
                    TwoNum = false;
                }
                if (oneNum && TwoNum) {
                    isCode = false;
                    ToastUtils.show("解锁成功");
                }
                LogUtils.e(123, num);
                times = System.currentTimeMillis();
                break;
            case R.id.tv_translate:
                Intent intent = new Intent(this, RegisterActivity.class);
                startActivity(intent);
                finish();
                break;
            case R.id.tv_login:
                if (TextUtils.isEmpty(binding.shoujihao.getText())) {
                    ToastUtils.show("请输入手机号码");
                    return;
                }
                if (binding.shoujihao.getText().length() != 11) {
                    ToastUtils.show("请输入11位手机号码");
                    return;
                }

                if (TextUtils.isEmpty(binding.mima.getText())) {
                    ToastUtils.show("请输入密码");
                    return;
                }

                if (isCode) {
                    if (TextUtils.isEmpty(binding.code.getText())) {
                        ToastUtils.show("请输入验证码");
                        return;
                    }
                }

                login();
                break;
            case R.id.find_password:
                Intent intents = new Intent(this, ForgetPasswordActivity.class);
                startActivity(intents);
                break;
            case R.id.send_sms:
                if (TextUtils.isEmpty(binding.shoujihao.getText())) {
                    binding.shoujihao.setError("请输入手机号");
                    ToastUtils.show("请输入手机号");
                    return;
                }
                if (binding.shoujihao.getText().length() != 11) {
                    binding.shoujihao.setError("请输入十一位手机号");
                    ToastUtils.show("请输入十一位手机号");
                    return;
                }
                String times = System.currentTimeMillis() + "";
                MyRequest.POST(GET, new String[]{"phone", "type", "time", "sign"}, new String[]{
                                binding.shoujihao.getText() + "",
                                "5",
                                times,
                                TxtUtils.Md5(binding.shoujihao.getText().toString() + "5" + times + "chuheridangwu12345")
                        },
                        GET_VERIFY_CODE_V2, PublicMode.class.getName(), 10, this);
                time.start();
                break;
        }
    }

    @Override
    public <T> void ok(T backMode, String jsonString, int httpTY) {
        switch (httpTY) {
            case 0:
                userInfo = (UserInfo) backMode;
                PreferencesUtil.putString(KEY.USER, jsonString);
                MyApplication.getInstance().setUserInfo(userInfo);
                MyApplication.getInstance().setLogin(true);
                ToastUtils.show("登录成功");
                LogUtils.e(123, userInfo.getRows().getPhone());
                finish();
                break;
            case 10:
                try {
                    PublicMode mode = (PublicMode) backMode;
                    ToastUtils.show(mode.getHeader().getMessage());
                } catch (Exception e) {
                }

                break;
        }

    }

    @Override
    public void error(String e, int uriType) {
        ToastUtils.show(e);

    }

    class TimeCount extends CountDownTimer {// 计时器

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            binding.sendSms.setText("发送验证码");
            binding.sendSms.setClickable(true);
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            binding.sendSms.setClickable(false);
            binding.sendSms.setText(millisUntilFinished / 1000 + "秒");
        }

    }
}
