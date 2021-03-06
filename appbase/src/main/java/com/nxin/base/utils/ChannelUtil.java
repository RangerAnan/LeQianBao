package com.nxin.base.utils;

import android.content.Context;
import android.content.SharedPreferences;
import android.content.SharedPreferences.Editor;
import android.content.pm.ApplicationInfo;
import android.content.pm.PackageManager.NameNotFoundException;
import android.preference.PreferenceManager;
import android.text.TextUtils;

import java.io.IOException;
import java.util.Enumeration;
import java.util.zip.ZipEntry;
import java.util.zip.ZipFile;

/**
 * Created by zhuhua
 * mail:mail.nxin.com
 * date: 2016/5/3
 * desc:获取包的渠道，一般设置到友盟统计中
 */
public class ChannelUtil {

    private static final String CHANNEL_KEY = "znt_channel";
    private static final String TAG = "ChannelUtil";
    private static final String CHANNEL_VERSION_KEY = "znt_channel_version";
    private static String mChannel;

    /**
     * 返回市场。  如果获取失败返回""
     *
     * @param context
     * @return
     */
    public static String getChannel(Context context) {
        return getChannel(context, "c_home");
    }

    /**
     * 返回市场。  如果获取失败返回defaultChannel
     */
    public static String getChannel(Context context, String defaultChannel) {
        Logger.i(TAG + "---getChannel---defaultChannel:" + defaultChannel);
        try {
            //内存中获取
            if (!TextUtils.isEmpty(mChannel)) {
                Logger.i(TAG + "-mChannel:" + mChannel);
                return mChannel;
            }
            Logger.i(TAG + "-sp");
            //sp中获取
            mChannel = getChannelBySharedPreferences(context);
            if (!TextUtils.isEmpty(mChannel)) {
                Logger.i(TAG + "-mChannel:" + mChannel);
                return mChannel;
            }
            Logger.i(TAG + "-apk");
            //从apk中获取
            mChannel = getChannelFromApk(context, CHANNEL_KEY);
            if (!TextUtils.isEmpty(mChannel)) {
                //保存sp中备用
                saveChannelBySharedPreferences(context, mChannel);
                Logger.i(TAG + "-mChannel:" + mChannel);
                return mChannel;
            }
        } catch (Exception ext) {
            ext.printStackTrace();
        }

        Logger.i(TAG + "-defaultChannel:" + defaultChannel);

        //全部获取失败
        return defaultChannel;
    }

    /**
     * 从apk中获取版本信息
     *
     * @param context
     * @param channelKey
     * @return
     */
    public static String getChannelFromApk(Context context, String channelKey) {
        //从apk包中获取
        ApplicationInfo appinfo = context.getApplicationInfo();
        String sourceDir = appinfo.sourceDir;
        //默认放在meta-inf/里， 所以需要再拼接一下
        String key = "META-INF/" + channelKey;
        String ret = "";
        ZipFile zipfile = null;
        try {
            zipfile = new ZipFile(sourceDir);
            Enumeration<?> entries = zipfile.entries();
            while (entries.hasMoreElements()) {
                ZipEntry entry = ((ZipEntry) entries.nextElement());
                String entryName = entry.getName();
                //攻击者可构造恶意zip文件，被解压的文件将会进行目录跳转被解压到其他目录，覆盖相应文件导致任意代码执行。
                if (entryName.contains("../")) {
                    Logger.e(TAG + "---getChannelFromApk--恶意文件:" + entryName);
                    break;
                }
                if (entryName.startsWith(key)) {
                    ret = entryName;
                    break;
                }
            }
        } catch (IOException e) {
            e.printStackTrace();
        } finally {
            if (zipfile != null) {
                try {
                    zipfile.close();
                } catch (IOException e) {
                    e.printStackTrace();
                }
            }
        }
        String[] split = ret.split("_");
        String channel = "";
        if (split != null && split.length >= 3) {
            channel = ret.substring(split[0].length() + split[1].length() + 2);
        }
        return channel;
    }

    /**
     * 本地保存channel & 对应版本号
     *
     * @param context
     * @param channel
     */
    public static void saveChannelBySharedPreferences(Context context, String channel) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        Editor editor = sp.edit();
        editor.putString(CHANNEL_KEY, channel);
        editor.putInt(CHANNEL_VERSION_KEY, getVersionCode(context));
        editor.commit();
    }

    /**
     * 从sp中获取channel
     *
     * @param context
     * @return 为空表示获取异常、sp中的值已经失效、sp中没有此值
     */
    public static String getChannelBySharedPreferences(Context context) {
        SharedPreferences sp = PreferenceManager.getDefaultSharedPreferences(context);
        int currentVersionCode = getVersionCode(context);
        if (currentVersionCode == -1) {
            //获取错误
            return "";
        }
        int versionCodeSaved = sp.getInt(CHANNEL_VERSION_KEY, -1);
        if (versionCodeSaved == -1) {
            //本地没有存储的channel对应的版本号
            //第一次使用  或者 原先存储版本号异常
            return "";
        }
        if (currentVersionCode != versionCodeSaved) {
            return "";
        }
        return sp.getString(CHANNEL_KEY, "");
    }

    /**
     * 从包信息中获取版本号
     *
     * @param context
     * @return
     */
    private static int getVersionCode(Context context) {
        try {
            return context.getPackageManager().getPackageInfo(context.getPackageName(), 0).versionCode;
        } catch (NameNotFoundException e) {
            e.printStackTrace();
        }
        return -1;
    }
}
