package com.huoli.test;

import static org.junit.Assert.*;

import java.util.List;
import java.util.regex.Pattern;

import org.junit.Test;

import com.huoli.utils.VerifyUtil;

public class VerifyUtilTest {
	@Test
	public void testReplaceMoblie() {
		String phone = "+8618571641120";
		assertEquals("18571641120", VerifyUtil.replaceMoblie(phone));
	}

	@Test
	public void testValidateMoblie() {
		String phone = "18571641120";
		assertEquals(true, VerifyUtil.validateMoblie(phone));
		
		phone = "185 7164 1120";
		assertEquals(true, VerifyUtil.validateMoblie(phone));
		
		phone = "185-7164-1120";
		assertEquals(true, VerifyUtil.validateMoblie(phone));
		
		phone = "185	7164	1120";
		assertEquals(true, VerifyUtil.validateMoblie(phone));
		
		phone = "185	7164	1120        ";
		assertEquals(true, VerifyUtil.validateMoblie(phone));
		
		phone = "+86185	7164	1120        ";
		assertEquals(true, VerifyUtil.validateMoblie(phone));
		phone = "86185	7164	1120        ";
		assertEquals(true, VerifyUtil.validateMoblie(phone));
	}

	@Test
	public void testValidatePhone() {
		String phone = "021-1010555";
		assertEquals(true, VerifyUtil.validatePhone(phone));
	}

	@Test
	public void testValidateMoblieOrEmail() {
		String str = "18571641120";
		assertEquals(true, VerifyUtil.validateMoblieOrEmail(str));

		str = "18571641120@163.com";
		assertEquals(true, VerifyUtil.validateMoblieOrEmail(str));
		
		str = "6212264000036738603533124198811080921";
		assertEquals(false, VerifyUtil.validateMoblieOrEmail(str));
		
		str = "zhong_kai2000@vip.163.com";
		assertEquals(true, VerifyUtil.validateMoblieOrEmail(str));
		
		str = "cc@ecc.cc";
		assertEquals(true, VerifyUtil.validateMoblieOrEmail(str));
		
		str = "heyitang@163.com";
		assertEquals(true, VerifyUtil.validateMoblieOrEmail(str));
		
		str = "heyitang@163.com";
		assertEquals(true, VerifyUtil.validateMoblieOrEmail(str));
	}
	
	@Test
	public void testFind() {
		List<String> list = VerifyUtil.find("1234abc123", "\\d*", 0);
		assertEquals(2, list.size());
		
		list = VerifyUtil.find("1234abc123", "[0-9]", 0);
		assertEquals(7, list.size());
	}
	
	@Test
	public void testReplace() {
		String str = VerifyUtil.replace("1abc3", "\\d", "abc");
		assertEquals(str, "abcabcabc");
	}
	
	@Test
	public void testGetPattern() {
		Pattern p = VerifyUtil.getPattern("\\d");
		Pattern p1 = VerifyUtil.getPattern("\\d");
		assertEquals(p, p1);
	}
	
	@Test
	public void testSplit() {
		String[] str = VerifyUtil.split("fsdfs1234abc123dss", "(1234|123)");
		assertEquals(3, str.length);
	}
}
