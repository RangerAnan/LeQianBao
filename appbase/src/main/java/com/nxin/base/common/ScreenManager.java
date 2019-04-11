package com.nxin.base.common;

import android.support.v4.app.FragmentActivity;
import android.util.Log;


import java.util.Stack;

/**
 * activity管理器
 * Created by fcl
 */
public class ScreenManager {


    /**
     * ScreenManager 单例模式
     */
    private static final ScreenManager instance = new ScreenManager();
    /**
     * Activity堆栈 单例模式
     */
    private static final Stack<FragmentActivity> activityList = new Stack<>();
    // TAG标签
    public final String TAG = this.getClass().getSimpleName();

    // 禁止创建
    private ScreenManager() {

    }

    public static ScreenManager getInstance() {
        return instance;
    }

    /**
     * 获取当前活动的activity
     *
     * @return
     */
    public FragmentActivity currentActivity() {
        if (activityList.size() == 0) {
            Log.i(TAG, "activityList堆栈 size = 0");
            return null;
        }
        //查看栈顶对象而不移除它
        FragmentActivity fragmentActivity = activityList.peek();
        Log.i(TAG, "获取当前activityList堆栈名称:" + fragmentActivity.getClass().getSimpleName());
        return fragmentActivity;
    }

    /**
     * 入栈
     *
     * @param activity
     */
    public void pushActivity(FragmentActivity activity) {
        if (activity == null) {
            Log.e(TAG, "传入的参数为空");
            return;
        }
        activityList.add(activity);
        Log.i(TAG, "入栈:" + activity.getClass().getSimpleName());
    }

    /**
     * 出栈
     *
     * @param activity
     */
    public void popActivity(FragmentActivity activity) {
        if (activity == null) {
            Log.e(TAG, "传入的参数为空");
            return;
        }
        activity.finish();
        activityList.remove(activity);
        if (activityList.size() < 1) {
            //清空缓存
            //线程池
        }

        Log.i(TAG, "出栈:" + activity.getClass().getSimpleName());
        activity = null;
    }

    public void popActivity(Class clazz) {
        for (int i = 0; i < activityList.size(); i++) {
            FragmentActivity activity = activityList.get(i);
            if (activity.getClass().equals(clazz)) {
                popActivity(activity);
            }
        }
    }


    /**
     * 退出堆栈中所有Activity, 当前的Activity除外
     *
     * @param clazz 当前活动窗口
     */
    public void popAllActivityExceptNamed(Class clazz) {

        //将栈中activity打印出来
        for (int i = 0; i < activityList.size(); i++) {
            Log.i(TAG, "--->>" + TAG + "---" + activityList.get(i));
        }
        while (true) {

            FragmentActivity activity = currentActivity();

            if (activity == null) {
                break;
            }

            if (activity.getClass().equals(clazz)) {
                break;
            }
            popActivity(activity);
        }
    }


    /**
     * 当前栈中是否含有某activity
     */
    public boolean containsActivity(Class clazz) {
        for (int i = 0; i < activityList.size(); i++) {
            if (activityList.get(i).getClass().equals(clazz)) {
                return true;
            }
        }
        return false;
    }


    /**
     * 查看当前栈中的activity
     */
    public Stack<FragmentActivity>  getAllActivities() {
        return activityList;
    }
}