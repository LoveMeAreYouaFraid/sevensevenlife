package com.sevensevenlife.utils;

import android.app.Notification;
import android.app.NotificationManager;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.os.Bundle;
import android.support.v4.app.NotificationCompat;
import android.text.TextUtils;

import com.sevensevenlife.model.KEY;
import com.sevensevenlife.view.custumview.PushWebActivity;
import com.sevensevenlife.view.Home.AppMsg.AppMsgActivity;
import com.example.youxiangshenghuo.R;

import org.json.JSONException;
import org.json.JSONObject;

import cn.jpush.android.api.JPushInterface;

import static android.content.Context.NOTIFICATION_SERVICE;

/**
 * Created by Administrator on 2017/1/11 0011.
 */

public class NotificationUtils {
    public static void show(Bundle txt, Context context) {

        String msg;
        Intent intent = null;
        if (!TextUtils.isEmpty(txt.getString(JPushInterface.EXTRA_MESSAGE))) {
            intent = new Intent(context, AppMsgActivity.class);
            msg = txt.getString(JPushInterface.EXTRA_MESSAGE);
            LogUtils.e("123+JPushInterface.msg", msg);
        } else {
            msg = "";
        }
        if (!TextUtils.isEmpty(txt.getString(JPushInterface.EXTRA_EXTRA))) {
            intent = new Intent(context, PushWebActivity.class);
            try {
                JSONObject json = new JSONObject(txt.getString(JPushInterface.EXTRA_EXTRA));
                String urissss = json.getString("url");
                intent.putExtra(KEY.PushURL, urissss);
                MemoryBean.push_Uri = urissss;
                LogUtils.e("123+JPushInterface.EXTRA_EXTRA", urissss);
            } catch (JSONException e) {
                e.printStackTrace();
            }

        }

        NotificationManager mNotificationManager = (NotificationManager)
                context.getSystemService(NOTIFICATION_SERVICE);
        NotificationCompat.Builder mBuilder = new NotificationCompat.Builder(context);
        PendingIntent pendingIntent = PendingIntent.getActivity(context, 0,
                intent, 0);
        mBuilder.setSmallIcon(R.drawable.icon_03)
                .setContentTitle("优享七七生活")
                .setContentText(msg)
                .setContentIntent(pendingIntent);
        Notification mNotification = mBuilder.build();
        mNotification.icon = R.drawable.icon_03;
        mNotification.flags = Notification.FLAG_AUTO_CANCEL;//FLAG_ONGOING_EVENT 在顶部常驻，可以调用下面的清除方法去除  FLAG_AUTO_CANCEL  点击和清理可以去调
        mNotification.defaults = Notification.DEFAULT_VIBRATE;
        mNotification.tickerText = "优享七七生活";
        mNotification.when = System.currentTimeMillis();
        mNotificationManager.notify(100, mNotification);

    }
}
