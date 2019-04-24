package com.leqian.bao.view.activity.resource;

import android.app.Activity;
import android.content.Intent;
import android.text.TextUtils;
import android.view.View;
import android.widget.ImageView;
import android.widget.RelativeLayout;
import android.widget.TextView;

import com.bumptech.glide.Glide;
import com.leqian.bao.R;
import com.leqian.bao.common.base.BaseToolBarActivity;
import com.leqian.bao.common.http.ResourceHttp;
import com.leqian.bao.common.http.UploadHttp;
import com.leqian.bao.common.permissions.PermissionsResultAction;
import com.leqian.bao.common.permissions.PermissonsUtil;
import com.leqian.bao.common.util.DeviceUtil;
import com.leqian.bao.common.util.ImageCutUtil;
import com.leqian.bao.common.util.ImageUtil;
import com.leqian.bao.common.util.ToastUtil;
import com.leqian.bao.model.BaseHttp;
import com.leqian.bao.model.BaseModelResp;
import com.leqian.bao.model.constant.Constants;
import com.leqian.bao.model.account.UploadImageResp;
import com.leqian.bao.model.code.RequestCode;
import com.leqian.bao.view.dialog.listDilog.BottomListDialog;
import com.nxin.base.model.http.callback.ModelCallBack;
import com.nxin.base.model.network.glide.GlideUtils;
import com.nxin.base.utils.Logger;
import com.nxin.base.widget.NXToolBarActivity;

import java.io.File;
import java.util.ArrayList;
import java.util.Collections;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by fcl on 19.4.22
 * desc:
 */
public class MakeCoverActivity extends BaseToolBarActivity implements BottomListDialog.IBottomListItemListener {

    @BindView(R.id.tv_title)
    TextView tv_title;

    @BindView(R.id.image_button)
    ImageView image_button;

    @BindView(R.id.et_title)
    TextView et_title;

    @BindView(R.id.et_desc)
    TextView et_desc;

    @BindView(R.id.bar_right)
    RelativeLayout bar_right;


    private BottomListDialog listDialog;

    private String imagePath;

    @Override
    public int getToolBarLayoutId() {
        return R.layout.title_common;
    }

    @Override
    public int getLayoutId() {
        return R.layout.activity_make_cover;
    }

    @Override
    public void initView() {
        super.initView();
        tv_title.setText("制作");
        bar_right.setVisibility(View.VISIBLE);
    }

    @Override
    public void initViewData() {
        super.initViewData();
    }

    @OnClick({R.id.bar_left, R.id.bar_right, R.id.image_button})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.bar_left:
                finish();
                break;
            case R.id.bar_right:
                String title = et_title.getText().toString().trim();
                if (TextUtils.isEmpty(title)) {
                    ToastUtil.showToastShort("标题不能为空");
                    return;
                }
                String desc = et_desc.getText().toString().trim();
                if (TextUtils.isEmpty(desc)) {
                    ToastUtil.showToastShort("描述不能为空");
                    return;
                }
                if (TextUtils.isEmpty(imagePath)) {
                    ToastUtil.showToastShort("封面不能为空");
                    return;
                }
                requestUploadCover(title, desc, imagePath);
                break;
            case R.id.image_button:
                String[] stringArray = getResources().getStringArray(R.array.dialog_list_item);
                ArrayList<String> nameList = new ArrayList<>();
                Collections.addAll(nameList, stringArray);

                listDialog = new BottomListDialog(mContext, R.style.PhotoSelectDialog, nameList);
                listDialog.setOnBottomListItemListener(this);
                listDialog.show();
                break;
            default:
                break;
        }
    }

    private void requestUploadCover(String title, String desc, String coverIcon) {
        UploadHttp.uploadCover(title, desc, new File(coverIcon), new ModelCallBack<BaseModelResp>() {
            @Override
            public void onResponse(BaseModelResp response, int id) {
                if (response.getCode() != 1) {
                    ToastUtil.showToastShort(response.getMsg());
                    return;
                }
                ToastUtil.showToastShort("上传成功");
                finish();
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
        PermissonsUtil.getCamraPermisson(MakeCoverActivity.this, new PermissionsResultAction() {
            @Override
            public void onGranted() {
                if (type == 0) {
                    DeviceUtil.openCamera(MakeCoverActivity.this);
                } else if (type == 1) {
                    DeviceUtil.openPhotos(mContext);
                }
            }

            @Override
            public void onDenied(String permission) {
                ToastUtil.showToastLong(getString(R.string.Permissons_Not_Camra));
            }
        });
    }

    @Override
    public void onActivityResult(int requestCode, int resultCode, Intent data) {
        super.onActivityResult(requestCode, resultCode, data);
        if (resultCode == Activity.RESULT_OK) {
            switch (requestCode) {
                case RequestCode.CHOICE_CMARE: {
                    imagePath = getImagePath(Constants.PHOTOFILEPATH);
                }
                break;
                case RequestCode.CHOICE_PHOTO: {
                    imagePath = getImagePath(ImageUtil.getImageAbsolutePath(mContext, data.getData()));
                }
                break;
                default:
                    break;
            }
        }
    }

    private String getImagePath(String srcPath) {
        String filepath = "";
        try {
            filepath = ImageUtil.getCutImageTempPath();
            Logger.i(initTag() + "--getImagePath--srcPath:" + srcPath + ";filepath:" + filepath);
            if (!ImageCutUtil.cut(srcPath, filepath)) {
                filepath = srcPath;
                throw new Exception(getString(R.string.error_image_cut));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return filepath;
    }

}
