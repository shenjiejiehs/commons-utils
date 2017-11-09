package com.huoli.test;

import org.junit.Assert;
import org.junit.Test;

import com.huoli.utils.NumberFormatUtil;

public class NumberFormatUtilTest {
	@Test
	public void test() {
		Assert.assertEquals("1", NumberFormatUtil.defaultFormatNumber(1.0));
	}
}
