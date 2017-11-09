package com.huoli.utils;

/**
 * 版本工具类 <br>
 * 版权：Copyright (c) 2011-2013<br>
 * 公司：北京活力天汇<br>
 * 作者：罗良杰<br>
 * 版本：1.0<br>
 * 创建日期：2014-9-30<br>
 */
public class VersionUtil {
	/**
	 * 比较两个版本大小
	 * @param v1 版本1
	 * @param v2 版本2
	 * @return 1，大于；0，等于；-1，小于
	 */
	public static int compareVersion(String v1, String v2) {
		if (isVersion(v1) == false || isVersion(v2) == false) {
			return -1;
		}
		
		// 多增加几个0，以解决长度不一致问题
		v1 = v1 + ".0.0.0.0";
		v2 = v2 + ".0.0.0.0";
		String[] v1s = StringUtil.split(v1, ".");
		String[] v2s = StringUtil.split(v2, ".");
		if (Integer.valueOf(v1s[0]).compareTo(Integer.valueOf(v2s[0])) != 0) {
			return Integer.valueOf(v1s[0]).compareTo(Integer.valueOf(v2s[0]));
		} else if (v1s.length > 1
				&& v2s.length > 1
				&& Integer.valueOf(v1s[1]).compareTo(Integer.valueOf(v2s[1])) != 0) {
			return Integer.valueOf(v1s[1]).compareTo(Integer.valueOf(v2s[1]));
		} else if (v1s.length > 2
				&& v2s.length > 2
				&& Integer.valueOf(v1s[2]).compareTo(Integer.valueOf(v2s[2])) != 0) {
			return Integer.valueOf(v1s[2]).compareTo(Integer.valueOf(v2s[2]));
		} else if (v1s.length > 3
				&& v2s.length > 3
				&& Integer.valueOf(v1s[3]).compareTo(Integer.valueOf(v2s[3])) != 0) {
			return Integer.valueOf(v1s[3]).compareTo(Integer.valueOf(v2s[3]));
		} else {
			return 0;
		}
	}
	
	/**
	 * 比较两个版本大小
	 * @param v1 版本1
	 * @param v2 版本2
	 * @param equals 是否可以等于
	 * @return true : false
	 */
	public static boolean compareVersion(String s1, String s2, boolean equals) {
		int i = compareVersion(s1, s2);
		if (i > 0) {
			return true;
		}
		if (equals && i == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 转换版本的格式，如2.1.1转成002001001，便于在数据库中比较
	 * @param v 原始版本
	 * @return 转换后的版本格式
	 */
	public static String versionStr(String v) {
		String[] vs = StringUtil.split(v, ".");
		StringBuilder sb = new StringBuilder();
		for (String s : vs) {
			sb.append("000".substring(0, 3 - s.length()));
			sb.append(s);
		}
		return sb.toString();
	}

	/**
	 * 判断字符串是否是版本
	 */
	public static boolean isVersion(String v) {
		if (StringUtil.isEmpty(v)) {
			return false;
		}
		
		v = StringUtil.remove(v, ".");
		try {
			Integer.parseInt(v);
			return true;
		} catch (Exception e) {
			return false;
		}
	}
}
