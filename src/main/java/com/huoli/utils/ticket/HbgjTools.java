package com.huoli.utils.ticket;

import java.net.InetAddress;
import java.net.NetworkInterface;
import java.util.ArrayList;
import java.util.Enumeration;
import java.util.HashMap;
import java.util.HashSet;
import java.util.List;
import java.util.ResourceBundle;

import org.apache.commons.codec.digest.DigestUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huoli.utils.DateTimeUtil;
import com.huoli.utils.ResourcesUtil;
import com.huoli.utils.StringSearchUtil;
import com.huoli.utils.StringUtil;
import com.huoli.utils.VersionUtil;


/**
 * 工具类 <br>
 * 版权：Copyright (c) 2011-2014<br>
 * 公司：北京活力天汇<br>
 * 作者：罗良杰<br>
 * 版本：1.0<br>
 * 创建日期：2014年9月17日<br>
 */
public class HbgjTools {
	/**
	 * 港澳台机场三字码
	 */
	public static final String[] HMTCODES = { "HKG", "MFM", "TPE", "KHH", "TSA", "TTT", "HUN", "MZG", "RMQ" };
	
	/**
	 * 默认客户端类型
	 */
	public static String DEFAULT_CLIENT_TYPE = "iphone";
	
	private static Logger logger = LoggerFactory.getLogger(HbgjTools.class);
	
	static ResourceBundle wapResources;
	
	static {
		try {
			wapResources = ResourcesUtil.load("wap");
		} catch (Exception e) {
			logger.error("load wap file fail!", e);
		}
	}
	
	/**
	 * 获取资源对应名称的String属性
	 * @param name 对应名称
	 * @return
	 */
	public static String getStringResources(String name) {
		return ResourcesUtil.getString(wapResources, name);
	}
	
	/**
	 * 获取资源对应名称的String属性
	 * @param name 对应名称
	 * @return
	 */
	public static int getIntegerResources(String name) {
		try {
			return ResourcesUtil.getInt(wapResources, name);
		} catch (Exception e) {
			logger.error("load wap file int property " + name + " fail!", e);
			return 0;
		}
	}
	
	/**
	 * 获取资源对应名称的String属性
	 * @param name 对应名称
	 * @return
	 */
	public static Boolean getBooleanResources(String name) {
		try {
			return ResourcesUtil.getBoolean(wapResources, name);
		} catch (Exception e) {
			logger.error("load wap file boolean property " + name + " fail!", e);
			return false;
		}
	}
	
	/**
	 * 判断是否为港澳台机场
	 * @param code 机场三字码
	 * @return 
	 */
	public static boolean isHMT(String code) {
		return StringUtil.equals(code, HMTCODES);
	}
	
	/**
	 * 返回错误hd的xml信息
	 * @param msg 消息内容
	 * @param pid 对应协议号
	 */
	public static String getHdXmlStr(String msg, int pid) {
		return getHdXmlStr(msg, pid, 0);
	}

	/**
	 * 返回hd的xml信息
	 * @param msg 消息内容
	 * @param pid 对应协议号
	 * @param code 是否成功 0：否 1：是
	 * @return
	 */
	public static String getHdXmlStr(String msg, int pid, int code) {
		return getHdXmlStr(msg, pid, code, 0, null);
	}

	/**
	 * 返回hd的xml信息
	 * @param msg 消息内容
	 * @param pid 对应协议号
	 * @param code 是否成功 0：否 1：是
	 * @param bd 附加bd信息，不加<bd>
	 * @return
	 */
	public static String getHdXmlStr(String msg, int pid, int code, String bd) {
		return getHdXmlStr(msg, pid, code, 0, bd);
	}

	/**
	 * 返回hd的xml信息
	 * @param msg 消息内容
	 * @param pid 对应协议号
	 * @param code 是否成功 0：否 1：是
	 * @param errortime 错误时间
	 * @param bd 附加bd信息，不加<bd>
	 * @return
	 */
	public static String getHdXmlStr(String msg, int pid, int code, int errortime, String bd) {
		StringBuilder str = new StringBuilder();
		str.append("<hd>");
		str.append("<code>").append(code).append("</code>");
		str.append("<desc>").append(msg).append("</desc>");
		if (pid > 0) {
			str.append("<pid>").append(pid).append("</pid>");
		}
		if (errortime > 0) {
			str.append("<errortime>").append(errortime).append("</errortime>");
		}
		if (!StringUtil.isEmpty(bd)) {
			str.append("<bd>").append(bd).append("</bd>");
		}
		str.append("</hd>");
		return str.toString();
	}
	
	/**
	 * 比较客户端版本是否大于某版本
	 * @param s1 需要对比的字符串
	 * @param s2 需要对比的字符串
	 * @param equals 是否相等
	 * @return
	 */
	public static boolean isVersion(String cver, String dver, boolean equals) {
		return VersionUtil.compareVersion(cver, dver, equals);
	}
	
	/**
	 * 比较客户端版本是否小于于某版本
	 * @param s1 需要对比的字符串
	 * @param s2 需要对比的字符串
	 * @param equals 是否相等
	 * @return
	 */
	public static boolean isLtVersion(String cver, String dver, boolean equals) {
		return VersionUtil.compareVersion(dver, cver, equals);
	}
	
	/**
	 * 获取客户端类型
	 * @param 客户端上传p参数
	 */
	public static String getClientType(String p) {
		String clientType = DEFAULT_CLIENT_TYPE;
		if (StringUtil.isEmpty(p)) {
			return clientType;
		}
		p = p.toLowerCase();
		if (StringUtil.contains(p, "hbgjpro")) {
			clientType = "iphonepro";
		} else if (StringUtil.contains(p, "android")) {
			clientType = "android";
		} else if (StringUtil.contains(p, "iphone") || StringUtil.contains(p, "ios")) {
			clientType = "iphone";
		} else if (StringUtil.contains(p, ",wp,")) {
			clientType = "wp";
		}
		return clientType;
	}
	
	/**
	 * 判断是否为航班号 模糊搜索使用
	 * @param no 航班号
	 * @return 是否合法
	 */
	public static boolean isLikeFlyNo(String no) {
		if (StringUtil.isEmpty(no)) {
			return false;
		}
		//航班号必须大于4位并小于7位
		if (no.length() < 4 || no.length() > 7) {
			return false;
		}
		
		String exp = "^\\w{2}\\d{2,4}[a-zA-Z]{0,1}$";
		String find =  StringSearchUtil.findMatch(no, exp);
		return !StringUtil.isEmpty(find);
	}
	
	/**
	 * 判断是否为航空公司
	 * 请参照StringUtil.isLetterAndNumber
	 * @param com 航司
	 * @return 是否合法
	 */
	public static boolean isLikeCom(String com) {
		if (StringUtil.isEmpty(com)) {
			return false;
		}
		if (com.length() != 2) {
			return false;
		}
		return StringUtil.isLetterAndNumber(com);
	}
	
	/**
	 * 替换航班号中多余0，例如HU007  替换为HU7 但是不替换 HU1007 
	 * @param 航班号
	 */
	public static String replaceFlyNoZero(String no) {
		if (StringUtil.isEmpty(no)) {
			return no;
		}
		if (!isLikeFlyNo(no)) {
			return no;
		}
		if (no.length() >= 5) {
			//干掉前2位航司代码
			String nos = no.substring(2);
			if (StringUtil.isNumber(nos)) {
				return no.substring(0, 2) + Integer.parseInt(nos);
			}
		} 
		return no;
	}
	
	/**
	 * 将字符串中的字符转换成html中的转义字符
	 * @param original 待处理的字符串
	 * @return 处理后的字符串
	 */
	public static String convertToHtml(String original) {
		if (original == null) {
			original = "";
		}
		if (original.trim().equals("")) {
			return original;
		}
		String[][] character = { { "&", "&amp;" }, { "'", "&#39;" },
				{ ">", "&gt;" }, { "<", "&lt;" }, { "\"", "&quot;" } };
		return StringUtil.replace(original, character);
	}

	/**
	 * 采编系统 批量采编时处理约定的标志位 将字符串中的标志位转换成html中的转义字符
	 * @param original待处理的字符串
	 * @return 处理后的字符串
	 */
	public static String convertToHtmlX(String original) {
		if (original == null) {
			original = "";
		}
		if (original.trim().equals("")) {
			return original;
		}
		String[][] character = { { "\\6", "\r" } };
		return StringUtil.replace(original, character);
	}

	/**
	 * 将字符串转换为数组
	 * @param str String
	 * @return int[]
	 */
	public static int[] stringToArray(String str) {
		java.util.StringTokenizer st = new java.util.StringTokenizer(str, ",");
		int[] ret = new int[st.countTokens()];
		int i = 0;
		while (st.hasMoreTokens()) {
			ret[i] = Integer.parseInt((String) st.nextElement());
			i++;
		}
		return ret;
	}
	
	/**
	 * 清理特殊字符
	 * @param text 需要清理的字符
	 * @param special5 是否调用特殊5的方法
	 * @return
	 */
	public static String cleanText(String text) {
		return StringUtil.cleanText(text, false);
	}
	
	/**
	 * 获得本机ip
	 */
	public static List<String> getLocalIPAddress() {
		List<String> ips = new ArrayList<String>();
		NetworkInterface nif;
		try {
			Enumeration<NetworkInterface> em = NetworkInterface.getNetworkInterfaces();
			while (em.hasMoreElements()) {
				nif = em.nextElement();
				String res = nif.getDisplayName();
				Enumeration<InetAddress> me = nif.getInetAddresses();
				while (me.hasMoreElements()) {
					InetAddress addr = me.nextElement();
					ips.add(res + ":" + addr);
				}
			}
		} catch (Exception e) {
		}
		return ips;
	}
	
	/**
	 * 加密URL
	 * 需要获取wap配置文件中Base64_key字段
	 */
	public static String encrypt(String str) {
		if (StringUtil.isEmpty(str) || str.length() == 0) {
			return str;
		}
		str = StringUtil.replace(str, "&amp;", "&");
		str = StringUtil.replace(str, "&", "&amp;");
		str = SecurityTools.encrypt(getStringResources("Base64_key"), str);
		str = StringUtil.replace(str, "+", "!");
		str = StringUtil.replace(str, "/", "@");
		return str;
	}
	
	/**
	 * 解密URL
	 * 需要获取wap配置文件中Base64_key字段
	 */
	public static String decrypt(String str) {
		if (StringUtil.isEmpty(str) || str.length() == 0) {
			return str;
		}
		str = StringUtil.replace(str, "!", "+");
		str = StringUtil.replace(str, "@", "/");
		try {
			str = SecurityTools.decrypt(getStringResources("Base64_key"), str);
		} catch (Exception ex) {
			str = "";
		}
		str = StringUtil.replace(str, "&amp;", "&");
		return str;
	}
	
	/**
	 * 加密校验码
	 */
	public static String softDog(String str) {
		String key = "&*^%@%$^#&";
		String md5 = DigestUtils.md5Hex(str + key).toUpperCase();
		// 5,2,17,10,20,31,29,23 取值位数
		return md5.charAt(4) + "" + md5.charAt(1) + "" + md5.charAt(16) + ""
				+ md5.charAt(9) + "" + md5.charAt(19) + "" + md5.charAt(30)
				+ "" + md5.charAt(28) + "" + md5.charAt(22);
	}
	
	/**
	 * 新版加密校验码
	 */
	public static String softDogNew(String str) {
		String key = "&*^%@#$^&$";
		String md5 = DigestUtils.md5Hex(str + key).toUpperCase();
		// 5,2,13,10,19,30,29,23 取值位数
		return md5.charAt(4) + "" + md5.charAt(1) + "" + md5.charAt(12) + ""
				+ md5.charAt(9) + "" + md5.charAt(18) + "" + md5.charAt(29)
				+ "" + md5.charAt(28) + "" + md5.charAt(22);
	}
	
	/**
	 * 获取日期中的时:分
	 */
	public static String getHMTime(String date) {
		String HHmm = "";
		if (date == null || date.length() < 8) {
			return date;
		}
		/** 获取日期中的时:分 **/
		HHmm = date.substring(date.length() - 8, date.length() - 3);
		if (DateTimeUtil.checkDate(HHmm, "HH:ss")) {
			if (HHmm.equals("00:00"))
				return "false";
			return HHmm;
		} else {
			return date;
		}
	}
	
	/**
	 * ISO-8859-1转换UTF-8
	 * @param s
	 * @return
	 */
	public static String getUtf8(String s) {
		return StringUtil.transcodeStr(s, "ISO-8859-1", "UTF-8");
	}

	/**
	 * 获取utf8编码
	 */
	public static String getUtf8(String s, String method) {
		if (method.equalsIgnoreCase("POST")) {
			return s;
		} else {
			return getUtf8(s);
		}
	}
	
	/**
	 * 
	 * @param code
	 * @return
	 */
	public static String getCityCode(String code) {
		HashMap<String, String> specCode = new HashMap<String, String>();
		specCode.put("BJS_C", "PEK"); // PEK+NAY
		specCode.put("CQS_C", "CKG"); // CKG+JIQ
		specCode.put("SHS_C", "SHA"); // SHA+PVG

		if (specCode.containsKey(code)) {
			return specCode.get(code);
		}
		return code;
	}

	/**
	 * 通过北京、上海、重庆这3个城市的机场三字码(PEK NAY CKG JIQ SHA PVG)获取城市三字码(BJS_C CQS_C SHS_C)
	 * 
	 * @param code
	 * @return
	 */
	public static String getSpecialCityCode(String code) {
		HashMap<String, String> specCode = new HashMap<String, String>();
		specCode.put("PEK", "BJS_C"); // PEK+NAY
		specCode.put("NAY", "BJS_C"); // PEK+NAY
		specCode.put("JIQ", "CQS_C"); // CKG+JIQ
		specCode.put("CKG", "CQS_C"); // CKG+JIQ
		specCode.put("SHA", "SHS_C"); // SHA+PVG
		specCode.put("PVG", "SHS_C"); // SHA+PVG

		if (specCode.containsKey(code)) {
			return specCode.get(code);
		}
		return code;
	}
	
	/**
	 * 通过城市三字码(BJS_C CQS_C SHS_C)获取北京、上海、重庆这3个城市的机场三字码(PEK NAY CKG JIQ SHA PVG)
	 * 
	 * @param code
	 * @return
	 */
	public static String getSpecialCode(String code) {
		HashMap<String, String> specCode = new HashMap<String, String>();
		specCode.put("BJS_C", "PEK,NAY"); // PEK+NAY
		specCode.put("CQS_C", "CKG,JIQ"); // PEK+NAY
		specCode.put("SHS_C", "SHA,PVG"); // CKG+JIQ

		if (specCode.containsKey(code)) {
			return specCode.get(code);
		}
		return code;
	}

	/**
	 * 判断是否是北京、上海、重庆这3个城市
	 */
	public static boolean isSpecialCityCode(String code) {
		HashSet<String> specCode = new HashSet<String>();
		specCode.add("BJS_C"); // PEK+NAY
		specCode.add("CQS_C"); // CKG+JIQ
		specCode.add("SHS_C"); // SHA+PVG

		if (specCode.contains(code)) {
			return true;
		}
		return false;
	}

	public static String getSpecCityName(String code) {
		HashMap<String, String> specnames = new HashMap<String, String>();
		specnames.put("PEK", "北京");
		specnames.put("NAY", "北京");
		specnames.put("BJS_C", "北京");

		specnames.put("SHA", "上海");
		specnames.put("PVG", "上海");
		specnames.put("SHS_C", "上海");

		specnames.put("CKG", "重庆");
		specnames.put("JIQ", "重庆");
		specnames.put("CQS_C", "重庆");

		if (specnames.containsKey(code)) {
			return specnames.get(code);
		}

		return "";
	}

	public static String getSpecAirportName(String code) {
		HashMap<String, String> specnames = new HashMap<String, String>();
		specnames.put("PEK", "首都");
		specnames.put("NAY", "南苑");

		specnames.put("SHA", "虹桥");
		specnames.put("PVG", "浦东");

		specnames.put("CKG", "江北");
		specnames.put("JIQ", "黔江");

		if (specnames.containsKey(code)) {
			return specnames.get(code);
		}
		return "";
	}
}
