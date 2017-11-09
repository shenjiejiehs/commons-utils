package com.huoli.utils;

import java.util.List;

/**
 * 
 * 描述：字符串分割查找工具类<br>
 * 版权：Copyright (c) 2011<br>
 * 公司：北京活力天汇<br>
 * 作者：童凡<br>
 * 版本：1.0<br>
 * 创建日期：2012-6-18<br>
 */
public class StringSearchUtil {
	public static final int POS_LAST = -10;
	public static final int POS_NEXT = 1;
	public static final int POS_PREVIOUS = -1;

	/**
	 * 根据绝对位置去查找字符串
	 * 
	 * @param source 源字符串
	 * @param delimiter 分隔符
	 * @param absulutePosition 绝对位置，可以为POS_LAST，最后一个
	 * @return 找到的字符串
	 */
	public static String search(String source, String delimiter, int absulutePosition) {
		if (source == null) {
			return null;
		}
		if ("".equals(source)) {
			return "";
		}
		String[] strings = StringUtil.split(source, delimiter);
		if (absulutePosition == POS_LAST) {
			return strings[strings.length - 1].trim();
		} else if (absulutePosition >= strings.length) {
			return null;
		}
		return strings[absulutePosition].trim();
	}

	/**
	 * 
	 * @param source 来源
	 * @param delimiter 分隔符
	 * @param referString 参考的字符串
	 * @param pos 位置
	 * @return
	 */
	public static String search(String source, String delimiter, String referString, int pos) {
		if (source == null) {
			return null;
		}
		if ("".equals(source)) {
			return "";
		}
		String[] strings = StringUtil.split(source, delimiter);
		int j = -1;
		for (int i = 0; i < strings.length; i++) {
			String string = strings[i].trim();
			if (referString.equals(string)) {
				j = i;
				break;
			}
		}
		if (j != -1) {
			if (j + pos < 0 || j + pos >= strings.length) {
				return null;
			}
			String targetString = strings[j + pos];
			return targetString;
		}
		return null;
	}

	/**
	 * 正则断言查询匹配内容
	 * 
	 * @param str 来源字符串
	 * @param exp 正则断言
	 * @return
	 */
	public static String findMatch(String str, String exp) {
		List<String> list = VerifyUtil.find(str, exp);
		if (list != null && list.size() > 0) {
			return list.get(0);
		}
		return "";
	}

	/**
	 * 正则断言查询多个匹配内容
	 * 
	 * @param str 来源字符串
	 * @param exp 正则断言
	 * @return
	 */
	public static List<String> findMatches(String str, String exp) {
		return VerifyUtil.find(str, exp);
	}
}