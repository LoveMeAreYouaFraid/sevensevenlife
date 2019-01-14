package com.sevensevenlife.view.Find;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;
import android.view.View;

import com.sevensevenlife.http.MyRequest;
import com.sevensevenlife.interfaces.BindViewInterface;
import com.sevensevenlife.interfaces.DialogListener;
import com.sevensevenlife.interfaces.MyHttpCallBack;
import com.sevensevenlife.model.httpmodel.FriendListMode;
import com.sevensevenlife.model.httpmodel.FriendListRows;
import com.sevensevenlife.model.httpmodel.PublicMode;
import com.sevensevenlife.utils.ImgLoadUtils;
import com.sevensevenlife.utils.LogUtils;
import com.sevensevenlife.utils.MyApplication;
import com.sevensevenlife.utils.PinYinUtils;
import com.sevensevenlife.utils.RecyclerViewUtils.PublicAdapter;
import com.sevensevenlife.utils.RecyclerViewUtils.RViewUtils;
import com.sevensevenlife.utils.ToastUtils;
import com.sevensevenlife.view.custumview.BaseActivity;
import com.sevensevenlife.view.DiaLog.DialogUtils;
import com.sevensevenlife.view.DiaLog.TitleDialog;
import com.andview.refreshview.XRefreshView;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.CircleOfFriendsActivityLayoutBinding;
import com.example.youxiangshenghuo.databinding.ContactsListItemBinding;

import java.util.ArrayList;
import java.util.HashSet;
import java.util.Iterator;
import java.util.List;
import java.util.Set;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import static com.sevensevenlife.http.RequestUtils.GET;
import static com.sevensevenlife.http.RequestUtils.POST;
import static com.sevensevenlife.http.RequestUtils.URI.GET_FRIEND_LIST;
import static com.sevensevenlife.http.RequestUtils.URI.UPDATE_FRIEND_LIST;


/**
 * Created by Administrator on 2017/3/13 0013.
 */

public class CircleOfFriendsActivity extends BaseActivity implements View.OnClickListener, MyHttpCallBack
        , BindViewInterface {

    private CircleOfFriendsActivityLayoutBinding binding;
    private List<FriendListRows> rows;
    private String phoneNum = "";
    private PublicAdapter adapter;

    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        try {
            initView();
        } catch (Exception e) {
            ToastUtils.show(e.getMessage());
        }
    }

    private void initView() throws Exception {
        binding = DataBindingUtil.setContentView(this, R.layout.circle_of_friends_activity_layout);
        binding.title.title.setText("熟人圈");
        binding.list.rootView.setBackgroundResource(R.color.transparent);
        binding.list.recyclerView.setBackgroundResource(R.color.transparent);
        binding.list.nullDataView.setVisibility(View.GONE);
        rows = new ArrayList<>();
        binding.title.imgBack.setOnClickListener(this);
        binding.addFriend.setOnClickListener(this);
        if (Build.VERSION.SDK_INT >= 23) {
            ActivityCompat.requestPermissions(this, new String[]{Manifest.permission.READ_CONTACTS, Manifest.permission.WRITE_CONTACTS}, 0);
        } else {
            phoneNum = getDataPhoneNum();
            if (!TextUtils.isEmpty(phoneNum)) {
                UptData();
            }
        }
        binding.list.xRefreshView.setPullLoadEnable(false);
        binding.list.xRefreshView.setXRefreshViewListener(new XRefreshView.SimpleXRefreshListener() {
            @Override
            public void onRefresh() {
                super.onRefresh();
                getData();
            }
        });
        adapter = new PublicAdapter<>(rows, R.layout.contacts_list_item, this);
        new RViewUtils(mContext, binding.list.recyclerView, binding.list.xRefreshView)
                .setAdapter(adapter);
    }

    private void getData() {
        MyRequest.POST(GET, new String[]{"phone"},
                new String[]{MyApplication.getInstance().getUserInfo().getRows().getPhone()},
                GET_FRIEND_LIST, FriendListMode.class.getName(), 22, CircleOfFriendsActivity.this);
    }

    private void UptData() {
        if (MyApplication.getInstance().isLogin()) {
            MyRequest.POST(POST, new String[]{"phone", "contacts"}, new String[]{
                    MyApplication.getInstance().getUserInfo().getRows().getPhone(),
                    phoneNum
            }, UPDATE_FRIEND_LIST, PublicMode.class.getName(), 11, this);
        } else {
            ToastUtils.show("您尚未登录，请先登录");
        }
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        if (requestCode == 0) {
            if (grantResults[0] == PackageManager.PERMISSION_GRANTED &&
                    grantResults[1] == PackageManager.PERMISSION_GRANTED) {
                try {
                    phoneNum = getDataPhoneNum();
                    if (!TextUtils.isEmpty(phoneNum)) {
                        MyRequest.POST(POST, new String[]{"phone", "contacts"}, new String[]{
                                MyApplication.getInstance().getUserInfo().getRows().getPhone(),
                                phoneNum
                        }, UPDATE_FRIEND_LIST, PublicMode.class.getName(), 11, this);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                    ToastUtils.show(e.getMessage());
                }
            } else {
                new TitleDialog().SHOW(mContext, new String[]{"需要您的联系人权限，请去设置-软件管理-优享七七生活，中打开", "好的"}, new DialogListener() {
                    @Override
                    public void buttType(int ButtType) {

                    }
                }, false);
            }
        }
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.add_friend:
                startActivity(new Intent(mContext, InvitationCourtesyActivity.class));
                break;
        }
    }

    private List<String> phones = new ArrayList<>();
    private List<String> newPhones = new ArrayList<>();

    private String getDataPhoneNum() throws Exception {
        String PhoneNumber = "";
        phones.clear();
        newPhones.clear();
        List<String> strings = new GetuUserNumUtils().getPhoneContacts(mContext);
        String regEx = "[^0-9]";
        Pattern p = Pattern.compile(regEx);
        Matcher m;
        for (int i = 0; i < strings.size(); i++) {
            m = p.matcher(strings.get(i));
            String a = m.replaceAll("").trim();
            String b = a.substring(0, 1);
            if (b.equals("8")) {//去掉+86
                a = a.substring(2, a.length());
            }
            if (a.length() == 11) {
                String cc = a.substring(0, 1);
                if (cc.equals("1")) {
                    phones.add(a);
                }
            }
        }
        if (phones.size() != 0) {
            newPhones.addAll(removeDuplicateWithOrder(phones));
            for (int ss = 0; ss < newPhones.size(); ss++) {
                if (ss == 0) {
                    PhoneNumber = newPhones.get(ss);
                } else {
                    PhoneNumber = PhoneNumber + "," + newPhones.get(ss);
                }
            }
        }
        return PhoneNumber;
    }

    /**
     * 去重复数据
     *
     * @param list
     * @return
     */
    public static List removeDuplicateWithOrder(List list) {
        Set set = new HashSet();
        List newList = new ArrayList();
        for (Iterator iter = list.iterator(); iter.hasNext(); ) {
            Object element = iter.next();
            if (set.add(element))
                newList.add(element);
        }
        return newList;
    }

    @Override
    public <T> void ok(T backMode, String jsonString, int httpTY) {
        try {
            switch (httpTY) {
                case 22:
                    FriendListMode mode = (FriendListMode) backMode;
                    if (mode.getRows().size() != 0) {
                        rows.clear();
                        rows.addAll(mode.getRows());
                        for (int i = 0; i < rows.size(); i++) {
                            rows.get(i).setPinyin(PinYinUtils.getPinyin(rows.get(i).getReal_name()).substring(0, 1));
                        }
                        new RViewUtils(mContext, binding.list.recyclerView, binding.list.xRefreshView)
                                .setAdapter(new PublicAdapter<>(rows, R.layout.contacts_list_item, this));
                    }
                    adapter.notifyDataSetChanged();
                    break;
                case 11:
                    getData();
                    break;
            }
        } catch (Exception e) {
            LogUtils.e(123, e.getMessage());
        }

    }

    @Override
    public void error(String e, int uriType) {
        ToastUtils.show(e);
    }


    private void goCall(String phone) {
        Intent intent = new Intent();
        intent.setAction("android.intent.action.CALL");
//		intent.addCategory("android.intent.category.DEFAULT");
        intent.setData(Uri.parse("tel:" + phone));
        startActivity(intent);//方法内部自动为intent添加类别android.intent.category.DEFAULT
    }


    @Override
    public void bandView(ViewDataBinding binding, final int position) {
        ContactsListItemBinding binding1 = (ContactsListItemBinding) binding;
        ImgLoadUtils.Load(binding1.userHeadImg.getContext(), rows.get(position).getHead_pic(), binding1.userHeadImg, true);
        String word = rows.get(position).getPinyin();
        binding1.tvWord.setText(word);
        binding1.tvName.setText(rows.get(position).getReal_name());
        if (position == 0) {
            binding1.tvWord.setVisibility(View.VISIBLE);
        } else {
            String headerWord = rows.get(position - 1).getPinyin();
            if (word.equals(headerWord)) {
                binding1.tvWord.setVisibility(View.GONE);
            } else {
                binding1.tvWord.setVisibility(View.VISIBLE);
            }
        }
        binding1.rootView.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                DialogUtils.init(mContext)
                        .setTitle("暂时还不可以在线聊天要不先给老朋友打个电话")
                        .setOne("确定", new DialogListener() {
                            @Override
                            public void buttType(int ButtType) {
                                goCall(rows.get(position).getPhone());
                            }
                        }).setTitleStyleBold();
            }
        });
        ((ContactsListItemBinding) binding).call.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                goCall(rows.get(position).getPhone());
            }
        });
    }
}
