package com.leqian.bao.model.network.statistics;

import com.leqian.bao.model.network.base.BaseModelResp;

/**
 * Created by fcl on 19.4.27
 * desc:
 */
public class UserCountResp extends BaseModelResp {


    /**
     * data : {"yesterday":0,"today":0,"month":0}
     */

    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * yesterday : 0
         * today : 0
         * month : 0
         */

        private int yesterday;
        private int today;
        private int month;

        public int getYesterday() {
            return yesterday;
        }

        public void setYesterday(int yesterday) {
            this.yesterday = yesterday;
        }

        public int getToday() {
            return today;
        }

        public void setToday(int today) {
            this.today = today;
        }

        public int getMonth() {
            return month;
        }

        public void setMonth(int month) {
            this.month = month;
        }
    }
}
