package com.sevensevenlife.view.Mian;

import android.content.Context;
import android.content.Intent;
import android.os.Build;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.content.ContextCompat;
import android.text.TextUtils;
import android.view.Window;
import android.view.WindowManager;

import com.sevensevenlife.http.MyRequest;
import com.sevensevenlife.interfaces.DialogListener;
import com.sevensevenlife.interfaces.MyHttpCallBack;
import com.sevensevenlife.model.httpmodel.CodeVersion;
import com.sevensevenlife.model.httpmodel.GetBannerMode;
import com.sevensevenlife.model.httpmodel.PublicMode;
import com.sevensevenlife.model.httpmodel.UserInfo;
import com.sevensevenlife.model.KEY;
import com.sevensevenlife.utils.JsonUtil;
import com.sevensevenlife.utils.LogUtils;
import com.sevensevenlife.utils.MyApplication;
import com.sevensevenlife.utils.NetWorkUtils;
import com.sevensevenlife.utils.OldPreferencesUtils;
import com.sevensevenlife.utils.PreferencesUtil;
import com.sevensevenlife.utils.ToastUtils;
import com.sevensevenlife.view.custumview.BaseActivity;
import com.sevensevenlife.view.DiaLog.DialogUtils;
import com.sevensevenlife.view.DiaLog.TitleDialog;
import com.example.youxiangshenghuo.R;

import cn.jpush.android.api.JPushInterface;

import static com.sevensevenlife.http.RequestUtils.GET;
import static com.sevensevenlife.http.RequestUtils.POST;
import static com.sevensevenlife.http.RequestUtils.URI.ADD_INSTALL_INFO;
import static com.sevensevenlife.http.RequestUtils.URI.EDIT_USER_INFO;
import static com.sevensevenlife.http.RequestUtils.URI.GER_VERSION;
import static com.sevensevenlife.http.RequestUtils.URI.GET_BANNER;
import static com.sevensevenlife.model.KEY.CARD_NO;
import static com.sevensevenlife.model.KEY.DOWNLOAD_LINK;
import static com.sevensevenlife.model.KEY.HEAD_PIC;
import static com.sevensevenlife.model.KEY.NICK_NAME;
import static com.sevensevenlife.model.KEY.ONE_START;
import static com.sevensevenlife.model.KEY.REAL_NAME;
import static com.sevensevenlife.model.KEY.SESSIONID;
import static com.sevensevenlife.model.KEY.USER;

public class WelcomeActivity extends BaseActivity implements MyHttpCallBack {
    private Context mContext;
    private String VersionCode;
    private CodeVersion codeVersion;
    private UserInfo userInfo;
    private String sessionId = "";
    private mHandler mhandler;
    private String deviceId;


    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mhandler = new mHandler();
        mContext = this;
        Window window = getWindow();
        window.addFlags(WindowManager.LayoutParams.FLAG_DRAWS_SYSTEM_BAR_BACKGROUNDS);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            window.setStatusBarColor(ContextCompat.getColor(mContext, R.color.white));
        }
        setContentView(R.layout.activity_welcom);
        VersionCode = MyApplication.getInstance().getVersionCode();

        if (!NetWorkUtils.isNetworkConnected()) {
            goActivity(main());
        } else {

            if (TextUtils.isEmpty(PreferencesUtil.getString(ONE_START))) {//如果是第一次启动新版本
                if (!TextUtils.isEmpty(MyApplication.getInstance().device_id)) {
                    deviceId = MyApplication.getInstance().getDevice_id();
                } else {
                    deviceId = System.currentTimeMillis() + "android";
                }

                MyRequest.POST(POST, new String[]{
                                "os_type",
                                "app_type",
                                "device_id",
                                "longitude",
                                "latitude",
                                "op_type",
                                "app_version"}, new String[]{
                                "a",
                                "c",
                                deviceId,
                                "",
                                "",
                                "0",
                                MyApplication.getInstance().getVersionCode()
                        }, ADD_INSTALL_INFO,
                        PublicMode.class.getName(), 9, this);

            } else {//如果是第2次启动新版本

                init();

            }

        }
    }

    private void init() {
        if (!TextUtils.isEmpty(PreferencesUtil.getString(USER))) {
            MyApplication.getInstance().setLogin(true);
            UserInfo userInfo = new JsonUtil<UserInfo>().json2Bean(PreferencesUtil.getString(USER), UserInfo.class.getName());
            MyApplication.getInstance().setUserInfo(userInfo);
        }
        MyRequest.POST(POST, new String[]{"version", "osType", "appType",},
                new String[]{VersionCode, "a", "c",}, GER_VERSION, CodeVersion.class.getName(), 0, this);
    }

    private Intent goIntent;

    private void goActivity(final Intent intent) {
        goIntent = intent;
        new Thread(new Runnable() {
            @Override
            public void run() {
                try {
                    Thread.sleep(500);
                    mhandler.sendEmptyMessage(0);
                } catch (Exception e) {
                    e.printStackTrace();
                    LogUtils.e(123, e.getMessage());
                }

            }
        }).start();

    }

    class mHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 0:
                    startActivity(goIntent);
                    finish();
                    break;
            }
        }
    }

    private Intent main() {
        return new Intent(mContext, MainActivityLp.class);
    }

    private void upDataDialog(final String String, final boolean isUpdeta, String info) {
        new TitleDialog().SHOW(mContext, "发现新版本是否更新？\n" + info, "", "",
                new DialogListener() {
                    @Override
                    public void buttType(int ButtType) {
                        if (ButtType == 0) {
                            ToastUtils.show("正在后台下载中...");
                            PreferencesUtil.putString(DOWNLOAD_LINK, String);
                            MyRequest.POST(GET, new String[]{"position"},
                                    new String[]{"3"}, GET_BANNER, GetBannerMode.class.getName(), 928, WelcomeActivity.this,true);
                        } else {
                            if (isUpdeta) {
                                DialogUtils.init(mContext)
                                        .setTitle("Oops~，不升级将会到时软件无法使用哦~")
                                        .setOne("升级", new DialogListener() {
                                            @Override
                                            public void buttType(int ButtType) {
                                                ToastUtils.show("正在后台下载中...");
                                                PreferencesUtil.putString(DOWNLOAD_LINK, String);
                                                MyRequest.POST(GET, new String[]{"position"},
                                                        new String[]{"3"}, GET_BANNER, GetBannerMode.class.getName(), 928, WelcomeActivity.this,true);
                                            }
                                        })
                                        .setTwo("没流量，先不升了", new DialogListener() {
                                            @Override
                                            public void buttType(int ButtType) {
                                                finish();
                                            }
                                        });
                            } else {
                                MyRequest.POST(GET, new String[]{"position"},
                                        new String[]{"3"}, GET_BANNER, GetBannerMode.class.getName(), 928, WelcomeActivity.this,true);
                            }
                        }

                    }
                }, false);
    }

    @Override
    public <T> void ok(T backMode, String jsonString, int httpTY) {
        switch (httpTY) {
            case 0:
                try {
                    codeVersion = (CodeVersion) backMode;

                    if (!codeVersion.getRows().get(0).getVername().equals(VersionCode)) {
                        upDataDialog(codeVersion.getRows().get(0).getVerlink(),
                                codeVersion.getRows().get(0).isForced_update(),
                                codeVersion.getRows().get(0).getVerinfo()
                        );
                    } else {
                        MyRequest.POST(GET, new String[]{"position"},
                                new String[]{"3"}, GET_BANNER, GetBannerMode.class.getName(), 928, this,true);
                    }
                } catch (Exception e) {
                    MyRequest.POST(GET, new String[]{"position"},
                            new String[]{"3"}, GET_BANNER, GetBannerMode.class.getName(), 928, this,true);
                }

                break;
            case 928:

                GetBannerMode mode = (GetBannerMode) backMode;
                if (mode != null &&
                        mode.getRows() != null &&
                        mode.getRows().size() >= 1 &&
                        !TextUtils.isEmpty(mode.getRows().get(0).getPic_url())) {
                    goActivity(new Intent(mContext, AdvertisementActivity.class)
                            .putExtra("PicUrl", mode.getRows().get(0).getPic_url())
                            .putExtra("LinkValue", mode.getRows().get(0).getLink_value()));
                } else {
                    goActivity(main());
                }

                break;
            case 3:
                userInfo = (UserInfo) backMode;
                userInfo.getRows().setSessionId(sessionId);
                PreferencesUtil.putString(KEY.USER, new JsonUtil<UserInfo>().bean2Json(userInfo));
                MyApplication.getInstance().setUserInfo(userInfo);
                MyApplication.getInstance().setLogin(true);
                init();
                break;
            case 9:
                PreferencesUtil.putString(ONE_START, "0");
                if (!TextUtils.isEmpty(OldPreferencesUtils.getString("sessionId"))) {//如果有老版本缓存

                    sessionId = OldPreferencesUtils.getString("sessionId");

                    MyRequest.POST(POST, new String[]{SESSIONID, NICK_NAME, HEAD_PIC, REAL_NAME, CARD_NO},

                            new String[]{sessionId, "", "", "", ""},

                            EDIT_USER_INFO, UserInfo.class.getName(), 3, this);
                } else {//如果没有
                    init();
                }
                break;
        }
    }


    @Override
    public void error(String e, int uriType) {
        goActivity(main());
    }

    @Override
    protected void onResume() {
        super.onResume();
        JPushInterface.onResume(mContext);
    }

    @Override
    protected void onPause() {
        super.onPause();
        JPushInterface.onPause(mContext);
    }
}
