package com.nxin.base.utils;

import android.app.ActivityManager;
import android.content.Context;
import android.util.Log;


/**
 * Created by fcl on 18.4.9
 * desc: 获取系统信息的工具类
 */

public class SystemUtil {


    /**
     * 设备分配给应用的最大堆内存
     */
    public static int getHeapSize(Context context) {
        int heapSize = -1;
        try {
            ActivityManager manager = (ActivityManager) context.getSystemService(Context.ACTIVITY_SERVICE);
            heapSize = manager.getMemoryClass();
            // manafest.xml   android:largeHeap="true"
            int maxHeapSize = manager.getLargeMemoryClass();
            Log.i("SystemUtil", "---maxHeapSize:" + maxHeapSize);
        } catch (Exception e) {
            e.printStackTrace();
        }
        return heapSize;
    }

    //根据手机的分辨率从 dp 的单位 转成为 px(像素)
    public static int dp2px(float dpValue) {
        float scale = ProHelper.getApplication().getResources().getDisplayMetrics().density;
        return (int) (dpValue * scale + 0.5f);
    }

}
