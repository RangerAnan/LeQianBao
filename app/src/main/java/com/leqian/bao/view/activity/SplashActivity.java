package com.leqian.bao.view.activity;

import android.os.Handler;
import android.widget.ImageView;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.leqian.bao.R;
import com.leqian.bao.common.base.BaseActivity;
import com.leqian.bao.common.http.AccountHttp;
import com.leqian.bao.common.http.BaseHttp;
import com.leqian.bao.common.sp.ShareUtilMain;
import com.leqian.bao.model.network.account.UserStarResp;
import com.leqian.bao.view.activity.account.LoginActivity;
import com.leqian.bao.view.fragment.MainFourFragment;
import com.nxin.base.model.http.callback.ModelCallBack;
import com.nxin.base.model.network.glide.GlideUtils;
import com.nxin.base.widget.NXActivity;

import butterknife.BindView;
import okhttp3.Call;

/**
 * Created by fcl on 19.4.10
 * desc:
 */
public class SplashActivity extends BaseActivity {

    @BindView(R.id.iv_head)
    ImageView iv_head;

    @BindView(R.id.tv_name)
    TextView tv_name;

    @BindView(R.id.tv_desc)
    TextView tv_desc;
    private Handler handler;
    private Boolean loginState;

    @Override
    public int getLayoutId() {
        return R.layout.activity_splash;
    }

    @Override
    public void initViewData() {
        super.initViewData();
        loginState = ShareUtilMain.getBoolean(ShareUtilMain.LOGIN_STATE, false);

        handler = new Handler();
        if (!loginState) {
            intentPage(loginState);
        } else {
            requestUserStar();
        }
    }

    private void requestUserStar() {
        AccountHttp.getStar(new ModelCallBack<UserStarResp>() {
            @Override
            public void onResponse(UserStarResp response, int id) {
                if (response.getCode() != 1) {
                    intentPage(loginState);
                    return;
                }
                UserStarResp.DataBean data = response.getData();
                tv_name.setText(data.getName());
                tv_desc.setText("总点击量" + data.getCount() + "次");
                GlideUtils.setCircleDrawableRequest(Glide.with(SplashActivity.this),
                        BaseHttp.IMAGE_HOST + response.getData().getHeadpic(), R.mipmap.me_default_icon).into(iv_head);
                intentPage(loginState);
            }

            @Override
            public void onError(Call call, Exception e, int id) {
                intentPage(loginState);
            }
        });
    }

    public void intentPage(final boolean loginState) {
        handler.postDelayed(new Runnable() {
            @Override
            public void run() {
                intent2Activity(loginState ? MainActivity.class : LoginActivity.class);
                finish();
            }
        }, 3000);
    }

}
