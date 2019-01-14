package com.sevensevenlife.view.Home.MyHome;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.View.OnClickListener;
import android.view.ViewGroup;

import com.sevensevenlife.http.MyRequest;
import com.sevensevenlife.http.UpImg;
import com.sevensevenlife.interfaces.HttpCallBack;
import com.sevensevenlife.interfaces.MyHttpCallBack;
import com.sevensevenlife.model.httpmodel.PublicMode;
import com.sevensevenlife.utils.ImgChoiceUtils;
import com.sevensevenlife.utils.ImgLoadUtils;
import com.sevensevenlife.utils.MyApplication;
import com.sevensevenlife.utils.ToastUtils;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.DynamicFragmentLayoutBinding;

import java.io.File;

import static com.sevensevenlife.http.RequestUtils.POST_TWO;
import static com.sevensevenlife.http.RequestUtils.URI.ADD_BBS_TOPIC;

/**
 * Created by Administrator on 2017/8/3 0003.
 */

public class DynamicFragment extends Fragment implements OnClickListener, MyHttpCallBack {
    private DynamicFragmentLayoutBinding binding;
    private String jsonString;
    private ImgChoiceUtils utils;
    private String imgUri = "";

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.dynamic_fragment_layout, container, false);
        binding.btUpImg.setOnClickListener(this);
        binding.tvSubmit.setOnClickListener(this);
        utils = ImgChoiceUtils.isNew(getActivity());
        utils.isCamera(true);
        return binding.getRoot();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        utils.onPermissions(requestCode, permissions, grantResults);
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        try {
            File file = utils.onActivityResult(requestCode, resultCode, data);
            UpImg.POST(file,
                    new HttpCallBack() {
                        @Override
                        public void ok(String backString) {
                            imgUri = backString;
                            ImgLoadUtils.Load(getActivity(), imgUri, binding.btUpImg, false);
                        }

                        @Override
                        public void error(String e) {
                            ToastUtils.show(e);
                        }
                    });
        } catch (Exception e) {
            ToastUtils.show("无法获取这张照片");
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.bt_up_img:
                utils.isCamera(true).show();
                break;
            case R.id.tv_submit:
                if (TextUtils.isEmpty(binding.edTitle.getText() + "")) {
                    ToastUtils.show("请输入标题");
                    return;
                }
                if (TextUtils.isEmpty(binding.edContext.getText() + "")) {
                    ToastUtils.show("请输入类容");
                    return;
                }

                jsonString = "{\"community_id\":\"" + MyApplication.getInstance().getCommunityId() + "\"," +
                        "\"title\":\"" + binding.edTitle.getText() + "\"" + "," +
                        "\"content\":\"" + binding.edContext.getText() + "\"}";

                MyRequest.POST(POST_TWO,
                        new String[]{""},
                        new String[]{jsonString},
                        ADD_BBS_TOPIC + "?sessionId=" + MyApplication.getInstance().getUserInfo().getRows().getSessionId(),
                        PublicMode.class.getName(),
                        102,
                        this);

                break;
        }
    }

    @Override
    public <T> void ok(T backMode, String jsonString, int httpTY) {
        switch (httpTY) {
            case 102:
                ToastUtils.show("提交成功");
                getActivity().setResult(getActivity().RESULT_OK);
                getActivity().finish();
                break;
        }

    }

    @Override
    public void error(String e, int uriType) {
        ToastUtils.show("提交失败" + e);
    }

}
