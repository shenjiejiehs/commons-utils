package com.huoli.test;

import static org.junit.Assert.assertEquals;

import java.util.ArrayList;
import java.util.List;
import java.util.Map;

import org.junit.Test;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.fasterxml.jackson.core.type.TypeReference;
import com.huoli.utils.JsonUtil;
import com.huoli.utils.geo.Position;

/**
 * Json工具类 <br>
 * 版权: Copyright (c) 2011-2016<br>
 * 公司: 北京活力天汇<br>
 * 
 * @author: 童凡<br>
 * @date: Feb 17, 2016<br>
 */
public class JsonUtilTest {
	private static Logger logger = LoggerFactory.getLogger(JsonUtilTest.class);

	@Test
	public void test1() {
		List<Position> positionList = new ArrayList<Position>();
		positionList.add(new Position(1, 1));
		String jsonString = JsonUtil.toString(positionList);
		logger.debug("jsonString:{}", jsonString);

		List<Position> positionListConvert = JsonUtil.toObject(jsonString, new TypeReference<List<Position>>() {
		});
		logger.debug("positionListConvert:{}", positionListConvert);
		assertEquals(positionList, positionListConvert);
	}

	@Test
	public void test2() {
		Position position = new Position(1, 1);
		String jsonString = JsonUtil.toString(position);
		logger.debug("jsonString:{}", jsonString);

		Position positionConvert = JsonUtil.toObject(jsonString, Position.class);
		logger.debug("positionConvert:{}", positionConvert);
		assertEquals(position, positionConvert);
	}

	@Test
	public void test3() {
		String content = "[{\"a\":\"1\"},{\"b\":\"2\"}]";
		{
			List<Map<String, String>> list = JsonUtil.toObject(content, new TypeReference<List<Map<String, String>>>() {
			});
			logger.debug("list:{}", JsonUtil.toString(list));
			assertEquals(2, list.size());
		}
		{
			List<Map<String, Integer>> list = JsonUtil.toObject(content,
					new TypeReference<List<Map<String, Integer>>>() {
					});
			logger.debug("list:{}", JsonUtil.toString(list));
			assertEquals(2, list.size());
		}
		{
			List<Map<String, Object>> list = JsonUtil.toObject(content, new TypeReference<List<Map<String, Object>>>() {
			});
			logger.debug("list:{}", JsonUtil.toString(list));
			assertEquals(2, list.size());
		}

	}

	@Test
	public void test4() {
		Position position = new Position(1, 1);
		String jsonString = JsonUtil.toString(position);

		Position p = JsonUtil.toObjectByObj(position, Position.class);
		logger.debug("jsonString:{}", JsonUtil.toString(p));
		Position p1 = JsonUtil.toObjectByObj(jsonString, Position.class);
		logger.debug("jsonString:{}", JsonUtil.toString(p1));

		assertEquals(p, p1);

		List<Position> positionList = new ArrayList<Position>();
		positionList.add(new Position(1, 1));
		String jsonlist = JsonUtil.toString(positionList);

		List<Position> plist = JsonUtil.toObjectListByObj(positionList, Position.class);
		logger.debug("jsonListString:{}", JsonUtil.toString(plist));
		List<Position> plist1 = JsonUtil.toObjectListByObj(jsonlist, Position.class);
		logger.debug("jsonListString:{}", JsonUtil.toString(plist1));

		assertEquals(plist, plist1);
	}

	@Test
	public void test5() {
		String json = "{\"name\":\"zitong\", \"age\":26}";
		Map<String, String> map = JsonUtil.toStringMap(json);
		
		assertEquals(map.get("age"), "26");
	}
}
