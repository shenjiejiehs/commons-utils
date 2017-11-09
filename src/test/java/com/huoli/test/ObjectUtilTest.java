package com.huoli.test;

import static org.junit.Assert.*;

import org.junit.Test;

import com.huoli.utils.ObjectUtil;

public class ObjectUtilTest {

	@Test
	public void testGetString() {
		String str = ObjectUtil.getString("123");
		assertEquals(str, "123");

		str = ObjectUtil.getString(null, "456");
		assertEquals(str, "456");
	}

	@Test
	public void testGetNumber() {
		Number str = ObjectUtil.getNumber("123");
		assertEquals(str.intValue(), 123);

		str = ObjectUtil.getNumber(null, 456);
		assertEquals(str.intValue(), 456);
	}

	@Test
	public void testGetInt() {
		Integer obj = ObjectUtil.getInt("123");
		assertEquals(obj.intValue(), 123);

		obj = ObjectUtil.getInt(null, 456);
		assertEquals(obj.intValue(), 456);
	}

	@Test
	public void testGetLongObject() {
		Long obj = ObjectUtil.getLong("123");
		assertEquals(obj.intValue(), 123);

		obj = ObjectUtil.getLong(null, 456L);
		assertEquals(obj.intValue(), 456);
	}

	@Test
	public void testGetDouble() {
		Double obj = ObjectUtil.getDouble("123.1");
		assertEquals(1, obj.doubleValue(), 123.1);

		obj = ObjectUtil.getDouble(null, 456.1);
		assertEquals(1, obj.doubleValue(), 456.1);
	}

	@Test
	public void testGetBoolean() {
		boolean b = ObjectUtil.getBoolean("true");
		assertTrue(b);

		b = ObjectUtil.getBoolean(null, false);
		assertFalse(b);
	}

	@Test
	public void testGetListValue() {
		Object o = ObjectUtil.getListValue(new String[] { "123", "456" }, 0);
		assertEquals(o, "123");
	}
}
