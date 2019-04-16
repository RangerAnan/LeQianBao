package com.leqian.bao.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.support.v7.app.AppCompatActivity;
import android.os.Bundle;
import android.view.View;
import android.widget.Button;
import android.widget.ImageView;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.leqian.bao.R;
import com.leqian.bao.common.adapter.FragmentAdapter;
import com.leqian.bao.common.http.AccountHttp;
import com.leqian.bao.common.util.ImageUtil;
import com.leqian.bao.common.util.ToastUtil;
import com.leqian.bao.model.BaseModelResp;
import com.leqian.bao.model.Constants;
import com.leqian.bao.model.account.LoginResp;
import com.leqian.bao.model.bll.LoginBLL;
import com.leqian.bao.model.code.RequestCode;
import com.leqian.bao.view.dialog.listDilog.JoinTeamDialog;
import com.leqian.bao.view.fragment.MainFourFragment;
import com.leqian.bao.view.fragment.ViewpagerFragment;
import com.leqian.bao.view.viewpager.TabContentViewPager;
import com.nxin.base.model.http.callback.ModelCallBack;
import com.nxin.base.utils.JsonUtils;
import com.nxin.base.utils.Logger;
import com.nxin.base.utils.ProHelper;
import com.nxin.base.view.dialog.MaterialDialogUtil;
import com.nxin.base.widget.NXActivity;
import com.nxin.base.widget.NXToolBarActivity;
import com.nxin.base.widget.statusbar.StatusBarUtil;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends NXActivity {

    @BindView(R.id.iv_tab1)
    ImageView iv_tab1;
    @BindView(R.id.iv_tab2)
    ImageView iv_tab2;
    @BindView(R.id.iv_tab3)
    ImageView iv_tab3;
    @BindView(R.id.iv_tab4)
    ImageView iv_tab4;

    @BindView(R.id.tv_tab1)
    TextView tv_tab1;
    @BindView(R.id.tv_tab2)
    TextView tv_tab2;
    @BindView(R.id.tv_tab3)
    TextView tv_tab3;
    @BindView(R.id.tv_tab4)
    TextView tv_tab4;

    @BindView(R.id.viewpager)
    TabContentViewPager viewpager;
    FragmentAdapter vpAdapter;

    ArrayList<ImageView> tabImgList = new ArrayList<>();
    ArrayList<TextView> tabTextList = new ArrayList<>();
    List<Integer> unSelectImgList = new ArrayList<>();
    List<Integer> selectImgList = new ArrayList<>();

    private LoginResp userInfo;

    private int currentPosition = 0;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        super.initView();
        userInfo = LoginBLL.getInstance().getUserInfo();

        tabImgList.clear();
        tabImgList.add(iv_tab1);
        tabImgList.add(iv_tab2);
        tabImgList.add(iv_tab3);
        tabImgList.add(iv_tab4);

        tabTextList.clear();
        tabTextList.add(tv_tab1);
        tabTextList.add(tv_tab2);
        tabTextList.add(tv_tab3);
        tabTextList.add(tv_tab4);

        unSelectImgList.add(R.drawable.tab_msg1);
        unSelectImgList.add(R.drawable.tab_circle1);
        unSelectImgList.add(R.drawable.tab_service1);
        unSelectImgList.add(R.drawable.tab_me1);

        selectImgList.add(R.drawable.tab_msg2);
        selectImgList.add(R.drawable.tab_circle2);
        selectImgList.add(R.drawable.tab_service2);
        selectImgList.add(R.drawable.tab_me2);

        setTabStyleChange(currentPosition);

        //set viewpager
        viewpager.setOffscreenPageLimit(3);
        vpAdapter = new FragmentAdapter(getSupportFragmentManager());
        viewpager.setAdapter(vpAdapter);
        viewpager.setPageTransformer(false, null);

        //检查账户
        checkAccountState();
    }


    public void setTabStyleChange(int position) {
        for (int i = 0; i < tabImgList.size(); i++) {
            int drawable = position == i ? selectImgList.get(i) : unSelectImgList.get(i);
            tabImgList.get(i).setImageResource(drawable);
        }

        for (int i = 0; i < tabTextList.size(); i++) {
            int color = position == i ? ContextCompat.getColor(mContext, R.color.theme) : ContextCompat.getColor(mContext, R.color.black);
            tabTextList.get(i).setTextColor(color);
        }
    }


    @OnClick({R.id.rl1, R.id.rl2, R.id.rl3, R.id.rl4})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.rl1:
                currentPosition = 0;
                setTabStyleChange(0);
                viewpager.setCurrentItem(Constants.TAB_FIRST, false);
                checkAccountState();
                break;
            case R.id.rl2:
                currentPosition = 1;
                setTabStyleChange(1);
                viewpager.setCurrentItem(Constants.TAB_MONEY, false);
                break;
            case R.id.rl3:
                currentPosition = 2;
                setTabStyleChange(2);
                viewpager.setCurrentItem(Constants.TAB_FIND, false);
                break;
            case R.id.rl4:
                currentPosition = 3;
                setTabStyleChange(3);
                viewpager.setCurrentItem(Constants.TAB_MINE, false);
                checkAccountState();
                break;
            default:
                break;
        }
    }


    /**
     * 检查用户状态
     */
    private void checkAccountState() {
        if (userInfo.getType().equals("1")) {
            Logger.i(initTag() + " 团长号");
            return;
        }

        AccountHttp.checkAccountState(new ModelCallBack<BaseModelResp>() {
            @Override
            public void onResponse(BaseModelResp response, int id) {
                if (response.getCode() == 1) {
                    ToastUtil.showToastLong(getString(R.string.account_is_stop));
                    LoginBLL.getInstance().exitAccount(mContext);
                } else if (response.getCode() == 2) {
                    //弹出邀请码
                    JoinTeamDialog joinTeamDialog = new JoinTeamDialog(mContext, userInfo);
                    joinTeamDialog.show();
                } else if (response.getCode() == 0) {
                    MaterialDialogUtil.showAlert(mContext, response.getMsg() + "，" + getString(R.string.repeat_request),
                            R.string.repeat_again, R.string.exit_app,
                            new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    checkAccountState();
                                }
                            },
                            new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    ProHelper.getScreenHelper().popAllActivityExceptNamed(null);
                                }
                            });
                }
            }
        });
    }

    @Override
    protected void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RequestCode.CHOICE_MEDIA:
                    //取得裁剪后的图片
                    if (currentPosition == 3) {
                        MainFourFragment item = (MainFourFragment) vpAdapter.getItem(currentPosition);
                        item.uploadImage(Constants.SAVE_IMAGE_TEMP_PATH + ImageUtil.CROP_TEMP_IMAGE_NAME);
                    }
                    break;
                default:
                    break;
            }
        }
    }
}
