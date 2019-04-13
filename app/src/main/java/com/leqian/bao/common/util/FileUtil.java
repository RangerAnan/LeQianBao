package com.leqian.bao.common.util;

import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;

import com.leqian.bao.GlobalApplication;
import com.nxin.base.utils.ProHelper;

/**
 * Created by fcl on 19.4.13
 * desc:
 */
public class FileUtil {

    /**
     * 通知系统媒体库更新
     */
    public static void noticeMediaScanner(String filepath) {
//        MyLogUtil.i("noticeMediaScanner filepath " + filepath);
        try {
            //如果是4.4及以上版本
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.KITKAT) {
                String[] paths = new String[]{filepath};
                MediaScannerConnection.scanFile(ProHelper.getApplication(), paths, null, new MediaScannerConnection.OnScanCompletedListener() {

                    @Override
                    public void onScanCompleted(String path, Uri uri) {
//                        MyLogUtil.i("noticeMediaScanner Finished scanning " + path);
                    }
                });
            } else {
                // 暂不考虑4.4以下
                // GlobalApplication.globalContext.sendBroadcast(new Intent(Intent.ACTION_MEDIA_MOUNTED, Uri.parse("file://" + Constant.TALK_IMAGE_PATH)));
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
    }
}
