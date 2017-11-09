package com.huoli.test;

import org.junit.Assert;
import org.junit.Test;

import com.huoli.utils.VersionUtil;

public class VersionUtilTest {
	@Test
	public void test() {
		//比较大于
		Assert.assertEquals(true, VersionUtil.compareVersion("3.6.9", "3.6.8", false));
		
		Assert.assertEquals(1, VersionUtil.compareVersion("3.6.9", "3.6.8"));
		
		Assert.assertEquals(-1, VersionUtil.compareVersion("test", "3.6.8"));
		
		//比较等于
		Assert.assertEquals(true, VersionUtil.compareVersion("3.6.8", "3.6.8", true));

		Assert.assertEquals(false, VersionUtil.compareVersion("3.6.8", "3.6.8", false));
		
		
		Assert.assertEquals(true, VersionUtil.isVersion("3.6.8"));
		
		Assert.assertEquals(false, VersionUtil.isVersion("a.b.c"));
		
		Assert.assertEquals("003006008", VersionUtil.versionStr("3.6.8"));
		
		
		//比较大于
		Assert.assertEquals(false, VersionUtil.compareVersion("5.0", "5.1", true));
		Assert.assertEquals(true, VersionUtil.compareVersion("5.0", "4.9", true));
		Assert.assertEquals(true, VersionUtil.compareVersion("5.0", "5.0", true));
	}
}
