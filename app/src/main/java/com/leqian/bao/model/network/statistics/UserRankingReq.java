package com.leqian.bao.model.network.statistics;

import com.leqian.bao.model.network.base.BaseModelReq;

/**
 * Created by fcl on 19.4.27
 * desc:
 */
public class UserRankingReq extends BaseModelReq {

    public static final String TIME_TODAY = "today";
    public static final String TIME_YESTERDAY = "yesterday";
    public static final String TIME_WEEK = "week";
    public static final String TIME_MONTH = "month";

    private String time = "";

    public String getTime() {
        return time == null ? "" : time;
    }

    public void setTime(String time) {
        this.time = time;
    }
}
