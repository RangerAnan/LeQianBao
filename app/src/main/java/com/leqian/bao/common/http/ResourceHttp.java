package com.leqian.bao.common.http;

import com.leqian.bao.model.BaseHttp;
import com.leqian.bao.model.BaseModelReq;
import com.leqian.bao.model.account.LoginReq;
import com.leqian.bao.model.resource.GetLinkReq;
import com.nxin.base.model.http.callback.ModelCallBack;

/**
 * Created by fcl on 19.4.18
 * desc:
 */
public class ResourceHttp extends BaseHttp {

    public static void getLink(boolean coverLink, ModelCallBack callBack) {
        if (coverLink) {
            BaseModelReq baseModelReq = new BaseModelReq();
            baseModelReq.method = "getLink";
            executePostHttp(baseModelReq, callBack);
        } else {
            GetLinkReq baseModelReq = new GetLinkReq();
            baseModelReq.method = "getLink";
            baseModelReq.coverLink = coverLink;
            executePostHttp(baseModelReq, callBack);
        }

    }
}
