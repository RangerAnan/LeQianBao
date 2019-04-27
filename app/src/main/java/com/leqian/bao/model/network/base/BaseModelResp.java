package com.leqian.bao.model.network.base;

import com.nxin.base.model.domain.BaseModel;

/**
 * Created by fcl on 19.4.11
 * desc:
 */
public class BaseModelResp extends BaseModel {

    /**
     * 成功code=1,失败code=0
     * <p>
     * 账户状态
     * code=1账户已经停用，执行退出程序
     * code=2还未加入战队，执行到首页弹出加入战队窗口
     * code=101正常的团长号
     * code=102正常的团员号
     */
    private int code;
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
