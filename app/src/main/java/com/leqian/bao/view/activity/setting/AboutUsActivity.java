package com.leqian.bao.view.activity.setting;

import android.widget.TextView;

import com.leqian.bao.R;
import com.leqian.bao.common.base.BaseToolBarActivity;
import com.nxin.base.widget.NXToolBarActivity;

import butterknife.BindView;

/**
 * Created by fcl on 19.4.19
 * desc:
 */
public class AboutUsActivity extends BaseToolBarActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;

    @Override
    public int getLayoutId() {
        return R.layout.activity_about_us;
    }

    @Override
    public int getToolBarLayoutId() {
        return R.layout.title_common;
    }

    @Override
    public void initView() {
        super.initView();
        tv_title.setText("关于我们");
    }
}
