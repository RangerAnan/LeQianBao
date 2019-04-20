package com.leqian.bao.view.fragment.ranking;

import android.os.Bundle;

import com.leqian.bao.R;
import com.leqian.bao.common.adapter.RankingAdapter;
import com.leqian.bao.common.base.BaseListFragment;
import com.leqian.bao.model.Constants;
import com.leqian.bao.model.ui.RankingUIModel;

import java.util.ArrayList;

/**
 * Created by fcl on 19.4.20
 * desc:排行列表
 */
public class RankingListFragmnet extends BaseListFragment {

    /**
     * 0-今日 1-昨日 2-本周 3-本月
     */
    private int pageFrom;

    private RankingAdapter mAdapter;
    private ArrayList<RankingUIModel> mListData = new ArrayList<>();

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
        mListData = getData();
        mAdapter.setListData(mListData);
    }

    private ArrayList<RankingUIModel> getData() {
        for (int i = 0; i < 10; i++) {
            RankingUIModel model = new RankingUIModel();
            model.name = "name" + i;
            model.setCount(i);
            model.setTeam("team" + i);
            mListData.add(model);
        }
        return mListData;
    }
}
