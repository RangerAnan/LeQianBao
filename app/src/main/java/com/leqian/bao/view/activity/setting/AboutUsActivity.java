package com.leqian.bao.view.activity.setting;

import android.text.TextUtils;
import android.view.View;
import android.widget.TextView;

import com.leqian.bao.R;
import com.leqian.bao.common.base.BaseToolBarActivity;
import com.leqian.bao.common.util.ToastUtil;
import com.nxin.base.utils.system.ClipboardUtils;
import com.nxin.base.widget.NXToolBarActivity;

import butterknife.BindView;
import butterknife.OnClick;

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
