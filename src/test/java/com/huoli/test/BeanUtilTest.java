package com.huoli.test;

import java.util.Map;

import org.junit.Assert;
import org.junit.Test;

import com.huoli.utils.BeanUtil;
import com.huoli.utils.geo.Position;

public class BeanUtilTest {
	@Test
	public void testTransBeanToMap() {
		Position log = new Position();
		log.setLat(123.123);
		log.setLon(123.123);

		Map<String, Object> map = BeanUtil.transBeanToMap(log, null);

		Assert.assertEquals("转换数据为map", 123.123, map.get("lat"));

		map = BeanUtil.transBeanToMap(log, "api.");

		Assert.assertEquals("转换数据为map", 123.123, map.get("api.lat"));
	}
}
