package com.leqian.bao.model.bll;

import android.app.Activity;
import android.widget.Toast;

import com.leqian.bao.view.imageview.ClipImageView;
import com.nxin.base.model.base.BaseManager;
import com.nxin.base.utils.Logger;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

/**
 * Created by fcl on 19.4.24
 * desc:
 */
public class ShareBLL extends BaseManager {

    private static ShareBLL instentce;

    public static ShareBLL getInstance() {
        if (instentce == null) {
            instentce = new ShareBLL();
        }
        return instentce;
    }

    public void shareToWX(Activity activity, String url, String title, String image, String desc) {

        UMWeb web = new UMWeb(url);
        web.setTitle(title);
        web.setThumb(new UMImage(mContext, image));
        web.setDescription(desc);

        new ShareAction(activity)
                .setPlatform(SHARE_MEDIA.WEIXIN)
                .setPlatform(SHARE_MEDIA.WEIXIN_CIRCLE)
                .withMedia(web)
                .setCallback(shareListener)
                .share();


    }

    private UMShareListener shareListener = new UMShareListener() {
        /**
         * @descrption 分享开始的回调
         * @param platform 平台类型
         */
        @Override
        public void onStart(SHARE_MEDIA platform) {
            Logger.i(initTag() + "--onStart--");
        }

        /**
         * @descrption 分享成功的回调
         * @param platform 平台类型
         */
        @Override
        public void onResult(SHARE_MEDIA platform) {
            Logger.i(initTag() + "--onResult--");
        }

        /**
         * @descrption 分享失败的回调
         * @param platform 平台类型
         * @param t 错误原因
         */
        @Override
        public void onError(SHARE_MEDIA platform, Throwable t) {
            Logger.i(initTag() + "--onError--");
        }

        /**
         * @descrption 分享取消的回调
         * @param platform 平台类型
         */
        @Override
        public void onCancel(SHARE_MEDIA platform) {
            Logger.i(initTag() + "--onCancel--");
        }
    };
}
