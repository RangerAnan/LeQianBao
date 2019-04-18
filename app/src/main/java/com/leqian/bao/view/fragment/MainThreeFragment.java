package com.leqian.bao.view.fragment;

import android.widget.ListView;

import com.leqian.bao.R;
import com.leqian.bao.common.adapter.RankingAdapter;
import com.leqian.bao.model.ui.RankingUIModel;
import com.nxin.base.utils.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by fcl on 19.4.12
 * desc:
 */
public class MainThreeFragment extends ViewpagerFragment {


    @BindView(R.id.listView)
    ListView listView;

    @BindView(R.id.refreshView)
    SmartRefreshLayout refreshView;

    ArrayList<RankingUIModel> mListData = new ArrayList<>();
    private RankingAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_three;
    }

    @Override
    public void initView() {
        super.initView();
        refreshView.setEnableLoadMore(false);

        mListData = getData();

        mAdapter = new RankingAdapter(mListData);
        listView.setAdapter(mAdapter);

        Logger.i(initTag() + "---mListData:" + mListData.size());
    }

    private ArrayList<RankingUIModel> getData() {
        for (int i = 0; i < 20; i++) {
            RankingUIModel rankingUIModel = new RankingUIModel();
            rankingUIModel.name = "title" + i;
            mListData.add(rankingUIModel);
        }
        return mListData;
    }
}
