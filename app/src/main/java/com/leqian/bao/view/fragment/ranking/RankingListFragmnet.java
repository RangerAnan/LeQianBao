package com.leqian.bao.view.fragment.ranking;

import android.os.Bundle;
import android.view.View;
import android.widget.AdapterView;

import com.leqian.bao.R;
import com.leqian.bao.common.adapter.RankingAdapter;
import com.leqian.bao.common.base.BaseListFragment;
import com.leqian.bao.model.constant.Constants;
import com.leqian.bao.model.eventbus.RankingSwitchEvent;
import com.leqian.bao.model.ui.RankingUIModel;
import com.leqian.bao.view.activity.statistics.ClickedTrendActivity;

import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;

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
    private ArrayList<RankingUIModel> mListData = new ArrayList<>();

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
        mAdapter = new RankingAdapter(mListData);
        listView.setAdapter(mAdapter);

        //模拟数据
        mListData = getData(0);
        mAdapter.setListData(mListData);

        listView.setOnItemClickListener(this);
    }

    private ArrayList<RankingUIModel> getData(int type) {
        mListData.clear();
        for (int i = 0; i < 10; i++) {
            RankingUIModel model = new RankingUIModel();
            model.name = "name" + i + (type == 0 ? "团员" : "部门");
            model.setCount(i);
            model.setTeam("team" + i);
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
                mAdapter.setListData(getData(0));
                break;
            case 1:
                //1-部门排行
                getRefreshLayout().autoRefresh();
                mAdapter.setListData(getData(1));
                break;
            case 3:
                //1-团队排行
                getRefreshLayout().autoRefresh();
                mAdapter.setListData(getData(1));
                break;
            case 4:
                //1-个人排行
                getRefreshLayout().autoRefresh();
                mAdapter.setListData(getData(1));
                break;
            default:
                break;
        }
    }


    @Override
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        RankingUIModel model = mListData.get(position);
        Bundle bundle = new Bundle();
        bundle.putString(Constants.INTENT_DATA_1, model.getUid());
        bundle.putString(Constants.INTENT_DATA_2, model.name);
        intent2Activity(ClickedTrendActivity.class, bundle);
    }
}
