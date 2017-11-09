package com.huoli.utils;

import java.beans.BeanInfo;
import java.beans.Introspector;
import java.beans.PropertyDescriptor;
import java.lang.reflect.Method;
import java.util.HashMap;
import java.util.Map;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 描述：实体工具包<br>
 * 版权：Copyright (c) 2011-2015<br>
 * 公司：北京活力天汇<br>
 * 作者：罗良杰<br>
 * 版本：1.0<br>
 * 创建日期：2016年5月9日<br>
 */
public class BeanUtil {
	private static Logger logger = LoggerFactory.getLogger(BeanUtil.class);
	
	/**
	 * bean对象转换map对象
	 * @param obj 对象
	 * @param name 附加名称
	 * @return
	 */
	public static Map<String, Object> transBeanToMap(Object obj, String name) {
		if (obj == null) {
			return null;
		}
		name = StringUtil.delNull(name);
		Map<String, Object> map = new HashMap<String, Object>();
		try {
			BeanInfo beanInfo = Introspector.getBeanInfo(obj.getClass());
			PropertyDescriptor[] propertyDescriptors = beanInfo.getPropertyDescriptors();
			for (PropertyDescriptor property : propertyDescriptors) {
				String key = property.getName();

				// 过滤class属性
				if (!key.equals("class")) {
					// 得到property对应的getter方法
					Method getter = property.getReadMethod();
					Object value = getter.invoke(obj);
					map.put(name + key, value);
				}
			}
		} catch (Exception e) {
			logger.error("transBean2Map Error", e);
		}
		return map;
	}
}
