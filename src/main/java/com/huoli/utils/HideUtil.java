package com.huoli.utils;

import com.huoli.utils.StringUtil;

/**
 * 描述：隐藏信息帮助<br>
 * 版权：Copyright (c) 2011-2015<br>
 * 公司：北京活力天汇<br>
 * 作者：罗良杰<br>
 * 版本：1.0<br>
 * 创建日期：2015年1月26日<br>
 */
public class HideUtil {
	/**
	 * 隐藏信息
	 * @param value 待隐藏字符
	 * @param start 开始
	 * @param end 结束
	 * @return
	 */
	public static String hideValue(String value, int start, int end) {
		if (StringUtil.isEmpty(value)) {
			return value;
		}

		if (value.length() < end || end <= 0 || start > end) {
			end = value.length();
		}
		StringBuilder str = new StringBuilder(value);
		for (int i = start; i < value.length(); i++) {
			if (i < end) {
				str.setCharAt(i, '*');
			}
		}
		return str.toString();
	}

	/**
	 * 隐藏信息
	 * @param value 待隐藏字符
	 * @param start 后几位
	 * @return
	 */
	public static String hideValue(String value, int start) {
		return hideValue(value, start, -1);
	}

	/**
	 * 隐藏姓名 只显示最后一位
	 * @param value 姓名
	 * @return
	 */
	public static String hideName(String value) {
		if (StringUtil.isEmpty(value)) {
			return value;
		}
		return hideValue(value, 0, value.length() - 1);
	}

	/**
	 * 隐藏姓名 只显示第一位
	 * @param value
	 * @return
	 */
	public static String hideEndName(String value) {
		return hideValue(value, 1);
	}

	/**
	 * 隐藏手机号
	 * @param value 手机号
	 * @return
	 */
	public static String hideMobile(String value) {
		return hideValue(value, 4, 8);
	}

	/**
	 * 隐藏邮箱
	 * @param value
	 * @return
	 */
	public static String hideMail(String value) {
		if (StringUtil.isEmpty(value)) {
			return value;
		}

		StringBuilder str = new StringBuilder();

		int split = value.indexOf("@");

		if (split > 0) {
			String userName = value.substring(0, split);
			str.append(hideValue(userName, 1, userName.length() - 2));
			str.append(value.substring(split));
		} else {
			str.append(hideValue(value, 1, value.length() - 2));
		}
		return str.toString();
	}

	/**
	 * 隐藏证件号
	 * @param value
	 * @return
	 */
	public static String hideCard(String value) {
		if (StringUtil.isEmpty(value)) {
			return value;
		}
		return hideValue(value, 6, value.length() - 4);
	}
}
