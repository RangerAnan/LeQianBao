/*
  Title: BaseThreadPool.java
  Description:
  Copyright: Copyright (c) 2013-2015 luoxudong.com
  Company: 个人
  Author: 罗旭东 (hi@luoxudong.com)
  Date: 2015年7月13日 上午10:39:12
  Version: 1.0
 */
package com.nxin.base.common.threadpool.manager;

import java.util.concurrent.LinkedBlockingDeque;
import java.util.concurrent.ThreadPoolExecutor;
import java.util.concurrent.TimeUnit;

/**
 * ClassName: BaseThreadPool
 * Description:基础线程池
 * date: 2016/5/20.
 */
public class BaseThreadPool extends ThreadPoolExecutor {
    public BaseThreadPool(ThreadPoolParams threadPoolParamter) {
        super(threadPoolParamter.getCorePoolSize(),
                threadPoolParamter.getMaximumPoolSize(),
                threadPoolParamter.getKeepAliveTime(),
                TimeUnit.MILLISECONDS, //线程池维护线程所允许的空闲时间的单位
                new LinkedBlockingDeque<Runnable>(threadPoolParamter.getPoolQueueSize()), //线程池所使用的缓冲队列
                new CallerRunsPolicy());//线程池对拒绝任务的处理策略,重试添加当前的任务，他会自动重复调用execute()方法
        this.allowCoreThreadTimeOut(threadPoolParamter.isAllowCoreThreadTimeOut());

    }
}
