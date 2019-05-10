package com.leqian.bao.model.network.account;

import com.leqian.bao.model.network.base.BaseModelReq;

/**
 * Created by Anan on 2019/5/9.
 */
public class SmsCodeReq extends BaseModelReq {

    public String phone;

    public String msgType;    //只能等于reg或者changePass
}
