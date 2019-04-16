package com.leqian.bao.view.activity.image;

import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Message;
import android.view.View;
import android.widget.TextView;

import com.leqian.bao.R;
import com.leqian.bao.common.util.ImageUtil;
import com.leqian.bao.model.Constants;
import com.nxin.base.widget.NXToolBarActivity;

import java.io.ByteArrayOutputStream;

import butterknife.BindView;
import butterknife.OnClick;

/**
 * Created by fcl on 19.4.16
 * desc:
 */
public class CropImageActivity extends NXToolBarActivity {

    @BindView(R.id.tv_title)
    TextView tv_title;


    private Handler handler = new Handler() {

        @Override
        public void handleMessage(Message msg) {
            switch (msg.what) {
                case Constants.CROP_LOCAL:
                    byte[] bis = (byte[]) msg.obj;
                    Bitmap bitmap = BitmapFactory.decodeByteArray(bis, 0, bis.length);
                    if (bitmap != null) {
                        String toPath = ImageUtil.getCutImageTempPath();
//                        if (ImageCutUtil.saveCropImage(bitmap, toPath)) {
//                            coverImageBitmap = BitmapFactory.decodeByteArray(bis, 0, bis.length);
//                            mProgressDialog = new LoadingDialog(mContext, false, getString(R.string.save_image_progress_warning));
//                            mProgressDialog.show();
//                            uploadCoverImage(toPath);
//                        }
                    }
                    break;
            }
        }
    };
    private CropImageFragment cropImageFragment;

    @Override
    public int getLayoutId() {
        return R.layout.activity_frame;
    }

    @Override
    public int getToolBarLayoutId() {
        return R.layout.title_common;
    }

    @Override
    public void initView() {
        super.initView();
        tv_title.setText("裁剪图片");
        cropImageFragment = new CropImageFragment(handler);
        openFragment(getIntent().getExtras(), cropImageFragment);
    }


    @OnClick({R.id.bar_right})
    public void onViewClicked(View v) {
        switch (v.getId()) {
            case R.id.bar_right:
                cropImage();
                break;
            default:
                break;
        }
    }


    private void cropImage() {
        Bitmap bitmap = cropImageFragment.getImage();

        //由于Intent传递bitmap不能超过40k,此处使用二进制数组传递
        ByteArrayOutputStream baos = new ByteArrayOutputStream();
        bitmap.compress(Bitmap.CompressFormat.JPEG, 100, baos);
        byte[] bitmapByte = baos.toByteArray();

        Message msg = handler.obtainMessage();
        msg.what = Constants.CROP_LOCAL;
        msg.obj = bitmapByte;
        handler.sendMessage(msg);
        finish();
    }
}
