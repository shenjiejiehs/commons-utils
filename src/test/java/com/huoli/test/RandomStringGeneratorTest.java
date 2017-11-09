package com.huoli.test;

import org.junit.Assert;
import org.junit.Test;

import com.huoli.utils.RandomStringGenerator;

public class RandomStringGeneratorTest {
	@Test
	public void test() {
		System.out.println(RandomStringGenerator.generalRandomString(6, true, true, true));
		String str = RandomStringGenerator.generalRandomString(4, true, true, true);
		Assert.assertEquals(str.length(), 4);
		
		str = RandomStringGenerator.generalRandomString("0123456789", 5);
		Assert.assertEquals(str.length(), 5);
	}
	
	
}
