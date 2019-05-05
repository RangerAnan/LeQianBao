package com.leqian.bao.model.network.team;

import com.leqian.bao.model.network.base.BaseModelResp;

/**
 * Created by fcl on 19.4.27
 * desc:
 */
public class TeamInfoResp extends BaseModelResp {

    public String name = "汪洋";
    public String inviteCode = "a186";


    /**
     * code : 53k9m3
     * type : 1
     * headpic : /uploads/20190426/3a7aab4e9a83ae966ec119756441de1e.jpg
     * announcement : 这是测试公告
     * population : 1
     */

    private String type;
    private String headpic;
    private String announcement;
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

    public int getPopulation() {
        return population;
    }

    public void setPopulation(int population) {
        this.population = population;
    }

    public String getName() {
        return name == null ? "" : name;
    }

    public void setName(String name) {
        this.name = name;
    }
}
