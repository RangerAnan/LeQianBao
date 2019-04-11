/*
  Title: ThreadTaskObject.java
  Description:
  Copyright: Copyright (c) 2013-2015 luoxudong.com
  Company: 个人
  Author: 罗旭东 (hi@luoxudong.com)
  Date: 2015年7月13日 上午10:42:59
  Version: 1.0
 */
package com.nxin.base.common.threadpool.manager;

import com.nxin.base.common.threadpool.ThreadPoolFactory;
import com.nxin.base.common.threadpool.constant.ThreadPoolTypeEnum;

/**
 * ClassName: ThreadTaskObject
 * Description:基本线程任务，未统一管理，业务中使用的线程都需要继承该类
 * date: 2016/5/20
 */
public class ThreadTaskObject implements Runnable {

    /**
     * 线程池类型
     */
    protected int iThreadPoolType;

    protected String taskName = null;

    public ThreadTaskObject(ThreadPoolTypeEnum threadPoolType, String threadTaskName) {
        initThreadTaskObject(threadPoolType, threadTaskName);
    }

    public ThreadTaskObject(ThreadPoolTypeEnum threadPoolType) {
        initThreadTaskObject(threadPoolType, this.toString());
    }

    /**
     * 在默认线程池中执行
     */
    public ThreadTaskObject() {
        initThreadTaskObject(ThreadPoolTypeEnum.THREAD_TYPE_WORK, this.toString());
    }

    /**
     * 初始化线程任务
     *
     * @param threadPoolType 线程池类型
     * @param threadTaskName 线程任务名称
     */
    private void initThreadTaskObject(ThreadPoolTypeEnum threadPoolType, String threadTaskName) {
        this.iThreadPoolType = threadPoolType.getValue();
        String name = ThreadPoolParams.getInstance(iThreadPoolType).name();
        if (threadTaskName != null) {
            name = name + "_" + threadTaskName;
        }
        setTaskName(name);
    }

    /**
     * 取得线程池类型
     */
    public int getThreadPoolType() {
        return iThreadPoolType;
    }

    /**
     * 开始任务
     */
    public void start() {
        ThreadPoolFactory.getThreadPoolManager().addTask(this);
    }

    /**
     * 取消任务
     */
    public void cancel() {
        ThreadPoolFactory.getThreadPoolManager().removeTask(this);
    }

    @Override
    public void run() {
        if (onRunListener != null) {
            onRunListener.onRun();
        }
    }

    public String getTaskName() {
        return taskName;
    }

    public void setTaskName(String taskName) {
        this.taskName = taskName;
    }

    public interface OnRunListener {
        void onRun();
    }

    OnRunListener onRunListener;

    public void setOnRunListener(OnRunListener onRunListener) {
        this.onRunListener = onRunListener;
    }
}
