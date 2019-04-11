package com.nxin.base.web.manager;

import android.annotation.SuppressLint;
import android.os.Build;
import android.webkit.WebSettings;
import android.webkit.WebView;

import com.nxin.base.model.base.BaseManager;
import com.nxin.base.utils.Logger;
import com.nxin.base.utils.NetworkManager;
import com.nxin.base.web.BaseWebViewActivity;

/**
 * webView 配置类
 */
@SuppressLint("SetJavaScriptEnabled")
public class WebViewConfigManager extends BaseManager {

    private static WebViewConfigManager instract;

    public static WebViewConfigManager getInstract() {
        instract = new WebViewConfigManager();
        return instract;
    }

    public void setWebViewConfig(final WebView webview, BaseWebViewActivity mContext) {
        webview.getSettings().setJavaScriptEnabled(true);
        webview.getSettings().setBuiltInZoomControls(true);
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.LOLLIPOP) {
            webview.getSettings().setMixedContentMode(WebSettings.MIXED_CONTENT_ALWAYS_ALLOW);
        }
        if (Integer.parseInt(Build.VERSION.SDK) >= 14) {
            webview.getSettings().setDisplayZoomControls(false);
        }
        webview.getSettings().setUserAgentString(mContext.getUserAgent() + "/" + webview.getSettings().getUserAgentString());
        webview.getSettings().setSupportZoom(true);
        webview.getSettings().setRenderPriority(WebSettings.RenderPriority.HIGH);
        webview.getSettings().setBlockNetworkImage(true);
        //开启存储
        webview.getSettings().setDomStorageEnabled(true);
        webview.getSettings().setAppCacheMaxSize(1024 * 1024 * 8);
        //设置缓冲路径
        String appCachePath = mContext.getCacheDir().getAbsolutePath();
        webview.getSettings().setAppCachePath(appCachePath);
        //开启文件数据缓存
        webview.getSettings().setAllowFileAccess(false);
        //开启APP缓存
        webview.getSettings().setAppCacheEnabled(true);

        //根据网络状态加载缓冲，有网：走默认设置；无网络：走加载缓冲
        if (NetworkManager.getInstance().isNetworkAvailable()) {
            webview.getSettings().setCacheMode(WebSettings.LOAD_DEFAULT);
        } else {
            webview.getSettings().setCacheMode(WebSettings.LOAD_CACHE_ELSE_NETWORK);
        }
        //支持显示PC宽屏页面的全部内容
        webview.getSettings().setUseWideViewPort(true);
        webview.getSettings().setLoadWithOverviewMode(true);
        Logger.i(initTag() + "--userAgent------" + webview.getSettings().getUserAgentString());
    }

    public void setWebViewCookie(final WebView webview, final String url_load) {
        //TODO 设置cookie

    }
}