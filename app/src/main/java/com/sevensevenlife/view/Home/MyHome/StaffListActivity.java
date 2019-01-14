package com.sevensevenlife.view.Home.MyHome;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.os.Bundle;
import android.view.View;

import com.sevensevenlife.http.MyRequest;
import com.sevensevenlife.interfaces.BindViewInterface;
import com.sevensevenlife.interfaces.MyHttpCallBack;
import com.sevensevenlife.model.httpmodel.SecurityInfo;
import com.sevensevenlife.model.httpmodel.SecurityList;
import com.sevensevenlife.model.KEY;
import com.sevensevenlife.utils.ImgLoadUtils;
import com.sevensevenlife.utils.MyApplication;
import com.sevensevenlife.utils.RecyclerViewUtils.PublicAdapter;
import com.sevensevenlife.utils.RecyclerViewUtils.RViewUtils;
import com.sevensevenlife.utils.ToastUtils;
import com.sevensevenlife.view.custumview.BaseActivity;
import com.sevensevenlife.view.DiaLog.MyProgressDialog;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.ListActivityBinding;
import com.example.youxiangshenghuo.databinding.StaffListItemBinding;

import java.util.ArrayList;
import java.util.List;

import static com.sevensevenlife.http.RequestUtils.GET;
import static com.sevensevenlife.http.RequestUtils.URI.SECURITY_LIST;

/**
 * Created by Administrator on 2017/4/7 0007.
 */

public class StaffListActivity extends BaseActivity implements View.OnClickListener, BindViewInterface, MyHttpCallBack {
    private ListActivityBinding binding;
    private List<SecurityInfo> strings = new ArrayList<>();

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.list_activity);
        binding.title.imgBack.setOnClickListener(this);
        binding.title.title.setText("小区保安");
        MyRequest.POST(GET,
                new String[]{
                        "sessionId",
                        "communityId"},
                new String[]{
                        MyApplication.getInstance().getUserInfo().getRows().getSessionId(),
                        getIntent().getStringExtra(KEY.ID)
                }, SECURITY_LIST,
                SecurityList.class.getName(),
                13,
                this);
        MyProgressDialog.getInstance().show(mContext, "Loading....");

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
        }

    }

    @Override
    public void bandView(ViewDataBinding binding, final int position) {
        StaffListItemBinding bin = (StaffListItemBinding) binding;
        ImgLoadUtils.Load(mContext, strings.get(position).getHead_pic(), bin.headerImg, true);
        bin.name.setText(strings.get(position).getName());
        bin.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                Intent CALLintent = new Intent();
                CALLintent.setAction("android.intent.action.CALL");
                CALLintent.setData(Uri.parse("tel:" + strings.get(position).getPhone()));
                startActivity(CALLintent);
            }
        });

    }

    @Override
    public <T> void ok(T backMode, String jsonString, int httpTY) {
        switch (httpTY) {
            case 13:
                MyProgressDialog.getInstance().cancel();
                SecurityList mo = (SecurityList) backMode;
                strings.clear();
                strings.addAll(mo.getRows());
                new RViewUtils(mContext, binding.list)
                        .setAdapter(new PublicAdapter<>(strings, R.layout.staff_list_item, this));
                break;
        }
    }

    @Override
    public void error(String e, int uriType) {
        ToastUtils.show(e);
        MyProgressDialog.getInstance().cancel();
    }
}
