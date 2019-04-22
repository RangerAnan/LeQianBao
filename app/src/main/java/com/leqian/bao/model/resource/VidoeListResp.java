package com.leqian.bao.model.resource;

import com.leqian.bao.model.BaseModelResp;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fcl on 19.4.19
 * desc:
 */
public class VidoeListResp extends BaseModelResp {

    private List<DataBean> data;

    public List<DataBean> getData() {
        return data == null ? new ArrayList<DataBean>() : data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {

        private int id;
        private String title;
        private String desc;
        private String pic;
        private int state;      //state=0表示等待审核，1表示审核通过，2表示审核失败。只有审核通过的才可能用

        private boolean isCheck;   //ui model

        public int getId() {
            return id;
        }

        public void setId(int id) {
            this.id = id;
        }

        public String getTitle() {
            return title;
        }

        public void setTitle(String title) {
            this.title = title;
        }

        public String getDesc() {
            return desc;
        }

        public void setDesc(String desc) {
            this.desc = desc;
        }

        public String getPic() {
            return pic;
        }

        public void setPic(String pic) {
            this.pic = pic;
        }

        public int getState() {
            return state;
        }

        public void setState(int state) {
            this.state = state;
        }

        public boolean isCheck() {
            return isCheck;
        }

        public void setCheck(boolean check) {
            isCheck = check;
        }
    }
}
