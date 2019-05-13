package com.leqian.bao.model.network.appinfo;

import com.google.gson.annotations.SerializedName;
import com.leqian.bao.model.network.base.BaseModelResp;

/**
 * Created by fcl on 19.5.11
 * desc:
 */
public class CheckAppVersionResp extends BaseModelResp {

    public boolean hasUpdate = true;

    public String updateDesc = "V1.1.0更新提示:\n\n1.优化界面\n2.增加新功能";

    public String apkUrl = "https://timgsa.baidu.com/timg?image&quality=80&size=b9999_10000&sec=1557569416771&di=1e2e4a8dd5e717de88ab53db60d4ee43&imgtype=0&src=http%3A%2F%2Fku.90sjimg.com%2Felement_origin_min_pic%2F01%2F59%2F45%2F2757485d2425ae9.jpg";
    public String newVersion = "1.1.0";


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
