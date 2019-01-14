package com.sevensevenlife.view.Find.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sevensevenlife.interfaces.ListItemListener;
import com.sevensevenlife.model.httpmodel.RideTopListMode;
import com.sevensevenlife.utils.ImgLoadUtils;
import com.sevensevenlife.utils.LogUtils;
import com.sevensevenlife.utils.MyApplication;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.RidingRankingListItemBinding;

import java.util.List;

/**
 * Created by Administrator on 2017/3/15 0015.
 */

public class RidingRankingAdapter extends RecyclerView.Adapter<rrkViewHolder> {
    private RidingRankingListItemBinding binding;
    private List<RideTopListMode.RowsBean> list;
    private Context context;
    private ListItemListener listItemListener;

    public void setListItemListener(ListItemListener listItemListener) {
        this.listItemListener = listItemListener;
    }

    public RidingRankingAdapter(Context context, List<RideTopListMode.RowsBean> list) {
        this.list = list;
        this.context = context;
    }

    @Override
    public rrkViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.riding_ranking_list_item, parent, false);
        return new rrkViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(rrkViewHolder holder, final int position) {
        ImgLoadUtils.Load(context, list.get(position).getHead_pic(), holder.userHead, true);
        holder.tvOne.setText(list.get(position).getReal_name());
        holder.tvTwo.setText("骑行距离：" + list.get(position).getKm() + "km");
        holder.tvThree.setText("TOP " + position + 1);
        holder.tvFor.setText("减少碳排放：" + list.get(position).getScore() + "kg");


        if (list.get(position).getPhone().equals(MyApplication.getInstance().getUserInfo().getRows().getPhone())) {
            listItemListener.ChildView(holder.tvOne, position);
            LogUtils.e(123, "position" + position + "");
        }
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItemListener.Item(position);
            }
        });

    }

    @Override
    public int getItemCount() {
        return list.size();
    }
}

class rrkViewHolder extends RecyclerView.ViewHolder {
    ImageView userHead;
    TextView tvOne;
    TextView tvTwo;
    TextView tvThree;
    TextView tvFor;
    View rootView;

    public rrkViewHolder(View v) {
        super(v);
        userHead = (ImageView) v.findViewById(R.id.user_head);
        tvOne = (TextView) v.findViewById(R.id.tv_one);
        tvTwo = (TextView) v.findViewById(R.id.tv_two);
        tvThree = (TextView) v.findViewById(R.id.tv_three);
        tvFor = (TextView) v.findViewById(R.id.tv_for);
        rootView = v.findViewById(R.id.root_view);
    }
}