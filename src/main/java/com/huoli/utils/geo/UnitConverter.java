package com.huoli.utils.geo;

/**
 * 单位转换工具 <br>
 * 版权：Copyright (c) 2011-2014<br>
 * 公司：北京活力天汇<br>
 * 作者：童凡<br>
 * 版本：1.0<br>
 * 创建日期：2014年12月15日<br>
 */
public class UnitConverter {
	/**
	 * 海里转公里
	 * 
	 * @return
	 */
	public static double nmi2kilo(double nmi) {
		return nmi * 1.852;
	}

	/**
	 * 公里转海里
	 * 
	 * @return
	 */
	public static double kilo2nmi(double kilo) {
		return kilo / 1.852;
	}
}
