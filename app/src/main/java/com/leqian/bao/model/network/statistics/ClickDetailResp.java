package com.leqian.bao.model.network.statistics;

import com.leqian.bao.model.network.base.BaseModelResp;

import java.util.List;

/**
 * Created by fcl on 19.4.27
 * desc:
 */
public class ClickDetailResp extends BaseModelResp {


    private List<DataBean> data;

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {

        private String date;
        private int count;

        public String getDate() {
            return date == null ? "" : date;
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
