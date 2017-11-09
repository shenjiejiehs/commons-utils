package com.huoli.utils;   

/**
 * sql语句工具类 <br>
 * 版权：Copyright (c) 2011-2014<br>
 * 公司：北京活力天汇<br>
 * 作者：罗良杰<br>
 * 版本：1.0<br>
 * 创建日期：2014年9月17日<br>
 */
public class SQLUtil {
	/**
	 * 获取oralce分页语句
	 * @param sql 原始sql
	 * @param startIndex 开始索引数
	 * @param size 当前页大小数
	 * @return
	 */
	public static String getOralcePageSql(String sql, int startIndex, int size) {
		StringBuffer querySQL = new StringBuffer();
		querySQL.append("SELECT * FROM (SELECT ROWNUM R,X.* FROM (");
		querySQL.append(sql).append(")  X ) Y WHERE Y.R < ");
		querySQL.append(startIndex + size + 1).append(" AND  Y.R > ");
		querySQL.append(startIndex);
		return querySQL.toString();
	}
}
