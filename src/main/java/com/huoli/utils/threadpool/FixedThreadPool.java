package com.huoli.utils.threadpool;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.Semaphore;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 固定数量线程池,通用类 <br>
 * 使用方法:<br>
 * 编写PoolTask的子类,子类需要包含任务执行所需要的资源<br>
 * 放入到队列中,线程池将PoolTask用PoolTaskThread包装,进行执行<br>
 * 线程延时,建议放到PoolTask实现类中实现<br>
 * 降低执行密度可以考虑投放任务到队列的时候延时,或者降低线程数<br>
 * 
 * 
 * 版权：Copyright (c) 2011-2014<br>
 * 公司：北京活力天汇<br>
 * 作者：童凡<br>
 * 版本：1.0<br>
 * 创建日期：2014年8月29日<br>
 */
public class FixedThreadPool implements Runnable {
	private static Logger logger = LoggerFactory.getLogger(FixedThreadPool.class);
	/** 线程池名称 */
	private String poolName;

	private ExecutorService executorService;
	/** 任务队列 */
	private LinkedBlockingQueue<PoolTask> taskQueue;
	/** 信号量，用于控制线程的产生 */
	private Semaphore semaphore;

	public void start() {
		new Thread(this).start();
		logger.info(poolName + "线程池已启动");
	}

	/**
	 * 构造方法
	 * @param poolName 线程池名称
	 * @param size 线程数
	 * @param taskQueue 任务队列
	 */
	public FixedThreadPool(String poolName, int size, LinkedBlockingQueue<PoolTask> taskQueue) {
		this.poolName = poolName;
		executorService = Executors.newFixedThreadPool(size);
		semaphore = new Semaphore(size);
		this.taskQueue = taskQueue;
	}

	/**
	 * 不断从队列里面获取子任务并执行，这是一个一直执行的线程池，<br>
	 * 当队列中没有任务的时候，所有线程会处于阻塞状态
	 * 
	 */
	public void run() {
		while (true) {
			try {
				semaphore.acquire();
				logger.trace("剩余信号量" + "(" + poolName + ")" + semaphore.availablePermits());
				PoolTask poolTask = taskQueue.take();
				executorService.execute(new PoolTaskThread(poolTask, semaphore, taskQueue, poolName));
			} catch (Exception e) {
				logger.error(poolName + ":出错了", e);
			}
			
		}
	}

	public Semaphore getSemaphore() {
		return semaphore;
	}

}
