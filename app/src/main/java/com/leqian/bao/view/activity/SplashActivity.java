package com.leqian.bao.view.activity;

import android.os.Handler;

import com.leqian.bao.R;
import com.leqian.bao.common.sp.ShareUtilMain;
import com.leqian.bao.view.activity.account.LoginActivity;
import com.nxin.base.widget.NXActivity;

/**
 * Created by fcl on 19.4.10
 * desc:
 */
public class SplashActivity extends NXActivity {

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initViewData() {
        super.initViewData();
        final Boolean loginState = ShareUtilMain.getBoolean(ShareUtilMain.LOGIN_STATE, false);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                intent2Activity(loginState ? MainActivity.class : MainActivity.class);
                finish();
            }
        }, 3000);
    }


}
