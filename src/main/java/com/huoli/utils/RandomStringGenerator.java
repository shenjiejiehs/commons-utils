package com.huoli.utils;

import java.util.Random;

/**
 * 随机字符串生成器 <br>
 * 版权：Copyright (c) 2011-2013<br>
 * 公司：北京活力天汇<br>
 * 作者：童凡<br>
 * 版本：1.0<br>
 * 创建日期：2013-9-10<br>
 */
public class RandomStringGenerator {
	public static final Random RANDOM = new Random();
	/** 大写字母 */
	public static final String UPPER_LETTERS = "ABCDEFGHIJKLMNOPQRSTUVWXYZ";
	/** 小写字母 */
	public static final String LOWER_LETTERS = "abcdefghijklmnopqrstuvwxyz";
	/** 数字 */
	public static final String DIGITALS = "0123456789";

	public static String generalRandomString(int length, boolean upperLetter, boolean lowerLetter, boolean digital) {
		StringBuilder sourceBuilder = new StringBuilder();
		if (upperLetter) {
			sourceBuilder.append(UPPER_LETTERS);
		}
		if (lowerLetter) {
			sourceBuilder.append(LOWER_LETTERS);
		}
		if (digital) {
			sourceBuilder.append(DIGITALS);
		}
		return generalRandomString(sourceBuilder.toString(), length);
	}
	
	public static String generalRandomString(String source, int length) {
		StringBuilder sourceBuilder = new StringBuilder(source);

		int sourceLength = sourceBuilder.length();
		for (int i = 0; i < length; i++) {
			sourceBuilder.append(sourceBuilder.charAt(RANDOM.nextInt(sourceLength)));
		}
		return sourceBuilder.substring(sourceLength);
	}
}
