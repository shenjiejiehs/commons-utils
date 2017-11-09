package com.huoli.test.geo;

import org.junit.Assert;
import org.junit.Test;

import com.huoli.utils.geo.LatLonConverter;

public class LatLonConverterTest {
	@Test
	public void test1() {
		double latlng1 = LatLonConverter.convertToDecimal(37, 25, 19.222);
		Assert.assertEquals("转换小数(数字参数)", 37.42200611111111, latlng1, 1);

		double latlng2 = LatLonConverter.convertToDecimalByString("-37°25′19.222″");
		Assert.assertEquals("转换小数(字符串参数):", -37.42200611111111, latlng2, 1);

		String latlng3 = LatLonConverter.convertToSexagesimal(121.084095);
		Assert.assertEquals("转换度分秒:", "121°5′2.74200439453125″", latlng3);

		String latlng4 = LatLonConverter.convertToSexagesimal(-121.084095);
		Assert.assertEquals("转换度分秒:", "-121°5′2.74200439453125″", latlng4);
	}
}
