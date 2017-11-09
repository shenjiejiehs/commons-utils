package com.huoli.utils;

import java.io.UnsupportedEncodingException;
import java.lang.Character.UnicodeBlock;
import java.util.ArrayList;
import java.util.List;
import java.util.regex.Matcher;
import java.util.regex.Pattern;

import org.apache.commons.lang3.CharUtils;
import org.apache.commons.lang3.StringUtils;

/**
 * @author 罗良杰
 * @version 创建时间：2014年9月17日 下午3:05:03 string帮助
 */
public class StringUtil extends StringUtils {
	// 特殊字符过滤
	public static List<Character> specialChar = new ArrayList<Character>();

	static {
		specialChar.add('');
	}

	/**
	 * 如果str==null 则返回EMPTY 如果str有值，则去掉前后空格（trim()）
	 */
	public static String delNull(String str) {
		return delStr(str, "null");
	}

	/**
	 * 如果str==null 则返回EMPTY 如果str有值，则去掉前后空格（trim()）
	 */
	public static boolean isNull(String str) {
		if (isEmpty(str)) {
			return true;
		}
		return equals(str, "null");
	}

	/**
	 * 删除字符串
	 * 
	 * @param src 来源
	 * @param special 数组 如果等于则返回EMPTY
	 */
	public static String delStr(String str, String... specials) {
		if (isEmpty(str)) {
			return EMPTY;
		} else if (equals(str, specials)) {
			return EMPTY;
		} else {
			return str.trim();
		}
	}

	/**
	 * 是否相等，不区分大小写，如果s2中有一位字符串等于s1则返回 true
	 * 
	 * @param s1 需要对比的字符串
	 * @param s2 需要对比的字符串数组
	 * @return
	 */
	public static boolean equals(String s1, String... s2) {
		if (isEmpty(s1)) {
			return false;
		}

		if (s2 == null || s2.length == 0) {
			return false;
		}
		for (String s : s2) {
			if (StringUtil.equalsIgnoreCase(s1.trim(), s.trim())) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 判断数组数据是否全部为空或null
	 * 
	 * @param strs 数组数据
	 * @return
	 */
	public static boolean isEmptyStrs(String... strs) {
		if (strs == null || strs.length == 0) {
			return true;
		}

		for (String s : strs) {
			if (!StringUtils.isEmpty(s)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断数组数据是否全部不为空或null
	 * 
	 * @param strs 数组数据
	 * @return
	 */
	public static boolean isNotEmptyStrs(String... strs) {
		if (strs == null || strs.length == 0) {
			return false;
		}

		for (String s : strs) {
			if (StringUtils.isEmpty(s)) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 比较2个字符串大小
	 * 
	 * @param s1 需要对比的字符串
	 * @param s2 需要对比的字符串
	 * @param equals 是否相等，为true
	 * @return
	 */
	public static boolean compareTo(String s1, String s2, boolean equals) {
		if (isEmpty(s1) || isEmpty(s2)) {
			return true;
		}
		int i = s1.compareTo(s2);
		if (i > 0) {
			return true;
		}
		if (equals && i == 0) {
			return true;
		}
		return false;
	}

	/**
	 * 判断字符串是否为纯字母
	 */
	public static boolean isLetter(String s) {
		if (isEmpty(s)) {
			return false;
		}
		for (int i = 0; i < s.length(); i++) {
			if (!CharUtils.isAsciiAlpha(s.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断字符串是否为纯字母或数字
	 */
	public static boolean isLetterAndNumber(String s) {
		if (isEmpty(s)) {
			return false;
		}
		for (int i = 0; i < s.length(); i++) {
			if (!CharUtils.isAsciiAlphanumeric(s.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断字符串是否为数字，增加负数判断
	 */
	public static boolean isNumber(String s) {
		if (isEmpty(s)) {
			return false;
		}
		// 如果为负数
		if (startsWith(s, "-")) {
			s = removeStart(s, "-");
		}
		for (int i = 0; i < s.length(); i++) {
			if (!CharUtils.isAsciiNumeric(s.charAt(i))) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断字符串是否为数字
	 */
	public static boolean isDouble(String s) {
		if (isEmpty(s)) {
			return false;
		}
		if (s.lastIndexOf(".") != s.indexOf(".")) {
			return false;
		}
		// 如果为负数
		if (startsWith(s, "-")) {
			s = removeStart(s, "-");
		}
		for (int i = 0; i < s.length(); i++) {
			if (!CharUtils.isAsciiNumeric(s.charAt(i)) && s.charAt(i) != '.') {
				return false;
			}
		}
		return true;
	}

	/**
	 * 将字符串中的某些字符转换成相对应的字符
	 * 
	 * @param original 待处理字符串。
	 * @param character 待替换的子串组。如：{"find":"replace"}, {"find1":"replace1"}
	 * @return 处理后的字符串
	 */
	public static String replace(String text, String[][] character) {
		if (isEmpty(text) || character.length == 0) {
			return text;
		}
		String[] searchList = new String[character.length];
		String[] replacementList = new String[character.length];
		for (int i = 0; i < character.length; i++) {
			if (character[i].length == 2) {
				searchList[i] = character[i][0];
				replacementList[i] = character[i][1];
			}
		}
		return replaceEach(text, searchList, replacementList);
	}

	/**
	 * 将字符串中行去掉
	 */
	public static String replaceLine(String text) {
		if (isEmpty(text)) {
			return text;
		}
		return replace(text, FileUtil.NEW_LINE, "");
	}

	/**
	 * 清理特殊字符
	 * 
	 * @param text 需要清理的字符
	 * @param special5 是否调用特殊5的方法
	 * @return
	 */
	public static String cleanText(String text, boolean special5) {
		String realText = text;
		try {
			char[] myBuffer = text.toCharArray();

			StringBuffer sb = new StringBuffer();
			for (int i = 0; i < text.length(); i++) {
				if (specialChar.contains(myBuffer[i])) {
					continue;
				}

				UnicodeBlock ub = UnicodeBlock.of(myBuffer[i]);
				if (ub == UnicodeBlock.BASIC_LATIN) {
					// 英文及数字等
					sb.append(myBuffer[i]);
				} else if (ub == UnicodeBlock.HALFWIDTH_AND_FULLWIDTH_FORMS) {
					// 全角半角字符
					sb.append(myBuffer[i]);
				} else if (myBuffer[i] >= 0xD800 && myBuffer[i] <= 0xDFFF) {
					i++;
					continue;
				} else {
					// 汉字
					String temp = String.valueOf(myBuffer[i]);
					if (special5) {
						if (judgeIfSpecial5(temp.getBytes("unicode"))) {
							continue;
						}
					} else {
						if (judgeIfSpecial(temp.getBytes("unicode"))) {
							continue;
						}
					}
					sb.append(myBuffer[i]);
				}
			}
			realText = sb.toString();
		} catch (Exception e) {
			realText = "weixin";
		}
		return realText;
	}

	/**
	 * 判断是否为特殊字符
	 */
	public static boolean judgeIfSpecial(byte[] c) {
		if (c.length != 2 && c.length != 4)
			return false;
		byte[] b = new byte[2];
		if (c.length == 4) {
			b[0] = c[2];
			b[1] = c[3];
		} else {
			b[0] = c[0];
			b[1] = c[1];
		}
		if (b[0] != -32 && b[0] != -31 && b[0] != -30 && b[0] != -29 && b[0] != -28 && b[0] != -27 && b[0] != 38
				&& b[0] != 39) {
			return false;
		}
		switch (b[0]) {
		case -32: // 0xe0 0xE001,0xE05A
			if (b[1] >= 1 && b[1] <= 90) // 0x01, 0x5A
			{
				return true;
			}
			break;
		case -31: // 0xe1 0xE101,0xE15A
			if (b[1] >= 1 && b[1] <= 90) // 0x01, 0x5A
			{
				return true;
			}
			break;
		case -30: // 0xe2 0xE201,0xE253
			if (b[1] >= 1 && b[1] <= 83) // 0x01, 0x53
			{
				return true;
			}
			break;
		case -29: // 0xe3 0xE301,0xE34d
			if (b[1] >= 1 && b[1] <= 77) // 0x01, 0x4d
			{
				return true;
			}
			break;
		case -28: // 0xe4 0xE401,0xE44C
			if (b[1] >= 1 && b[1] <= 76) // 0x01, 0x4c
			{
				return true;
			}
			break;
		case -27: // 0xe5 0xE501,0xE537
			if (b[1] >= 1 && b[1] <= 55) // 0x01, 0x37
			{
				return true;
			}
			break;
		case 38: // 0xe0 0xE001,0xE05A
			if (b[1] >= -90 && b[1] <= 90) // 0x01, 0x5A
			{
				return true;
			}
			break;
		case 39: // 0xe0 0xE001,0xE05A
			if (b[1] >= -90 && b[1] <= 90) // 0x01, 0x5A
			{
				return true;
			}
			break;
		default:
			break;
		}
		return false;
	}

	/**
	 * 判断是否为特殊字符5
	 */
	public static boolean judgeIfSpecial5(byte[] c) {
		if (c.length != 2 && c.length != 4)
			return false;
		byte[] b = new byte[2];
		if (c.length == 4) {
			b[0] = c[2];
			b[1] = c[3];
		} else {
			b[0] = c[0];
			b[1] = c[1];
		}
		if (b[0] != 38 && b[0] != 39 && b[0] != 12 && b[0] != 14 && b[0] != 15) {
			return false;
		}
		switch (b[0]) {
		case 12: // 0xe0 0xE001,0xE05A
			if (b[1] >= -90 && b[1] <= 100) // 0x01, 0x5A
			{
				return true;
			}
			break;
		case 14: // 0xe0 0xE001,0xE05A
			if (b[1] >= -90 && b[1] <= 100) // 0x01, 0x5A
			{
				return true;
			}
			break;
		case 15: // 0xe0 0xE001,0xE05A
			if (b[1] >= -90 && b[1] <= 100) // 0x01, 0x5A
			{
				return true;
			}
			break;
		case 38: // 0xe0 0xE001,0xE05A
			if (b[1] >= -90 && b[1] <= 90) // 0x01, 0x5A
			{
				return true;
			}
			break;
		case 39: // 0xe0 0xE001,0xE05A
			if (b[1] >= -90 && b[1] <= 90) // 0x01, 0x5A
			{
				return true;
			}
			break;
		default:
			break;
		}
		return false;
	}

	/**
	 * 数组转换string
	 * 
	 * @param array 数组
	 * @param bufferStr 附加值
	 * @param splitStr 分割字符
	 * @return 例如：{"name", "value"} to: 'name', 'value' 则参数为bufferStr:'
	 *         splitStr:,
	 */
	public static String arrayToString(Object[] array, String bufferStr, String splitStr) {
		if (array == null || array.length == 0) {
			return EMPTY;
		}

		StringBuffer buf = new StringBuffer();
		for (int i = 0; i < array.length; i++) {
			if (!isEmpty(bufferStr)) {
				buf.append(bufferStr);
			}
			buf.append(array[i]);
			if (!isEmpty(bufferStr)) {
				buf.append(bufferStr);
			}
			if (!isEmpty(splitStr)) {
				buf.append(splitStr);
			}
		}
		if (!isEmpty(splitStr) && isNotEmpty(buf.toString())) {
			buf.deleteCharAt(buf.lastIndexOf(splitStr));
		}
		return buf.toString();
	}

	/**
	 * 转换编码
	 * 
	 * @param src 来源
	 * @param fromCharset 转码前编码
	 * @param toCharset 转码后编码
	 * @return
	 */
	public static String transcodeStr(String s, String fromCharset, String toCharset) {
		if (StringUtil.isEmpty(s)) {
			return s;
		}
		try {
			s = delNull(s);
			return new String(s.getBytes(fromCharset), toCharset);
		} catch (UnsupportedEncodingException e) {
		}
		return s;
	}

	/**
	 * 去除重复的字符串<br>
	 * 比如"上海浦东上海浦东",应该返回上海浦东<br>
	 * 逻辑上最多支持5次重复
	 * 
	 * @param source 来源字符串
	 * @return 去除重复之后的字符串
	 */
	public static String clearDuplicate(String source) {
		return clearDuplicate(source, 5);
	}

	/**
	 * 去除重复的字符串<br>
	 * 比如"上海浦东上海浦东",应该返回上海浦东<br>
	 * timesLimit参数不能小于等于0,逻辑上默认支持5次重复,
	 * 
	 * @param source
	 * @param timesLimit 次数的限制
	 * @return
	 */
	public static String clearDuplicate(String source, int timesLimit) {
		if (timesLimit <= 0) {
			timesLimit = 5;
		}
		if (StringUtils.isNotBlank(source)) {
			for (int i = timesLimit; i > 0; i--) {
				if (source.length() % i == 0) {
					// 分解的长度
					int subLength = source.length() / i;
					boolean matched = true;
					for (int j = 0; j < i - 1; j++) {
						if (!StringUtils.equals(source.substring(subLength * j, subLength * (j + 1)),
								source.substring(subLength * (j + 1), subLength * (j + 2)))) {
							matched = false;
							break;
						}
					}
					if (matched) {
						return source.substring(0, subLength);
					}
				}
			}
		}
		return source;
	}

	/**
	 * 检测是否有emoji字符
	 */
	public static boolean containsEmoji(String source) {
		if (StringUtil.isBlank(source)) {
			return false;
		}

		int len = source.length();

		for (int i = 0; i < len; i++) {
			char codePoint = source.charAt(i);
			if (isEmojiCharacter(codePoint)) {
				// do nothing，判断到了这里表明，确认有表情字符
				return true;
			}
		}

		return false;
	}

	private static final Pattern EMOJI_PATTERN = Pattern.compile(RegExp.EMOJI, Pattern.UNICODE_CASE
			| Pattern.CASE_INSENSITIVE);

	private static final Pattern SYMBOL_PATTERN = Pattern.compile(RegExp.SYMBOL, Pattern.UNICODE_CASE
			| Pattern.CASE_INSENSITIVE);

	/**
	 * 验证是否emoji
	 */
	public static boolean isEmojiCharacter(char code) {
		String str = String.valueOf(code);

		Matcher symbolMatcher = SYMBOL_PATTERN.matcher(str);
		boolean isSymbol = symbolMatcher.find();
		if (isSymbol) {
			return false;
		}

		Matcher emojiMatcher = EMOJI_PATTERN.matcher(str);
		return emojiMatcher.find();
	}

	/**
	 * 过滤emoji 或者 其他非文字类型的字符
	 */
	public static String filterEmoji(String source) {

		if (!containsEmoji(source)) {
			return source;// 如果不包含，直接返回
		}
		// 到这里铁定包含
		StringBuilder buf = new StringBuilder(source.length());

		int len = source.length();

		for (int i = 0; i < len; i++) {
			char codePoint = source.charAt(i);
			if (!isEmojiCharacter(codePoint)) {
				buf.append(codePoint);
			}
		}
		return buf.toString();
	}

	/**
	 * 字符串转换,全角转半角符号
	 * 
	 * @param string
	 * @return
	 */
	public static String full2Half(String string) {
		if (isEmpty(string)) {
			return string;
		}

		char[] charArray = string.toCharArray();
		for (int i = 0; i < charArray.length; i++) {
			char c = charArray[i];
			if (c == 12288) {
				c = ' ';
			} else if (c >= 65281 && c <= 65374) {
				c = (char) (c - 65248);
			}
			charArray[i] = c;
		}
		return new String(charArray);
	}

	/**
	 * 字符串转换,半角符号转全角符号
	 * 
	 * @param value
	 * @return
	 */
	public static String half2Full(String value) {
		if (isEmpty(value)) {
			return value;
		}
		char[] cha = value.toCharArray();

		/**
		 * full blank space is 12288, half blank space is 32 others :full is
		 * 65281-65374,and half is 33-126.
		 */
		for (int i = 0; i < cha.length; i++) {
			char c = cha[i];
			if (c == 32) {
				c = (char) 12288;
			} else if (c > 32 && c < 127) {
				c = (char) (c + 65248);
			}
			cha[i] = c;
		}
		return new String(cha);
	}

}