package com.leqian.bao.model;

import com.nxin.base.model.domain.BaseModel;

/**
 * Created by fcl on 19.4.11
 * desc:
 */
public class BaseModelResp extends BaseModel {


    private int code;           //成功code=1,失败code=0
    private String msg;

    public int getCode() {
        return code;
    }

    public void setCode(int code) {
        this.code = code;
    }

    public String getMsg() {
        return msg == null ? "" : msg;
    }

    public void setMsg(String msg) {
        this.msg = msg;
    }
}
