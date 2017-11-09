package com.huoli.utils.threadpool;

/**
 * 线程池中的执行任务 <br>
 * 版权：Copyright (c) 2011-2014<br>
 * 公司：北京活力天汇<br>
 * 作者：童凡<br>
 * 版本：1.0<br>
 * 创建日期：2014年8月29日<br>
 */
public abstract class PoolTask implements Runnable {
	/** 重试的最大次数限制 */
	private int retryLimit;
	/** 重试次数 */
	private int retry;

	/**
	 * 重试次数增加1次
	 */
	public void retryAdd() {
		retry++;
	}

	/**
	 * 能不能重复执行<br>
	 * 检测的逻辑是先增加再判断,所以这里判断符号使用<=
	 * 
	 * @return
	 */
	public boolean isRetriable() {
		return retry <= retryLimit;
	}

	public int getRetryLimit() {
		return retryLimit;
	}

	public void setRetryLimit(int retryLimit) {
		this.retryLimit = retryLimit;
	}

	public int getRetry() {
		return retry;
	}

	public void setRetry(int retry) {
		this.retry = retry;
	}

}
