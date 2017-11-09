package com.huoli.utils;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 描述：检测运行时间<br>
 * 版权：Copyright (c) 2011-2012<br>
 * 公司：北京活力天汇<br>
 * 作者：童凡<br>
 * 版本：1.0<br>
 * 创建日期：2012-6-4<br>
 */
public class RunTimer {
	private static Logger logger = LoggerFactory.getLogger(RunTimer.class);
	private static long start;

	public static void start() {
		start = System.currentTimeMillis();
	}

	public static void end() {
		logger.debug("耗时：" + (System.currentTimeMillis() - start) + "毫秒");
	}
}
