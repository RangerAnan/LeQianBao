package com.leqian.bao.model.network.statistics;

import com.leqian.bao.model.network.base.BaseModelResp;

import java.util.List;

/**
 * Created by fcl on 19.4.27
 * desc:
 */
public class UserRankingResp extends BaseModelResp {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {

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

        //个人排行
        private String father;
        private String fatherName;

        public String getFather() {
            return father == null ? "" : father;
        }

        public void setFather(String father) {
            this.father = father;
        }

        public String getFatherName() {
            return fatherName == null ? "" : fatherName;
        }

        public void setFatherName(String fatherName) {
            this.fatherName = fatherName;
        }
    }
}
