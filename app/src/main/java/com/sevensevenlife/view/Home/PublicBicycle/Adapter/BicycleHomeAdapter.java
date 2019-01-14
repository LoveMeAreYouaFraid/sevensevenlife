package com.sevensevenlife.view.Home.PublicBicycle.Adapter;

import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sevensevenlife.interfaces.ListItemListener;
import com.example.youxiangshenghuo.R;

/**
 * Created by 10237 on 2016/12/23.
 */

public class BicycleHomeAdapter extends RecyclerView.Adapter<SimpleAdapterViewHolder> {
    private ListItemListener listener;

    public void setListItemListener(ListItemListener listener) {
        this.listener = listener;
    }

    private int[] imgIds = new int[]{
            R.mipmap.zcfs,
            R.mipmap.qxxx,
            R.mipmap.zfb,
            R.mipmap.zbzd,
            R.mipmap.sczh,
            R.mipmap.zckye,
            R.mipmap.gzbx,
            R.mipmap.yjcz};
    private String[] tvs = new String[]{
            "租车方式",
            "骑行信息",
            "支付宝扫码",
            "周边站点",
            "失车找回",
            "租车卡余额",
            "故障报修",
            "押金充值"};

    @Override
    public SimpleAdapterViewHolder onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(
                R.layout.bicycle_home_list_item, parent, false);
        SimpleAdapterViewHolder vh = new SimpleAdapterViewHolder(v);
        return vh;
    }

    @Override
    public void onBindViewHolder(final SimpleAdapterViewHolder holder, final int position) {
        holder.img.setBackgroundResource(imgIds[position]);
        holder.tv.setText(tvs[position]);
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View view) {
                listener.ChildView(holder.tv, position);
            }
        });

    }


    @Override
    public int getItemCount() {
        return imgIds.length;
    }

}

class SimpleAdapterViewHolder extends RecyclerView.ViewHolder {

    public ImageView img;
    public TextView tv;
    public View rootView;

    public SimpleAdapterViewHolder(View v) {
        super(v);

        img = (ImageView) v
                .findViewById(R.id.img);
        tv = (TextView) v
                .findViewById(R.id.tv);
        rootView = v.findViewById(R.id.root_view);


    }
}
