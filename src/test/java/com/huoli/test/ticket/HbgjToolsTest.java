package com.huoli.test.ticket;   

import org.junit.Assert;
import org.junit.Test;

import com.huoli.utils.StringUtil;
import com.huoli.utils.ticket.HbgjTools;

/**
 * 航班管家机票部分工具测试 <br>
 * 版权：Copyright (c) 2011-2014<br>
 * 公司：北京活力天汇<br>
 * 作者：罗良杰<br>
 * 版本：1.0<br>
 * 创建日期：2014年9月24日<br>
 */
public class HbgjToolsTest {
	@Test
	public void testResources() {
		Assert.assertEquals("test", HbgjTools.getStringResources("test"));
		
		Assert.assertEquals(true, HbgjTools.getBooleanResources("test-boolean"));
		
		Assert.assertEquals(1, HbgjTools.getIntegerResources("test-int"));
	}
	
	@Test
	public void testVersionAndPlatForm() {
		Assert.assertEquals(true, HbgjTools.isVersion("3.6.9", "3.6.8", false));
		
		Assert.assertEquals(true, HbgjTools.isVersion("3.6.8", "3.6.8", true));
		
		Assert.assertEquals(true, HbgjTools.isLtVersion("3.6.7", "3.6.8", false));
		
		Assert.assertEquals("iphone", HbgjTools.getClientType("appstore,ios7.0.4,hbgj,3.8.2,iPhone6.2"));
		Assert.assertEquals("iphone", HbgjTools.getClientType(""));
		Assert.assertEquals("android", HbgjTools.getClientType("android"));
	}
	
	@Test
	public void testOther() {
		Assert.assertEquals(true, HbgjTools.isHMT("HKG"));
		
		Assert.assertEquals(false, HbgjTools.isHMT("PEK"));
		
		Assert.assertEquals(true, HbgjTools.isLikeFlyNo("HU788A"));
		
		Assert.assertEquals(false, HbgjTools.isLikeFlyNo("HUA824Y"));
		
		Assert.assertEquals(true, HbgjTools.isLikeCom("9C"));
		
		Assert.assertEquals("ZH87", HbgjTools.replaceFlyNoZero("ZH087"));
		
		Assert.assertEquals("ZH8712", HbgjTools.replaceFlyNoZero("ZH8712"));
		
		Assert.assertEquals("&amp;&#39;", HbgjTools.convertToHtml("&'"));
		
		Assert.assertEquals("\r", HbgjTools.convertToHtmlX("\\6"));
		
		int[] is = {1, 2, 3};
		Assert.assertArrayEquals(is, HbgjTools.stringToArray("1,2,3"));
		
		Assert.assertEquals("testtest", HbgjTools.cleanText("testtest"));
		
		String url = "http://jp.rsscc.com/about?a=1&b=1&c=5";
		String encryptstr = HbgjTools.encrypt(url);
		Assert.assertEquals("hAzAC3I6hzsS0eTG!fQycu@OWuuyPYEtLJG@b3B8mNlP5pIK@e!Nn2ps2WkcAMkv", encryptstr);
		
		Assert.assertEquals(url, HbgjTools.decrypt(encryptstr));
		
		String test = HbgjTools.getStringResources("test");
		
		Assert.assertEquals("774744D1", HbgjTools.softDog(test));
		Assert.assertEquals("717BE5F4", HbgjTools.softDogNew(test));
		
		Assert.assertEquals("15:32", HbgjTools.getHMTime("2014-10-08 15:32:00"));
		
		String str = "测试";
		Assert.assertEquals(str, HbgjTools.getUtf8(StringUtil.transcodeStr(str, "UTF-8", "ISO-8859-1")));
		
		Assert.assertEquals(str, HbgjTools.getUtf8(StringUtil.transcodeStr(str, "UTF-8", "ISO-8859-1"), "get"));
		
		Assert.assertEquals(str, HbgjTools.getUtf8(str, "post"));
	}
}