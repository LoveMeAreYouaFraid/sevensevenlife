package com.sevensevenlife.view.Home.PublicBicycle.Adapter;

import android.content.Context;
import android.support.v7.widget.RecyclerView;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;


import com.sevensevenlife.interfaces.ListItemListener;
import com.sevensevenlife.utils.ImgLoadUtils;
import com.example.youxiangshenghuo.R;

import java.util.List;

/**
 * Created by 10237 on 2016/12/9.
 */

public class FaultRepairAdapter extends RecyclerView.Adapter<FaultRepairAdapter.MasonryView> {
    private Context mContext;
    private MasonryView view;
    private List<String> imgUrl;
    public ListItemListener onItemClickListener;

    public FaultRepairAdapter(Context context, List<String> imgUrl) {
        mContext = context;
        this.imgUrl = imgUrl;

    }

    @Override
    public MasonryView onCreateViewHolder(ViewGroup parent, int viewType) {
        View v = LayoutInflater.from(parent.getContext()).inflate(R.layout.img_layout, parent, false);
        view = new MasonryView(v);
        return view;
    }

    @Override
    public void onBindViewHolder(MasonryView holder, final int position) {

        if (position != 0) {
            ImgLoadUtils.Load(mContext, imgUrl.get(position - 1), view.img, false);
        }

        view.img.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                onItemClickListener.Item(position);
            }
        });
    }

    @Override
    public int getItemCount() {
        return imgUrl.size() + 1;
    }

    public class MasonryView extends RecyclerView.ViewHolder {

        ImageView img;

        public MasonryView(View v) {
            super(v);
            img = (ImageView) v.findViewById(R.id.img);
        }

    }

    public void setOnItemClickListener(ListItemListener listener) {
        this.onItemClickListener = listener;
    }
}
