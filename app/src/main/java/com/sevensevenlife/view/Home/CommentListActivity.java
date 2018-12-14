package com.sevensevenlife.view.Home;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;
import android.widget.LinearLayout;

import com.sevensevenlife.http.MyRequest;
import com.sevensevenlife.interfaces.MyHttpCallBack;
import com.sevensevenlife.model.httpmodel.CommentListMode;
import com.sevensevenlife.utils.MyApplication;
import com.sevensevenlife.utils.RecyclerViewUtils.RecyclerItemMargin;
import com.sevensevenlife.utils.ToastUtils;
import com.sevensevenlife.view.custumview.BaseActivity;
import com.sevensevenlife.view.Home.Adapter.CommentListAdapter;
import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.CommentListActivityLayoutBinding;
import com.sevensevenlife.utils.MemoryBean;

import java.util.ArrayList;
import java.util.List;

import static com.sevensevenlife.http.RequestUtils.GET;
import static com.sevensevenlife.http.RequestUtils.URI.GET_APPRAISAL_LIST;

public class CommentListActivity extends BaseActivity implements MyHttpCallBack, View.OnClickListener {

    private CommentListActivityLayoutBinding binding;
    private int pageNum = 1;
    private int pageSize = 20;
    private List<CommentListMode.RowsBean> list;
    private CommentListAdapter adapter;
    private String star = "";
    private boolean isHaveData = true;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        list = new ArrayList<>();
        binding = DataBindingUtil.setContentView(this, R.layout.comment_list_activity_layout);
        binding.title.imgBack.setOnClickListener(this);
        binding.allComment.setOnClickListener(this);
        binding.lackComment.setOnClickListener(this);
        binding.goodComment.setOnClickListener(this);
        binding.list.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayout.VERTICAL));
        adapter = new CommentListAdapter(mContext, list);
        adapter.setCustomLoadMoreView(new XRefreshViewFooter(mContext));
        binding.list.xRefreshView.setPinnedTime(1500);
        binding.list.recyclerView.addItemDecoration(new RecyclerItemMargin(true));
        binding.list.recyclerView.setAdapter(adapter);
        getData("");
        binding.list.xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh() {
                if (!MyApplication.getInstance().isLogin()) {
                    binding.list.xRefreshView.stopRefresh();
                    return;
                }
                pageNum = 1;
                getData(star);

            }

            @Override
            public void onLoadMore(boolean isSilence) {
                if (!MyApplication.getInstance().isLogin()) {
                    binding.list.xRefreshView.stopLoadMore();
                    return;
                }
                if (isHaveData) {
                    pageNum++;
                    getData(star);
                } else {

                    binding.list.xRefreshView.setPullLoadEnable(false);
                    adapter.notifyDataSetChanged();
                }


            }
        });
    }

    private void getData(String star) {

        MyRequest.POST(GET, new String[]{"serviceId", "pageNum", "pageSize", "star"},
                new String[]{MemoryBean.serviceID,
                        pageNum + "",
                        pageSize + "",
                        star + ""}, GET_APPRAISAL_LIST, CommentListMode.class.getName(), 10, this);
    }

    @Override
    public <T> void ok(T backMode, String jsonString, int httpTY) {
        switch (httpTY) {
            case 10:
                binding.list.xRefreshView.stopRefresh();
                binding.list.xRefreshView.stopLoadMore();
                CommentListMode mode = (CommentListMode) backMode;
//                if (pageNum == 1) {
                list.clear();
//                }
                if (mode.getRows() != null && mode.getRows().size() != 0) {
                    list.addAll(mode.getRows());
                } else {
                    isHaveData = false;
                }
                adapter.notifyDataSetChanged();
                break;
        }

    }

    @Override
    public void error(String e, int uriType) {
        ToastUtils.show(e);
        binding.list.xRefreshView.stopRefresh();
        binding.list.xRefreshView.stopLoadMore();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.all_comment:
                binding.allComment.setBackgroundResource(R.drawable.bg_red_fillet);
                binding.goodComment.setBackgroundResource(R.drawable.bg_white_fillet);
                binding.lackComment.setBackgroundResource(R.drawable.bg_white_fillet);
                star = "";
                pageNum = 1;
                getData(star);
                break;
            case R.id.lack_comment:
                binding.allComment.setBackgroundResource(R.drawable.bg_white_fillet);
                binding.goodComment.setBackgroundResource(R.drawable.bg_white_fillet);
                binding.lackComment.setBackgroundResource(R.drawable.bg_red_fillet);
                star = "1";
                pageNum = 1;
                getData(star);
                break;
            case R.id.good_comment:
                binding.allComment.setBackgroundResource(R.drawable.bg_white_fillet);
                binding.goodComment.setBackgroundResource(R.drawable.bg_red_fillet);
                binding.lackComment.setBackgroundResource(R.drawable.bg_white_fillet);
                star = "5";
                pageNum = 1;
                getData(star);
                break;
        }

    }
}
