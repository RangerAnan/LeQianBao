package com.leqian.bao.model.network.statistics;

import com.leqian.bao.model.network.base.BaseModelResp;

import java.util.List;

/**
 * Created by fcl on 19.4.27
 * desc:
 */
public class UserRankingResp extends BaseModelResp {


    private List<DataBean> data;
    /**
     * data : [{"name":"刘力","headpic":null,"uid":"2220243","count":0},{"name":"小方","headpic":"/uploads/20190416/46c972295b0a2eb6f0e87a02dc5df5a1.jpg","uid":"1753690","count":0},{"name":"方子6","headpic":"/uploads/20190505/66369e9eac17143e1993d39685c2aadc.jpg","uid":"1652893","count":0},{"name":"方子60","headpic":"/uploads/20190422/14844aa1404f953293e2b134ca431129.jpg","uid":"1526259","count":0},{"name":"詹小芳","headpic":"/uploads/20190504/5580deb580775cd108f78f8460f8c221.jpg","uid":"2142716","count":0},{"name":"郑","headpic":"/uploads/20190421/f6d95f1d2814f32e19afd535413a71e3.jpg","uid":"2040114","count":0},{"name":"陈sir","headpic":null,"uid":"2394063","count":0},{"name":"34","headpic":"/uploads/20190416/156a80a8cbb7b6e268fa58c319003a75.jpg","uid":"1842980","count":0},{"name":"34","headpic":"/uploads/20190416/d0e5a41c7efae290b453989fdc9b6ed4.jpg","uid":"1914880","count":0}]
     * data2 : {"total":1}
     */

    private Data2Bean data2;

    public Data2Bean getData2() {
        return data2;
    }

    public void setData2(Data2Bean data2) {
        this.data2 = data2;
    }

    public List<DataBean> getData() {
        return data;
    }

    public void setData(List<DataBean> data) {
        this.data = data;
    }

    public static class DataBean {

        private String name;
        private String headpic;
        private String uid;
        private int count;

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

        public String getUid() {
            return uid;
        }

        public void setUid(String uid) {
            this.uid = uid;
        }

        public int getCount() {
            return count;
        }

        public void setCount(int count) {
            this.count = count;
        }

        //个人排行
        private String father;
        private String fatherName;

        public String getFather() {
            return father == null ? "" : father;
        }

        public void setFather(String father) {
            this.father = father;
        }

        public String getFatherName() {
            return fatherName == null ? "" : fatherName;
        }

        public void setFatherName(String fatherName) {
            this.fatherName = fatherName;
        }
    }


    public static class Data2Bean {


        private int total;

        public int getTotal() {
            return total;
        }

        public void setTotal(int total) {
            this.total = total;
        }
    }
}
