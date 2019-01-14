package com.sevensevenlife.view.Home.Adapter;

import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.TextView;

import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.SevenServiceListItemBinding;
import com.sevensevenlife.interfaces.ListItemListener;
import com.sevensevenlife.model.httpmodel.ServiceListRows;
import com.sevensevenlife.utils.DPIUtil;
import com.sevensevenlife.utils.ImgLoadUtils;
import com.sevensevenlife.utils.TextViewParser;
import com.sevensevenlife.view.custumview.CircleImageView;

import java.util.List;

/**
 * Created by Administrator on 2017/3/6 0006.
 */

public class SevenServiceAdapter extends BaseRecyclerAdapter<SevenServiceViewHolder> {
    private List<ServiceListRows> rowses;
    private SevenServiceListItemBinding binding;
    private String[] workStatus = new String[]{"", "空闲", "忙碌", "离线"};
    private String[] authenticateStatus = new String[]{"未认证", "七七认证", ""};

    private ListItemListener listItemListener;

    public SevenServiceAdapter(List<ServiceListRows> rowses) {
        this.rowses = rowses;
    }

    @Override
    public SevenServiceViewHolder getViewHolder(View view) {
        return new SevenServiceViewHolder(view);
    }

    @Override
    public SevenServiceViewHolder onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.seven_service_list_item, parent, false);
        return new SevenServiceViewHolder(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(SevenServiceViewHolder holder, final int position, boolean isItem) {
        ServiceListRows info = rowses.get(position);
        ImgLoadUtils.Load(holder.servicePic.getContext(), info.getHeadPic(), holder.servicePic, true);
        holder.serviceType.setText(workStatus[Integer.valueOf(info.getWorkStatus())]);
        switch (info.getWorkStatus()) {
            case "1":
                holder.call.setVisibility(View.VISIBLE);
                holder.appointment.setVisibility(View.VISIBLE);
                holder.downOrder.setVisibility(View.VISIBLE);
                break;
            case "2":
                holder.downOrder.setVisibility(View.GONE);
                break;
            case "3":
                holder.call.setVisibility(View.GONE);
                holder.appointment.setVisibility(View.GONE);
                holder.downOrder.setVisibility(View.GONE);
                break;

        }
        holder.serviceName.setText(info.getRealName());
        holder.address.setText(info.getCurrentAddress());
        String mNum;
        if (!TextUtils.isEmpty(info.getDistance() + "")) {
            if (Integer.valueOf(info.getDistance()) > 999) {
                mNum = (Integer.valueOf(info.getDistance()) / 1000) + "" + "km";
            } else {
                mNum = info.getDistance() + "" + "m";
            }
            holder.decode.setText(mNum);
        }

        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItemListener.Item(position);
            }
        });
        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItemListener.ChildView(v, position);
            }
        });
        holder.downOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItemListener.ChildView(v, position);
            }
        });
        holder.appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItemListener.ChildView(v, position);
            }
        });
        TextViewParser textViewParser = new TextViewParser();
        textViewParser.append("综合评分：", DPIUtil.dip2px(14),
                ContextCompat.getColor(holder.serviceDetail.getContext(), R.color.txt_black));

        String branch;
        if (!TextUtils.isEmpty(info.getAppraisalTotal() + "")) {
            branch = info.getAppraisalTotal() + "\n";
        } else {
            branch = "暂\n";
        }
        textViewParser.append(branch, DPIUtil.dip2px(14),
                ContextCompat.getColor(holder.serviceDetail.getContext(), R.color.style_color));

        int Authenticate = 0;
        if (!TextUtils.isEmpty(info.getAuthenticate() + "")) {
            Authenticate = Integer.valueOf(info.getAuthenticate());
        }
        textViewParser.append(authenticateStatus[Authenticate] + "\n", DPIUtil.dip2px(14),
                ContextCompat.getColor(holder.serviceDetail.getContext(), R.color.style_color));

        textViewParser.append("月成单(次)：", DPIUtil.dip2px(14),
                ContextCompat.getColor(holder.serviceDetail.getContext(), R.color.txt_black));
        int OrderCount = 0;
        if (!TextUtils.isEmpty(info.getOrderCount() + "")) {
            OrderCount = Integer.valueOf(info.getOrderCount());
        }
        textViewParser.append(OrderCount + "", DPIUtil.dip2px(14),
                ContextCompat.getColor(holder.serviceDetail.getContext(), R.color.app_blue));

        textViewParser.parse(holder.serviceDetail);
//        holder.serviceDetail authenticate
        binding.serviceDetail.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItemListener.ChildView(v, position);
            }
        });
    }

    @Override
    public int getAdapterItemCount() {
        return rowses.size();
    }


    public void setListItemListener(ListItemListener listItemListener) {
        this.listItemListener = listItemListener;
    }
}

class SevenServiceViewHolder extends RecyclerView.ViewHolder {
    View rootView;
    CircleImageView servicePic;
    TextView serviceType;
    TextView serviceName;
    TextView address;
    TextView decode;
    View call, downOrder, appointment;
    TextView serviceDetail;


    public SevenServiceViewHolder(View v) {
        super(v);
        rootView = v.findViewById(R.id.root_view);
        servicePic = (CircleImageView) v.findViewById(R.id.service_pic);
        serviceType = (TextView) v.findViewById(R.id.service_type);
        serviceName = (TextView) v.findViewById(R.id.service_name);
        address = (TextView) v.findViewById(R.id.address);
        decode = (TextView) v.findViewById(R.id.distance);
        call = v.findViewById(R.id.call);
        downOrder = v.findViewById(R.id.down_order);
        appointment = v.findViewById(R.id.appointment);
        serviceDetail = (TextView) v.findViewById(R.id.service_detail);
    }
}