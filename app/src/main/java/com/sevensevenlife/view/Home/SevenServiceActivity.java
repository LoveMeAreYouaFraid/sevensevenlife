package com.sevensevenlife.view.Home;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.sevensevenlife.http.MyRequest;
import com.sevensevenlife.interfaces.DialogListener;
import com.sevensevenlife.interfaces.ListItemListener;
import com.sevensevenlife.interfaces.MyHttpCallBack;
import com.sevensevenlife.interfaces.SevenServiceDialogListener;
import com.sevensevenlife.model.httpmodel.ProjectByParentLevelMode;
import com.sevensevenlife.model.httpmodel.PublicMode;
import com.sevensevenlife.model.httpmodel.ServiceListMode;
import com.sevensevenlife.model.httpmodel.ServiceListRows;
import com.sevensevenlife.utils.ImgLoadUtils;
import com.sevensevenlife.utils.LogUtils;
import com.sevensevenlife.utils.MyApplication;
import com.sevensevenlife.utils.RecyclerViewUtils.RecyclerItemMargin;
import com.sevensevenlife.utils.ToastUtils;
import com.sevensevenlife.view.custumview.BaseActivity;
import com.sevensevenlife.view.DiaLog.CarLenDialog;
import com.sevensevenlife.view.DiaLog.DialogUtils;
import com.sevensevenlife.view.DiaLog.SevenServiceDialog;
import com.sevensevenlife.view.Home.Adapter.SevenServiceAdapter;
import com.sevensevenlife.view.User.LoginActivity;
import com.andview.refreshview.XRefreshView;
import com.example.youxiangshenghuo.PaoTuiInfoActivity;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.YuYueActivity;
import com.example.youxiangshenghuo.databinding.SevenServiceActivityBinding;
import com.umeng.analytics.MobclickAgent;
import com.sevensevenlife.utils.MemoryBean;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import static com.sevensevenlife.http.RequestUtils.GET;
import static com.sevensevenlife.http.RequestUtils.POST;
import static com.sevensevenlife.http.RequestUtils.URI.APP_PHONE_ORDER;
import static com.sevensevenlife.http.RequestUtils.URI.GET_PROJECT_BY_PARENT_LEVEL;
import static com.sevensevenlife.http.RequestUtils.URI.GET_SERVICE_LIST;
import static com.sevensevenlife.utils.MemoryBean.serviceID;

/**
 * Created by Administrator on 2017/3/6 0006.
 */

public class SevenServiceActivity extends BaseActivity implements MyHttpCallBack, View.OnClickListener
        , ListItemListener {
    private SevenServiceActivityBinding binding;
    private int mposition = 0;
    private List<ServiceListRows> rowses = new ArrayList<>();
    private SevenServiceAdapter adapter;
    private String typeCode;
    private String projectName;
    private String projectId;
    private String orderBy = "";
    private int pageNum = 1;
    private String authenticate = "";
    private String status = "";
    private boolean isLevelMode = true;
    private int size = 10;
    private ServiceListMode mode1;
    private SevenServiceDialog dialog;

    private List<String> downOrderStrings;
    private ProjectByParentLevelMode mode;
    private String image;
    private boolean isCache = true;
    private String phone;
    private String carLength = "";
    private String needPorter = "";//是否需要搬运工 1：需要，0：不需要
    private List<TextView> lables = new ArrayList<>();
    private String[] lens = new String[]{"0-2", "2-3", "3-4", "4-5"};
    private boolean CanClick = false;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.seven_service_activity);
        binding.list.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(1, LinearLayout.VERTICAL));
        dialog = new SevenServiceDialog();
        downOrderStrings = new ArrayList<>();
        binding.title.imgBack.setOnClickListener(this);
        binding.lockDetail.setOnClickListener(this);
        binding.workLayout.setOnClickListener(this);
        binding.list.nullDataView.setVisibility(View.GONE);
        if (getIntent().hasExtra("typeCode")
                & getIntent().hasExtra("projectId") &
                getIntent().hasExtra("projectName") &
                getIntent().hasExtra("typeCode") &
                getIntent().hasExtra("label")) {

            String lable = getIntent().getStringExtra("label");
            String[] lableList = lable.split(",");
            if (lable.length() != 0) {
                lables.add(binding.lableOne);
                lables.add(binding.lableTwo);
                lables.add(binding.lableThree);
                lables.add(binding.lableFour);
                if (lableList.length >= 2) {
                    binding.labLayoutOne.setVisibility(View.VISIBLE);
                }
                if (lableList.length >= 3) {
                    binding.labLayoutTwo.setVisibility(View.VISIBLE);
                }

                for (int i = 0; i < lableList.length; i++) {
                    lables.get(i).setText(lableList[i]);
                    lables.get(i).setVisibility(View.VISIBLE);
                }
            }
            typeCode = getIntent().getStringExtra("typeCode");
            projectId = getIntent().getStringExtra("projectId");
            image = getIntent().getStringExtra("image");
            if (!TextUtils.isEmpty(image)) {
                binding.headerImg.setVisibility(View.VISIBLE);
                ImgLoadUtils.Load(mContext, image, binding.headerImg, false);
            } else {
                binding.headerImg.setVisibility(View.GONE);
            }


            MemoryBean.projectID = projectId;
            projectName = getIntent().getStringExtra("projectName");

            binding.title.title.setText(projectName);
            binding.work.setText(projectName);

            adapter = new SevenServiceAdapter(rowses);
            adapter.setListItemListener(this);
            binding.list.recyclerView.setAdapter(adapter);
            isCache = false;
            binding.list.xRefreshView.setPullLoadEnable(true);
            binding.list.xRefreshView.setMoveForHorizontal(true);
            binding.list.xRefreshView.setPinnedContent(false);
            binding.list.xRefreshView.setPinnedTime(1500);
            binding.list.recyclerView.addItemDecoration(new RecyclerItemMargin(16));
            binding.list.xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
                @Override
                public void onRefresh() {
                    pageNum = 1;
                    getData();
                    isCache = false;
                    binding.list.xRefreshView.setPullLoadEnable(true);

                }

                @Override
                public void onLoadMore(boolean isSilence) {
                    if (size == 10) {
                        pageNum++;
                        getData();
                    } else {
                        binding.list.xRefreshView.setPullLoadEnable(false);
                    }
                }
            });
            binding.sort.setOnClickListener(this);
            binding.screen.setOnClickListener(this);

        } else {
            ToastUtils.show("获取数据失败，请稍后重试...");
        }
        if (getIntent().hasExtra("Housemoving")) {
            new CarLenDialog().Show(mContext, new DialogListener() {
                @Override
                public void buttType(int ButtType) {
                    carLength = lens[ButtType];
                    DialogUtils.init(mContext)
                            .setTitle("是否需要搬运工?")
                            .setOne("需要")
                            .setTwo("不需要")
                            .isTouchcCancel(false)
                            .setDialogListener(new DialogListener() {
                                @Override
                                public void buttType(int ButtType) {
                                    if (ButtType == 1) {
                                        needPorter = "1";
                                    } else {
                                        needPorter = "2";
                                    }
                                    getData();
                                }
                            });
                }
            });
        } else {
            getData();
        }

    }


    @Override
    public <T> void ok(T backMode, String jsonString, int httpTY) {
        switch (httpTY) {
            case 100:
                try {
                    mode = (ProjectByParentLevelMode) backMode;
                    isLevelMode = false;
                    for (int i = 0; i < mode.getRows().size(); i++) {
                        downOrderStrings.add(mode.getRows().get(i).getTypeName());
                    }
                    if (!TextUtils.isEmpty(downOrderStrings.get(0))) {
                        binding.work.setText(downOrderStrings.get(0));
                    }
                    MemoryBean.typeDesc = mode.getRows().get(0).getTypeDesc() + "";
                    binding.headerTitle.setText("起步价：" + mode.getRows().get(0).getPrice());
                } catch (Exception e) {
                    isLevelMode = true;
                }
                CanClick = true;
                break;
            case 101:
                binding.list.xRefreshView.stopLoadMore();
                binding.list.xRefreshView.stopRefresh();
                binding.list.xRefreshView.setPullLoadEnable(true);
                try {
                    mode1 = (ServiceListMode) backMode;
                    size = mode1.getRows().size();
                    if (pageNum == 1) {
                        rowses.clear();
                    }
                    rowses.addAll(mode1.getRows());
                    adapter.notifyDataSetChanged();
                } catch (Exception e) {
                    LogUtils.e("出错了", e.getMessage());
                    ToastUtils.show("出错了获取服务器数据失败");
                } finally {
                    if (isLevelMode) {
                        MyRequest.POST(GET, new String[]{"typeCode"}, new String[]{typeCode}, GET_PROJECT_BY_PARENT_LEVEL,
                                ProjectByParentLevelMode.class.getName(), 100, this, isCache);
                    }
                }


                break;
            case 19:

                break;
        }
    }

    @Override
    public void error(String e, int uriType) {
        ToastUtils.show(e);
        binding.list.xRefreshView.stopRefresh();
        binding.list.xRefreshView.stopRefresh();
        binding.list.xRefreshView.setPullLoadEnable(true);

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.sort:
                dialog.show(mContext, 0, null, new SevenServiceDialogListener() {
                    @Override
                    public void item(String positionOne, String positionTwo) {

                        switch (positionOne) {
                            case "0":
                                orderBy = "0";
                                break;
                            case "1":
                                orderBy = "0";
                                break;
                            case "2":
                                orderBy = "1";
                                break;
                            case "3":
                                orderBy = "2";
                                break;
                        }
                        pageNum = 1;
                        getData();
                    }
                });
                break;
            case R.id.work_layout:
                if (!isLevelMode) {
                    pageNum = 1;
                    dialog.show(mContext, 1, downOrderStrings, new SevenServiceDialogListener() {
                        @Override
                        public void item(String positionOne, String positionTwo) {
                            projectId = mode.getRows().get(Integer.valueOf(positionOne)).getId();
                            mposition = Integer.valueOf(positionOne);
                            binding.work.setText(mode.getRows().get(mposition).getTypeName());
                            pageNum = 1;
                            binding.headerTitle.setText("起步价：" + mode.getRows().get(mposition).getPrice());
                            getData();
                            MemoryBean.typeDesc = mode.getRows().get(0).getTypeDesc() + "";
                        }
                    });
                } else {
                    ToastUtils.show("数据获取中，请稍后重试");
                }

                break;
            case R.id.screen:

                dialog.show(mContext, 2, null, new SevenServiceDialogListener() {
                    @Override
                    public void item(String positionOne, String positionTwo) {

                        switch (positionOne) {
                            case "0":
                                authenticate = "";
                                break;
                            case "1":
                                authenticate = "1";
                                break;
                            case "2":
                                authenticate = "0";
                                break;

                        }
                        switch (positionOne) {
                            case "0":
                                status = "";
                                break;
                            case "1":
                                status = "1";
                                break;
                            case "2":
                                status = "2";
                                break;
                        }
                        pageNum = 1;
                        getData();
                    }
                });
                break;
            case R.id.lock_detail:
                startActivity(new Intent(mContext, SevenServiceDetailActivity.class));
                break;
        }
    }

    private void getData() {
        MyRequest.POST(GET, new String[]{
                        "projectId",
                        "orderBy",
                        "longitude",
                        "latitude",
                        "pageNum",
                        "pageSize",
                        "authenticate",
                        "status",
                        "carLength",
                        "needPorter"}, new String[]{
                        projectId,
                        orderBy,
                        MemoryBean.Longitude + "",
                        MemoryBean.Latitude + "",
                        pageNum + "",
                        "10",
                        authenticate,
                        status,
                        carLength,
                        needPorter
                }, GET_SERVICE_LIST,
                ServiceListMode.class.getName(), 101, this, isCache);
    }

    @Override
    public void Item(int position) {
        if (CanClick) {
            MemoryBean.Child_projectID = mode1.getRows().get(position).getProjectId();
            Intent intent = new Intent(mContext, PaoTuiInfoActivity.class);
            intent.putExtra("id", rowses.get(position).getId() + "");
            intent.putExtra("workStatus", rowses.get(position).getWorkStatus());
            intent.putExtra("phone", rowses.get(position).getPhone());
            MemoryBean.prepayment = mode.getRows().get(mposition).getPrice() + "";
            serviceID = rowses.get(position).getId() + "";
            MemoryBean.typeDesc = mode.getRows().get(mposition).getTypeDesc() + "";
            startActivity(intent);
        } else {
            ToastUtils.show("正在拉取数据，请稍后");
        }
    }

    @Override
    public void ChildView(View v, int position) {
        serviceID = rowses.get(mposition).getId() + "";
        switch (v.getId()) {
            case R.id.call:
                phone = rowses.get(position).getPhone();
                MyRequest.POST(POST, new String[]{"sessionId", "serviceId", "projectId"}, new String[]{
                        MyApplication.getInstance().getUserInfo().getRows().getSessionId(),
                        serviceID,
                        mode.getRows().get(position).getId()
                }, APP_PHONE_ORDER, PublicMode.class.getName(), 19, this);
                Intent CALLintent = new Intent();
                CALLintent.setAction("android.intent.action.CALL");
                CALLintent.setData(Uri.parse("tel:" + phone));
                startActivity(CALLintent);//方法内部自动为intent添加类别android.intent.category.DEFAULT
                break;
            case R.id.down_order:
                MemoryBean.Child_projectID = mode1.getRows().get(position).getProjectId();
                MemoryBean.prepayment = mode.getRows().get(mposition).getPrice() + "";
                Map<String, String> map_value = new HashMap<>();
                map_value.put("type", "项目" + projectName + rowses.get(position).getRealName());
                MobclickAgent.onEvent(mContext, "12", map_value);

                if (rowses.get(position).getWorkStatus().equals("1")) {
                    Intent intents;
                    if (MyApplication.getInstance().isLogin()) {
                        intents = new Intent(mContext, YuYueActivity.class);
                        intents.putExtra("paotuiName", rowses.get(position).getRealName());
                        intents.putExtra("isXiaDan", "true");
                        serviceID = rowses.get(position).getId() + "";
                    } else {
                        intents = new Intent(mContext, LoginActivity.class);
                    }
                    startActivity(intents);
                } else {
                    ToastUtils.show("服务人员正忙，请选择其他空闲服务人员或预约");
                }
                break;
            case R.id.appointment:
                MemoryBean.Child_projectID = mode1.getRows().get(position).getProjectId();
                Map<String, String> map_values = new HashMap<>();
                map_values.put("type", "项目" + projectName + rowses.get(position).getRealName());
                MobclickAgent.onEvent(mContext, "11", map_values);
                Intent intentAppointment;
                if (MyApplication.getInstance().isLogin()) {
                    intentAppointment = new Intent(mContext, YuYueActivity.class);
                    intentAppointment.putExtra("paotuiName", rowses.get(position).getRealName());

                    serviceID = rowses.get(position).getId() + "";
                } else {
                    intentAppointment = new Intent(mContext, LoginActivity.class);
                }
                startActivity(intentAppointment);
                break;
            case R.id.service_detail:
                MemoryBean.Child_projectID = mode1.getRows().get(position).getProjectId();
                if (CanClick) {
                    Intent intent = new Intent(mContext, PaoTuiInfoActivity.class);
                    intent.putExtra("id", rowses.get(position).getId() + "");
                    intent.putExtra("workStatus", rowses.get(position).getWorkStatus());
                    intent.putExtra("phone", rowses.get(position).getPhone());
                    MemoryBean.prepayment = mode.getRows().get(mposition).getPrice() + "";
                    serviceID = rowses.get(position).getId() + "";
                    MemoryBean.typeDesc = mode.getRows().get(mposition).getTypeDesc() + "";
                    startActivity(intent);
                } else {
                    ToastUtils.show("正在拉取数据，请稍后");
                }
                break;
        }

    }
}
