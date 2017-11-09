package com.huoli.test;

import org.junit.Assert;
import org.junit.Test;

import com.huoli.utils.StringSearchUtil;

/**
 * 字符串查询工具测试 <br>
 * 版权：Copyright (c) 2011-2014<br>
 * 公司：北京活力天汇<br>
 * 作者：童凡<br>
 * 版本：1.0<br>
 * 创建日期：2014年9月24日<br>
 */
public class StringSearchUtilTest {
	@Test
	public void test1() {
		Assert.assertEquals("丽江", StringSearchUtil.search("丽江-武汉", "-", 0));
		Assert.assertEquals("武汉", StringSearchUtil.search("丽江-武汉", "-", StringSearchUtil.POS_LAST));
		Assert.assertEquals("丽江", StringSearchUtil.search("丽江", "-", 0));
		Assert.assertEquals(null, StringSearchUtil.search("丽江", "-", 1));

		Assert.assertEquals("福州", StringSearchUtil.search("丽江-武汉-福州", "-", "武汉", StringSearchUtil.POS_NEXT));
		Assert.assertEquals("丽江", StringSearchUtil.search("丽江-武汉-福州", "-", "武汉", StringSearchUtil.POS_PREVIOUS));
		Assert.assertEquals(null, StringSearchUtil.search("丽江-武汉-福州", "-", "福州", StringSearchUtil.POS_NEXT));
		Assert.assertEquals(null, StringSearchUtil.search("丽江-武汉-福州", "-", "丽江", StringSearchUtil.POS_PREVIOUS));
		Assert.assertEquals(null, StringSearchUtil.search("天津-天津", "-", "天津", StringSearchUtil.POS_PREVIOUS));

		Assert.assertEquals("1234567", StringSearchUtil.findMatch("测试1234567测试", "(?<=测试).*?(?=测试)"));
	}

}
