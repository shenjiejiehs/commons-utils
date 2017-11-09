package com.huoli.utils;

import java.math.BigDecimal;

/**
 * 描述：数字帮助对象<br>
 * 版权：Copyright (c) 2011-2015<br>
 * 公司：北京活力天汇<br>
 * 作者：罗良杰<br>
 * 版本：1.0<br>
 * 创建日期：2016年10月14日<br>
 */
public class NumberUtil {
	/**
	 * 计算折扣
	 * @param price 价格
	 * @param base 基础价格
	 * @param scale 保留几位小数
	 * @return
	 */
	public static double countDis(Number price, Number base, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		if (price == null || price.doubleValue() == 0) {
			return 0;
		}
		if (base == null || base.doubleValue() == 0) {
			return 10;
		}
		BigDecimal b = new BigDecimal(price.doubleValue());
		BigDecimal basePrice = new BigDecimal(base.doubleValue());
		BigDecimal dis = b.divide(basePrice, scale * 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("10"));
		BigDecimal one = new BigDecimal("1");
		return dis.divide(one, scale, BigDecimal.ROUND_HALF_UP).doubleValue();
	}

	/**
	 * 计算百分率
	 * 
	 * @param numerator 分子
	 * @param denominator 分母
	 * @param scale 保留几位小数
	 * @return
	 */
	public static double countPercentage(Number numerator, Number denominator, int scale) {
		if (scale < 0) {
			throw new IllegalArgumentException("The scale must be a positive integer or zero");
		}
		if (numerator == null || numerator.doubleValue() == 0) {
			return 0;
		}
		if (denominator == null || denominator.doubleValue() == 0) {
			return 100;
		}
		BigDecimal b = new BigDecimal(numerator.doubleValue());
		BigDecimal basePrice = new BigDecimal(denominator.doubleValue());
		BigDecimal dis = b.divide(basePrice, scale * 2, BigDecimal.ROUND_HALF_UP).multiply(new BigDecimal("100"));
		return dis.doubleValue();
	}
}
