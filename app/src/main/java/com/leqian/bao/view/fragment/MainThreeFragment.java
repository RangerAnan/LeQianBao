package com.leqian.bao.view.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.leqian.bao.R;
import com.leqian.bao.common.adapter.RankingAdapter;
import com.leqian.bao.common.adapter.RankingFragmentAdapter;
import com.leqian.bao.common.base.BaseListFragment;
import com.leqian.bao.common.http.StatisticsHttp;
import com.leqian.bao.model.constant.Constants;
import com.leqian.bao.model.eventbus.RankingSwitchEvent;
import com.leqian.bao.model.network.statistics.UserRankingResp;
import com.leqian.bao.model.ui.RankingUIModel;
import com.leqian.bao.view.fragment.ranking.RankingListFragmnet;
import com.nxin.base.model.http.callback.ModelCallBack;
import com.nxin.base.utils.Logger;
import com.nxin.base.widget.NXFragment;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by fcl on 19.4.12
 * desc:
 */
public class MainThreeFragment extends ViewpagerFragment implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.radio_button1)
    RadioButton radio_button1;

    @BindView(R.id.radio_button2)
    RadioButton radio_button2;

    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    ArrayList<NXFragment> mFragmentList = new ArrayList<>();
    private RankingFragmentAdapter mAdapter;


    @Override
    protected boolean isDelayLoad() {
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_three;
    }

    @Override
    public void initView() {
        super.initView();

        radio_button1.setText("团队排行");
        radio_button2.setText("个人排行");

        tabLayout.setupWithViewPager(viewPager);

        mFragmentList = getFragment();

        mAdapter = new RankingFragmentAdapter(getChildFragmentManager(), mFragmentList);
        viewPager.setAdapter(mAdapter);

        radioGroup.setOnCheckedChangeListener(this);
    }


    @Override
    public void initData() {
        super.initData();

    }


    private ArrayList<NXFragment> getFragment() {
        String[] array = getResources().getStringArray(R.array.ranking_vp_item);

        for (int i = 0; i < array.length; i++) {
            RankingListFragmnet rankingListFragmnet = new RankingListFragmnet();
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.INTENT_DATA_1, i);
            bundle.putInt(Constants.INTENT_DATA_2, Constants.TAB_THREE_RANK_TYPE);
            bundle.putInt(Constants.INTENT_DATA_3, 1);
            rankingListFragmnet.setArguments(bundle);
            mFragmentList.add(rankingListFragmnet);
        }
        return mFragmentList;
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radio_button1:
                //团队
                changeRadioButtonTextColor(true);
                Constants.TAB_THREE_RANK_TYPE = 2;
                EventBus.getDefault().post(new RankingSwitchEvent(Constants.TAB_THREE_RANK_TYPE));
                break;
            case R.id.radio_button2:
                //个人
                changeRadioButtonTextColor(false);
                Constants.TAB_THREE_RANK_TYPE = 3;
                EventBus.getDefault().post(new RankingSwitchEvent(Constants.TAB_THREE_RANK_TYPE));
                break;
            default:
                break;
        }
    }

    private void changeRadioButtonTextColor(boolean button1Check) {
        radio_button1.setTextColor(button1Check ? ContextCompat.getColor(mContext, R.color.theme) : ContextCompat.getColor(mContext, R.color.color_gray));
        radio_button2.setTextColor(!button1Check ? ContextCompat.getColor(mContext, R.color.theme) : ContextCompat.getColor(mContext, R.color.color_gray));
    }
}
