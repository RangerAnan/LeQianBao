package com.leqian.bao;

import android.content.Context;

import com.leqian.bao.common.util.FileUtil;
import com.leqian.bao.model.AppConstants;
import com.nxin.base.BaseApplication;
import com.nxin.base.model.http.OkHttpUtils;
import com.nxin.base.model.http.log.LoggerInterceptor;
import com.nxin.base.model.http.ssl.HttpsCertificateUtils;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.scwang.smartrefresh.layout.header.ClassicsHeader;

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
        FileUtil.createProjectSdcardFile();

    }


    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.colorPrimary, android.R.color.white);//全局设置主题颜色
                return new ClassicsHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));//指定为经典Header，默认是 贝塞尔雷达Header
            }
        });
        //设置全局的Footer构建器
        SmartRefreshLayout.setDefaultRefreshFooterCreator(new DefaultRefreshFooterCreator() {
            @Override
            public RefreshFooter createRefreshFooter(Context context, RefreshLayout layout) {
                //指定为经典Footer，默认是 BallPulseFooter
                return new ClassicsFooter(context).setDrawableSize(20);
            }
        });
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
