package com.huoli.test;

import java.util.Arrays;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.huoli.utils.JsonUtil;
import com.huoli.utils.MapUtil;

public class MapUtilTest {
	@Test
	public void test() {
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("int", "123");
		map.put("long", "123");
		map.put("boolean", "true");
		map.put("string", "123");
		map.put("double", "123.1");

		Assert.assertEquals("test", MapUtil.getInt(map, "int"), new Integer(123));
		Assert.assertEquals("test", MapUtil.getInt(map, "double"), new Integer(123));
		Assert.assertEquals("test", MapUtil.getInt(map, "string"), new Integer(123));
		Assert.assertNull(MapUtil.getInt(map, "test"));
		Assert.assertNull(MapUtil.getInt(map, "boolean"));

		Assert.assertEquals("test", MapUtil.getLong(map, "int"), new Long(123));
		Assert.assertEquals("test", MapUtil.getLong(map, "double"), new Long(123));
		Assert.assertEquals("test", MapUtil.getLong(map, "string"), new Long(123));
		Assert.assertNull(MapUtil.getLong(map, "test"));
		Assert.assertNull(MapUtil.getLong(map, "boolean"));

		Assert.assertEquals("test", MapUtil.getString(map, "int"), "123");
		Assert.assertEquals("test", MapUtil.getString(map, "boolean"), "true");
		Assert.assertEquals("test", MapUtil.getString(map, "string"), "123");
		Assert.assertNull(MapUtil.getString(map, "test"));

		Assert.assertEquals("test", MapUtil.getBoolean(map, "boolean"), true);
		Assert.assertEquals("test", MapUtil.getBoolean(map, "int"), false);
		Assert.assertEquals("test", MapUtil.getBoolean(map, "test"), false);

		Assert.assertEquals("test", MapUtil.getDouble(map, "double"), new Double(123.1));
		Assert.assertEquals("test", MapUtil.getDouble(map, "string"), new Double(123));
		Assert.assertEquals("test", MapUtil.getDouble(map, "int"), new Double(123));
		Assert.assertNull(MapUtil.getDouble(map, "test"));
		Assert.assertNull(MapUtil.getDouble(map, "boolean"));
	}

	@SuppressWarnings("rawtypes")
	@Test
	public void testGetKey() {
		String[] strs = new String[] { "1", "2" };
		Map<String, Object> map = new HashMap<String, Object>();
		map.put("stringlist", Arrays.asList(strs));
		map.put("string", "123");
		map.put("strings", strs);
		Assert.assertEquals("MapUtil", MapUtil.get(map, "string"), "123");
		Assert.assertEquals("MapUtil", MapUtil.get(map, "stringlist[1]"), "2");
		Assert.assertEquals("MapUtil", MapUtil.get(map, "stringlist[0]"), "1");
		Assert.assertEquals("MapUtil", MapUtil.get(map, "strings[1]"), "2");
		Assert.assertEquals("MapUtil", MapUtil.get(map, "strings[0]"), "1");

		String json = "{'test':[{'name':'1','code':1}, {'name':'2','code':2}], 'code':1, 'data':{'code':1}}";
		map = JsonUtil.toMap(json);

		Assert.assertEquals("MapUtil", MapUtil.get(map, "test[1].code"), 2);
		Assert.assertEquals("MapUtil", MapUtil.get(map, "code"), 1);
		Assert.assertEquals("MapUtil", MapUtil.get(map, "data.code"), 1);

		// Assert.assertEquals("MapUtil", MapUtil.getMap(map, test[0]), 1);

		Map data = MapUtil.getMap(map, "test[0]");
		Assert.assertNotNull(data);

		List<?> list = MapUtil.getList(map, "test");
		Assert.assertEquals("MapUtil", list.size(), 2);
	}
}
