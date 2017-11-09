package com.huoli.utils;

import java.io.IOException;
import java.text.DateFormat;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.List;

import org.apache.commons.lang3.StringUtils;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.databind.DeserializationFeature;
import com.fasterxml.jackson.databind.ObjectMapper;
import com.fasterxml.jackson.databind.SerializationFeature;
import com.fasterxml.jackson.databind.type.CollectionLikeType;
import com.fasterxml.jackson.annotation.JsonInclude;
import com.fasterxml.jackson.core.JsonParser.Feature;

/**
 * 描述：JSON功能绑定类<br>
 * 版权：Copyright (c) 2011<br>
 * 公司：北京活力天汇<br>
 * 作者：罗良杰<br>
 * 版本：1.0<br>
 * 创建日期：2014-09-01<br>
 */
public class JsonBinder {
	private static Logger logger = LoggerFactory.getLogger(JsonBinder.class);

	private ObjectMapper mapper;

	public static JsonBinder NON_NULL = buildNonNullBinder();

	public static JsonBinder NORMAL = buildNormalBinder();

	public static JsonBinder NON_DEFAULT = buildNonDefaultBinder();
	
	public static JsonBinder NON_NULL_HHMMSS = buildNonNullHHMMSSBinder();

	public static JsonBinder NORMAL_HHMMSS = buildNormalHHMMSSBinder();

	public static JsonBinder NON_DEFAULT_HHMMSS = buildNonDefaultHHMMSSBinder();

	public JsonBinder(JsonInclude.Include inclusion) {
		mapper = new ObjectMapper();
		// 设置输出包含的属性
		mapper.setSerializationInclusion(inclusion);
		// 设置输入时忽略JSON字符串中存在而Java对象实际没有的属性
		mapper.configure(Feature.ALLOW_UNQUOTED_FIELD_NAMES, true);
		mapper.configure(DeserializationFeature.FAIL_ON_UNKNOWN_PROPERTIES, false);
		mapper.configure(SerializationFeature.WRITE_DATES_AS_TIMESTAMPS, false);

		// 解析器支持解析单引号
		mapper.configure(Feature.ALLOW_SINGLE_QUOTES, true);
		// 解析器支持解析结束符
		mapper.configure(Feature.ALLOW_UNQUOTED_CONTROL_CHARS, true);
		// 设置当前json格式日期格式
		setDateFormat(DateTimeUtil.YYYYMMDDHHmm);
	}

	/**
	 * 创建输出全部属性到Json字符串的Binder.
	 */
	public static JsonBinder buildNormalBinder() {
		return new JsonBinder(JsonInclude.Include.ALWAYS);
	}
	
	/**
	 * 创建输出全部属性到Json字符串的Binder.
	 */
	public static JsonBinder buildNormalHHMMSSBinder() {
		JsonBinder json = buildNormalBinder();
		json.setDateFormat(DateTimeUtil.YYYYMMDDHHmmss);
		return json;
	}

	/**
	 * 创建只输出非空属性到Json字符串的Binder.
	 */
	public static JsonBinder buildNonNullBinder() {
		return new JsonBinder(JsonInclude.Include.NON_NULL);
	}
	
	/**
	 * 创建只输出非空属性到Json字符串的Binder.
	 */
	public static JsonBinder buildNonNullHHMMSSBinder() {
		JsonBinder json = buildNonNullBinder();
		json.setDateFormat(DateTimeUtil.YYYYMMDDHHmmss);
		return json;
	}

	/**
	 * 创建只输出初始值被改变的属性到Json字符串的Binder.
	 */
	public static JsonBinder buildNonDefaultBinder() {
		return new JsonBinder(JsonInclude.Include.NON_DEFAULT);
	}
	
	/**
	 * 创建只输出非空属性到Json字符串的Binder.
	 */
	public static JsonBinder buildNonDefaultHHMMSSBinder() {
		JsonBinder json = buildNonDefaultBinder();
		json.setDateFormat(DateTimeUtil.YYYYMMDDHHmmss);
		return json;
	}

	/**
	 * json转换对象
	 * @param jsonString json数据
	 * @param clazz 需要转换对象
	 * @return
	 */
	public <T> T fromJson(String jsonString, Class<T> clazz) {
		if (StringUtils.isEmpty(jsonString)) {
			return null;
		}
		try {
			return mapper.readValue(jsonString, clazz);
		} catch (IOException e) {
			logger.warn("parse json string error:" + jsonString, e);
			return null;
		}
	}

	/**
	 * json转换对象
	 * @param obj 对象数据
	 * @param clazz 需要转换对象
	 * @return
	 */
	public <T> T fromObjJson(Object obj, Class<T> clazz) {
		if (obj == null) {
			return null;
		}
		String jsonString = null;
		if (obj instanceof String) {
			jsonString = obj.toString();
		} else {
			jsonString = toJson(obj);
		}
		return fromJson(jsonString, clazz);
	}

	/**
	 * json转换集合对象
	 * @param obj 对象数据
	 * @param clazz 需要转换集合子对象
	 * @return
	 */
	public <T> List<T> fromObjListJson(Object obj, Class<T> clazz) {
		if (obj == null) {
			return null;
		}
		String jsonString = null;
		if (obj instanceof String) {
			jsonString = obj.toString();
		} else {
			jsonString = toJson(obj);
		}
		return fromListJson(jsonString, clazz);
	}

	/**
	 * json转换集合对象
	 * @param jsonString json数据
	 * @param clazz 需要转换集合子对象
	 * @return
	 */
	public <T> List<T> fromListJson(String jsonString, Class<T> clazz) {
		if (StringUtils.isEmpty(jsonString)) {
			return null;
		}

		CollectionLikeType array = mapper.getTypeFactory().constructCollectionType(ArrayList.class, clazz);
		try {
			return mapper.readValue(jsonString, array);
		} catch (IOException e) {
			logger.warn("parse json string error:" + jsonString, e);
			return null;
		}
	}

	/**
	 * 返回json字符
	 * @param object 需要转换对象
	 */
	public String toJson(Object object) {
		try {
			return mapper.writeValueAsString(object);
		} catch (IOException e) {
			logger.warn("write to json string error:" + object, e);
			return null;
		}
	}

	/**
	 * 返回json字符(带换行)
	 * @param object 需要转换对象
	 */
	public String toLineJson(Object object) {
		try {
			return mapper.writerWithDefaultPrettyPrinter().writeValueAsString(object);
		} catch (IOException e) {
			logger.warn("write to json string error:" + object, e);
			return null;
		}
	}

	/**
	 * 设置转换日期类型的format pattern,如果不设置默认打印Timestamp毫秒数.
	 */
	public void setDateFormat(String pattern) {
		if (StringUtils.isNotBlank(pattern)) {

			DateFormat df = new SimpleDateFormat(pattern);
			mapper.setDateFormat(df);
		}
	}

	/**
	 * 取出Mapper做进一步的设置或使用其他序列化API.
	 */
	public ObjectMapper getMapper() {
		return mapper;
	}
}
