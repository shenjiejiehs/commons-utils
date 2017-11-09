package com.huoli.utils;

import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.HashMap;
import java.util.Map;

import org.apache.commons.lang3.time.DateUtils;

import com.huoli.utils.DateTimeUtil;
import com.huoli.utils.StringUtil;

/**
 * 证件号码帮助<br>
 * 身份证验证的工具（支持5位或18位省份证） 身份证号码结构：<br>
 * 
 * 17位数字和1位校验码：6位地址码数字，8位生日数字，3位出生时间顺序号，1位校验码。<br>
 * 地址码（前6位）：表示对象常住户口所在县（市、镇、区）的行政区划代码，按GB/T2260的规定执行。 <br>
 * 出生日期码，（第七位至十四位）：表示编码对象出生年、月、日，按GB按GB/T7408的规定执行，年、月、日代码之间不用分隔符。<br>
 * 顺序码（第十五位至十七位）：表示在同一地址码所标示的区域范围内，对同年、同月、同日出生的人编订的顺序号， 顺序码的奇数分配给男性，偶数分配给女性。<br>
 * 
 * 校验码（第十八位数）：<br>
 * 
 * 十七位数字本体码加权求和公式 s = sum(Ai*Wi), i = 0,,16，先对前17位数字的权求和；<br>
 * Ai:表示第i位置上的身份证号码数字值.Wi:表示第i位置上的加权因.Wi: 7 9 10 5 8 4 2 1 6 3 7 9 10 5 8 4 2；<br>
 * 计算模 Y = mod(S, 11) 通过模得到对应的校验码 Y: 0 1 2 3 4 5 6 7 8 9 10 校验码: 1 0 X 9 8 7 6 5
 * 4 3 2<br>
 * <br>
 * 版权：Copyright (c) 2011-2014<br>
 * 公司：北京活力天汇<br>
 * 作者：罗良杰<br>
 * 版本：1.0<br>
 * 创建日期：2014年12月8日<br>
 */
public class IdcardUtil {
	final static Map<Integer, String> zoneNum = new HashMap<Integer, String>();

	static {
		zoneNum.put(11, "北京");
		zoneNum.put(12, "天津");
		zoneNum.put(13, "河北");
		zoneNum.put(14, "山西");
		zoneNum.put(15, "内蒙古");
		zoneNum.put(21, "辽宁");
		zoneNum.put(22, "吉林");
		zoneNum.put(23, "黑龙江");
		zoneNum.put(31, "上海");
		zoneNum.put(32, "江苏");
		zoneNum.put(33, "浙江");
		zoneNum.put(34, "安徽");
		zoneNum.put(35, "福建");
		zoneNum.put(36, "江西");
		zoneNum.put(37, "山东");
		zoneNum.put(41, "河南");
		zoneNum.put(42, "湖北");
		zoneNum.put(43, "湖南");
		zoneNum.put(44, "广东");
		zoneNum.put(45, "广西");
		zoneNum.put(46, "海南");
		zoneNum.put(50, "重庆");
		zoneNum.put(51, "四川");
		zoneNum.put(52, "贵州");
		zoneNum.put(53, "云南");
		zoneNum.put(54, "西藏");
		zoneNum.put(61, "陕西");
		zoneNum.put(62, "甘肃");
		zoneNum.put(63, "青海");
		zoneNum.put(64, "宁夏");
		zoneNum.put(65, "新疆");
		zoneNum.put(71, "台湾");
		zoneNum.put(81, "香港");
		zoneNum.put(82, "澳门");
		zoneNum.put(91, "外国");
	}

	final static int[] PARITYBIT = { '1', '0', 'X', '9', '8', '7', '6', '5', '4', '3', '2' };
	final static int[] POWER_LIST = { 7, 9, 10, 5, 8, 4, 2, 1, 6, 3, 7, 9, 10, 5, 8, 4, 2 };

	/**
	 * 身份证验证
	 * @param s 号码内容
	 * @return 是否有效 null和"" 都是false
	 */
	public static boolean isIdcard(String s) {
		if (s == null || (s.length() != 15 && s.length() != 18))
			return false;
		final char[] cs = s.toUpperCase().toCharArray();
		// 校验位数
		int power = 0;
		for (int i = 0; i < cs.length; i++) {
			if (i == cs.length - 1 && cs[i] == 'X')
				break;// 最后一位可以 是X或x
			if (cs[i] < '0' || cs[i] > '9')
				return false;
			if (i < cs.length - 1) {
				power += (cs[i] - '0') * POWER_LIST[i];
			}
		}

		// 校验区位码
		if (!zoneNum.containsKey(Integer.valueOf(s.substring(0, 2)))) {
			return false;
		}

		// 验证生日
		String birthday = getBirthdayByCard(s);
		if (StringUtil.isEmpty(birthday)) {
			return false;
		}

		// 校验"校验码"
		if (s.length() == 15) {
			return true;
		}
		return cs[cs.length - 1] == PARITYBIT[power % 11];
	}

	/**
	 * 根据证件号获取生日
	 * @param card
	 * @return
	 */
	public static String getBirthdayByCard(String card) {
		if (card == null || (card.length() != 15 && card.length() != 18))
			return null;
		// 校验年份
		String year = card.length() == 15 ? getIdcardCalendar() + card.substring(6, 8) : card.substring(6, 10);
		
		if (StringUtil.isNumeric(year) == false) {
			return null;
		}
		final int iyear = Integer.parseInt(year);
		if (iyear < 1900 || iyear > Calendar.getInstance().get(Calendar.YEAR)) {
			return null;// 1900年的PASS，超过今年的PASS
		}

		// 校验月份
		String month = card.length() == 15 ? card.substring(8, 10) : card.substring(10, 12);
		
		if (StringUtil.isNumeric(month) == false) {
			return null;
		}
		final int imonth = Integer.parseInt(month);
		if (imonth < 1 || imonth > 12) {
			return null;
		}

		// 校验天数
		String day = card.length() == 15 ? card.substring(10, 12) : card.substring(12, 14);
		if (StringUtil.isNumeric(day) == false) {
			return null;
		}
		final int iday = Integer.parseInt(day);
		if (iday < 1 || iday > 31) {
			return null;
		}

		return year + "-" + month + "-" + day;
	}

	/**
	 * 根据身份证件号和航班日期 获取当前年龄
	 * @param card
	 * @param flyDate
	 * @return -1或年龄
	 */
	public static long getDateDiffYearByCard(String card, String flyDate) {
		String birthday = getBirthdayByCard(card);
		if (StringUtil.isNotEmpty(birthday)) {
			return getDateDiffYear(birthday, flyDate);
		}
		return 0;
	}

	/**
	 * 根据生日和航班日期，根据年月日获取年龄
	 * @param birthday 生日
	 * @param flyDate 航班日期
	 * @return
	 */
	public static long getDateDiffYear(String birthday, String flyDate) {
		try {
			Date date = DateTimeUtil.parseDate(birthday);
			Calendar birthdayTime = DateUtils.toCalendar(date);

			Date flyday = DateTimeUtil.parseDate(flyDate);
			Calendar flydayTime = DateUtils.toCalendar(flyday);

			// 2个时间年份减去获取年龄
			long age = flydayTime.get(Calendar.YEAR) - birthdayTime.get(Calendar.YEAR);

			// 2个时间的月份差
			int monthDiff = birthdayTime.get(Calendar.MONTH) - flydayTime.get(Calendar.MONTH);
			if (monthDiff > 0) {
				age--;
			} else if (monthDiff == 0) {
				int dayDiff = birthdayTime.get(Calendar.DAY_OF_MONTH) - flydayTime.get(Calendar.DAY_OF_MONTH);
				if (dayDiff > 0) {
					age--;
				}
			}

			return age;
		} catch (Exception e) {
			return 0;
		}
	}

	/**
	 * 获取年份后面几位
	 * @return
	 */
	private static int getIdcardCalendar() {
		GregorianCalendar curDay = new GregorianCalendar();
		int curYear = curDay.get(Calendar.YEAR);
		int year2bit = Integer.parseInt(String.valueOf(curYear).substring(2));
		return year2bit;
	}

	/**
	 * 根据身份证号获取性别 1-男|2-女
	 * @param cardNum 身份证号
	 * @return
	 * @throws Exception
	 */
	public static int getSexByIdcard(String cardNum) throws Exception {
		if (StringUtil.isEmpty(cardNum)) {
			throw new Exception("未能判断男女。");
		}
		int sexNum = -1;
		if (cardNum.length() == 15) {
			String temp = cardNum.charAt(14) + "";
			if (StringUtil.isNumber(temp)) {
				sexNum = Integer.parseInt(temp);
			}
		} else if (cardNum.length() == 18) {
			String temp = cardNum.charAt(16) + "";
			if (StringUtil.isNumber(temp)) {
				sexNum = Integer.parseInt(temp);
			}
		}
		if (sexNum > -1 && sexNum % 2 == 0) {
			return 2;
		} else if (sexNum > -1 && sexNum % 2 != 0) {
			return 1;
		} else {
			throw new Exception("未能判断男女。");
		}
	}
}
