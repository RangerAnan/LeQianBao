package com.nxin.base;

import android.support.multidex.MultiDex;
import android.support.multidex.MultiDexApplication;

import com.nxin.base.utils.ProHelper;

/**
 * Created by fcl on 18.4.23
 * desc:
 */

public abstract class BaseApplication extends MultiDexApplication {

    /**
     * 是否打印日志
     */
    public boolean isPrintLog;

    @Override
    public void onCreate() {
        super.onCreate();
        ProHelper.getInstance().init(this);
        MultiDex.install(this);
    }

    /**
     * 是否保存日志
     */
    public abstract boolean isSaveLog();

    /**
     * 是否打印日志
     */
    public abstract boolean isPrintLog();

    /**
     * 获取app下gradle配置的flavor
     */
    public abstract String getAppFlavor();

    /**
     * 是否为线上环境
     *
     * @return
     */
    public abstract boolean isOnLine();
}
