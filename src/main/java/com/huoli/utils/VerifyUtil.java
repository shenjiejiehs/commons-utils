package com.huoli.utils;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

/**
 * 描述：验证数据<br>
 * 版权：Copyright (c) 2011-2015<br>
 * 公司：北京活力天汇<br>
 * 作者：罗良杰<br>
 * 版本：1.0<br>
 * 创建日期：2015年5月18日<br>
 */
public class VerifyUtil {
	private static Map<String, Pattern> patterns = new HashMap<String, Pattern>();

	/**
	 * 替换手机号 特殊字符
	 * @param str
	 * @return
	 */
	public static String replaceMoblie(String str) {
		if (StringUtil.isEmpty(str)) {
			return str;
		}

		str = StringUtil.remove(str, "+86");
		str = StringUtil.remove(str, "-");
		str = StringUtil.remove(str, " ");
		str = StringUtil.remove(str, "	");
		if (StringUtil.length(str) == 13) {
			str = StringUtil.removeStart(str, "86");
		}
		return str.trim();
	}

	/**
	 * 验证手机号
	 */
	public static boolean validateMoblie(String str) {
		if (StringUtil.isEmpty(str)) {
			return false;
		}

		str = replaceMoblie(str);

		if (str.length() != 11) {
			return false;
		}

		if (!StringUtil.isNumber(str)) {
			return false;
		}
		return matchingText(str, RegExp.MOBILE);
	}

	/**
	 * 验证手机号或电话
	 */
	public static boolean validatePhone(String str) {
		if (StringUtil.isEmpty(str)) {
			return false;
		}

		if (!StringUtil.isNumber(StringUtil.remove(str, "-"))) {
			return false;
		}

		if (matchingText(str, RegExp.PHONE)) {
			return true;
		} else {
			return validateMoblie(str);
		}
	}

	/**
	 * 验证手机号或邮箱
	 */
	public static boolean validateMoblieOrEmail(String str) {
		if (StringUtil.isNull(str)) {
			return false;
		}
		return matchingText(str, RegExp.MOBILE, RegExp.EMAIL);
	}

	/**
	 * 验证表达式 多个表达式
	 */
	public static boolean matchingText(String text, String... expressions) {
		for (String expression : expressions) {
			boolean b = matchingText(text, expression);
			if (b) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取缓存表达式
	 * @param exr
	 * @return
	 */
	public static Pattern getPattern(String exr) {
		if (patterns.containsKey(exr)) {
			return patterns.get(exr);
		} else {
			Pattern p = Pattern.compile(exr);
			patterns.put(exr, p);
			return p;
		}
	}

	/**
	 * 验证表达式
	 * @param expression 表达式
	 * @param text 文本
	 * @return
	 */
	public static boolean matchingText(String text, String expression) {
		Pattern p = getPattern(expression); // 正则表达式
		Matcher m = p.matcher(text); // 操作的字符串
		return m.matches();
	}
	
	/**
	 * 正则表达式查找
	 * @param text 正文
	 * @param expression 表达式
	 * @param searchIndex 查找索引 如：group(0),group(1)
	 * @return
	 */
	public static List<String> find(String text, String expression) {
		return find(text, expression, 0);
	}

	/**
	 * 正则表达式查找
	 * @param text 正文
	 * @param expression 表达式
	 * @param searchIndex 查找索引 如：group(0),group(1)
	 * @return
	 */
	public static List<String> find(String text, String expression, int searchIndex) {
		List<String> list = new ArrayList<String>();
		if (StringUtil.isEmpty(text) || StringUtil.isEmpty(expression)) {
			return list;
		}
		if (searchIndex < 0) {
			return list;
		}
		Pattern p = getPattern(expression);
		Matcher m = p.matcher(text);
		while (m.find()) {
			String value = m.group(searchIndex);
			if (StringUtil.isNotEmpty(value)) {
				list.add(value);
			}
		}
		return list;
	}

	/**
	 * 使用正则表达式替换数据
	 * @param text 正文
	 * @param expression 表达式
	 * @param newsub 替换内容
	 * @return
	 */
	public static String replace(String text, String exr, String newsub) {
		Pattern p = getPattern(exr);
		Matcher m = p.matcher(text);
		return m.replaceAll(newsub);
	}
	
	/**
	 * 使用正则表达式分割
	 * @param text 正文
	 * @param expression 表达式
	 * @return
	 */
	public static String[] split(String text, String exr) {
		Pattern p = getPattern(exr);
		return p.split(text, 0);
	}
}