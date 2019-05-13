package com.leqian.bao.view.activity;

import android.app.Activity;
import android.content.Intent;
import android.support.annotation.NonNull;
import android.support.v4.content.ContextCompat;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.leqian.bao.R;
import com.leqian.bao.common.adapter.FragmentAdapter;
import com.leqian.bao.common.base.BaseActivity;
import com.leqian.bao.common.http.AccountHttp;
import com.leqian.bao.common.http.AppInfoHttp;
import com.leqian.bao.common.sp.ShareUtilMain;
import com.leqian.bao.common.sp.ShareUtilUser;
import com.leqian.bao.common.util.DeviceUtil;
import com.leqian.bao.common.util.ImageUtil;
import com.leqian.bao.common.util.ToastUtil;
import com.leqian.bao.model.network.appinfo.CheckAppVersionResp;
import com.leqian.bao.model.network.base.BaseModelResp;
import com.leqian.bao.model.constant.Constants;
import com.leqian.bao.model.network.account.UserInfoResp;
import com.leqian.bao.model.bll.LoginBLL;
import com.leqian.bao.model.code.RequestCode;
import com.leqian.bao.view.dialog.listDilog.JoinTeamDialog;
import com.leqian.bao.view.dialog.updat.VersionUpgradeDialog;
import com.leqian.bao.view.fragment.MainFourFragment;
import com.leqian.bao.view.viewpager.TabContentViewPager;
import com.leqian.bao.widget.VersionUpdateService;
import com.nxin.base.model.http.callback.ModelCallBack;
import com.nxin.base.utils.Logger;
import com.nxin.base.utils.ProHelper;
import com.nxin.base.view.dialog.MaterialDialogUtil;

import java.util.ArrayList;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

public class MainActivity extends BaseActivity {

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

    @BindView(R.id.rl3)
    RelativeLayout rl3;

    FragmentAdapter vpAdapter;

    ArrayList<ImageView> tabImgList = new ArrayList<>();
    ArrayList<TextView> tabTextList = new ArrayList<>();
    List<Integer> unSelectImgList = new ArrayList<>();
    List<Integer> selectImgList = new ArrayList<>();

    private UserInfoResp userInfo;

    private int currentPosition = 0;

    private boolean isLeader;

    @Override
    public int getLayoutId() {
        return R.layout.activity_main;
    }

    @Override
    public void initView() {
        super.initView();
        userInfo = LoginBLL.getInstance().getUserInfo();
        isLeader = ShareUtilUser.getString(ShareUtilUser.ORG_TYPE, "").equals("1");

        rl3.setVisibility(isLeader ? View.VISIBLE : View.GONE);
        tabImgList.clear();
        tabImgList.add(iv_tab1);
        tabImgList.add(iv_tab2);
        if (isLeader) {
            tabImgList.add(iv_tab3);
        }
        tabImgList.add(iv_tab4);

        tabTextList.clear();
        tabTextList.add(tv_tab1);
        tabTextList.add(tv_tab2);
        if (isLeader) {
            tabTextList.add(tv_tab3);
        }
        tabTextList.add(tv_tab4);

        unSelectImgList.add(R.drawable.tab_msg1);
        unSelectImgList.add(R.drawable.tab_circle1);
        if (isLeader) {
            unSelectImgList.add(R.drawable.tab_service1);
        }
        unSelectImgList.add(R.drawable.tab_me1);

        selectImgList.add(R.drawable.tab_msg2);
        selectImgList.add(R.drawable.tab_circle2);
        if (isLeader) {
            selectImgList.add(R.drawable.tab_service2);
        }
        selectImgList.add(R.drawable.tab_me2);

        setTabStyleChange(currentPosition);

        //set viewpager
        vpAdapter = new FragmentAdapter(getSupportFragmentManager(), isLeader);
        viewpager.setOffscreenPageLimit(vpAdapter.getCount() - 1);
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
                viewpager.setCurrentItem(0, false);
                checkAccountState();
                break;
            case R.id.rl2:
                currentPosition = 1;
                setTabStyleChange(1);
                viewpager.setCurrentItem(1, false);
                break;
            case R.id.rl3:
                currentPosition = isLeader ? 2 : 1;
                setTabStyleChange(isLeader ? 2 : 1);
                viewpager.setCurrentItem(isLeader ? 2 : 1, false);
                break;
            case R.id.rl4:
                currentPosition = isLeader ? 3 : 2;
                setTabStyleChange(isLeader ? 3 : 2);
                viewpager.setCurrentItem(isLeader ? 3 : 2, false);
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
        if (isLeader) {
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
                    if (currentPosition == (isLeader ? 3 : 2)) {
                        MainFourFragment item = (MainFourFragment) vpAdapter.getItem(currentPosition);
                        item.uploadImage(Constants.SAVE_IMAGE_TEMP_PATH + ImageUtil.CROP_TEMP_IMAGE_NAME);
                    }
                    break;
                default:
                    break;
            }
        }
    }

    @Override
    public boolean isSetStatusBar() {
        return true;
    }

    @Override
    public void initViewData() {
        super.initViewData();

        checkAppVersion();
    }

    private void checkAppVersion() {
        AppInfoHttp.checkAppVersion(DeviceUtil.getVersionName(), new ModelCallBack<CheckAppVersionResp>() {
            @Override
            public void onResponse(CheckAppVersionResp response, int id) {
                if (response.getCode() != 1) {
                    ToastUtil.showToastShort(response.getMsg());
                    return;
                }
                String versionNew = response.getData().getVersion();
                String versionName = DeviceUtil.getVersionName();
                try {
                    String svNew = versionNew.replace(".", "");
                    String svOld = versionName.replace(".", "");
                    if (Integer.parseInt(svNew) > Integer.parseInt(svOld)) {
                        showVersionDialog(response);
                    }
                } catch (Exception e) {
                    e.printStackTrace();
                }
            }
        });
    }

    /**
     * 展示升级对话框
     */
    private void showVersionDialog(final CheckAppVersionResp model) {

        final VersionUpgradeDialog dialog = new VersionUpgradeDialog(mContext, model.getData().getMsgX(), "暂不更新");
        dialog.setUpgradeButton(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                Intent serviceIntent = new Intent(mContext, VersionUpdateService.class);
                serviceIntent.putExtra("APK_URL", model.getData().getAddress());
                serviceIntent.putExtra("APK_VERSION", model.getData().getVersion());
                startService(serviceIntent);
            }
        }).setCancelButton(new View.OnClickListener() {

            @Override
            public void onClick(View v) {
                dialog.dismiss();
            }
        }).show();
        dialog.setCancelable(false);
        dialog.setForce(model.getData().getForceUpdate() == 1);
    }
}
