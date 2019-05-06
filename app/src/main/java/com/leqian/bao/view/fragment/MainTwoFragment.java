package com.leqian.bao.view.fragment;

import android.os.Bundle;
import android.support.design.widget.TabLayout;
import android.support.v4.content.ContextCompat;
import android.support.v4.view.ViewPager;
import android.text.SpannableString;
import android.text.Spanned;
import android.text.style.AbsoluteSizeSpan;
import android.text.style.StyleSpan;
import android.view.View;
import android.widget.ImageView;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.leqian.bao.R;
import com.leqian.bao.common.adapter.RankingFragmentAdapter;
import com.leqian.bao.common.http.AccountHttp;
import com.leqian.bao.common.http.BaseHttp;
import com.leqian.bao.common.http.StatisticsHttp;
import com.leqian.bao.common.sp.ShareUtilUser;
import com.leqian.bao.common.util.DeviceUtil;
import com.leqian.bao.common.util.ToastUtil;
import com.leqian.bao.model.constant.Constants;
import com.leqian.bao.model.eventbus.ClickTotalEvent;
import com.leqian.bao.model.eventbus.RankingSwitchEvent;
import com.leqian.bao.model.network.statistics.TeamClickDetailResp;
import com.leqian.bao.model.network.team.TeamInfoResp;
import com.leqian.bao.view.fragment.ranking.RankingListFragmnet;
import com.nxin.base.model.http.callback.ModelCallBack;
import com.nxin.base.model.network.glide.GlideUtils;
import com.nxin.base.widget.NXFragment;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

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

    @BindView(R.id.iv_image)
    ImageView iv_image;

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
        AccountHttp.getTeamManage(ShareUtilUser.getString(ShareUtilUser.UID, ""), new ModelCallBack<TeamInfoResp>() {
            @Override
            public void onResponse(TeamInfoResp response, int id) {
                if (response.getCode() != 1) {
                    ToastUtil.showToastShort(response.getMsg());
                    return;
                }
                TeamInfoResp.DataBean model = response.getData();

                SpannableString teamSpan = new SpannableString(model.getName() + getString(R.string.user_team) + "（" + model.getPopulation() + "人）");
                teamSpan.setSpan(new AbsoluteSizeSpan((int) DeviceUtil.sp2px(14)), (model.getName() + getString(R.string.user_team)).length(),
                        teamSpan.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                teamSpan.setSpan(new StyleSpan(android.graphics.Typeface.NORMAL), (model.getName() + getString(R.string.user_team)).length(),
                        teamSpan.length(), Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
                tv_title.setText(teamSpan);

                tv_desc.setText(response.getData().getAnnouncement());
                tv_name.setText(model.getName());
                GlideUtils.setCircleDrawableRequest(Glide.with(mContext), BaseHttp.IMAGE_HOST + model.getHeadpic(), R.mipmap.me_default_icon).into(iv_image);
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

    @Override
    public boolean isOpenEventBus() {
        return true;
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void clickTotalEvent(ClickTotalEvent event) {
        tv_count_today.setText("今日总计费：" + event.todayTotalCount + "次");
    }
}
