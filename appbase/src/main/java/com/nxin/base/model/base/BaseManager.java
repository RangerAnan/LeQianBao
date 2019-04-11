package com.nxin.base.model.base;

import android.content.Context;

import com.nxin.base.utils.ProHelper;

/**
 * Created by fcl on 2017/6/8.
 * description:manager基类
 */

public abstract class BaseManager {

    /**
     * context
     */
    protected Context mContext = ProHelper.getApplication();

    /**
     * 获取类名
     */
    public String initTag() {
        return this.getClass().getSimpleName();
    }
}
