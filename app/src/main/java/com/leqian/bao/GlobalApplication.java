package com.leqian.bao;

import com.leqian.bao.model.AppConstants;
import com.nxin.base.BaseApplication;
import com.nxin.base.model.http.OkHttpUtils;
import com.nxin.base.model.http.log.LoggerInterceptor;
import com.nxin.base.model.http.ssl.HttpsCertificateUtils;

import java.util.List;
import java.util.concurrent.TimeUnit;

import okhttp3.Cookie;
import okhttp3.CookieJar;
import okhttp3.HttpUrl;
import okhttp3.OkHttpClient;

/**
 * Created by fcl on 19.4.10
 * desc:
 */
public class GlobalApplication extends BaseApplication {


    @Override
    public void onCreate() {
        super.onCreate();
        initOkHttp();
    }

    @Override
    public boolean isSaveLog() {
        return false;
    }

    @Override
    public boolean isPrintLog() {
        return true;
    }

    @Override
    public String getAppFlavor() {
        return "myb";
    }

    @Override
    public boolean isOnLine() {
        return false;
    }


    /**
     * 初始化OkHttpClient
     */
    private void initOkHttp() {
        OkHttpClient.Builder httpBuilder = new OkHttpClient().newBuilder()
                .connectTimeout(30, TimeUnit.SECONDS)
                .readTimeout(30, TimeUnit.SECONDS)
                .writeTimeout(30, TimeUnit.SECONDS)
//                .addNetworkInterceptor(new StethoInterceptor())
                .addInterceptor(new LoggerInterceptor(getAppFlavor(), true));
//                .cookieJar(new CookieJar() {
//                    @Override
//                    public void saveFromResponse(HttpUrl url, List<Cookie> cookies) {
////                        MyLogUtil.d("okhttp---saveFromResponse--cookies:" + cookies.size() + ";" + ConstantNetwork.URL_HOST.equals(url.toString()));
//                        if (ConstantNetwork.URL_HOST.equals(url.toString()) && cookies.size() > 0) {
//                            ZntCookieManager.getInstract().setCookieStore(cookies);
//                        }
//                    }
//
//                    @Override
//                    public List<Cookie> loadForRequest(HttpUrl url) {
////                        MyLogUtil.d("okhttp---loadForRequest");
//                        return ZntCookieManager.getInstract().getCookieList();
//                    }
//                });


//        HttpsCertificateUtils.checkSSLCertificate(httpBuilder, getSslCertatication());
        OkHttpClient okHttpClient = httpBuilder.build();

        OkHttpUtils.initClient(okHttpClient);
    }
}
