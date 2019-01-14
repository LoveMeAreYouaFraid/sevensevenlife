package com.sevensevenlife.view.Home.MyHome;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.sevensevenlife.http.MyRequest;
import com.sevensevenlife.interfaces.DialogListener;
import com.sevensevenlife.interfaces.MyHttpCallBack;
import com.sevensevenlife.model.httpmodel.DictionaryList;
import com.sevensevenlife.model.httpmodel.PublicMode;
import com.sevensevenlife.model.KEY;
import com.sevensevenlife.utils.MyApplication;
import com.sevensevenlife.utils.ToastUtils;
import com.sevensevenlife.view.custumview.BaseActivity;
import com.sevensevenlife.view.DiaLog.ListDialogUtils;
import com.sevensevenlife.view.DiaLog.MyProgressDialog;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.UpRepairActivityLayoutBinding;

import java.util.ArrayList;
import java.util.List;

import static com.sevensevenlife.http.RequestUtils.GET;
import static com.sevensevenlife.http.RequestUtils.POST;
import static com.sevensevenlife.http.RequestUtils.URI.ADD_REPAIR_ORDER;
import static com.sevensevenlife.http.RequestUtils.URI.GET_DICTIONARY;

/**
 * Created by Administrator on 2017/6/21 0021.
 */

public class UpRepairActivity extends BaseActivity implements View.OnClickListener, MyHttpCallBack {
    private UpRepairActivityLayoutBinding binding;
    private DictionaryList mo;
    private String repairType;
    private List<String> types = new ArrayList<>();
    private boolean initDataIsOk = false;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.up_repair_activity_layout);
        binding.title.title.setText("物业报修");
        binding.title.imgBack.setOnClickListener(this);
        binding.comment.setOnClickListener(this);
        binding.edType.setOnClickListener(this);
        MyRequest.POST(GET,
                new String[]{"code"},
                new String[]{"REPAIR_TYPE"},
                GET_DICTIONARY,
                DictionaryList.class.getName(),
                13,
                this);
        MyProgressDialog.getInstance().show(mContext, "Loading...");
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.ed_type:
                if (initDataIsOk) {
                    ListDialogUtils.init(mContext)
                            .setData(types)
                            .OnClickListener(new DialogListener() {
                                @Override
                                public void buttType(int ButtType) {
                                    repairType = types.get(ButtType);
                                    binding.edType.setText("报修类型：" + repairType);
                                }
                            }).show();
                } else {
                    ToastUtils.show("数据获取中...");
                }


                break;
            case R.id.comment:
                if (mo == null || mo.getRows() == null) {
                    ToastUtils.show("获取数据失败，请稍后重试。");
                    return;
                }
                if (TextUtils.isEmpty(binding.edPhoneNum.getText() + "")) {
                    ToastUtils.show("请输入手机号");
                    return;
                }
                if (binding.edPhoneNum.getText().toString().length() != 11) {
                    ToastUtils.show("请输入11位手机号");
                    return;
                }
                if (TextUtils.isEmpty(binding.edAddress.getText() + "")) {
                    ToastUtils.show("请输入几栋报修");
                    return;
                }
                if (TextUtils.isEmpty(binding.edHomeNum.getText() + "")) {
                    ToastUtils.show("请输入门牌号码");
                    return;
                }
                if (TextUtils.isEmpty(repairType)) {
                    ToastUtils.show("请选择报修类型");
                    return;
                }
                if (TextUtils.isEmpty(binding.edContext.getText() + "")) {
                    ToastUtils.show("请输入简单描述");
                    return;
                }


                MyRequest.POST(POST, new String[]{"sessionId", "communityId", "userName", "address", "description", "repairType", "phone"},
                        new String[]{
                                MyApplication.getInstance().getUserInfo().getRows().getSessionId(),
                                getIntent().getStringExtra(KEY.ID),
                                MyApplication.getInstance().getUserInfo().getRows().getRealName(),
                                binding.edAddress.getText() + "栋，门牌号：" + binding.edHomeNum.getText(),
                                binding.edContext.getText() + "",
                                repairType,
                                binding.edPhoneNum.getText() + ""
                        },
                        ADD_REPAIR_ORDER,
                        PublicMode.class.getName(),
                        14,
                        this);
                break;
        }
    }

    @Override
    public <T> void ok(T backMode, String jsonString, int httpTY) {
        MyProgressDialog.getInstance().cancel();
        switch (httpTY) {
            case 13:
                mo = (DictionaryList) backMode;
                for (int i = 0; i < mo.getRows().size(); i++) {
                    types.add(mo.getRows().get(i).getValue());
                }
                initDataIsOk = true;
                break;
            case 14:
                PublicMode pm = (PublicMode) backMode;
                ToastUtils.show(pm.getHeader().getMessage());
                finish();
                break;
        }
    }

    @Override
    public void error(String e, int uriType) {
        MyProgressDialog.getInstance().cancel();
        ToastUtils.show(e);
    }
}
