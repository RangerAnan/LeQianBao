package com.leqian.bao.widget;

import android.app.Activity;
import android.app.IntentService;
import android.app.NotificationChannel;
import android.app.NotificationManager;
import android.content.Context;
import android.content.Intent;
import android.graphics.BitmapFactory;
import android.os.Handler;
import android.os.Looper;
import android.support.v4.app.FragmentActivity;
import android.support.v4.app.NotificationCompat;

import com.leqian.bao.BuildConfig;
import com.leqian.bao.GlobalApplication;
import com.leqian.bao.R;
import com.leqian.bao.common.permissions.PermissionsResultAction;
import com.leqian.bao.common.permissions.PermissonsUtil;
import com.leqian.bao.common.util.DeviceUtil;
import com.leqian.bao.model.constant.Constants;
import com.nxin.base.common.ScreenManager;
import com.nxin.base.model.http.OkHttpUtils;
import com.nxin.base.model.http.callback.FileCallBack;
import com.nxin.base.utils.Logger;
import com.nxin.base.utils.ProHelper;
import com.nxin.base.utils.UIUtil;

import java.io.File;

import okhttp3.Call;

/**
 * Created by fcl on 19.5.11
 * desc:
 */
public class VersionUpdateService extends IntentService {

    private Handler handler;
    NotificationManager mNotificationManager;
    File apkFile;
    private int notificationId = 101;
    private int progress = 0;
    private int loadState = 0;                      //下载状态，0-下载中，1-失败，2成功
    private String channelId = "101";
    private String channelName = "应用升级";

    public static final String TAG = VersionUpdateService.class.getSimpleName();
    private NotificationCompat.Builder builder;

    public VersionUpdateService() {
        super("VersionUpdateService");
    }

    @Override
    public void onCreate() {
        super.onCreate();
        mNotificationManager = (NotificationManager) getSystemService(Context.NOTIFICATION_SERVICE);
        handler = new Handler(Looper.getMainLooper());
    }

    @Override
    protected void onHandleIntent(Intent intent) {

        final String apkUrl = intent.getStringExtra("APK_URL");
        final String apkVersion = intent.getStringExtra("APK_VERSION");
        final String tips = String.format(getString(R.string.download_app_description), getString(R.string.app_name));

        handler.post(new Runnable() {
            @Override
            public void run() {
                FragmentActivity currentActivity = ScreenManager.getInstance().currentActivity();
                if (currentActivity == null) {
                    return;
                }
                PermissonsUtil.getStoragePermisson(currentActivity, new PermissionsResultAction() {
                    @Override
                    public void onGranted() {
                        download(apkUrl, BuildConfig.FLAVOR + "-" + apkVersion + ".apk");
                    }

                    @Override
                    public void onDenied(String permission) {
                    }
                });
            }
        });
    }

    public void download(String apkUrl, String apkName) {
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.O) {
            NotificationChannel channel = new NotificationChannel(channelId, channelName, NotificationManager.IMPORTANCE_DEFAULT);
            channel.enableLights(false); //是否在桌面icon右上角展示小红点
            channel.setShowBadge(false); //是否在久按桌面图标时显示此渠道的通知
            channel.enableVibration(false);
            channel.setSound(null, null);
            mNotificationManager.createNotificationChannel(channel);
        }

        builder = new NotificationCompat.Builder(ProHelper.getApplication(), channelId);
        //设置通知的小图标
        builder.setSmallIcon(DeviceUtil.getIconSmallRes(ProHelper.getApplication()));
        builder.setLargeIcon(BitmapFactory.decodeResource(ProHelper.getApplication().getResources(),
                DeviceUtil.getIconRes(ProHelper.getApplication())));
        builder.setSound(null);

        OkHttpUtils.get().url(apkUrl).build().execute(new FileCallBack(Constants.SAVE_FILE_DOWNLOAD_PATH, apkName) {

            @Override
            public void onError(Call call, Exception e, int id) {
                Logger.i(TAG + "---onError--e:" + e);
                loadState = 1;
            }

            @Override
            public void onResponse(File response, int id) {
                Logger.i(TAG + "---onResponse--" + response.getName());
                apkFile = response;
                loadState = 2;
            }

            @Override
            public void inProgress(float progress, long total, int id) {
                super.inProgress(progress, total, id);
                Logger.i(TAG + "---inProgress--progress:" + progress + "---total:" + total);
                VersionUpdateService.this.progress = (int) (progress * 100);
            }
        });

        mNotificationManager.notify(notificationId, getNotification("正在下载" +
                ProHelper.getApplication().getResources().getString(R.string.app_name), 0).build());
        handler.postDelayed(runnable, 1500);
    }

    Runnable runnable = new Runnable() {
        @Override
        public void run() {
            if (loadState == 1) {
                // failure
                mNotificationManager.notify(notificationId, getNotification("下载失败", progress).build());
            } else if (loadState == 2) {
                //success
                mNotificationManager.notify(notificationId, getNotification("下载完成", 100).build());
                mNotificationManager.cancel(notificationId);
                DeviceUtil.installApk(apkFile);
            } else {
                // downloading
                if (progress > 100) {
                    progress = 100;
                }
                mNotificationManager.notify(notificationId, getNotification("正在下载" +
                        ProHelper.getApplication().getResources().getString(R.string.app_name), progress).build());
                handler.postDelayed(runnable, 1500);
            }
        }
    };

    /**
     * @param title
     * @param progress
     * @return
     */
    private NotificationCompat.Builder getNotification(String title, int progress) {
        //设置通知的标题
        builder.setContentTitle(title);
        if (progress >= 0) {
            //当progress大于或等于0时，才需要显示下载进度
            Logger.i(TAG + "---getNotification--progress:" + progress);
            builder.setContentText(progress + "%");
            builder.setProgress(100, progress, false);
        }
        return builder;
    }

    @Override
    public void onDestroy() {
        super.onDestroy();
        if (mNotificationManager != null) {
            mNotificationManager.cancel(notificationId);
        }
    }
}
