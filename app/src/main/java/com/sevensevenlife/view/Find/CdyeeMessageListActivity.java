package com.sevensevenlife.view.Find;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.text.TextUtils;
import android.view.View;

import com.sevensevenlife.http.MyRequest;
import com.sevensevenlife.interfaces.BindViewInterface;
import com.sevensevenlife.interfaces.MyHttpCallBack;
import com.sevensevenlife.model.httpmodel.CdyeeMessageListMode;
import com.sevensevenlife.model.httpmodel.CdyeeMessageStatusMode;
import com.sevensevenlife.model.httpmodel.CydeeMessageRowsBean;
import com.sevensevenlife.model.KEY;
import com.sevensevenlife.utils.MyApplication;
import com.sevensevenlife.utils.RecyclerViewUtils.PublicAdapter;
import com.sevensevenlife.utils.RecyclerViewUtils.RViewUtils;
import com.sevensevenlife.utils.ToastUtils;
import com.sevensevenlife.view.custumview.BaseActivity;
import com.sevensevenlife.view.custumview.LpWebActivity;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.CdyeeMessageListItemBinding;
import com.example.youxiangshenghuo.databinding.ListActivityBinding;

import java.util.ArrayList;
import java.util.List;

import static com.sevensevenlife.http.RequestUtils.GET;
import static com.sevensevenlife.http.RequestUtils.URI.CDYEE_MESSAGE;
import static com.sevensevenlife.http.RequestUtils.URI.GET_CDYEE_MESSAGE_STAUS;

/**
 * Created by Administrator on 2017/4/17 0017.
 */

public class CdyeeMessageListActivity extends BaseActivity implements MyHttpCallBack, View.OnClickListener, BindViewInterface {
    private ListActivityBinding binding;
    private RViewUtils listUtils;
    private List<CydeeMessageRowsBean> list = new ArrayList<>();
    private PublicAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.list_activity);
        binding.title.imgBack.setOnClickListener(this);
        binding.title.rightButton.setOnClickListener(this);
        binding.title.rightButton.setText("发帖");
        listUtils = new RViewUtils(mContext, binding.list);
        adapter = new PublicAdapter(list, R.layout.cdyee_message_list_item, this);
        listUtils.IsRefreshOrLoad(false)
                .setAdapter(adapter);
        getData();
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.right_button:
                startActivityForResult(new Intent(mContext, LeavingMessageActivity.class), 1);
                break;
        }

    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            if (requestCode == 1) {
                getData();
            }
        }
    }

    @Override
    public <T> void ok(T backMode, String jsonString, int httpTY) {
        switch (httpTY) {
            case 10:
                CdyeeMessageListMode mo = (CdyeeMessageListMode) backMode;
                list.clear();
                list.addAll(mo.getRows());
                adapter.notifyDataSetChanged();
                break;
            case 102:
                CdyeeMessageStatusMode mode = (CdyeeMessageStatusMode) backMode;

                if (!TextUtils.isEmpty(mode.getRows())) {
                    startActivity(new Intent(mContext, LpWebActivity.class)
                            .putExtra(KEY.URL, mode.getRows()));
                } else {
                    ToastUtils.show("暂无相关数据");
                }
                break;
        }
    }

    @Override
    public void error(String e, int uriType) {
        ToastUtils.show(e);

    }

    public void getData() {
        MyRequest.POST(GET, new String[]{"sessionId"}, new String[]{
                MyApplication.getInstance().getUserInfo().getRows().getSessionId()
        }, CDYEE_MESSAGE, CdyeeMessageListMode.class.getName(), 10, this);
    }

    private int ckposition = 0;

    @Override
    public void bandView(ViewDataBinding binding, final int position) {
        CdyeeMessageListItemBinding lbin = (CdyeeMessageListItemBinding) binding;
        lbin.tvTitle.setText(list.get(position).getTitle());
        lbin.tvData.setText(list.get(position).getCreate_time());
        lbin.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ckposition = position;
                MyRequest.POST(GET,
                        new String[]{"sessionId", "id"},
                        new String[]{MyApplication.getInstance().getUserInfo().getRows().getSessionId(),
                                list.get(position).getUrl()},
                        GET_CDYEE_MESSAGE_STAUS,
                        CdyeeMessageStatusMode.class.getName(),
                        102,
                        CdyeeMessageListActivity.this);
            }
        });
    }
}
