package com.sevensevenlife.utils;

import android.annotation.SuppressLint;
import android.app.Activity;
import android.content.Context;
import android.os.Handler;
import android.os.Message;
import android.text.TextUtils;

import com.sevensevenlife.http.MyRequest;
import com.sevensevenlife.interfaces.MyHttpCallBack;
import com.sevensevenlife.model.httpmodel.AilPayMode;
import com.alipay.sdk.app.PayTask;
import com.alipay.sdk.pay.PayResult;

import static com.sevensevenlife.http.RequestUtils.GET;
import static com.sevensevenlife.http.RequestUtils.URI.DOWN_ORDER;

/**
 * Created by Administrator on 2017/4/12 0012.
 * 支付宝支付工具
 */

public class PayAliUtils {
    private static final int SDK_PAY_FLAG = 1;
    private static mHandler mHandler;

    public void goPAY(final Context mContext, String orderNo, final Activity activity) {
        mHandler = new mHandler();
        MyRequest.POST(GET, new String[]{"sessionId", "orderNo", "payType"}, new String[]{
                MyApplication.getInstance().getUserInfo().getRows().getSessionId(),
                orderNo, "1"
        }, DOWN_ORDER, AilPayMode.class.getName(), 111, new MyHttpCallBack() {
            @Override
            public <T> void ok(T backMode, final String jsonString, int httpTY) {
                final AilPayMode mode = (AilPayMode) backMode;
                new Runnable() {
                    @Override
                    public void run() {
                        PayTask alipay = new PayTask(activity);
                        String result = alipay.pay(mode.getRows(), true);
                        Message msg = new Message();
                        msg.what = SDK_PAY_FLAG;
                        msg.obj = result;
                        mHandler.sendMessage(msg);
                    }
                }.run();

            }

            @Override
            public void error(String e, int uriType) {
                ToastUtils.show(e);
            }
        });
    }

    @SuppressLint("HandlerLeak")
    private class mHandler extends Handler {
        @SuppressWarnings("unused")
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case SDK_PAY_FLAG: {
                    PayResult payResult = new PayResult((String) msg.obj);
                    /**
                     * 同步返回的结果必须放置到服务端进行验证（验证的规则请看https://doc.open.alipay.com/doc2/
                     * detail.htm?spm=0.0.0.0.xdvAU6&treeId=59&articleId=103665&
                     * docType=1) 建议商户依赖异步通知
                     */
                    String resultInfo = payResult.getResult();// 同步返回需要验证的信息

                    String resultStatus = payResult.getResultStatus();
                    // 判断resultStatus 为“9000”则代表支付成功，具体状态码代表含义可参考接口文档
                    if (TextUtils.equals(resultStatus, "9000")) {
                        ToastUtils.show("支付成功");
                    } else {
                        // 判断resultStatus 为非"9000"则代表可能支付失败
                        // "8000"代表支付结果因为支付渠道原因或者系统原因还在等待支付结果确认，最终交易是否成功以服务端异步通知为准（小概率状态）
                        if (TextUtils.equals(resultStatus, "8000")) {
                            ToastUtils.show("支付结果确认中");
                        } else {
                            ToastUtils.show("支付失败");
                            // 其他值就可以判断为支付失败，包括用户主动取消支付，或者系统返回的错误
                        }
                    }

                    break;
                }
            }
        }
    }

    ;
}
