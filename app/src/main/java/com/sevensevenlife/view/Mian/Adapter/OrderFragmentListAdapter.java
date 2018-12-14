package com.sevensevenlife.view.Mian.Adapter;

import android.content.Context;
import android.databinding.DataBindingUtil;
import android.support.v7.widget.RecyclerView;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.view.animation.AlphaAnimation;
import android.view.animation.Animation;
import android.view.animation.AnimationSet;
import android.view.animation.AnimationUtils;
import android.view.animation.ScaleAnimation;
import android.widget.ImageView;
import android.widget.TextView;

import com.sevensevenlife.interfaces.ListItemListener;
import com.sevensevenlife.model.httpmodel.OrderListItemMode;
import com.sevensevenlife.utils.ImgLoadUtils;
import com.andview.refreshview.recyclerview.BaseRecyclerAdapter;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.OrderListItemBinding;

import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/2/3 0003.
 */

public class OrderFragmentListAdapter extends BaseRecyclerAdapter<viewItem> {

    private OrderListItemBinding binding;

    private Context mContext;

    private ListItemListener listItemListener;

    private List<OrderListItemMode> orderList = new ArrayList<>();

    private OrderListItemMode mode;

    private int type;

    private String[] orderStrings = new String[]{"待付款", "执行中", "待评价", "待付款", "待评价", "已评价", "爽约", "取消"};

    public OrderFragmentListAdapter(Context context, List<OrderListItemMode> orderList, int type) {
        mContext = context;
        this.orderList.clear();
        this.orderList = orderList;
        this.type = type;

    }


    @Override
    public viewItem getViewHolder(View view) {
        return new viewItem(view);
    }

    @Override
    public viewItem onCreateViewHolder(ViewGroup parent, int viewType, boolean isItem) {
        binding = DataBindingUtil.inflate(LayoutInflater.from(mContext), R.layout.order_list_item, parent, false);
        return new viewItem(binding.getRoot());
    }

    @Override
    public void onBindViewHolder(final viewItem holder, final int position, boolean isItem) {
        mode = orderList.get(position);
        DecimalFormat df = new DecimalFormat("######0.00");
        double prepaymentD = 0.0;
        double remain_moneyD = 0.0;
        double couponMoneyD = 0.0;

        if (!TextUtils.isEmpty(mode.getPrepayment())) {
            prepaymentD = Double.parseDouble(mode.getPrepayment());
        }
        if (!TextUtils.isEmpty(mode.getRemain_money())) {
            remain_moneyD = Double.parseDouble(mode.getRemain_money());
        }
        if (!TextUtils.isEmpty(mode.getCouponMoney())) {
            couponMoneyD = Double.parseDouble(mode.getCouponMoney());
        }


        ImgLoadUtils.Load(mContext, mode.getService_pic(), holder.headImg, true);
        holder.serviceName.setText(mode.getService_name());
        holder.title.setText("\t\t\t\t服务项目：" + mode.getProject_name());
        holder.address.setText("目的地：" + mode.getDeliver_address());
        holder.addressSet.setText("取货地：" + mode.getPick_address());
        holder.payTime.setText("\t\t\t\t下单时间：" + mode.getReserve_time());
        if (mode.getFinish_status().equals("-1")) {
            mode.setFinish_status("6");
        }
        if (mode.getFinish_status().equals("-2")) {
            mode.setFinish_status("7");
        }

        holder.orderState.setText("订单状态：" + orderStrings[Integer.valueOf(mode.getFinish_status())]);
        holder.comment.setVisibility(View.GONE);
        switch (type) {
            case 0:
                holder.payNum.setText("应付款："
                        + df.format(prepaymentD + remain_moneyD) + "元");
                holder.pay.setVisibility(View.VISIBLE);
                break;
            case 1:
                holder.payNum.setText("已付款："
                        + df.format(prepaymentD + remain_moneyD) + "元");
                holder.pay.setVisibility(View.GONE);
                holder.reminder.setVisibility(View.VISIBLE);
                break;
            case 2:
                holder.pay.setVisibility(View.GONE);
                holder.payDetails.setVisibility(View.INVISIBLE);
                holder.payNum.setText("订单实际支付"
                        + df.format(prepaymentD + remain_moneyD - couponMoneyD) + "元");
                break;

            //状态 1：执行中，3：待付款，4：待评价，5：已完成
        }

        holder.reminder.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItemListener.ChildView(holder.reminder, position);
            }
        });
        holder.pay.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItemListener.ChildView(holder.pay, position);
            }
        });
        holder.comment.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItemListener.ChildView(holder.comment, position);
            }
        });
        holder.collection.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                AnimationSet animationSet = new AnimationSet(false);
                AlphaAnimation alphaAnimation = new AlphaAnimation(1F, 0.3f);
                ScaleAnimation scaleAnimation = new ScaleAnimation(1.0F, 2F, 1.0F, 2F,
                        Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                scaleAnimation.setDuration(300);
                alphaAnimation.setDuration(300);
                animationSet.addAnimation(alphaAnimation);
                animationSet.addAnimation(scaleAnimation);
                holder.collectionTwo.startAnimation(animationSet);
                animationSet.setAnimationListener(new Animation.AnimationListener() {
                    @Override
                    public void onAnimationStart(Animation animation) {

                    }

                    @Override
                    public void onAnimationEnd(Animation animation) {
                        AnimationSet animationSet = new AnimationSet(false);
                        AlphaAnimation alphaAnimation = new AlphaAnimation(0.3F, 1f);
                        final ScaleAnimation scaleAnimation = new ScaleAnimation(2F, 1F, 2F, 1F,
                                Animation.RELATIVE_TO_SELF, 0.5f, Animation.RELATIVE_TO_SELF, 0.5f);
                        scaleAnimation.setDuration(300);
                        alphaAnimation.setDuration(300);
                        animationSet.addAnimation(alphaAnimation);
                        animationSet.addAnimation(scaleAnimation);
                        holder.collectionTwo.startAnimation(animationSet);
                        animationSet.setAnimationListener(new Animation.AnimationListener() {
                            @Override
                            public void onAnimationStart(Animation animation) {

                            }

                            @Override
                            public void onAnimationEnd(Animation animation) {
                                holder.collectionTwo.startAnimation(AnimationUtils.loadAnimation(mContext,
                                        R.anim.slide_left_out));
                            }

                            @Override
                            public void onAnimationRepeat(Animation animation) {

                            }
                        });
                    }

                    @Override
                    public void onAnimationRepeat(Animation animation) {

                    }
                });
                listItemListener.ChildView(holder.collection, position);
            }
        });

        holder.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                listItemListener.Item(position);
            }
        });
        if (mode.getFinish_status().equals("2")) {
            binding.comment.setVisibility(View.VISIBLE);
        } else {
            binding.comment.setVisibility(View.GONE);
        }
    }


    @Override
    public int getAdapterItemCount() {
        return orderList.size();
    }

    public void setListItemListener(ListItemListener listItemListener) {
        this.listItemListener = listItemListener;
    }
}


class viewItem extends RecyclerView.ViewHolder {

    View rootView;
    ImageView headImg;
    TextView serviceName,
            orderState,
            title,
            address,
            addressSet,
            payDetails,
            payNum,
            pay,
            comment,
            collection,
            collectionTwo,
            payTime, reminder;


    public viewItem(View v) {
        super(v);
        rootView = v.findViewById(R.id.constraintLayout);
        headImg = (ImageView) v.findViewById(R.id.hoder_img);
        serviceName = (TextView) v.findViewById(R.id.service_name);
        orderState = (TextView) v.findViewById(R.id.order_state);
        title = (TextView) v.findViewById(R.id.title);
        address = (TextView) v.findViewById(R.id.address);
        addressSet = (TextView) v.findViewById(R.id.address_set);
        payDetails = (TextView) v.findViewById(R.id.pay_details);
        payNum = (TextView) v.findViewById(R.id.pay_num);
        pay = (TextView) v.findViewById(R.id.pay);
        comment = (TextView) v.findViewById(R.id.comment);
        collection = (TextView) v.findViewById(R.id.collection);
        collectionTwo = (TextView) v.findViewById(R.id.collection_two);
        payTime = (TextView) v.findViewById(R.id.tv_pay_time);
        reminder = (TextView) v.findViewById(R.id.reminder);
    }

}
