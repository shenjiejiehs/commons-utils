package com.huoli.utils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

/**
 * 描述：字符类型帮助类<br>
 * 版权：Copyright (c) 2011-2015<br>
 * 公司：北京活力天汇<br>
 * 作者：罗良杰<br>
 * 版本：1.0<br>
 * 创建日期：2015年4月15日<br>
 */
public class CharTypeUtil {
	public enum CharType {
		DELIMITER, // 非字母截止字符，例如，．）（　等等　（ 包含U0000-U0080）
		NUM, // 2字节数字１２３４
		LETTER, // gb2312中的，例如:ＡＢＣ，2字节字符同时包含 1字节能表示的 basic latin and latin-1
		OTHER, // 其他字符
		CHINESE, // 中文字
		SPECIAL_LETTER, // 特殊字母 例如：Â latin-1，编码区间0080-00ff
		SPECIAL_DELIMITER;// 特殊符号 例如：´¿ latin-1，编码区间0080-00ff
	}

	private static List<Character> specialChinese = new ArrayList<Character>();

	static {
		specialChinese.add('䶮');
		specialChinese.add('䮞');
		specialChinese.add('');

		// 𥖄
		specialChinese.add((char) 55381);
		specialChinese.add((char) 56708);

		// 䶮
		specialChinese.add((char) 59491);
		// 䮞
		specialChinese.add((char) 19358);
		// 㭎
		specialChinese.add((char) 15182);
	}

	/**
	 * 加载特殊中文字符
	 * @param c
	 */
	public static void loadChineseChar(Character c) {
		specialChinese.add(c);
	}

	/**
	 * 是否是纯中文
	 */
	public static boolean isAllChinese(String name) {
		if (StringUtil.isEmpty(name)) {
			return false;
		}
		char[] chars = name.toCharArray();
		for (int i = 0; i < chars.length; i++) {
			char ch = chars[i];
			CharType ct = CharTypeUtil.checkType(ch);
			if (ct != CharType.CHINESE) {
				return false;
			}
		}
		return true;
	}

	/**
	 * 判断输入char类型变量的字符类型
	 * @param c char类型变量
	 * @return CharType 字符类型
	 */
	public static CharType checkType(char c) {
		CharType ct = null;
		// 特殊中文
		if (specialChinese.contains(c)) {
			ct = CharType.CHINESE;
		}

		// 中文，编码区间0x4e00-0x9fbb
		else if ((c >= 0x4e00) && (c <= 0x9fbb)) {
			ct = CharType.CHINESE;
		}
		// Halfwidth and Fullwidth Forms， 编码区间0xff00-0xffef 2字节英文字
		else if ((c >= 0xff00) && (c <= 0xffef)) {
			if (((c >= 0xff21) && (c <= 0xff3a)) || ((c >= 0xff41) && (c <= 0xff5a))) {
				ct = CharType.LETTER;
			}
			// 2字节数字
			else if ((c >= 0xff10) && (c <= 0xff19)) {
				ct = CharType.NUM;
			}// 其他字符，可以认为是标点符号
			else {
				ct = CharType.DELIMITER;
			}
		}
		// basic latin，编码区间 0000-007f
		else if ((c >= 0x0021) && (c <= 0x007e)) { // 1字节数字
			if ((c >= 0x0030) && (c <= 0x0039)) {
				ct = CharType.NUM;
			}
			// 1字节字符
			else if (((c >= 0x0041) && (c <= 0x005a)) || ((c >= 0x0061) && (c <= 0x007a))) {
				ct = CharType.LETTER;
			}
			// 其他字符，可以认为是标点符号
			else {
				ct = CharType.DELIMITER;
			}
		} // latin-1，编码区间0080-00ff
		else if ((c >= 0x00a1) && (c <= 0x00ff)) {
			if ((c >= 0x00c0) && (c <= 0x00ff)) {
				ct = CharType.SPECIAL_LETTER;
			} else {
				ct = CharType.SPECIAL_DELIMITER;
			}
		} else
			ct = CharType.OTHER;

		return ct;
	}

	/**
	 * 包含字符类型
	 * @param str 字符串
	 * @param chartype 字符类型
	 * @return
	 */
	public static boolean contains(String str, CharType chartype) {
		if (StringUtil.isEmpty(str)) {
			return false;
		}

		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			CharType ct = CharTypeUtil.checkType(c);
			if (ct == chartype) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 过滤字符
	 * @param str 需要过滤的字符
	 * @param types 需要过滤类型
	 * @return
	 */
	public static String filter(String str, CharType... types) {
		if (StringUtil.isEmpty(str)) {
			return str;
		}
		if (types == null || types.length == 0) {
			return str;
		}
		List<CharType> list = Arrays.asList(types);
		StringBuilder filter = new StringBuilder();
		for (int i = 0; i < str.length(); i++) {
			char c = str.charAt(i);
			CharType ct = CharTypeUtil.checkType(c);

			if (!list.contains(ct)) {
				filter.append(c);
			}
		}
		return filter.toString();
	}
}
