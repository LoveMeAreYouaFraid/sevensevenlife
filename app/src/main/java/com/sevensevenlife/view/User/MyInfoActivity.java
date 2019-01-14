package com.sevensevenlife.view.User;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.view.ViewCompat;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.View;

import com.sevensevenlife.http.MyRequest;
import com.sevensevenlife.http.UpImg;
import com.sevensevenlife.interfaces.EditDialogListener;
import com.sevensevenlife.interfaces.HttpCallBack;
import com.sevensevenlife.interfaces.MyHttpCallBack;
import com.sevensevenlife.model.httpmodel.PublicMode;
import com.sevensevenlife.model.httpmodel.UserInfo;
import com.sevensevenlife.model.KEY;
import com.sevensevenlife.utils.ImgChoiceUtils;
import com.sevensevenlife.utils.ImgLoadUtils;
import com.sevensevenlife.utils.JsonUtil;
import com.sevensevenlife.utils.MyApplication;
import com.sevensevenlife.utils.PreferencesUtil;
import com.sevensevenlife.utils.ToastUtils;
import com.sevensevenlife.view.custumview.BaseActivity;
import com.sevensevenlife.view.DiaLog.DialogUtils;
import com.sevensevenlife.view.DiaLog.EdItDialog;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.ActivityMyInfoBinding;

import java.io.File;

import static com.sevensevenlife.http.RequestUtils.POST;
import static com.sevensevenlife.http.RequestUtils.URI.EDIT_USER_INFO;
import static com.sevensevenlife.http.RequestUtils.URI.UP_PASSWORD;
import static com.sevensevenlife.view.DiaLog.EdItDialog.ID_CAR;
import static com.sevensevenlife.view.DiaLog.EdItDialog.NAME;
import static com.sevensevenlife.view.DiaLog.EdItDialog.NICK_NAME;
import static com.sevensevenlife.view.DiaLog.EdItDialog.PASS_WORD;

public class MyInfoActivity extends BaseActivity implements
        View.OnClickListener, MyHttpCallBack {
    private ActivityMyInfoBinding binding;
    private boolean isSetUserHead = false;
    private ImgChoiceUtils utils;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.activity_my_info);
        if (!TextUtils.isEmpty(MyApplication.getInstance().getUserInfo() + "")) {
            init();
        }


    }

    private void init() {

        binding.title.title.setText("账户信息");
        binding.title.imgBack.setOnClickListener(this);
        binding.layoutPic.setOnClickListener(this);
        binding.layoutNike.setOnClickListener(this);
        binding.layoutName.setOnClickListener(this);
        binding.layoutIdCar.setOnClickListener(this);
        binding.layoutPas.setOnClickListener(this);
        binding.edPhone.setOnClickListener(this);
        if (MyApplication.getInstance().isLogin()) {
            initVie();
        }

    }

    private void initVie() {
        ImgLoadUtils.Load(mContext, userInfo.getRows().getHeadPic(), binding.touxiang, true);

        binding.nickName.setText(userInfo.getRows().getNickName());

        binding.loginPhone.setText(userInfo.getRows().getPhone().substring(0, 4) + "****" +
                userInfo.getRows().getPhone().substring(7, 11));
        ViewCompat.setTransitionName(binding.touxiang, "touxiang");
        ViewCompat.setTransitionName(binding.loginPhone, "loginPhone");
        ViewCompat.setTransitionName(binding.nickName, "nickName");
        if (!TextUtils.isEmpty(MyApplication.getInstance().getUserInfo().getRows().getCardNo())) {
            binding.cardNo.setVisibility(View.VISIBLE);
            binding.cardNo.setText(MyApplication.getInstance().getUserInfo().getRows().getCardNo());

        } else {
            binding.cardNo.setText("未输入");
        }
        if (!TextUtils.isEmpty(MyApplication.getInstance().getUserInfo().getRows().getRealName())) {
            binding.realName.setText(MyApplication.getInstance().getUserInfo().getRows().getRealName());
        } else {
            binding.realName.setText("未输入");
        }

    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, final Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                File file = utils.onActivityResult(requestCode, resultCode, data);

                UpImg.POST(file,
                        new HttpCallBack() {
                            @Override
                            public void ok(String backString) {
                                userInfo.getRows().setHeadPic(backString);
                                updateInfo();
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
    }

    private void updateInfo() {

        MyRequest.POST(POST, new String[]{"sessionId", "nickName", "headPic", "realName", "cardNo"}, new String[]{
                MyApplication.getInstance().getUserInfo().getRows().getSessionId(),
                MyApplication.getInstance().getUserInfo().getRows().getNickName(),
                MyApplication.getInstance().getUserInfo().getRows().getHeadPic(),
                MyApplication.getInstance().getUserInfo().getRows().getRealName(),
                MyApplication.getInstance().getUserInfo().getRows().getCardNo()
        }, EDIT_USER_INFO, UserInfo.class.getName(), 0, this);

    }

    @Override
    public boolean onKeyDown(int keyCode, KeyEvent event) {
        if (isSetUserHead) {
            setResult(RESULT_OK);
            finish();
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.ed_phone:
                DialogUtils.init(mContext)
                        .setTitle("注册手机号暂不支持修改哦~")
                        .setOne("朕知道了",null);
                break;
            case R.id.img_back:
                if (isSetUserHead) {
                    setResult(RESULT_OK);
                }
                if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                    finishAfterTransition();
                } else {
                    finish();
                }
                break;
            case R.id.layout_pas:
                EdDialog(PASS_WORD);
                break;
            case R.id.layout_nike:
                EdDialog(NICK_NAME);
                break;
            case R.id.layout_name:
                EdDialog(NAME);
                break;
            case R.id.layout_id_car:
                EdDialog(ID_CAR);
                break;
            case R.id.layout_pic:
                utils = ImgChoiceUtils.isNew(this);
                utils.isCamera(true).show();
                break;

        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        utils.onPermissions(requestCode, permissions, grantResults);
    }

    private void EdDialog(int type) {
        EdItDialog.show(mContext, new EditDialogListener() {
            @Override
            public void click(int type, String[] data) {
                switch (type) {
                    case PASS_WORD:
                        passWord(data);
                        break;
                    case NAME:
                        userInfo.getRows().setRealName(data[0]);
                        updateInfo();
                        break;
                    case ID_CAR:
                        userInfo.getRows().setCardNo(data[0]);
                        updateInfo();
                        break;
                    case NICK_NAME:
                        userInfo.getRows().setNickName(data[0]);
                        updateInfo();
                        break;

                }

            }

        }, type);
    }

    private void passWord(String[] data) {
        MyRequest.POST(POST, new String[]{"sessionId", "oldPassword", "newPassword"},
                new String[]{userInfo.getRows().getSessionId(), data[0], data[1]
                }, UP_PASSWORD, PublicMode.class.getName(), 1, this);
    }

    @Override
    public <T> void ok(T backMode, String jsonString, int httpTY) {
        switch (httpTY) {
            case 0:
                UserInfo Info = new JsonUtil<UserInfo>().json2Bean(jsonString, UserInfo.class.getName());
                MyApplication.getInstance().getUserInfo().getRows().setHeadPic(
                        Info.getRows().getHeadPic());
                MyApplication.getInstance().getUserInfo().getRows().setNickName(
                        Info.getRows().getNickName());
                MyApplication.getInstance().getUserInfo().getRows().setRealName(
                        Info.getRows().getRealName());
                MyApplication.getInstance().getUserInfo().getRows().setCardNo(
                        Info.getRows().getCardNo());
                String userJson = new JsonUtil<UserInfo>().bean2Json(
                        MyApplication.getInstance().getUserInfo());
                PreferencesUtil.removeKey(KEY.USER);
                PreferencesUtil.putString(KEY.USER, userJson);
                userInfo = MyApplication.getInstance().getUserInfo();
                initVie();
                isSetUserHead = true;
                ToastUtils.show("修改成功");
                break;
            case 1:
                ToastUtils.show("修改成功");
                break;
        }

    }

    @Override
    public void error(String e, int uriType) {
        ToastUtils.show(e);
    }
}
