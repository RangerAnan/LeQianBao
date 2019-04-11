package com.nxin.base.web.jsimpl;

import android.content.Intent;
import android.text.TextUtils;

import com.nxin.base.model.base.BaseManager;
import com.nxin.base.utils.Logger;

/**
 * Created by fcl on 19.3.11
 * desc: jssdk 常见方法实现类
 */

public abstract class JSSDKCommonImpl extends BaseManager implements JSSDKInterface {

    @Override
    public boolean onNewIntent(Intent intent) {
        Logger.i(initTag() + "--onNewIntent--");
        return false;
    }

    @Override
    public boolean onResume() {
        Logger.i(initTag() + "--onResume--");
        return false;
    }

    @Override
    public boolean onDestroy() {
        Logger.i(initTag() + "--onDestroy--");
        return false;
    }

    /**
     * 返回值为true，处理后WebView中该方法不再处理此回调
     */
    @Override
    public boolean onActivityResult(int requestCode, int resultCode, Intent data) {
        Logger.i(initTag() + "--onActivityResult--requestCode:" + requestCode + ";resultCode:" + resultCode);
        return false;
    }

    @Override
    public boolean onBackPressed() {
        Logger.i(initTag() + "--onBackPressed--");
        return false;
    }

    @Override
    public boolean onPause() {
        Logger.i(initTag() + "--onPause--");
        return false;
    }

}
