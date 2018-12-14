package com.sevensevenlife.utils;

import android.content.Context;
import android.content.Intent;
import android.content.pm.PackageInfo;
import android.net.Uri;
import android.provider.Settings;

/**
 * Created by Administrator on 2016/12/26 0026.
 */

public class AppUtils {
    /**
     * 检查是否安装某app
     *
     * @param context
     * @param packageName
     * @return boolean
     */
    public static boolean isInstall(Context context, String packageName) {
        boolean checkResult;
        try {
            PackageInfo packageInfo = context.getPackageManager().getPackageInfo(packageName, 0);
            if (packageInfo == null) {
//				checkResult="未安装";
                checkResult = false;
            } else {
//				checkResult="已经安装";
                checkResult = true;
            }
        } catch (Exception e) {
//			   checkResult="未安装";
            checkResult = false;
        }
        return checkResult;
    }

    public static void RunApp(Context context, String packageName) {
        Intent intent = context.getPackageManager().getLaunchIntentForPackage(packageName);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK | Intent.FLAG_ACTIVITY_RESET_TASK_IF_NEEDED | Intent.FLAG_ACTIVITY_CLEAR_TOP);
        context.startActivity(intent);
    }

    public static void startAppSettings() {
        Intent intent = new Intent(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
        intent.setData(Uri.parse("package:" + MyApplication.getInstance().getPackageName()));
        MyApplication.getInstance().startActivity(intent);
    }
}
