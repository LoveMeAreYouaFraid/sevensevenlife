package com.sevensevenlife.view.Home.PublicBicycle;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.text.InputFilter;
import android.text.TextUtils;
import android.view.View;

import com.sevensevenlife.http.MyRequest;
import com.sevensevenlife.interfaces.MyHttpCallBack;
import com.sevensevenlife.model.httpmodel.UserInfo;
import com.sevensevenlife.model.KEY;
import com.sevensevenlife.utils.DPIUtil;
import com.sevensevenlife.utils.MyApplication;
import com.sevensevenlife.utils.PreferencesUtil;
import com.sevensevenlife.utils.TextViewParser;
import com.sevensevenlife.utils.ToastUtils;
import com.sevensevenlife.view.custumview.BaseActivity;
import com.sevensevenlife.view.custumview.WebViewInfoActivity;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.BindCardActivityLayoutBinding;

import static com.sevensevenlife.http.RequestUtils.POST;
import static com.sevensevenlife.http.RequestUtils.PROTOCOL;
import static com.sevensevenlife.http.RequestUtils.URI.EDIT_USER_INFO;

/**
 * Created by 10237 on 2016/12/23.
 */

public class EditUserInfoActivity extends BaseActivity implements View.OnClickListener, MyHttpCallBack {
    private BindCardActivityLayoutBinding binding;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.bind_card_activity_layout);
        binding.title.imgBack.setOnClickListener(this);
        binding.title.title.setText("完善信息");

        binding.myEdName.edLeftTxt.setText("姓名");
        binding.myEdName.editText.setHint("请填写姓名");

        binding.myEdId.edLeftTxt.setText("身份证号");
        binding.myEdId.editText.setHint("请填写身份证号");
        binding.myEdId.editText.setFilters(new InputFilter[]{new InputFilter.LengthFilter(18),
                new InputFilter.AllCaps() {

                }});
        binding.yes.setOnClickListener(this);

        TextViewParser textViewParser = new TextViewParser();
        textViewParser.append("使用本软件即视为同意",
                DPIUtil.dip2px(12), ContextCompat.getColor(mContext, R.color.txt_black));

        textViewParser.append("《租车服务协议》",
                DPIUtil.dip2px(12), ContextCompat.getColor(mContext, R.color.style_color),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(mContext, WebViewInfoActivity.class);
                        intent.putExtra("UrlAddress", PROTOCOL);
                        intent.putExtra("IsRecharge", "1");
                        startActivity(intent);
                    }
                });


        textViewParser.parse(binding.tvUserAgreement);
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.yes:

                if (TextUtils.isEmpty(binding.myEdName.editText.getText()) || TextUtils.isEmpty(binding.myEdId.editText.getText())) {
                    ToastUtils.show("请完善个人信息");
                    return;
                }
                if (binding.myEdId.editText.getText().length() != 18) {
                    ToastUtils.show("身份证号不能少于18位哦");
                    return;
                }
                UserInfo userInfo = MyApplication.getInstance().getUserInfo();
                MyRequest.POST(POST,
                        new String[]{"sessionId", "nickName", "headPic", "realName", "cardNo"},
                        new String[]{MyApplication.getInstance().getUserInfo().getRows().getSessionId(),
                                userInfo.getRows().getNickName(),
                                userInfo.getRows().getHeadPic(),
                                binding.myEdName.editText.getText() + "",
                                binding.myEdId.editText.getText() + ""},
                        EDIT_USER_INFO, UserInfo.class.getName(), 0, this);

                break;

        }

    }

    //把一个字符串中的大写转为小写，小写转换为大写：思路2
    public static String exChange2(String str) {
        for (int i = 0; i < str.length(); i++) {
            //如果是小写
            if (str.substring(i, i + 1).equals(str.substring(i, i + 1).toLowerCase())) {
                str.substring(i, i + 1).toUpperCase();
            } else {
                str.substring(i, i + 1).toLowerCase();
            }
        }
        return str;
    }

    @Override
    public <T> void ok(T backMode, String jsonString, int httpTY) {
        switch (httpTY) {
            case 0:
                UserInfo info = (UserInfo) backMode;
                MyApplication.getInstance().setUserInfo((UserInfo) backMode);
                PreferencesUtil.putString(KEY.USER, jsonString);
                ToastUtils.show("提交成功");
                setResult(RESULT_OK, new Intent(mContext, EditUserInfoActivity.class)
                        .putExtra(KEY.CARD_NO, info.getRows().getCardNo())
                        .putExtra(KEY.REAL_NAME, info.getRows().getRealName()));
                finish();
                break;
        }
    }

    @Override
    public void error(String e, int uriType) {
        ToastUtils.show(e);
    }
}
