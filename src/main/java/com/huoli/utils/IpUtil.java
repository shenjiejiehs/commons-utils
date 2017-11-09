package com.huoli.utils;

/**
 * 描述：ip帮助对象<br>
 * 版权：Copyright (c) 2011-2015<br>
 * 公司：北京活力天汇<br>
 * 作者：罗良杰<br>
 * 版本：1.0<br>
 * 创建日期：2016年5月11日<br>
 */
public class IpUtil {
	/**
	 * ip换数字
	 * @return
	 */
	public static long ipToLong(String strIp) {
		if (StringUtil.isEmpty(strIp)) {
			return -1;
		}

		if (StringUtil.split(strIp, ".").length != 4) {
			return -1;
		}

		try {
			long[] ip = new long[4];
			// 先找到IP地址字符串中.的位置
			int position1 = strIp.indexOf(".");
			int position2 = strIp.indexOf(".", position1 + 1);
			int position3 = strIp.indexOf(".", position2 + 1);
			// 将每个.之间的字符串转换成整型
			ip[0] = Long.parseLong(strIp.substring(0, position1));
			ip[1] = Long.parseLong(strIp.substring(position1 + 1, position2));
			ip[2] = Long.parseLong(strIp.substring(position2 + 1, position3));
			ip[3] = Long.parseLong(strIp.substring(position3 + 1));
			return (ip[0] << 24) + (ip[1] << 16) + (ip[2] << 8) + ip[3];
		} catch (Exception e) {
			return -1;
		}
	}
}
