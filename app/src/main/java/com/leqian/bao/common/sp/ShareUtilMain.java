package com.leqian.bao.common.sp;

import android.content.Context;
import android.content.SharedPreferences;

import com.leqian.bao.GlobalApplication;
import com.qsmaxmin.qsbase.common.utils.QsHelper;

/**
 * Created by fcl on 19.4.10
 * desc:
 */
public class ShareUtilMain {

    private static SharedPreferences preferences;

    private static final String SHARE_FILE_NAME = "sp_main";

    /**
     * 登录状态
     */
    public static final String LOGIN_STATE = "m1";


    static {
        preferences = QsHelper.getInstance().getApplication().getSharedPreferences(SHARE_FILE_NAME, Context.MODE_PRIVATE);
    }

    /**
     * @param key 键值
     * @param def 缺省值
     * @return
     */
    public static String getString(String key, String def) {
        return preferences.getString(key, def);
    }

    /**
     * @param key
     * @param value
     */
    public static void setString(String key, String value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putString(key, value).commit();
    }

    /**
     * @param key 键值
     * @param def 缺省值
     * @return
     */
    public static int getInt(String key, int def) {
        return preferences.getInt(key, def);
    }

    /**
     * @param key
     * @param value
     */
    public static void setInt(String key, int value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putInt(key, value).commit();
    }

    /**
     * @param key 键值
     * @param def 缺省值
     * @return
     */
    public static long getLong(String key, long def) {
        return preferences.getLong(key, def);
    }

    /**
     * @param key
     * @param value
     */
    public static void setLong(String key, long value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putLong(key, value).commit();
    }

    /**
     * @param key 键值
     * @param def 缺省值
     * @return
     */
    public static Boolean getBoolean(String key, Boolean def) {
        return preferences.getBoolean(key, def);
    }

    /**
     * @param key
     * @param value
     */
    public static void setBoolean(String key, Boolean value) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.putBoolean(key, value).commit();
    }

    /**
     * @param key 键值
     */
    public static void remove(String key) {
        SharedPreferences.Editor editor = preferences.edit();
        editor.remove(key);
        editor.commit();
    }

}
