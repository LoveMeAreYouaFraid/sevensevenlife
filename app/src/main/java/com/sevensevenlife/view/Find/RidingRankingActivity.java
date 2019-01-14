package com.sevensevenlife.view.Find;

import com.sevensevenlife.http.MyRequest;
import com.sevensevenlife.interfaces.ListItemListener;
import com.sevensevenlife.interfaces.MyHttpCallBack;
import com.sevensevenlife.model.httpmodel.RideTopListMode;
import com.sevensevenlife.model.httpmodel.RlxhMede;
import com.sevensevenlife.utils.ImgLoadUtils;
import com.sevensevenlife.utils.MyApplication;
import com.sevensevenlife.utils.TextViewParser;
import com.sevensevenlife.utils.ToastUtils;
import com.sevensevenlife.view.custumview.BaseActivity;
import com.sevensevenlife.view.Find.Adapter.RidingRankingAdapter;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.RidingRankingActivityLayoutBinding;

import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.content.ContextCompat;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import java.util.ArrayList;
import java.util.List;

import static com.sevensevenlife.http.RequestUtils.GET;
import static com.sevensevenlife.http.RequestUtils.URI.GET_RIDE_TOP_LIST;
import static com.sevensevenlife.http.RequestUtils.URI.GET_RLXH;

/**
 * Created by Administrator on 2017/3/15 0015.
 */

public class RidingRankingActivity extends BaseActivity implements View.OnClickListener, MyHttpCallBack
        , ListItemListener {
    private RidingRankingActivityLayoutBinding binding;
    private RidingRankingAdapter adapter;
    private List<RideTopListMode.RowsBean> list;
    private String sort = "1";
    private boolean oneSet = true;
    private int userPosition = 999;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.riding_ranking_activity_layout);
        binding.title.imgBack.setOnClickListener(this);
        binding.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayout.VERTICAL));
        list = new ArrayList<>();
        adapter = new RidingRankingAdapter(mContext, list);
        adapter.setListItemListener(this);
        binding.allRanking.setOnClickListener(this);
        binding.friendRanking.setOnClickListener(this);
        binding.recyclerView.setAdapter(adapter);
        binding.query.setOnClickListener(this);
        binding.title.title.setText("绿色出行");
        initList();

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.friend_ranking:
                binding.friendRanking.setTextColor(ContextCompat.getColor(mContext, R.color.style_color));
                binding.allRanking.setTextColor(ContextCompat.getColor(mContext, R.color.txt_black));
                sort = "1";
                initList();
                break;
            case R.id.all_ranking:
                binding.friendRanking.setTextColor(ContextCompat.getColor(mContext, R.color.txt_black));
                binding.allRanking.setTextColor(ContextCompat.getColor(mContext, R.color.style_color));
                sort = "2";
                initList();
                break;
            case R.id.query:
                if (TextUtils.isEmpty(binding.edKg.getText() + "")) {
                    binding.edKg.setError("请输入体重");
                    ToastUtils.show("请输入体重");
                    return;
                }
                try {
                    MyRequest.POST(GET, new String[]{"distance", "weight"}, new String[]{
                            list.get(userPosition).getKm(),
                            binding.edKg.getText() + ""

                    }, GET_RLXH, RlxhMede.class.getName(), 11, this);
                } catch (Exception e) {
                    ToastUtils.show("您最近没骑车，无法查询哦");
                }

                break;
        }

    }

    @Override
    public <T> void ok(T backMode, String jsonString, int httpTY) {
        try {
            switch (httpTY) {
                case 10:
                    RideTopListMode mode = (RideTopListMode) backMode;
                    list.clear();
                    list.addAll(mode.getRows());
                    if (list.size() == 0) {
                        binding.wo.setText("暂无排名");
                    } else {
                        binding.wo.setText("我");
                    }
                    adapter.notifyDataSetChanged();
                    break;
                case 11:
                    RlxhMede rlxhMede = (RlxhMede) backMode;
                    binding.tvCalorie.setText(rlxhMede.getRows());
                    binding.tvCalorie.setVisibility(View.VISIBLE);
                    binding.edKg.setText("");
                    break;
            }
        } catch (Exception e) {
            ToastUtils.show("服务起数据异常，请稍后重试");
        }


    }

    @Override
    public void error(String e, int uriType) {
        ToastUtils.show(e);
    }

    @Override
    public void Item(int position) {

    }

    @Override
    public void ChildView(View v, int position) {
        userPosition = position;
        if (oneSet && userPosition != 999) {
            TextViewParser txt = new TextViewParser();
            TextViewParser km = new TextViewParser();
            TextViewParser kg = new TextViewParser();
            txt.append(list.get(userPosition).getReal_name(), 40, ContextCompat.getColor(mContext, R.color.txt_black));
            txt.append("\nTOP  " + (userPosition + 1) + "", 34, ContextCompat.getColor(mContext, R.color.style_color));
            txt.parse(binding.nameOrTop);

            ImgLoadUtils.Load(mContext, list.get(userPosition).getHead_pic(), binding.userHead, true);
            km.append("骑行距离   ", 40, ContextCompat.getColor(mContext, R.color.txt_black));
            km.append(list.get(userPosition).getKm(), 40, ContextCompat.getColor(mContext, R.color.green));
            km.append("   km", 40, ContextCompat.getColor(mContext, R.color.txt_black));
            km.parse(binding.km);

            kg.append("减少排放   ", 40, ContextCompat.getColor(mContext, R.color.txt_black));
            kg.append(list.get(userPosition).getKm(), 40, ContextCompat.getColor(mContext, R.color.green));
            kg.append("   kg", 40, ContextCompat.getColor(mContext, R.color.txt_black));
            kg.parse(binding.kg);
        }
        oneSet = false;

    }

    private void initList() {
        MyRequest.POST(GET, new String[]{"phone", "sort"}, new String[]{
                MyApplication.getInstance().getUserInfo().getRows().getPhone(),
                sort
        }, GET_RIDE_TOP_LIST, RideTopListMode.class.getName(), 10, this);
    }
}
