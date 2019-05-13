package com.leqian.bao.model.network.appinfo;

import com.google.gson.annotations.SerializedName;
import com.leqian.bao.model.network.base.BaseModelResp;

/**
 * Created by fcl on 19.5.11
 * desc:
 */
public class CheckAppVersionResp extends BaseModelResp {


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * version : 2.0.0
         * forceUpdate : 1
         * address : http://vs.ahqibang.com/myb-v2.0.0-2019-05-13-16-25-34-release.apk
         * msg : 更新拆线图，修复未知BUG
         */

        private String version;
        private int forceUpdate;
        private String address;
        @SerializedName("msg")
        private String msgX;

        public String getVersion() {
            return version;
        }

        public void setVersion(String version) {
            this.version = version;
        }

        public int getForceUpdate() {
            return forceUpdate;
        }

        public void setForceUpdate(int forceUpdate) {
            this.forceUpdate = forceUpdate;
        }

        public String getAddress() {
            return address;
        }

        public void setAddress(String address) {
            this.address = address;
        }

        public String getMsgX() {
            return msgX;
        }

        public void setMsgX(String msgX) {
            this.msgX = msgX;
        }
    }
}
