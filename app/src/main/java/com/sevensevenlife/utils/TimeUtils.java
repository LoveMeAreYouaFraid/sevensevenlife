package com.sevensevenlife.utils;

import android.annotation.SuppressLint;
import android.os.CountDownTimer;
import android.util.Log;
import android.widget.Button;

import java.sql.Date;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Locale;

/**
 * Created by Administrator on 2016/12/26 0026.
 */

public class TimeUtils {

    @SuppressLint("SimpleDateFormat")
    public static String getHourAndMin(long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH:mm");
        return format.format(new Date(time));
    }

    @SuppressLint("SimpleDateFormat")
    public static String getMinAndSecond(long time) {
        SimpleDateFormat format = new SimpleDateFormat("mm:ss");
        return format.format(new Date(time));
    }

    @SuppressLint("SimpleDateFormat")
    public static String getYearAndMonth(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM");
        return format.format(new Date(time));
    }

    @SuppressLint("SimpleDateFormat")
    public static String getYearMonthAndDay(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
        return format.format(new Date(time));
    }

    @SuppressLint("SimpleDateFormat")
    public static String getMonthAndDay(long time) {
        SimpleDateFormat format = new SimpleDateFormat("MM-dd");
        return format.format(new Date(time));
    }

    @SuppressLint("SimpleDateFormat")
    public static String getDateFormat(long time) {
        String timeString = getYearMonthAndDay(time);
        return timeString.replaceAll("-", ".");
    }

    @SuppressLint("SimpleDateFormat")
    public static String getYearMonthAndDayWithHour(long time) {
        SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(new Date(time));
    }


    @SuppressLint("SimpleDateFormat")
    public static String getWith(long time) {
        SimpleDateFormat format = new SimpleDateFormat("HH");
        return format.format(new Date(time));
    }

    public static String convertDateTime2DateStr(Date date) {
        SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss", Locale.CHINA);
        return df.format(date);
    }

    public static String yyyymmddhhmmss() {
        long startT = System.currentTimeMillis();
        long ss = (startT) / (1000) + 28800; //共计秒数
        return ss + "";
    }

    public static boolean startTimUtll(Long start) {
        java.util.Date dt = new java.util.Date();//获取当前时间
        Long time = dt.getTime();
        final int liangtian = 172800000;
        if (time + liangtian > start) {
            return false;
        } else {
            return true;
        }
    }

    public static long fromDateStringToLong(String inVal) { //此方法计算时间毫秒

        Calendar c = Calendar.getInstance();

        try {
            c.setTime(new SimpleDateFormat("yyyyMMddHHmmss").parse(inVal));
        } catch (ParseException e) {
            e.printStackTrace();
            Log.e("ParseException", e.getMessage());
        }
        return c.getTimeInMillis();   //返回毫秒数
    }

    /**
     * 传入毫秒数 返回HH:mm:ss格式的字符串
     *
     * @param time
     */
    public static String castLastDate(long time) {

        long duration = System.currentTimeMillis() - time;

        if (duration < 2592000000l) {
            long tempweek = duration / 604800000l;
            if (tempweek > 0) {
                return tempweek + "周前";
            }
            long tempday = duration / 86400000l;
            if (tempday > 0) {
                return tempday + "天前";
            }
            long temphour = duration / 3600000l;
            if (temphour > 0) {
                return temphour + "小时前";
            }
            long tempminutes = duration / 60000l;
            if (tempminutes > 0) {
                return tempminutes + "分钟前";
            }
            long tempseconds = duration / 1000l;
            if (tempseconds > 0) {
                return tempseconds + "秒前";
            }
        } else {
            return getYearMonthAndDayWithHour(time);
        }
        return "";
    }

    /**
     * 返回活动日期格式
     */
    public static String getDateFormat(Long start, Long end) {
        String dateFormat = getYearMonthAndDay(start)
                + " ~ " + getMonthAndDay(end);

        return dateFormat;
    }

    /**
     * 返回活动时间格式
     * 如果时间一样  则只返回一个
     */
    public static String getTimeFormat(Long start, Long end) {

        String startTime = getHourAndMin(start);

        String endTime = getHourAndMin(end);

        if (startTime.equals(endTime)) {
            return startTime;
        }

        return startTime + " ~ " + endTime;
    }

    public static void invertedTime(final long totalTime, long interval, final Button timeButton) {

        new CountDownTimer(totalTime, interval) {
            @Override
            public void onTick(long millisUntilFinished) {
                timeButton.setText((millisUntilFinished / 1000) + "秒");
            }

            @Override
            public void onFinish() {
                timeButton.setText("发送");
            }
        }.start();

    }

}