package com.leqian.bao.model.account;

import com.leqian.bao.model.BaseModelResp;

/**
 * Created by fcl on 19.4.11
 * desc:
 */
public class LoginResp extends BaseModelResp {


    /**
     * uid : 12
     * type : 2
     */

    private String uid;
    private String type;    //type=1表示团长，2表示团员

    public String getUid() {
        return uid;
    }

    public void setUid(int String) {
        this.uid = uid;
    }

    public String getType() {
        return type;
    }

    public void setType(String type) {
        this.type = type;
    }
}
