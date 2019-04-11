package com.leqian.bao.model;

import com.nxin.base.model.domain.BaseModel;

/**
 * Created by fcl on 19.4.11
 * desc:
 */
public class BaseModelResp extends BaseModel {


    private int r;
    private String m;

    public int getR() {
        return r;
    }

    public void setR(int r) {
        this.r = r;
    }

    public String getM() {
        return m;
    }

    public void setM(String m) {
        this.m = m;
    }


    public static class DBean {
    }
}
