package com.huoli.test;

import java.util.Date;
import java.util.TimeZone;

import org.junit.Test;

import com.huoli.utils.DateTimeUtil;

import static org.junit.Assert.*;

/**
 * 日期时间工具类测试 <br>
 * 版权：Copyright (c) 2011-2014<br>
 * 公司：北京活力天汇<br>
 * 作者：童凡<br>
 * 版本：1.0<br>
 * 创建日期：2014年9月24日<br>
 */
public class DateTimeUtilTest {
	@Test
	public void test1() {
		assertEquals("2014-11-21 21:00:00", DateTimeUtil.format(DateTimeUtil.getFlightTime("21:00", "2014-11-21"), DateTimeUtil.YYYYMMDDHHmmss));

		assertEquals("2014-11-22 01:00:00", DateTimeUtil.format(DateTimeUtil.getFlightTime("01:00", "2014-11-21"), DateTimeUtil.YYYYMMDDHHmmss));

		assertEquals("2014-11-21 21:00:00", DateTimeUtil.format(DateTimeUtil.getTicketTime("21:00", "2014-11-21"), DateTimeUtil.YYYYMMDDHHmmss));

		assertEquals("2014-11-21 01:00:00", DateTimeUtil.format(DateTimeUtil.getTicketTime("0100", "2014-11-21"), DateTimeUtil.YYYYMMDDHHmmss));

		int i = DateTimeUtil.getDateWeek("2014-12-04");
		assertEquals(i, 4);

		assertEquals(DateTimeUtil.getDateWeek("2014-12-03", "yyyy-MM-dd"), 3);

		assertEquals(DateTimeUtil.getDateWeek("2015-05-23"), 6);

		assertEquals(DateTimeUtil.getDateWeek("2015-05-24"), 0);

		Date time = DateTimeUtil.now();

		assertEquals(DateTimeUtil.toGreenWichTime(time, TimeZone.getDefault()).getTime(), DateTimeUtil.toGreenWichTime(time).getTime());
	}

	/**
	 * 测试就近补年份方法
	 */
	@Test
	public void testFillYearNearly() {
		DateTimeUtil.setNow("2015-12-20");
		assertEquals("2016-01-23", DateTimeUtil.fillYearNearly("01-23"));
		DateTimeUtil.setNow("2016-01-20");
		assertEquals("2015-12-23", DateTimeUtil.fillYearNearly("12-23"));
		assertEquals("2015-08-23", DateTimeUtil.fillYearNearly("08-23"));
	}

	/**
	 * 测试日期格式判断
	 */
	@Test
	public void testIsDate() {
		assertTrue(DateTimeUtil.isDate("2015-12-30"));
		assertFalse(DateTimeUtil.isDate(" 2015-12-30"));
		assertFalse(DateTimeUtil.isDate("2015-12-30 12:00:00"));

		assertTrue(DateTimeUtil.isFullDate("2015-12-30 12:00:00"));
		assertFalse(DateTimeUtil.isFullDate("2015-12-30 12:00:00.123"));
	}
}
