package com.huoli.test;

import org.junit.Assert;
import org.junit.Test;

import com.huoli.utils.ZipUtil;

/**
 * 描述：压缩和解压<br>
 * 版权：Copyright (c) 2011-2015<br>
 * 公司：北京活力天汇<br>
 * 作者：罗良杰<br>
 * 版本：1.0<br>
 * 创建日期：2016年5月26日<br>
 */
public class ZipUtilTest {
	@Test
	public void test() throws Exception {
		String str = "{\"companyCode\":\"MU\",\"no\":\"MU5693\",\"dep\":\"PEK\",\"arr\":\"SHA\",\"depDate\":\"2016-06-06\",\"arrDate\":\"2016-06-06\",\"deptime\":\"2016-06-06 07:55\",\"arrtime\":\"2016-06-06 12:45\",\"model\":\"73E\",\"stop\":1,\"meal\":true,\"yprice\":1240.0,\"surcharge\":50.0,\"baseCodes\":\"Y,J,F\"}";
		String compress = ZipUtil.compress(str, "ISO-8859-1");
//		System.out.println(compress);
		
		String uncompress = ZipUtil.uncompress(compress, "ISO-8859-1");
//		System.out.println(uncompress);
		
		Assert.assertEquals("测试中文类型：", uncompress, str);
	}
}