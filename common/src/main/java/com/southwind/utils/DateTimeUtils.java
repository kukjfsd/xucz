package com.southwind.utils;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.Locale;

public class DateTimeUtils {

	public static int getAddDate(Date date, int days) {
			date = org.apache.commons.lang.time.DateUtils.addDays(date, days);
			SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
			return Integer.parseInt(sdf.format(date)); //20191025
	}
	
	public static int getAddDate(int date, int days) {
		String dt = String.valueOf(date);
		Date dtt = StrToDate(dt,"yyyyMMdd");
		Date dat = org.apache.commons.lang.time.DateUtils.addDays(dtt, days);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return Integer.parseInt(sdf.format(dat)); //20191025
	}
	
	public static String getAddDateStr(int date, int days) {
		String dt = String.valueOf(date);
		Date dtt = StrToDate(dt,"yyyyMMdd");
		Date dat = org.apache.commons.lang.time.DateUtils.addDays(dtt, days);
		SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
		return sdf.format(dat); //2019-10-25
	}

	public static String getCurTimeMilli() {
		 String ss = new SimpleDateFormat("yyyyMMddHHmmssSSS").format(new Date());
		 return ss.substring(0,12) + "-" + ss.substring(12,14) + "-" + ss.substring(14);
//
//        Calendar cld = Calendar.getInstance();
//        int YY = cld.get(Calendar.YEAR);
//        int MM = cld.get(Calendar.MONTH)+1;
//        int DD = cld.get(Calendar.DATE);
//        int HH = cld.get(Calendar.HOUR_OF_DAY);
//        int mm = cld.get(Calendar.MINUTE);
//        int SS = cld.get(Calendar.SECOND);
//        int MI = cld.get(Calendar.MILLISECOND);
//
//        return YY + MM + DD + HH + mm + SS + MI;
	}
	
	public static int getIntDate(Date date) {
		SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
		return Integer.parseInt(sdf.format(date)); //20191025
	}
	
	public static Date StrToDate(String str, String pattern) {
		SimpleDateFormat format = new SimpleDateFormat(pattern);
		Date date = null;
		try {
			date = format.parse(str);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return date;
	}

	public static void main(String[] args) {
		System.out.println(getAddDateStr(20191001,-1));
		System.out.println(getCurTimeMilli());
	}
}
