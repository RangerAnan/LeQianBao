package com.leqian.bao.model.ui;

import com.nxin.base.model.domain.BaseModel;

/**
 * Created by fcl on 19.4.18
 * desc:
 */
public class RankingUIModel extends CommonUIModel {

    private  String title;

    public String getTitle() {
        return title == null ? "" : title;
    }

    public void setTitle(String title) {
        this.title = title;
    }
}
