package com.nxin.base.model;

import android.os.Environment;

import com.nxin.base.utils.ProHelper;

import java.io.File;

/**
 * Created by fcl on 2017/6/3.
 * description:常量基类
 */

public class BaseConstant {

    //页面标签不可更改
    public static final String page_tag_key = "page_tag";


    //配置目录
    public static File mExternalStorage = Environment.getExternalStorageDirectory();
    public static final String DIR_PROJECT = "/nxin/" + ProHelper.getApplication().getAppFlavor() + "/";

    public static final String SAVE_GLIDE_IMAGE = mExternalStorage + DIR_PROJECT;         //Glide图片缓存路径
    public static final String SAVE_EXCEPTION_PATH = mExternalStorage + DIR_PROJECT + "exception/"; //异常文件保存路径

    public static final float DIALOG_WIDTH = 0.78f;

    public static final String PROGRESS_DIALOG_UNCANCEL = "uncancel";
}
