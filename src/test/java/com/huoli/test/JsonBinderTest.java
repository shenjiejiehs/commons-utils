package com.huoli.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.Date;
import java.util.List;
import java.util.Map;

import org.junit.Assert;
import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.huoli.utils.JsonBinder;
import com.huoli.utils.JsonUtil;
import com.huoli.utils.geo.Position;

public class JsonBinderTest {
	private static Logger logger = LoggerFactory.getLogger(JsonBinderTest.class);
	
	@Test
	@SuppressWarnings("unchecked")
	public void test() throws Exception {
		JsonBinder binder = JsonBinder.NON_NULL;

		Map<String, String> map = binder.fromJson("{\"123\":\"32423\"}", Map.class);
		Assert.assertEquals("32423", map.get("123"));
		
		logger.debug(binder.toJson(map));

		List<String> list = binder.fromListJson("[\"123\",\"1234\"]", String.class);
		Assert.assertEquals(2, list.size());
		Assert.assertEquals("1234", list.get(1));
		
		TestData test = binder.fromJson("{\"name\":\"123123\", \"time\":\"2016-09-07 19:00\"}", TestData.class);
		Assert.assertNotNull(test);
	}
	
	@Test
	public void test4() {
		JsonBinder binder = JsonBinder.NON_NULL;
		
		Position position = new Position(1, 1);
		String jsonString = binder.toJson(position);

		Position p = binder.fromObjJson(position, Position.class);
		logger.debug("jsonString:{}", binder.toJson(p));
		Position p1 = binder.fromObjJson(jsonString, Position.class);
		logger.debug("jsonString:{}", binder.toJson(p1));

		assertEquals(p, p1);

		List<Position> positionList = new ArrayList<Position>();
		positionList.add(new Position(1, 1));
		String jsonlist = binder.toJson(positionList);

		List<Position> plist = binder.fromObjListJson(positionList, Position.class);
		logger.debug("jsonListString:{}", JsonUtil.toString(plist));
		List<Position> plist1 = binder.fromObjListJson(jsonlist, Position.class);
		logger.debug("jsonListString:{}", JsonUtil.toString(plist1));

		assertEquals(plist, plist1);
	}

	
	public static class TestData {
		private String name;
		
		private Date time;

		public String getName() {
			return name;
		}

		public void setName(String name) {
			this.name = name;
		}

		public Date getTime() {
			return time;
		}

		public void setTime(Date time) {
			this.time = time;
		}
	}
}
