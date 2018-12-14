package com.sevensevenlife.view.User;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.sevensevenlife.http.MyRequest;
import com.sevensevenlife.interfaces.MyHttpCallBack;
import com.sevensevenlife.model.httpmodel.PublicMode;
import com.sevensevenlife.utils.MyApplication;
import com.sevensevenlife.utils.ToastUtils;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.ActivitySuggestioinBinding;

import static com.sevensevenlife.http.RequestUtils.POST;
import static com.sevensevenlife.http.RequestUtils.URI.ADD_SUGGESTIOIN;

public class SuggestioinActivity extends Activity implements
        View.OnClickListener, MyHttpCallBack {
    private final int SUBMIT_JIANYI = 0;
    private ActivitySuggestioinBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_suggestioin);
        binding.title.imgBack.setOnClickListener(this);
        binding.title.rightButton.setVisibility(View.VISIBLE);
        binding.title.rightButton.setOnClickListener(this);
        binding.title.rightButton.setText("发送");

    }


    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.right_button:
                if (!TextUtils.isEmpty(binding.jianyi.getText())) {
                    MyRequest.POST(POST, new String[]{"sessionId", "content"}, new String[]{
                                    MyApplication.getInstance().getUserInfo().getRows().getSessionId(),
                                    binding.jianyi.getText() + ""
                            },
                            ADD_SUGGESTIOIN, PublicMode.class.getName(), 101, this);
                } else {
                    ToastUtils.show("不能为空");
                }

                break;
        }
    }

    @Override
    public <T> void ok(T backMode, String jsonString, int httpTY) {
        switch (httpTY) {
            case 101:
                ToastUtils.show("感谢您的建议");
                finish();
                break;
        }
    }

    @Override
    public void error(String e, int uriType) {
        ToastUtils.show(e);

    }
}
