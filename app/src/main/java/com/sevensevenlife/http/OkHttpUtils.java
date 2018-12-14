package com.sevensevenlife.http;

import android.text.TextUtils;

import com.sevensevenlife.interfaces.HttpCallBack;
import com.sevensevenlife.model.httpmodel.CacheMode;
import com.sevensevenlife.utils.FileUtils;
import com.sevensevenlife.utils.JsonUtil;
import com.sevensevenlife.utils.LogUtils;
import com.sevensevenlife.utils.NetWorkUtils;
import com.sevensevenlife.utils.PreferencesUtil;
import com.sevensevenlife.utils.TimeUtils;
import com.sevensevenlife.utils.ToastUtils;

import org.json.JSONObject;

import java.io.File;
import java.io.FileOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.concurrent.TimeUnit;

import okhttp3.Call;
import okhttp3.Callback;
import okhttp3.FormBody;
import okhttp3.MediaType;
import okhttp3.MultipartBody;
import okhttp3.OkHttpClient;
import okhttp3.Request;
import okhttp3.RequestBody;
import okhttp3.Response;

import static com.sevensevenlife.http.RequestUtils.DOWNLOAD;
import static com.sevensevenlife.http.RequestUtils.QEQUEST_URL;
import static com.sevensevenlife.http.RequestUtils.URI.GER_VERSION;
import static com.sevensevenlife.http.RequestUtils.URI.UP_LOAD;


/**
 * Created by 10237 on 2016/11/11.
 *
 * 网络请求底层
 */

public class OkHttpUtils {
    private static OkHttpUtils mOkHttpUtils;
    private int RequestMode;
    private CacheMode cacheMode;

    public static synchronized OkHttpUtils getInstance() {
        mOkHttpUtils = new OkHttpUtils();
        return mOkHttpUtils;
    }

    public void OkHttpRequest(int Request_Mode, final String uri, final String[] key,
                              final String[] value, final HttpCallBack httpBack, final boolean isCache) {
        LogUtils.e("apiUri=", QEQUEST_URL + uri + ParamString(key, value));
        if (isCache) {
            if (!TextUtils.isEmpty(PreferencesUtil.getString(uri + ParamString(key, value)))) {
                cacheMode = new JsonUtil<CacheMode>().json2Bean(PreferencesUtil.getString(uri + ParamString(key, value))
                        , CacheMode.class.getName());
                String startTime = cacheMode.getTime();
                String endTime = TimeUtils.getYearMonthAndDay(System.currentTimeMillis());
                if (startTime == endTime) {
                    httpBack.ok(cacheMode.getJsonString());
                    LogUtils.e(123, "缓存数据" + cacheMode.getJsonString());
                    return;
                } else {
                    PreferencesUtil.removeKey(uri + ParamString(key, value));
                }
            }
        }
        if (!NetWorkUtils.isNetworkConnected()) {
            ToastUtils.show("网络未连接");
            httpBack.error("网络未连接");
            return;
        }
        OkHttpClient okHttpClient = new OkHttpClient.Builder()
                .connectTimeout(10, TimeUnit.SECONDS)
                .build();
        Request request = null;
        RequestMode = Request_Mode;
        switch (Request_Mode) {
            case RequestUtils.GET:
                request = new Request.Builder()
                        .url(QEQUEST_URL + uri + ParamString(key, value))
                        .build();
                break;
            case RequestUtils.POST:
                request = new Request.Builder()
                        .url(QEQUEST_URL + uri)
                        .post(mRequestBody(key, value))
                        .build();
                break;
            case RequestUtils.POST_TWO:
                request = new Request.Builder()
                        .url(QEQUEST_URL + uri)
                        .post(mRequestBody(key, value))
                        .put(RequestBody.create(
                                MediaType.parse("application/json; charset=utf-8"),
                                value[0]))// post json提交
                        .build();
                break;
            case DOWNLOAD:
                request = new Request.Builder()
                        .url(uri)
                        .build();
                break;
        }

        Call call = okHttpClient.newCall(request);


        call.enqueue(new Callback() {
                         @Override
                         public void onFailure(Call call, IOException e) {
                             LogUtils.e("onFailure", e.getMessage());
                             httpBack.error("请求超时");
                         }

                         @Override
                         public void onResponse(Call call, Response response) throws IOException {
                             if (RequestMode == DOWNLOAD) {
                                 File file = FileUtils.newApkFile();
                                 InputStream is = null;
                                 FileOutputStream fos = null;
                                 byte[] buf = new byte[2048];
                                 int len;
                                 long downloadLen = 0;
                                 try {
                                     is = response.body().byteStream();
                                     fos = new FileOutputStream(file);
                                     while ((len = is.read(buf)) != -1) {
                                         fos.write(buf, 0, len);
                                         downloadLen += len;
                                     }
                                     fos.flush();
                                     httpBack.ok(file.getPath());
                                 } catch (Exception e) {
                                     e.printStackTrace();
                                     httpBack.error("下载失败");
                                     LogUtils.e("下载失败", e.getMessage());
                                 } finally {
                                     try {
                                         if (is != null) is.close();
                                         if (fos != null) fos.close();
                                     } catch (IOException e) {
                                         httpBack.error("下载失败");
                                         LogUtils.e("下载失败", e.getMessage());
                                     }
                                 }

                             } else {
                                 String StringJson = response.body().string();
                                 PreferencesUtil.putString(uri + ParamString(key, value), StringJson);
                                 LogUtils.e("backString=", StringJson);
                                 try {
                                     JSONObject j = new JSONObject(StringJson);
                                     JSONObject hoder = j.getJSONObject("header");
                                     String code = hoder.getString("code");
                                     String message = hoder.getString("message");
                                     if (uri.equals(GER_VERSION)) {
                                         httpBack.ok(StringJson);
                                     } else if (code.equals("000")) {
                                         if (isCache) {
                                             CacheMode cacheMode = new CacheMode();
                                             cacheMode.setTime(TimeUtils.getYearMonthAndDay(System.currentTimeMillis()));
                                             cacheMode.setJsonString(StringJson);
                                             String cacheString = new JsonUtil<CacheMode>().bean2Json(cacheMode);
                                             PreferencesUtil.putString(uri + ParamString(key, value), cacheString);
                                         }
                                         httpBack.ok(StringJson);
                                     } else {
                                         httpBack.error(message);
                                     }
                                 } catch (Exception e) {
                                     httpBack.error("获取数据失败，请稍后重试");
//                                     httpBack.error(e.getMessage());
                                 }
                             }
                         }
                     }

        );
    }

    public void OkHttpCompletely(int Request_Mode, final String uri, final String[] key,
                                 final String[] value, final HttpCallBack httpBack, final boolean isCache) {
        LogUtils.e("apiUri=", QEQUEST_URL + uri + ParamString(key, value));

        if (isCache) {
            if (!TextUtils.isEmpty(PreferencesUtil.getString(uri + ParamString(key, value)))) {
                cacheMode = new JsonUtil<CacheMode>().json2Bean(PreferencesUtil.getString(uri + ParamString(key, value))
                        , CacheMode.class.getName());
                String startTime = cacheMode.getTime();
                String endTime = TimeUtils.getYearMonthAndDay(System.currentTimeMillis());
                LogUtils.e(123, "startTime=" + startTime + "\n+endTime=" + endTime);
                if (startTime.equals(endTime)) {
                    httpBack.ok(cacheMode.getJsonString());
                    LogUtils.e(123, "缓存数据" + cacheMode.getJsonString());
                    return;
                } else {
                    LogUtils.e(123, "缓存数据过期" + cacheMode.getJsonString());
                    PreferencesUtil.removeKey(uri + ParamString(key, value));
                }
            } else {
                LogUtils.e(123, "没有缓存");
            }
        } else {
            LogUtils.e(123, "不允许缓存");
        }
        if (!NetWorkUtils.isNetworkConnected()) {
            ToastUtils.show("网络未连接");
            httpBack.error("网络未连接");
            return;
        }

        OkHttpClient okHttpClient = new OkHttpClient();
        Request request = null;

        switch (Request_Mode) {
            case RequestUtils.GET:
                request = new Request.Builder()
                        .url(uri + ParamString(key, value))
                        .build();

                break;
            case RequestUtils.POST:
                request = new Request.Builder()
                        .url(uri)
                        .post(mRequestBody(key, value))
                        .build();
                break;

        }

        Call call = okHttpClient.newCall(request);


        call.enqueue(new Callback() {
            @Override
            public void onFailure(Call call, IOException e) {
                LogUtils.e(123, e.getMessage());
                httpBack.error("请求超时");
            }

            @Override
            public void onResponse(Call call, Response response) throws IOException {
                String StringJson = response.body().string();
                LogUtils.e("backString=", StringJson);
                if (isCache) {
                    CacheMode cacheMode = new CacheMode();
                    cacheMode.setTime(TimeUtils.getYearMonthAndDay(System.currentTimeMillis()));
                    cacheMode.setJsonString(StringJson);
                    String cacheString = new JsonUtil<CacheMode>().bean2Json(cacheMode);
                    PreferencesUtil.putString(uri + ParamString(key, value), cacheString);
                    LogUtils.e(123, "缓存成功");
                }
                httpBack.ok(StringJson);

            }
        });
    }

    public void PostImg(File file, final HttpCallBack callback) {
        if (!NetWorkUtils.isNetworkConnected()) {
            ToastUtils.show("网络未连接");
            return;
        }
        OkHttpClient client = new OkHttpClient();

        MultipartBody.Builder multipartBody = new MultipartBody.Builder().setType(MultipartBody.FORM);

        multipartBody.addFormDataPart("file", file.getName(), RequestBody.create(MediaType.parse("image/png"), file));

        Request request = new Request.Builder()
                .url(QEQUEST_URL + UP_LOAD)
                .post(multipartBody.build())
                .build();

        client
                .newBuilder()
                .readTimeout(60000, TimeUnit.MILLISECONDS).build()
                .newCall(request)
                .enqueue(new Callback() {

                    @Override
                    public void onFailure(Call call, IOException e) {
                        callback.error("请求超时");

                        LogUtils.e(123, e.toString());
                    }

                    @Override
                    public void onResponse(Call call, Response response) {
                        String jsonString = null;
                        try {
                            jsonString = response.body().string();
                            JSONObject jsonObject = new JSONObject(jsonString);
                            String uri = jsonObject.getString("url");
                            callback.ok(uri);
                            LogUtils.e("onResponse", uri);

                        } catch (Exception e) {
                            callback.error(e.getMessage());
                        }


                    }
                });

    }


    private String ParamString(String[] key, String[] value) {
        String mParamString = "";
        if (key == null || key.length == 0) {
            return mParamString;
        } else {
            for (int i = 0; i < key.length; i++) {
                if (!TextUtils.isEmpty(key[i])) {
                    if (value[i] == null) {
                        value[i] = "";
                    }
                    if (i == 0) {
                        mParamString = mParamString + "?" + key[i] + "=" + value[i];
                    } else {
                        mParamString = mParamString + "&" + key[i] + "=" + value[i];
                    }

                }
            }
        }
        return mParamString;

    }

    public static RequestBody mRequestBody(String[] key, String[] value) {
        RequestBody requestBody;
        FormBody.Builder builder = new FormBody.Builder();
        if (key != null) {
            for (int i = 0; i < key.length; i++) {
                if (!TextUtils.isEmpty(key[i])) {
                    if (TextUtils.isEmpty(value[i])) {
                        value[i] = "";
                    }
                    builder.add(key[i], value[i]);
                    LogUtils.e("参数", "key=  " + key[i] + "\t\t\tvalue=  " + value[i]);
                }
            }
        }

        requestBody = builder.build();

        return requestBody;
    }


}
