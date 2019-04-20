package com.leqian.bao.model.ui;

import com.nxin.base.model.domain.BaseModel;

/**
 * Created by fcl on 19.4.18
 * desc:
 */
public class RankingUIModel extends CommonUIModel {

    private String icon;
    private int count;
    private String team;
    private String uid;


    public String getIcon() {
        return icon == null ? "" : icon;
    }

    public void setIcon(String icon) {
        this.icon = icon;
    }

    public int getCount() {
        return count;
    }

    public void setCount(int count) {
        this.count = count;
    }

    public String getTeam() {
        return team == null ? "" : team;
    }

    public void setTeam(String team) {
        this.team = team;
    }

    public String getUid() {
        return uid == null ? "" : uid;
    }

    public void setUid(String uid) {
        this.uid = uid;
    }
}
