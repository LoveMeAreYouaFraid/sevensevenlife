package com.sevensevenlife.view.Home.MyHome;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.view.View;

import com.sevensevenlife.http.MyRequest;
import com.sevensevenlife.interfaces.BindViewInterface;
import com.sevensevenlife.interfaces.MyHttpCallBack;
import com.sevensevenlife.model.httpmodel.BbsReplyListMode;
import com.sevensevenlife.model.httpmodel.BbsReplyListRowsBean;
import com.sevensevenlife.model.httpmodel.PublicMode;
import com.sevensevenlife.utils.ImgLoadUtils;
import com.sevensevenlife.utils.MyApplication;
import com.sevensevenlife.utils.RecyclerViewUtils.PublicAdapter;
import com.sevensevenlife.utils.RecyclerViewUtils.RViewUtils;
import com.sevensevenlife.utils.RecyclerViewUtils.RecyclerItemMargin;
import com.sevensevenlife.utils.ToastUtils;
import com.sevensevenlife.view.custumview.BaseActivity;
import com.sevensevenlife.view.DiaLog.MyProgressDialog;
import com.andview.refreshview.XRefreshView;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.BbsCommentListItemLayoutBinding;
import com.example.youxiangshenghuo.databinding.ListActivityBinding;

import java.util.ArrayList;
import java.util.List;

import static com.sevensevenlife.http.RequestUtils.GET;
import static com.sevensevenlife.http.RequestUtils.POST_TWO;
import static com.sevensevenlife.http.RequestUtils.URI.GET_BBS_REPLY_LIST;
import static com.sevensevenlife.http.RequestUtils.URI.PRAISE_BBS;

/**
 * Created by Administrator on 2017/8/16 0016.
 */

public class AllCommentActivity extends BaseActivity implements MyHttpCallBack, BindViewInterface {
    private ListActivityBinding binding;
    private String pageNum = "1";
    private List<BbsReplyListRowsBean> rows = new ArrayList<>();
    private BbsReplyListMode mode;
    private boolean oneHttp = true;
    private PublicAdapter adapter;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.list_activity);
        binding.title.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
        adapter = new PublicAdapter(rows, R.layout.bbs_comment_list_item_layout, this);
        new RViewUtils(mContext, binding.list).setAdapter(adapter);
        binding.title.title.setText("所有评论");
        binding.list.xRefreshView.setPullRefreshEnable(true);
        binding.list.xRefreshView.setPullLoadEnable(true);
        binding.list.recyclerView.addItemDecoration(new RecyclerItemMargin(3));
        binding.list.xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh() {
                super.onRefresh();
                rows.clear();
                getCommentList();
            }

            @Override
            public void onLoadMore(boolean isSilence) {
                super.onLoadMore(isSilence);
                if (mode != null && mode.getRows() != null && oneHttp) {
                    if (mode.getRows().size() < 10) {
                        ToastUtils.show("没有更多数据了");
                        binding.list.xRefreshView.stopLoadMore();
                    } else {
                        getCommentList();
                    }
                } else {
                    getCommentList();
                }

            }
        });
        binding.list.xRefreshView.setPullRefreshEnable(true);
        binding.list.xRefreshView.setPullLoadEnable(true);
        getCommentList();
    }

    public static void show(Context mContext, String topicId) {
        mContext.startActivity(new Intent(mContext, AllCommentActivity.class).putExtra("topicId", topicId));
    }


    @Override
    public void bandView(ViewDataBinding binding, final int position) {
        final BbsCommentListItemLayoutBinding bin = (BbsCommentListItemLayoutBinding) binding;
        ImgLoadUtils.Load(mContext, rows.get(position).getHead_pic(), bin.imgUserHoder, true);
        bin.tvUserName.setText(rows.get(position).getNick_name());
        bin.tvCommentTime.setText(rows.get(position).getCreate_time());
        bin.tvCommentDetail.setText(rows.get(position).getReply_message());
        if (!rows.get(position).getPraise_count().equals("0")) {
            bin.tvFabulousNum.setText(rows.get(position).getPraise_count());
        } else {
            bin.tvFabulousNum.setText("");
        }
        if (rows.get(position).getIsPraised().equals("0")) {
            bin.imgFabulous.setBackgroundResource(R.drawable.fabulous_off);
        } else {
            bin.imgFabulous.setBackgroundResource(R.drawable.fabulous_on);
        }
        bin.imgFabulous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                String postJson = "{\"reply_id\":" + "\"" + rows.get(position).getId() + "\"}";
                if (rows.get(position).getIsPraised().equals("0")) {
                    MyRequest.POST(
                            POST_TWO,
                            new String[]{""},
                            new String[]{postJson},
                            PRAISE_BBS + "?sessionId=" + MyApplication.getInstance().getUserInfo().getRows().getSessionId(),
                            PublicMode.class.getName(),
                            10193, new MyHttpCallBack() {
                                @Override
                                public <T> void ok(T backMode, String jsonString, int httpTY) {
                                    switch (httpTY) {
                                        case 10193:
                                            bin.imgFabulous.setBackgroundResource(R.drawable.fabulous_on);
                                            rows.get(position).setIsPraised("1");
                                            ToastUtils.show("点赞成功~");
                                            rows.clear();
                                            getCommentList();
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
    }

    @Override
    public <T> void ok(T backMode, String jsonString, int httpTY) {
        switch (httpTY) {
            case 12450:
                oneHttp = false;
                MyProgressDialog.getInstance().cancel();
                binding.list.xRefreshView.stopLoadMore();
                binding.list.xRefreshView.stopRefresh();
                mode = (BbsReplyListMode) backMode;
                rows.addAll(mode.getRows());
                adapter.notifyDataSetChanged();
                break;
        }

    }

    @Override
    public void error(String e, int uriType) {
        ToastUtils.show(e);
        MyProgressDialog.getInstance().cancel();
        binding.list.xRefreshView.stopLoadMore();
        binding.list.xRefreshView.stopRefresh();
    }

    private void getCommentList() {
        MyProgressDialog.getInstance().showLoding(mContext);
        MyRequest.POST(GET,
                new String[]{"sessionId", "topicId", "pageNum", "pageSize"},
                new String[]{MyApplication.getInstance().getUserInfo().getRows().getSessionId(),
                        getIntent().getStringExtra("topicId"), pageNum, "10"},
                GET_BBS_REPLY_LIST,
                BbsReplyListMode.class.getName(),
                12450,
                this);
    }
}
