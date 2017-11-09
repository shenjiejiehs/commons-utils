package com.huoli.test;

import java.util.Date;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.junit.Assert;
import org.junit.Test;

import com.huoli.utils.CharTypeUtil;
import com.huoli.utils.StringUtil;
import com.huoli.utils.CharTypeUtil.CharType;

/**
 * 描述：字符类型帮助测试<br>
 * 版权：Copyright (c) 2011-2015<br>
 * 公司：北京活力天汇<br>
 * 作者：罗良杰<br>
 * 版本：1.0<br>
 * 创建日期：2015年4月15日<br>
 */
public class CharTypeUtilTest {
	public String UnicodeToString(String str) {
		// Pattern pattern = Pattern.compile("(\\\\u(\\p{XDigit}{4}))");
		Pattern pattern = Pattern.compile("(\\\\x(\\p{XDigit}{2}))");
		Matcher matcher = pattern.matcher(str);
		char ch;
		while (matcher.find()) {
			ch = (char) Integer.parseInt(matcher.group(2), 16);
			str = StringUtil.replace(str, matcher.group(1), ch + "");
		}
		return str;
	}

	@Test
	public void testCheckType() {
		System.out.println(new Date(1494901166000L));
		String str = UnicodeToString("\\xD5\\xC5\\xFE\\x9F\\xB5\\xA4");
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			System.out.println(CharTypeUtil.checkType(c));
		}

		System.out.println((char) 59491);
		System.out.println(CharTypeUtil.checkType('䶮'));
		System.out.println(str);
		str = CharTypeUtil.filter(str, CharType.OTHER, CharType.SPECIAL_LETTER, CharType.SPECIAL_DELIMITER);
		System.out.println(str);

		Assert.assertEquals("测试中文类型：", CharType.CHINESE, CharTypeUtil.checkType('中'));
		Assert.assertNotEquals("测试非中文类型：", CharType.CHINESE, CharTypeUtil.checkType('A'));

		Assert.assertEquals("测试数字类型：", CharType.NUM, CharTypeUtil.checkType('1'));
		Assert.assertNotEquals("测试非数字类型：", CharType.NUM, CharTypeUtil.checkType('中'));

		Assert.assertEquals("测试字母类型：", CharType.LETTER, CharTypeUtil.checkType('a'));
		Assert.assertEquals("测试字母类型：", CharType.LETTER, CharTypeUtil.checkType('B'));
		Assert.assertNotEquals("测试非字母类型：", CharType.LETTER, CharTypeUtil.checkType('2'));

		Assert.assertEquals("测试符号类型：", CharType.DELIMITER, CharTypeUtil.checkType('/'));
		Assert.assertEquals("测试符号类型：", CharType.DELIMITER, CharTypeUtil.checkType('#'));
		Assert.assertNotEquals("测试非符号类型：", CharType.DELIMITER, CharTypeUtil.checkType('♡'));

		Assert.assertEquals("测试其他类型：", CharType.OTHER, CharTypeUtil.checkType('♡'));
		Assert.assertEquals("测试其他类型：", CharType.OTHER, CharTypeUtil.checkType('❤'));
		Assert.assertNotEquals("测试非其他类型：", CharType.OTHER, CharTypeUtil.checkType('1'));

		Assert.assertEquals("测试特殊字母：", CharType.SPECIAL_LETTER, CharTypeUtil.checkType('ç'));
		Assert.assertEquals("测试特殊字母：", CharType.SPECIAL_LETTER, CharTypeUtil.checkType('ï'));
		Assert.assertNotEquals("测试非特殊字母：", CharType.SPECIAL_LETTER, CharTypeUtil.checkType('1'));

		Assert.assertEquals("测试特殊符号：", CharType.SPECIAL_DELIMITER, CharTypeUtil.checkType('´'));
		Assert.assertEquals("测试特殊符号：", CharType.SPECIAL_DELIMITER, CharTypeUtil.checkType('¿'));
		Assert.assertNotEquals("测试非特殊符号：", CharType.SPECIAL_DELIMITER, CharTypeUtil.checkType('1'));
	}

	@Test
	public void testContains() {
		Assert.assertEquals("测试包含中文：", true, CharTypeUtil.contains("上海123", CharType.CHINESE));
		Assert.assertEquals("测试包含中文：", true, CharTypeUtil.contains("A北京A", CharType.CHINESE));
		Assert.assertEquals("测试不包含中文：", false, CharTypeUtil.contains("ABFFAA", CharType.CHINESE));
		//
		Assert.assertEquals("测试包含数字：", true, CharTypeUtil.contains("第三方1234士大夫", CharType.NUM));
		Assert.assertEquals("测试包含数字：", true, CharTypeUtil.contains("REER433FD", CharType.NUM));
		Assert.assertEquals("测试不包含数字：", false, CharTypeUtil.contains("ABFFAA", CharType.NUM));
		//
		Assert.assertEquals("测试包含字母：", true, CharTypeUtil.contains("123ABFFAA123", CharType.LETTER));
		Assert.assertEquals("测试包含字母：", true, CharTypeUtil.contains("REER433FD", CharType.LETTER));
		Assert.assertEquals("测试不包含字母：", false, CharTypeUtil.contains("上海", CharType.LETTER));

		Assert.assertEquals("测试包含符号：", true, CharTypeUtil.contains("1343#34\5", CharType.DELIMITER));
		Assert.assertEquals("测试包含符号：", true, CharTypeUtil.contains("444,.7", CharType.DELIMITER));
		Assert.assertEquals("测试不包含符号：", false, CharTypeUtil.contains("上海", CharType.DELIMITER));

		Assert.assertEquals("测试包含其他：", true, CharTypeUtil.contains("❤️😆😛", CharType.OTHER));
		Assert.assertEquals("测试包含其他：", true, CharTypeUtil.contains("。、】【", CharType.OTHER));
		Assert.assertEquals("测试不包含其他：", false, CharTypeUtil.contains("上海", CharType.OTHER));

		Assert.assertEquals("测试包含特殊字母：", true, CharTypeUtil.contains("Â", CharType.SPECIAL_LETTER));
		Assert.assertEquals("测试包含特殊字母：", true, CharTypeUtil.contains("ÒÒÒ", CharType.SPECIAL_LETTER));
		Assert.assertEquals("测试不包含特殊字母：", false, CharTypeUtil.contains("123", CharType.SPECIAL_LETTER));

		Assert.assertEquals("测试包含特殊符号：", true, CharTypeUtil.contains("´", CharType.SPECIAL_DELIMITER));
		Assert.assertEquals("测试包含特殊符号：", true, CharTypeUtil.contains("¿", CharType.SPECIAL_DELIMITER));
		Assert.assertEquals("测试不包含特殊符号：", false, CharTypeUtil.contains("123", CharType.SPECIAL_DELIMITER));
	}

	@Test
	public void testFilter() {
		Assert.assertEquals("测试过滤中文：", "123", CharTypeUtil.filter("上海123", CharType.CHINESE));
		Assert.assertEquals("测试过滤中文：", "AA", CharTypeUtil.filter("A北京A", CharType.CHINESE));
		Assert.assertEquals("测试过滤中文：", "ABFFAA", CharTypeUtil.filter("ABFFAA", CharType.CHINESE));

		Assert.assertEquals("测试过滤数字：", "上海", CharTypeUtil.filter("上海123", CharType.NUM));
		Assert.assertEquals("测试过滤数字：", "北京", CharTypeUtil.filter("123北京123", CharType.NUM));
		Assert.assertEquals("测试过滤数字：", "ABFFAA", CharTypeUtil.filter("ABFFAA", CharType.NUM));

		Assert.assertEquals("测试过滤字母：", "上海123", CharTypeUtil.filter("上海123", CharType.LETTER));
		Assert.assertEquals("测试过滤字母：", "北京", CharTypeUtil.filter("dsf北京sdfsdf", CharType.LETTER));
		Assert.assertEquals("测试过滤字母：", "234❤️北京", CharTypeUtil.filter("234❤️北京sdfsdf", CharType.LETTER));

		Assert.assertEquals("测试过滤符号：", "1343345", CharTypeUtil.filter("1343#34/5", CharType.DELIMITER));
		Assert.assertEquals("测试过滤符号：", "4447", CharTypeUtil.filter("444,.7", CharType.DELIMITER));
		Assert.assertEquals("测试过滤符号：", "上海", CharTypeUtil.filter("上海", CharType.DELIMITER));

		Assert.assertEquals("测试过滤其他：", "1", CharTypeUtil.filter("❤️😆😛1", CharType.OTHER));
		Assert.assertEquals("测试过滤其他：", "", CharTypeUtil.filter("。、】【", CharType.OTHER));
		Assert.assertEquals("测试过滤其他：", "上海", CharTypeUtil.filter("上海", CharType.OTHER));

		Assert.assertEquals("测试过滤其他和符号：", "上海",
				CharTypeUtil.filter(",.❤️😆😛上海。、】【", CharType.OTHER, CharType.DELIMITER));

		Assert.assertEquals("测试过滤中文、字母、数字：", ",.❤️😆😛。、】【",
				CharTypeUtil.filter(",.❤️😆😛GFDG上海dsfs312。、】【", CharType.CHINESE, CharType.NUM, CharType.LETTER));

		Assert.assertEquals("测试过滤其他与特殊字母：", "第三方1234士大夫", CharTypeUtil.filter("第Â三ç方1234士ÒÒÒ大夫❤️😆😛âå。、】¿´【",
				CharType.OTHER, CharType.SPECIAL_LETTER, CharType.SPECIAL_DELIMITER));
	}

	@Test
	public void testIsAllChineseName() {
		String name = "𥖄䶮䮞";
		
		boolean b = CharTypeUtil.isAllChinese(name);
		Assert.assertTrue(b);
	}
}
