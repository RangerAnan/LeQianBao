package com.leqian.bao.model.network.account;

import com.leqian.bao.model.network.base.BaseModelResp;

/**
 * Created by fcl on 19.4.16
 * desc:
 */
public class UploadImageResp extends BaseModelResp {

    private String headpic;

    public String getHeadpic() {
        return headpic == null ? "" : headpic;
    }

    public void setHeadpic(String headpic) {
        this.headpic = headpic;
    }
}
