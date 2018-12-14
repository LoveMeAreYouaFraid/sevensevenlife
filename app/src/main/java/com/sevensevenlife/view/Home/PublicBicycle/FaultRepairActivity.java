package com.sevensevenlife.view.Home.PublicBicycle;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;

import com.sevensevenlife.http.MyRequest;
import com.sevensevenlife.http.UpImg;
import com.sevensevenlife.interfaces.DialogListener;
import com.sevensevenlife.interfaces.HttpCallBack;
import com.sevensevenlife.interfaces.ListItemListener;
import com.sevensevenlife.interfaces.MyHttpCallBack;
import com.sevensevenlife.model.httpmodel.PublicMode;
import com.sevensevenlife.utils.FileUtils;
import com.sevensevenlife.utils.ImgChoiceUtils;
import com.sevensevenlife.utils.LogUtils;
import com.sevensevenlife.utils.MyApplication;
import com.sevensevenlife.utils.ToastUtils;
import com.sevensevenlife.view.custumview.BaseActivity;
import com.sevensevenlife.view.DiaLog.DialogUtils;
import com.sevensevenlife.view.DiaLog.MyProgressDialog;
import com.sevensevenlife.view.Home.PublicBicycle.Adapter.FaultRepairAdapter;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.FaultRepairActivityLayoutBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.sevensevenlife.http.RequestUtils.POST;
import static com.sevensevenlife.http.RequestUtils.URI.ADD_BIKE_ERROR;

/**
 * Created by 10237 on 2016/12/24.
 */

public class FaultRepairActivity extends BaseActivity
        implements View.OnClickListener, ListItemListener, MyHttpCallBack {
    private FaultRepairActivityLayoutBinding binding;
    private int type = 1;
    private String pic = "";
    private FaultRepairAdapter adapter;
    private List<String> picList;
    private List<String> upPicList;
    private ImgChoiceUtils utils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.fault_repair_activity_layout);
        binding.title.imgBack.setOnClickListener(this);
        binding.title.rightButton.setText("上报");
        binding.title.title.setText("故障报修");
        binding.title.rightButton.setOnClickListener(this);
        binding.carPile.setOnClickListener(this);
        binding.station.setOnClickListener(this);
        binding.vehicle.setOnClickListener(this);
        picList = new ArrayList<>();
        upPicList = new ArrayList<>();
        binding.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(3,
                StaggeredGridLayoutManager.VERTICAL));//LinearLayout.HORIZONTAL
        adapter = new FaultRepairAdapter(mContext, picList);
        adapter.setOnItemClickListener(this);
        binding.recyclerView.setAdapter(adapter);
        utils = ImgChoiceUtils.isNew(this);
        utils.isCamera(true);
        // Storage Permissions


    }


    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.vehicle:
                type = 1;
                binding.carPile.setBackgroundResource(R.drawable.bt_gray_fillet);
                binding.vehicle.setBackgroundResource(R.drawable.bg_red_fillet);
                binding.station.setBackgroundResource(R.drawable.bt_gray_fillet);
                break;
            case R.id.car_pile:
                type = 2;
                binding.carPile.setBackgroundResource(R.drawable.bg_red_fillet);
                binding.vehicle.setBackgroundResource(R.drawable.bt_gray_fillet);
                binding.station.setBackgroundResource(R.drawable.bt_gray_fillet);
                break;
            case R.id.station:
                type = 3;
                binding.carPile.setBackgroundResource(R.drawable.bt_gray_fillet);
                binding.vehicle.setBackgroundResource(R.drawable.bt_gray_fillet);
                binding.station.setBackgroundResource(R.drawable.bg_red_fillet);
                break;
            case R.id.right_button:
                if (!isLogin) {
                    ToastUtils.show("先登录一哈");
                    return;
                }
                if (TextUtils.isEmpty(binding.editText.getText() + "")) {
                    ToastUtils.show("简单描述一下故障吧");
                    return;
                }
                if (picList.size() == 0) {
                    ToastUtils.show("请上传几张照片哦");
                }
                PostData();
                break;

        }
    }

    private void PostData() {
        for (int i = 0; i < picList.size(); i++) {

            postImg(new File(picList.get(i)), i + 1);
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        utils.onPermissions(requestCode, permissions, grantResults);
    }


    @Override
    public void Item(final int position) {


        if (position == 0) {
            if (picList.size() == 3) {
                ToastUtils.show("只能发三张照片哦~");
                return;
            }
            utils.show();
        } else {
            DialogUtils.init(mContext)
                    .setTitle("是否删除这张照片?")
                    .setOne("确定", new DialogListener() {
                        @Override
                        public void buttType(int ButtType) {
                            if (ButtType == 0) {

                                if (position == 1) {
                                    picList.remove(0);
                                }
                                if (position == 2) {
                                    picList.remove(1);
                                }
                                if (position == 3) {
                                    picList.remove(2);
                                }
                                adapter.notifyDataSetChanged();
                            }
                        }
                    });
        }


    }

    @Override
    public void ChildView(View v, int position) {
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            try {
                File file = utils.onActivityResult(requestCode, resultCode, data);
                picList.add(file.getPath());
                adapter.notifyDataSetChanged();
            } catch (Exception e) {
                ToastUtils.show("无法获取这张照片");
            }

        }
    }

    private void postImg(File file, final int i) {

        UpImg.POST(file,
                new HttpCallBack() {
                    @Override
                    public void ok(String backString) {
                        upPicList.add(backString);
                        MyProgressDialog.getInstance().show(mContext, "第" + i + "张"
                                + "图片上传完成...");
                        if (i == picList.size()) {
                            postData();
                        }

                    }

                    @Override
                    public void error(String e) {

                    }
                });

    }

    private void postData() {
        MyProgressDialog.getInstance().cancel();
        for (int i = 0; i < upPicList.size(); i++) {
            if (i == 0) {
                pic = upPicList.get(0);
            } else {
                pic = pic + "," + upPicList.get(i);
            }
        }

        LogUtils.e(123, "postDatapostDatapostData");
        MyRequest.POST(POST, new String[]{"sessionId",
                "type",
                "content",
                "pic"}, new String[]{
                MyApplication.getInstance().getUserInfo().getRows().getSessionId(),
                type + "",
                binding.editText.getText() + "",
                pic
        }, ADD_BIKE_ERROR, PublicMode.class.getName(), 0, this);
    }

    @Override
    public <T> void ok(T backMode, String jsonString, int httpTY) {

        switch (httpTY) {
            case 0:
                ToastUtils.showLong("报修成功！");
                FileUtils.DPngFile();
                finish();
                break;
        }
    }

    @Override
    public void error(String e, int uriType) {
        ToastUtils.showLong(e);

    }
}
