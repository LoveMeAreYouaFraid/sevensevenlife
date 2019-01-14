package com.sevensevenlife.utils;

import android.app.Application;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.provider.Settings;

import com.sevensevenlife.model.httpmodel.CommunityInfo;
import com.sevensevenlife.model.httpmodel.TopIcMode;
import com.sevensevenlife.model.httpmodel.UserInfo;

import java.util.List;

import cn.jpush.android.api.JPushInterface;

/**
 * Created by Administrator on 2016/12/26 0026.
 */

public class MyApplication extends Application {
    private static MyApplication mApplication;
    private UserInfo userInfo;
    private boolean login = false;
    public static Integer mainTabActivityIndex = 0;
    private boolean loding = false;
    public String device_id;
    private List<CommunityInfo> communityInfos;
    private TopIcMode dynamicTopIcMode;
    private TopIcMode voteTopIcMode;
    private String CommunityId;

    public static synchronized MyApplication getInstance() {
        return mApplication;
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mApplication = this;
        if (NetWorkUtils.isNetworkConnected()) {
            JPushInterface.setDebugMode(true);    // 设置开启日志,发布时请关闭日志
            JPushInterface.resumePush(this);
            JPushInterface.init(this);
        }
    }

    public String getCommunityId() {
        return CommunityId;
    }

    public void setCommunityId(String communityId) {
        CommunityId = communityId;
    }

    public TopIcMode getVoteTopIcMode() {
        return voteTopIcMode;
    }

    public void setVoteTopIcMode(TopIcMode voteTopIcMode) {
        this.voteTopIcMode = voteTopIcMode;
    }

    public TopIcMode getDynamicTopIcMode() {
        return dynamicTopIcMode;
    }

    public void setDynamicTopIcMode(TopIcMode dynamicTopIcMode) {
        this.dynamicTopIcMode = dynamicTopIcMode;
    }

    public String getDevice_id() {
        return Settings.Secure.getString(getContentResolver(), Settings.Secure.ANDROID_ID);
    }

    public List<CommunityInfo> getCommunityInfos() {
        return communityInfos;
    }

    public void setCommunityInfos(List<CommunityInfo> communityInfos) {
        this.communityInfos = communityInfos;
    }

    public UserInfo getUserInfo() {
        return userInfo;
    }

    public boolean isLoding() {
        return loding;
    }

    public void setLoding(boolean loding) {
        this.loding = loding;
    }

    public void setUserInfo(UserInfo userInfo) {
        this.userInfo = userInfo;
    }

    public boolean isLogin() {
        return login;
    }

    public void setLogin(boolean login) {
        this.login = login;
    }

    public String getVersionCode() {
        return getPackageInfo().versionName;
    }

    private static PackageInfo getPackageInfo() {
        PackageInfo pi = null;
        try {
            PackageManager pm = MyApplication.getInstance().getPackageManager();
            pi = pm.getPackageInfo(MyApplication.getInstance().getPackageName(),
                    PackageManager.GET_CONFIGURATIONS);
            return pi;
        } catch (Exception e) {
            e.printStackTrace();
        }

        return pi;
    }
}
