package com.huoli.test;

import org.junit.Assert;
import org.junit.Test;

import com.huoli.utils.HideUtil;

/**
 * 描述：隐藏字符串<br>
 * 版权：Copyright (c) 2011-2015<br>
 * 公司：北京活力天汇<br>
 * 作者：罗良杰<br>
 * 版本：1.0<br>
 * 创建日期：2016年11月21日<br>
 */
public class HideUtilTest {
	@Test
	public void testHideValue() {
		String str = "abcdef";
		Assert.assertEquals("ab*def", HideUtil.hideValue(str, 2, 3));
		Assert.assertEquals("ab****", HideUtil.hideValue(str, 2, 7));
		Assert.assertEquals("ab****", HideUtil.hideValue(str, 2, 6));
	}

	@Test
	public void testHideName() {
		Assert.assertEquals("**杰", HideUtil.hideName("罗良杰"));
		Assert.assertEquals("********9", HideUtil.hideName("123456789"));
	}

	@Test
	public void testHideEndName() {
		Assert.assertEquals("罗**", HideUtil.hideEndName("罗良杰"));
		Assert.assertEquals("1********", HideUtil.hideEndName("123456789"));
	}

	@Test
	public void testHideMobile() {
		Assert.assertEquals("1363****753", HideUtil.hideMobile("13638675753"));
		Assert.assertEquals("1806****131", HideUtil.hideMobile("18062131131"));
	}

	@Test
	public void testHideMail() {
		Assert.assertEquals("l***********34@gmail.com", HideUtil.hideMail("lijie117236234@gmail.com"));
		Assert.assertEquals("1********31", HideUtil.hideMail("18062131131"));
		Assert.assertEquals("l**lj@huoli.com", HideUtil.hideMail("luolj@huoli.com"));
	}

	@Test
	public void testHideCard() {
		Assert.assertEquals("420206********0819", HideUtil.hideCard("420206196804180819"));
		Assert.assertEquals("420109********0619", HideUtil.hideCard("420109197806280619"));
		Assert.assertEquals("420506********0920", HideUtil.hideCard("420506197801280920"));
	}
}
