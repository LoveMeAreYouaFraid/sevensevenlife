package com.sevensevenlife.view.Home.MyHome;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.View;

import com.sevensevenlife.http.MyRequest;
import com.sevensevenlife.interfaces.BindViewInterface;
import com.sevensevenlife.interfaces.MyHttpCallBack;
import com.sevensevenlife.model.httpmodel.VoteInfo;
import com.sevensevenlife.model.httpmodel.VoteList;
import com.sevensevenlife.model.KEY;
import com.sevensevenlife.utils.MyApplication;
import com.sevensevenlife.utils.RecyclerViewUtils.PublicAdapter;
import com.sevensevenlife.utils.RecyclerViewUtils.RViewUtils;
import com.sevensevenlife.utils.ToastUtils;
import com.sevensevenlife.view.custumview.BaseActivity;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.ListActivityBinding;
import com.example.youxiangshenghuo.databinding.VoteListItemLayoutBinding;

import java.util.ArrayList;
import java.util.List;

import static com.sevensevenlife.http.RequestUtils.GET;
import static com.sevensevenlife.http.RequestUtils.URI.GET_VOTE_LIST;

/**
 * Created by Administrator on 2017/4/7 0007.
 */

public class VoteListActivity extends BaseActivity implements View.OnClickListener
        , BindViewInterface, MyHttpCallBack {
    private ListActivityBinding binding;
    private List<VoteInfo> list = new ArrayList<>();
    private PublicAdapter adapter;

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.list_activity);
        binding.title.title.setText("投票列表");
        binding.title.imgBack.setOnClickListener(this);
        binding.list.recyclerView.setBackgroundResource(R.color.white);

        MyRequest.POST(GET,
                new String[]{"sessionId", "communityId"},
                new String[]{MyApplication.getInstance().getUserInfo().getRows().getSessionId(),
                        getIntent().getStringExtra(KEY.ID)
                }, GET_VOTE_LIST, VoteList.class.getName(), 31, this);//,"status","pageNum","pageSize"


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }

    @Override
    public void bandView(ViewDataBinding binding, final int position) {
        VoteListItemLayoutBinding bin = (VoteListItemLayoutBinding) binding;
        bin.msgTime.setText(list.get(position).getEnd_time());
        bin.msgTitle.setText(list.get(position).getTitle());
        bin.msgDetail.setText(list.get(position).getDescription());
        bin.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(VoteListActivity.this, VoteDetailActivity.class)
                        .putExtra(KEY.ID, list.get(position).getId()+""));
                ToastUtils.show(list.get(position).getId());
            }
        });
    }

    @Override
    public <T> void ok(T backMode, String jsonString, int httpTY) {
        switch (httpTY) {
            case 31:
                VoteList mo = (VoteList) backMode;
                list.clear();
                list.addAll(mo.getRows());
                adapter = new PublicAdapter<>(list.size(), R.layout.vote_list_item_layout, this);
                new RViewUtils(mContext, binding.list)
                        .setAdapter(adapter);
//                adapter.notifyDataSetChanged();

                break;
        }
    }

    @Override
    public void error(String e, int uriType) {
        ToastUtils.show(e);
    }
}
