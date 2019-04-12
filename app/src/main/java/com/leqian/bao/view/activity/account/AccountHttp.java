package com.leqian.bao.view.activity.account;

import com.leqian.bao.model.BaseHttp;
import com.leqian.bao.model.BaseModelReq;
import com.leqian.bao.model.account.LoginResp;
import com.nxin.base.model.http.OkHttpUtils;
import com.nxin.base.model.http.callback.ModelCallBack;


/**
 * @CreateBy qsmaxmin
 * @Date 2017/5/9 15:53
 * @Description
 */

public class AccountHttp extends BaseHttp {


    public static final String testJson = hostUrl + "/wxarticle/chapters/json";

    public static void userLogin(String phone, String psd, ModelCallBack callBack) {
        executeGetHttp("login")
                .addParams("username", phone)
                .addParams("password", psd)
                .build().execute(callBack);
    }

}
