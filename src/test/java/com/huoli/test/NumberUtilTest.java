package com.huoli.test;

import org.junit.Assert;
import org.junit.Test;

import com.huoli.utils.NumberUtil;

/**
 * 描述：数字帮助测试<br>
 * 版权：Copyright (c) 2011-2015<br>
 * 公司：北京活力天汇<br>
 * 作者：罗良杰<br>
 * 版本：1.0<br>
 * 创建日期：2016年10月14日<br>
 */
public class NumberUtilTest {
	@Test
	public void testCountDis() {
		Assert.assertEquals("测试折扣", NumberUtil.countDis(20, 100, 2), 2.0, 0);
		
		Assert.assertEquals("测试折扣", NumberUtil.countDis(50, 100, 2), 5.0, 0);
		
		Assert.assertEquals("测试折扣", NumberUtil.countDis(100, 100, 2), 10.0, 0);
		
		Assert.assertEquals("测试折扣", 4.8, NumberUtil.countDis(540, 1130, 1), 0);
	}
	
	@Test
	public void testCountPercentage() {
		Assert.assertEquals("测试百分比", NumberUtil.countPercentage(20, 100, 2), 20, 0);
		
		Assert.assertEquals("测试百分比", NumberUtil.countPercentage(50, 100, 2), 50, 0);
		
		Assert.assertEquals("测试百分比", NumberUtil.countPercentage(100, 100, 2), 100, 0);
		
		Assert.assertEquals("测试百分比", NumberUtil.countPercentage(540, 1130, 1), 48, 0);
	}
}
