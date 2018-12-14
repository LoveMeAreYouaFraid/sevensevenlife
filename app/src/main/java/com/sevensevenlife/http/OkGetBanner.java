package com.sevensevenlife.http;

import com.sevensevenlife.interfaces.MyHttpCallBack;
import com.sevensevenlife.model.httpmodel.GetBannerMode;

import static com.sevensevenlife.http.RequestUtils.GET;
import static com.sevensevenlife.http.RequestUtils.URI.GET_BANNER;

/**
 * Created by Administrator on 2017/1/6 0006.
 * 无视
 */

public class OkGetBanner {
    public static void get(int position, MyHttpCallBack callBack, boolean isc) {
        MyRequest.POST(GET, new String[]{"position"}, new String[]{position + ""},
                GET_BANNER, GetBannerMode.class.getName(), position, callBack, isc);
    }

    public static void get(String position, MyHttpCallBack callBack, boolean isc) {
        MyRequest.POST(GET, new String[]{"position"}, new String[]{position + ""},
                GET_BANNER, GetBannerMode.class.getName(), 99, callBack, isc);
    }
}
