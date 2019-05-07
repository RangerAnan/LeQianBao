package com.leqian.bao.model.eventbus;

/**
 * Created by fcl on 19.5.6
 * desc:
 */
public class ClickTotalEvent extends BaseEvent {

    public int todayTotalCount = 0;
    public int timeType;    //0-今日 1-昨日 2-本周 3-本月

    public ClickTotalEvent(int total, int timeType) {
        this.todayTotalCount = total;
        this.timeType = timeType;
    }
}
