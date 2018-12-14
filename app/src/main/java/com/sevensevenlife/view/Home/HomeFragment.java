package com.sevensevenlife.view.Home;

import android.content.BroadcastReceiver;
import android.content.Context;
import android.content.Intent;
import android.content.IntentFilter;
import android.databinding.DataBindingUtil;
import android.databinding.ViewDataBinding;
import android.graphics.Typeface;
import android.os.Bundle;
import android.os.Handler;
import android.support.v4.app.Fragment;
import android.support.v4.widget.SwipeRefreshLayout;
import android.support.v7.widget.LinearLayoutManager;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.view.ViewGroup;
import android.widget.ImageView;

import com.sevensevenlife.http.MyNewRequest;
import com.sevensevenlife.http.MyRequest;
import com.sevensevenlife.interfaces.BindViewInterface;
import com.sevensevenlife.interfaces.CheckBoxListener;
import com.sevensevenlife.interfaces.DialogListener;
import com.sevensevenlife.interfaces.ListItemListener;
import com.sevensevenlife.interfaces.ListItemListenerTwo;
import com.sevensevenlife.interfaces.MyHttpCallBack;
import com.sevensevenlife.model.httpmodel.AddMessageMode;
import com.sevensevenlife.model.httpmodel.BannerInfo;
import com.sevensevenlife.model.httpmodel.CdyeeNews;
import com.sevensevenlife.model.httpmodel.CdyeeNewsRows;
import com.sevensevenlife.model.httpmodel.FirstLevelProjecInfo;
import com.sevensevenlife.model.httpmodel.FirstLevelProjecMode;
import com.sevensevenlife.model.httpmodel.GetBannerMode;
import com.sevensevenlife.model.KEY;
import com.sevensevenlife.utils.ImgLoadUtils;
import com.sevensevenlife.utils.JsonUtil;
import com.sevensevenlife.utils.LogUtils;
import com.sevensevenlife.utils.MyApplication;
import com.sevensevenlife.utils.NetWorkUtils;
import com.sevensevenlife.utils.PreferencesUtil;
import com.sevensevenlife.utils.RecyclerViewUtils.PublicAdapter;
import com.sevensevenlife.utils.RecyclerViewUtils.RViewUtils;
import com.sevensevenlife.utils.ToastUtils;
import com.sevensevenlife.view.custumview.LpWebActivity;
import com.sevensevenlife.view.DiaLog.DialogUtils;
import com.sevensevenlife.view.DiaLog.HomeMessageDialog;
import com.sevensevenlife.view.Home.Adapter.HomeButtonAdapter;
import com.sevensevenlife.view.Home.AppMsg.AppMsgActivity;
import com.sevensevenlife.view.Home.MyCommunity.CommunityHomeActivity;
import com.sevensevenlife.view.Home.PublicBicycle.BicycleHomeActivity;
import com.sevensevenlife.view.Mian.MainActivityLp;
import com.sevensevenlife.view.User.LoginActivity;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.FindListItemBinding;
import com.example.youxiangshenghuo.databinding.HomeFragmentViewBinding;
import com.umeng.analytics.MobclickAgent;
import com.sevensevenlife.utils.MemoryBean;

import java.util.ArrayList;
import java.util.List;

import static com.sevensevenlife.http.RequestUtils.GET;
import static com.sevensevenlife.http.RequestUtils.URI.FIRDT_LEVEL_PORJECT;
import static com.sevensevenlife.http.RequestUtils.URI.GET_AD_MESSAGE;
import static com.sevensevenlife.http.RequestUtils.URI.GET_BANNER;
import static com.sevensevenlife.http.RequestUtils.URI.GET_CDYEE_NEWS;
import static com.sevensevenlife.model.KEY.REG_ID;
import static com.sevensevenlife.model.KEY.URL;

/**
 * Created by Administrator on 2017/1/25 0025.
 */

public class HomeFragment extends Fragment implements View.OnClickListener, MyHttpCallBack, ListItemListener,
        ListItemListenerTwo {

    private static final Object TAG = HomeFragment.class.getName();

    private HomeFragmentViewBinding binding;

    private Context mContext;

    private List<View> mViewList = null;

    private List<FirstLevelProjecMode> firstLevelList;

    private boolean isVisible = false;

    private Intent intent;

    private mBroadcastReceiver myReceiver;

    private boolean isLodOk = false;


    private FirstLevelProjecInfo info;
    private List<BannerInfo> rows1 = new ArrayList<>();
    private List<CdyeeNewsRows> rows5 = new ArrayList<>();
    private List<BannerInfo> rows6 = new ArrayList<>();
    private HomeButtonAdapter adapter;
    private HomeButtonFragment fragmentOne;
    private HomeButtonFragmentTwo fragmentTwo;
    private List<Fragment> lists = new ArrayList<>();
    private List<FirstLevelProjecMode> buttonListOne = new ArrayList<>();
    private List<FirstLevelProjecMode> buttonListTwo = new ArrayList<>();
    private Handler handler;

    @Override

    public View onCreateView(LayoutInflater inflater, ViewGroup container, Bundle savedInstanceState) {

        if (!isVisible) {

            binding = DataBindingUtil.inflate(inflater, R.layout.home_fragment_view, container, false);

            mContext = getActivity();

            IntentFilter filter = new IntentFilter();

            filter.addAction("com.example.youxiangshenghuo.Location");

            myReceiver = new mBroadcastReceiver();

            mContext.registerReceiver(myReceiver, filter);

            firstLevelList = new ArrayList<>();


            setListener();

            if (!TextUtils.isEmpty(PreferencesUtil.getString("appAddress"))) {
                binding.address.setText(PreferencesUtil.getString("appAddress"));
            }

            if (!NetWorkUtils.isNetworkConnected()) {
                setValue(null, true, true);
                setValue(null, true, false);
                binding.myTopImgView.setValue(mViewList, false, false);
                ToastUtils.show("网络未连接");

            }
            isVisible = true;

        }

        Typeface typeFace = Typeface.createFromAsset(getActivity().getAssets(), "fonts/pm.ttf");

        binding.newsTitle.setTypeface(typeFace);

        binding.selectedTitle.setTypeface(typeFace);

        binding.newsList.nullDataView.setVisibility(View.GONE);

        binding.selectedList.nullDataView.setVisibility(View.GONE);

        binding.swipeRefreshLayout.setColorSchemeResources(
                R.color.chun_blue,
                R.color.chun_red,
                R.color.chun_yellow);

        MyRequest.POST(GET, new String[]{"position"}, new String[]{"1,6"},
                GET_BANNER, GetBannerMode.class.getName(), 991, HomeFragment.this);

        binding.swipeRefreshLayout.setOnRefreshListener(new SwipeRefreshLayout.OnRefreshListener() {
            @Override
            public void onRefresh() {
                isHomeButtChecks = false;
                if (!NetWorkUtils.isNetworkConnected()) {
                    isHomeButtChecks = true;
                }
                MyRequest.POST(GET, new String[]{"position"}, new String[]{"1,6"},
                        GET_BANNER, GetBannerMode.class.getName(), 991, HomeFragment.this);
            }
        });

        LogUtils.e(123, PreferencesUtil.getString(REG_ID));
        MainActivityLp activity = (MainActivityLp) getActivity();
        handler = activity.handler;
        adapter = new HomeButtonAdapter(getChildFragmentManager(), lists);
        binding.buttView.setAdapter(adapter);
        binding.circlePageIndicator.setViewPager(binding.buttView);
        return binding.getRoot();
    }


    private void setListener() {

        binding.address.setOnClickListener(this);

        binding.msg.setOnClickListener(this);

        binding.bicycle.setOnClickListener(this);

        binding.myHome.setOnClickListener(this);

        binding.news.setOnClickListener(this);

        binding.menu.setOnClickListener(this);
    }

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.address:
                if (!MemoryBean.cityCode.equals("")) {

                    Intent intent = new Intent(mContext, LocationAgainActivity.class);
                    startActivityForResult(intent, 99);
                } else {

                    ToastUtils.show("再次定位中...");
                }
                break;
            case R.id.msg:
                if (MyApplication.getInstance().isLogin()) {

                    startActivity(new Intent(mContext, AppMsgActivity.class));
                } else {
                    startActivity(new Intent(mContext, LoginActivity.class));
                }
                break;
            case R.id.bicycle:
                if (!NetWorkUtils.isNetworkConnected()) {
                    ToastUtils.show("网络未连接");
                    return;
                }
                if (TextUtils.isEmpty(PreferencesUtil.getString("NoReminder"))) {
                    showDialog();
                } else {
                    Bicycle();
                }

                break;
            case R.id.my_home:
                if (MyApplication.getInstance().isLogin()) {
                    startActivity(new Intent(mContext, CommunityHomeActivity.class));
                } else {
                    ToastUtils.show("请先登录~");
                }
                break;
            case R.id.news:
                startActivity(new Intent(mContext, NewsActivity.class));
                break;

            case R.id.menu:
                handler.sendEmptyMessage(100);
                break;
        }
    }

    private boolean isChecks = false;
    private boolean isHomeButtChecks = true;

    private void showDialog() {
        DialogUtils.init(mContext)
                .setTitle("还车时请务必注意听到车桩语音提示“车已还好，谢谢”方可离开")
                .setOne("我知道了", new DialogListener() {
                    @Override
                    public void buttType(int ButtType) {
                        if (isChecks) {
                            PreferencesUtil.putString("NoReminder", "000");
                        }
                        LogUtils.e(123, isChecks);
                        Bicycle();
                    }
                }).isChecked(new CheckBoxListener() {
            @Override
            public void isCheck(boolean isCheck) {
                isChecks = isCheck;
            }
        });
    }


    @Override
    public <T> void ok(T backMode, String jsonString, int httpTY) {
        switch (httpTY) {
            case 991:
                binding.swipeRefreshLayout.setRefreshing(false);
                try {
                    GetBannerMode infos = (GetBannerMode) backMode;
                    if (infos != null) {
                        if (infos.getRows().size() != 0) {
                            rows6.clear();
                            for (int i = 0; i < infos.getRows().size(); i++) {
                                switch (Integer.valueOf(infos.getRows().get(i).getPosition())) {
                                    case 1:
                                        rows1.add(infos.getRows().get(i));
                                        break;
                                    case 6:
                                        rows6.add(infos.getRows().get(i));
                                        break;
                                }
                            }
                            if (rows6 != null && rows6.size() > 0) {
                                binding.selectedLayout.setVisibility(View.VISIBLE);
                            }
                            LinearLayoutManager mLinearLayoutManager = new LinearLayoutManager(mContext);
                            mLinearLayoutManager.setSmoothScrollbarEnabled(true);
                            mLinearLayoutManager.setAutoMeasureEnabled(true);
                            new RViewUtils(mContext, binding.selectedList)
                                    .setLayoutMg(mLinearLayoutManager)
                                    .setAdapter(new PublicAdapter<>(rows6.size(), R.layout.find_list_item, new BindViewInterface() {
                                        @Override
                                        public void bandView(ViewDataBinding b, final int position) {
                                            try {
                                                FindListItemBinding binding1 = (FindListItemBinding) b;
                                                binding1.date.setText(rows6.get(position).getUpdate_date());
                                                if (TextUtils.isEmpty(rows6.get(position).getTitle())) {
                                                    binding1.titleLayout.setVisibility(View.GONE);
                                                } else {
                                                    binding1.title.setText(rows6.get(position).getTitle());
                                                    binding1.titleLayout.setVisibility(View.VISIBLE);
                                                }
                                                ImgLoadUtils.Load(mContext, rows6.get(position).getPic_url(), binding1.comprehensive, false);

                                                binding1.rootView.setOnClickListener(new View.OnClickListener() {
                                                    @Override
                                                    public void onClick(View v) {
                                                        startActivity(new Intent(mContext, LpWebActivity.class)
                                                                .putExtra(KEY.URL, rows6.get(position).getLink_value()));
                                                    }
                                                });
                                            } catch (Exception e) {
                                                LogUtils.e(123, e.getMessage());
                                            }
                                        }
                                    }));
                        }
                    }
                    if (rows1.size() == 0) {
                        BannerInfo info = new BannerInfo();
                        info.setLink_value("http://218.75.134.187:7777/yxsh-api/html/banner1.html");
                        info.setPic_url("http://111.8.133.24:7777/yxsh-api/image/b1.jpg");
                        rows1.add(info);
                    }
                    setValue(rows1, false, true);//最上面轮播图
                    binding.myTopImgView.setValue(mViewList, true, true);
                } catch (Exception e) {
                    LogUtils.e("123HomeFragment", "ok(T backMode, String jsonString, int httpTY)991 Exception");
                }

                MyNewRequest.getInstance().setApiUrl(FIRDT_LEVEL_PORJECT)
                        .setClassName(FirstLevelProjecInfo.class.getName())
                        .setCallBacks(this)
                        .setHttpType(3)
                        .setKey("")
                        .setValue("")
                        .setCache(isHomeButtChecks)
                        .GET();

//                MyRequest.POST(GET, null, null, FIRDT_LEVEL_PORJECT,
//                        FirstLevelProjecInfo.class.getName(), 3, this, true);
                break;

            case 3:
                MyRequest.POST(GET, null, null, GET_CDYEE_NEWS, CdyeeNews.class.getName(), 100, this, true);
                binding.swipeRefreshLayout.setRefreshing(false);

                try {
                    info = new JsonUtil<FirstLevelProjecInfo>().json2Bean(jsonString, FirstLevelProjecInfo.class.getName());
                    if (info == null && info.getRows() == null) {
                        return;
                    }
                    firstLevelList.clear();
                    firstLevelList.addAll(info.getRows());
                    buttonListOne.clear();
                    buttonListTwo.clear();
                    lists.clear();
                    for (int i = 0; i < firstLevelList.size(); i++) {
                        if (i <= 7) {
                            buttonListOne.add(firstLevelList.get(i));
                        } else if (i > 7 && i < 16) {
                            buttonListTwo.add(firstLevelList.get(i));
                        }

                    }
                    if (buttonListOne.size() > 0) {
                        fragmentOne = new HomeButtonFragment(buttonListOne).setListItemListener(this);
                        lists.add(fragmentOne);
                    }
                    if (buttonListTwo.size() > 0) {
                        fragmentTwo = new HomeButtonFragmentTwo(buttonListTwo).setListItemListenerTwo(this);
                        lists.add(fragmentTwo);
                    }
                    adapter.notifyDataSetChanged();
                    isLodOk = true;

                    if (info != null) {
                        MemoryBean.industyListMap = true;
                    }
                } catch (Exception e) {
                    LogUtils.e(123, e.getMessage());
                }
                break;
            case 100:
                if (MyApplication.getInstance().isLogin()) {
                    MyRequest.POST(GET,
                            new String[]{"sessionId"},
                            new String[]{MyApplication.getInstance().getUserInfo().getRows().getSessionId()},
                            GET_AD_MESSAGE,
                            AddMessageMode.class.getName(),
                            1021,
                            this);
                }

                try {
                    CdyeeNews cdyeeNews = (CdyeeNews) backMode;
                    rows5.addAll(cdyeeNews.getRows());
                    if (rows5.size() == 0) {
                        return;
                    } else {
                        binding.newsLayout.setVisibility(View.VISIBLE);
                    }
                    new RViewUtils(mContext, binding.newsList.recyclerView, binding.newsList.xRefreshView)
                            .setAdapter(new PublicAdapter<>(1, R.layout.find_list_item, new BindViewInterface() {
                                @Override
                                public void bandView(ViewDataBinding b, final int position) {
                                    FindListItemBinding bin = (FindListItemBinding) b;
                                    if (!TextUtils.isEmpty(rows5.get(position).getTitle())) {
                                        bin.title.setText(rows5.get(position).getTitle());
                                        bin.titleLayout.setVisibility(View.VISIBLE);
                                    } else {
                                        bin.titleLayout.setVisibility(View.GONE);
                                    }

                                    ImgLoadUtils.Load(mContext, rows5.get(position).getImage(), bin.comprehensive, false);
                                    bin.comprehensive.setOnClickListener(new View.OnClickListener() {
                                        @Override
                                        public void onClick(View v) {
                                            startActivity(new Intent(mContext, LpWebActivity.class)
                                                    .putExtra(KEY.URL, rows5.get(position).getUrl()));
                                        }
                                    });

                                }
                            }));
                } catch (Exception e) {
                    e.fillInStackTrace();
                    LogUtils.e(TAG, e.getMessage());
                }

                break;
            case 1021:
                AddMessageMode mode = new JsonUtil<AddMessageMode>().json2Bean(jsonString, AddMessageMode.class.getName());
                new HomeMessageDialog().init(mContext)
                        .setAddMessageMode(mode)
                        .show();
                break;

        }

    }

    @Override
    public void error(String e, int uriType) {
        binding.swipeRefreshLayout.setRefreshing(false);
        switch (uriType) {
            case 991:
                MyNewRequest.getInstance().setApiUrl(FIRDT_LEVEL_PORJECT)
                        .setClassName(FirstLevelProjecInfo.class.getName())
                        .setCallBacks(this)
                        .setHttpType(3)
                        .setKey("")
                        .setValue("")
                        .setCache(isHomeButtChecks)
                        .GET();
                break;
            case 3:
                MyRequest.POST(GET, null, null, GET_CDYEE_NEWS, CdyeeNews.class.getName(), 100, this, true);
                break;

        }
        if (uriType != 1021) {
            ToastUtils.show(e);
        }


    }


    private void setValue(final List<BannerInfo> list, boolean isDefaultImg, boolean isTop) {
        mViewList = new ArrayList<>();
        ImageView img;
        View viewPagerItem;
        if (!isDefaultImg) {
            for (int i = 0; i < list.size(); i++) {
                viewPagerItem = View.inflate(mContext, R.layout.viewpaper_item, null);
                img = (ImageView) viewPagerItem.findViewById(R.id.title_image);
                final int finalI = i;
                ImgLoadUtils.Load(mContext, list.get(i).getPic_url(), img, false);
                img.setOnClickListener(new View.OnClickListener() {
                    @Override
                    public void onClick(View v) {
                        Intent intent = new Intent(mContext, LpWebActivity.class);
                        intent.putExtra(URL, list.get(finalI).getLink_value());
                        startActivity(intent);
                    }
                });
                mViewList.add(viewPagerItem);
            }

        } else {
            viewPagerItem = View.inflate(mContext, R.layout.viewpaper_item, null);
            img = (ImageView) viewPagerItem.findViewById(R.id.title_image);
            if (!isTop) {
                img.setImageResource(R.mipmap.top_default_img);
            } else {
                img.setImageResource(R.mipmap.boom_default_img);
            }
            img.getLayoutParams().height = 450;
            mViewList.add(viewPagerItem);
        }

    }

    @Override
    public void Item(int position) {
        setlists(position, 0);
    }

    private int[] ids = new int[]{4, 6, 9, 16, 19, 26, 27};

    @Override
    public void ChildView(View v, int position) {

    }


    private void Bicycle() {
        MobclickAgent.onEvent(mContext, "1");
        if (MyApplication.getInstance().isLogin()) {
            intent = new Intent(mContext, BicycleHomeActivity.class);
        } else {
            intent = new Intent(mContext, LoginActivity.class);
        }
        startActivity(intent);

    }

    @Override
    public void Items(int position) {
        setlists(position, 1);
    }

    class mBroadcastReceiver extends BroadcastReceiver {

        @Override
        public void onReceive(Context context, Intent intent) {

            if (!TextUtils.isEmpty(PreferencesUtil.getString("appAddress"))) {
                if (PreferencesUtil.getString("appAddress").equals(intent.getStringExtra("Locationmsg"))) {
                    binding.address.setText(PreferencesUtil.getString("appAddress"));
                } else {
                    binding.address.setText(intent.getStringExtra("Locationmsg"));
                    PreferencesUtil.putString("appAddress", intent.getStringExtra("Locationmsg"));
                }

            } else {
                binding.address.setText(intent.getStringExtra("Locationmsg"));
                PreferencesUtil.putString("appAddress", intent.getStringExtra("Locationmsg"));
            }

        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == getActivity().RESULT_OK) {
            switch (requestCode) {
                case 99:
                    binding.address.setText(MemoryBean.address);
                    break;
            }
        }

    }

    private void setlists(int position, int Page) {
        if (!NetWorkUtils.isNetworkConnected()) {
            ToastUtils.show("网络未连接");
            return;
        }
        if (!isLodOk) {
            return;
        }
        if (MemoryBean.cityCode.equals("")) {
            ToastUtils.show("正在定位");
        } else {
            MobclickAgent.onEvent(mContext, (position + 3) + "");
            if (firstLevelList.get(position).getTypeName().equals("更多")) {
                Intent intent = new Intent(mContext, MoreActivity.class);
                startActivity(intent);
            } else {
                Intent intent = new Intent(mContext, SevenServiceActivity.class);
                if (Page == 1) {
                    position = position + 8;
                }
                intent.putExtra("typeCode", firstLevelList.get(position).getTypecode());
                intent.putExtra("projectId", firstLevelList.get(position).getId());
                intent.putExtra("projectName", firstLevelList.get(position).getTypeName());
                intent.putExtra("image", info.getRows().get(position).getImage());
                intent.putExtra("label", info.getRows().get(position).getLabel());
                if (firstLevelList.get(position).getId().equals("2")) {
                    intent.putExtra("Housemoving", "0");
                }

                startActivity(intent);
            }
        }
    }
}
