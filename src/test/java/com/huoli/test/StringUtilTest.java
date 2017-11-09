package com.huoli.test;

import static org.junit.Assert.assertEquals;

import org.junit.Assert;
import org.junit.Test;

import com.huoli.utils.StringUtil;

/**
 * 字符串帮助工具测试类 <br>
 * 版权：Copyright (c) 2011-2014<br>
 * 公司：北京活力天汇<br>
 * 作者：罗良杰<br>
 * 版本：1.0<br>
 * 创建日期：2014年9月24日<br>
 */
public class StringUtilTest {
	@Test
	public void test() {
		Assert.assertEquals("", StringUtil.delNull("null"));
		Assert.assertEquals("testnull", StringUtil.delNull("testnull"));

		Assert.assertEquals(true, StringUtil.isNull("null"));
		Assert.assertEquals(true, StringUtil.isNull(""));
		Assert.assertEquals(false, StringUtil.isNull("testnull"));

		Assert.assertEquals(true, StringUtil.equals("test", "null", "test"));
		Assert.assertEquals(true, StringUtil.equals("null", "null", "test"));
		Assert.assertEquals(false, StringUtil.equals("test1", "null", "test"));

		Assert.assertEquals("", StringUtil.delStr("test", "null", "test"));
		Assert.assertEquals("", StringUtil.delStr("null", "null", "test"));
		Assert.assertEquals("test1", StringUtil.delStr("test1", "null", "test"));

		Assert.assertEquals(true, StringUtil.compareTo("368", "367", false));
		Assert.assertEquals(true, StringUtil.compareTo("368", "368", true));
		Assert.assertEquals(false, StringUtil.compareTo("368", "368", false));

		Assert.assertEquals(true, StringUtil.isLetter("abc"));
		Assert.assertEquals(false, StringUtil.isLetter("abc123"));

		Assert.assertEquals(true, StringUtil.isLetterAndNumber("abc123"));
		Assert.assertEquals(false, StringUtil.isLetterAndNumber("abc123!@#$"));

		Assert.assertEquals(true, StringUtil.isNumber("123"));
		Assert.assertEquals(true, StringUtil.isNumber("-123"));
		Assert.assertEquals(false, StringUtil.isNumber("-12-3"));
		Assert.assertEquals(false, StringUtil.isNumber("adc123"));

		Assert.assertEquals(true, StringUtil.isDouble("123"));
		Assert.assertEquals(true, StringUtil.isDouble("123.123"));
		Assert.assertEquals(true, StringUtil.isDouble("-123.123"));
		Assert.assertEquals(false, StringUtil.isDouble("-123.-123"));
		Assert.assertEquals(false, StringUtil.isDouble("a123.d"));

		String strs[][] = { { "find", "replaceFind" }, { "ice", "replaceIce" } };
		Assert.assertEquals("replaceFind test replaceIce", StringUtil.replace("find test ice", strs));

		Object[] ints = { 1, 2, 3, 4 };
		Assert.assertEquals("1,2,3,4", StringUtil.arrayToString(ints, "", ","));
		Assert.assertEquals("'1','2','3','4'", StringUtil.arrayToString(ints, "'", ","));
		Assert.assertEquals("", StringUtil.arrayToString(null, "'", ""));
		Assert.assertEquals("", StringUtil.arrayToString(new Object[0], "'", ""));

		String str = "测试";
		Assert.assertEquals(str, StringUtil.transcodeStr(StringUtil.transcodeStr(str, "UTF-8", "ISO-8859-1"), "ISO-8859-1", "UTF-8"));

		Assert.assertEquals("1飞是饿额地方123123", StringUtil.filterEmoji("1⭕飞是⭕️😆饿额😆地方😆😛123123"));
		Assert.assertEquals("1飞是饿额地方123123", StringUtil.filterEmoji("1⭕飞是⭕️😆饿额😆地方😆😛12⭕3⭕123"));

		Assert.assertEquals("明", StringUtil.filterEmoji("❤️明"));
		Assert.assertEquals("♡明", StringUtil.filterEmoji("♡明"));

		Assert.assertEquals("7-3104", StringUtil.filterEmoji("7-3104"));
	}

	/**
	 * 测试去除重复字符串的功能
	 */
	@Test
	public void testClearDuplicate() {
		Assert.assertEquals("上海浦东", StringUtil.clearDuplicate("上海浦东上海浦东"));
		Assert.assertEquals("上海浦东", StringUtil.clearDuplicate("上海浦东上海浦东上海浦东"));
		Assert.assertEquals("上海浦东", StringUtil.clearDuplicate("上海浦东上海浦东上海浦东上海浦东"));
		Assert.assertEquals("上海浦东", StringUtil.clearDuplicate("上海浦东上海浦东上海浦东上海浦东上海浦东"));
		Assert.assertEquals("上海上海上海上海上海上海上海", StringUtil.clearDuplicate("上海上海上海上海上海上海上海"));
		Assert.assertEquals("上海", StringUtil.clearDuplicate("上海上海上海上海上海上海", 6));
		Assert.assertEquals("上海", StringUtil.clearDuplicate("上海上海上海上海上海上海", 7));

	}

	/** 测试文本中全角半角符号的转换 */
	@Test
	public void testFull2Half() {
		assertEquals(" ,.()+-*/\\测试", StringUtil.full2Half("　，．（）＋－＊／＼测试"));
		assertEquals("　，测试／", StringUtil.half2Full(" ,测试/"));
	}
}
