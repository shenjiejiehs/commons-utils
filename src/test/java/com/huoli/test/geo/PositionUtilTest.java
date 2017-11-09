package com.huoli.test.geo;

import org.junit.Assert;
import org.junit.Test;

import com.huoli.utils.geo.Position;
import com.huoli.utils.geo.PositionUtil;

/**
 * 距离工具测试 <br>
 * 版权：Copyright (c) 2011-2015<br>
 * 公司：北京活力天汇<br>
 * 作者：童凡<br>
 * 版本：1.0<br>
 * 创建日期：2015年1月4日<br>
 */
public class PositionUtilTest {
	@Test
	public void test1() {
		Assert.assertEquals("测试", 10572.0, PositionUtil.getDistance(43.677219, -79.630543, 40.080104, 116.5846), 1);
	}

	@Test
	public void test2() {
		Assert.assertEquals("测试", 1077, PositionUtil.getDistance(31.19787, 121.3363, 40.080104, 116.5846), 1);
	}

	@Test
	public void testSimple1() {
		Assert.assertEquals("测试", 13581.0, PositionUtil.getDistanceSimple(43.677219, -79.630543, 40.080104, 116.5846), 1);
	}

	@Test
	public void testSimple2() {
		Assert.assertEquals("测试", 1078.0, PositionUtil.getDistanceSimple(31.19787, 121.3363, 40.080104, 116.5846), 1);
	}
	
	@Test
	public void testRectangle() {
		Position p = new Position(31.19787, 121.3363);
		
		Position[] ps = PositionUtil.getRectangle(p, 15 * 1000);
		Assert.assertEquals("测试", ps.length, 2, 1);
	}
}
