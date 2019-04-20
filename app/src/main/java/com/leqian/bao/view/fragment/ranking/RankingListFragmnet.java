package com.leqian.bao.view.fragment.ranking;

import android.os.Bundle;

import com.leqian.bao.R;
import com.leqian.bao.common.base.BaseListFragment;
import com.leqian.bao.model.Constants;

/**
 * Created by fcl on 19.4.20
 * desc:
 */
public class RankingListFragmnet extends BaseListFragment {


    private int pageFrom;

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
}
