package com.leqian.bao;

import com.qsmaxmin.qsbase.QsApplication;
import com.qsmaxmin.qsbase.common.http.HttpBuilder;

/**
 * Created by fcl on 19.4.10
 * desc:
 */
public class GlobalApplication extends QsApplication {


    @Override
    public boolean isLogOpen() {
        return false;
    }

    @Override
    public void initHttpAdapter(HttpBuilder httpBuilder) {

    }
}
