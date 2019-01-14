package com.sevensevenlife.view.Home.MyHome;

import com.sevensevenlife.http.MyRequest;
import com.sevensevenlife.interfaces.BindViewInterface;
import com.sevensevenlife.interfaces.MyHttpCallBack;
import com.sevensevenlife.model.httpmodel.HomeMsgInfo;
import com.sevensevenlife.model.httpmodel.HomeMsgList;
import com.sevensevenlife.model.KEY;
import com.sevensevenlife.utils.MyApplication;
import com.sevensevenlife.utils.RecyclerViewUtils.PublicAdapter;
import com.sevensevenlife.utils.RecyclerViewUtils.RViewUtils;
import com.sevensevenlife.utils.ToastUtils;
import com.sevensevenlife.view.custumview.BaseActivity;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.MsgListActivityLayoutBinding;
import com.example.youxiangshenghuo.databinding.MsgListLayoutBinding;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.View;

import java.util.ArrayList;
import java.util.List;

import static com.sevensevenlife.http.RequestUtils.GET;
import static com.sevensevenlife.http.RequestUtils.URI.GET_NOTICE_LIST;

/**
 * Created by Administrator on 2017/4/6 0006.
 */

public class MsgActivity extends BaseActivity implements View.OnClickListener, MyHttpCallBack
        , BindViewInterface {
    private MsgListActivityLayoutBinding binding;
    private List<HomeMsgInfo> list = new ArrayList<>();
    private PublicAdapter<String> adapter;
    private int pageNum = 1;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.msg_list_activity_layout);
        binding.act.title.title.setText("小区公告");
        binding.act.title.imgBack.setOnClickListener(this);

        MyRequest.POST(GET, new String[]{"sessionId", "communityId", "pageNum", "pageSize"},
                new String[]{
                        MyApplication.getInstance().getUserInfo().getRows().getSessionId(),
                        getIntent().getStringExtra(KEY.ID), pageNum + "", "20"
                }, GET_NOTICE_LIST, HomeMsgList.class.getName(), 12, this);

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
        MsgListLayoutBinding bin = (MsgListLayoutBinding) binding;
        bin.msgTitle.setText(list.get(position).getTitle());
        bin.msgTime.setText(list.get(position).getPublish_time());
        bin.msgDetail.setText(list.get(position).getContent());
        bin.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, MsgDetailsActivity.class)
                        .putExtra("title", list.get(position).getTitle())
                        .putExtra("msgDetail", list.get(position).getPublish_time())
                        .putExtra("times", list.get(position).getContent()));
            }
        });
    }

    @Override
    public <T> void ok(T backMode, String jsonString, int httpTY) {
        switch (httpTY) {
            case 12:
                HomeMsgList mo = (HomeMsgList) backMode;
                list.clear();
                list.addAll(mo.getRows());

                adapter = new PublicAdapter<>(list.size(), R.layout.msg_list_layout, this);
                new RViewUtils(mContext, binding.act.list)
                        .setAdapter(adapter);
                binding.act.list.recyclerView.setBackgroundResource(R.color.white);
                break;
        }
    }

    @Override
    public void error(String e, int uriType) {
        ToastUtils.show(e);
    }
}
