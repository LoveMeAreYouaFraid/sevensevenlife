package com.sevensevenlife.view.Home.PublicBicycle;

import android.Manifest;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.databinding.DataBindingUtil;
import android.net.Uri;
import android.os.Bundle;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.support.v4.app.ActivityOptionsCompat;
import android.support.v4.content.ContextCompat;
import android.support.v4.util.Pair;
import android.support.v7.widget.StaggeredGridLayoutManager;
import android.text.TextUtils;
import android.view.View;
import android.widget.LinearLayout;

import com.sevensevenlife.http.OkHttpUtils;
import com.sevensevenlife.http.RequestUtils;
import com.sevensevenlife.interfaces.HttpCallBack;
import com.sevensevenlife.interfaces.ListItemListener;
import com.sevensevenlife.model.httpmodel.CheckUserInfo;
import com.sevensevenlife.model.httpmodel.TokonMode;
import com.sevensevenlife.model.KEY;
import com.sevensevenlife.utils.DPIUtil;
import com.sevensevenlife.utils.JsonUtil;
import com.sevensevenlife.utils.LogUtils;
import com.sevensevenlife.utils.MyApplication;
import com.sevensevenlife.utils.PreferencesUtil;
import com.sevensevenlife.utils.TimeUtils;
import com.sevensevenlife.utils.ToastUtils;
import com.sevensevenlife.view.custumview.BaseActivity;
import com.sevensevenlife.utils.TextViewParser;
import com.sevensevenlife.view.custumview.WebViewInfoActivity;
import com.sevensevenlife.view.custumview.zxing.CaptureActivity;
import com.sevensevenlife.view.Home.PublicBicycle.Adapter.BicycleHomeAdapter;
import com.sevensevenlife.view.User.LoginActivity;
import com.alipay.sdk.pay.SignUtils;
import com.example.youxiangshenghuo.R;
import com.example.youxiangshenghuo.databinding.BicycleHomeLayoutBinding;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;
import com.umeng.analytics.MobclickAgent;
import com.sevensevenlife.utils.MemoryBean;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static com.sevensevenlife.http.RequestUtils.PROTOCOL;
import static com.sevensevenlife.http.RequestUtils.ZIXINGCHE_URL;
import static com.sevensevenlife.model.KEY.BAND_CAR;
import static com.sevensevenlife.model.KEY.CAR_RENTAL;
import static com.sevensevenlife.model.KEY.PID;
import static com.sevensevenlife.model.KEY.RSA_STRING;
import static com.sevensevenlife.model.KEY.STAROKEN;

/**
 * 租车首页
 * Created by 10237 on 2016/12/23.
 */

public class BicycleHomeActivity extends BaseActivity implements View.OnClickListener, ListItemListener {
    private BicycleHomeLayoutBinding binding;
    private BicycleHomeAdapter adapter;
    private String datetime;
    private int position;

    @Override
    protected void onCreate(Bundle savedInstanceState) {
        super.onCreate(savedInstanceState);
        binding = DataBindingUtil.setContentView(this, R.layout.bicycle_home_layout);
        binding.title.imgBack.setOnClickListener(this);
        binding.sys.setOnClickListener(this);
        binding.title.title.setText("公共自行车");
        binding.recyclerView.setLayoutManager(
                new StaggeredGridLayoutManager(4, LinearLayout.VERTICAL));
        adapter = new BicycleHomeAdapter();
        binding.recyclerView.setAdapter(adapter);
        datetime = TimeUtils.yyyymmddhhmmss();
        adapter.setListItemListener(this);
        if (isLogin) {
            if (!TextUtils.isEmpty(PreferencesUtil.getString(STAROKEN))) {
                CheckUserInfo();
            } else {
                getUserToken();
            }
        }
        setUserAgreement();

    }

    private void setUserAgreement() {
        TextViewParser textViewParser = new TextViewParser();
        textViewParser.append("使用本软件即视为同意",
                DPIUtil.dip2px(12), ContextCompat.getColor(mContext, R.color.txt_black));

        textViewParser.append("《租车服务协议》",
                DPIUtil.dip2px(12), ContextCompat.getColor(mContext, R.color.style_color),
                new View.OnClickListener() {
                    @Override
                    public void onClick(View view) {
                        Intent intent = new Intent(BicycleHomeActivity.this, WebViewInfoActivity.class);
                        intent.putExtra("UrlAddress", PROTOCOL);
                        intent.putExtra("IsRecharge", "1");
                        startActivity(intent);
                    }
                });


        textViewParser.parse(binding.tvUserAgreement);
    }

    private void getUserToken() {
        String datetime = TimeUtils.yyyymmddhhmmss();
        String sign = getSign(PID + MyApplication.getInstance().getUserInfo().getRows().getPhone() + datetime);
        new OkHttpUtils().OkHttpCompletely(RequestUtils.POST,
                ZIXINGCHE_URL + "/H5.V2/User/getusertoken" +
                        "?Pid=" + PID +
                        "&datetime=" + datetime +
                        "&Telephone=" + userInfo.getRows().getPhone() +
                        "&sign=" + sign
                , new String[]{},
                new String[]{}, new HttpCallBack() {
                    @Override
                    public void ok(String backString) {
                        TokonMode tokonMode = new JsonUtil<TokonMode>().json2Bean(backString, TokonMode.class.getName());
                        if (tokonMode.getMessage() != null) {
                            LogUtils.e(123, "new " + tokonMode.getMessage());
                            PreferencesUtil.removeKey(STAROKEN);
                            PreferencesUtil.putString(STAROKEN, tokonMode.getMessage());
                        }
                    }

                    @Override
                    public void error(String e) {

                    }
                }, false);

    }

    private void getviewUserToken() {
        String datetime = TimeUtils.yyyymmddhhmmss();
        String sign = getSign(PID + MyApplication.getInstance().getUserInfo().getRows().getPhone() + datetime);
        new OkHttpUtils().OkHttpCompletely(RequestUtils.POST,
                ZIXINGCHE_URL + "/H5.V2/User/getusertoken" +
                        "?Pid=" + PID +
                        "&datetime=" + datetime +
                        "&Telephone=" + userInfo.getRows().getPhone() +
                        "&sign=" + sign
                , new String[]{},
                new String[]{}, new HttpCallBack() {
                    @Override
                    public void ok(String backString) {
                        TokonMode tokonMode = new JsonUtil<TokonMode>().json2Bean(backString, TokonMode.class.getName());
                        if (tokonMode.getMessage() != null) {
                            LogUtils.e(123, "new " + PreferencesUtil.getString(STAROKEN));
                            mhander.sendEmptyMessage(1993);
                            PreferencesUtil.removeKey(STAROKEN);
                            PreferencesUtil.putString(STAROKEN, tokonMode.getMessage());
                        }
                    }

                    @Override
                    public void error(String e) {
                        ToastUtils.show("Tokon获取失败");
                    }
                }, false);

    }

    private CheckUserInfo checkUserInfo;

    private void CheckUserInfo() {

        String datetime = TimeUtils.yyyymmddhhmmss();
        new OkHttpUtils().OkHttpCompletely(RequestUtils.POST, ZIXINGCHE_URL + "/H5.V2/User/CheckUserInfo" +
                        "?strToken=" + PreferencesUtil.getString(STAROKEN) +
                        "&datetime=" + datetime +
                        "&Pid=" + PID +
                        "&sign=" + getSign(PreferencesUtil.getString(STAROKEN) + datetime + PID),
                new String[]{},
                new String[]{}, new HttpCallBack() {
                    @Override
                    public void ok(String backString) {
                        checkUserInfo = new JsonUtil<CheckUserInfo>().json2Bean(backString,
                                CheckUserInfo.class.getName());
                        if (!checkUserInfo.isTrue()) {
                            getUserToken();
                        }
                    }

                    @Override
                    public void error(String e) {
                        getUserToken();
                    }
                }, false);


    }

    private void ChildViewCheckUserInfo() {

        String datetime = TimeUtils.yyyymmddhhmmss();
        new OkHttpUtils().OkHttpCompletely(RequestUtils.POST, ZIXINGCHE_URL + "/H5.V2/User/CheckUserInfo" +
                        "?strToken=" + PreferencesUtil.getString(STAROKEN) +
                        "&datetime=" + datetime +
                        "&Pid=" + PID +
                        "&sign=" + getSign(PreferencesUtil.getString(STAROKEN) + datetime + PID),
                new String[]{},
                new String[]{}, new HttpCallBack() {
                    @Override
                    public void ok(String backString) {
                        checkUserInfo = new JsonUtil<CheckUserInfo>().json2Bean(backString,
                                CheckUserInfo.class.getName());
                        if (!checkUserInfo.isTrue()) {
                            getviewUserToken();
                        } else {
                            mhander.sendEmptyMessage(1993);

                        }
                    }

                    @Override
                    public void error(String e) {
                        getUserToken();
                    }
                }, false);


    }

    private mHandler mhander = new mHandler();

    class mHandler extends Handler {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            switch (msg.what) {
                case 1993:
                    startView(position);
                    break;
            }
        }
    }

    @Override
    public void onClick(View view) {
        switch (view.getId()) {
            case R.id.img_back:
                finish();
                break;
            case R.id.sys:
                CheckUserInfo();
                if (TextUtils.isEmpty(PreferencesUtil.getString(CAR_RENTAL))) {
                    ToastUtils.show("请选择租车方式");
                    startActivityForResult(new Intent(mContext, CarRentalActivity.class), 1);
                } else {
                    try {
                        showDialog();
                    } catch (Exception e) {
                    }

                }
                break;
        }

    }


    private String getSign(String str) {
        String sign = SignUtils.sign(str, RSA_STRING);
        try {
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sign;
    }


    @Override
    public void Item(int position) {
        if (!isLogin) {
            ToastUtils.show("亲：您尚未登陆，请登录");
            startActivity(new Intent(mContext, LoginActivity.class));
        }
    }

    @Override
    public void ChildView(View v, int position) {
        if (!isLogin) {
            ToastUtils.show("亲：您尚未登陆，请登录");
            startActivity(new Intent(mContext, LoginActivity.class));
            return;
        }

        if (TextUtils.isEmpty(userInfo.getRows().getCardNo()) ||
                TextUtils.isEmpty(userInfo.getRows().getRealName())) {
            ToastUtils.show("请填写真实姓名和身份证号码");
            Intent intent = new Intent(this, EditUserInfoActivity.class);
            startActivityForResult(intent, 0);
            return;
        }
        this.position = position;
        switch (position) {
            case 0:
                ActivityOptionsCompat options = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        this, new Pair[]{Pair.create(v, "touxiang")});
                ActivityCompat.startActivity(mContext,
                        new Intent(mContext, CarRentalActivity.class)
                        , options.toBundle());

                break;
            case 5:
                startActivity(new Intent(mContext, BalanceActivity.class));
                break;
            case 4://找回
                ActivityOptionsCompat option = ActivityOptionsCompat.makeSceneTransitionAnimation(
                        this, new Pair[]{Pair.create(v, "touxiang")});
                ActivityCompat.startActivity(mContext, new Intent(mContext, LoseCarActivity.class),
                        option.toBundle());
                break;

            case 2:
//                String packageName = "com.eg.android.AlipayGphone";
//                if (AppUtils.isInstall(mContext, packageName)) {
//                    ToastUtils.show("正在启动支付宝...");
//                    AppUtils.RunApp(mContext, packageName);
//                } else {
//                    ToastUtils.show("请安装支付宝");
//                }

                try {
                    //利用Intent打开支付宝
                    //支付宝跳过开启动画打开扫码和付款码的url scheme分别是alipayqr://platformapi/startapp?saId=10000007
                    //和alipayqr://platformapi/startapp?saId=20000056
                    Uri uri = Uri.parse("alipayqr://platformapi/startapp?saId=10000007");
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } catch (Exception e) {
                    //若无法正常跳转，在此进行错误处理
                    ToastUtils.show("请安装支付宝");
                }
                break;

            case 6://报修
                startActivity(new Intent(mContext, FaultRepairActivity.class));
                break;
            case 3:
            case 1:
            case 7:
                ChildViewCheckUserInfo();
                break;
        }
    }

    void startView(int position) {
        switch (position) {
            case 3:
                String longitude = String.valueOf(MemoryBean.Longitude);
                String latitude = String.valueOf(MemoryBean.Latitude);
                if (longitude.equals("") && latitude.equals("")) {
                    ToastUtils.show("请返回首页重新定位");
                } else {
                    Intent intent = new Intent(mContext, WebViewInfoActivity.class);
                    intent.putExtra("UrlAddress", "http://map.changde.esstation.com/Home/Map?lnglat=" + longitude + "," + latitude);
                    intent.putExtra("QQ_MAP", "true");
                    startActivity(intent);
                }
                break;

            case 1:
                String sign = getSign(PreferencesUtil.getString(STAROKEN) + datetime + userInfo.getRows().getPhone() +
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
                break;
            case 7:
                String signs = getSign(PreferencesUtil.getString(STAROKEN) + datetime + userInfo.getRows().getPhone() +
                        MyApplication.getInstance().getUserInfo().getRows().getCardNo());
                Intent intents = new Intent(mContext, WebViewInfoActivity.class);
                intents.putExtra("UrlAddress", ZIXINGCHE_URL + "/H5.V2/User/MyInfo"
                        + "?certno=" + MyApplication.getInstance().getUserInfo().getRows().getCardNo()
                        + "&datetime=" + datetime
                        + "&strtoken=" + PreferencesUtil.getString(STAROKEN)
                        + "&pid=" + PID
                        + "&telephone=" + userInfo.getRows().getPhone()
                        + "&sign=" + signs);
                intents.putExtra("IsRecharge", "0");
                startActivity(intents);
                break;
        }
    }

    //显示对话框
    public void showDialog() {
        if (TextUtils.isEmpty(userInfo.getRows().getCardNo()) ||
                TextUtils.isEmpty(userInfo.getRows().getRealName())) {
            ToastUtils.show("请填写真实姓名和身份证号码");
            Intent intent = new Intent(this, EditUserInfoActivity.class);
            startActivityForResult(intent, 9);
        } else {
            int s;
            if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.M) {
                s = mContext.checkSelfPermission(Manifest.permission.CAMERA);
                if (s != PackageManager.PERMISSION_GRANTED) {

                    ActivityCompat.requestPermissions(this, new String[]{
                            Manifest.permission.CAMERA,}, CAMERA_PERMISSION);

                } else {
                    startCamera();
                }
            } else {
                startCamera();
            }

        }
    }

    private void startCamera() {
        MobclickAgent.onEvent(this, "2");
        IntentIntegrator integrator = new IntentIntegrator(this);
        integrator.setCaptureActivity(CaptureActivity.class);
        integrator.setBeepEnabled(true);
        integrator.setPrompt("请将二维码对准取景框中");
        integrator.setCameraId(0);
        integrator.initiateScan();
        LogUtils.e(88,"8888888888startCamera");
    }

    private static final int CAMERA_PERMISSION = 1101;

    @Override
    public void onRequestPermissionsResult(int requestCode, String[] permissions, int[] grantResults) {
        LogUtils.e(88,"8888888888onRequestPermissionsResult");
        if (requestCode == CAMERA_PERMISSION) {
            if (permissions[0].equals(Manifest.permission.CAMERA) && grantResults[0] == 0) {
                //用户同意使用write
                LogUtils.e(123, "1212124524521524");
                startCamera();
            } else {
                //用户不同意，自行处理即可
                ToastUtils.show("没有权限，请去设置打开");
                LogUtils.e(123, "22222222222222");
            }
        }
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (resultCode == RESULT_OK) {
            switch (requestCode) {
                case 1:
                    try {
                        showDialog();
                    } catch (Exception e) {
                    }
                    break;
                case 0:
                    if (!TextUtils.isEmpty(data.getStringExtra(KEY.REAL_NAME))) {
                        userInfo.getRows().setRealName(data.getStringExtra(KEY.REAL_NAME));
                    }
                    if (!TextUtils.isEmpty(data.getStringExtra(KEY.CARD_NO))) {
                        userInfo.getRows().setCardNo(data.getStringExtra(KEY.CARD_NO));
                    }
                    break;
                case 9:
                    if (!TextUtils.isEmpty(data.getStringExtra(KEY.CARD_NO))) {
                        userInfo.getRows().setCardNo(data.getStringExtra(KEY.CARD_NO));
                    }
                    if (!TextUtils.isEmpty(data.getStringExtra(KEY.REAL_NAME))) {
                        userInfo.getRows().setRealName(data.getStringExtra(KEY.REAL_NAME));
                    }

                    break;
            }
            try {
                if (!TextUtils.isEmpty(intentResult.getContents())) {
                    LogUtils.e(123, "!TextUtils.isEmpty(intentResult.getContents())");
                    if (PreferencesUtil.getString(CAR_RENTAL).equals(BAND_CAR)) {
                        BandCar(intentResult.getContents());
                    } else {
                        yaJing(intentResult.getContents());
                    }
                    return;
                } else {
                    LogUtils.e(123, "lp");
                }
            } catch (Exception e) {
                LogUtils.e(123, e);
            }
        }


    }

    private String iTradeType = "";

    /**
     * 押金租车
     *
     * @param
     */
    //扫码租车
    public void yaJing(String code) {
        iTradeType = "413";
        String sign = getSign(PreferencesUtil.getString(STAROKEN) + datetime + code + PID + MyApplication.getInstance().getUserInfo().getRows().getPhone() + iTradeType);

        Intent intent = new Intent(this, WebViewInfoActivity.class);
        intent.putExtra("UrlAddress", ZIXINGCHE_URL + "/H5.V2/UserScan/RendCar"
                + "?telephone=" + MyApplication.getInstance().getUserInfo().getRows().getPhone() + "&"
                + "datetime=" + datetime + "&"
                + "strtoken=" + PreferencesUtil.getString(STAROKEN) + "&"
                + "code=" + code + "&"
                + "sign=" + sign + "&"
                + "iTradeType=" + iTradeType + "&"
                + "PID=" + PID
        );
        startActivity(intent);
    }

    /**
     * 绑卡
     */
    private void BandCar(String result) {
        if (result != null) {
            String code = result;
            String iTradeType = "513";
            String cardNo = userInfo.getRows().getCardNo();
            String realName = userInfo.getRows().getRealName();
            String Telephone = userInfo.getRows().getPhone();
            String datetime = TimeUtils.yyyymmddhhmmss();

            String sign = getSign(PreferencesUtil.getString(STAROKEN) + datetime + code + PID + Telephone + cardNo + iTradeType + realName);
            //设置 银行卡和姓名
            Intent intent = new Intent(mContext, WebViewInfoActivity.class);
            intent.putExtra("UrlAddress", ZIXINGCHE_URL + "/H5.V2/UserScan/RendCar"
                    + "?telephone=" + Telephone
                    + "&certNO=" + cardNo
                    + "&strtoken=" + PreferencesUtil.getString(STAROKEN)
                    + "&code=" + code
                    + "&sign=" + sign
                    + "&iTradeType=" + iTradeType
                    + "&PID=" + PID
                    + "&datetime=" + datetime
                    + "&username=" + realName
            );
            startActivity(intent);
        }
    }
}
