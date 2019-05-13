package com.leqian.bao.model.network.account;

import com.leqian.bao.model.network.base.BaseModelResp;

/**
 * Created by fcl on 19.5.13
 * desc:
 */
public class UserStarResp extends BaseModelResp {


    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * name : 郭峰2
         * headpic : /uploads/20190512/9a89dafec778a1b4680b2faeef1c6b06.jpg
         * uid : 2863341
         * count : 3
         */

        private String name;
        private String headpic;
        private String uid;
        private int count;

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public String getHeadpic() {
            return headpic;
        }

        public void setHeadpic(String headpic) {
            this.headpic = headpic;
        }

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }
    }
}
