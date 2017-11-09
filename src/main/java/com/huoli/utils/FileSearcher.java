package com.huoli.utils;

import java.io.File;
import java.util.ArrayList;
import java.util.List;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * 文件搜索工具类 <br>
 * 版权：Copyright (c) 2011-2013<br>
 * 公司：北京活力天汇<br>
 * 作者：罗良杰<br>
 * 版本：1.0<br>
 * 创建日期：2014-9-30<br>
 */
public class FileSearcher {
	private static Logger logger = LoggerFactory.getLogger(FileSearcher.class);

	private String dir;// 需要查找的目录

	private String target;// 需要查找的文件名

	private String orgTarger;// 原始查找文件名

	private int length;// 搜索目录长度

	private List<Filer> list;// 查找到的文件名

	public static class Filer {
		public Filer(File file, String dir) {
			this.file = file;
			this.dir = dir;
		}

		private File file;

		private String dir;

		public File getFile() {
			return file;
		}

		public void setFile(File file) {
			this.file = file;
		}

		public String getDir() {
			return dir;
		}

		public void setDir(String dir) {
			this.dir = dir;
		}
	}

	public FileSearcher(String dir, String target) {
		this.dir = dir;
		target = StringUtil.replaceChars(target, '/', File.separatorChar);
		this.orgTarger = target;
		if (StringUtil.contains(target, File.separatorChar)) {
			String[] s = StringUtil.split(target, File.separatorChar);
			length = s.length - 1;
			this.target = s[length];
		} else {
			this.target = target;
		}
	}

	public List<Filer> findFiles() {
		// 判断目录是否存在
		File baseDir = new File(dir);
		if (!baseDir.exists() || !baseDir.isDirectory()) {
			logger.info("文件查找失败：" + dir + "不是一个目录！");
		} else {
			list = new ArrayList<Filer>();
			findFiles(baseDir);
		}
		return list;
	}

	private void findFiles(File dir) {
		File[] dirs = dir.listFiles();
		if (dirs == null || dirs.length == 0) {
			return;
		}
		String tempName = null;
		for (File f : dirs) {
			if (!f.isDirectory()) {
				tempName = f.getName();
				if (match(target, tempName)) {
					String parentDir = matchDir(f);
					// 匹配成功，将文件名添加到结果集
					if (StringUtil.isNotEmpty(parentDir)) {
						list.add(new Filer(f, parentDir));
					}
				}
			} else {
				findFiles(f);
			}
		}
	}

	/**
	 * 匹配文件夹
	 */
	private String matchDir(File f) {
		if (length == 0) {
			return f.getParentFile().getName();
		}
		String filename = f.getName();

		File parent = f;
		for (int i = 0; i < length; i++) {
			parent = parent.getParentFile();
			if (parent.exists()) {
				filename = parent.getName() + File.separatorChar + filename;
			}
		}
		if (filename.equals(orgTarger)) {
			return parent.getParentFile().getName();
		} else {
			return "";
		}
	}

	/**
	 * 通配符匹配文件名
	 * 
	 * @param pattern
	 *            通配符模式
	 * @param str
	 *            待匹配的字符串
	 * @return 匹配成功则返回true，否则返回false
	 */
	private boolean match(String pattern, String str) {
		int patternLength = pattern.length();
		int strLength = str.length();
		int strIndex = 0;
		char ch;
		for (int patternIndex = 0; patternIndex < patternLength; patternIndex++) {
			ch = pattern.charAt(patternIndex);
			if (ch == '*') {
				// 通配符星号*表示可以匹配任意多个字符
				while (strIndex < strLength) {
					if (match(pattern.substring(patternIndex + 1),
							str.substring(strIndex))) {
						return true;
					}
					strIndex++;
				}
			} else if (ch == '?') {
				// 通配符问号?表示匹配任意一个字符
				strIndex++;
				if (strIndex > strLength) {
					// 表示str中已经没有字符匹配?了。
					return false;
				}
			} else {
				if ((strIndex >= strLength) || (ch != str.charAt(strIndex))) {
					return false;
				}
				strIndex++;
			}
		}
		return (strIndex == strLength);
	}

	public String getOrgTarger() {
		return orgTarger;
	}

	public List<Filer> getList() {
		return list;
	}
}