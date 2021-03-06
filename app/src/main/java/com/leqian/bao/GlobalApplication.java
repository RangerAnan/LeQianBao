package com.leqian.bao;

import android.content.Context;

import com.leqian.bao.common.util.DeviceUtil;
import com.leqian.bao.common.util.FileUtil;
import com.leqian.bao.model.constant.SDKConstant;
import com.leqian.bao.view.refresh.MybBezierCircleHeader;
import com.nxin.base.BaseApplication;
import com.nxin.base.model.http.OkHttpUtils;
import com.nxin.base.model.http.log.LoggerInterceptor;
import com.nxin.base.utils.ChannelUtil;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;
import com.scwang.smartrefresh.layout.api.DefaultRefreshFooterCreator;
import com.scwang.smartrefresh.layout.api.DefaultRefreshHeaderCreator;
import com.scwang.smartrefresh.layout.api.RefreshFooter;
import com.scwang.smartrefresh.layout.api.RefreshHeader;
import com.scwang.smartrefresh.layout.api.RefreshLayout;
import com.scwang.smartrefresh.layout.footer.ClassicsFooter;
import com.umeng.analytics.MobclickAgent;
import com.umeng.commonsdk.UMConfigure;
import com.umeng.socialize.PlatformConfig;

import java.util.concurrent.TimeUnit;

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

        initUmeng();
    }

    private void initUmeng() {
        UMConfigure.init(this, null, ChannelUtil.getChannel(this), UMConfigure.DEVICE_TYPE_PHONE, null);
        MobclickAgent.setScenarioType(this, MobclickAgent.EScenarioType.E_UM_NORMAL);

        if (!DeviceUtil.isApkDebugable()) {
            if (isPrintLog) {
                UMConfigure.setLogEnabled(true);
            } else {
                UMConfigure.setLogEnabled(false);
            }
        }

        MobclickAgent.openActivityDurationTrack(false);
        PlatformConfig.setWeixin(SDKConstant.APP_KEY_WX, SDKConstant.APP_SECRET_WX);
    }


    static {
        //设置全局的Header构建器
        SmartRefreshLayout.setDefaultRefreshHeaderCreator(new DefaultRefreshHeaderCreator() {
            @Override
            public RefreshHeader createRefreshHeader(Context context, RefreshLayout layout) {
                layout.setPrimaryColorsId(R.color.white, R.color.theme);//全局设置主题颜色
                return new MybBezierCircleHeader(context);//.setTimeFormat(new DynamicTimeFormat("更新于 %s"));
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
