package com.leqian.bao;

import com.leqian.bao.model.AppConstants;
import com.qsmaxmin.qsbase.QsApplication;
import com.qsmaxmin.qsbase.common.http.HttpBuilder;

/**
 * Created by fcl on 19.4.10
 * desc:
 */
public class GlobalApplication extends QsApplication {


    @Override
    public boolean isLogOpen() {
        return true;
    }


    @Override
    public void initHttpAdapter(HttpBuilder httpBuilder) {
        httpBuilder.setTerminal("http://www.baidu.com");
        httpBuilder.addHeader("Content-Type", "application/json");
        httpBuilder.addHeader("os", AppConstants.APP_OS);
        httpBuilder.addHeader("bundleId", AppConstants.PACKAGE_NAME);
    }


}
