package com.tyhgg.core.framework.util;

import java.text.SimpleDateFormat;
import java.util.Date;

import org.apache.commons.lang.StringUtils;

public class DateUtil {

	public static final String YY_MM_DD = "yyyy-MM-dd";
	public static final String YY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

	/**
	 * 方法说明 自定义日期格式
	 * 
	 * @param date
	 * @param formatStr
	 * @return
	 */
	public static String dateFormat(Date date, String pattern) {
		if ((null == date) || date.toString().equals(StringUtils.EMPTY)) {
			return StringUtils.EMPTY;
		}
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		return format.format(date);
	}

	/**
	 * 方法说明 自定义日期格式
	 * 
	 * @param date
	 * @param formatStr
	 * @return
	 */
	public static String getNowDateStr() {
		
		SimpleDateFormat format = new SimpleDateFormat(YY_MM_DD_HH_MM_SS);
		return format.format(new Date());
	}

	public static String setLongToDateStr(String dateFormat, Long time) {
		SimpleDateFormat format = new SimpleDateFormat(dateFormat);
		Date date = new Date(time);
		return format.format(date);
	}

	/**
	 * 两个时间相隔的毫秒数
	 * 
	 * @param date
	 * @param formatStr
	 * @return
	 */
	public static int dateBetweenMillisecond(java.sql.Timestamp beginTime, java.sql.Timestamp endTime) {
		if (null == beginTime || null == endTime) {
			return 0;
		}
		return (int) (endTime.getTime() - beginTime.getTime());

	}

	// public static void main(String[] args){
	//
	// System.out.println(dateFormat(new Date(), "yyyy.MM.dd"));
	//
	// Date date1 = new Date();
	//
	// Calendar cl = Calendar.getInstance();
	// cl.setTime(new Date());
	// cl.add(Calendar.SECOND, 1);
	// Date date2 = cl.getTime();
	// System.out.println(dateBetweenMillisecond(new
	// java.sql.Timestamp(date1.getTime()), new
	// java.sql.Timestamp(date2.getTime())));
	// }
}
