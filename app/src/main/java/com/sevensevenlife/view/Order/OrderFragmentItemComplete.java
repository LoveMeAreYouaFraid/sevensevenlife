package com.sevensevenlife.view.Order;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sevensevenlife.http.MyRequest;
import com.sevensevenlife.interfaces.DialogListener;
import com.sevensevenlife.interfaces.ListItemListener;
import com.sevensevenlife.interfaces.MyHttpCallBack;
import com.sevensevenlife.model.httpmodel.OrderListItemMode;
import com.sevensevenlife.model.httpmodel.OrderListMode;
import com.sevensevenlife.model.httpmodel.PublicMode;
import com.sevensevenlife.model.KEY;
import com.sevensevenlife.utils.MyApplication;
import com.sevensevenlife.utils.RecyclerViewUtils.RecyclerItemMargin;
import com.sevensevenlife.utils.ToastUtils;
import com.sevensevenlife.view.DiaLog.DialogUtils;
import com.sevensevenlife.view.Mian.Adapter.OrderFragmentListAdapter;
import com.sevensevenlife.view.User.LoginActivity;
import com.andview.refreshview.XRefreshView;
import com.example.youxiangshenghuo.DingDanInfo2Activity;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.ListLayoutBinding;
import com.sevensevenlife.utils.MemoryBean;

import java.util.ArrayList;
import java.util.List;

import static com.sevensevenlife.http.RequestUtils.GET;
import static com.sevensevenlife.http.RequestUtils.POST;
import static com.sevensevenlife.http.RequestUtils.URI.ADD_COLLECT;
import static com.sevensevenlife.http.RequestUtils.URI.GET_ORDER_LIST;

/**
 * Created by Administrator on 2017/2/3 0003.
 */

public class OrderFragmentItemComplete extends Fragment implements MyHttpCallBack, ListItemListener {

    private static OrderFragmentItemComplete instance;

    private static String subscript;

    private static int pageNum = 1;

    private ListLayoutBinding binding;

    private Context mContext;

    private List<OrderListItemMode> orderList;

    private OrderListMode mode;

    private OrderFragmentListAdapter adapter;

    private boolean isHaveData = true;

    private boolean isCache = true;

    public static OrderFragmentItemComplete getInstance(String onSubscript) {
        instance = new OrderFragmentItemComplete();
        subscript = onSubscript;
        return instance;

    }

    private void getData(String subscript, int pageNum) {
        if (!MyApplication.getInstance().isLogin()) {
            return;
        }
        MyRequest.POST(GET, new String[]{
                        KEY.SESSIONID, "pageNum", "pageSize", "status"
                },
                new String[]{
                        MyApplication.getInstance().getUserInfo().getRows().getSessionId(),
                        pageNum + "",
                        "10",
                        subscript,
                },
                GET_ORDER_LIST,
                OrderListMode.class.getName(),
                1,
                this, isCache);
    }

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        binding = DataBindingUtil.inflate(inflater, R.layout.list_layout, container, false);

        mContext = getActivity();

        orderList = new ArrayList<>();

        adapter = new OrderFragmentListAdapter(mContext, orderList, 2);

//        adapter.setCustomLoadMoreView(new XRefreshViewFooter(getActivity()));
        binding.xRefreshView.setPullLoadEnable(true);
        binding.xRefreshView.setPinnedTime(1000);
        binding.xRefreshView.setMoveForHorizontal(true);
        binding.recyclerView.setHasFixedSize(true);

        binding.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayout.VERTICAL));

        binding.recyclerView.addItemDecoration(new RecyclerItemMargin(16));

        binding.recyclerView.setAdapter(adapter);

        adapter.setListItemListener(this);


        binding.xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh() {
                if (!MyApplication.getInstance().isLogin()) {
                    binding.xRefreshView.stopRefresh();
                    return;
                }

                oneStart = false;
                pageNum = 1;
                isCache = false;
                getData(subscript, pageNum);

            }

            @Override
            public void onLoadMore(boolean isSilence) {
                if (!MyApplication.getInstance().isLogin()) {
                    binding.xRefreshView.stopLoadMore();
                    return;
                }
                if (isHaveData) {
                    pageNum++;
                    getData(subscript, pageNum);
                } else {
                    binding.xRefreshView.setPullLoadEnable(false);
                    adapter.notifyDataSetChanged();
                }


            }
        });
        return binding.getRoot();
    }

    private boolean oneStart = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (oneStart && isVisibleToUser) {
            binding.xRefreshView.startRefresh();
        }
    }

    @Override
    public <T> void ok(T backMode, String jsonString, int httpTY) {

        switch (httpTY) {
            case 1:
                mode = (OrderListMode) backMode;
                if (pageNum == 1) {
                    orderList.clear();
                }
                if (mode.getRows() != null && mode.getRows().size() != 0) {
                    orderList.addAll(mode.getRows());
                    adapter.notifyDataSetChanged();

                } else {
                    isHaveData = false;
                }
                if (orderList.size() == 0) {
                    binding.nullDataView.setVisibility(View.VISIBLE);
                } else {
                    binding.nullDataView.setVisibility(View.GONE);
                }

                binding.xRefreshView.stopRefresh();
                binding.xRefreshView.stopLoadMore();

                break;
            case 11:
                ToastUtils.show("收藏成功");
                break;
        }

    }

    @Override
    public void error(String e, int uriType) {
        binding.xRefreshView.stopRefresh();
        binding.xRefreshView.stopLoadMore();
        ToastUtils.show(e);
    }

    @Override
    public void Item(int position) {

        MemoryBean.projectName = orderList.get(position).getProject_name();
        Intent intent = new Intent(mContext, DingDanInfo2Activity.class);
        MemoryBean.projectName = orderList.get(position).getProject_name();
        intent.putExtra("OrderNo", orderList.get(position).getOrder_no());
        Bundle bundle = new Bundle();
        bundle.putSerializable("orderInfo", orderList.get(position));
        intent.putExtras(bundle);
        mContext.startActivity(intent);

    }


    @Override
    public void ChildView(View v, final int position) {
        if (!MyApplication.getInstance().isLogin()) {
            DialogUtils.init(getActivity())
                    .setTitle("尚未登陆，是否登录?")
                    .setOne("登录", new DialogListener() {
                        @Override
                        public void buttType(int ButtType) {
                            startActivityForResult(new Intent(getActivity(), LoginActivity.class), 99);
                        }
                    });
            return;
        }
        switch (v.getId()) {

            case R.id.collection:
                MyRequest.POST(POST, new String[]{KEY.SESSIONID, "objectId", "objectType", "projectId"},
                        new String[]{MyApplication.getInstance().getUserInfo().getRows().getSessionId(),
                                orderList.get(position).getService_id(), "1", orderList.get(position).getProject_id()}, ADD_COLLECT, PublicMode.class.getName()
                        , 11, this);
                break;
            case R.id.comment:
                Intent intent = new Intent(mContext, PingFenActivity.class);
                intent.putExtra("orderInfo", mode.getRows().get(position));
                mContext.startActivity(intent);
                break;
        }

    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            switch (requestCode) {
                case 99:
                    pageNum = 1;
                    getData(subscript, pageNum);
                    break;
            }
        }
    }
}
