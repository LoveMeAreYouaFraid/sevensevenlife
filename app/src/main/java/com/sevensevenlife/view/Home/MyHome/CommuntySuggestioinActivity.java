package com.sevensevenlife.view.Home.MyHome;

import android.app.Activity;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.sevensevenlife.http.MyRequest;
import com.sevensevenlife.interfaces.MyHttpCallBack;
import com.sevensevenlife.model.httpmodel.PublicMode;
import com.sevensevenlife.model.KEY;
import com.sevensevenlife.utils.MyApplication;
import com.sevensevenlife.utils.ToastUtils;
import com.sevensevenlife.view.DiaLog.MyProgressDialog;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.ActivitySuggestioinBinding;

import static com.sevensevenlife.http.RequestUtils.POST;
import static com.sevensevenlife.http.RequestUtils.URI.ADD_COMMUNITY_SUGGESTIOIN;

public class CommuntySuggestioinActivity extends Activity implements
        View.OnClickListener, MyHttpCallBack {
    private ActivitySuggestioinBinding binding;
    String communityId = "";

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
                    MyProgressDialog.getInstance().show(this, "Loading..");
                    MyRequest.POST(POST, new String[]{"sessionId", "communityId", "content"}, new String[]{
                                    MyApplication.getInstance().getUserInfo().getRows().getSessionId(),
                                    getIntent().getStringExtra(KEY.ID),
                                    binding.jianyi.getText() + ""
                            },
                            ADD_COMMUNITY_SUGGESTIOIN, PublicMode.class.getName(), 101, this);
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
                MyProgressDialog.getInstance().cancel();
                ToastUtils.show("感谢您的建议");
                finish();
                break;
        }
    }

    @Override
    public void error(String e, int uriType) {
        ToastUtils.show(e);
        MyProgressDialog.getInstance().cancel();
    }
}
