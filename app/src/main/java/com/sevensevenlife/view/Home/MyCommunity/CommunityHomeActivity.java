package com.sevensevenlife.view.Home.MyCommunity;

import android.content.Context;
import android.content.Intent;
import android.databinding.DataBindingUtil;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;
import android.view.LayoutInflater;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TabHost;
import android.widget.TextView;

import com.sevensevenlife.http.MyRequest;
import com.sevensevenlife.http.RequestUtils;
import com.sevensevenlife.interfaces.MyHttpCallBack;
import com.sevensevenlife.model.httpmodel.PublicMode;
import com.sevensevenlife.utils.LogUtils;
import com.sevensevenlife.utils.MyApplication;
import com.sevensevenlife.utils.PreferencesUtil;
import com.sevensevenlife.utils.ToastUtils;
import com.sevensevenlife.view.custumview.BaseActivity;
import com.sevensevenlife.view.DiaLog.MyProgressDialog;
import com.sevensevenlife.view.Home.MyHome.MyHomeActivity;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.CommunityHomeActivityLayoutBinding;
import com.google.zxing.client.android.Intents;
import com.google.zxing.integration.android.IntentIntegrator;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by Administrator on 2017/11/14 0014.
 */

public class CommunityHomeActivity extends BaseActivity implements MyHttpCallBack {
    private CommunityHomeActivityLayoutBinding binding;
    private Context mContext;
    //Fragment数组
    private Class<?> fragmentArray[] = new Class<?>[]{
            BusinessFragment.class,
            AuthorityFragment.class};

    //Tab标签标题数组
    private int mTabNameArray[] = new int[]{
            R.string.community_tab_business,
            R.string.community_tab_authority};

    private int[] mIconArray = new int[]{
            R.drawable.cmt_tab_icon_business_selector,
            R.drawable.cmt_tab_icon_authority_selector};

    @Override
    public void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        mContext = this;
        MyApplication.getInstance().setCommunityId(PreferencesUtil.getString("communityId"));
        if (TextUtils.isEmpty(MyApplication.getInstance().getCommunityId())) {
            startActivity(new Intent(mContext, MyHomeActivity.class));
            finish();
        }

        binding = DataBindingUtil.setContentView(this, R.layout.community_home_activity_layout);
        binding.title.rightButton.setText("扫");
        binding.title.rightButton.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                startCamera();
            }
        });
        getDate();

        binding.tabHost.setup(this, getSupportFragmentManager(), R.id.real_content);

        int count = fragmentArray.length;

        for (int i = 0; i < count; i++) {
            TabHost.TabSpec tabSpec = binding.tabHost.newTabSpec(String.valueOf(i)).setIndicator(getTabItemViews(i));
            binding.tabHost.addTab(tabSpec, fragmentArray[i], null);
        }

        binding.tabHost.getTabWidget().setPadding(0, 0, 0, 0);

        binding.tabHost.getTabWidget().setShowDividers(LinearLayout.SHOW_DIVIDER_NONE);

        binding.tabHost.setOnTabChangedListener(onTabChangeListener);

        binding.title.imgBack.setOnClickListener(new View.OnClickListener() {
            @Override
            public void onClick(View v) {
                finish();
            }
        });
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

    private void startCamera() {
        MobclickAgent.onEvent(mContext, "2");
        IntentIntegrator integrator = new IntentIntegrator(CommunityHomeActivity.this);
        integrator.setCaptureActivity(CaptureActivity.class);
        integrator.setBeepEnabled(true);
        integrator.setPrompt("请将二维码对准取景框中");
        integrator.setCameraId(0);
        integrator.initiateScan();
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (data == null || TextUtils.isEmpty(data.getStringExtra(Intents.Scan.RESULT))) {
            return;
        }
        LogUtils.e(888, "11111111111data.getStringExtra:" + data.getStringExtra(Intents.Scan.RESULT));
        String communityStr = data.getStringExtra(Intents.Scan.RESULT).split("&community=")[1];
        LogUtils.e(888, "22222222222communityStr:" + communityStr);
        if (TextUtils.isEmpty(communityStr)) {
            handler.sendEmptyMessage(0);
        } else {
            MyApplication.getInstance().setCommunityId(communityStr);
            handler.sendEmptyMessage(1);
        }
    }

    TabHost.OnTabChangeListener onTabChangeListener = new TabHost.OnTabChangeListener() {

        @Override
        public void onTabChanged(String tabId) {

           /* int currentIndex = Integer.parseInt(tabId);

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
            }*/

        }


    };

    @Override
    public <T> void ok(T backMode, String jsonString, int httpTY) {
        MyProgressDialog.getInstance().cancel();
        switch (httpTY) {
            case 14:
                getDate();
            case 15:
                binding.title.title.setText("小区名称");
        }
    }

    @Override
    public void error(String e, int uriType) {
        MyProgressDialog.getInstance().cancel();
        ToastUtils.show(e);
    }

    private void getDate() {

    }

    Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg != null) {
                switch (msg.what) {
                    case 0:
                        ToastUtils.show("无效二维码");
                        break;
                    case 1:
                        MyProgressDialog.getInstance().show(mContext, "Loading...");
                        MyRequest.POST(RequestUtils.POST,
                                new String[]{"sessionId", "communityId"},
                                new String[]{
                                        MyApplication.getInstance().getUserInfo().getRows().getSessionId(),
                                        MyApplication.getInstance().getCommunityId()
                                },
                                RequestUtils.URI.ADD_COMMUNITY,
                                PublicMode.class.getName(),
                                15,
                                CommunityHomeActivity.this);
                        break;
                }
            }
        }
    };
}
