/*
  Title: IThreadPoolManager.java
  Description:
  Copyright: Copyright (c) 2013-2015 luoxudong.com
  Company: 个人
  Author: 罗旭东 (hi@luoxudong.com)
  Date: 2015年7月13日 上午10:38:28
  Version: 1.0
 */
package com.nxin.base.common.threadpool.interfaces;

import com.nxin.base.common.threadpool.manager.BaseThreadPool;
import com.nxin.base.common.threadpool.manager.ThreadTaskObject;

/** 
 * ClassName: IThreadPoolManager
 * Description:线程池管理接口
 * date: 2016/5/20.
 */
public interface IThreadPoolManager {
	/**
	 * 往线程池中增加一个线程任务
	 * @param task 线程任务
	 */
	void addTask(ThreadTaskObject task);

	/**
	 * 
	 * @description:获取指定类型的线程池，如果不存在则创建
	 * @param @param ThreadPoolType
	 * @return BaseThreadPool
	 * @throws
	 */
	BaseThreadPool getThreadPool(int threadPoolType);
	
	/**
	 * 从线程队列中移除一个线程任务
	 * @param task 线程任务
	 * @return 是否移除成功
	 */
	boolean removeTask(ThreadTaskObject task);
	
	/**
	 * 停止所有任务
	 */
	void stopAllTask();
}
