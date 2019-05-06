package com.leqian.bao.model.network.statistics;

import com.google.gson.annotations.SerializedName;
import com.leqian.bao.model.network.base.BaseModelResp;

import java.util.List;

/**
 * Created by fcl on 19.5.6
 * desc:
 */
public class TeamClickDetailResp extends BaseModelResp {


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
         * totalToday : 1
         * day : {"04-28":1,"04-29":0,"04-30":0,"05-01":0,"05-02":0,"05-03":0,"05-04":0}
         * totalDay : 0
         */

        private int totalToday;
        private DayBean day;
        private int totalDay;
        private List<Integer> today;

        public int getTotalToday() {
            return totalToday;
        }

        public void setTotalToday(int totalToday) {
            this.totalToday = totalToday;
        }

        public DayBean getDay() {
            return day;
        }

        public void setDay(DayBean day) {
            this.day = day;
        }

        public int getTotalDay() {
            return totalDay;
        }

        public void setTotalDay(int totalDay) {
            this.totalDay = totalDay;
        }

        public List<Integer> getToday() {
            return today;
        }

        public void setToday(List<Integer> today) {
            this.today = today;
        }

        public static class DayBean {
            /**
             * 04-28 : 1
             * 04-29 : 0
             * 04-30 : 0
             * 05-01 : 0
             * 05-02 : 0
             * 05-03 : 0
             * 05-04 : 0
             */

            @SerializedName("04-28")
            private int _$0428;
            @SerializedName("04-29")
            private int _$0429;
            @SerializedName("04-30")
            private int _$0430;
            @SerializedName("05-01")
            private int _$0501;
            @SerializedName("05-02")
            private int _$0502;
            @SerializedName("05-03")
            private int _$0503;
            @SerializedName("05-04")
            private int _$0504;

            public int get_$0428() {
                return _$0428;
            }

            public void set_$0428(int _$0428) {
                this._$0428 = _$0428;
            }

            public int get_$0429() {
                return _$0429;
            }

            public void set_$0429(int _$0429) {
                this._$0429 = _$0429;
            }

            public int get_$0430() {
                return _$0430;
            }

            public void set_$0430(int _$0430) {
                this._$0430 = _$0430;
            }

            public int get_$0501() {
                return _$0501;
            }

            public void set_$0501(int _$0501) {
                this._$0501 = _$0501;
            }

            public int get_$0502() {
                return _$0502;
            }

            public void set_$0502(int _$0502) {
                this._$0502 = _$0502;
            }

            public int get_$0503() {
                return _$0503;
            }

            public void set_$0503(int _$0503) {
                this._$0503 = _$0503;
            }

            public int get_$0504() {
                return _$0504;
            }

            public void set_$0504(int _$0504) {
                this._$0504 = _$0504;
            }
        }
    }
}
