package com.leqian.bao.view.activity.image;

import android.annotation.SuppressLint;
import android.graphics.Bitmap;
import android.os.Handler;
import android.text.TextUtils;

import com.leqian.bao.R;
import com.leqian.bao.common.util.ImageUtil;
import com.leqian.bao.view.imageview.ClipImageView;
import com.nxin.base.widget.NXFragment;

import butterknife.BindView;

/**
 * Created by fcl on 19.4.16
 * desc:
 */

@SuppressLint("ValidFragment")
public class CropImageFragment extends NXFragment {

    @BindView(R.id.iv_clip_image)
    ClipImageView clipImageView;

    private Handler handler;

    public CropImageFragment(Handler handler) {
        this.handler = handler;
    }


    @Override
    public int getLayoutId() {
        return R.layout.fragment_crop_image;
    }

    @Override
    public void initView() {
        super.initView();

        clipImageView.setBorderDistance(0);
        clipImageView.setBorderWidth(1);
        clipImageView.setBorderWeight(15, 8);

        if (getArguments() != null) {
            String cropImagePath = getArguments().getString("CROP_IMAGE_PATH");
            if (!TextUtils.isEmpty(cropImagePath)) {
                Bitmap mBitmap = ImageUtil.scaleImage(cropImagePath);
                if (mBitmap != null) {
                    clipImageView.setImageBitmap(mBitmap);
                }
            }
        }

    }

    public Bitmap getImage() {
        return clipImageView.clip();
    }
}
