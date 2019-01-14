package com.sevensevenlife.utils;

import android.Manifest;
import android.app.Activity;
import android.content.Intent;
import android.content.pm.PackageManager;
import android.os.Handler;
import android.os.Message;
import android.support.v4.app.ActivityCompat;
import android.text.TextUtils;

import com.sevensevenlife.interfaces.CheckUserTokenInterFace;
import com.sevensevenlife.view.custumview.WebViewInfoActivity;
import com.sevensevenlife.view.custumview.zxing.CaptureActivity;
import com.sevensevenlife.view.Home.PublicBicycle.CarRentalActivity;
import com.sevensevenlife.view.Home.PublicBicycle.EditUserInfoActivity;
import com.google.zxing.integration.android.IntentIntegrator;
import com.google.zxing.integration.android.IntentResult;

import static com.sevensevenlife.http.RequestUtils.ZIXINGCHE_URL;
import static com.sevensevenlife.model.KEY.BAND_CAR;
import static com.sevensevenlife.model.KEY.CAR_RENTAL;
import static com.sevensevenlife.model.KEY.PID;
import static com.sevensevenlife.model.KEY.STAROKEN;

/**
 * Created by Administrator on 2017/5/12 0012.
 */

public class RentCarUtils {
    private Activity mContext;
    private RentCarUtils rentCarUtils;
    private static final int CAMERA_PERMISSION = 1101;
    private static final int START_CAMERA = 1109;
    private MyHandle mHandle = new MyHandle();

    public RentCarUtils(Activity context) {
        mContext = context;
        rentCarUtils = this;
    }

    public void sys() {
        /**
         *检查是否登录
         */
        if (!MyApplication.getInstance().isLogin()) {
            ToastUtils.show("请先登录");
            return;
        }
        /**
         *检查没有租车方式
         */
        if (TextUtils.isEmpty(PreferencesUtil.getString(CAR_RENTAL))) {
            ToastUtils.show("请选择租车方式");
            mContext.startActivityForResult(new Intent(mContext, CarRentalActivity.class), 1);
            return;
        }
        /**
         *检查实名信息
         */
        if (TextUtils.isEmpty(MyApplication.getInstance().getUserInfo().getRows().getCardNo()) ||
                TextUtils.isEmpty(MyApplication.getInstance().getUserInfo().getRows().getRealName())) {
            ToastUtils.show("请填写真实姓名和身份证号码");
            Intent intent = new Intent(mContext, EditUserInfoActivity.class);
            mContext.startActivityForResult(intent, 9);
            return;
        }

        if (ActivityCompat.checkSelfPermission(mContext, Manifest.permission.CAMERA) != PackageManager.PERMISSION_GRANTED) {
            ActivityCompat.requestPermissions(mContext, new String[]{
                    Manifest.permission.CAMERA,}, CAMERA_PERMISSION);
        } else {
            new BicycleGetUserTokeUtils().Check(new CheckUserTokenInterFace() {
                @Override
                public void True(String tonken) {
                    mHandle.sendEmptyMessage(START_CAMERA);
                }

                @Override
                public void False() {
                    ToastUtils.show("获取信息失败，请稍后重试");
                }
            });

        }

    }


    public void requestPermissions(int requestCode, String[] permissions, int[] grantResults) {
        if (requestCode == CAMERA_PERMISSION) {
            if (permissions[0].equals(Manifest.permission.CAMERA) && grantResults[0] == 0) {
                startCamera();
            } else {
                ToastUtils.show("没有权限，请去设置打开");
            }
        }
    }

    public void activityResult(int requestCode, int resultCode, Intent data) {
        IntentResult intentResult = IntentIntegrator.parseActivityResult(requestCode, resultCode, data);
        if (resultCode == mContext.RESULT_OK) {
            try {
                if (!TextUtils.isEmpty(intentResult.getContents())) {
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

    private void startCamera() {
        IntentIntegrator integrator = new IntentIntegrator(mContext);
        integrator.setCaptureActivity(CaptureActivity.class);
        integrator.setBeepEnabled(true);
        integrator.setPrompt("请将二维码对准取景框中");
        integrator.setCameraId(0);
        integrator.initiateScan();
    }

    private class MyHandle extends Handler {
        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case START_CAMERA:
                    startCamera();
                    break;
            }
        }

    }

    /**
     * 押金租车
     *
     * @param
     */
    //扫码租车
    public void yaJing(String code) {
        String iTradeType = "413";
        String datetime = TimeUtils.yyyymmddhhmmss();
        String sign = BicycleGetSignUtils.getSign(PreferencesUtil.getString(STAROKEN) + datetime + code + PID + MyApplication.getInstance().getUserInfo().getRows().getPhone() + iTradeType);

        Intent intent = new Intent(mContext, WebViewInfoActivity.class);
        intent.putExtra("UrlAddress", ZIXINGCHE_URL + "/H5.V2/UserScan/RendCar"
                + "?telephone=" + MyApplication.getInstance().getUserInfo().getRows().getPhone() + "&"
                + "datetime=" + datetime + "&"
                + "strtoken=" + PreferencesUtil.getString(STAROKEN) + "&"
                + "code=" + code + "&"
                + "sign=" + sign + "&"
                + "iTradeType=" + iTradeType + "&"
                + "PID=" + PID
        );
        mContext.startActivity(intent);
    }

    /**
     * 绑卡
     */
    private void BandCar(String result) {
        if (result != null) {
            String code = result;
            String iTradeType = "513";
            String cardNo = MyApplication.getInstance().getUserInfo().getRows().getCardNo();
            String realName = MyApplication.getInstance().getUserInfo().getRows().getRealName();
            String Telephone = MyApplication.getInstance().getUserInfo().getRows().getPhone();
            String datetime = TimeUtils.yyyymmddhhmmss();

            String sign = BicycleGetSignUtils.getSign(PreferencesUtil.getString(STAROKEN) + datetime + code + PID + Telephone + cardNo + iTradeType + realName);
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
            mContext.startActivity(intent);
        }
    }
}
