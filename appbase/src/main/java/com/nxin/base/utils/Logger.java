package com.nxin.base.utils;

import android.util.Log;

/**
 * Created by fcl on 18.9.13
 * desc:
 */

public class Logger {


    public static String getTag() {
        return "myb";
    }

    /**
     * 日志长度
     */
    private static int LOG_MAX_LENGTH = 3 * 1024;


    public static void e(String msg) {
        if (ProHelper.getApplication().isPrintLog()) {
            Log.e(getTag(), msg);
        }
    }

    public static void d(String msg) {
        if (ProHelper.getApplication().isPrintLog()) {
            Log.d(getTag(), msg);
        }
    }

    public static void v(String msg) {
        if (ProHelper.getApplication().isPrintLog()) {
            Log.v(getTag(), msg);
        }
    }

    public static void i(String msg) {
        if (!ProHelper.getApplication().isPrintLog()) {
            return;
        }

        int strLength = msg.length();
        int start = 0;
        int end = LOG_MAX_LENGTH;

        // 长度小于等于限制直接打印
        if (strLength <= end) {
            Log.i(getTag(), msg);
            return;
        }

        while (strLength > end) {
            Log.i(getTag(), msg.substring(start, end));
            start = end;
            end = end + LOG_MAX_LENGTH;
        }
        //打印剩余日志
        String substring = msg.substring(start, strLength);
        Log.i(getTag(), substring);
    }
}
