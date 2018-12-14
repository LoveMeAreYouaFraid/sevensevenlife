package com.sevensevenlife.view.Home.MyHome;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.sevensevenlife.http.MyRequest;
import com.sevensevenlife.interfaces.BindViewInterface;
import com.sevensevenlife.interfaces.MyHttpCallBack;
import com.sevensevenlife.model.httpmodel.BBSMode;
import com.sevensevenlife.model.httpmodel.BBSModeRowsBean;
import com.sevensevenlife.model.httpmodel.PublicMode;
import com.sevensevenlife.model.KEY;
import com.sevensevenlife.utils.ImgLoadUtils;
import com.sevensevenlife.utils.LogUtils;
import com.sevensevenlife.utils.MyApplication;
import com.sevensevenlife.utils.RecyclerViewUtils.PublicAdapter;
import com.sevensevenlife.utils.RecyclerViewUtils.RViewUtils;
import com.sevensevenlife.utils.RecyclerViewUtils.RecyclerItemMargin;
import com.sevensevenlife.utils.ToastUtils;
import com.sevensevenlife.utils.TxtUtils;
import com.sevensevenlife.view.custumview.BaseActivity;
import com.sevensevenlife.view.custumview.ImageViewActivity;
import com.sevensevenlife.view.DiaLog.MyProgressDialog;
import com.andview.refreshview.XRefreshView;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.BbsItemLayoutBinding;
import com.example.youxiangshenghuo.databinding.ListActivityBinding;
import com.example.youxiangshenghuo.databinding.UpImgListItemLayoutBinding;

import java.util.ArrayList;
import java.util.List;

import static com.sevensevenlife.http.RequestUtils.GET;
import static com.sevensevenlife.http.RequestUtils.POST_TWO;
import static com.sevensevenlife.http.RequestUtils.URI.BBS_LIST;
import static com.sevensevenlife.http.RequestUtils.URI.PRAISE_BBS;

/**
 * Created by Administrator on 2017/8/3 0003.
 */

public class BbsListActivity extends BaseActivity implements View.OnClickListener, BindViewInterface, MyHttpCallBack {

    private ListActivityBinding binding;
    private PublicAdapter adapter;
    private BBSMode mode;
    private List<BBSModeRowsBean> list = new ArrayList<>();
    private String pageNum = "1",
            pageSize = "10";
    private boolean oneHttp = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.list_activity);
        binding.title.title.setText("小区论坛");
        binding.title.imgBack.setOnClickListener(this);
        binding.title.rightButton.setOnClickListener(this);
        binding.title.rightButton.setText("发帖");
        adapter = new PublicAdapter(list, R.layout.bbs_item_layout, this);

        new RViewUtils(mContext, binding.list)
                .IsRefreshOrLoad(true)
                .setAdapter(adapter);
        binding.list.xRefreshView.setPullRefreshEnable(true);
        binding.list.xRefreshView.setPullLoadEnable(true);
        binding.list.recyclerView.addItemDecoration(new RecyclerItemMargin(3));
        binding.list.xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {

            @Override
            public void onRefresh() {
                super.onRefresh();
                list.clear();
                getData();
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                if (mode.getRows().size() != 10 && oneHttp) {
                    getData();
                } else {
                    ToastUtils.show("没有更多了~");
                    binding.list.xRefreshView.stopLoadMore();
                }

            }
        });

        if (getIntent().hasExtra(KEY.ID)) {
            getData();
        } else {
            ToastUtils.show("小区数据异常401");
        }

    }

    private void getData() {
        MyProgressDialog.getInstance().show(mContext, "数据加载中....");
        MyRequest.POST(GET,
                new String[]{"sessionId", "communityId", "pageNum", "pageSize"},
                new String[]{MyApplication.getInstance().getUserInfo().getRows().getSessionId(),
                        getIntent().getStringExtra(KEY.ID),
                        pageNum,
                        pageSize
                }, BBS_LIST, BBSMode.class.getName(), 10198, this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.right_button:
                startActivityForResult(new Intent(mContext, PostActivity.class), 998);
                break;
        }
    }

    @Override
    public void bandView(ViewDataBinding binding, final int position) {
        final BbsItemLayoutBinding bin = (BbsItemLayoutBinding) binding;
        bin.content.setText(list.get(position).getContent());
        bin.date.setText(list.get(position).getCreate_time());
        bin.title.setText(list.get(position).getTitle());
        ImgLoadUtils.Load(mContext, list.get(position).getHead_pic(), bin.tvImg, true);
        bin.tvUserName.setText(list.get(position).getNick_name());
        if (list.get(position).getReply_count().equals("0")) {
            bin.tvCommentNum.setText("快来抢沙发把~");
        } else {
            bin.tvCommentNum.setText(list.get(position).getReply_count());
        }
        if (!list.get(position).getPraise_count().equals("0")) {
            bin.tvFabulousNum.setText(list.get(position).getPraise_count());
        }
        bin.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, BbsDetailsActivity.class).putExtra("info", list.get(position)));
            }
        });
        if (list.get(position).getIsPraised().equals("0")) {
            bin.imgFabulous.setBackgroundResource(R.drawable.fabulous_off);
        } else {
            bin.imgFabulous.setBackgroundResource(R.drawable.fabulous_on);
        }
        final String[] picList = TxtUtils.split(list.get(position).getPicture());
        LogUtils.e(123, picList.length);
        if (!TextUtils.isEmpty(list.get(position).getPicture())) {
            if (picList.length == 0) {
                return;
            }
            if (picList.length == 1) {
                ImgLoadUtils.Load(mContext, picList[0], bin.imgDetail, false);
                bin.recyclerView.setVisibility(View.GONE);
                bin.imgDetail.setVisibility(View.VISIBLE);
            } else {
                bin.imgDetail.setVisibility(View.GONE);
                bin.recyclerView.setVisibility(View.VISIBLE);
                bin.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, LinearLayout.VERTICAL));
                bin.recyclerView.setAdapter(new PublicAdapter(picList.length, R.layout.up_img_list_item_layout, new BindViewInterface() {
                    @Override
                    public void bandView(ViewDataBinding binding, final int position) {
                        UpImgListItemLayoutBinding binding1 = (UpImgListItemLayoutBinding) binding;
                        ImgLoadUtils.Load(mContext, picList[position], binding1.imgImg, false);
                        binding1.imgImg.setOnClickListener(new View.OnClickListener() {
                            @Override
                            public void onClick(View v) {
                                ImageViewActivity.Show(mContext, picList[position]);
                            }
                        });
                    }
                }));
            }
        }

        bin.layoutComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, BbsDetailsActivity.class).putExtra("info", list.get(position)));
            }
        });
        bin.imgFabulous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (list.get(position).getIsPraised().equals("0")) {

                    String postJson = "{\"topic_id\":" + "\"" + list.get(position).getId() + "\"}";
                    MyRequest.POST(
                            POST_TWO,
                            new String[]{""},
                            new String[]{postJson},
                            PRAISE_BBS + "?sessionId=" + MyApplication.getInstance().getUserInfo().getRows().getSessionId(),
                            PublicMode.class.getName(),
                            10197, new MyHttpCallBack() {
                                @Override
                                public <T> void ok(T backMode, String jsonString, int httpTY) {
                                    switch (httpTY) {
                                        case 10197:
                                            bin.imgFabulous.setBackgroundResource(R.drawable.fabulous_on);
                                            list.get(position).setIsPraised("1");
                                            break;
                                    }
                                }

                                @Override
                                public void error(String e, int uriType) {
                                    ToastUtils.show(e);
                                }
                            });
                } else {
                    ToastUtils.show("您已经点赞过了~");
                }

            }
        });
        bin.imgDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                ImageViewActivity.Show(mContext, picList[0]);
            }
        });
    }

    @Override
    public <T> void ok(T backMode, String jsonString, int httpTY) {
        switch (httpTY) {
            case 10198:
                MyProgressDialog.getInstance().cancel();
                oneHttp = false;
                mode = (BBSMode) backMode;
                list.addAll(mode.getRows());
                adapter.notifyDataSetChanged();
                binding.list.xRefreshView.stopRefresh();
                binding.list.xRefreshView.stopRefresh();
                break;
        }

    }

    @Override
    public void error(String e, int uriType) {
        ToastUtils.show(e);
        MyProgressDialog.getInstance().cancel();
        binding.list.xRefreshView.stopRefresh();
        binding.list.xRefreshView.stopRefresh();
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK && requestCode == 998) {
            pageNum = "1";
            list.clear();
            getData();
        }
    }
}
