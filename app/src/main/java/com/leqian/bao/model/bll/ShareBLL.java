package com.leqian.bao.model.bll;

import android.app.Activity;
import android.graphics.Bitmap;
import android.graphics.BitmapFactory;
import android.widget.Toast;

import com.leqian.bao.R;
import com.leqian.bao.model.constant.SDKConstant;
import com.leqian.bao.view.imageview.ClipImageView;
import com.nxin.base.model.base.BaseManager;
import com.nxin.base.utils.Logger;
import com.tencent.mm.opensdk.modelmsg.SendMessageToWX;
import com.tencent.mm.opensdk.modelmsg.WXMediaMessage;
import com.tencent.mm.opensdk.modelmsg.WXWebpageObject;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;
import com.umeng.socialize.ShareAction;
import com.umeng.socialize.UMShareListener;
import com.umeng.socialize.bean.SHARE_MEDIA;
import com.umeng.socialize.media.UMImage;
import com.umeng.socialize.media.UMWeb;

import java.io.ByteArrayOutputStream;

/**
 * Created by fcl on 19.4.24
 * desc:
 */
public class ShareBLL extends BaseManager {

    private int mTargetScene = SendMessageToWX.Req.WXSceneSession;
    private static final int THUMB_SIZE = 150;

    private static ShareBLL instentce;

    public static ShareBLL getInstance() {
        if (instentce == null) {
            instentce = new ShareBLL();
        }
        return instentce;
    }

    public void shareToWX(int id, Activity activity, String url, String title, String image, String desc) {

        SHARE_MEDIA channel = SHARE_MEDIA.WEIXIN;

        switch (id) {
            case 0:
                channel = SHARE_MEDIA.WEIXIN;
                break;
            case 1:
                channel = SHARE_MEDIA.WEIXIN_CIRCLE;
                break;
            default:
                break;
        }
        UMWeb web = new UMWeb(url);
        web.setTitle(title);
        web.setThumb(new UMImage(mContext, image));
        web.setDescription(desc);

        new ShareAction(activity)
                .setPlatform(channel)
                .withMedia(web)
                .setCallback(shareListener)
                .share();

       /* IWXAPI wxapi = WXAPIFactory.createWXAPI(mContext, SDKConstant.APP_KEY_WX);

        //初始化一个WXWebpageObject，填写url
        WXWebpageObject webpage = new WXWebpageObject();
        webpage.webpageUrl = url;

        //用 WXWebpageObject 对象初始化一个 WXMediaMessage 对象
        WXMediaMessage msg = new WXMediaMessage(webpage);
        msg.title = title;
        msg.description = desc;
        Bitmap bmp = BitmapFactory.decodeResource(mContext.getResources(), R.mipmap.app_icon);
        Bitmap thumbBmp = Bitmap.createScaledBitmap(bmp, THUMB_SIZE, THUMB_SIZE, true);
        bmp.recycle();
        msg.thumbData = bmpToByteArray(thumbBmp, true);

        SendMessageToWX.Req req = new SendMessageToWX.Req();
        req.transaction = buildTransaction("webpage");
        req.message = msg;
        req.scene = mTargetScene;


        //调用api接口，发送数据到微信
        wxapi.sendReq(req);*/

    }

    private String buildTransaction(final String type) {
        return (type == null) ? String.valueOf(System.currentTimeMillis()) : type + System.currentTimeMillis();
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
            Logger.i(initTag() + "--onError--" + t);
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


    public static byte[] bmpToByteArray(final Bitmap bmp, final boolean needRecycle) {
        ByteArrayOutputStream output = new ByteArrayOutputStream();
        bmp.compress(Bitmap.CompressFormat.PNG, 100, output);
        if (needRecycle) {
            bmp.recycle();
        }

        byte[] result = output.toByteArray();
        try {
            output.close();
        } catch (Exception e) {
            e.printStackTrace();
        }

        return result;
    }
}
