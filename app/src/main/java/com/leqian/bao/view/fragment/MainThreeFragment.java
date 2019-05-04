package com.leqian.bao.view.fragment;

import android.support.v4.content.ContextCompat;
import android.widget.ListView;
import android.widget.RadioButton;
import android.widget.RadioGroup;

import com.leqian.bao.R;
import com.leqian.bao.common.adapter.RankingAdapter;
import com.leqian.bao.common.base.BaseListFragment;
import com.leqian.bao.common.http.StatisticsHttp;
import com.leqian.bao.model.constant.Constants;
import com.leqian.bao.model.eventbus.RankingSwitchEvent;
import com.leqian.bao.model.network.statistics.UserRankingResp;
import com.leqian.bao.model.ui.RankingUIModel;
import com.nxin.base.model.http.callback.ModelCallBack;
import com.nxin.base.utils.Logger;
import com.scwang.smartrefresh.layout.SmartRefreshLayout;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;

/**
 * Created by fcl on 19.4.12
 * desc:
 */
public class MainThreeFragment extends BaseListFragment implements RadioGroup.OnCheckedChangeListener {

    @BindView(R.id.radio_button1)
    RadioButton radio_button1;

    @BindView(R.id.radio_button2)
    RadioButton radio_button2;

    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;

    ArrayList<UserRankingResp.DataBean> mListData = new ArrayList<>();
    private RankingAdapter mAdapter;

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_three;
    }

    @Override
    public void initView() {
        super.initView();

        mListData = getData();

        mAdapter = new RankingAdapter(mListData, this);
        listView.setAdapter(mAdapter);

        getRefreshLayout().setEnableLoadMore(false);

        radio_button1.setText("团队排行");
        radio_button2.setText("个人排行");
    }

    private ArrayList<UserRankingResp.DataBean> getData() {
        for (int i = 0; i < 20; i++) {
            UserRankingResp.DataBean rankingUIModel = new UserRankingResp.DataBean();
            rankingUIModel.setName("title" + i);
            mListData.add(rankingUIModel);
        }
        return mListData;
    }

    @Override
    public void initData() {
        super.initData();
//        requestRanking();
    }

    private void requestRanking() {
        StatisticsHttp.getUserRanking(new ModelCallBack<UserRankingResp>() {
            @Override
            public void onResponse(UserRankingResp response, int id) {

            }
        });
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radio_button1:
                //团队
                changeRadioButtonTextColor(true);
                Constants.RANK_TYPE = 3;
                EventBus.getDefault().post(new RankingSwitchEvent(Constants.RANK_TYPE));
                break;
            case R.id.radio_button2:
                //个人
                changeRadioButtonTextColor(false);
                Constants.RANK_TYPE = 4;
                EventBus.getDefault().post(new RankingSwitchEvent(Constants.RANK_TYPE));
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
