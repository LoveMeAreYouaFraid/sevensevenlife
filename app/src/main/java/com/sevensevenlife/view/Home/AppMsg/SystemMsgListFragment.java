package com.sevensevenlife.view.Home.AppMsg;

import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.os.Bundle;
import android.support.v4.app.Fragment;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;

import com.sevensevenlife.http.MyRequest;
import com.sevensevenlife.interfaces.BindViewInterface;
import com.sevensevenlife.interfaces.DialogListener;
import com.sevensevenlife.interfaces.MyHttpCallBack;
import com.sevensevenlife.model.httpmodel.MessageListBen;
import com.sevensevenlife.model.httpmodel.MessageMode;
import com.sevensevenlife.model.httpmodel.MsgContent;
import com.sevensevenlife.model.httpmodel.PublicMode;
import com.sevensevenlife.utils.JsonUtil;
import com.sevensevenlife.utils.LogUtils;
import com.sevensevenlife.utils.MyApplication;
import com.sevensevenlife.utils.RecyclerViewUtils.PublicAdapter;
import com.sevensevenlife.utils.RecyclerViewUtils.RViewUtils;
import com.sevensevenlife.utils.ToastUtils;
import com.sevensevenlife.view.DiaLog.DialogUtils;
import com.sevensevenlife.view.DiaLog.MyProgressDialog;
import com.andview.refreshview.XRefreshView;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.AppMsgListItemBinding;
import com.example.youxiangshenghuo.databinding.ListLayoutBinding;

import java.util.ArrayList;
import java.util.List;

import static com.sevensevenlife.http.RequestUtils.GET;
import static com.sevensevenlife.http.RequestUtils.URI.DEL_MESSAGE;
import static com.sevensevenlife.http.RequestUtils.URI.PUSh_MESSAGE;

/**
 * Created by Administrator on 2017/4/7 0007.
 */

public class SystemMsgListFragment extends Fragment implements MyHttpCallBack, BindViewInterface {
    private ListLayoutBinding binding;
    private MessageMode mode;
    private int pageNum = 1;
    private List<MessageListBen> listBens = new ArrayList<>();
    private PublicAdapter<MessageListBen> adapter;
    private boolean initOk = false;

    @Override
    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {
        binding = DataBindingUtil.inflate(inflater, R.layout.list_layout, container, false);
        adapter = new PublicAdapter<>(listBens, R.layout.app_msg_list_item, this);
        new RViewUtils(getContext(), binding)
                .IsRefreshOrLoad(true)
                .setAdapter(adapter);
        binding.xRefreshView.setPullRefreshEnable(true);
        binding.xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh() {
                if (initOk) {
                    listBens.clear();
                    pageNum = 1;
                    getData();
                    binding.xRefreshView.setPullLoadEnable(true);
                } else {
                    binding.xRefreshView.stopRefresh();
                }

            }

            @Override
            public void onLoadMore(boolean isSilence) {
                if (initOk) {

                    if (mode.getRows().size() == 10) {
                        if (initOk) {
                            pageNum++;
                            getData();
                        }
                    } else {
                        binding.xRefreshView.setPullLoadEnable(false);
                    }
                } else {
                    binding.xRefreshView.stopLoadMore();
                }

            }
        });
        return binding.getRoot();
    }

    private boolean oneStart = true;

    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (isVisibleToUser && oneStart) {
            getData();
        }
    }

    @Override
    public <T> void ok(T backMode, String jsonString, int httpTY) {
        try {
            switch (httpTY) {
                case 931:
                    MyProgressDialog.getInstance().cancel();
                    initOk = true;
                    mode = (MessageMode) backMode;
                    listBens.addAll(mode.getRows());
                    adapter.notifyDataSetChanged();
                    binding.nullDataView.setBackgroundResource(R.drawable.icon_03);
                    break;
                case 12:
                    listBens.remove(positions);
                    adapter.notifyDataSetChanged();
                    ToastUtils.show("删除成功");
                    break;
            }
        } catch (Exception e) {
            LogUtils.e(123, e.getMessage());
        }
        binding.xRefreshView.stopRefresh();
        binding.xRefreshView.stopLoadMore();
    }

    @Override
    public void error(String e, int uriType) {
        ToastUtils.show(e);
        MyProgressDialog.getInstance().cancel();
    }

    private int positions = 0;

    @Override
    public void bandView(ViewDataBinding binding, final int position) {
        AppMsgListItemBinding bin = (AppMsgListItemBinding) binding;
        bin.msgTime.setText(listBens.get(position).getYmd());
        MsgContent msgContent = new JsonUtil<MsgContent>().json2Bean(listBens.get(position).getContent(), MsgContent.class.getName());
        bin.msgTitle.setText(msgContent.getHeader().getMessage());
        bin.msgDetail.setText(msgContent.getRows().getText());
        bin.msgDel.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                positions = position;
                DialogUtils.init(getActivity())
                        .setTitle("是否删除这条消息?")
                        .setOne("删除")
                        .setTwo("算了吧")
                        .setDialogListener(new DialogListener() {
                            @Override
                            public void buttType(int ButtType) {
                                if (ButtType == 1) {
                                    MyRequest.POST(GET, new String[]{"sessionId", "id"}, new String[]{
                                            MyApplication.getInstance().getUserInfo().getRows().getSessionId(),
                                            listBens.get(position).getId() + ""
                                    }, DEL_MESSAGE, PublicMode.class
                                            .getName(), 12, SystemMsgListFragment.this);
                                }

                            }
                        });
//                MyRequest.POST(GET, new String[]{"sessionId", "id"}, new String[]{
//                        MyApplication.getInstance().getUserInfo().getRows().getSessionId(),
//                        listBens.get(position).getId() + ""
//                }, DEL_MESSAGE, PublicMode.class
//                        .getName(), 12, SystemMsgListFragment.this);
            }
        });
    }

    public void getData() {
        oneStart = false;
        MyProgressDialog.getInstance().show(getActivity(), "正在加载...");
        MyRequest.POST(GET, new String[]{"sessionId", "pageNum", "pageSize", "code"}, new String[]{
                MyApplication.getInstance().getUserInfo().getRows().getSessionId(),
                pageNum + "",
                "10",
                "000"
        }, PUSh_MESSAGE, MessageMode.class.getName(), 931, this);
    }
}
