package com.leqian.bao.common.http;

import com.leqian.bao.model.network.resource.GetCoverReq;
import com.leqian.bao.model.network.statistics.UserCountReq;
import com.leqian.bao.model.network.statistics.UserCountResp;
import com.leqian.bao.model.network.statistics.UserRankingReq;
import com.nxin.base.model.http.callback.ModelCallBack;

/**
 * Created by fcl on 19.4.27
 * desc:
 */
public class StatisticsHttp extends BaseHttp {

    /**
     * 获取用户统计数量
     */
    public static void getUserCount(ModelCallBack callBack) {
        UserCountReq req = new UserCountReq();
        req.method = "getCount";
        executePostHttp(req, callBack);
    }


    /**
     * 获取用户统计排行
     */
    public static void getUserRanking(ModelCallBack callBack) {
        UserRankingReq req = new UserRankingReq();
        req.method = "getCount";
        executePostHttp(req, callBack);
    }

    /**
     * 获取团队按钮下的团员排行
     */
    public static void getMemberRank(String time, ModelCallBack callBack) {
        UserRankingReq req = new UserRankingReq();
        req.method = "getMemberRank";
        req.setTime(time);
        executePostHttp(req, callBack);
    }

    /**
     * 获取排行按钮下的团队排行
     */
    public static void getTeamRank(String time, ModelCallBack callBack) {
        UserRankingReq req = new UserRankingReq();
        req.method = "getTeamRank";
        req.setTime(time);
        executePostHttp(req, callBack);
    }

    /**
     * 获取排行按钮下的个人排行
     */
    public static void getPersonRank(String time, ModelCallBack callBack) {
        UserRankingReq req = new UserRankingReq();
        req.method = "getPersonRank";
        req.setTime(time);
        executePostHttp(req, callBack);
    }
}
