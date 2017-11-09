package com.huoli.utils.threadpool;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 线程池中的任务线程 <br>
 * 版权：Copyright (c) 2011-2014<br>
 * 公司：北京活力天汇<br>
 * 作者：童凡<br>
 * 版本：1.0<br>
 * 创建日期：2014年8月29日<br>
 */
public class PoolTaskThread implements Runnable {
	private static Logger logger = LoggerFactory.getLogger(PoolTaskThread.class);
	/** 任务队列 */
	private LinkedBlockingQueue<PoolTask> taskQueue;
	/** 线程池信号量 */
	private Semaphore semaphore;
	/** 需要运行的线程池任务 */
	private PoolTask poolTask;
	/** 线程池名称 */
	private String poolName;

	public PoolTaskThread(PoolTask poolTask, Semaphore semaphore, LinkedBlockingQueue<PoolTask> taskQueue, String poolName) {
		super();
		this.taskQueue = taskQueue;
		this.semaphore = semaphore;
		this.poolTask = poolTask;
		this.poolName = poolName;
	}

	public void run() {
		try {
			poolTask.run();
		} catch (Exception e) {
			// 增加重试
			poolTask.retryAdd();
			// 判断是否能重试,能重试则放入到队列中
			if (poolTask.isRetriable()) {
				try {
					taskQueue.put(poolTask);
				} catch (InterruptedException e1) {
					logger.error("ERROR", e);
				}
			}
			logger.error("ERROR", e);
		} finally {
			logger.trace("释放信号(" + poolName + "),信号量剩余" + semaphore.availablePermits());
			semaphore.release();
		}

	}
}
