package com.huoli.utils;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.Locale;
import java.util.TimeZone;

import org.apache.commons.lang3.StringUtils;
import org.apache.commons.lang3.time.DateFormatUtils;
import org.apache.commons.lang3.time.DateUtils;
import org.apache.commons.lang3.time.FastDateFormat;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 
 * 描述：日期时间工具类<br>
 * 版权：Copyright (c) 2011<br>
 * 公司：北京活力天汇<br>
 * 作者：童凡<br>
 * 版本：1.0<br>
 * 创建日期：2012-3-5<br>
 */
public class DateTimeUtil {
	private static Logger logger = LoggerFactory.getLogger(DateTimeUtil.class);

	/**
	 * 秒
	 */
	public static final long SECOND = 1000;

	/**
	 * 分钟
	 */
	public static final long MINUTE = 60 * SECOND;

	/**
	 * 小时
	 */
	public static final long HOUR = 60 * MINUTE;

	/**
	 * 天
	 */
	public static final long DAY = 24 * HOUR;

	/**
	 * 月，每月30天来计算
	 */
	public static final long MONTH = 30 * DAY;

	/**
	 * 年，计算1年365天来算
	 */
	public static final long YEAR = 365 * DAY;

	/**
	 * yyyy-MM-dd格式
	 */
	public static final String YYYYMMDD = "yyyy-MM-dd";

	/**
	 * HH:mm格式
	 */
	public static final String HHmm = "HH:mm";

	/**
	 * HH:mm:ss格式
	 */
	public static final String HHmmss = HHmm + ":ss";

	/**
	 * HH:mm:ss.SSS格式
	 */
	public static final String HHmmssSSS = HHmmss + ".SSS";

	/**
	 * yyyy-MM-dd HH:mm格式
	 */
	public static final String YYYYMMDDHHmm = YYYYMMDD + " " + HHmm;

	/**
	 * yyyy-MM-dd HH:mm:ss格式
	 */
	public static final String YYYYMMDDHHmmss = YYYYMMDD + " " + HHmmss;

	/**
	 * yyyy-MM-dd HH:mm:ss.SSS格式
	 */
	public static final String YYYYMMDDHHmmssSSS = YYYYMMDD + " " + HHmmssSSS;

	/**
	 * 默认格式：yyyy-MM-dd
	 */
	public static final String defaultDatePattern = YYYYMMDD;

	/** 日期时间格式列表 */

	/**
	 * 解析时间，格式HH:mm
	 * 
	 * @param timeString
	 * @return
	 * @throws ParseException
	 */
	public static Date parseTime(String timeString) {
		Date time = null;
		try {
			time = DateUtils.parseDate(timeString, HHmm);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return time;
	}

	/**
	 * 删除时、分、秒和毫秒，只留下日期
	 * 
	 * @param date
	 * @return
	 */
	public static Date trancateToDate(Date date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.set(Calendar.HOUR_OF_DAY, 0);
		calendar.set(Calendar.MINUTE, 0);
		calendar.set(Calendar.SECOND, 0);
		calendar.set(Calendar.MILLISECOND, 0);
		return calendar.getTime();

	}

	/**
	 * 格式化日期到分钟
	 * 
	 * @return 格式化之后的字符串
	 */
	public String formatDateEndWithMinute(Date date) {
		return FastDateFormat.getInstance(YYYYMMDDHHmm).format(date);

	}

	/**
	 * 根据时间加上今天的日期
	 * 
	 * @param timeString
	 * @return
	 */
	public static String addToday(String timeString) {
		if (timeString.length() == 5) {
			timeString = timeString + ":00";
		}
		return DateFormatUtils.format(now(), YYYYMMDD) + " " + timeString;
	}

	/**
	 * 格式化完整日期
	 * 
	 * @param date
	 * @return
	 */
	public static String formatFullDate(Date date) {
		if (date == null) {
			return null;
		}
		return DateFormatUtils.format(date, YYYYMMDDHHmmss);
	}

	public static Date parseFullDate(String str) {
		if (str == null) {
			return null;
		}
		if ("".equals(str)) {
			return null;
		}
		try {
			Date date = FastDateFormat.getInstance(YYYYMMDDHHmmss).parse(str);
			return date;
		} catch (Exception e) {
			logger.warn("出错啦", e);
		}
		return null;
	}

	/**
	 * 格式化日期,yyyy-MM-dd
	 * 
	 * @param date
	 * @return
	 */
	public static String formatDate(Date date) {
		return DateFormatUtils.format(date, defaultDatePattern);
	}

	public static Date getToday() {
		return DateUtils.truncate(now(), Calendar.DATE);
	}

	public static String getTodayString() {
		return DateFormatUtils.format(getToday(), defaultDatePattern);
	}

	public static Date getYesterday() {
		return DateUtils.addDays(getToday(), -1);
	}

	public static String getYesterdayString() {
		return DateFormatUtils.format(getYesterday(), defaultDatePattern);
	}

	public static Date getTomorrow() {
		return DateUtils.addDays(getToday(), 1);
	}

	public static String getTomorrowString() {
		return DateFormatUtils.format(getTomorrow(), defaultDatePattern);
	}

	public static int getDateWeek(String timeStr) {
		return getDateWeek(timeStr, YYYYMMDD);
	}

	public static int getDateWeek(String timeStr, String pattern) {
		Date time = DateTimeUtil.parse(timeStr, pattern);
		if (time == null) {
			return 0;
		}
		return getDateWeek(time);
	}

	public static int getDateWeek(Date time) {
		Calendar c = Calendar.getInstance();
		c.setTime(time);
		int i = c.get(Calendar.DAY_OF_WEEK) - 1;
		return i;
	}

	/**
	 * 比较两个时间天数的差值
	 * 
	 * @param date1
	 * @param date2
	 * @return 两个时间天数的差值
	 */
	public static int getDateDiffDay(Date date1, Date date2) {
		Calendar c = Calendar.getInstance();
		c.setTime(date1);
		int day1 = c.get(Calendar.DATE);// 获取日
		c.setTime(date2);
		int day2 = c.get(Calendar.DATE);// 获取日
		return day1 - day2;
	}

	/**
	 * 比较两个时间的差，返回天数，用时间1-时间2 <br>
	 * 比较只针对日期,对于时间不做比较
	 * 
	 * @param date1
	 *            时间1
	 * @param date2
	 *            时间2
	 * @return 两个时间相差的天数
	 */
	public static int getDateDiffDays(Date date1, Date date2) {
		if (date1 == null || date2 == null)
			return 0;

		return getDateDiffSecond(trancateToDate(date1), trancateToDate(date2)) / (60 * 60 * 24);
	}

	/**
	 * 比较两个时间的差，返回分钟数，用时间1-时间2 注解： 作者：lanlei 生成日期：Aug 15, 2009
	 * 
	 * @param date1
	 *            时间1
	 * @param date2
	 *            时间2
	 * @return 两个时间相差的分钟数
	 */
	public static int getDateDiffMinute(Date date1, Date date2) {
		return getDateDiffSecond(date1, date2) / 60;
	}

	/**
	 * 比较两个时间的差，返回秒数，用时间1-时间2
	 * 
	 * @param date1
	 * @param date2
	 * @return 两个时间相差的秒数
	 */
	public static int getDateDiffSecond(Date date1, Date date2) {
		return (int) (getDateDiffMillisecond(date1, date2) / 1000);
	}

	/**
	 * 比较两个时间的差，返回毫秒数，用时间1-时间2
	 * 
	 * @param date1
	 * @param date2
	 * @return 两个时间相差的秒数
	 */
	public static long getDateDiffMillisecond(Date date1, Date date2) {
		if (date1 == null || date2 == null)
			return 0;

		long time1 = date1.getTime();
		long time2 = date2.getTime();

		long diff = time1 - time2;

		Long longValue = new Long(diff);

		long di = longValue;
		return di;
	}

	/**
	 * 解析日期
	 * 
	 * @param dateString
	 * @return
	 */
	public static Date parseDate(String dateString) {
		Date date = null;
		try {
			date = FastDateFormat.getInstance(YYYYMMDD).parse(dateString);
		} catch (ParseException e) {
			logger.warn("出错啦", e);
		}
		return date;
	}

	/**
	 * 在日期上增加数个整日
	 * 
	 * @param date
	 *            日期
	 * @param n
	 *            天数
	 * @return
	 */
	public static Date addDay(Date date, int n) {
		return DateUtils.addDays(date, n);
	}

	/**
	 * 在日期上增加分钟
	 * 
	 * @param date
	 *            日期
	 * @param n
	 *            分钟
	 * @return
	 */
	public static Date addMinute(Date date, int n) {
		return DateUtils.addMinutes(date, n);
	}

	/**
	 * 在日期上增加小时
	 * 
	 * @param date
	 *            日期
	 * @param n
	 *            小时
	 * @return
	 */
	public static Date addHours(Date date, int n) {
		return DateUtils.addHours(date, n);
	}

	/**
	 * 在日期上增加秒
	 * 
	 * @param date
	 *            日期
	 * @param n
	 *            秒
	 * @return
	 */
	public static Date addSecond(Date date, int n) {
		return DateUtils.addSeconds(date, n);
	}

	/**
	 * 在日期上增加豪秒
	 * 
	 * @param date
	 *            日期
	 * @param n
	 *            豪秒
	 * @return
	 */
	public static Date addMilliseconds(Date date, int n) {
		return DateUtils.addMilliseconds(date, n);
	}

	/**
	 * 使用参数Format格式化Date成字符串
	 * 
	 * @param date
	 * @param pattern
	 * @return
	 */
	public static String format(Date date, String pattern) {
		return date == null ? null : DateFormatUtils.format(date, pattern);
	}

	/**
	 * 检验输入是否为正确的日期格式(不含秒的任何情况),严格要求日期正确性,自定议格式:yyyy-MM-dd HH:mm (需要的时候变为有时、分)
	 * 
	 * @param sourceDate
	 * @param pattern
	 * @return
	 */
	public static boolean checkDate(String sourceDate, String pattern) {
		if (sourceDate == null) {
			return false;
		}
		try {
			DateFormat dateFormat = new SimpleDateFormat(pattern);
			dateFormat.setLenient(false);
			dateFormat.parse(sourceDate);
			return true;

		} catch (Exception e) {
			// logger.warn("出错啦", e);
		}
		return false;
	}

	public static boolean checkDate(String sourceDate, String pattern, String localeString) {
		if (sourceDate == null) {
			return false;
		}
		try {
			DateFormat dateFormat = new SimpleDateFormat(pattern, new Locale(localeString));
			dateFormat.setLenient(false);
			dateFormat.parse(sourceDate);
			return true;

		} catch (Exception e) {
			// logger.warn("出错啦", e);
		}
		return false;
	}

	/**
	 * 使用参数Format将字符串转为Date
	 * 
	 * @param strDate
	 * @param pattern
	 * @return
	 * @throws ParseException
	 */
	public static Date parse(String strDate, String pattern) {
		try {
			return (strDate == null || strDate.equals("")) ? null : FastDateFormat.getInstance(pattern).parse(strDate);
		} catch (ParseException e) {
			logger.warn("出错啦", e);
		}
		return null;
	}

	/**
	 * 使用参数Format将字符串转为Date
	 * 
	 * @param strDate
	 * @param pattern
	 * @return
	 * @throws ParseException
	 */
	public static Date parse(String strDate, String pattern, String localeString) {

		try {
			FastDateFormat dateFormat = FastDateFormat.getInstance(pattern, new Locale(localeString));
			return (strDate == null || strDate.equals("")) ? null : dateFormat.parse(strDate);
		} catch (ParseException e) {
			logger.warn("出错啦", e);
		}
		return null;
	}

	/**
	 * 根据当前时间取得航班日期,如果是0~4点，判断为昨天的航班，否则为今天的航班
	 * 
	 * @return
	 */
	public static String getFlightDate() {
		Date now = now();
		if (isTimeBetweenClock(now, 0, 4)) {
			return DateTimeUtil.getYesterdayString();
		}
		return DateTimeUtil.getTodayString();
	}

	/**
	 * 解析航班时间格式，在0-4点自动添加1天时间
	 * 
	 * @param strDate
	 *            时间 HH:mm 或 HHmm
	 * @param date
	 *            日期 yyyy-MM-dd 格式
	 * @return
	 */
	public static Date getFlightTime(String strDate, String toDay) {
		Date time = getTicketTime(strDate, toDay);
		if (time == null) {
			return null;
		}
		if (isTimeBetweenClock(time, 0, 4)) {
			return addDay(time, 1);
		} else {
			return time;
		}
	}

	/**
	 * 解析机票时间格式
	 * 
	 * @param strDate
	 *            时间 HH:mm 或 HHmm
	 * @param date
	 *            日期 yyyy-MM-dd 格式
	 * @return
	 */
	public static Date getTicketTime(String strDate, String toDay) {
		if (StringUtil.isEmpty(strDate) || StringUtil.equals(strDate, "false")) {
			return null;
		}

		if (!(strDate.length() == 5 || strDate.length() == 4)) {
			return null;
		}
		if (strDate.length() == 4) {
			strDate = DateTimeUtil.format(DateTimeUtil.parse(strDate, "HHmm"), HHmm);
		}
		return DateTimeUtil.fillTimeWithDateTime(strDate, HHmm, toDay, "zh_CN");
	}

	/**
	 * 判断一个日期是否在两个时间之间
	 * 
	 * @param date
	 * @param startClock
	 * @param endClock
	 * @return
	 */
	public static boolean isTimeBetweenClock(Date date, int startClock, int endClock) {
		if (startClock > endClock && endClock == 0) {
			endClock = 24;
		}
		long currentClock = DateUtils.getFragmentInHours(date, Calendar.DATE);
		return startClock <= currentClock && endClock > currentClock;
	}

	/**
	 * 判断时间是否在某个时间区间之内
	 * 
	 * @param date
	 * @param startDate
	 * @param endDate
	 * @return
	 */
	public static boolean isTimeBetweenTime(Date date, Date startDate, Date endDate) {
		long currentClock = DateUtils.getFragmentInMilliseconds(date, Calendar.DATE);
		long startMilliseconds = DateUtils.getFragmentInMilliseconds(startDate, Calendar.DATE);
		long endMilliseconds = DateUtils.getFragmentInMilliseconds(endDate, Calendar.DATE);
		if (endMilliseconds == 0) {
			endMilliseconds = 24 * 60 * 60 * 1000;
		}
		return currentClock >= startMilliseconds && currentClock < endMilliseconds;
	}

	/**
	 * 判断时间是否在某个时间区间之内
	 * 
	 * @param date
	 * @param startTimeString
	 * @param endTimeString
	 * @return
	 */
	public static boolean isTimeBetweenTime(Date date, String startTimeString, String endTimeString) {
		Date startTime = parseTime(startTimeString);
		Date endTime = parseTime(endTimeString);
		long currentClock = DateUtils.getFragmentInMilliseconds(date, Calendar.DATE);
		long startMilliseconds = DateUtils.getFragmentInMilliseconds(startTime, Calendar.DATE);
		long endMilliseconds = DateUtils.getFragmentInMilliseconds(endTime, Calendar.DATE);
		if (endMilliseconds == 0) {
			endMilliseconds = 24 * 60 * 60 * 1000;
		}
		return currentClock >= startMilliseconds && currentClock < endMilliseconds;
	}

	/**
	 * 使用今天的日期填充时间中没有的属性
	 * 
	 * @param time
	 * @param pattern
	 * @return
	 */
	// public static String fillDateToday(Date time, String pattern) {
	// if (time == null) {
	// return null;
	// }
	// // 需要修正时间
	// if (pattern.indexOf("d") == -1 || pattern.indexOf("M") == -1 ||
	// pattern.indexOf("y") == -1) {
	//
	// Calendar nowCalendar = Calendar.getInstance();
	// Calendar timeCalendar = Calendar.getInstance();
	// timeCalendar.setTime(time);
	// if (pattern.indexOf("y") == -1 && pattern.indexOf("M") == -1
	// && pattern.indexOf("d") == -1) {// 时间格式中没有年月日
	// timeCalendar.set(Calendar.YEAR, nowCalendar.get(Calendar.YEAR));
	// timeCalendar.set(Calendar.MONTH, nowCalendar.get(Calendar.MONTH));
	// timeCalendar.set(Calendar.DATE, nowCalendar.get(Calendar.DATE));
	// } else if (pattern.indexOf("y") == -1 && pattern.indexOf("M") == -1) {//
	// 时间格式中没有月
	// timeCalendar.set(Calendar.YEAR, nowCalendar.get(Calendar.YEAR));
	// timeCalendar.set(Calendar.MONTH, nowCalendar.get(Calendar.MONTH));
	// } else if (pattern.indexOf("y") == -1) {// 时间格式中没有日
	// timeCalendar.set(Calendar.YEAR, nowCalendar.get(Calendar.YEAR));
	// }
	// time = timeCalendar.getTime();
	// }
	// return DateFormatUtils.format(time, "yyyy-MM-dd HH:mm:ss");

	// fillTimeWithDate(timeString, pattern, getTodayString());
	//
	// }

	/**
	 * 使用判定的飞行日期填充时间中没有的属性
	 * 
	 * @param timeString
	 * @param pattern
	 * @return
	 * @throws ParseException
	 */
	public static String fillTimeWithFlightDate(String timeString, String pattern) throws ParseException {
		return fillTimeWithDate(timeString, pattern, getFlightDate());
	}

	/**
	 * 使用判定的飞行日期填充时间中没有的属性
	 * 
	 * @param timeString
	 * @param pattern
	 * @return
	 * @throws ParseException
	 */
	public static String fillTimeWithFlightDate(String timeString, String pattern, String localeString) throws ParseException {
		return fillTimeWithDate(timeString, pattern, getFlightDate(), localeString);
	}

	/**
	 * 使用今天的日期填充时间中没有的属性
	 * 
	 * @param timeString
	 * @param pattern
	 * @return
	 * @throws ParseException
	 */
	public static String fillTimeWithToday(String timeString, String pattern) throws ParseException {
		return fillTimeWithDate(timeString, pattern, getTodayString());
	}

	/**
	 * 使用判定的飞行日期对提供了时分的日期进行填充
	 * 
	 * @param timeString
	 * @return
	 * @throws ParseException
	 */
	public static String fillTimeHHMMWithFlightDate(String timeString) throws ParseException {
		return fillTimeWithFlightDate(timeString, HHmm);
	}

	/**
	 * 使用今天的日期对提供了时分的日期进行填充
	 * 
	 * @param timeString
	 * @return
	 * @throws ParseException
	 */
	public static String fillTimeHHMMWithToday(String timeString) throws ParseException {
		return fillTimeWithToday(timeString, HHmm);
	}

	/**
	 * 使用给定的日期填充时间中没有的属性
	 * 
	 * @param timeString
	 *            时间
	 * @param pattern
	 *            时间的格式
	 * @param dateString
	 *            填充日期,yyyy-MM-dd格式
	 * @return
	 * @throws ParseException
	 */
	public static Date fillTimeWithDateTime(String timeString, String pattern, String dateString, String localeString) {
		Date date = parseDate(dateString);
		if (isNotIncludeYMD(pattern)) {// 时间格式中没有年月日
			return parse(dateString + " " + timeString, "yyyy-MM-dd " + pattern, localeString);
		} else if (isNotIncludeYM(pattern)) {// 时间格式中没有年月
			return parse(format(date, "yyyy-MM-") + timeString, "yyyy-MM-" + pattern, localeString);
		} else if (isNotIncludeY(pattern)) {// 时间格式中没有年
			return parse(format(date, "yyyy-") + timeString, "yyyy-" + pattern, localeString);
		}
		return parse(timeString, pattern, localeString);
	}

	/**
	 * 已有MM-dd,就近补年份
	 * 
	 * @param dateString
	 * @return
	 */
	public static String fillYearNearly(String dateString) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(getToday());
		// 当前年份
		int year = calendar.get(Calendar.YEAR);
		calendar.setTime(DateTimeUtil.parse(dateString, "MM-dd"));
		// 判断顺序为今年,明年,去年
		int[] offsets = { 0, 1, -1 };
		for (int i = 0; i < offsets.length; i++) {
			int offset = offsets[i];
			calendar.set(Calendar.YEAR, year + offset);
			int days = getDateDiffDays(DateTimeUtil.getToday(), calendar.getTime());
			// 183为一年的一半
			if (Math.abs(days) < 184) {
				return formatDate(calendar.getTime());
			}
		}
		return null;
	}

	/**
	 * 使用给定的日期填充时间中没有的属性
	 * 
	 * @param timeString
	 *            时间
	 * @param pattern
	 *            时间的格式
	 * @param dateString
	 *            填充日期,yyyy-MM-dd格式
	 * @return
	 * @throws ParseException
	 */
	public static String fillTimeWithDate(String timeString, String pattern, String dateString, String localeString) {
		return formatFullDate(fillTimeWithDateTime(timeString, pattern, dateString, localeString));
	}

	/**
	 * 使用给定的日期填充时间中没有的属性
	 * 
	 * @param timeString
	 *            时间
	 * @param pattern
	 *            时间的格式
	 * @param dateString
	 *            填充日期,yyyy-MM-dd格式
	 * @return
	 * @throws ParseException
	 */
	public static String fillTimeWithDate(String timeString, String pattern, String dateString) {
		String localeLanguageString = "zh_CN";
		return fillTimeWithDate(timeString, pattern, dateString, localeLanguageString);
	}

	/**
	 * 日期格式不包含年月日
	 * 
	 * @param pattern
	 * @return
	 */
	public static boolean isNotIncludeYMD(String pattern) {
		return pattern.indexOf("y") == -1 && pattern.indexOf("M") == -1 && pattern.indexOf("d") == -1;
	}

	/**
	 * 日期格式不包含年月
	 * 
	 * @param pattern
	 * @return
	 */
	public static boolean isNotIncludeYM(String pattern) {
		return pattern.indexOf("y") == -1 && pattern.indexOf("M") == -1;
	}

	/**
	 * 日期格式包含日
	 * 
	 * @param pattern
	 * @return
	 */
	public static boolean isIncludeD(String pattern) {
		return pattern.indexOf("d") != -1;
	}

	/**
	 * 日期格式不包含年
	 * 
	 * @param pattern
	 * @return
	 */
	public static boolean isNotIncludeY(String pattern) {
		return pattern.indexOf("y") == -1;
	}

	/**
	 * 填充日期，对于没有年，没有月的日期数据，进行填充
	 * 
	 * @param dateString
	 * @param pattern
	 * @return
	 * @throws ParseException
	 */

	public static String fillDateWithToday(String dateString, String pattern) throws ParseException {
		Date date = now();
		if (isNotIncludeYM(pattern)) {// 时间格式中没有年月
			return formatDate(DateTimeUtil.parse(format(date, "yyyy-MM-") + dateString, "yyyy-MM-" + pattern));
		} else if (isNotIncludeY(pattern)) {// 时间格式中没有年
			return formatDate(DateTimeUtil.parse(format(date, "yyyy-") + dateString, "yyyy-" + pattern));
		}
		return formatDate(parse(dateString, pattern));
	}

	/**
	 * 在日期格式上加天数
	 * 
	 * @param dateString
	 * @param days
	 * @return
	 */
	public static String addDayInDateFormat(String dateString, int days) {
		Date date = parseDate(dateString);
		date = addDay(date, days);
		return formatDate(date);
	}

	/**
	 * 在完整格式上增加天数
	 * 
	 * @param dateString
	 * @param days
	 * @return
	 */
	public static String addDayInFullFormat(String dateString, int days) {
		Date date = parseFullDate(dateString);
		date = addDay(date, days);
		return formatFullDate(date);
	}

	/**
	 * 取得当前年，使用yyyy格式
	 * 
	 * @return
	 */
	public static String getCurrentYear() {
		return format(now(), "yyyy");
	}

	/**
	 * 日期相减，得到分钟差,date1-date2
	 * 
	 * @param date1
	 * @param date2
	 * @return
	 */
	public static long subtractInMinute(Date date1, Date date2) {
		if (date1 == null || date2 == null)
			return 0;

		long time1 = date1.getTime();
		long time2 = date2.getTime();

		long diff = time1 - time2;

		Long longValue = new Long(diff / 1000 / 60);
		return longValue;
	}

	/**
	 * 转换时间
	 * 
	 * @param date
	 *            当前时间
	 * @param t
	 *            当前时区
	 * @param t1
	 *            转换的时区
	 */
	public static Date changeTime(Date date, TimeZone t, TimeZone t1) {
		try {
			String format = "yyyy-MM-dd HH:mm:ss";
			Calendar loc = Calendar.getInstance();
			loc.setTime(date);

			GregorianCalendar gmtTime = new GregorianCalendar(t);
			gmtTime.set(Calendar.YEAR, loc.get(Calendar.YEAR));
			gmtTime.set(Calendar.MONTH, loc.get(Calendar.MONTH));
			gmtTime.set(Calendar.DAY_OF_MONTH, loc.get(Calendar.DAY_OF_MONTH));
			gmtTime.set(Calendar.HOUR_OF_DAY, loc.get(Calendar.HOUR_OF_DAY));
			gmtTime.set(Calendar.MINUTE, loc.get(Calendar.MINUTE));
			gmtTime.set(Calendar.SECOND, loc.get(Calendar.SECOND));

			SimpleDateFormat sf = new SimpleDateFormat(format);
			sf.setTimeZone(t1);
			String str = sf.format(gmtTime.getTime());

			SimpleDateFormat sf1 = new SimpleDateFormat(format);
			return sf1.parse(str);
		} catch (Exception e) {
			return date;
		}
	}

	/**
	 * 变换为格林威治时间
	 * 
	 * @param date
	 *            当前时间
	 */
	public static Date toGreenWichTime(Date date) {
		return toGreenWichTime(date, TimeZone.getDefault());
	}

	/**
	 * 变换为格林威治时间
	 * 
	 * @param date
	 *            当前时间
	 * @param t
	 *            当前时区
	 */
	public static Date toGreenWichTime(Date date, TimeZone t) {
		return changeTime(date, TimeZone.getDefault(), getGreenWichTimeZone());
	}

	/**
	 * 获取格林威治时区
	 * 
	 * @return
	 */
	public static TimeZone getGreenWichTimeZone() {
		return TimeZone.getTimeZone("Etc/Greenwich");
	}

	/** 设置当前时间,主要供测试时使用 */
	private static Date now = null;

	/**
	 * 取得当前时间,如果设置了时间,则取设置的时间,否则取当前时间
	 * 
	 * @return
	 */
	public static Date now() {
		if (now == null) {
			return new Date();
		} else {
			return now;
		}
	}

	/**
	 * 取得当前时间,按yyyy-MM-dd HH:mm:ss格式返回
	 * 
	 * @return
	 */
	public static String nowString() {
		return formatFullDate(now());
	}

	/**
	 * 设置当前时间,主要供测试使用
	 * 
	 * @param dateNow
	 */
	public static void setNow(Date dateNow) {
		now = dateNow;
	}

	/**
	 * 支持两种时间格式<br>
	 * yyyy-MM-dd HH:mm:ss和yyyy-MM-dd
	 * 
	 * @param dateString
	 */
	public static void setNow(String dateString) {
		if (isFullDate(dateString)) {
			now = parseFullDate(dateString);
		}
		if (isDate(dateString)) {
			now = parseDate(dateString);
		}
	}

	public static void clearNow() {
		now = null;
	}

	/**
	 * 判断是不是符合一个标准的日期格式,yyyy-MM-dd
	 * 
	 * @return
	 */
	public static boolean isDate(String dateString) {
		if (StringUtils.isNotBlank(dateString) && dateString.matches("^\\d{4}-\\d{2}-\\d{2}$")) {
			return true;
		}
		return false;
	}

	/**
	 * 判断是不是符合一个完整的时间格式,yyyy-MM-dd HH:mm:ss
	 * 
	 * @return
	 */
	public static boolean isFullDate(String dateString) {
		if (StringUtils.isNotBlank(dateString) && dateString.matches("^\\d{4}-\\d{2}-\\d{2} \\d{2}:\\d{2}:\\d{2}$")) {
			return true;
		}
		return false;
	}

}
