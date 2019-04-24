package com.leqian.bao.common.base;

import com.nxin.base.widget.NXActivity;
import com.umeng.analytics.MobclickAgent;

/**
 * Created by fcl on 19.4.24
 * desc:
 */
public class BaseActivity extends NXActivity {

    @Override
    public void onResume() {
        super.onResume();
        MobclickAgent.onResume(this);
    }

    @Override
    public void onPause() {
        super.onPause();
        MobclickAgent.onPause(this);
    }
}
