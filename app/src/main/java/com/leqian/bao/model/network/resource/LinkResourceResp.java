package com.leqian.bao.model.network.resource;

import com.leqian.bao.model.network.base.BaseModelResp;

import java.util.List;

/**
 * Created by fcl on 19.4.19
 * desc:
 */
public class LinkResourceResp extends BaseModelResp {


    private List<String> data;

    public List<String> getData() {
        return data;
    }

    public void setData(List<String> data) {
        this.data = data;
    }
}
