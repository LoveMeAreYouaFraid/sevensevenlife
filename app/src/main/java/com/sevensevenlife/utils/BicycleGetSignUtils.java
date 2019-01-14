package com.sevensevenlife.utils;

import com.alipay.sdk.pay.SignUtils;

import java.io.UnsupportedEncodingException;
import java.net.URLEncoder;

import static com.sevensevenlife.model.KEY.RSA_STRING;

/**
 * Created by Administrator on 2017/7/17 0017.
 */

public class BicycleGetSignUtils {
    public static String getSign(String str) {
        String sign = SignUtils.sign(str, RSA_STRING);
        try {
            sign = URLEncoder.encode(sign, "UTF-8");
        } catch (UnsupportedEncodingException e) {
            e.printStackTrace();
        }
        return sign;
    }
}
