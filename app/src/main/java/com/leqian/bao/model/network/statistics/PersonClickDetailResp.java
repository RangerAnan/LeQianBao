package com.leqian.bao.model.network.statistics;

import com.leqian.bao.model.network.base.BaseModelResp;

import java.util.List;

/**
 * Created by fcl on 19.5.6
 * desc:
 */
public class PersonClickDetailResp extends BaseModelResp {



    private DataBean data;

    public DataBean getData() {
        return data;
    }

    public void setData(DataBean data) {
        this.data = data;
    }

    public static class DataBean {
        /**
         * today : [0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0,0]
         * totalToday : 0
         * day : [{"date":"04-29","count":0},{"date":"04-30","count":0},{"date":"05-01","count":0},{"date":"05-02","count":0},{"date":"05-03","count":0},{"date":"05-04","count":0},{"date":"05-05","count":0}]
         * totalDay : 0
         * name : 方子60
         */

        private int totalToday;
        private int totalDay;
        private String name;
        private List<Integer> today;
        private List<DayBean> day;

        public int getTotalToday() {
            return totalToday;
        }

        public void setTotalToday(int totalToday) {
            this.totalToday = totalToday;
        }

        public int getTotalDay() {
            return totalDay;
        }

        public void setTotalDay(int totalDay) {
            this.totalDay = totalDay;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public List<Integer> getToday() {
            return today;
        }

        public void setToday(List<Integer> today) {
            this.today = today;
        }

        public List<DayBean> getDay() {
            return day;
        }

        public void setDay(List<DayBean> day) {
            this.day = day;
        }

        public static class DayBean {
            /**
             * date : 04-29
             * count : 0
             */

            private String date;
            private int count;

            public String getDate() {
                return date;
            }

            public void setDate(String date) {
                this.date = date;
            }

            public int getCount() {
                return count;
            }

            public void setCount(int count) {
                this.count = count;
            }
        }
    }
}
