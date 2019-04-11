package com.nxin.base.web;

import android.annotation.SuppressLint;
import android.content.Intent;
import android.graphics.Bitmap;
import android.net.Uri;
import android.net.http.SslError;
import android.os.Build;
import android.os.Environment;
import android.os.Handler;
import android.os.Message;
import android.os.Parcelable;
import android.provider.MediaStore;
import android.support.annotation.NonNull;
import android.text.TextUtils;
import android.view.View;
import android.webkit.DownloadListener;
import android.webkit.JsResult;
import android.webkit.SslErrorHandler;
import android.webkit.ValueCallback;
import android.webkit.WebChromeClient;
import android.webkit.WebResourceError;
import android.webkit.WebResourceRequest;
import android.webkit.WebView;
import android.webkit.WebViewClient;
import android.widget.FrameLayout;
import android.widget.LinearLayout;
import android.widget.ProgressBar;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.nxin.base.R;
import com.nxin.base.utils.FileUtil;
import com.nxin.base.utils.Logger;
import com.nxin.base.utils.RegexURLUtil;
import com.nxin.base.widget.NXActivity;
import com.nxin.base.view.dialog.MaterialDialogUtil;
import com.nxin.base.view.loading.CommonEmptyView;
import com.nxin.base.web.constant.BaseWebConstant;
import com.nxin.base.web.manager.BaseJSSDKManager;
import com.nxin.base.web.manager.WebViewConfigManager;

import java.io.File;

/**
 * Created by fcl on 18.9.3
 * desc: WebViewActivity基类
 */

public abstract class BaseWebViewActivity extends NXActivity {

    /**
     * View
     */
    protected WebView mWebView;
    public LinearLayout containerView;
    public CommonEmptyView webEmptyView;
    public TextView titleBarTv;
    public ProgressBar progressBar;

    /**
     * field
     */
    protected String pageUrl;
    private String firstLoadUrl = "";
    private int serverConnect = 1;              //0:表示与服务器连接异常；1：正常
    private BaseJSSDKManager jssdkManager;

    private boolean isOnPause = false;

    /**
     * 获取UserAgent
     */
    public abstract String getUserAgent();

    /**
     * 设置Cookie
     */
    protected abstract void setWebViewCookie(WebView mWebView, String pageUrl);

    /**
     * 获取js sdk manager
     */
    protected abstract BaseJSSDKManager getJsSDKManager();

    /**
     * 页面加载完成
     */
    protected void onPageLoadFinish(WebView view, String url) {
    }

    /**
     * 页面长按
     */
    protected void onPageLongClick(String imageUrl) {
    }

    /**
     * 处理handle的消息
     */
    protected void handleChildMessage(Message msg) {
    }

    @SuppressLint("HandlerLeak")
    protected Handler handler = new Handler() {
        @Override
        public void handleMessage(Message msg) {
            super.handleMessage(msg);
            if (msg.what == BaseWebConstant.OPENGJSALERT) {
                MaterialDialogUtil.showConfirm(mContext, (String) msg.obj, R.string.confirm);
            } else {
                handleChildMessage(msg);
            }
        }
    };

    @Override
    public void initView() {
        super.initView();
        getIntentData();
        mWebView = new WebView(getApplicationContext());
        jssdkManager = getJsSDKManager();
    }

    public void getIntentData() {
        Intent webIntent = getIntent();
        pageUrl = webIntent.getStringExtra(BaseWebConstant.WEBVIEW_URL);
        Logger.i(initTag() + "---getIntentData--pageUrl:" + pageUrl);
        if (TextUtils.isEmpty(pageUrl)) {
//            ToastUtil.showToastShort("地址缺失");
            finish();
            return;
        }

    }

    @Override
    public void initViewData() {
        super.initViewData();

        //add webView
        containerView.addView(mWebView, new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));

        //set webView
        WebViewConfigManager.getInstract().setWebViewConfig(mWebView, BaseWebViewActivity.this);
        mWebView.addJavascriptInterface(jssdkManager, "oatongJSBridge");
        WebViewConfigManager.getInstract().setWebViewCookie(mWebView, pageUrl);

        mWebView.setWebViewClient(new NXWebViewClient());
        mWebView.setWebChromeClient(new ChromeClient());
        mWebView.setDownloadListener(new DownloadListener() {
            @Override
            public void onDownloadStart(String url, String userAgent, String contentDisposition, String mimetype, long contentLength) {
                try {
                    Uri uri = Uri.parse(url);
                    Intent intent = new Intent(Intent.ACTION_VIEW, uri);
                    startActivity(intent);
                } catch (Exception e) {
                    e.printStackTrace();
//                    ToastUtil.showToastLong(R.string.toast_no_open);
                }
            }
        });
        mWebView.setOnLongClickListener(new View.OnLongClickListener() {
            @Override
            public boolean onLongClick(View v) {
                WebView.HitTestResult result = ((WebView) v).getHitTestResult();
                if (result == null) {
                    return false;
                }
                int type = result.getType();
                if (type == WebView.HitTestResult.IMAGE_TYPE || type == WebView.HitTestResult.SRC_IMAGE_ANCHOR_TYPE) {
                    String imageUrl = result.getExtra();
                    if (!TextUtils.isEmpty(imageUrl)) {
                        onPageLongClick(imageUrl);
                    }
                }
                return false;
            }
        });

        //load url
        mWebView.loadUrl(pageUrl);
    }

    /**
     * 解决视频返回播放问题
     */
    @Override
    public void onPause() {
        super.onPause();
        if (mWebView != null) {
            mWebView.onPause();
        }
        isOnPause = true;
    }

    @Override
    protected void onResume() {
        super.onResume();
        if (isOnPause) {
            if (mWebView != null) {
                mWebView.onResume();
            }
            isOnPause = false;
        }
    }

    @Override
    protected void onDestroy() {
        super.onDestroy();
        destroyWebView();
    }

    private void destroyWebView() {
        synchronized (this) {
            if (containerView != null) {
                containerView.removeView(mWebView);
            }
            if (mWebView != null) {
                mWebView.stopLoading();
                mWebView.clearHistory();
                mWebView.removeAllViews();
                mWebView = null;
            }
        }
    }

    /**
     * webView回调h5
     */
    public void reLoadUrl(String fun) {
        if (mWebView != null && !TextUtils.isEmpty(fun)) {
            final String sweburl = fun;
            mWebView.post(new Runnable() {

                @Override
                public void run() {
                    synchronized (this) {
                        if (null != mWebView && sweburl != null) {
                            mWebView.loadUrl(sweburl);
                        }
                    }
                }
            });
        }
    }

    @SuppressLint("NewApi")
    class NXWebViewClient extends WebViewClient {
        @Override
        public boolean shouldOverrideUrlLoading(WebView view, WebResourceRequest request) {
            String url = request.getUrl().toString();
            Logger.i(initTag() + "---shouldOverrideUrlLoading--" + url);
            if (url.toLowerCase().startsWith("http") || url.toLowerCase().startsWith("https")) {
                //兼容8.0以上 点击a标签两次跳转不一致（eg：猪场管理/行情宝）
                if (TextUtils.isEmpty(firstLoadUrl)) {
                    firstLoadUrl = url;
                } else {
                    if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.O && firstLoadUrl.equals(url)) {
                        Logger.e(initTag() + "  do not load again  ");
                        return false;
                    }
                }
                pageUrl = url;
                setWebViewCookie(mWebView, pageUrl);
                view.loadUrl(pageUrl);
                return true;
            }
            return false;
        }

        @Override
        public void onPageStarted(WebView view, String url, Bitmap favicon) {
            super.onPageStarted(view, url, favicon);
            Logger.i(initTag() + "--onPageStarted--" + url);
            webEmptyView.dealRequestData();
        }

        @Override
        public void onPageFinished(WebView view, String url) {
            super.onPageFinished(view, url);
            Logger.i(initTag() + "--onPageFinished--" + url);
            synchronized (this) {
                view.getSettings().setBlockNetworkImage(false);
                //设置标题
                String title = view.getTitle();
                if (!TextUtils.isEmpty(title)) {
                    String host = RegexURLUtil.getHostPrefix(title);
                    if (!url.contains(host)) {
                        if (titleBarTv != null) {
                            titleBarTv.setText(title);
                        }
                    }
                }
                pageUrl = url;
                onPageLoadFinish(view, url);
            }
            switchView();
            firstLoadUrl = "";
        }

        @Override
        public void onReceivedError(WebView view, WebResourceRequest request, WebResourceError error) {
            super.onReceivedError(view, request, error);
            Logger.i(initTag() + "--onReceivedError--" + error);
            if (request.isForMainFrame()) {
                //无法与服务器正常连接,断网或者网络连接超时
                serverConnect = 0;
                switchView();
            }
        }

        @Override
        public void onReceivedSslError(WebView view, SslErrorHandler handler, SslError error) {
            handler.proceed();
            Logger.i(initTag() + "--onReceivedSslError--" + error);
        }
    }


    /**
     * 切换视图
     */
    public void switchView() {
        if (serverConnect == 0) {
            containerView.setVisibility(View.GONE);
            webEmptyView.setVisibility(View.VISIBLE);
            webEmptyView.dealRequestDataFail();
        } else {
            containerView.setVisibility(View.VISIBLE);
            webEmptyView.setVisibility(View.GONE);
        }
    }

    /**
     * 打开文件相关变量
     */
    public static int FILECHOOSER_RESULTCODE_FOR_ANDROID_5 = 2;
    private static final int FILECHOOSER_RESULTCODE = 1;
    private ValueCallback mUploadMessage;
    private Uri mCapturedImageURI;
    public ValueCallback<Uri[]> mUploadMessageForAndroid5;

    class ChromeClient extends WebChromeClient {

        @Override
        public boolean onJsAlert(WebView view, String url, String message, final JsResult result) {
            if (!isFinishing()) {
                handler.obtainMessage(BaseWebConstant.OPENGJSALERT, message).sendToTarget();
                result.confirm();
            }
            return true;
        }

        @Override
        public boolean onJsConfirm(WebView arg0, String arg1, String arg2, final JsResult arg3) {
            if (!isFinishing()) {
                MaterialDialog dialog = MaterialDialogUtil.showAlert(BaseWebViewActivity.this, arg2, R.string.confirm, R.string.cancel,
                        new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                arg3.confirm();
                            }
                        }, new MaterialDialog.SingleButtonCallback() {
                            @Override
                            public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                arg3.cancel();
                            }
                        });
                dialog.setCancelable(false);
            }
            return true;
        }

        @Override
        public void onProgressChanged(WebView view, int newProgress) {
            Logger.i(initTag() + "---onProgressChanged--newProgress:" + newProgress);
            if (newProgress == 100) {
                switchView();
                progressBar.setVisibility(View.GONE);
            } else {
                progressBar.setVisibility(View.VISIBLE);
                progressBar.setProgress(newProgress);
            }
            super.onProgressChanged(view, newProgress);
        }

        @Override
        public View getVideoLoadingProgressView() {
            FrameLayout frameLayout = new FrameLayout(BaseWebViewActivity.this);
            frameLayout.setLayoutParams(new FrameLayout.LayoutParams(FrameLayout.LayoutParams.MATCH_PARENT, FrameLayout.LayoutParams.MATCH_PARENT));
            return frameLayout;
        }

        //For Android 5.0
        @Override
        public boolean onShowFileChooser(WebView view, ValueCallback<Uri[]> filePath, FileChooserParams fileChooserParams) {
            openFileChooserImplForAndroid5(filePath);
            return true;
        }

        //openFileChooser for Android 3.0+
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType) {
            mUploadMessage = uploadMsg;
            //Create AndroidExampleFolder at sdcard
            File imageStorageDir = new File(Environment.getExternalStoragePublicDirectory(Environment.DIRECTORY_PICTURES), "AndroidExampleFolder");
            if (!imageStorageDir.exists()) {
                imageStorageDir.mkdirs();
            }
            //Create camera captured image file path and name
            File file = new File(imageStorageDir + File.separator + "_" + String.valueOf(System.currentTimeMillis()) + "." + FileUtil.getExtensionName(imageStorageDir.getName()));
            mCapturedImageURI = Uri.fromFile(file);
            //Camera capture image intent
            final Intent captureIntent = new Intent(android.provider.MediaStore.ACTION_IMAGE_CAPTURE);
            captureIntent.putExtra(MediaStore.EXTRA_OUTPUT, mCapturedImageURI);
            Intent i = new Intent(Intent.ACTION_GET_CONTENT);
            i.setType("*/*");
            i.addCategory(Intent.CATEGORY_OPENABLE);
            Intent chooserIntent = Intent.createChooser(i, getString(R.string.webview_file_browser_title));
            chooserIntent.putExtra(Intent.EXTRA_INITIAL_INTENTS, new Parcelable[]{captureIntent});
            startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE);
        }

        //openFileChooser for Android < 3.0
        public void openFileChooser(ValueCallback<Uri> uploadMsg) {
            openFileChooser(uploadMsg, "");
        }

        //openFileChooser for other Android versions
        public void openFileChooser(ValueCallback<Uri> uploadMsg, String acceptType, String capture) {
            openFileChooser(uploadMsg, acceptType);
        }

        /**
         * Android5.0+
         **/
        private void openFileChooserImplForAndroid5(ValueCallback<Uri[]> uploadMsg) {
            mUploadMessageForAndroid5 = uploadMsg;
            Intent contentSelectionIntent = new Intent(Intent.ACTION_GET_CONTENT);
            contentSelectionIntent.addCategory(Intent.CATEGORY_OPENABLE);
            contentSelectionIntent.setType("*/*");

            Intent chooserIntent = new Intent(Intent.ACTION_CHOOSER);
            chooserIntent.putExtra(Intent.EXTRA_INTENT, contentSelectionIntent);
            chooserIntent.putExtra(Intent.EXTRA_TITLE, getString(R.string.webview_file_browser_title));

            startActivityForResult(chooserIntent, FILECHOOSER_RESULTCODE_FOR_ANDROID_5);
        }
    }
}
