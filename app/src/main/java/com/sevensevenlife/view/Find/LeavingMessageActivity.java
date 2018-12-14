package com.sevensevenlife.view.Find;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;

import com.sevensevenlife.http.MyRequest;
import com.sevensevenlife.http.UpImg;
import com.sevensevenlife.interfaces.HttpCallBack;
import com.sevensevenlife.interfaces.MyHttpCallBack;
import com.sevensevenlife.model.httpmodel.PublicMode;
import com.sevensevenlife.utils.ImgChoiceUtils;
import com.sevensevenlife.utils.ImgLoadUtils;
import com.sevensevenlife.utils.LogUtils;
import com.sevensevenlife.utils.MyApplication;
import com.sevensevenlife.utils.ToastUtils;
import com.sevensevenlife.view.custumview.BaseActivity;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.LeavingMessageActivityLayoutBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.sevensevenlife.http.RequestUtils.POST;
import static com.sevensevenlife.http.RequestUtils.URI.ADD_CDYEE_MESSAGE;

/**
 * Created by Administrator on 2017/4/17 0017.
 */

public class LeavingMessageActivity extends BaseActivity implements MyHttpCallBack, View.OnClickListener {
    private LeavingMessageActivityLayoutBinding binding;
    private String pic = "";
    private String picOne = "";
    private String picTwo = "";
    private ImgChoiceUtils utils;
    private int upButtonType = 0;
    private List<ImageView> imgList = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.leaving_message_activity_layout);
        binding.title.imgBack.setOnClickListener(this);
        binding.upImgOne.setOnClickListener(this);
        binding.upImgTwo.setOnClickListener(this);
        binding.comment.setOnClickListener(this);
        imgList.add(binding.upImgOne);
        imgList.add(binding.upImgTwo);
        utils = ImgChoiceUtils.isNew(this);
        utils.isCamera(true);
    }

    @Override
    public <T> void ok(T backMode, String jsonString, int httpTY) {
        switch (httpTY) {
            case 10:
                ToastUtils.show("发表成功");
                setResult(RESULT_OK);
                finish();
                break;
        }
    }

    @Override
    public void error(String e, int uriType) {

    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        utils.onPermissions(requestCode, permissions, grantResults);
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                ToastUtils.show("图片上传中...");
                File file = utils.onActivityResult(requestCode, resultCode, data);
                UpImg.POST(file, new HttpCallBack() {
                    @Override
                    public void ok(String backString) {
                        if (upButtonType == 0) {
                            picOne = backString;
                        } else {
                            picTwo = backString;
                        }
                        ImgLoadUtils.Load(mContext, backString, imgList.get(upButtonType), false);
                    }

                    @Override
                    public void error(String e) {
                        ToastUtils.show(e);
                    }
                });
            } catch (Exception e) {
                ToastUtils.show("无法获取这张照片");
                LogUtils.e(123, "无法获取这张照片" + e.getMessage());
            }

        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.up_img_one:
                upButtonType = 0;
//                utils = ImgChoiceUtils.isNew(this);
//                utils.isCamera(true).show();
                utils.show();
                break;
            case R.id.up_img_two:
                upButtonType = 1;
//                utils = ImgChoiceUtils.isNew(this);
//                utils.isCamera(true).show();
                utils.show();
                break;
            case R.id.comment:
                if (TextUtils.isEmpty(binding.edTitle.getText() + "")) {
                    ToastUtils.show("请输入标题");
                    return;
                }
                if (TextUtils.isEmpty(binding.edContent.getText() + "")) {
                    ToastUtils.show("请输入发帖类容");
                    return;
                }
                pic = picOne + "," + picTwo;
                MyRequest.POST(POST, new String[]{"sessionId", "title", "content", "image"}, new String[]{
                        MyApplication.getInstance().getUserInfo().getRows().getSessionId(),
                        binding.edTitle.getText() + "",
                        binding.edContent.getText() + "",
                        pic
                }, ADD_CDYEE_MESSAGE, PublicMode.class.getName(), 10, this);
                break;
        }

    }
}
