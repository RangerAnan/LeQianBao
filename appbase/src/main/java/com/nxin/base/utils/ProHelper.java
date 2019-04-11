package com.nxin.base.utils;

import android.content.res.AssetManager;
import android.support.v4.content.LocalBroadcastManager;

import com.nxin.base.BaseApplication;
import com.nxin.base.common.ScreenManager;

import java.io.File;

/**
 * Created by fcl on 18.4.26
 * desc:帮助类中心
 */

public class ProHelper {

    private static ProHelper proHelper = new ProHelper();

    private volatile static BaseApplication mApplication;

    /**
     * 获取单例
     */
    public static ProHelper getInstance() {
        return proHelper;
    }

    /**
     * 初始化
     */
    public void init(BaseApplication application) {
        mApplication = application;
    }

    /**
     * 获取Application
     */
    public static BaseApplication getApplication() {
        return mApplication;
    }

    /**
     * 获取页面管理器
     */
    public static ScreenManager getScreenHelper() {
        return ScreenManager.getInstance();
    }

    /**
     * 获取本地广播管理器
     */
    public static LocalBroadcastManager getLocalBroadcastManager() {
        return LocalBroadcastManager.getInstance(mApplication);
    }

    /**
     * 获取Assets资源
     */
    public static AssetManager getAssets() {
        return getApplication().getAssets();
    }

    /**
     * 获取/data/data/<application package>/files目录
     */
    public static File getFilesDir() {
        return getApplication().getFilesDir();
    }

    /**
     * 从资源文件中获取字符串
     */
    public static String getString(int strRes) {
        return mApplication.getString(strRes);
    }
}
