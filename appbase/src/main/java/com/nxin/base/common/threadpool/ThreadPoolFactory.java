/*
  Title: ThreadPoolFactory.java
  Description:
  Copyright: Copyright (c) 2013-2015 luoxudong.com
  Company: 个人
  Author: 罗旭东 (hi@luoxudong.com)
  Date: 2015年7月13日 上午10:37:27
  Version: 1.0
 */
package com.nxin.base.common.threadpool;

import com.nxin.base.common.threadpool.interfaces.IThreadPoolManager;
import com.nxin.base.common.threadpool.manager.ThreadPoolManager;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;

/**
 * ClassName: ThreadPoolFactory
 * Description:线程池工厂类
 * date: 2016/5/20.
 */
public class ThreadPoolFactory {

    private static ExecutorService pool = null;

    public static IThreadPoolManager getThreadPoolManager() {
        return SingletonFactory.getInstance(ThreadPoolManager.class);
    }

    public static ExecutorService getFexThreadPoolManager() {
        InitThreadExecutor();
        return pool;
    }

    private static void InitThreadExecutor() {
        if (pool == null) {
            pool = Executors.newCachedThreadPool();
        }
    }
}
