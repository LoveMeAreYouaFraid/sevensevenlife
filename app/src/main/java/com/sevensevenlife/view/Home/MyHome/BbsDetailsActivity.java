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
import com.sevensevenlife.interfaces.EditDialogListener;
import com.sevensevenlife.interfaces.MyHttpCallBack;
import com.sevensevenlife.model.httpmodel.BBSModeRowsBean;
import com.sevensevenlife.model.httpmodel.BbsDetailMode;
import com.sevensevenlife.model.httpmodel.BbsReplyListMode;
import com.sevensevenlife.model.httpmodel.BbsReplyListRowsBean;
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
import com.sevensevenlife.view.DiaLog.EdItDialog;
import com.andview.refreshview.XRefreshView;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.BbsCommentListItemLayoutBinding;
import com.example.youxiangshenghuo.databinding.BbsDetailsActivityLayoutBinding;
import com.example.youxiangshenghuo.databinding.UpImgListItemLayoutBinding;

import java.util.ArrayList;
import java.util.List;

import static com.sevensevenlife.http.RequestUtils.GET;
import static com.sevensevenlife.http.RequestUtils.POST_TWO;
import static com.sevensevenlife.http.RequestUtils.URI.GET_BBS_DETAILS;
import static com.sevensevenlife.http.RequestUtils.URI.GET_BBS_REPLY_LIST;
import static com.sevensevenlife.http.RequestUtils.URI.PRAISE_BBS;
import static com.sevensevenlife.http.RequestUtils.URI.REPLY_BBS_TOPIC;

/**
 * Created by Administrator on 2017/8/9 0009.
 */

public class BbsDetailsActivity extends BaseActivity implements View.OnClickListener, BindViewInterface, MyHttpCallBack {
    private BbsDetailsActivityLayoutBinding binding;
    private BBSModeRowsBean item;
    private PublicAdapter adapter;
    private String postJson;
    private String pageNum = "1";
    private List<BbsReplyListRowsBean> rows = new ArrayList<>();
    private BbsReplyListMode mode;
    private boolean oneHttp = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.bbs_details_activity_layout);
        binding.title.imgBack.setOnClickListener(this);
        item = (BBSModeRowsBean) getIntent().getSerializableExtra("info");
        binding.title.title.setText("详情");

        adapter = new PublicAdapter(rows, R.layout.bbs_comment_list_item_layout, this);
        new RViewUtils(mContext, binding.recyclerViewComment, binding.xRefreshView)
                .IsRefreshOrLoad(true).setAdapter(adapter);
        binding.xRefreshView.setPullRefreshEnable(true);
        binding.xRefreshView.setPullLoadEnable(true);
        binding.recyclerViewComment.addItemDecoration(new RecyclerItemMargin(3));
        binding.xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
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
                        binding.xRefreshView.stopLoadMore();
                    } else {
                        getCommentList();
                    }
                } else {
                    getCommentList();
                }

            }
        });
        initView();
        MyRequest.POST(GET,
                new String[]{"sessionId", "topicId"},
                new String[]{MyApplication.getInstance().getUserInfo().getRows().getSessionId(),
                        item.getId()},
                GET_BBS_DETAILS,
                BbsDetailMode.class.getName(),
                12451,
                this);
    }

    private void initView() {
        binding.content.setText(item.getContent());
        binding.date.setText(item.getCreate_time());
        binding.tvTitle.setText(item.getTitle());
        ImgLoadUtils.Load(mContext, item.getHead_pic(), binding.tvImg, true);
        binding.tvUserName.setText(item.getNick_name());
        if (item.getReply_count().equals("0")) {
            binding.tvCommentNum.setText("快来抢沙发把~");
        } else {
            binding.tvCommentNum.setText(item.getReply_count());
        }
        if (!item.getPraise_count().equals("0")) {
            binding.tvFabulousNum.setText(item.getPraise_count());
        }

        if (item.getIsPraised().equals("0")) {
            binding.imgFabulous.setBackgroundResource(R.drawable.fabulous_off);
        } else {
            binding.imgFabulous.setBackgroundResource(R.drawable.fabulous_on);
        }
        final String[] picList = TxtUtils.split(item.getPicture());
        LogUtils.e(123, picList.length);
        if (!TextUtils.isEmpty(item.getPicture())) {
            if (picList.length == 0) {
                return;
            }
            if (picList.length == 1) {
                ImgLoadUtils.Load(mContext, picList[0], binding.imgDetail, false);
                binding.recyclerView.setVisibility(View.GONE);
                binding.imgDetail.setVisibility(View.VISIBLE);
                binding.imgDetail.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        ImageViewActivity.Show(mContext, picList[0]);
                    }
                });
            } else {
                binding.imgDetail.setVisibility(View.GONE);
                binding.recyclerView.setVisibility(View.VISIBLE);
                binding.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, LinearLayout.VERTICAL));
                binding.recyclerView.setAdapter(new PublicAdapter(picList.length, R.layout.up_img_list_item_layout, new BindViewInterface() {
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
        binding.tvAllComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AllCommentActivity.show(mContext, item.getId());
            }
        });
        binding.layoutComment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                EdItDialog.show(mContext, new EditDialogListener() {
                    @Override
                    public void click(int type, String[] data) {
                        if (TextUtils.isEmpty(data[0])) {
                            ToastUtils.show("请填写你的评论在提交~");
                            return;
                        }
                        String replyJson = "{\"" + "topic_id" + "\":" + "\"" + item.getId() + "\"," +
                                "\"" + "reply_message" + "\":" + "\"" + data[0] + "\"" + "}";
                        LogUtils.e(123, replyJson);
                        MyRequest.POST(POST_TWO,
                                new String[]{""},
                                new String[]{replyJson},
                                REPLY_BBS_TOPIC + "?sessionId=" + MyApplication.getInstance().getUserInfo().getRows().getSessionId(),
                                PublicMode.class.getName(),
                                10854,
                                BbsDetailsActivity.this);
                    }
                }, 5);
            }
        });
        binding.imgFabulous.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                postJson = "{\"topic_id\":" + "\"" + item.getId() + "\"}";
                if (item.getIsPraised().equals("0")) {
                    fabulous();
                } else {
                    ToastUtils.show("您已经点赞过了~");
                }
            }


        });
        binding.voteBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startActivity(new Intent(mContext, VoteDetailActivity.class)
                        .putExtra(KEY.ID, item.getId() + ""));
            }
        });
    }

    private void fabulous() {

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
                                binding.imgFabulous.setBackgroundResource(R.drawable.fabulous_on);
                                try {
                                    int num = Integer.valueOf(binding.tvFabulousNum.getText() + "");
                                    binding.tvFabulousNum.setText(num + 1);
                                } catch (Exception e) {
                                    binding.tvFabulousNum.setText("");
                                }
                                item.setIsPraised("1");
                                break;
                        }
                    }

                    @Override
                    public void error(String e, int uriType) {
                        ToastUtils.show(e);
                    }
                });
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
                postJson = "{\"reply_id\":" + "\"" + rows.get(position).getId() + "\"}";
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
            case 10854:
                ToastUtils.show("提交成功");
                getCommentList();
                rows.clear();
                break;
            case 12451:
                try {
                    BbsDetailMode BbsDetail = (BbsDetailMode) backMode;
                    if (BbsDetail.getRows().getVote() == null) {
                        binding.voteBt.setVisibility(View.GONE);
                    }else {
                        binding.voteBt.setVisibility(View.VISIBLE);
                    }
                } catch (Exception x) {
                }
                getCommentList();
                break;
            case 12450:
                oneHttp = false;
                binding.xRefreshView.stopLoadMore();
                binding.xRefreshView.stopRefresh();
                mode = (BbsReplyListMode) backMode;
                rows.addAll(mode.getRows());
                if (rows.size() > 0) {
                    binding.tvAllComment.setVisibility(View.VISIBLE);
                }
                adapter.notifyDataSetChanged();
                break;
        }

    }

    @Override
    public void error(String e, int uriType) {
        ToastUtils.show(e);
        binding.xRefreshView.stopLoadMore();
        binding.xRefreshView.stopRefresh();
    }


    private void getCommentList() {
        MyRequest.POST(GET,
                new String[]{"sessionId", "topicId", "pageNum", "pageSize"},
                new String[]{MyApplication.getInstance().getUserInfo().getRows().getSessionId(),
                        item.getId(), pageNum, "10"},
                GET_BBS_REPLY_LIST,
                BbsReplyListMode.class.getName(),
                12450,
                this);
    }
}
