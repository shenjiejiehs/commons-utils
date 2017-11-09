package com.huoli.utils;

import java.io.IOException;
import java.util.Locale;
import java.util.Map;
import java.util.HashMap;
import java.util.ResourceBundle;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 资源管理帮助对象 <br>
 * 版权：Copyright (c) 2011-2014<br>
 * 公司：北京活力天汇<br>
 * 作者：罗良杰<br>
 * 版本：1.0<br>
 * 创建日期：2014年9月17日<br>
 */
public class ResourcesUtil {
	private static Logger logger = LoggerFactory.getLogger(ResourcesUtil.class);

	private static Map<String, ResourceBundle> resourcesList = new HashMap<String, ResourceBundle>();

	private static final MyControl myControl = new MyControl();

	/**
	 * 加载配置文件
	 * @param name 文件名 例如wap、data等properties文件 可能出现加载不到文件异常
	 */
	public static ResourceBundle load(String filename) {
		// 检查是否存在资源信息
		ResourceBundle prb = get(filename);
		if (prb != null) {
			return prb;
		}
		// 加载资源信息，可能出现文件异常
		prb = ResourceBundle.getBundle(filename, myControl);
		if (prb != null) {
			// 放入资源信息
			resourcesList.put(filename, prb);
		}
		return prb;
	}

	/**
	 * 在tomcat的运行环境下，使用ResourceBundle.clearCache()没效果，需要增加一个Control<br>
	 * 版权：Copyright (c) 2011-2014<br>
	 * 公司：北京活力天汇<br>
	 * 作者：罗良杰<br>
	 * 版本：1.0<br>
	 * 创建日期：2014年10月23日<br>
	 */
	public static class MyControl extends ResourceBundle.Control {
		public ResourceBundle newBundle(String baseName, Locale locale, String format, ClassLoader loader,
				boolean reload) throws IllegalAccessException, InstantiationException, IOException {
			// 将reload标识位置为true
			return super.newBundle(baseName, locale, format, loader, true);
		}
	}

	/**
	 * 清理缓存数据
	 * @param name
	 */
	public static void clearCache(String name) {
		if (resourcesList.containsKey(name)) {
			resourcesList.put(name, null);
			ResourceBundle.clearCache();
			load(name);
		}
	}

	/**
	 * 获取配置文件根据文件名
	 * @param filename 文件名
	 * @return
	 */
	public static ResourceBundle get(String filename) {
		// 检查是否存在资源信息
		if (resourcesList.containsKey(filename)) {
			return resourcesList.get(filename);
		}
		return null;
	}

	/**
	 * 获取资源中对应名称的String对象
	 * @param prb 资源对象
	 * @param name 名称
	 * @return
	 */
	public static String getString(ResourceBundle prb, String name) {
		if (prb == null) {
			return StringUtil.EMPTY;
		}
		try {
			String value = prb.getString(name);
			return StringUtil.delNull(value);
		} catch (Exception e) {
			return StringUtil.EMPTY;
		}
	}
	
	/**
	 * 获取资源中对应名称的String对象
	 * @param resname 资源名
	 * @param name 名称
	 * @return
	 */
	public static String[] getStrings(String resname, String name) {
		String values = getString(resname, name);
		if (StringUtil.isEmpty(values)) {
			return null;
		} else {
			return StringUtil.split(values, ",");
		}
	}

	/**
	 * 获取资源中对应名称的String对象
	 * @param resname 资源名
	 * @param name 名称
	 * @return
	 */
	public static String getString(String resname, String name) {
		return getString(get(resname), name);
	}

	/**
	 * 获取资源中对应名称的Integer对象
	 * @param prb 资源对象
	 * @param name 名称
	 * @return |对应值
	 */
	public static Integer getInt(ResourceBundle prb, String name) {
		String value = getString(prb, name);
		return ObjectUtil.getInt(value);
	}

	/**
	 * 获取资源中对应名称的Integer对象
	 * @param resname 资源名
	 * @param name 名称
	 * @return |对应值
	 */
	public static Integer getInt(String resname, String name) {
		return getInt(get(resname), name);
	}

	/**
	 * 获取资源中对应名称的Boolean对象
	 * @param prb 资源对象
	 * @param name 名称
	 * @return |对应值
	 */
	public static Boolean getBoolean(ResourceBundle prb, String name) {
		String value = getString(prb, name);
		if (StringUtil.isEmpty(value) || !StringUtil.equals(value, "true", "false")) {
			logger.error("no find " + name + " or " + name + " not is boolean");
			return null;
		}
		return ObjectUtil.getBoolean(value);
	}

	/**
	 * 获取资源中对应名称的Boolean对象
	 * @param resname 资源名称
	 * @param name 名称
	 * @return |对应值
	 */
	public static Boolean getBoolean(String resname, String name) throws Exception {
		return getBoolean(get(resname), name);
	}
}