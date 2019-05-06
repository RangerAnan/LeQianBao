package com.leqian.bao.model.network.team;

import com.google.gson.annotations.SerializedName;
import com.leqian.bao.model.network.base.BaseModelResp;

/**
 * Created by fcl on 19.4.27
 * desc:
 */
public class TeamInfoResp extends BaseModelResp {



    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * type : 1
         * headpic : /uploads/20190426/3a7aab4e9a83ae966ec119756441de1e.jpg
         * announcement : 这是测试的公告
         * code : 53k9m3
         * name : 方子
         * population : 1
         */

        private String type;
        private String headpic;
        private String announcement;
        @SerializedName("code")
        private String codeX;
        private String name;
        private int population;

        public String getType() {
            return type;
        }

        public void setType(String type) {
            this.type = type;
        }

        public String getHeadpic() {
            return headpic;
        }

        public void setHeadpic(String headpic) {
            this.headpic = headpic;
        }

        public String getAnnouncement() {
            return announcement;
        }

        public void setAnnouncement(String announcement) {
            this.announcement = announcement;
        }

        public String getCodeX() {
            return codeX;
        }

        public void setCodeX(String codeX) {
            this.codeX = codeX;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getPopulation() {
            return population;
        }

        public void setPopulation(int population) {
            this.population = population;
        }
    }
}
