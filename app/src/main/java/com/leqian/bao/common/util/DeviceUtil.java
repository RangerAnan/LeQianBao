package com.leqian.bao.common.util;

import android.app.Activity;
import android.app.PendingIntent;
import android.content.Context;
import android.content.Intent;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageInfo;
import android.content.pm.PackageManager;
import android.content.pm.PackageManager.NameNotFoundException;
import android.content.res.Resources;
import android.location.LocationManager;
import android.net.Uri;
import android.net.wifi.WifiInfo;
import android.net.wifi.WifiManager;
import android.os.Build;
import android.provider.MediaStore;
import android.provider.MediaStore.Images.Media;
import android.provider.Settings;
import android.support.v4.app.Fragment;
import android.support.v4.app.FragmentActivity;
import android.support.v4.content.ContextCompat;
import android.support.v4.content.FileProvider;
import android.telephony.TelephonyManager;
import android.text.TextPaint;
import android.util.DisplayMetrics;
import android.util.TypedValue;
import android.view.Display;
import android.view.WindowManager;

import com.leqian.bao.R;
import com.leqian.bao.common.permissions.PermissionsResultAction;
import com.leqian.bao.common.permissions.PermissonsUtil;
import com.leqian.bao.model.constant.Constants;
import com.leqian.bao.model.code.RequestCode;
import com.nxin.base.utils.Logger;
import com.nxin.base.utils.ProHelper;

import java.io.File;
import java.lang.reflect.Field;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;
import java.util.UUID;

/**
 * 设备信息工具类
 */
public class DeviceUtil {

    /**
     * 是否是开发模式
     */
    public static boolean isApkDebugable() {
        try {
            ApplicationInfo info = ProHelper.getApplication().getApplicationInfo();
            if (info != null) {
                return (info.flags & ApplicationInfo.FLAG_DEBUGGABLE) != 0;
            }
        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }

    public static void openDial(final Context context, final String phoneNo) {
        PermissonsUtil.getPhonePermisson((FragmentActivity) context, new PermissionsResultAction() {

            @Override
            public void onGranted() {
                try {
                    Intent intent = new Intent(Intent.ACTION_DIAL, Uri.parse("tel:" + phoneNo));
                    context.startActivity(intent);
                } catch (Exception e) {
                    ToastUtil.showToastShort(R.string.device_nonsupport_dial);
                    e.printStackTrace();
                }
            }

            @Override
            public void onDenied(String permission) {
                ToastUtil.showToastLong(ProHelper.getApplication().getString(R.string.Permissons_Not_Phone));
            }
        });
    }

    public static void openBrowser(Context mContext, String url) {
        Uri uri = Uri.parse(url);
        Intent intent = new Intent(Intent.ACTION_VIEW, uri);
        try {
            mContext.startActivity(intent);
        } catch (Exception e) {
            // 捕捉扫描此类二维码出错 HTTPS://QR.ALIPAY.COM/FKX07302RXNRAKSDRX0FB7
            try {
                intent.setClassName("com.android.browser", "com.android.browser.BrowserActivity");
                mContext.startActivity(intent);
            } catch (Exception ex) {
                ex.printStackTrace();
                ToastUtil.showToastShort("不支持打开该网址");
            }
        }
    }

    public static void installApk(File apkFile) {
        Intent intent = new Intent(Intent.ACTION_VIEW);
        intent.addCategory(Intent.CATEGORY_DEFAULT);
        intent.setFlags(Intent.FLAG_ACTIVITY_NEW_TASK);
        if (Build.VERSION.SDK_INT > Build.VERSION_CODES.M) {
            intent.setDataAndType(getUriForFile(ProHelper.getApplication(), apkFile), "application/vnd.android.package-archive");
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION);
        } else {
            intent.setDataAndType(Uri.fromFile(apkFile), "application/vnd.android.package-archive");
        }
        ProHelper.getApplication().startActivity(intent);
    }

    //获取文字需要显示的宽度
    public static float getTextWidth(String text, float Size) {
        TextPaint tp = new TextPaint();
        tp.setTextSize(sp2px(Size));
        return tp.measureText(text);
    }

    //获取屏幕宽度
    public static int getScreenWidth() {
        DisplayMetrics dm = new DisplayMetrics();
        ProHelper.getScreenHelper().currentActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.widthPixels;
    }

    //获取屏幕高度
    public static int getScreenHeight() {
        DisplayMetrics dm = new DisplayMetrics();
        ProHelper.getScreenHelper().currentActivity().getWindowManager().getDefaultDisplay().getMetrics(dm);
        return dm.heightPixels;
    }
/*
    //获取状态栏高度
    public static int getStatusBarHeight() {
        int h = ShareUtilMain.getInt(ShareUtilMain.SHARE_STATUS_BAR_HEIGHT, 0);
        if (h == 0) {
            // 状态栏
            try {
                Class<?> c = Class.forName("com.android.internal.R$dimen");
                Object obj = c.newInstance();
                Field field = c.getField("status_bar_height");
                int x = Integer.parseInt(field.get(obj).toString());
                h = ProHelper.getApplication().getResources().getDimensionPixelSize(x);
                ShareUtilMain.setInt(ShareUtilMain.SHARE_STATUS_BAR_HEIGHT, h);
            } catch (Exception e1) {
                e1.printStackTrace();
            }
        }
        return h;
    }*/

    //设置字体大小如20sp
    public static float applyDimension(int unit, float value) {
        return TypedValue.applyDimension(unit, value, ProHelper.getApplication().getResources().getDisplayMetrics());
    }

    //根据手机的分辨率从 dp 的单位 转成为 px(像素)
    public static int dp2px(float dpValue) {
        float scale = ProHelper.getApplication().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

    //根据手机的分辨率从 px(像素) 的单位 转成为 dp
    public static int px2dp(float pxValue) {
        float scale = ProHelper.getApplication().getResources().getDisplayMetrics().density;
        return (int) (pxValue / scale + 0.5f);
    }

    //将sp值转换为px值
    public static float sp2px(float spValue) {
        float fontScale = ProHelper.getApplication().getResources().getDisplayMetrics().scaledDensity;
        return spValue * fontScale;
    }

    //将px值转换为sp值
    public static float px2sp(float pxValue) {
        float fontScale = ProHelper.getApplication().getResources().getDisplayMetrics().scaledDensity;
        return pxValue / fontScale;
    }


    //除状态栏标题栏的屏幕高度
   /* public static int getAppInnerHeight() {
        return getScreenHeight() - getStatusBarHeight() - DeviceUtil.dp2px(48);
    }*/

    public static int getNavigationBarHeight() {
        Resources resources = ProHelper.getApplication().getResources();
        int resourceId = resources.getIdentifier("navigation_bar_height", "dimen", "android");
        if (resourceId > 0) {
            return resources.getDimensionPixelSize(resourceId);
        }
        return 0;
    }

    //获取机器iMei值
    public static String getImei() {
        String imei = "";
        TelephonyManager telephonyManager = (TelephonyManager) ProHelper.getApplication().getSystemService(Context.TELEPHONY_SERVICE);
        try {
            if (telephonyManager != null) {
                imei = telephonyManager.getDeviceId();
                if (imei == null || imei.equals("")) {
                    imei = getMac();
                }
            }

        } catch (Exception ext) {
            ext.printStackTrace();
        }

        return imei;
    }

    //获取机器mac值
    public static String getMac() {
        try {
            WifiManager wifi = (WifiManager) ProHelper.getApplication().getApplicationContext().getSystemService(Context.WIFI_SERVICE);
            WifiInfo info = wifi.getConnectionInfo();
            return info.getMacAddress().toLowerCase();
        } catch (Exception ext) {
            ext.printStackTrace();
        }
        return "";
    }

    //获取UUID
    public static String getUUID() {
        return UUID.randomUUID().toString().replace("-", "");
    }

    //获取版本号
    public static String getVersionName() {
        try {
            PackageInfo pi = ProHelper.getApplication().getPackageManager().getPackageInfo(ProHelper.getApplication().getPackageName(), 0);
            return pi.versionName;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
            return "";
        }
    }

    //获取设备型号
    public static String getMODEL() {
        try {
            return Build.MODEL;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    //设备的系统版本
    public static String getSystemRelease() {
        try {
            return Build.VERSION.RELEASE;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    //设备的系统版本
    public static String getSystemSDKVersion() {
        try {
            return Build.VERSION.SDK;
        } catch (Exception e) {
            e.printStackTrace();
            return "";
        }
    }

    //打开相机
    public static void openCamera(Context mContext) {
        String filename = "myb_camera_" + System.currentTimeMillis() + ".jpg";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            File file = new File(Constants.SAVE_IMAGE_TEMP_PATH, filename);
            if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
            Uri imageUri = getUriForFile(mContext, file);//FileProvider.getUriForFile(mContext, getFileProviderName(mContext), file);//通过FileProvider创建一个content类型的Uri
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//将拍取的照片保存到指定URI
            Constants.PHOTOFILEPATH = Constants.SAVE_IMAGE_TEMP_PATH + "/" + filename;
            ((Activity) mContext).startActivityForResult(intent, RequestCode.CHOICE_CMARE);
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // 下面这句指定调用相机拍照后的照片存储的路径
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Constants.SAVE_IMAGE_TEMP_PATH, filename)));
            Constants.PHOTOFILEPATH = Constants.SAVE_IMAGE_TEMP_PATH + "/" + filename;
            ((Activity) mContext).startActivityForResult(intent, RequestCode.CHOICE_CMARE);
        }
    }

    //打开相机
    public static void openCamera(Fragment fragment) {
        String filename = "myb_camera_" + System.currentTimeMillis() + ".jpg";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            File file = new File(Constants.SAVE_IMAGE_TEMP_PATH, filename);
            if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
            Uri imageUri = getUriForFile(ProHelper.getApplication(), file);//FileProvider.getUriForFile(mContext, getFileProviderName(mContext), file);//通过FileProvider创建一个content类型的Uri
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//将拍取的照片保存到指定URI
            Constants.PHOTOFILEPATH = Constants.SAVE_IMAGE_TEMP_PATH + "/" + filename;
            fragment.startActivityForResult(intent, RequestCode.CHOICE_CMARE);
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // 下面这句指定调用相机拍照后的照片存储的路径
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Constants.SAVE_IMAGE_TEMP_PATH, filename)));
            Constants.PHOTOFILEPATH = Constants.SAVE_IMAGE_TEMP_PATH + "/" + filename;
            fragment.startActivityForResult(intent, RequestCode.CHOICE_CMARE);
        }
    }

    //打开相机
    public static void openCameraBatch(Context mContext) {
//        String filename = "znt_camera_" + System.currentTimeMillis() + ".jpg";
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        // 下面这句指定调用相机拍照后的照片存储的路径
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Constants.SAVE_IMAGE_TEMP_PATH, filename)));
//        Constants.PHOTOFILEPATH = Constants.SAVE_IMAGE_TEMP_PATH + "/" + filename;
//        ((Activity) mContext).startActivityForResult(intent, RequestCode.CHOICE_CMARE_Batch);

        String filename = "znt_camera_" + System.currentTimeMillis() + ".jpg";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            File file = new File(Constants.SAVE_IMAGE_TEMP_PATH, filename);
            if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
            Uri imageUri = getUriForFile(mContext, file);//FileProvider.getUriForFile(mContext, getFileProviderName(mContext), file);//通过FileProvider创建一个content类型的Uri
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//将拍取的照片保存到指定URI
            Constants.PHOTOFILEPATH = Constants.SAVE_IMAGE_TEMP_PATH + "/" + filename;
            ((Activity) mContext).startActivityForResult(intent, RequestCode.CHOICE_CMARE_Batch);
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // 下面这句指定调用相机拍照后的照片存储的路径
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Constants.SAVE_IMAGE_TEMP_PATH, filename)));
            Constants.PHOTOFILEPATH = Constants.SAVE_IMAGE_TEMP_PATH + "/" + filename;
            ((Activity) mContext).startActivityForResult(intent, RequestCode.CHOICE_CMARE_Batch);
        }
    }

    //打开相机
    public static void openQiniuCameraBatch(Context mContext) {
//        String filename = "znt_camera_" + System.currentTimeMillis() + ".jpg";
//        Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
//        // 下面这句指定调用相机拍照后的照片存储的路径
//        intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Constants.SAVE_IMAGE_TEMP_PATH, filename)));
//        Constants.PHOTOFILEPATH = Constants.SAVE_IMAGE_TEMP_PATH + "/" + filename;
//        ((Activity) mContext).startActivityForResult(intent, RequestCode.CHOICE_CMARE_Batch);

        String filename = "znt_camera_" + System.currentTimeMillis() + ".jpg";
        if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.N) {
            File file = new File(Constants.SAVE_IMAGE_TEMP_PATH, filename);
            if (!file.getParentFile().exists()) file.getParentFile().mkdirs();
            Uri imageUri = getUriForFile(mContext, file);//FileProvider.getUriForFile(mContext, getFileProviderName(mContext), file);//通过FileProvider创建一个content类型的Uri
            Intent intent = new Intent();
            intent.addFlags(Intent.FLAG_GRANT_READ_URI_PERMISSION); //添加这一句表示对目标应用临时授权该Uri所代表的文件
            intent.setAction(MediaStore.ACTION_IMAGE_CAPTURE);//设置Action为拍照
            intent.putExtra(MediaStore.EXTRA_OUTPUT, imageUri);//将拍取的照片保存到指定URI
            Constants.PHOTOFILEPATH = Constants.SAVE_IMAGE_TEMP_PATH + "/" + filename;
            ((Activity) mContext).startActivityForResult(intent, RequestCode.CHOICE_CMARE_QINIU_Batch);
        } else {
            Intent intent = new Intent(MediaStore.ACTION_IMAGE_CAPTURE);
            // 下面这句指定调用相机拍照后的照片存储的路径
            intent.putExtra(MediaStore.EXTRA_OUTPUT, Uri.fromFile(new File(Constants.SAVE_IMAGE_TEMP_PATH, filename)));
            Constants.PHOTOFILEPATH = Constants.SAVE_IMAGE_TEMP_PATH + "/" + filename;
            ((Activity) mContext).startActivityForResult(intent, RequestCode.CHOICE_CMARE_QINIU_Batch);
        }
    }

    //打开相册：不再使用，见ChoosePhotoUtil
    public static void openPhotos(Context mContext) {
        try {
            //Intent intent_photo = new Intent(Intent.ACTION_GET_CONTENT); 错误
            Intent intent = new Intent(Intent.ACTION_PICK, Media.EXTERNAL_CONTENT_URI);
            intent.setDataAndType(Media.EXTERNAL_CONTENT_URI, "image/*");
            ((Activity) mContext).startActivityForResult(intent, RequestCode.CHOICE_PHOTO);
        } catch (Exception ext) {
            ext.printStackTrace();
        }
    }

    //打开相册:不再使用，见ChoosePhotoUtil
    public static void openPhotos(Fragment fragment) {
        try {
            Intent intent_photo = new Intent(Intent.ACTION_PICK, Media.EXTERNAL_CONTENT_URI);
            intent_photo.setDataAndType(Media.EXTERNAL_CONTENT_URI, "image/*");
            fragment.startActivityForResult(intent_photo, RequestCode.CHOICE_PHOTO);
        } catch (Exception ext) {
            ext.printStackTrace();
        }
    }

    //打开视频
    public static void openMdeia(Context mContext) {
        try {
            Intent intent_photo = new Intent(Intent.ACTION_GET_CONTENT);
            intent_photo.setDataAndType(Media.EXTERNAL_CONTENT_URI, "video/*");
            ((Activity) mContext).startActivityForResult(intent_photo, RequestCode.CHOICE_MEDIA);
        } catch (Exception ext) {
            ext.printStackTrace();
        }
    }

    //验证GPS是否打开
    public static boolean isGPSOpen(Context mContext) {
        LocationManager locationManager = (LocationManager) mContext.getSystemService(Context.LOCATION_SERVICE);
        return locationManager.isProviderEnabled(LocationManager.GPS_PROVIDER);
    }

    //打开GPS设置
    public static void openGPSConfig(Context mContext) {
        Intent intent = new Intent(Settings.ACTION_LOCATION_SOURCE_SETTINGS);
        ((Activity) mContext).startActivityForResult(intent, 0); // 设置完成后返回到原来的界面
    }

    //强制帮用户打开GPS
    public static boolean openGPS(Context context) {
        Intent GPSIntent = new Intent();
        GPSIntent.setClassName("com.android.settings",
                "com.android.settings.widget.SettingsAppWidgetProvider");
        GPSIntent.addCategory("android.intent.category.ALTERNATIVE");
        GPSIntent.setData(Uri.parse("custom:3"));
        try {
            PendingIntent.getBroadcast(context, 0, GPSIntent, 0).send();
            return true;
        } catch (Exception e) {
            e.printStackTrace();
            return false;
        }
    }

    public static boolean checkPermission(Context context, String permission) {
        boolean result = false;
        if (Build.VERSION.SDK_INT >= 23) {
            if (ContextCompat.checkSelfPermission(context, permission) == PackageManager.PERMISSION_GRANTED) {
                result = true;
            }
        } else {
            PackageManager pm = context.getPackageManager();
            if (pm.checkPermission(permission, context.getPackageName()) == PackageManager.PERMISSION_GRANTED) {
                result = true;
            }
        }
        return result;
    }

    /**
     * 创建一个用于拍照图片输出路径的Uri (FileProvider)
     *
     * @param context
     * @return
     */
    public static Uri getUriForFile(Context context, File file) {
        return FileProvider.getUriForFile(context, Constants.FILE_PROVIDER_AUTHORITY, file);
    }

    //获取屏幕原始尺寸高度，包括虚拟功能键高度
    public static int getDpi(Context context) {
        int dpi = 0;
        WindowManager windowManager = (WindowManager) context.getSystemService(Context.WINDOW_SERVICE);
        Display display = windowManager.getDefaultDisplay();
        DisplayMetrics displayMetrics = new DisplayMetrics();
        @SuppressWarnings("rawtypes")
        Class c;
        try {
            c = Class.forName("android.view.Display");
            @SuppressWarnings("unchecked")
            Method method = c.getMethod("getRealMetrics", DisplayMetrics.class);
            method.invoke(display, displayMetrics);
            dpi = displayMetrics.heightPixels;
        } catch (Exception e) {
            e.printStackTrace();
        }
        return dpi;
    }


    /**
     * 收集设备参数信息
     *
     * @param ctx
     */
    public static Map<String, String> collectDeviceInfo(Context ctx) {
        Map<String, String> infos = new HashMap<>();
        try {
            PackageManager pm = ctx.getPackageManager();
            PackageInfo pi = pm.getPackageInfo(ctx.getPackageName(), PackageManager.GET_ACTIVITIES);
            if (pi != null) {
                String versionName = pi.versionName == null ? "null" : pi.versionName;
                String versionCode = pi.versionCode + "";
                infos.put("versionName", versionName);
                infos.put("versionCode", versionCode);
            }
        } catch (NameNotFoundException e) {
            Logger.e("collectDeviceInfo: an error occured when collect package info  " + e);
        }
        Field[] fields = Build.class.getDeclaredFields();
        for (Field field : fields) {
            try {
                field.setAccessible(true);
                infos.put(field.getName(), field.get(null).toString());
                Logger.d("collectDeviceInfo: " + field.getName() + " : " + field.get(null));
            } catch (Exception e) {
                Logger.e("collectDeviceInfo: an error occured when collect crash info  " + e);
            }
        }
        return infos;
    }

}
