package com.sevensevenlife.utils.RecyclerViewUtils;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.widget.LinearLayout;

import com.andview.refreshview.XRefreshView;
import com.andview.refreshview.XRefreshViewFooter;
import com.example.youxiangshenghuo.databinding.ListLayoutBinding;

/**
 * RecyclerView  通用方法
 * Created by Administrator on 2017/3/16 0016.
 */

public class RViewUtils {
    private RecyclerView r;
    private Context context;
    private XRefreshView x;
    private PublicAdapter adapter;
    private ListLayoutBinding binding;


    public RViewUtils(Context context, RecyclerView r, XRefreshView x) {
        this.r = r;
        this.x = x;
        this.context = context;
    }

    public RViewUtils(Context context, ListLayoutBinding binding) {
        this.context = context;
        this.binding = binding;
        this.r = binding.recyclerView;
        this.x = binding.xRefreshView;
    }

    public RViewUtils setLayout(int spanCount, int orientation) {
        r.setLayoutManager(new StaggeredGridLayoutManager(spanCount, orientation));
        return this;
    }

    public RViewUtils setLayoutMg(RecyclerView.LayoutManager layoutMg) {
        r.setLayoutManager(layoutMg);
        return this;
    }


    public RViewUtils setAdapter(PublicAdapter adapters) {
        if (r.getLayoutManager() == null) {
            r.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayout.VERTICAL));
        }
        adapter = adapters;
        r.setAdapter(adapter);
        if (x != null) {
            x.setPullLoadEnable(false);
            x.setPullRefreshEnable(false);
            x.setMoveForHorizontal(false);
            x.setAutoLoadMore(false);
        }
        r.setHasFixedSize(false);
        return this;
    }

    public RViewUtils IsRefreshOrLoad(boolean IsRefresh) {
        x.stopRefresh();
        x.stopLoadMore();
        x.setPullRefreshEnable(IsRefresh);
        x.setPullLoadEnable(IsRefresh);
        if (IsRefresh) {
            x.setPinnedTime(100);
            if (adapter != null)
                adapter.setCustomLoadMoreView(new XRefreshViewFooter(context));
        }
        return this;
    }
}
