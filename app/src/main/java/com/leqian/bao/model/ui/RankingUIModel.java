package com.leqian.bao.model.ui;

import com.nxin.base.model.domain.BaseModel;

/**
 * Created by fcl on 19.4.18
 * desc:
 */
public class RankingUIModel extends CommonUIModel {

    private int count;
    private String team;
    private String uid;


    private String name;
    private String headpic;



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

}
