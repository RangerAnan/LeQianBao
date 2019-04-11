package com.nxin.base.common.system;

import android.app.Notification;
import android.net.Uri;
import android.os.Build;
import android.os.Bundle;

import com.nxin.base.utils.Logger;
import com.nxin.base.utils.ProHelper;

import java.lang.reflect.Field;
import java.lang.reflect.Method;

/**
 * Created by fcl on 18.11.8
 * desc:设置桌面图标未读消息提醒
 */

public class AppBadgeUtil {

    /**
     * 设置未读消息的入口
     *
     * @param num
     */
    public static void setAppBadge(int num) {
        Logger.i("AppBadgeUtil--setAppBadge--num:" + num + ";" + Build.MANUFACTURER);
        if (num < 0) {
            num = 0;
        } else if (num > 99) {
            num = 99;
        }

        if (Build.MANUFACTURER.equalsIgnoreCase("HUAWEI")) {
            setHWPhoneBadge(num);
        }

    }


    /**
     * 设置小米手机 未读消息提醒
     * <p>
     * 1.支持版本：MIUI 6 - MIUI 10
     * 2.当应用向通知栏发送了一条通知 （除了进度条样式和常驻通知外），应用图标的右上角就会显示「1」。
     * 值得一提，角标的数字代表应用的通知数，即应用发送了「x」条通知，角标就会显示为「x」
     */
    public static void setMIPhoneBadge(Notification notification, int mCount) {
        try {

          /*  @SuppressLint("PrivateApi")
            Class miuiNotificationClass = Class.forName("android.app.MiuiNotification");
            Object miuiNotification = miuiNotificationClass.newInstance();
            Field field = miuiNotification.getClass().getDeclaredField("messageCount");
            field.setAccessible(true);
            field.set(miuiNotification, mCount);*/

            Field field = notification.getClass().getDeclaredField("extraNotification");
            Object extraNotification = field.get(notification);
            Method method = extraNotification.getClass().getDeclaredMethod("setMessageCount", new Class[]{Integer.TYPE});
            method.invoke(extraNotification, new Object[]{Integer.valueOf(mCount)});
        } catch (Exception e) {
            e.printStackTrace();
        }
    }


    /**
     * 设置华为手机 未读消息提醒
     * <p>
     * 1.系统版本：EMUI4.1 及以上
     */
    private static void setHWPhoneBadge(int num) {
        if (Build.VERSION.SDK_INT < Build.VERSION_CODES.M) {
            return;
        }
        try {
            String pName = ProHelper.getApplication().getPackageName();
            Bundle bunlde = new Bundle();
            bunlde.putString("package", pName + ""); // 包名
            bunlde.putString("class", "com.dbn.OAConnect.ui.SplashActivity"); //类名

            //badgenumber为0时，不显示角标；badgenumber大于0时，显示角标
            bunlde.putInt("badgenumber", num);
            ProHelper.getApplication().getContentResolver().call(Uri.parse("content://com.huawei.android.launcher.settings/badge/"), "change_badge", null, bunlde);
        } catch (Exception e) {
            e.printStackTrace();
        }
    }

}
