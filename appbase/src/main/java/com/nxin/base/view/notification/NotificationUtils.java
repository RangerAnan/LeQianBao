package com.nxin.base.view.notification;

import android.app.AppOpsManager;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.net.Uri;
import android.os.Build;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by fcl on 18.12.13
 * desc: 通知栏工具类
 */

public class NotificationUtils {

    private static final String CHECK_OP_NO_THROW = "checkOpNoThrow";
    private static final String OP_POST_NOTIFICATION = "OP_POST_NOTIFICATION";

    /**
     * 检查通知栏权限是否可用，19以下默认可用
     *
     * @return
     */
    public static boolean isNotificationEnabled(Context context) {

        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O) {
            //8.0手机以上
            if (((NotificationManager) context.getSystemService(Context.NOTIFICATION_SERVICE)).getImportance() == NotificationManager.IMPORTANCE_NONE) {
                return false;
            }
        }

        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.KITKAT) {
            return true;
        }

        AppOpsManager mAppOps = (AppOpsManager) context.getSystemService(Context.APP_OPS_SERVICE);

        ApplicationInfo appInfo = context.getApplicationInfo();
        String pkg = context.getApplicationContext().getPackageName();
        int uid = appInfo.uid;
        Class appOpsClass = null;

        /* Context.APP_OPS_MANAGER */
        try {

            //api >= 19 ==> os 4.4
            appOpsClass = Class.forName(AppOpsManager.class.getName());

            Method checkOpNoThrowMethod = appOpsClass.getMethod(CHECK_OP_NO_THROW, Integer.TYPE, Integer.TYPE, String.class);

            Field opPostNotificationValue = appOpsClass.getDeclaredField(OP_POST_NOTIFICATION);
            int value = (Integer) opPostNotificationValue.get(Integer.class);

            return ((Integer) checkOpNoThrowMethod.invoke(mAppOps, value, uid, pkg) == AppOpsManager.MODE_ALLOWED);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }


    /**
     * 跳转应用设置页面
     */
    public static void enterNotificationSetting(Context context) {
        Intent localIntent = new Intent();

//        if (android.os.Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
//            //应用设置页面
//            localIntent.setAction("android.settings.APP_NOTIFICATION_SETTINGS");
//            localIntent.putExtra("app_package", context.getPackageName());
//            localIntent.putExtra("app_uid", context.getApplicationInfo().uid);
//
//        } else if (android.os.Build.VERSION.SDK_INT == Build.VERSION_CODES.KITKAT) {
//            localIntent.setAction(Settings.ACTION_APPLICATION_DETAILS_SETTINGS);
//            localIntent.addCategory(Intent.CATEGORY_DEFAULT);
//            localIntent.setData(Uri.parse("package:" + context.getPackageName()));
//
//        } else {
        //4.4以下没有从app跳转到应用通知设置页面的Action，可考虑跳转到应用详情页面
        localIntent.addFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.GINGERBREAD) {
            localIntent.setAction("android.settings.APPLICATION_DETAILS_SETTINGS");
            localIntent.setData(Uri.fromParts("package", context.getPackageName(), null));
        }
//        }
        context.startActivity(localIntent);
    }


}
