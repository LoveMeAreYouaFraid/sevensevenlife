package com.sevensevenlife.view.User;

import android.app.Activity;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.sevensevenlife.http.MyRequest;
import com.sevensevenlife.interfaces.ListItemListener;
import com.sevensevenlife.interfaces.MyHttpCallBack;
import com.sevensevenlife.model.httpmodel.CollectInfo;
import com.sevensevenlife.model.httpmodel.CollectListInfo;
import com.sevensevenlife.model.httpmodel.PublicMode;
import com.sevensevenlife.model.KEY;
import com.sevensevenlife.utils.MyApplication;
import com.sevensevenlife.utils.ToastUtils;
import com.sevensevenlife.view.User.Adpter.CollectListAdapter;
import com.andview.refreshview.XRefreshView;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.ActivityShoucangBinding;
import com.sevensevenlife.utils.MemoryBean;

import java.util.ArrayList;
import java.util.List;

import static com.sevensevenlife.http.RequestUtils.GET;
import static com.sevensevenlife.http.RequestUtils.POST;
import static com.sevensevenlife.http.RequestUtils.URI.DEL_COLLECT_LIST;
import static com.sevensevenlife.http.RequestUtils.URI.GET_COLLEC_LIST;

public class CollectListActivity extends Activity implements MyHttpCallBack, View.OnClickListener, ListItemListener {

    private ActivityShoucangBinding binding;

    private Context mContext;

    private CollectListAdapter adapter;

    private List<CollectInfo> CollectList;

    private Intent intent;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);

        mContext = this;

        CollectList = new ArrayList<>();

        binding = DataBindingUtil.setContentView(this, R.layout.activity_shoucang);

        binding.title.title.setText("我的服务员");

        binding.title.imgBack.setOnClickListener(this);

        binding.listLayout.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayout.VERTICAL));

        adapter = new CollectListAdapter(mContext, CollectList);

        binding.listLayout.recyclerView.setAdapter(adapter);

        adapter.setListItemListener(this);

        getData();

        binding.listLayout.xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh() {
                getData();
            }

            @Override
            public void onLoadMore(boolean isSilence) {

            }
        });
    }


    @Override
    public <T> void ok(T backMode, String jsonString, int httpTY) {
        binding.listLayout.xRefreshView.stopRefresh();
        binding.listLayout.xRefreshView.stopLoadMore();
        switch (httpTY) {
            case 1:
                ToastUtils.show("取消成功");
                break;
            case 2:
                try {
                    CollectListInfo info = (CollectListInfo) backMode;
                    if (info.getRows().size() > 0) {
                        CollectList.clear();
                        CollectList.addAll(info.getRows());
                        adapter.notifyDataSetChanged();
                        binding.listLayout.nullDataView.setVisibility(View.GONE);
                    }

                } catch (Exception e) {

                }

                break;
        }

    }


    @Override
    public void error(String e, int uriType) {
        ToastUtils.show(e);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
        }
    }

    private void getData() {
        MyRequest.POST(GET, new String[]{
                KEY.SESSIONID,
                "longitude",
                "latitude"
        }, new String[]{
                MyApplication.getInstance().getUserInfo().getRows().getSessionId(),
                MemoryBean.Longitude + "",
                MemoryBean.Latitude + ""

        }, GET_COLLEC_LIST, CollectListInfo.class.getName(), 2, this);
    }


    @Override
    public void Item(int position) {
        ToastUtils.show("正在开发中...您可以直接给服务人员打电话下单");
//        Intent intent = new Intent(mContext, SevenServiceActivity.class);
//        intent.putExtra("typeCode", CollectList.get(position).getUserType());
//        intent.putExtra("projectId", CollectList.get(position).getId());
//        intent.putExtra("projectName", CollectList.get(position).getRealName());
////        MemoryBean.serviceID=CollectList.get(position).get
//        MemoryBean.projectID = CollectList.get(position).getId();
//        startActivity(intent);
    }

    @Override
    public void ChildView(View v, int position) {
        switch (v.getId()) {
            case R.id.call:
                Intent phoneIntent = new Intent(
                        "android.intent.action.CALL", Uri.parse("tel:"
                        + CollectList.get(position).getPhone()));
                startActivity(phoneIntent);
                adapter.notifyDataSetChanged();
                break;
            case R.id.down_order:
                ToastUtils.show("正在开发中...您可以直接给服务人员打电话下单");
//                if (MyApplication.getInstance().isLogin()) {
//                    intent = new Intent(mContext, YuYueActivity.class);
//                    intent.putExtra("paotuiName", CollectList.get(position).getRealName());
//                    intent.putExtra("isXiaDan", "true");
//                    MemoryBean.serviceID = CollectList.get(position).getId();
//                } else {
//                    intent = new Intent(mContext, LoginActivity.class);
//                }
//                startActivity(intent);
                break;
            case R.id.appointment://预约
                ToastUtils.show("正在开发中...您可以直接给服务人员打电话下单");
//                if (MyApplication.getInstance().isLogin()) {
//                    intent = new Intent(mContext, YuYueActivity.class);
//                    intent.putExtra("paotuiName", CollectList.get(position).getRealName());
//                    MemoryBean.serviceID = CollectList.get(position).getId();
//                } else {
//                    intent = new Intent(mContext, LoginActivity.class);
//                }
//                startActivity(intent);
                break;
            case R.id.delete_collect:
                MyRequest.POST(POST, new String[]{"sessionId", "objectId", "objectType"},
                        new String[]{
                                MyApplication.getInstance().getUserInfo().getRows().getSessionId(),
                                CollectList.get(position).getId(),
                                "1"
                        }, DEL_COLLECT_LIST,
                        PublicMode.class.getName(), 1, this);
                CollectList.remove(position);
                adapter.notifyDataSetChanged();
                break;

        }

    }
}
