package com.leqian.bao.view.activity.resource;

import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.AdapterView;
import android.widget.AdapterView.OnItemClickListener;
import android.widget.RadioButton;
import android.widget.RadioGroup;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.leqian.bao.R;
import com.leqian.bao.common.adapter.VideoListAdapter;
import com.leqian.bao.common.base.BaseListToolBarActivity;
import com.leqian.bao.common.util.ToastUtil;
import com.leqian.bao.model.Constants;
import com.leqian.bao.model.eventbus.RankingSwitchEvent;
import com.leqian.bao.model.resource.VidoeListResp;
import com.nxin.base.widget.NXToolBarActivity;

import org.greenrobot.eventbus.EventBus;

import java.util.ArrayList;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by fcl on 19.4.19
 * desc:
 */
public class VideoListActivity extends BaseListToolBarActivity implements RadioGroup.OnCheckedChangeListener, OnItemClickListener {

    @BindView(R.id.bar_left)
    RelativeLayout bar_left;

    @BindView(R.id.bar_right)
    RelativeLayout bar_right;

    @BindView(R.id.bar_right_tv)
    TextView bar_right_tv;

    @BindView(R.id.radioGroup)
    RadioGroup radioGroup;

    @BindView(R.id.radio_button1)
    RadioButton radio_button1;

    @BindView(R.id.radio_button2)
    RadioButton radio_button2;

    ArrayList<VidoeListResp> mListData;
    VideoListAdapter mAdapter;

    @Override
    public int getToolBarLayoutId() {
        return R.layout.title_main_ranking;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_video_list;
    }

    @Override
    public void initView() {
        super.initView();
        bar_left.setVisibility(View.VISIBLE);
        bar_right.setVisibility(View.VISIBLE);
        bar_right_tv.setText("新增");
        getRefreshLayout().setEnableLoadMore(false);

        radio_button1.setText("团队标题");
        radio_button2.setText("公有标题");
        radioGroup.setOnCheckedChangeListener(this);

        listView.setOnItemClickListener(this);
    }

    @Override
    public void initViewData() {
        super.initViewData();
        mListData = new ArrayList<>();

        mListData = getData();
        mAdapter = new VideoListAdapter(mListData);
        listView.setAdapter(mAdapter);
    }

    private ArrayList<VidoeListResp> getData() {
        mListData.clear();
        for (int i = 0; i < 10; i++) {
            VidoeListResp resp = new VidoeListResp();
            mListData.add(resp);
        }
        return mListData;
    }

    @OnClick({R.id.bar_left, R.id.bar_right})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.bar_left:
                finish();
                break;
            case R.id.bar_right:
                ToastUtil.showToastShort("新增");
                break;
            default:
                break;
        }
    }

    @Override
    public void onCheckedChanged(RadioGroup group, int checkedId) {
        switch (checkedId) {
            case R.id.radio_button1:
                //团队标题
                changeRadioButtonTextColor(true);
                getRefreshLayout().autoRefresh();
                break;
            case R.id.radio_button2:
                //公有标题
                changeRadioButtonTextColor(false);
                getRefreshLayout().autoRefresh();
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
    public void onItemClick(AdapterView<?> parent, View view, int position, long id) {
        ToastUtil.showToastShort(String.valueOf(position));
    }
}
