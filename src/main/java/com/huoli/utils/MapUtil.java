package com.huoli.utils;

import java.util.Arrays;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 描述：map帮助对象<br>
 * 版权：Copyright (c) 2011-2015<br>
 * 公司：北京活力天汇<br>
 * 作者：罗良杰<br>
 * 版本：1.0<br>
 * 创建日期：2016年9月9日<br>
 */

@SuppressWarnings("rawtypes")
public class MapUtil {
	private static boolean verifyIsNull(Map map) {
		return map == null;
	}

	/**
	 * 获取map对象数据
	 * @param key 支持test.key 或 test.key[0]
	 * @return
	 */
	public static Object get(final Map map, final Object keyObj) {
		if (verifyIsNull(map)) {
			return null;
		}
		final String key = keyObj.toString();
		if (StringUtil.contains(key, ".")) {
			List<String> keys = Arrays.asList(StringUtil.split(key, "."));
			Iterator<String> iterator = keys.iterator();
			Map keyValue = map;
			while (iterator.hasNext()) {
				String k = iterator.next();
				Object value = getObject(keyValue, k);
				if (iterator.hasNext()) {
					if (value != null && value instanceof Map) {
						keyValue = (Map) value;
					} else {
						return null;
					}
				} else {
					return value;
				}
			}
		} else {
			return getObject(map, keyObj);
		}
		return null;
	}

	/**
	 * 获取map具体key-value
	 * @param map map对象
	 * @param key 支持key 或 key[0]
	 * @return
	 */
	public static Object getObject(final Map map, final Object keyObj) {
		Object value = null;

		String key = keyObj.toString();
		// 判断当前key为数组
		if (StringUtil.contains(key, "[") && StringUtil.endsWith(key, "]")) {
			// 去除数组格式后key
			String k = StringUtil.substringBeforeLast(key, "[");
			// 获取数组位数
			String num = StringUtil.substringBeforeLast(StringUtil.substringAfterLast(key, "["), "]");

			value = map.get(k);
			// 判断数组格式是否正确
			if (value == null || StringUtil.isNumber(num) == false) {
				return null;
			}
			int size = Integer.parseInt(num);

			return ObjectUtil.getListValue(value, size);
		} else {
			value = map.get(keyObj);
		}
		return value;
	}

	/**
	 * 获取字符串
	 */
	public static String getString(final Map map, final Object key) {
		Object o = get(map, key);
		return ObjectUtil.getString(o);
	}

	/**
	 * 获取字符串
	 */
	public static String getString(final Map map, final Object key, final String defaultValue) {
		Object o = get(map, key);
		return ObjectUtil.getString(o, defaultValue);
	}

	/**
	 * 获取数字
	 */
	public static Number getNumber(final Map map, final Object key) {
		Object answer = get(map, key);
		return ObjectUtil.getNumber(answer);
	}

	/**
	 * 获取数字
	 */
	public static Number getNumber(final Map map, final Object key, final Number defaultValue) {
		Object obj = get(map, key);
		return ObjectUtil.getNumber(obj, defaultValue);
	}

	/**
	 * 获取数字
	 */
	public static Integer getInt(final Map map, final Object key) {
		Object obj = get(map, key);
		return ObjectUtil.getInt(obj);
	}

	/**
	 * 获取数字
	 */
	public static Integer getInt(final Map map, final Object key, final Integer defaultValue) {
		Object obj = get(map, key);
		return ObjectUtil.getInt(obj, defaultValue);
	}

	/**
	 * 获取数字
	 */
	public static Long getLong(final Map map, final Object key) {
		Object obj = get(map, key);
		return ObjectUtil.getLong(obj);
	}

	/**
	 * 获取数字
	 */
	public static Long getLong(final Map map, final Object key, final Long defaultValue) {
		Object obj = get(map, key);
		return ObjectUtil.getLong(obj, defaultValue);
	}

	/**
	 * 获取浮点数
	 */
	public static Double getDouble(final Map map, final Object key) {
		Object obj = get(map, key);
		return ObjectUtil.getDouble(obj);
	}

	/**
	 * 获取浮点数
	 */
	public static Double getDouble(final Map map, final Object key, final Double defaultValue) {
		Object obj = get(map, key);
		return ObjectUtil.getDouble(obj, defaultValue);
	}

	/**
	 * 获取布尔值
	 */
	public static boolean getBoolean(final Map map, final Object key) {
		Object obj = get(map, key);
		return ObjectUtil.getBoolean(obj);
	}

	/**
	 * 获取布尔值
	 */
	public static Boolean getBoolean(final Map map, final Object key, final Boolean defaultValue) {
		Object obj = get(map, key);
		return ObjectUtil.getBoolean(obj, defaultValue);
	}

	/**
	 * 获取map
	 */
	public static Map getMap(final Map map, final Object key) {
		Object answer = get(map, key);
		if (answer != null && answer instanceof Map) {
			return (Map) answer;
		}
		return null;
	}

	/**
	 * 获取list
	 */
	public static List<?> getList(final Map map, final Object key) {
		Object answer = get(map, key);
		if (answer != null && answer instanceof List) {
			return (List<?>) answer;
		}
		return null;
	}
}