package com.leqian.bao.view.fragment;

import android.app.Activity;
import android.content.Intent;
import android.net.Uri;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.LinearLayout;
import android.widget.TextView;

import com.leqian.bao.R;
import com.leqian.bao.common.permissions.PermissionsResultAction;
import com.leqian.bao.common.permissions.PermissonsUtil;
import com.leqian.bao.common.util.DeviceUtil;
import com.leqian.bao.common.util.ImageUtil;
import com.leqian.bao.common.util.ToastUtil;
import com.leqian.bao.model.Constants;
import com.leqian.bao.model.code.RequestCode;
import com.leqian.bao.model.ui.CommonUIModel;
import com.leqian.bao.view.activity.account.ModifyLoginPsdActivity;
import com.leqian.bao.view.dialog.listDilog.BottomListDialog;
import com.leqian.bao.view.imageview.CircleImageView;
import com.nxin.base.utils.Logger;
import com.nxin.base.utils.ProHelper;

import java.io.File;
import java.lang.reflect.Array;
import java.util.ArrayList;
import java.util.Collections;
import java.util.List;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by fcl on 19.4.12
 * desc:我的模块
 */
public class MainFourFragment extends ViewpagerFragment implements BottomListDialog.IBottomListItemListener {

    @BindView(R.id.me_user_photo)
    CircleImageView me_user_photo;

    @BindView(R.id.tv_name)
    TextView tv_name;

    @BindView(R.id.ll_zfb)
    LinearLayout ll_zfb;

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
        for (int i = 0; i < stringArray.length; i++) {
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
                ToastUtil.showToastShort("支付宝");
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
                    Uri cameraUri = Uri.fromFile(temp);
                    if (cameraUri != null) {
                        String path = ImageUtil.getImageAbsolutePath(mContext, cameraUri);
                        if (TextUtils.isEmpty(path)) {
                            Logger.e(initTag() + "   图片为空");
                            return;
                        }
                        //TODO 上报服务器
                        ToastUtil.showToastShort(path);
                    }
                }
                break;
                case RequestCode.CHOICE_PHOTO: {
                    File temp = new File(Constants.PHOTOFILEPATH);
                    Uri cameraUri = Uri.fromFile(temp);
                    if (cameraUri != null) {
                        String path = ImageUtil.getImageAbsolutePath(mContext, cameraUri);
                        if (TextUtils.isEmpty(path)) {
                            Logger.e(initTag() + "   图片为空");
                            return;
                        }
                        //TODO 上报服务器
                        ToastUtil.showToastShort(path);
                    }
                }
                break;
                default:
                    break;
            }
        }
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
        PermissonsUtil.getCamraPermisson(ProHelper.getScreenHelper().currentActivity(), new PermissionsResultAction() {
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
                    ToastUtil.showToastShort("团队管理");
                    break;
                case 1:
                    ToastUtil.showToastShort("点击明细");
                    break;
                case 2:
                    startActivity(new Intent(mContext, ModifyLoginPsdActivity.class));
                    break;
                case 3:
                    ToastUtil.showToastShort("联系客服");
                    break;
                case 4:
                    ToastUtil.showToastShort("关于我们");
                    break;
                case 5:
                    ToastUtil.showToastShort("退出登录");
                    break;
                default:
                    break;
            }
        }
    }
}
