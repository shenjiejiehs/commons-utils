package com.huoli.utils.geo;

/**
 * 地理位置工具 <br>
 * 版权：Copyright (c) 2011-2013<br>
 * 公司：北京活力天汇<br>
 * 作者：童凡<br>
 * 版本：1.0<br>
 * 创建日期：2013-9-13<br>
 */
public class PositionUtil {
	private final static double PI = 3.14159265358979323; // 圆周率
	private static double EARTH_RADIUS = 6378.137;// 地球半径

	/**
	 * 获取两点之间的距离,简版的算法,对于千公里级的距离计算最好不要使用
	 * 
	 * @param lon1 经度1
	 * @param lat1 纬度1
	 * @param lon2 经度2
	 * @param lat2 纬度2
	 * @return 公里数
	 */
	public static double getDistanceSimple(double lat1, double lon1, double lat2, double lon2) {
		double x, y, distance;
		double lonDiff = Math.abs(lon2 - lon1);
		if (lonDiff > 180) {
			lonDiff = 360 - lonDiff;
		}
		x = lonDiff * PI * EARTH_RADIUS * Math.cos(((lat1 + lat2) / 2) * PI / 180) / 180;
		y = Math.abs(lat2 - lat1) * PI * EARTH_RADIUS / 180;
		distance = Math.hypot(x, y);
		return distance;
	}

	private static double rad(double d) {
		return d * Math.PI / 180.0;
	}

	/**
	 * 标准的距离计算,消耗资源较大,返回公里数
	 * 
	 * @param lat1
	 *            纬度1
	 * @param lon1
	 *            经度1
	 * @param lat2
	 *            纬度2
	 * @param lon2
	 *            经度2
	 * @return 公里数
	 */
	public static double getDistance(double lat1, double lon1, double lat2, double lon2) {
		double radLat1 = rad(lat1);
		double radLat2 = rad(lat2);
		double a = radLat1 - radLat2;
		double b = rad(lon1) - rad(lon2);

		double s = 2 * Math.asin(Math.sqrt(Math.pow(Math.sin(a / 2), 2) + Math.cos(radLat1) * Math.cos(radLat2)
				* Math.pow(Math.sin(b / 2), 2)));
		s = s * EARTH_RADIUS;
		return s;
	}

	/**
	 * 根据经纬度和距离返回一个矩形范围
	 * @param pos 位置信息
	 * @param distance 距离(单位为米)
	 * @return [p1, p2] 矩形的左下角Position(lng1,lat1)和右上角Position(lng2,lat2)
	 */
	public static Position[] getRectangle(Position pos, long distance) {
		float delta = 111 * 1000;
		double lon = pos.getLon();
		double lat = pos.getLat();
		double lon1;
		double lon2;
		double lat1;
		double lat2;
		if (lon != 0 && lat != 0) {
			lon1 = lon - distance / Math.abs(Math.cos(Math.toRadians(lat)) * delta);
			lon2 = lon + distance / Math.abs(Math.cos(Math.toRadians(lat)) * delta);
			lat1 = lat - (distance / delta);
			lat2 = lat + (distance / delta);

		} else {
			lon1 = lon - distance / delta;
			lon2 = lon + distance / delta;
			lat1 = lat - (distance / delta);
			lat2 = lat + (distance / delta);
		}
		Position p1 = new Position(lat1, lon1);
		Position p2 = new Position(lat2, lon2);

		return new Position[] { p1, p2 };
	}

	/**
	 * 获取两点之间的距离
	 * 
	 * @param pos1 点1
	 * @param pos2 点2
	 * @return 公里数
	 */
	public static double getDistance(Position pos1, Position pos2) {
		return getDistance(pos1.getLat(), pos1.getLon(), pos2.getLat(), pos2.getLon());
	}
}
