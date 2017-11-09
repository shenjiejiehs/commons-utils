package com.huoli.test;

import java.util.Date;

import org.junit.Assert;
import org.junit.Test;

import com.huoli.utils.HexUtil;

public class HexUtilTest {
	@Test
	public void testToHex() {
		int sum = 3972493 + 10000000;
		int o = 5;
		String str = HexUtil.intToHex(sum, o);

		// System.out.println(ServerUtil.numberToHex(1 + 888888));

		Assert.assertEquals("测试加密数字int", "PXGFA", str);

		Assert.assertEquals("测试解密数字int", HexUtil.hexToInt(str, o), sum);

		Number number = -Long.valueOf(3901727 + new Date().getTime());
		String m = HexUtil.numberToHex(number.longValue());
		Assert.assertEquals("测试解密数字long", number, HexUtil.hexToNumber(m));
		
		System.out.println(HexUtil.hexToNumber("SXLN"));
	}
}
