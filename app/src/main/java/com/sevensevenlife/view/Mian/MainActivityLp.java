package com.sevensevenlife.view.Mian;

import android.Manifest;
import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.annotation.NonNull;
import android.support.v4.app.ActivityCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.support.v7.app.ActionBarDrawerToggle;
import android.text.TextUtils;
import android.view.KeyEvent;
import android.view.LayoutInflater;
import android.view.View;
import android.view.Window;
import android.view.WindowManager;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.sevensevenlife.http.OkHttpUtils;
import com.sevensevenlife.interfaces.CheckUserTokenInterFace;
import com.sevensevenlife.interfaces.DialogListener;
import com.sevensevenlife.interfaces.HttpCallBack;
import com.sevensevenlife.utils.AppUtils;
import com.sevensevenlife.utils.BicycleGetSignUtils;
import com.sevensevenlife.utils.BicycleGetUserTokeUtils;
import com.sevensevenlife.utils.GuideDialog;
import com.sevensevenlife.utils.ImgLoadUtils;
import com.sevensevenlife.utils.LogUtils;
import com.sevensevenlife.utils.MyApplication;
import com.sevensevenlife.utils.PreferencesUtil;
import com.sevensevenlife.utils.RentCarUtils;
import com.sevensevenlife.utils.TimeUtils;
import com.sevensevenlife.utils.ToastUtils;
import com.sevensevenlife.view.custumview.BaseActivity;
import com.sevensevenlife.view.custumview.WebViewInfoActivity;
import com.sevensevenlife.view.DiaLog.DialogUtils;
import com.sevensevenlife.view.DiaLog.TitleDialog;
import com.sevensevenlife.view.Find.FindFragment;
import com.sevensevenlife.view.Find.InvitationCourtesyActivity;
import com.sevensevenlife.view.Home.AppMsg.AppMsgActivity;
import com.sevensevenlife.view.Home.HomeFragment;
import com.sevensevenlife.view.Home.MyHome.MyHomeActivity;
import com.sevensevenlife.view.Home.PublicBicycle.BalanceActivity;
import com.sevensevenlife.view.Order.OrderFragment;
import com.sevensevenlife.view.User.CollectListActivity;
import com.sevensevenlife.view.User.CouponListActivity;
import com.sevensevenlife.view.User.MyInfoActivity;
import com.sevensevenlife.view.User.SheZhiActivity;
import com.sevensevenlife.view.User.SuggestioinActivity;
import com.sevensevenlife.view.User.UserFragment;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.LayoutLpMainBinding;
import com.tencent.map.geolocation.TencentLocation;
import com.tencent.map.geolocation.TencentLocationListener;
import com.tencent.map.geolocation.TencentLocationManager;
import com.tencent.map.geolocation.TencentLocationRequest;
import com.sevensevenlife.utils.MemoryBean;

import java.io.File;

import static com.sevensevenlife.http.RequestUtils.DOWNLOAD;
import static com.sevensevenlife.http.RequestUtils.ZIXINGCHE_URL;
import static com.sevensevenlife.model.KEY.DOWNLOAD_LINK;
import static com.sevensevenlife.model.KEY.ONE_START;
import static com.sevensevenlife.model.KEY.PID;
import static com.sevensevenlife.model.KEY.STAROKEN;

/**
 * Created by Administrator on 2017/1/25 0025.
 */

public class MainActivityLp extends BaseActivity implements TencentLocationListener, View.OnClickListener {

    private Context mContext;

    private LayoutLpMainBinding binding;
    private TencentLocationRequest request;
    private int error;
    private TencentLocationManager locationManager;

    private String apkPath;

    private String datetime;

    private ActionBarDrawerToggle drawerToggle = null;

    //Fragment数组
    private Class<?> fragmentArray[] = new Class<?>[]{
            HomeFragment.class,
            OrderFragment.class,
            OrderFragment.class,
            FindFragment.class,
            UserFragment.class};

    //Tab标签标题数组
    private int mTabNameArray[] = new int[]{
            R.string.shouye,
            R.string.dingdan,
            R.string.sys,
            R.string.faxian,
            R.string.wode};

    private int[] mIconArray = new int[]{
            R.drawable.home_bar_home_selector,
            R.drawable.home_bar_order_selector,
            R.drawable.home_bar_order_selector,
            R.drawable.home_bar_find_selector,
            R.drawable.home_bar_user_selector
    };

    private String[] needPermissions = {
            Manifest.permission.ACCESS_COARSE_LOCATION,
            Manifest.permission.ACCESS_FINE_LOCATION,
            Manifest.permission.WRITE_EXTERNAL_STORAGE,
            Manifest.permission.READ_EXTERNAL_STORAGE,
            Manifest.permission.READ_PHONE_STATE,
            Manifest.permission.ACCESS_WIFI_STATE};

    private RentCarUtils rentCarUtils;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.layout_lp_main);
        mContext = this;
        rentCarUtils = new RentCarUtils(this);
        initViews();
        if (ActivityCompat.checkSelfPermission(this, needPermissions[0]) != 0 ||
                ActivityCompat.checkSelfPermission(this, needPermissions[1]) != 0 ||
                ActivityCompat.checkSelfPermission(this, needPermissions[2]) != 0 ||
                ActivityCompat.checkSelfPermission(this, needPermissions[3]) != 0 ||
                ActivityCompat.checkSelfPermission(this, needPermissions[4]) != 0 ||
                ActivityCompat.checkSelfPermission(this, needPermissions[5]) != 0) {
            ActivityCompat.requestPermissions(MainActivityLp.this, needPermissions, 1210);
        } else {
            Location();
        }
        if (!TextUtils.isEmpty(PreferencesUtil.getString("cityCode"))) {
            MemoryBean.cityCode = PreferencesUtil.getString("cityCode");
            MemoryBean.city = PreferencesUtil.getString("city");
            MemoryBean.Latitude = Double.valueOf(PreferencesUtil.getString("Latitude"));
            MemoryBean.Longitude = Double.valueOf(PreferencesUtil.getString("Longitude"));
        }
        if (!TextUtils.isEmpty(PreferencesUtil.getString(DOWNLOAD_LINK))) {
            OkHttpUtils.getInstance().OkHttpRequest(DOWNLOAD, PreferencesUtil.getString(DOWNLOAD_LINK),
                    new String[]{}, new String[]{}, new HttpCallBack() {
                        @Override
                        public void ok(String backString) {
                            if (!TextUtils.isEmpty(backString)) {
                                apkPath = backString;
                                mHandler.sendEmptyMessage(110);
                            } else {
                                mHandler.sendEmptyMessage(111);
                            }
                        }

                        @Override
                        public void error(String e) {

                        }
                    }, false);
        }
        binding.sysBt.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                rentCarUtils.sys();
            }
        });
        if (TextUtils.isEmpty(PreferencesUtil.getString("2.5.2_oneStart"))) {
            new GuideDialog(mContext);
            PreferencesUtil.putString("2.5.2_oneStart", "0");
        }
        drawerToggle = new ActionBarDrawerToggle(this, binding.drawerLayout, R.string.about_our, R.string.about_our);
        binding.drawerLayout.addDrawerListener(drawerToggle);
        binding.drawerLayout.setOnClickListener(this);
        if (MyApplication.getInstance().isLogin()) {
            ImgLoadUtils.Load(mContext, MyApplication.getInstance().getUserInfo().getRows().getHeadPic(), binding.userHeadImg, true);
            binding.tvUserName.setText(MyApplication.getInstance().getUserInfo().getRows().getNickName());
            binding.userHeadImg.setOnClickListener(this);
            binding.leftLayout.setOnClickListener(this);

            binding.userHome.setOnClickListener(this);

            binding.userInvitation.setOnClickListener(this);

            binding.userMsg.setOnClickListener(this);

            binding.userOpinion.setOnClickListener(this);

            binding.userSettled.setOnClickListener(this);

            binding.userSetup.setOnClickListener(this);

            binding.userOut.setOnClickListener(this);
            binding.userDiscount.setOnClickListener(this);
            binding.periphery.setOnClickListener(this);
            binding.ridingInformation.setOnClickListener(this);
            binding.balance.setOnClickListener(this);


        }
    }

    private myHandler mHandler = new myHandler();

    private Intent intent;

    @Override
    public void onClick(View v) {
        switch (v.getId()) {
            case R.id.drawer_layout:
                break;
            case R.id.user_head_img:
                Intent userIntent = new Intent(mContext, MyInfoActivity.class);

                startActivityForResult(userIntent, 1);
                break;
            case R.id.user_balance:
                ToastUtils.show(getResources().getString(R.string.qidai));
                break;

            case R.id.user_discount:
                if (MyApplication.getInstance().isLogin()) {
                    startActivity(new Intent(mContext, CouponListActivity.class));
                }
                break;

            case R.id.integral:
                ToastUtils.show(getResources().getString(R.string.qidai));
                break;

            case R.id.waiter:
                if (MemoryBean.industyListMap) {
                    Intent intent;
                    intent = new Intent(mContext, CollectListActivity.class);
                    startActivity(intent);
                } else {
                    ToastUtils.show("请稍等，当前网络较慢");
                }
                break;

            case R.id.user_home://我的小区
                if (MyApplication.getInstance().isLogin()) {
                    startActivity(new Intent(mContext, MyHomeActivity.class));
                } else {
                    ToastUtils.show("请先登录~");
                }
                break;
            case R.id.user_invitation://邀请
                startActivity(new Intent(mContext, InvitationCourtesyActivity.class));
                break;
            case R.id.user_msg://消息中心
                intent = new Intent(mContext, AppMsgActivity.class);
                startActivity(intent);
                break;
            case R.id.user_opinion://意见
                intent = new Intent(mContext, SuggestioinActivity.class);
                startActivity(intent);
                break;
            case R.id.user_setup://设置
                Intent intent = new Intent(mContext, SheZhiActivity.class);
                startActivity(intent);
                break;
            case R.id.user_settled://入驻
                Intent intents = new Intent();
                intents.setAction("android.intent.action.VIEW");
                Uri content_url = Uri.parse("http://218.75.134.187:7777/yxsh-api/download.jsp?type=service");
                intents.setData(content_url);
                startActivity(intents);
                break;
            case R.id.user_out:
                finish();
                System.exit(0);
                break;
            case R.id.periphery:
                String longitude = String.valueOf(MemoryBean.Longitude);
                String latitude = String.valueOf(MemoryBean.Latitude);
                if (longitude.equals("") && latitude.equals("")) {
                    ToastUtils.show("请返回首页重新定位");
                } else {
                    Intent in = new Intent(mContext, WebViewInfoActivity.class);
                    in.putExtra("UrlAddress", "http://map.changde.esstation.com/Home/Map?lnglat=" + longitude + "," + latitude);
                    in.putExtra("QQ_MAP", "true");
                    startActivity(in);
                }
                break;
            case R.id.riding_information:
                new BicycleGetUserTokeUtils().Check(new CheckUserTokenInterFace() {
                    @Override
                    public void True(String tonken) {
                        datetime = TimeUtils.yyyymmddhhmmss();
                        String sign = BicycleGetSignUtils.getSign(PreferencesUtil.getString(STAROKEN) + datetime + userInfo.getRows().getPhone() +
                                MyApplication.getInstance().getUserInfo().getRows().getCardNo());
                        Intent intent = new Intent(mContext, WebViewInfoActivity.class);
                        intent.putExtra("UrlAddress", ZIXINGCHE_URL + "/H5.V2/User/MyInfo"
                                + "?certno=" + MyApplication.getInstance().getUserInfo().getRows().getCardNo()
                                + "&datetime=" + datetime
                                + "&strtoken=" + PreferencesUtil.getString(STAROKEN)
                                + "&pid=" + PID
                                + "&telephone=" + userInfo.getRows().getPhone()
                                + "&sign=" + sign);
                        startActivity(intent);
                    }

                    @Override
                    public void False() {
                        ToastUtils.show("获取数据异常，请稍后重试");
                    }
                });
                break;
            case R.id.balance:
                startActivity(new Intent(mContext, BalanceActivity.class));
                break;


        }
    }


    private class myHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 110:
                    try {
                        PreferencesUtil.removeKey(DOWNLOAD_LINK);
                        PreferencesUtil.removeKey(ONE_START);
                        File file = new File(apkPath);
                        Intent intent = new Intent(Intent.ACTION_VIEW);
                        Uri apkUri;
                        //判读版本是否在7.0以上
                        if (Build.VERSION.SDK_INT >= 24) {
                            apkUri = FileProvider.getUriForFile(mContext, "com.example.youxiangshenghuo", file);
                            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
                        } else {
                            apkUri = Uri.fromFile(file);
                        }
                        intent.setDataAndType(apkUri, "application/vnd.android.package-archive");
                        mContext.startActivity(intent);
                    } catch (Exception e) {
                        DialogUtils.init(mContext)
                                .setTitle("Oops！更新失败，请前往应用宝重新下载安装")
                                .setOne("确定", null);
                    }

                    break;
                case 111:
                    ToastUtils.show("下载失败：uri=null，请去应用宝下载新版本");
                    break;
            }
        }
    }

    private void initViews() {

        binding.tabHost.setup(this, getSupportFragmentManager(), R.id.real_content);

        int count = fragmentArray.length;

        for (int i = 0; i < count; i++) {
            TabHost.TabSpec tabSpec = binding.tabHost.newTabSpec(String.valueOf(i)).setIndicator(getTabItemViews(i));
            binding.tabHost.addTab(tabSpec, fragmentArray[i], null);
        }

        binding.tabHost.getTabWidget().setPadding(0, 0, 0, 0);

        binding.tabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);

        binding.tabHost.setOnTabChangedListener(onTabChangeListener);

    }

    TabHost.OnTabChangeListener onTabChangeListener = new TabHost.OnTabChangeListener() {

        @Override
        public void onTabChanged(String tabId) {

            int currentIndex = Integer.parseInt(tabId);

            MyApplication.mainTabActivityIndex = currentIndex;
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
                Window window = getWindow();
                window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
                if (currentIndex == 4) {
                    window.setStatusBarColor(ContextCompat.getColor(mContext, R.color.user_bg));
                } else {
                    window.setStatusBarColor(ContextCompat.getColor(mContext, R.color.style_color));
                }

            }
            if (binding.tabHost.getCurrentTab() != currentIndex) {
                setCurrentPage(currentIndex);
            }

        }


    };

    public void setCurrentPage(int index) {
        binding.tabHost.setCurrentTab(index);
    }

    /**
     * 给Tab按钮设置图标和文字
     */
    private View getTabItemViews(int index) {

        View view = LayoutInflater.from(this).inflate(R.layout.layout_main_tab_item_child, null);

        TextView textView = (TextView) view.findViewById(R.id.tv_main_tab_item);
        ImageView imageView = (ImageView) view.findViewById(R.id.iv_main_tab_item);
        textView.setText(mTabNameArray[index]);

        int resourceId = mIconArray[index];

        imageView.setImageResource(resourceId);

        view.setTag(index + "");

        return view;
    }


    @Override
    protected void onResume() {

        binding.tabHost.setCurrentTab(MyApplication.mainTabActivityIndex);

        super.onResume();
    }

    private long mExitTime = 0;

    public boolean onKeyDown(int keyCode, KeyEvent event) {

        if (keyCode == KeyEvent.KEYCODE_BACK) {
            if ((System.currentTimeMillis() - mExitTime) > 1500) {

                ToastUtils.show("再按一次退出七七生活");

                mExitTime = System.currentTimeMillis();
            } else {

                finish();

                System.exit(0);

            }
            return true;
        }
        return super.onKeyDown(keyCode, event);
    }

    @Override
    public void onRequestPermissionsResult(int requestCode, @NonNull String[] permissions, @NonNull int[] grantResults) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults);
        rentCarUtils.requestPermissions(requestCode, permissions, grantResults);
        if (requestCode == 1210) {
            if (grantResults.length != 0 &&
                    grantResults[0] == 0 &&
                    grantResults[1] == 0 &&
                    grantResults[2] == 0 &&
                    grantResults[4] == 0 &&
                    grantResults[5] == 0) {
                Location();
            } else {
                new TitleDialog().SHOW(this, "没有定位权限，相关功能将无法使用，是否去设置打开？", "设置", "不设置", new DialogListener() {
                    @Override
                    public void buttType(int ButtType) {
                        if (ButtType == 0) {
                            AppUtils.startAppSettings();
                        }
                    }
                }, false);

            }
        }
    }

    private void Location() {
        request = TencentLocationRequest.create();
        request.setInterval(5000);
        request.setRequestLevel(3);
        locationManager = TencentLocationManager.getInstance(mContext);
        error = locationManager.requestLocationUpdates(request, this);
    }


    @Override
    public void onLocationChanged(TencentLocation tencentLocation, int i, String s) {
        LogUtils.e("123onLocationChanged", s + tencentLocation.getCity());
        try {
            if (tencentLocation != null) {
                if (error == 0) {
                    String cityCode = tencentLocation.getCityCode();
                    String city = tencentLocation.getCity();
                    String Latitude = tencentLocation.getLatitude() + "";
                    String Longitude = tencentLocation.getLongitude() + "";
                    PreferencesUtil.putString("city", city);
                    PreferencesUtil.putString("cityCode", cityCode);
                    PreferencesUtil.putString("Latitude", Latitude);
                    PreferencesUtil.putString("Longitude", Longitude);
                    MemoryBean.city = tencentLocation.getCity();
                    MemoryBean.cityCode = tencentLocation.getCityCode();
                    MemoryBean.Latitude = tencentLocation.getLatitude();
                    MemoryBean.Longitude = tencentLocation.getLongitude();
                    String streetNo = "";
                    if (!tencentLocation.getStreetNo().equals("Unknown")) {
                        streetNo = tencentLocation.getStreetNo();
                    }
                    String address = tencentLocation.getCity() + tencentLocation.getDistrict() +
                            tencentLocation.getStreet() + streetNo;
                    MemoryBean.address = address;
                    LogUtils.e(123, address);
                    Intent intent = new Intent();
                    intent.setAction("com.example.youxiangshenghuo.Location");
                    intent.putExtra("Locationmsg", address);
                    sendBroadcast(intent);
                    locationManager.removeUpdates(this);
                }
            }
        } catch (Exception e) {
            LogUtils.e("Exception", e.getMessage());
            LogUtils.e("123e.getMessage()", e.getMessage());
        }

    }

    @Override
    public void onStatusUpdate(String s, int i, String s1) {
        LogUtils.e("123onStatusUpdate", s + s1);
    }


    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        rentCarUtils.activityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    binding.tvUserName.setText(MyApplication.getInstance().getUserInfo().getRows().getNickName());
                    ImgLoadUtils.Load(mContext, MyApplication.getInstance().getUserInfo().getRows().getHeadPic(), binding.userHeadImg, true);
                    break;
            }
        }
    }

    public Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                switch (msg.what) {
                    case 100:
                        binding.drawerLayout.openDrawer(binding.leftLayout);
                        break;
                }
            }
        }
    };

}
