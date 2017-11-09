package com.huoli.test;

import java.security.Key;

import org.apache.commons.codec.binary.Hex;
import org.junit.Assert;
import org.junit.Test;

import com.huoli.utils.DesUtil;
import com.huoli.utils.HexUtil;
import com.huoli.utils.RandomStringGenerator;

/**
 * 描述：Des加密帮助<br>
 * 版权：Copyright (c) 2011-2015<br>
 * 公司：北京活力天汇<br>
 * 作者：罗良杰<br>
 * 版本：1.0<br>
 * 创建日期：2015年3月16日<br>
 */
public class DesUtilTest {
	@Test
	public void testEncrypt() throws Exception {
		String data = RandomStringGenerator.DIGITALS + RandomStringGenerator.LOWER_LETTERS
				+ RandomStringGenerator.UPPER_LETTERS;

		for (char ch : data.toCharArray()) {
			System.out.println(ch + "==" + (int) ch + "==" + HexUtil.numberToHex(ch));
		}
		// String data = "28";
		// String test = DesUtil.encrypt(data);
		// System.out.println(test);
	}

	@Test
	public void test() throws Exception {
		String data = "{\"id\":123, \"name\":\"咖啡订单\", \"paytitle\":\"咖啡订单\", \"desc\":\"支付咖啡订单\", \"price\":123}";

		// 生成加密密钥
		byte[] keys = DesUtil.initSecretKey();
		Assert.assertNotNull(keys);

		// 密钥转换成str
		Assert.assertNotNull(Hex.encodeHex(keys));
		// System.out.println(Hex.encodeHex(keys));

		String keyStr = DesUtil.DEFAULT_KEY;

		// 默认密钥还原成密钥对象
		Key key = DesUtil.toKey(Hex.decodeHex(keyStr.toCharArray()));
		Assert.assertNotNull(key);

		// 默认加密解密
		String encryptStr = DesUtil.encrypt(data);
		Assert.assertNotNull(encryptStr);
		String decryptStr = DesUtil.decrypt(encryptStr);
		Assert.assertEquals(data, decryptStr);

		// 加密解密
		byte[] encrypts = DesUtil.encrypt(data.getBytes(), keys);
		Assert.assertNotNull(encrypts);
		byte[] decrypts = DesUtil.decrypt(encrypts, keys);
		Assert.assertEquals(data, new String(decrypts));

		// 加密解密
		encrypts = DesUtil.encrypt(data.getBytes(), key);
		Assert.assertNotNull(encrypts);
		decrypts = DesUtil.decrypt(encrypts, key);
		Assert.assertEquals(data, new String(decrypts));

		// 加密解密
		encrypts = DesUtil.encrypt(data.getBytes(), key, "DES/ECB/PKCS5Padding");
		Assert.assertNotNull(encrypts);
		decrypts = DesUtil.decrypt(encrypts, key, "DES/ECB/PKCS5Padding");
		Assert.assertEquals(data, new String(decrypts));
	}

	/**
	 * 测试压缩加密解密解压
	 */
	@Test
	public void testCompress() throws Exception {
		String data = "{\"flight\":{\"model\":\"738\",\"depDate\":\"2016-07-09\",\"arrtime\":\"2016-07-09 "
				+ "08:45\",\"deptime\":\"2016-07-09 06:50\",\"arr\":\"SHA\",\"no\":\"HO1252\",\"fuelTax\""
				+ ":30.0,\"surcharge\":50.0,\"type\":\"OB\",\"dep\":\"PEK\",\"companyCode\":\"HO\"},"
				+ "\"depDate\":\"2016-07-09\",\"arr\":\"SHS_C\",\"tripType\":\"RT\",\"type\":\"domestic\""
				+ ",\"returnDate\":\"2016-07-09\",\"dep\":\"BJS_C\",\"searchCabinCode\":\"ALL\"}";

		String compress = DesUtil.compressEncrypt(data);
		Assert.assertNotNull(compress);
		String decompress = DesUtil.decryptDecompress(compress);
		Assert.assertEquals(decompress, data);
	}
}
