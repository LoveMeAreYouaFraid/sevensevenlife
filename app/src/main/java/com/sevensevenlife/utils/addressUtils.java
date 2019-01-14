package com.sevensevenlife.utils;

import android.util.Log;

import com.sevensevenlife.interfaces.addressUtilsInterface;
import com.tencent.lbssearch.TencentSearch;
import com.tencent.lbssearch.httpresponse.BaseObject;
import com.tencent.lbssearch.httpresponse.HttpResponseListener;
import com.tencent.lbssearch.object.Location;
import com.tencent.lbssearch.object.param.Geo2AddressParam;
import com.tencent.lbssearch.object.param.SearchParam;
import com.tencent.lbssearch.object.result.Geo2AddressResultObject;
import com.tencent.lbssearch.object.result.SearchResultObject;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/3/9 0009.
 */

public class addressUtils {
    private static String address;

    public static String get(double lat, double lng) {

        Geo2AddressParam param = new Geo2AddressParam().location(new Location().lat((float) lat).lng((float) lng));
        param.get_poi(true);
        TencentSearch tencentSearch = new TencentSearch(MyApplication.getInstance());
        tencentSearch.geo2address(param, new HttpResponseListener() {
            @Override
            public void onSuccess(int i, BaseObject object) {
                Geo2AddressResultObject oj = (Geo2AddressResultObject) object;
                if (oj.result != null) {
                    Log.e("demo", "address:" + oj.result.address);
                    address = oj.result.address;
                }
            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {

            }
        });
        return address;

    }

    public static void get(String city, String params, final addressUtilsInterface listener) {
        final List<String> strings = new ArrayList<>();
        final List<String> title = new ArrayList<>();

        SearchParam.Region r = new SearchParam.Region().poi(city);
        SearchParam param = new SearchParam().keyword(params).boundary(r);
        TencentSearch tencentSearch = new TencentSearch(MyApplication.getInstance());
        tencentSearch.search(param, new HttpResponseListener() {
            @Override
            public void onSuccess(int i, BaseObject baseObject) {

                SearchResultObject oj = (SearchResultObject) baseObject;
                if (oj.data != null) {
                    for (SearchResultObject.SearchResultData data : oj.data) {
                        Log.e("demo", "title:" + data.address);
                        address = address + data.address;
                        strings.add(data.address);
                        title.add(data.title);
                    }
                }
                listener.back(strings, title);
            }

            @Override
            public void onFailure(int i, String s, Throwable throwable) {

            }
        });

    }


}
