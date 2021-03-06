package com.leqian.bao.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.support.annotation.NonNull;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.afollestad.materialdialogs.DialogAction;
import com.afollestad.materialdialogs.MaterialDialog;
import com.bumptech.glide.Glide;
import com.leqian.bao.R;
import com.leqian.bao.common.base.BaseListFragment;
import com.leqian.bao.common.http.AccountHttp;
import com.leqian.bao.common.http.StatisticsHttp;
import com.leqian.bao.common.http.UploadHttp;
import com.leqian.bao.common.permissions.PermissionsResultAction;
import com.leqian.bao.common.permissions.PermissonsUtil;
import com.leqian.bao.common.sp.ShareUtilUser;
import com.leqian.bao.common.util.DeviceUtil;
import com.leqian.bao.common.util.ImageUtil;
import com.leqian.bao.common.util.ToastUtil;
import com.leqian.bao.common.http.BaseHttp;
import com.leqian.bao.model.constant.Constants;
import com.leqian.bao.model.network.account.UploadImageResp;
import com.leqian.bao.model.network.account.UserInfoResp;
import com.leqian.bao.model.bll.LoginBLL;
import com.leqian.bao.model.code.RequestCode;
import com.leqian.bao.model.network.statistics.UserCountResp;
import com.leqian.bao.model.ui.CommonUIModel;
import com.leqian.bao.view.activity.account.ModifyLoginPsdActivity;
import com.leqian.bao.view.activity.setting.AboutUsActivity;
import com.leqian.bao.view.activity.setting.ClickDetailedActivity;
import com.leqian.bao.view.activity.setting.ContactUsActivity;
import com.leqian.bao.view.activity.setting.TeamManagerActivity;
import com.leqian.bao.view.dialog.listDilog.BottomListDialog;
import com.leqian.bao.view.imageview.CircleImageView;
import com.nxin.base.model.http.callback.ModelCallBack;
import com.nxin.base.model.network.glide.GlideUtils;
import com.nxin.base.utils.JsonUtils;
import com.nxin.base.utils.Logger;
import com.nxin.base.view.dialog.MaterialDialogUtil;
import com.scwang.smartrefresh.layout.api.RefreshLayout;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by fcl on 19.4.12
 * desc:我的模块
 */
public class MainFourFragment extends BaseListFragment implements BottomListDialog.IBottomListItemListener {

    @BindView(R.id.me_user_photo)
    CircleImageView me_user_photo;

    @BindView(R.id.tv_name)
    TextView tv_name;

    @BindView(R.id.ll_zfb)
    LinearLayout ll_zfb;

    @BindView(R.id.tv_zfb)
    TextView tv_zfb;

    @BindView(R.id.tv_today_charge)
    TextView tv_today_charge;

    @BindView(R.id.tv_yesterday_charge)
    TextView tv_yesterday_charge;

    @BindView(R.id.tv_month_charge)
    TextView tv_month_charge;

    @BindView(R.id.content)
    LinearLayout content;


    ArrayList<CommonUIModel> uiModels = new ArrayList<>();

    private BottomListDialog listDialog;


    @Override
    protected boolean isDelayLoad() {
        return true;
    }

    @Override
    public int getLayoutId() {
        return R.layout.fragment_main_four;
    }

    @Override
    public void initView() {
        super.initView();
        //set user info

        //set content
        content.removeAllViews();
        uiModels.clear();
        String[] stringArray = getResources().getStringArray(R.array.mine_ui_item);
        int[] iconArray = {R.mipmap.team_management, R.mipmap.my_user_detailed, R.mipmap.my_user_password,
                R.mipmap.my_user_message_n, R.mipmap.my_user_about_us, R.mipmap.my_user_exit};

        boolean isTeamer = ShareUtilUser.getString(ShareUtilUser.ORG_TYPE, "").equals("1");

        for (int i = isTeamer ? 0 : 1; i < stringArray.length; i++) {
            CommonUIModel commonUIModel = new CommonUIModel();
            commonUIModel.name = stringArray[i];
            commonUIModel.code = i;
            commonUIModel.img = iconArray[i];
            uiModels.add(commonUIModel);
        }

        for (int i = 0; i < uiModels.size(); i++) {
            View inflate = View.inflate(mContext, R.layout.item_fragment_me_text, null);
            LinearLayout layout1 = inflate.findViewById(R.id.layout1);
            TextView tvTitle = inflate.findViewById(R.id.tv_title);
            ImageView iv_image = inflate.findViewById(R.id.iv_image);

            CommonUIModel commonUIModel = uiModels.get(i);
            tvTitle.setText(commonUIModel.name);
            iv_image.setImageResource(commonUIModel.img);
            layout1.setOnClickListener(new ItemClickListener(commonUIModel));
            content.addView(inflate);
        }

        getRefreshLayout().setEnableLoadMore(false);
    }

    @Override
    public void initData() {
        super.initData();
        requestUserInfo();
    }


    @Override
    public void setUserVisibleHint(boolean isVisibleToUser) {
        super.setUserVisibleHint(isVisibleToUser);
        if (getUserVisibleHint()) {
            getRefreshLayout().autoRefresh();
        }
    }


    @Override
    public void onRefresh(@NonNull RefreshLayout refreshLayout) {
        super.onRefresh(refreshLayout);
        requestUserCount();
    }

    private void requestUserInfo() {
        AccountHttp.getUserInfo(new ModelCallBack<UserInfoResp>() {
            @Override
            public void onResponse(UserInfoResp response, int id) {
                if (response == null || response.getData() == null) {
                    Logger.i(initTag() + " user info is null ");
                    return;
                }
                ShareUtilUser.setString(ShareUtilUser.USER_INFO, JsonUtils.object2Json(response));
                GlideUtils.setDrawableRequest(Glide.with(MainFourFragment.this),
                        BaseHttp.IMAGE_HOST + response.getData().getHeadpic(), R.mipmap.me_default_icon).into(me_user_photo);

                tv_name.setText(response.getData().getName());
                tv_zfb.setText(response.getData().getZfb());

            }
        });
    }


    private void requestUserCount() {
        StatisticsHttp.getUserCount(new ModelCallBack<UserCountResp>() {
            @Override
            public void onResponse(UserCountResp response, int id) {
                if (response.getData() == null) {
                    return;
                }
                tv_today_charge.setText(String.valueOf(response.getData().getToday()));
                tv_yesterday_charge.setText(String.valueOf(response.getData().getYesterday()));
                tv_month_charge.setText(String.valueOf(response.getData().getMonth()));
            }
        });
    }

    @OnClick({R.id.me_user_photo, R.id.ll_zfb})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.me_user_photo:
                String[] stringArray = getResources().getStringArray(R.array.dialog_list_item);
                ArrayList<String> nameList = new ArrayList<>();
                Collections.addAll(nameList, stringArray);

                listDialog = new BottomListDialog(mContext, R.style.PhotoSelectDialog, nameList);
                listDialog.setOnBottomListItemListener(this);
                listDialog.show();
                break;
            case R.id.ll_zfb:
//                ToastUtil.showToastShort("支付宝");
                break;
            default:
                break;
        }
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RequestCode.CHOICE_CMARE: {
                    File temp = new File(Constants.PHOTOFILEPATH);
                    ImageUtil.cropImage(mContext, Uri.fromFile(temp));
                }
                break;
                case RequestCode.CHOICE_PHOTO: {
                    ImageUtil.cropImage(mContext, data.getData());
                }
                break;
                case RequestCode.CHOICE_MEDIA:
                    //取得裁剪后的图片
                    Logger.i(initTag() + "---uploadImage--CHOICE_MEDIA");
                    uploadImage(Constants.SAVE_IMAGE_TEMP_PATH + ImageUtil.CROP_TEMP_IMAGE_NAME);
                    break;
                default:
                    break;
            }
        }
    }

    /**
     * 上传图片
     *
     * @param path
     */
    public void uploadImage(String path) {
        File file = new File(path);
        if (!file.exists() || file.length() == 0) {
            Logger.i(initTag() + "----uploadImage---file is null");
            return;
        }
        UploadHttp.uploadImage(file, new ModelCallBack<UploadImageResp>() {
            @Override
            public void onResponse(UploadImageResp response, int id) {
                if (response.getCode() != 1) {
                    ToastUtil.showToastShort(response.getMsg());
                    return;
                }
                String headIcon = BaseHttp.IMAGE_HOST + response.getHeadpic();
                GlideUtils.setDrawableRequest(Glide.with(MainFourFragment.this), headIcon, R.mipmap.me_default_icon).into(me_user_photo);

            }
        });
    }

    @Override
    public void onBottomListItemListener(int position) {
        if (position == 0) {
            requestCameraPermission(0);
        } else if (position == 1) {
            requestCameraPermission(1);
        }
        if (listDialog != null && listDialog.isShowing()) {
            listDialog.dismiss();
        }
    }

    private void requestCameraPermission(final int type) {
        PermissonsUtil.getFragmentCameraPermission(this, new PermissionsResultAction() {
            @Override
            public void onGranted() {
                if (type == 0) {
                    DeviceUtil.openCamera(MainFourFragment.this);
                } else if (type == 1) {
                    DeviceUtil.openPhotos(MainFourFragment.this);
                }
            }

            @Override
            public void onDenied(String permission) {
                ToastUtil.showToastLong(getString(R.string.Permissons_Not_Camra));
            }
        });
    }

    private class ItemClickListener implements View.OnClickListener {
        CommonUIModel commonUIModel;

        public ItemClickListener(CommonUIModel commonUIModel) {
            this.commonUIModel = commonUIModel;
        }

        @Override
        public void onClick(View v) {
            switch (commonUIModel.code) {
                case 0:
                    intent2Activity(TeamManagerActivity.class);
                    break;
                case 1:
                    intent2Activity(ClickDetailedActivity.class);
                    break;
                case 2:
                    intent2Activity(ModifyLoginPsdActivity.class);
                    break;
                case 3:
                    intent2Activity(ContactUsActivity.class);
                    break;
                case 4:
                    intent2Activity(AboutUsActivity.class);
                    break;
                case 5:
                    MaterialDialogUtil.showAlert(mContext, "确定退出登录吗？", "确定", "取消",
                            new MaterialDialog.SingleButtonCallback() {
                                @Override
                                public void onClick(@NonNull MaterialDialog dialog, @NonNull DialogAction which) {
                                    LoginBLL.getInstance().exitAccount(mContext);
                                }
                            });
                    break;
                default:
                    break;
            }
        }
    }
}
