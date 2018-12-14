package com.sevensevenlife.view.Home.PublicBicycle;

import android.app.Activity;
import android.content.Context;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewCompat;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.view.View;

import com.sevensevenlife.http.MyRequest;
import com.sevensevenlife.interfaces.DialogListener;
import com.sevensevenlife.interfaces.EditDialogListener;
import com.sevensevenlife.interfaces.MyHttpCallBack;
import com.sevensevenlife.model.httpmodel.LostBikeMode;
import com.sevensevenlife.model.httpmodel.bikeListmode;
import com.sevensevenlife.utils.ImgLoadUtils;
import com.sevensevenlife.utils.NetWorkUtils;
import com.sevensevenlife.utils.RecyclerViewUtils.RecyclerItemMargin;
import com.sevensevenlife.utils.ToastUtils;
import com.sevensevenlife.view.DiaLog.EdItDialog;
import com.sevensevenlife.view.DiaLog.TitleDialog;
import com.sevensevenlife.view.Home.PublicBicycle.Adapter.LoseCarListAdapter;
import com.andview.refreshview.XRefreshView;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.LoseCarActivityLayoutBinding;

import java.util.ArrayList;
import java.util.List;

import static android.widget.LinearLayout.VERTICAL;
import static com.sevensevenlife.http.RequestUtils.GET;
import static com.sevensevenlife.http.RequestUtils.URI.GET_LOST_BIKE;

/**
 * Created by 10237 on 2016/12/25.
 */

public class LoseCarActivity extends Activity implements View.OnClickListener, MyHttpCallBack {
    private LoseCarActivityLayoutBinding binding;
    private Context mContext;
    private List<bikeListmode> bikeList;
    private List<bikeListmode> getYesList;
    private List<bikeListmode> getNoList;
    private List<bikeListmode> adapterList;
    private LoseCarListAdapter adapter;
    private boolean isGetYes = true;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        binding = DataBindingUtil.setContentView(this, R.layout.lose_car_activity_layout);

        bikeList = new ArrayList<>();
        getYesList = new ArrayList<>();
        getNoList = new ArrayList<>();
        adapterList = new ArrayList<>();

        binding.title.title.setText("失车找回");
        binding.title.imgBack.setOnClickListener(this);
        binding.title.rightButton.setText("搜索");
        binding.title.rightButton.setOnClickListener(this);

        binding.xRefreshView.setPullLoadEnable(true);
        binding.xRefreshView.setPinnedTime(1000);
        binding.xRefreshView.setMoveForHorizontal(true);
        binding.recyclerView.setHasFixedSize(true);
        binding.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, VERTICAL));
        binding.recyclerView.addItemDecoration(new RecyclerItemMargin(16));
        adapter = new LoseCarListAdapter(mContext, adapterList);
        binding.recyclerView.setAdapter(adapter);

        binding.getYes.setOnClickListener(this);
        binding.getNo.setOnClickListener(this);
        ViewCompat.setTransitionName(binding.title.title, "touxiang");

        getData("");
        new TitleDialog().SHOW(mContext, "因用户还车时未确认“车已还好”的语音提示和锁车柱的电子字幕提示，所借车辆实际未还好而丢失，如有发现失车，请立即联系0736-7777777，谢谢",
                "确定", new DialogListener() {
                    @Override
                    public void buttType(int ButtType) {

                    }
                }, false);
        binding.xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh() {
                if (!NetWorkUtils.isNetworkConnected()) {
                    binding.xRefreshView.stopRefresh();
                    return;
                }
                getData("");
            }

            @Override
            public void onLoadMore(boolean isSilence) {

            }

        });


    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                ActivityCompat.finishAfterTransition(this);
                break;

            case R.id.right_button:
                EdItDialog.show(mContext, new EditDialogListener() {
                    @Override
                    public void click(int type, String[] data) {
                        getData(data[0]);
                    }
                }, EdItDialog.CarNum);

                break;
            case R.id.get_yes:
                adapterList.clear();
                adapterList.addAll(getYesList);
                adapter.notifyDataSetChanged();
                binding.getNo.setTextColor(ContextCompat.getColor(mContext, R.color.txt_black));
                binding.getNo.setBackgroundResource(R.color.white);
                binding.getYes.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                binding.getYes.setBackgroundResource(R.color.red);
                isGetYes = true;
                break;
            case R.id.get_no:
                adapterList.clear();
                adapterList.addAll(getNoList);
                adapter.notifyDataSetChanged();
                binding.getYes.setTextColor(ContextCompat.getColor(mContext, R.color.txt_black));
                binding.getYes.setBackgroundResource(R.color.white);
                binding.getNo.setTextColor(ContextCompat.getColor(mContext, R.color.white));
                binding.getNo.setBackgroundResource(R.color.red);
                isGetYes = false;
                break;


        }
    }

    private void getData(String num) {
        new MyRequest().POST(GET, new String[]{"number"}, new String[]{num},
                GET_LOST_BIKE, LostBikeMode.class.getName(), 0, this);
    }


    @Override
    public <T> void ok(T backMode, String jsonString, int httpTY) {
        switch (httpTY) {
            case 0:
                LostBikeMode mode = (LostBikeMode) backMode;
                adapterList.clear();
                getYesList.clear();
                getNoList.clear();
                for (int i = 0; i < mode.getRows().getBikeList().size(); i++) {
                    if (mode.getRows().getBikeList().get(i).getBikestate().equals("1002")) {
                        getYesList.add(mode.getRows().getBikeList().get(i));
                    } else {
                        getNoList.add(mode.getRows().getBikeList().get(i));
                    }
                }
                if (isGetYes) {
                    adapterList.addAll(getYesList);
                } else {
                    adapterList.addAll(getNoList);
                }

                ImgLoadUtils.Load(mContext, mode.getRows().getPic(), binding.hoderImg, false);
                adapter.notifyDataSetChanged();
                binding.xRefreshView.stopRefresh();
                break;

        }
    }

    @Override
    public void error(String e, int uriType) {
        ToastUtils.show(e);
        binding.xRefreshView.stopRefresh();
    }
}
