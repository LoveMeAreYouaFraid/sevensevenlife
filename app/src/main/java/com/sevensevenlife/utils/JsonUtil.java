package com.sevensevenlife.utils;

import android.text.TextUtils;


import com.google.gson.Gson;
import com.google.gson.GsonBuilder;
import com.google.gson.JsonSyntaxException;

import org.json.JSONArray;
import org.json.JSONException;

import java.util.ArrayList;
import java.util.List;

/**
 * JSON转换工具类
 *
 * @param <T>
 */
public class JsonUtil<T> {

    public List<T> json2List(String jsonStr, String className) {
        List<T> list = new ArrayList<T>();
        if (jsonStr != null && !"".equals(jsonStr)) {
            try {
                JSONArray jarry = new JSONArray(jsonStr);
                int length = jarry.length();
                for (int i = 0; i < length; i++) {
                    list.add(json2Bean(jarry.getString(i), className));
                }
            } catch (JSONException e) {
                e.printStackTrace();
            }
        }
        return list;
    }

    public T json2Bean(String jsonString, String className) {
        try {
            Class<?> clazz = Class.forName(className);
            if (TextUtils.isEmpty(jsonString)) {
                return (T) clazz.newInstance();
            } else {
                jsonString = jsonString.replaceAll(", null", "");
                GsonBuilder builder;
                builder = new GsonBuilder();
//                builder.registerTypeAdapter(Uri.class, new UriDeserializer());
                Gson gson = builder.create();
                T t = (T) gson.fromJson(jsonString, clazz);
                return t;
            }
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        } catch (JsonSyntaxException e) {
            e.printStackTrace();
        } catch (Exception e) {
            e.printStackTrace();
        }
        return null;
    }

    public String bean2Json(T t) {
        return new Gson().toJson(t);
    }

    public String list2Json(List<T> t) {
        return new Gson().toJson(t);
    }

    public static String getJson(List<String> imgUrl) {
        String urlJson = null;
        if (imgUrl.size() == 0) {
            return "";
        }
        for (int i = 0; i < imgUrl.size(); i++) {
            if (i == 0) {
                urlJson = "{" + "\"" + "url" + "\"" + ":" + "\"" + imgUrl.get(i) + "\"" + "}";
            } else {
                urlJson = urlJson + "," + "{" + "\"" + "url" + "\"" + ":" + "\"" + imgUrl.get(i) + "\"" + "}";
            }

        }
        urlJson = "[" + urlJson + "]";
        LogUtils.e(123, urlJson);
        return urlJson;
    }

    /**
     * 传入储存的key 返回对应的model
     *
     * @param key
     * @return
     */
    public T getModel(String key, String className) {

        return json2Bean(PreferencesUtil.getString(key), className);
    }

}
