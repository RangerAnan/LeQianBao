package com.leqian.bao.common.http;

import com.leqian.bao.model.BaseHttp;
import com.leqian.bao.model.BaseModelReq;
import com.leqian.bao.model.account.LoginReq;
import com.nxin.base.model.http.callback.ModelCallBack;

/**
 * Created by fcl on 19.4.18
 * desc:
 */
public class ResourceHttp extends BaseHttp {

    public static void getLink(ModelCallBack callBack) {
        BaseModelReq baseModelReq = new BaseModelReq();
        baseModelReq.method = "getLink";
        executePostHttp(baseModelReq, callBack);

    }
}
