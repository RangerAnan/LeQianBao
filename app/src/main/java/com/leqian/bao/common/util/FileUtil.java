package com.leqian.bao.common.util;

import android.media.MediaScannerConnection;
import android.net.Uri;
import android.os.Build;
import android.os.Environment;

import com.leqian.bao.GlobalApplication;
import com.leqian.bao.model.Constants;
import com.nxin.base.utils.Logger;
import com.nxin.base.utils.ProHelper;

import java.io.File;

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


    /**
     * 创建所需要的文件夹；创建隐藏文件，不显示媒体文件;
     */
    public static void createProjectSdcardFile() {
        try {
            Logger.d("createProjectSdcardFile============================");
            if (!checkCard()) {
                return;
            }
            File mExternalStorage = Environment.getExternalStorageDirectory();
            Logger.d("mExternalStorage = " + mExternalStorage);
            File file = new File(mExternalStorage + Constants.DIR_PROJECT);
            Logger.d("file.exists() " + file.exists());
            if (!file.exists()) {
                file.mkdirs();
            }

            file = new File(mExternalStorage + Constants.DIR_DOWNLOAD);
            Logger.d("file.exists() " + file.exists());
            if (!file.exists()) {
                file.mkdirs();
            }

            file = new File(mExternalStorage + Constants.DIR_MEDIA);
            Logger.d("file.exists() " + file.exists());
            if (!file.exists()) {
                file.mkdirs();
            }

            file = new File(mExternalStorage + Constants.DIR_VOICE);
            Logger.d("file.exists() " + file.exists());
            if (!file.exists()) {
                file.mkdirs();
            }

            file = new File(mExternalStorage + Constants.DIR_IMAGE);
            Logger.d("file.exists() " + file.exists());
            if (!file.exists()) {
                file.mkdirs();
            }

            file = new File(mExternalStorage + Constants.DIR_VIDEO);
            Logger.d("file.exists() " + file.exists());
            if (!file.exists()) {
                file.mkdirs();
            }

            file = new File(mExternalStorage + Constants.DIR_FILE);
            Logger.d("file.exists() " + file.exists());
            if (!file.exists()) {
                file.mkdirs();
            }

            file = new File(mExternalStorage + Constants.DIR_LOG);
            Logger.d("file.exists() " + file.exists());
            if (!file.exists()) {
                file.mkdirs();
            }

            file = new File(mExternalStorage + Constants.DIR_IMAGE_TEMP);
            Logger.d("file.exists() " + file.exists());
            if (!file.exists()) {
                file.mkdirs();
            }

            // 临时图片禁止系统读取
            file = new File(mExternalStorage + Constants.DIR_IMAGE_TEMP_NOMEDIA);
            Logger.d("file.exists() " + file.exists());
            if (!file.exists()) {
                file.createNewFile();
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
    }

    /**
     * 检查SD卡是否插入
     *
     * @return
     */
    public static boolean checkCard() {
        String status = Environment.getExternalStorageState();
        return status.equals(Environment.MEDIA_MOUNTED);
    }
}
