package com.leqian.bao.model.eventbus;

/**
 * Created by fcl on 19.5.6
 * desc:
 */
public class ClickTotalEvent extends BaseEvent {

    public int todayTotalCount = 0;

    public ClickTotalEvent(int total) {
        this.todayTotalCount = total;
    }
}
