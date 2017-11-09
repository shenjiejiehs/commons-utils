package com.huoli.utils;

import java.io.ByteArrayInputStream;
import java.io.ByteArrayOutputStream;
import java.io.IOException;
import java.security.Key;
import java.util.zip.ZipEntry;
import java.util.zip.ZipInputStream;
import java.util.zip.ZipOutputStream;

import javax.crypto.Cipher;
import javax.crypto.KeyGenerator;
import javax.crypto.SecretKey;
import javax.crypto.SecretKeyFactory;
import javax.crypto.spec.DESKeySpec;

import org.apache.commons.codec.binary.Base64;
import org.apache.commons.codec.binary.Hex;

/**
 * 描述：加密帮助对象<br>
 * 版权：Copyright (c) 2011-2015<br>
 * 公司：北京活力天汇<br>
 * 作者：罗良杰<br>
 * 版本：1.0<br>
 * 创建日期：2015年3月16日<br>
 */
public class DesUtil {
	/**
	 * 密钥算法
	 */
	private static final String KEY_ALGORITHM = "DES";

	/**
	 * 加密算法/工作模式/填充方式
	 */
	private static final String DEFAULT_CIPHER_ALGORITHM = "DES/ECB/PKCS5Padding";

	/**
	 * 默认密钥
	 */
	public static final String DEFAULT_KEY = "01313B08E520C42F";
	
	/**
	 * 默认字符集
	 */
	private static final String DEFAULT_CHARSET = "UTF-8";

	/**
	 * 初始化密钥
	 * @return byte[] 密钥
	 */
	public static byte[] initSecretKey() throws Exception {
		// 返回生成指定算法的秘密密钥的 KeyGenerator 对象
		KeyGenerator kg = KeyGenerator.getInstance(KEY_ALGORITHM);
		// 初始化此密钥生成器，使其具有确定的密钥大小
		kg.init(56);
		// 生成一个密钥
		SecretKey secretKey = kg.generateKey();
		return secretKey.getEncoded();
	}

	/**
	 * 转换密钥
	 * @param key 二进制密钥
	 * @return Key 密钥
	 */
	public static Key toKey(byte[] key) throws Exception {
		// 实例化DES密钥规则
		DESKeySpec dks = new DESKeySpec(key);
		// 实例化密钥工厂
		SecretKeyFactory skf = SecretKeyFactory.getInstance(KEY_ALGORITHM);
		// 生成密钥
		SecretKey secretKey = skf.generateSecret(dks);
		return secretKey;
	}

	/**
	 * 加密
	 * @param data 待加密数据
	 * @return 加密后base64转码
	 * @throws Exception
	 */
	public static String encrypt(String data) throws Exception {
		return encrypt(data, DEFAULT_CHARSET);
	}
	
	/**
	 * 加密
	 * @param data 待加密数据
	 * @return 加密后base64转码
	 * @throws Exception
	 */
	public static String encrypt(String data, String charset) throws Exception {
		byte[] encrypts = encrypt(data.getBytes(charset), Hex.decodeHex(DEFAULT_KEY.toCharArray()));
		return Base64.encodeBase64String(encrypts);
	}

	/**
	 * 压缩并加密数据
	 * @param data 待加密数据
	 * @return 加密后base64转码
	 * @throws Exception
	 */
	public static String compressEncrypt(String data) throws Exception {
		byte[] compress = compress(data);
		byte[] encrypts = encrypt(compress, Hex.decodeHex(DEFAULT_KEY.toCharArray()));
		return Base64.encodeBase64String(encrypts);
	}

	/**
	 * 加密
	 * @param data 待加密数据
	 * @param key 密钥
	 * @return byte[] 加密数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, Key key) throws Exception {
		return encrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
	}

	/**
	 * 加密
	 * @param data 待加密数据
	 * @param key 二进制密钥
	 * @return byte[] 加密数据
	 * @throws Exception
	 */
	public static byte[] encrypt(byte[] data, byte[] key) throws Exception {
		// 还原密钥
		Key k = toKey(key);
		return encrypt(data, k, DEFAULT_CIPHER_ALGORITHM);
	}

	/**
	 * 加密
	 * @param data 待加密数据
	 * @param key 密钥
	 * @param cipherAlgorithm 加密算法/工作模式/填充方式
	 */
	public static byte[] encrypt(byte[] data, Key key, String cipherAlgorithm) throws Exception {
		// 实例化
		Cipher cipher = Cipher.getInstance(cipherAlgorithm);
		// 使用密钥初始化，设置为加密模式
		cipher.init(Cipher.ENCRYPT_MODE, key);
		// 执行操作
		return cipher.doFinal(data);
	}

	/**
	 * 解密
	 * @param data 待解密数据 base64后的数据
	 * @return 解密数据
	 * @throws Exception
	 */
	public static String decrypt(String data) throws Exception {
		return decrypt(data, DEFAULT_CHARSET);
	}
	
	/**
	 * 解密
	 * @param data 待解密数据 base64后的数据
	 * @return 解密数据
	 * @throws Exception
	 */
	public static String decrypt(String data, String charset) throws Exception {
		byte[] datas = Base64.decodeBase64(data);
		byte[] decrypts = decrypt(datas, Hex.decodeHex(DEFAULT_KEY.toCharArray()));
		return new String(decrypts, charset);
	}

	/**
	 * 解密并解压
	 * @param data 待解密数据 base64后的数据
	 * @return 解密数据
	 * @throws Exception
	 */
	public static String decryptDecompress(String data) throws Exception {
		byte[] datas = Base64.decodeBase64(data);
		byte[] decrypts = decrypt(datas, Hex.decodeHex(DEFAULT_KEY.toCharArray()));
		return decompress(decrypts);
	}

	/**
	 * 解密
	 * @param data 待解密数据
	 * @param key 二进制密钥
	 * @return byte[] 解密数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, byte[] key) throws Exception {
		Key k = toKey(key);
		return decrypt(data, k, DEFAULT_CIPHER_ALGORITHM);
	}

	/**
	 * 解密
	 * @param data 待解密数据
	 * @param key 密钥
	 * @return byte[] 解密数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, Key key) throws Exception {
		return decrypt(data, key, DEFAULT_CIPHER_ALGORITHM);
	}

	/**
	 * 解密
	 * @param data 待解密数据
	 * @param key 密钥
	 * @param cipherAlgorithm 加密算法/工作模式/填充方式
	 * @return byte[] 解密数据
	 * @throws Exception
	 */
	public static byte[] decrypt(byte[] data, Key key, String cipherAlgorithm) throws Exception {
		// 实例化
		Cipher cipher = Cipher.getInstance(cipherAlgorithm);
		// 使用密钥初始化，设置为解密模式
		cipher.init(Cipher.DECRYPT_MODE, key);
		// 执行操作
		return cipher.doFinal(data);
	}

	/**
	 * 压缩字符串
	 * @param str 字符串
	 * @return
	 */
	public static final byte[] compress(String str) {
		if (str == null) {
			return null;
		}
		ByteArrayOutputStream byteArrayOutputStream = null;
		ZipOutputStream zipOutputStream = null;
		byte[] arrayOfByte;
		try {
			byteArrayOutputStream = new ByteArrayOutputStream();
			zipOutputStream = new ZipOutputStream(byteArrayOutputStream);
			zipOutputStream.putNextEntry(new ZipEntry("0"));
			zipOutputStream.write(str.getBytes());
			zipOutputStream.closeEntry();
			arrayOfByte = byteArrayOutputStream.toByteArray();
		} catch (IOException localIOException5) {
			arrayOfByte = null;
		} finally {
			if (zipOutputStream != null) {
				try {
					zipOutputStream.close();
				} catch (IOException localIOException6) {
				}
			}
			if (byteArrayOutputStream != null) {
				try {
					byteArrayOutputStream.close();
				} catch (IOException localIOException7) {
				}
			}
		}
		return arrayOfByte;
	}

	/**
	 * 解压数据
	 * @param paramArrayOfByte
	 * @return
	 */
	public static final String decompress(byte[] paramArrayOfByte) {
		if (paramArrayOfByte == null) {
			return null;
		}
		ByteArrayOutputStream byteArrayOutputStream = null;
		ByteArrayInputStream byteArrayInputStream = null;
		ZipInputStream zipInputStream = null;
		String str;
		try {
			byteArrayOutputStream = new ByteArrayOutputStream();
			byteArrayInputStream = new ByteArrayInputStream(paramArrayOfByte);
			zipInputStream = new ZipInputStream(byteArrayInputStream);
			zipInputStream.getNextEntry();
			byte[] arrayOfByte = new byte[1024];
			int i = -1;
			while ((i = zipInputStream.read(arrayOfByte)) != -1)
				byteArrayOutputStream.write(arrayOfByte, 0, i);
			str = byteArrayOutputStream.toString();
		} catch (IOException localIOException7) {
			str = null;
		} finally {
			if (zipInputStream != null) {
				try {
					zipInputStream.close();
				} catch (IOException localIOException8) {
				}
			}
			if (byteArrayInputStream != null) {
				try {
					byteArrayInputStream.close();
				} catch (IOException localIOException9) {
				}
			}
			if (byteArrayOutputStream != null) {
				try {
					byteArrayOutputStream.close();
				} catch (IOException localIOException10) {
				}
			}
		}
		return str;
	}
}
