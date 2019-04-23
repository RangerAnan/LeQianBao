package com.leqian.bao.common.http;

import com.leqian.bao.model.BaseHttp;
import com.leqian.bao.model.BaseModelReq;
import com.leqian.bao.model.account.LoginReq;
import com.leqian.bao.model.resource.GetCoverReq;
import com.leqian.bao.model.resource.GetLinkReq;
import com.leqian.bao.model.resource.UploadCoverReq;
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


    public static void getCover(boolean publicCover, ModelCallBack callBack) {
        GetCoverReq coverReq = new GetCoverReq();
        coverReq.method = "getCover";
        coverReq.publicCover = publicCover;
        executePostHttp(coverReq, callBack);
    }

    /*public static void uploadCover(String title, String desc, String coverImg, ModelCallBack callBack) {
        UploadCoverReq coverReq = new UploadCoverReq();
        coverReq.method = "uploadCover";
        coverReq.title = title;
        coverReq.desc = desc;
        coverReq.pic = coverImg;
        executePostHttp(coverReq, callBack);
    }*/
}
