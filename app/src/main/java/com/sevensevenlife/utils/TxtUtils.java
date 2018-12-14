package com.sevensevenlife.utils;

import java.security.MessageDigest;
import java.util.ArrayList;
import java.util.List;

/**
 * Created by Administrator on 2017/5/11 0011.
 */

public class TxtUtils<T> {
    public static String Md5(String plainText) {
        StringBuffer buf = null;
        try {
            MessageDigest md = MessageDigest.getInstance("MD5");
            md.update(plainText.getBytes());
            byte b[] = md.digest();
            int i;
            buf = new StringBuffer("");
            for (int offset = 0; offset < b.length; offset++) {
                i = b[offset];
                if (i < 0) i += 256;
                if (i < 16)
                    buf.append("0");
                buf.append(Integer.toHexString(i));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return buf.toString();
    }

    public static String[] split(String string) {
        String[] list;
        list = string.split(",");
        return list;
    }

    public static String[] split(String string, String Symbol) {
        String[] list;
        list = string.split(Symbol);
        return list;
    }

    public List<T> array2List(T[] string) {
        List<T> list = new ArrayList<>();
        if (string.length != 0) {
            for (int i = 0; i < string.length; i++) {
                list.add(string[i]);
            }
        }

        return list;
    }
}
