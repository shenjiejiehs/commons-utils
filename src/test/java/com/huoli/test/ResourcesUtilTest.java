package com.huoli.test;

import org.junit.Assert;
import org.junit.Test;

import com.huoli.utils.ResourcesUtil;

public class ResourcesUtilTest {
	@Test
	public void test() throws Exception {
		String filename = "wap";
		
		
		ResourcesUtil.load(filename);
		
		Assert.assertEquals("test", ResourcesUtil.getString(filename, "test"));
		
		Assert.assertEquals(true, ResourcesUtil.getBoolean(filename, "test-boolean"));
		
		Assert.assertEquals(new Integer(1), ResourcesUtil.getInt(filename, "test-int"));
	}
}
