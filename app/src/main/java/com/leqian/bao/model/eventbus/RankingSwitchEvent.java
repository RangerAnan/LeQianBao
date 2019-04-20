package com.leqian.bao.model.eventbus;

/**
 * Created by fcl on 19.4.20
 * desc:
 */
public class RankingSwitchEvent extends BaseEvent {

    /**
     * 0-团员排行，1-部门排行
     */
    public int rankType;

    public RankingSwitchEvent(int rankType) {
        this.rankType = rankType;
    }
}
