package com.huoli.test;

import org.junit.Assert;
import org.junit.Test;

import com.huoli.utils.IdcardUtil;

public class IdCardUtilTest {
	@Test
	public void testIsIdCard() {
		String card = "420521199011205334";
		Assert.assertEquals("验证是否为身份证", true, IdcardUtil.isIdcard(card));
	}

	@Test
	public void testGetBirthdayByCard() {
		Assert.assertEquals("获取当前身份证的生日", "1990-11-20", IdcardUtil.getBirthdayByCard("420521199011205334"));
		Assert.assertEquals("获取当前身份证的生日", null, IdcardUtil.getBirthdayByCard("420521l99011205334"));
	}

	@Test
	public void testGetDateDiffYearByCard() {
		Assert.assertEquals("获取当前身份证者的年龄", 24, IdcardUtil.getDateDiffYearByCard("420521199011205334", "2015-11-19"));
		Assert.assertEquals("获取当前身份证者的年龄", 25, IdcardUtil.getDateDiffYearByCard("420521199011205334", "2015-11-21"));
	}

	@Test
	public void testGetDateDiffYear() {
		Assert.assertEquals("根据指定日期和生日获取当前年龄", 24, IdcardUtil.getDateDiffYear("1990-11-20", "2015-10-20"));
	}
	
	@Test
	public void testGetSexByIdcard() throws Exception {
		Assert.assertEquals("根据身份证获取性别", 1, IdcardUtil.getSexByIdcard("420106198801280419"));
		Assert.assertEquals("根据身份证获取性别", 2, IdcardUtil.getSexByIdcard("420106198801280429"));
	}
}
