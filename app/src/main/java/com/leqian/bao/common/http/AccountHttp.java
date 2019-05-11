package com.leqian.bao.common.http;

import com.leqian.bao.model.network.account.ChangePsdReq;
import com.leqian.bao.model.network.account.CheckSmsReq;
import com.leqian.bao.model.network.account.SmsCodeReq;
import com.leqian.bao.model.network.account.UpdateTeamManageReq;
import com.leqian.bao.model.network.base.BaseModelReq;
import com.leqian.bao.model.network.account.JoinTeamReq;
import com.leqian.bao.model.network.account.LoginReq;
import com.leqian.bao.model.network.account.RegisterReq;
import com.nxin.base.model.http.callback.ModelCallBack;

/**
 * 账户相关网络请求
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

    public static void joinTeam(String inviteCode, ModelCallBack callBack) {

        JoinTeamReq joinTeamReq = new JoinTeamReq();
        joinTeamReq.method = "joinTeam";
        joinTeamReq.code = inviteCode;
        executePostHttp(joinTeamReq, callBack);
    }

    public static void checkAccountState(ModelCallBack callBack) {
        BaseModelReq baseModelReq = new BaseModelReq();
        baseModelReq.method = "getUserState";
        executePostHttp(baseModelReq, callBack);
    }


    public static void getUserInfo(ModelCallBack callBack) {
        BaseModelReq baseModelReq = new BaseModelReq();
        baseModelReq.method = "getUserInfo";
        executePostHttp(baseModelReq, callBack);
    }


    public static void getTeamManage(String uid, ModelCallBack callBack) {
        BaseModelReq baseModelReq = new BaseModelReq();
        baseModelReq.method = "getTeamManage";
        baseModelReq.uid = uid;
        executePostHttp(baseModelReq, callBack);
    }

    public static void updateTeamManage(String desc, ModelCallBack callBack) {
        UpdateTeamManageReq req = new UpdateTeamManageReq();
        req.method = "updateTeamManage";
        req.announcement = desc;
        executePostHttp(req, callBack);
    }

    public static void getMsg(String phone, String msgType, ModelCallBack callBack) {
        SmsCodeReq req = new SmsCodeReq();
        req.method = "sendMsg";
        req.phone = phone;
        req.msgType = msgType;
        executePostHttp(req, callBack);
    }


    public static void regValidatePhone(String phone, String code, ModelCallBack callBack) {
        CheckSmsReq req = new CheckSmsReq();
        req.method = "regValidatePhone";
        req.phone = phone;
        req.code = code;
        executePostHttp(req, callBack);
    }

    public static void changePass(String phone, String psd, String code, ModelCallBack callBack) {
        ChangePsdReq req = new ChangePsdReq();
        req.method = "changePass";
        req.phone = phone;
        req.code = code;
        req.password = psd;
        executePostHttp(req, callBack);
    }
}
