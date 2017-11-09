package com.huoli.utils;

import java.lang.reflect.Array;
import java.text.NumberFormat;
import java.text.ParseException;
import java.util.Collection;
import java.util.Enumeration;
import java.util.Iterator;
import java.util.List;
import java.util.Map;

/**
 * 描述：object对象转换其他数据帮助对象<br>
 * 版权：Copyright (c) 2011-2015<br>
 * 公司：北京活力天汇<br>
 * 作者：罗良杰<br>
 * 版本：1.0<br>
 * 创建日期：2016年12月5日<br>
 */
public class ObjectUtil {
	/**
	 * 判断是否为空
	 */
	public static boolean isNull(Object param) {
		return param == null || StringUtil.isNull(param.toString());
	}

	/**
	 * 验证多参数
	 * @param objs
	 * @return
	 */
	public static boolean isNulls(Object... objs) {
		for (Object o : objs) {
			if (isNull(o)) {
				return true;
			}
		}
		return false;
	}

	/**
	 * 获取字符串
	 */
	public static String getString(Object obj) {
		if (obj == null) {
			return null;
		}
		return obj.toString();
	}

	/**
	 * 获取字符串
	 */
	public static String getString(Object obj, final String defaultValue) {
		String str = getString(obj);
		if (str == null) {
			str = defaultValue;
		}
		return str;
	}

	/**
	 * 获取数字
	 */
	public static Number getNumber(Object answer) {
		if (answer != null) {
			if (answer instanceof Number) {
				return (Number) answer;

			} else if (answer instanceof String) {
				try {
					String text = (String) answer;
					return NumberFormat.getInstance().parse(text);
				} catch (ParseException e) {
				}
			}
		}
		return null;
	}

	/**
	 * 获取数字
	 */
	public static Number getNumber(Object obj, final Number defaultValue) {
		Number answer = getNumber(obj);
		if (answer == null) {
			answer = defaultValue;
		}
		return answer;
	}

	/**
	 * 获取数字
	 */
	public static Integer getInt(Object obj) {
		Number answer = getNumber(obj);
		if (answer == null) {
			return null;
		} else if (answer instanceof Integer) {
			return (Integer) answer;
		}
		return new Integer(answer.intValue());
	}

	/**
	 * 获取数字
	 */
	public static Integer getInt(Object obj, final Integer defaultValue) {
		Integer integer = getInt(obj);
		if (integer == null) {
			return defaultValue;
		}
		return integer;
	}

	/**
	 * 获取数字
	 */
	public static Long getLong(Object obj) {
		Number answer = getNumber(obj);
		if (answer == null) {
			return null;
		} else if (answer instanceof Long) {
			return (Long) answer;
		}
		return new Long(answer.longValue());
	}

	/**
	 * 获取数字
	 */
	public static Long getLong(Object obj, final Long defaultValue) {
		Long answer = getLong(obj);
		if (answer == null) {
			answer = defaultValue;
		}
		return answer;
	}

	/**
	 * 获取浮点数
	 */
	public static Double getDouble(Object obj) {
		Number answer = getNumber(obj);
		if (answer == null) {
			return null;
		} else if (answer instanceof Double) {
			return (Double) answer;
		}
		return new Double(answer.doubleValue());
	}

	/**
	 * 获取浮点数
	 */
	public static Double getDouble(Object obj, final Double defaultValue) {
		Double answer = getDouble(obj);
		if (answer == null) {
			answer = defaultValue;
		}
		return answer;
	}

	/**
	 * 获取布尔值
	 */
	public static boolean getBoolean(Object obj) {
		if (obj != null) {
			if (obj instanceof Boolean) {
				return (Boolean) obj;

			} else if (obj instanceof String) {
				return new Boolean((String) obj);
			}
		}
		return false;
	}

	/**
	 * 获取布尔值
	 */
	public static Boolean getBoolean(Object obj, final Boolean defaultValue) {
		Boolean answer = getBoolean(obj);
		if (answer == null) {
			answer = defaultValue;
		}
		return answer;
	}

	/**
	 * 获取数组具体对象
	 * @param object 数组对象
	 * @param index 游标
	 * @return
	 */
	@SuppressWarnings("rawtypes")
	public static Object getListValue(Object object, int index) {
		if (index < 0) {
			throw new IndexOutOfBoundsException("Index cannot be negative: " + index);
		}
		if (object == null) {
			throw new IllegalArgumentException("Unsupported object type: null");
		} else if (object instanceof Map) {
			Map map = (Map) object;
			Iterator iterator = map.entrySet().iterator();
			return getListValue(iterator, index);
		} else if (object instanceof List) {
			return ((List) object).get(index);
		} else if (object.getClass().isArray()) {
			return Array.get(object, index);
		} else if (object instanceof Iterator) {
			Iterator it = (Iterator) object;
			while (it.hasNext()) {
				index--;
				if (index == -1) {
					return it.next();
				} else {
					it.next();
				}
			}
			throw new IndexOutOfBoundsException("Entry does not exist: " + index);
		} else if (object instanceof Collection) {
			Iterator iterator = ((Collection) object).iterator();
			return getListValue(iterator, index);
		} else if (object instanceof Enumeration) {
			Enumeration it = (Enumeration) object;
			while (it.hasMoreElements()) {
				index--;
				if (index == -1) {
					return it.nextElement();
				} else {
					it.nextElement();
				}
			}
			throw new IndexOutOfBoundsException("Entry does not exist: " + index);
		} else {
			try {
				return Array.get(object, index);
			} catch (IllegalArgumentException ex) {
				throw new IllegalArgumentException("Unsupported object type: " + object.getClass().getName());
			}
		}
	}
}
