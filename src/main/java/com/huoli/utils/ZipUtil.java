package com.huoli.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.io.InputStream;
import java.util.zip.GZIPInputStream;
import java.util.zip.GZIPOutputStream;

/**
 * 描述：将一个字符串按照zip方式压缩和解压缩<br>
 * 版权：Copyright (c) 2011-2015<br>
 * 公司：北京活力天汇<br>
 * 作者：罗良杰<br>
 * 版本：1.0<br>
 * 创建日期：2016年5月26日<br>
 */
public class ZipUtil {
	/**
	 * 压缩
	 * @param str 字符
	 * @param charset 字符集
	 * @return
	 * @throws IOException
	 */
	public static String compress(String str, String charset) throws IOException {
		if (str == null || str.length() == 0) {
			return str;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPOutputStream gzip = new GZIPOutputStream(out);
		gzip.write(str.getBytes());
		gzip.close();
		return out.toString(charset);
	}
	
	/**
	 * 解压
	 */
	public static String uncompress(InputStream input) throws IOException {
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		GZIPInputStream gunzip = new GZIPInputStream(input);
		byte[] buffer = new byte[256];
		int n;
		while ((n = gunzip.read(buffer)) >= 0) {
			out.write(buffer, 0, n);
		}
		// toString()使用平台默认编码，也可以显式的指定如toString("GBK")
		return out.toString();
	}
	
	/**
	 * 压缩
	 * @param str
	 * @param charset
	 * @return
	 * @throws IOException
	 */
	public static String uncompress(String str, String charset) throws IOException {
		if (str == null || str.length() == 0) {
			return str;
		}
		ByteArrayOutputStream out = new ByteArrayOutputStream();
		ByteArrayInputStream in = new ByteArrayInputStream(str.getBytes(charset));
		GZIPInputStream gunzip = new GZIPInputStream(in);
		byte[] buffer = new byte[256];
		int n;
		while ((n = gunzip.read(buffer)) >= 0) {
			out.write(buffer, 0, n);
		}
		// toString()使用平台默认编码，也可以显式的指定如toString("GBK")
		return out.toString();
	}
}