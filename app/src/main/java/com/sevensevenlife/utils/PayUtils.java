package com.sevensevenlife.utils;

import android.content.Context;

import com.sevensevenlife.http.MyRequest;
import com.sevensevenlife.interfaces.MyHttpCallBack;
import com.sevensevenlife.model.httpmodel.PayMode;
import com.sevensevenlife.view.Order.PayActivity;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;

import static com.sevensevenlife.http.RequestUtils.GET;
import static com.sevensevenlife.http.RequestUtils.URI.DOWN_ORDER;
import static com.example.youxiangshenghuo.wxapi.WXEntryActivity.app_id;

/**
 * Created by Administrator on 2017/4/12 0012.
 * <p>
 * 微信支付工具
 */

public class PayUtils {

    public static void goPAY(final Context mContext, String orderNo) {
        MyRequest.POST(GET, new String[]{"sessionId", "orderNo", "payType"}, new String[]{
                MyApplication.getInstance().getUserInfo().getRows().getSessionId(),
                orderNo, "2"
        }, DOWN_ORDER, PayMode.class.getName(), 111, new MyHttpCallBack() {
            @Override
            public <T> void ok(T backMode, String jsonString, int httpTY) {
                PayMode mode = (PayMode) backMode;
                final IWXAPI msgApi = WXAPIFactory.createWXAPI(mContext, app_id);
                msgApi.registerApp(app_id);
                PayReq request = new PayReq();
                request.appId = app_id;
                request.partnerId = mode.getRows().getPartnerid();
                request.prepayId = mode.getRows().getPrepayid();
                request.packageValue = mode.getRows().getPackageX();
                request.nonceStr = mode.getRows().getNoncestr();
                request.timeStamp = mode.getRows().getTimestamp();
                request.sign = mode.getRows().getSign();
                PayActivity.btPaycK = false;
                msgApi.sendReq(request);

            }

            @Override
            public void error(String e, int uriType) {
                ToastUtils.show(e);
            }
        });
    }
}
