package com.sevensevenlife.view.Home.PublicBicycle.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.sevensevenlife.model.httpmodel.bikeListmode;
import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.example.youxiangshenghuo.R;

import java.util.List;

/**
 * Created by 10237 on 2016/12/25.
 */

public class LoseCarListAdapter extends BaseRecyclerAdapter<mViewHolder> {
    private List<bikeListmode> bikeList;
    private Context mContext;

    public LoseCarListAdapter(Context context, List<bikeListmode> bikeList) {
        this.mContext = context;
        this.bikeList = bikeList;
    }


    @Override
    public mViewHolder getViewHolder(View view) {
        return new mViewHolder(view);
    }

    @Override
    public mViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        View view = View.inflate(parent.getContext(), R.layout.lose_car_activity_list_item, null);
        return new mViewHolder(view);
    }

    @Override
    public void onBindViewHolder(mViewHolder holder, int position, boolean isItem) {
        if (bikeList.get(position).getBikestate().equals("1001")) {
            holder.tvState.setText("已丢失");
        } else {
            holder.tvState.setText("已找回");
        }
        holder.carNum.setText("车身编号：" + bikeList.get(position).getBikesn());

        String data = bikeList.get(position).getLosedate().replaceAll("00:00:00", "");

        holder.tvData.setText("丢失日期：" + data);

        holder.tvAddress.setText("丢失地点：" + bikeList.get(position).getSitename());

    }


    @Override
    public int getAdapterItemCount() {
        return bikeList.size();
    }

}

class mViewHolder extends RecyclerView.ViewHolder {
    public TextView tvData, tvAddress, carNum, tvState;

    public mViewHolder(View v) {
        super(v);
        tvData = (TextView) v.findViewById(R.id.tv_data);
        tvAddress = (TextView) v.findViewById(R.id.tv_address);
        carNum = (TextView) v.findViewById(R.id.car_num);
        tvState = (TextView) v.findViewById(R.id.tv_state);

    }

}