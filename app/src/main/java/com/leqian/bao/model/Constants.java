package com.leqian.bao.model;

import android.os.Environment;

import com.leqian.bao.BuildConfig;
import com.nxin.base.utils.ProHelper;

import java.io.File;

/**
 * Created by fcl on 19.4.12
 * desc:
 */
public class Constants {

    //配置目录
    public static File mExternalStorage = Environment.getExternalStorageDirectory();
    public static final String DIR_PROJECT = "/myb/" + ProHelper.getApplication().getAppFlavor() + "/";

    public static final String SAVE_GLIDE_IMAGE = mExternalStorage + DIR_PROJECT;         //Glide图片缓存路径
    public static final String SAVE_EXCEPTION_PATH = mExternalStorage + DIR_PROJECT + "exception/"; //异常文件保存路径

    //文件路径
    public static final String TALK_VOICE_PATH = mExternalStorage + Constants.DIR_MEDIA; //语音收发路径
    public static final String TALK_IMAGE_PATH = mExternalStorage + Constants.DIR_IMAGE; //图片收发路径
    public static final String TALK_VIDEO_PATH = mExternalStorage + Constants.DIR_VIDEO; //视频收发路径
    public static final String SAVE_VOICE_PATH = mExternalStorage + Constants.DIR_VOICE; //语音合成保存路径
    public static final String SAVE_FILE_PATH = mExternalStorage + Constants.DIR_FILE;   //文件保存路径
    public static final String SAVE_FILE_LOG = mExternalStorage + Constants.DIR_LOG;     //日志文件
    public static final String SAVE_IMAGE_TEMP_PATH = mExternalStorage + Constants.DIR_IMAGE_TEMP;  //图片保存路径
    public static final String SAVE_FILE_DOWNLOAD_PATH = mExternalStorage + Constants.DIR_DOWNLOAD; //文件下载保存路径


    public static final String DIR_DOWNLOAD = DIR_PROJECT + "download/"; //文件下载
    public static final String DIR_MEDIA = DIR_PROJECT + "media/"; //语音
    public static final String DIR_VOICE = DIR_PROJECT + "voice/"; //讯飞语音合成
    public static final String DIR_IMAGE = DIR_PROJECT + "image/"; //图片
    public static final String DIR_VIDEO = DIR_PROJECT + "video/"; //视频
    public static final String DIR_FILE = DIR_PROJECT + "file/";   //文件
    public static final String DIR_LOG = DIR_PROJECT + "log/";     //日志
    public static final String DIR_IMAGE_TEMP = DIR_PROJECT + "temp/"; //临时目录

    public static final String FILE_PROVIDER_AUTHORITY = BuildConfig.APPLICATION_ID + ".fileprovider";

    public static final int TAB_FIRST = 0;
    public static final int TAB_MONEY = 1;
    public static final int TAB_FIND = 2;
    public static final int TAB_MINE = 3;


    //相机路径
    public static String PHOTOFILEPATH = "";
}
