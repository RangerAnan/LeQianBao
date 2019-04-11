package com.leqian.bao.view.activity;

import android.os.Bundle;
import android.os.Handler;

import com.leqian.bao.R;
import com.leqian.bao.common.sp.ShareUtilMain;
import com.qsmaxmin.qsbase.mvp.QsABActivity;
import com.qsmaxmin.qsbase.mvp.QsActivity;

/**
 * Created by fcl on 19.4.10
 * desc:
 */
public class SplashActivity extends QsActivity {

    @Override
    public int layoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initData(Bundle bundle) {

        final Boolean loginState = ShareUtilMain.getBoolean(ShareUtilMain.LOGIN_STATE, false);

        Handler handler = new Handler();
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                intent2Activity(loginState ? MainActivity.class : LoginActivity.class);
                finish();
            }
        }, 3000);
    }
}
