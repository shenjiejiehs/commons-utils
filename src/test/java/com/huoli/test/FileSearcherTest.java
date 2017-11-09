package com.huoli.test;

import java.io.File;
import java.util.List;

import org.junit.After;
import org.junit.Assert;
import org.junit.Before;
import org.junit.Test;

import com.huoli.utils.FileSearcher;
import com.huoli.utils.FileUtil;
import com.huoli.utils.FileSearcher.Filer;

public class FileSearcherTest {
	
	private static String createDir = "temp" + File.separator;
	
	private static String filename = "message" + File.separator + "test.ftl";
	
	private static String createFile = createDir + "default" + File.separator + filename;
	
	@Before
	public void createFile() throws Exception {
		File f = new File(createFile);
		f.getParentFile().mkdirs();
		f.createNewFile();
		FileUtil.writeFile("${test}", createFile);
	}
	
	@Test
	public void test() {
		FileSearcher search = new FileSearcher(createDir, filename);

		List<Filer> resultList = search.findFiles();
		Assert.assertEquals(1, resultList.size());
		Filer f = resultList.get(0);
		// 显示查找结果。
		Assert.assertEquals(f.getFile().toString(), createFile);
	}
	
	@After
	public void deleteFile() throws Exception {
		FileUtil.delete(createDir);
	}
}
