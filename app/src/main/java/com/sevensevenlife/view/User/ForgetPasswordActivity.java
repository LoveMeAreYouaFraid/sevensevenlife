package com.sevensevenlife.view.User;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.CountDownTimer;
import android.view.View;

import com.sevensevenlife.http.MyRequest;
import com.sevensevenlife.interfaces.MyHttpCallBack;
import com.sevensevenlife.model.httpmodel.PublicMode;
import com.sevensevenlife.utils.ToastUtils;
import com.sevensevenlife.utils.TxtUtils;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.ActivityForgetpasswordBinding;

import static com.sevensevenlife.http.RequestUtils.GET;
import static com.sevensevenlife.http.RequestUtils.POST;
import static com.sevensevenlife.http.RequestUtils.URI.GET_VERIFY_CODE_V2;
import static com.sevensevenlife.http.RequestUtils.URI.RESET_PASSWORD;

public class ForgetPasswordActivity extends Activity implements MyHttpCallBack, View.OnClickListener {


    private String shoujihaoString, yanzhengmaString, mimaString, querenmimaString;
    private TimeCount time;
    private ActivityForgetpasswordBinding binding;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_forgetpassword);
        time = new TimeCount(60000, 1000);
        binding.ok.setOnClickListener(this);
        binding.sendCode.setOnClickListener(this);
        binding.title.imgBack.setOnClickListener(this);
    }


    @Override
    public <T> void ok(T backMode, String jsonString, int httpTY) {
        switch (httpTY) {
            case 100:
                ToastUtils.show("请注意查看手机");
                break;
            case 99:
                ToastUtils.show("修改成功，请登录");
                finish();
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
            case R.id.ok:
                yanzhengmaString = binding.yanzhengma.getText().toString();
                mimaString = binding.mima.getText().toString();
                querenmimaString = binding.querenmima.getText().toString();
                shoujihaoString = binding.shoujihao.getText().toString();

                boolean isNull = yanzhengmaString.equals("") ||
                        mimaString.equals("") ||
                        querenmimaString.equals("") ||
                        shoujihaoString.equals("");
                if (isNull) {
                    ToastUtils.show("不能为空");
                } else {
                    if (mimaString.equals(querenmimaString)) {

                        MyRequest.POST(POST, new String[]{"phone", "password", "verifycode"},
                                new String[]{shoujihaoString, mimaString, yanzhengmaString},
                                RESET_PASSWORD, PublicMode.class.getName(), 99, this);
                    } else {
                        ToastUtils.show("两次输入密码不匹配");
                    }
                }
                break;
            case R.id.img_back:
                finish();
                break;
            case R.id.send_code:
                String phoneString = binding.shoujihao.getText().toString();
                if (phoneString.equals("") || phoneString.length() < 11) {
                    ToastUtils.show(getResources().getString(R.string.sjhmgsbzq));
                } else {
                    time.start();
                    String time = System.currentTimeMillis() + "";
                    MyRequest.POST(GET, new String[]{"phone", "type", "time", "sign"}, new String[]{
                                    binding.shoujihao.getText().toString(),
                                    "3",
                                    time,
                                    TxtUtils.Md5(binding.shoujihao.getText().toString() + "3" + time + "chuheridangwu12345")
                            }, GET_VERIFY_CODE_V2, PublicMode.class.getName()
                            , 100, this);
                }
                break;
        }

    }

    class TimeCount extends CountDownTimer {// 计时器

        public TimeCount(long millisInFuture, long countDownInterval) {
            super(millisInFuture, countDownInterval);// 参数依次为总时长,和计时的时间间隔
        }

        @Override
        public void onFinish() {// 计时完毕时触发
            binding.sendCode.setClickable(true);
            binding.sendCode.setText("发送");
        }

        @Override
        public void onTick(long millisUntilFinished) {// 计时过程显示
            binding.sendCode.setText((millisUntilFinished / 1000) + "");
            binding.sendCode.setClickable(false);

        }
    }


}
