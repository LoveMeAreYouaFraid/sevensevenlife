package com.sevensevenlife.view.User.Adpter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;
import android.widget.TextView;

import com.sevensevenlife.interfaces.ListItemListener;
import com.sevensevenlife.model.httpmodel.CollectInfo;
import com.sevensevenlife.utils.DPIUtil;
import com.sevensevenlife.utils.ImgLoadUtils;
import com.sevensevenlife.utils.TextViewParser;
import com.sevensevenlife.view.custumview.CircleImageView;
import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.CollectListItemBinding;

import java.util.List;


/**
 * Created by Administrator on 2017/2/10 0010.
 */

public class CollectListAdapter extends BaseRecyclerAdapter<viewHead> {
    private Context mContext;
    private List<CollectInfo> info;
    private CollectListItemBinding binding;
    private String address;
    private ListItemListener listItemListener;
    private String[] strings = new String[]{"", "空闲", "忙碌", "离线"};

    public void setListItemListener(ListItemListener l) {
        listItemListener = l;
    }


    public CollectListAdapter(Context context, List<CollectInfo> info) {
        mContext = context;
        this.info = info;
    }

    @Override
    public viewHead getViewHolder(View view) {
        return null;
    }

    @Override
    public viewHead onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(parent.getContext()),
                R.layout.collect_list_item, parent, false);

        return new viewHead(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(final viewHead holder, final int position, boolean isItem) {
        ImgLoadUtils.Load(mContext, info.get(position).getHeadPic(), holder.imageView, true);
        holder.name.setText(info.get(position).getRealName());
        if (TextUtils.isEmpty(info.get(position).getWorkStatus())) {
            int Status = Integer.valueOf(info.get(position).getWorkStatus());
            holder.workStatus.setText(strings[Integer.valueOf(info.get(position).getWorkStatus())]);
            switch (Status) {
                case 1:
                    holder.call.setVisibility(View.VISIBLE);
                    holder.downOrder.setVisibility(View.VISIBLE);
                    holder.appraisalTotal.setVisibility(View.VISIBLE);
                    break;
                case 2:
                    holder.call.setVisibility(View.VISIBLE);
                    holder.downOrder.setVisibility(View.GONE);
                    holder.appraisalTotal.setVisibility(View.VISIBLE);
                    break;
                case 3:
                    holder.call.setVisibility(View.GONE);
                    holder.downOrder.setVisibility(View.GONE);
                    holder.appraisalTotal.setVisibility(View.GONE);
                    break;
            }
        }


        if (TextUtils.isEmpty(info.get(position).getCurrentAddress())) {
            address = "未知";
        } else {
            address = info.get(position).getCurrentAddress();
            holder.currentAddress.setText(address);

        }
        String ll = "m";
        if (!TextUtils.isEmpty(info.get(position).getDistance())) {
            int mm = Integer.valueOf(info.get(position).getDistance());
            if (mm > 1000) {
                mm = mm / 1000;
                ll = "km";
            }
            holder.distance.setText(mm + "" + ll);
        }

        setUserAgreement(holder.appraisalTotal, info.get(position));
        holder.delete.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItemListener.ChildView(holder.delete, position);

            }
        });
        holder.downOrder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItemListener.ChildView(holder.downOrder, position);

            }
        });
        holder.call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItemListener.ChildView(holder.call, position);
            }
        });
        binding.appointment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItemListener.ChildView(holder.appointment, position);
            }
        });
        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItemListener.Item(position);
            }
        });

    }

    @Override
    public int getAdapterItemCount() {
        return info.size();
    }

    /**
     * @param tvUserAgreement
     * @param mode            设置多行文字
     */
    private void setUserAgreement(TextView tvUserAgreement, CollectInfo mode) {
        TextViewParser textViewParser = new TextViewParser();
        textViewParser.append("综合平分：",
                DPIUtil.dip2px(12), ContextCompat.getColor(mContext, R.color.txt_black));

        textViewParser.append(mode.getAppraisalTotal() + "\n",
                DPIUtil.dip2px(12), ContextCompat.getColor(mContext, R.color.style_color));
        String authenticate;

        if (mode.getAuthenticate().equals("1")) {
            authenticate = "七七认证";
        } else {
            authenticate = "未认证";
        }
        textViewParser.append(authenticate + "\n",
                DPIUtil.dip2px(16), ContextCompat.getColor(mContext, R.color.style_color));
        textViewParser.parse(tvUserAgreement);

        textViewParser.append("月成单(次):",
                DPIUtil.dip2px(12), ContextCompat.getColor(mContext, R.color.txt_black));
        textViewParser.parse(tvUserAgreement);

        textViewParser.append(mode.getOrderCount() + "\n",
                DPIUtil.dip2px(12), ContextCompat.getColor(mContext, R.color.style_color));
        textViewParser.parse(tvUserAgreement);

    }


}

//状态：1：空闲，2：忙碌，3：离线 distance
class viewHead extends RecyclerView.ViewHolder {
    CircleImageView imageView;
    TextView workStatus;
    View delete;
    TextView name;
    TextView currentAddress;
    TextView distance;
    TextView appraisalTotal;
    TextView orderCount;
    ImageView call;
    TextView downOrder;
    TextView appointment;//预约
    View rootView;

    public viewHead(View v) {
        super(v);
        imageView = (CircleImageView) v.findViewById(R.id.top_image);
        workStatus = (TextView) v.findViewById(R.id.workStatus);
        delete = v.findViewById(R.id.delete_collect);
        name = (TextView) v.findViewById(R.id.paoduiName);
        currentAddress = (TextView) v.findViewById(R.id.currentAddress);
        distance = (TextView) v.findViewById(R.id.distance);
        appraisalTotal = (TextView) v.findViewById(R.id.appraisalTotal);
        orderCount = (TextView) v.findViewById(R.id.orderCount);
        call = (ImageView) v.findViewById(R.id.call);
        downOrder = (TextView) v.findViewById(R.id.down_order);
        appointment = (TextView) v.findViewById(R.id.appointment);
        rootView = v.findViewById(R.id.root_view);

    }
}
