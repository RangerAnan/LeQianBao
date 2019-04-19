package com.leqian.bao.common.http;

import com.leqian.bao.model.BaseHttp;
import com.leqian.bao.model.BaseModelReq;
import com.leqian.bao.model.account.JoinTeamReq;
import com.leqian.bao.model.account.LoginReq;
import com.leqian.bao.model.account.LoginResp;
import com.leqian.bao.model.account.RegisterReq;
import com.nxin.base.model.http.callback.ModelCallBack;


/**
 * @CreateBy qsmaxmin
 * @Date 2017/5/9 15:53
 * @Description
 */

public class AccountHttp extends BaseHttp {



    public static void userLogin(String phone, String psd, ModelCallBack callBack) {
        LoginReq loginRep = new LoginReq();
        loginRep.username = phone;
        loginRep.password = psd;
        loginRep.method = "login";
        executePostHttp(loginRep, callBack);

    }

    public static void userRegister(String phone, String psd, String zfb, String realName, ModelCallBack callBack) {
        RegisterReq registerReq = new RegisterReq();
        registerReq.method = "reg";
        registerReq.username = phone;
        registerReq.password = psd;
        registerReq.zfb = zfb;
        registerReq.name = realName;
        executePostHttp(registerReq, callBack);
    }

    public static void joinTeam(String inviteCode, LoginResp userInfo, ModelCallBack callBack) {

        JoinTeamReq joinTeamReq = new JoinTeamReq();
        joinTeamReq.method = "joinTeam";
        joinTeamReq.code = inviteCode;
        joinTeamReq.uid = userInfo.getUid();
        executePostHttp(joinTeamReq, callBack);
    }

    public static void checkAccountState(ModelCallBack callBack) {
        BaseModelReq baseModelReq = new BaseModelReq();
        baseModelReq.method = "getUserState";
        executePostHttp(baseModelReq, callBack);
    }

}
