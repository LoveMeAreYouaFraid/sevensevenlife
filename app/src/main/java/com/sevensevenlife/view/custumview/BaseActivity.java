package com.sevensevenlife.view.custumview;

import android.content.Context;
import android.os.Bundle;
import android.support.v7.app.AppCompatActivity;

import com.sevensevenlife.model.httpmodel.UserInfo;
import com.sevensevenlife.utils.MyApplication;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by Administrator on 2016/12/29 0029.
 */

public class BaseActivity extends AppCompatActivity {
    public Context mContext;
    public boolean isLogin = false;
    public UserInfo userInfo;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        isLogin = MyApplication.getInstance().isLogin();
        // SDK在统计Fragment时，需要关闭Activity自带的页面统计，
        // 然后在每个页面中重新集成页面统计的代码(包括调用了 onResume 和 onPause 的Activity)。
        MobclickAgent.setDebugMode(true);
        MobclickAgent.openActivityDurationTrack(false);
//         MobclickAgent.setAutoLocation(true);
        MobclickAgent.setSessionContinueMillis(1000);
//        MobclickAgent.startWithConfigure(
//                new MobclickAgent.UMAnalyticsConfig(mContext, "58d1fa233eae2525f8001ac2", "Umeng",
//                        MobclickAgent.EScenarioType.E_UM_NORMAL));
        MobclickAgent.setScenarioType(mContext, MobclickAgent.EScenarioType.E_UM_NORMAL);
        if (isLogin) {
            userInfo = MyApplication.getInstance().getUserInfo();
        }
    }

    @Override
    protected void onRestart() {
        super.onRestart();
        isLogin = MyApplication.getInstance().isLogin();
    }

    @Override
    protected void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }

    @Override
    protected void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }
}
