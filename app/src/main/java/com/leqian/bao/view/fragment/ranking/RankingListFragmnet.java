package com.leqian.bao.view.fragment.ranking;

import android.os.Bundle;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.AdapterView;

import com.leqian.bao.R;
import com.leqian.bao.common.adapter.RankingAdapter;
import com.leqian.bao.common.base.BaseListFragment;
import com.leqian.bao.common.http.StatisticsHttp;
import com.leqian.bao.model.constant.Constants;
import com.leqian.bao.model.eventbus.RankingSwitchEvent;
import com.leqian.bao.model.network.statistics.UserRankingReq;
import com.leqian.bao.model.network.statistics.UserRankingResp;
import com.leqian.bao.model.ui.RankingUIModel;
import com.leqian.bao.view.activity.statistics.ClickedTrendActivity;
import com.nxin.base.model.http.callback.ModelCallBack;
import com.nxin.base.utils.Logger;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by fcl on 19.4.20
 * desc:排行列表
 */
public class RankingListFragmnet extends BaseListFragment implements AdapterView.OnItemClickListener {

    /**
     * 0-今日 1-昨日 2-本周 3-本月
     */
    private int timeType;

    /**
     * 0-团员排行，1-部门排行,2-团队排行，3-个人排行
     */
    private int rankType;

    /**
     * 团队进入-0，排行进入-1
     */
    private int pageFrom;

    private RankingAdapter mAdapter;
    private List<UserRankingResp.DataBean> mListData = new ArrayList<>();

    private String rankTime = UserRankingReq.TIME_TODAY;

    @Override
    public boolean isOpenEventBus() {
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_ranking_list;
    }

    @Override
    public void initView() {
        super.initView();
        Bundle arguments = getArguments();
        timeType = arguments.getInt(Constants.INTENT_DATA_1, 0);
        rankType = arguments.getInt(Constants.INTENT_DATA_2, 0);
        pageFrom = arguments.getInt(Constants.INTENT_DATA_3, 0);
        getRefreshLayout().setEnableLoadMore(false);

        if (pageFrom == 0) {
            rankType = Constants.TAB_TWO_RANK_TYPE;
        } else if (pageFrom == 1) {
            rankType = Constants.TAB_THREE_RANK_TYPE;
        }
    }

    @Override
    public void initData() {
        super.initData();
        mAdapter = new RankingAdapter(mListData, this, rankType);
        listView.setAdapter(mAdapter);

        mAdapter.setListData(mListData);

        listView.setOnItemClickListener(this);

        //check timeType
        if (timeType == 0) {
            rankTime = UserRankingReq.TIME_TODAY;
        } else if (timeType == 1) {
            rankTime = UserRankingReq.TIME_YESTERDAY;
        } else if (timeType == 2) {
            rankTime = UserRankingReq.TIME_WEEK;
        } else if (timeType == 3) {
            rankTime = UserRankingReq.TIME_MONTH;
        }

        //check rankType
        requestServerCount();
    }

    private void requestServerCount() {
        Logger.i(initTag() + "---requestServerCount--rankType:" + rankType + ";timeType:" + timeType);
        if (rankType == 0) {
            requestMemberRanking();
        } else if (rankType == 2) {   //团队
            requestTeamRank();
        } else if (rankType == 3) {   //个人
            requestUserRank();
        }
    }

    private void requestUserRank() {
        StatisticsHttp.getPersonRank(rankTime, new ModelCallBack<UserRankingResp>() {
            @Override
            public void onResponse(UserRankingResp response, int id) {
                mListData = response.getData();
                mAdapter.setListData(mListData);
            }
        });
    }

    private void requestTeamRank() {
        StatisticsHttp.getTeamRank(rankTime, new ModelCallBack<UserRankingResp>() {
            @Override
            public void onResponse(UserRankingResp response, int id) {
                mListData = response.getData();
                mAdapter.setListData(mListData);
            }
        });
    }

    private void requestMemberRanking() {
        StatisticsHttp.getMemberRank(rankTime, new ModelCallBack<UserRankingResp>() {
            @Override
            public void onResponse(UserRankingResp response, int id) {
                mListData = response.getData();
                mAdapter.setListData(mListData);
            }
        });
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void switchRankType(RankingSwitchEvent event) {
        if (pageFrom == 0 && event.rankType != 0 && event.rankType != 1) {
            return;
        }
        if (pageFrom == 1 && (event.rankType != 2 && event.rankType != 3)) {
            return;
        }

        rankType = event.rankType;
        Logger.i(initTag() + "---switchRankType--rankType:" + rankType + ";timeType:" + timeType + ";pageFrom:" + pageFrom);
        mAdapter.setRankType(rankType);
        getRefreshLayout().autoRefresh();

        switch (event.rankType) {
            case 0:
                //0-团员排行
                break;
            case 1:
                //1-部门排行
                break;
            case 2:
                //1-团队排行
                break;
            case 3:
                //1-个人排行
                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        super.onRefresh(refreshLayout);
        requestServerCount();
    }

    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        UserRankingResp.DataBean model = mListData.get(position);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.INTENT_DATA_1, model.getUid());
        bundle.putString(Constants.INTENT_DATA_2, model.getName());
        intent2Activity(ClickedTrendActivity.class, bundle);
    }
}
