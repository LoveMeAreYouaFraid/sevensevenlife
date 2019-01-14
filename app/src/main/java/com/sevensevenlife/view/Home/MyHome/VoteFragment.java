package com.sevensevenlife.view.Home.MyHome;

import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.LinearLayout;

import com.sevensevenlife.http.MyRequest;
import com.sevensevenlife.http.UpImg;
import com.sevensevenlife.interfaces.BindViewInterface;
import com.sevensevenlife.interfaces.DialogListener;
import com.sevensevenlife.interfaces.MyHttpCallBack;
import com.sevensevenlife.interfaces.OneEditDialogListener;
import com.sevensevenlife.model.httpmodel.OptionsBean;
import com.sevensevenlife.model.httpmodel.PublicMode;
import com.sevensevenlife.model.httpmodel.TopIcMode;
import com.sevensevenlife.utils.ImgChoiceUtils;
import com.sevensevenlife.utils.ImgLoadUtils;
import com.sevensevenlife.utils.JsonUtil;
import com.sevensevenlife.utils.LogUtils;
import com.sevensevenlife.utils.MyApplication;
import com.sevensevenlife.utils.RecyclerViewUtils.PublicAdapter;
import com.sevensevenlife.utils.TimeUtils;
import com.sevensevenlife.utils.ToastUtils;
import com.sevensevenlife.view.DiaLog.DialogUtils;
import com.sevensevenlife.view.DiaLog.OneEdItDialog;
import com.sevensevenlife.view.DiaLog.TitleDialog;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.AddVoteListItemBinding;
import com.example.youxiangshenghuo.databinding.UpImgListItemLayoutBinding;
import com.example.youxiangshenghuo.databinding.VoteFragmentLayoutBinding;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import static com.sevensevenlife.http.RequestUtils.POST_TWO;
import static com.sevensevenlife.http.RequestUtils.URI.ADD_BBS_TOPIC;

/**
 * Created by Administrator on 2017/8/3 0003.
 */

public class VoteFragment extends Fragment implements View.OnClickListener, BindViewInterface, MyHttpCallBack {
    private VoteFragmentLayoutBinding binding;
    private PublicAdapter adapter;
    private List<String> stringList = new ArrayList<>();
    private String jsonString;
    private ImgChoiceUtils utils;
    private TopIcMode topIcMode;
    private long time;
    private List<OptionsBean> getOptions = new ArrayList<>();
    private List<String> imgList = new ArrayList<>();
    private PublicAdapter imgAdapter;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.vote_fragment_layout, container, false);
        binding.addVote.setOnClickListener(this);
        binding.tvSubmit.setOnClickListener(this);
        adapter = new PublicAdapter(stringList, R.layout.add_vote_list_item, this);
        binding.recyclerView.setLayoutManager(new StaggeredGridLayoutManager(2, LinearLayout.VERTICAL));
        binding.recyclerView.setAdapter(adapter);
        utils = ImgChoiceUtils.isNew(getActivity());
        topIcMode = new TopIcMode();
        getOptions.clear();

        imgList.add("");
        imgAdapter = new PublicAdapter(imgList, R.layout.up_img_list_item_layout, new BindViewInterface() {
            @Override
            public void bandView(ViewDataBinding binding, final int position) {
                UpImgListItemLayoutBinding bin = (UpImgListItemLayoutBinding) binding;
                if (position != 0) {
                    ImgLoadUtils.Load(getActivity(), imgList.get(position), bin.imgImg, false);
                }
                bin.imgImg.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        if (position == 0) {
                            if (imgList.size() >= 10) {
                                ToastUtils.show("最多只能上传9张照片哦");
                                return;
                            }
                            utils.isCamera(true).show();
                        } else {
                            new TitleDialog().SHOW(getActivity(), "是否删除这张照片?", "删除", "取消", new DialogListener() {
                                @Override
                                public void buttType(int ButtType) {
                                    if (ButtType == 0) {
                                        imgList.remove(position);
                                        imgAdapter.notifyDataSetChanged();
                                    }
                                }
                            }, false);


                        }
                    }
                });
            }
        });
        binding.imgRecyclerView.setLayoutManager(new StaggeredGridLayoutManager(3, LinearLayout.VERTICAL));
        binding.imgRecyclerView.setAdapter(imgAdapter);
        return binding.getRoot();
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        utils.onPermissions(requestCode, permissions, grantResults);
    }

    private File file;

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            try {
                file = utils.onActivityResult(requestCode, resultCode, data);
                LogUtils.e(123, "onActivityResult");
                UpImg.POST(file, new MyHttpCallBack() {
                    @Override
                    public <T> void ok(T backMode, String jsonString, int httpTY) {
                        imgList.add(jsonString);
                        adapter.notifyDataSetChanged();
                    }

                    @Override
                    public void error(String e, int uriType) {
                        ToastUtils.show(e);
                    }
                });
            } catch (Exception e) {
                e.printStackTrace();
            }
        }

    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.add_vote:
                if (stringList.size() >= 6) {
                    ToastUtils.show("最多只能添加6个选项哦~");
                } else {
                    new OneEdItDialog()
                            .init(getActivity())
                            .setHintString("请输入投票项")
                            .setTitleSting("提示")
                            .setListener(new OneEditDialogListener() {
                                @Override
                                public void click(String data) {
                                    stringList.add(data);
                                    adapter.notifyDataSetChanged();
                                }
                            }).show();
                }

                break;
            case R.id.tv_submit:
                if (TextUtils.isEmpty(binding.edTitle.getText() + "")) {
                    ToastUtils.show("请输入标题");
                    return;
                }
                if (TextUtils.isEmpty(binding.edContext.getText() + "")) {
                    ToastUtils.show("请输入类容");
                    return;
                }

                topIcMode.setCommunity_id(MyApplication.getInstance().getCommunityId());
                topIcMode.setContent(binding.edContext.getText() + "");
                topIcMode.setTitle(binding.edTitle.getText() + "");
                topIcMode.setVote(new TopIcMode.VoteBean());
                if (binding.switchCompat.isChecked()) {
                    topIcMode.getVote().setOption_type("1");
                } else {
                    topIcMode.getVote().setOption_type("2");
                }
                if (TextUtils.isEmpty(binding.edDayNum.getText() + "")) {
                    ToastUtils.show("请输入有效期");
                    return;
                }
                time = System.currentTimeMillis() + (86400000 * Integer.valueOf(binding.edDayNum.getText() + ""));

                topIcMode.getVote().setEnd_time(TimeUtils.getYearMonthAndDay(time));

                if (imgList.size() == 1) {
                    ToastUtils.show("请上传图片");
                    return;
                }

                if (stringList.size() == 0) {
                    ToastUtils.show("请添加投票选项");
                    return;
                }
                for (int i = 0; i < stringList.size(); i++) {
                    OptionsBean optionsBean = new OptionsBean();
                    optionsBean.setOption_name(stringList.get(i));
                    getOptions.add(optionsBean);
                }
                String imgSting = null;
                for (int j = 0; j < imgList.size(); j++) {
                    if (j != 0) {
                        if (j == 1) {
                            imgSting = imgList.get(j);
                        } else {
                            imgSting = imgSting + "," + imgList.get(j);
                        }
                    }
                }

                topIcMode.setPicture(imgSting);
                topIcMode.getVote().getOptions().addAll(getOptions);
                jsonString = new JsonUtil<TopIcMode>().bean2Json(topIcMode);


                MyRequest.POST(POST_TWO,
                        new String[]{""},
                        new String[]{jsonString},
                        ADD_BBS_TOPIC + "?sessionId=" + MyApplication.getInstance().getUserInfo().getRows().getSessionId(),
                        PublicMode.class.getName(),
                        103,
                        this);
                break;
        }


    }

    @Override
    public void bandView(ViewDataBinding binding, final int position) {
        AddVoteListItemBinding bin = (AddVoteListItemBinding) binding;
        bin.tvVote.setText(stringList.get(position));
        bin.tvVote.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                if (stringList.size() >= 10) {
                    ToastUtils.show("最多只能添加10个选项哦");
                    return;
                }
                DialogUtils.init(getActivity())
                        .setTitle("是否删除这条数据?")
                        .setOne("删除", new DialogListener() {
                            @Override
                            public void buttType(int ButtType) {
                                stringList.remove(position);
                                adapter.notifyDataSetChanged();
                            }
                        }).setTwo("取消", null);
            }
        });

    }

    @Override
    public <T> void ok(T backMode, String jsonString, int httpTY) {
        switch (httpTY) {
            case 103:
                ToastUtils.show("提交成功");
                getActivity().setResult(getActivity().RESULT_OK);
                getActivity().finish();
                break;
        }
    }

    @Override
    public void error(String e, int uriType) {
        ToastUtils.show("提交失败");
    }
}
