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
        pageFrom = arguments.getInt(Constants.INTENT_DATA_1, 0);
        getRefreshLayout().setEnableLoadMore(false);

    }

    @Override
    public void initData() {
        super.initData();
        mAdapter = new RankingAdapter(mListData,this);
        listView.setAdapter(mAdapter);

        mAdapter.setListData(mListData);

        listView.setOnItemClickListener(this);

        //check rankType
        if (pageFrom == 0) {
            rankTime = UserRankingReq.TIME_TODAY;
        } else if (pageFrom == 1) {
            rankTime = UserRankingReq.TIME_YESTERDAY;
        } else if (pageFrom == 2) {
            rankTime = UserRankingReq.TIME_WEEK;
        } else if (pageFrom == 3) {
            rankTime = UserRankingReq.TIME_MONTH;
        }
        requestRanking();
    }

    private void requestRanking() {
        StatisticsHttp.getMemberRank(rankTime, new ModelCallBack<UserRankingResp>() {
            @Override
            public void onResponse(UserRankingResp response, int id) {
                mListData = response.getData();
                mAdapter.setListData(mListData);
            }
        });
    }

    private List<UserRankingResp.DataBean> getData(int type) {
        mListData.clear();
        for (int i = 0; i < 10; i++) {
            UserRankingResp.DataBean model = new UserRankingResp.DataBean();
            model.setName("name" + i + (type == 0 ? "团员" : "部门"));
            model.setCount(i);
            mListData.add(model);
        }
        return mListData;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void switchRankType(RankingSwitchEvent event) {
        switch (event.rankType) {
            case 0:
                //0-团员排行
                getRefreshLayout().autoRefresh();
                break;
            case 1:
                //1-部门排行
                getRefreshLayout().autoRefresh();
                break;
            case 3:
                //1-团队排行
                getRefreshLayout().autoRefresh();
                break;
            case 4:
                //1-个人排行
                getRefreshLayout().autoRefresh();
                break;
            default:
                break;
        }
    }

    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        super.onRefresh(refreshLayout);
        requestRanking();
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
