package com.novel.util;
import java.math.BigDecimal;

/**
 * 常用公共方法
 */
public class ComUtils {
	/**
	 * 转成int，错误返回0
	 * @author panyk
	 * @param str
	 * @return
	 */
	public static int parseInt(Object str) {
		int num = 0;
		if (str == null) {
		} else {
			try {
				num = (int) Float.parseFloat(str.toString());
			} catch (Exception e) {
			}
		}
		return num;
	}
	/**
	 * 转成double，错误返回0
	 * @author panyk
	 * @param str
	 * @return
	 */
	public static double parseDouble(Object str) {
		double num = 0d;
		if (str == null) {
		} else {
			try {
				if(!str.toString().equals("")){
					num = Double.parseDouble(str.toString());
				}
			} catch (Exception e) {
			}
		}
		return num;
	}
	/**
	 * 调toString方法转成string，删除两边空格
	 * @author panyk
	 * @param obj
	 * @return
	 */
	public static String dealNull(Object obj) {
		if (obj == null) {
			return "";
		} else {
			return obj.toString().trim();
		}
	}
	/**
	 * double四舍五入，并保留两位小数
	 * @author panyk
	 * @param obj
	 * @return
	 */
	public static Double doubleRound(Double obj){
		BigDecimal bd = null;
		try{
			bd = new BigDecimal(obj);
			bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		}catch(Exception e){
			e.printStackTrace();
			bd = new BigDecimal(0d);
		}        
        return bd.doubleValue();
	}
	/**
	 * double四舍五入，并保留两位小数
	 * @author panyk
	 * @param obj
	 * @return
	 */
	public static Double doubleRound(Object obj){
		BigDecimal bd = null;
		try{
			bd = new BigDecimal(parseDouble(obj));
			bd = bd.setScale(2, BigDecimal.ROUND_HALF_UP);
		}catch(Exception e){
			e.printStackTrace();
			bd = new BigDecimal(0d);
		}        
        return bd.doubleValue();
	}
}
