package com.huoli.utils;

/**
 * 描述：数字转换hex代码<br>
 * 版权：Copyright (c) 2011-2015<br>
 * 公司：北京活力天汇<br>
 * 作者：罗良杰<br>
 * 版本：1.0<br>
 * 创建日期：2016年5月9日<br>
 */
public class HexUtil {
	static char[] digits = new char[] { '0', '1', '2', '3', '4', '5', '6', '7', '8', '9', 'A', 'B', 'C', 'D', 'E', 'F',
			'G', 'H', 'J', 'K', 'L', 'M', 'N', 'P', 'Q', 'R', 'S', 'T', 'U', 'V', 'W', 'X', 'Y', 'Z' };

	/**
	 * 根据数值转换为35进制的指定长度的进制码 不足补
	 * @param sum 转换数值
	 * @param o 长度
	 * @return
	 */
	public static String intToHex(int sum, int o) {
		int mod = digits.length;
		StringBuilder line = new StringBuilder();
		for (int i = 0; i < o; i++) {
			line.insert(i, digits[sum % mod]);
			sum /= mod;
		}
		return line.toString();
	}

	/**
	 * 根据数值转换为35进制的指定长度的进制码
	 * @param sum 转换数值
	 * @return
	 */
	public static String numberToHex(long sum) {
		long mod = digits.length;
		StringBuilder line = new StringBuilder();
		if (sum < 0) {
			line.append("-");
			sum = -sum;
		}
		for (int i = 0; sum > 0; i++) {
			line.insert(i, digits[(int) (sum % mod)]);
			sum /= mod;
		}

		return line.toString();
	}

	/**
	 * 根据35进制的进制码转换为数值
	 * @param sum 转换数值
	 * @param o 长度
	 * @return
	 */
	public static long hexToNumber(String str) {
		int mod = digits.length;
		long sum = 0;
		boolean negative = false;
		if (str.endsWith("-")) {
			negative = true;
			str = str.substring(0, str.length() - 1);
		}
		for (int i = (str.length() - 1); i >= 0; i--) {
			sum *= mod;
			for (int it = 0; it < mod; it++) {
				if (digits[it] == str.charAt(i)) {
					sum += it;
				}
			}
		}
		if (negative) {
			sum = -sum;
		}
		return sum;
	}

	/**
	 * 根据35进制的进制码转换为数值
	 * @param sum 转换数值
	 * @param o 长度
	 * @return
	 */
	public static int hexToInt(String str, int o) {
		int mod = digits.length;
		int sum = 0;
		for (int i = (o - 1); i >= 0; i--) {
			sum *= mod;
			for (int it = 0; it < mod; it++) {
				if (digits[it] == str.charAt(i)) {
					sum += it;
				}
			}
		}
		return sum;
	}
}
