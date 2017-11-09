package com.huoli.test;

import org.junit.Assert;
import org.junit.Test;

import com.huoli.utils.IpUtil;

public class IpUtilTest {
	@Test
	public void testIpToLong() {
		Assert.assertEquals("ip转换为数字", 454101714, IpUtil.ipToLong("27.17.10.210"));
	}
}
