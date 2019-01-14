package com.sevensevenlife.utils;

public class DPIUtil {

    public static int dip2px(float dipValue) {
        final float scale = MyApplication.getInstance().getResources().getDisplayMetrics().densityDpi;
        return (int) (dipValue * (scale / 160) + 0.5f);
    }

}
