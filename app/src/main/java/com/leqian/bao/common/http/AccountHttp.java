package com.leqian.bao.common.http;

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

    public static void userRegister(String phone, String psd, String zfb, String realName, ModelCallBack callBack) {
        executeGetHttp("reg")
                .addParams(" zfb", zfb)
                .addParams("username", phone)
                .addParams("password", psd)
                .addParams("name", realName)
                .build().execute(callBack);
    }

    public static void joinTeam(String inviteCode, LoginResp userInfo, ModelCallBack callBack) {
        executeGetHttp("joinTeam")
                .addParams("uid", String.valueOf(userInfo.getUid()))
                .addParams("code", inviteCode)
                .build().execute(callBack);
    }

}
