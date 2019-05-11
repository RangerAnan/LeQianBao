package com.leqian.bao.common.http;

import com.leqian.bao.model.network.account.LoginReq;
import com.leqian.bao.model.network.appinfo.CheckAppVersionReq;
import com.nxin.base.model.http.callback.ModelCallBack;

/**
 * Created by fcl on 19.5.11
 * desc:
 */
public class AppInfoHttp extends BaseHttp {

    public static void checkAppVersion(String version, ModelCallBack callBack) {
        CheckAppVersionReq loginRep = new CheckAppVersionReq();
        loginRep.versionName = version;
        loginRep.method = "checkAppVersion";
        executePostHttp(loginRep, callBack);
    }
}
