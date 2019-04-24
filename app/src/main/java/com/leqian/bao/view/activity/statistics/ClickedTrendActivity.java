package com.leqian.bao.view.activity.statistics;

import android.os.Bundle;
import android.view.View;
import android.widget.TextView;

import com.leqian.bao.R;
import com.leqian.bao.common.base.BaseToolBarActivity;
import com.leqian.bao.model.constant.Constants;
import com.nxin.base.widget.NXToolBarActivity;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by fcl on 19.4.20
 * desc:
 */
public class ClickedTrendActivity extends BaseToolBarActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;

    private String uid;

    @Override
    public int getToolBarLayoutId() {
        return R.layout.title_common;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_click_trend;
    }

    @Override
    public void initView() {
        super.initView();
        Bundle extras = getIntent().getExtras();
        uid = extras.getString(Constants.INTENT_DATA_1);
        String name = extras.getString(Constants.INTENT_DATA_2);

        tv_title.setText(name + "点击明细趋势");
    }

    @OnClick({R.id.bar_left})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.bar_left:
                finish();
                break;
            default:
                break;
        }
    }
}
