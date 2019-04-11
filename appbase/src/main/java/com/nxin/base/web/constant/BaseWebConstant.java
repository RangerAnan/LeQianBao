package com.nxin.base.web.constant;


import com.nxin.base.model.BaseConstant;

/**
 * Created by fcl on 18.9.19
 * desc:
 */

public class BaseWebConstant extends BaseConstant {


    /**
     * webView intent-key
     */
    public static final String WEBVIEW_URL = "WEBVIEW_URL";

    /**
     * 本地demo路径
     */
    public static final String JSSDK_DEMO_PATH = "file:///android_asset/jssdk/demo.html";


    /**
     * js 回调状态
     */
    public static final int ERROR_LOGIN_USER_INFO = -100;


    /**
     * handler what
     */
    public static final int OPENGJSALERT = 100;
    public static final int JS_LOGIN_ERROR = 101;
    public static final int JS_CALLBACK_ERROR = 102;
    public static final int JS_CLOSE_WIN = 103;

}
