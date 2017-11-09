package com.huoli.utils;   

import java.text.DecimalFormat;

/**
 * 格式化工具类 <br>
 * 版权：Copyright (c) 2011-2013<br>
 * 公司：北京活力天汇<br>
 * 作者：罗良杰<br>
 * 版本：1.0<br>
 * 创建日期：2013-9-10<br>
 */
public class NumberFormatUtil {
	/**
	 * 数值格式化模式
	 * 获取数字或小数
	 * #.#模式
	 */
	public static final DecimalFormat INT_DOUBLE = new DecimalFormat("#.#");
	
	/**
	 * 格式化数字
	 * 默认格式化
	 */
	public static String defaultFormatNumber(Number n) {
		return formatNumber(n, INT_DOUBLE);
	}
	
	/**
	 * 格式化数字
	 */
	public static String formatNumber(Number n, DecimalFormat df) {
		return df.format(n.doubleValue());
	}
}
