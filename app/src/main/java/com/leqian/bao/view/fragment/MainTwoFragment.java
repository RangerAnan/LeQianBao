package com.leqian.bao.view.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.view.View;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.leqian.bao.R;
import com.leqian.bao.common.adapter.RankingFragmentAdapter;
import com.leqian.bao.common.http.AccountHttp;
import com.leqian.bao.model.constant.Constants;
import com.leqian.bao.model.eventbus.RankingSwitchEvent;
import com.leqian.bao.model.network.team.TeamInfoResp;
import com.leqian.bao.view.fragment.ranking.RankingListFragmnet;
import com.nxin.base.model.http.callback.ModelCallBack;
import com.nxin.base.widget.NXFragment;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by fcl on 19.4.12
 * desc:
 */
public class MainTwoFragment extends ViewpagerFragment implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.tabLayout)
    TabLayout tabLayout;

    @BindView(R.id.viewpager)
    ViewPager viewPager;

    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;

    @BindView(R.id.radio_button1)
    RadioButton radio_button1;

    @BindView(R.id.radio_button2)
    RadioButton radio_button2;

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.tv_desc)
    TextView tv_desc;

    @BindView(R.id.tv_name)
    TextView tv_name;

    @BindView(R.id.tv_count_today)
    TextView tv_count_today;

    ArrayList<NXFragment> mFragmentList = new ArrayList<>();
    private RankingFragmentAdapter mAdapter;


    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_two;
    }


    @Override
    protected boolean isDelayLoad() {
        return true;
    }

    @Override
    public void initView() {
        super.initView();
        radio_button2.setVisibility(View.GONE);

        tabLayout.setupWithViewPager(viewPager);

        mFragmentList = getFragment();

        mAdapter = new RankingFragmentAdapter(getChildFragmentManager(), mFragmentList);
        viewPager.setAdapter(mAdapter);

        radioGroup.setOnCheckedChangeListener(this);
    }

    @Override
    public void initData() {
        super.initData();

        requestTeamInfo();
    }

    private void requestTeamInfo() {
        AccountHttp.getTeamManage(new ModelCallBack<TeamInfoResp>() {
            @Override
            public void onResponse(TeamInfoResp response, int id) {
                String name = response.getName();
                tv_title.setText(name + "-团队");
                tv_desc.setText(response.getAnnouncement());

            }
        });

    }

    private ArrayList<NXFragment> getFragment() {
        String[] array = getResources().getStringArray(R.array.ranking_vp_item);

        for (int i = 0; i < array.length; i++) {
            RankingListFragmnet rankingListFragmnet = new RankingListFragmnet();
            Bundle bundle = new Bundle();
            bundle.putInt(Constants.INTENT_DATA_1, i);
            bundle.putInt(Constants.INTENT_DATA_2, Constants.TAB_TWO_RANK_TYPE);
            bundle.putInt(Constants.INTENT_DATA_3, 0);
            rankingListFragmnet.setArguments(bundle);
            mFragmentList.add(rankingListFragmnet);
        }
        return mFragmentList;
    }


    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radio_button1:
                //团员
                changeRadioButtonTextColor(true);
                Constants.TAB_TWO_RANK_TYPE = 0;
                EventBus.getDefault().post(new RankingSwitchEvent(Constants.TAB_TWO_RANK_TYPE));
                break;
            case R.id.radio_button2:
                //部门
                changeRadioButtonTextColor(false);
                Constants.TAB_TWO_RANK_TYPE = 1;
                EventBus.getDefault().post(new RankingSwitchEvent(Constants.TAB_TWO_RANK_TYPE));
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
