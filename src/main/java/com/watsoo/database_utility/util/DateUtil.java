package com.watsoo.database_utility.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;
import java.util.TimeZone;

import org.springframework.stereotype.Component;

import com.watsoo.database_utility.constant.Constant;

@Component
public class DateUtil {

	public static String getDateAfterSomeDay(Date date, Integer noOfDays) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.DATE_FORMAT_YYYY_MM_DD);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, -noOfDays);
//		cal.set(Calendar.HOUR_OF_DAY, 18);
//		cal.set(Calendar.MINUTE, 30);
//		cal.set(Calendar.SECOND, 0);
		Date finalDate = cal.getTime();
		return dateFormat.format(finalDate);
	}

	public static Date addMinutes(long dateInLong, long min) {
		dateInLong = dateInLong + Constant.ONE_MIN_IN_LONG * min;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(dateInLong));
		Date date = calendar.getTime();
		return date;
	}

	public static Date addSeconds(long dateInLong, long seconds) {
		dateInLong = dateInLong + Constant.ONE_SEC_IN_LONG * seconds;
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(dateInLong));
		Date date = calendar.getTime();
		return date;
	}

	public static Date getDateFromUtcToISTDateFromat(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR, +5);
		cal.add(Calendar.MINUTE, +30);
		Date dateFrom = cal.getTime();
		return dateFrom;
	}

	public static Date getOnlyDateWithOutTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date finalDate = cal.getTime();
		return finalDate;
	}

	public static Date getDateStartingUTCTime(Date date) {

		Calendar calendar = Calendar.getInstance();

		// Set the desired time to 18:30:00
		calendar.set(Calendar.HOUR_OF_DAY, 18);
		calendar.set(Calendar.MINUTE, 30);
		calendar.set(Calendar.SECOND, 0);

		calendar.add(Calendar.DAY_OF_MONTH, -1);

		Date finalDate = calendar.getTime();
		return finalDate;
	}

	public static Date getOnlyDateFromUtcToISTDateFromat(Date date) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.DATE_FORMAT_YYYY_MM_DD);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR, +5);
		cal.add(Calendar.MINUTE, +30);
		Date dateFrom = cal.getTime();
		return dateFormat.parse(dateFormat.format(dateFrom));
	}

	public static Date getDateStartingUTCTimeSpecificFormat(Date date, String format) throws ParseException {
		SimpleDateFormat dateFormat = new SimpleDateFormat(format);
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.add(Calendar.DAY_OF_MONTH, -1);
		cal.set(Calendar.HOUR, 18);
		cal.set(Calendar.MINUTE, 30);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		Date finalDate = cal.getTime();
		return dateFormat.parse(dateFormat.format(finalDate));
	}

	public static Date getUTCDateStartTime(Long date) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(new Date(date));
		Date finalDate = calendar.getTime();
		finalDate = addMinutesToJavaUtilDate(finalDate, (Constant.IST_OFFSET_IN_MINUTES));
		SimpleDateFormat isoFormat = new SimpleDateFormat("E MMM dd HH:mm:ss Z yyyy");
		isoFormat.setTimeZone(TimeZone.getTimeZone("UTC"));
		try {
			finalDate = isoFormat.parse(finalDate.toString());
			finalDate = getDateStartingUTCTime(finalDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return finalDate;
	}

	public static Date getUTCDateStartTimeINString(Date date) {

		Date finalDate = date;
		finalDate = addMinutesToJavaUtilDate(finalDate, (Constant.IST_OFFSET_IN_MINUTES));
		
		String stringDate = getDateAfterSomeDay(finalDate, 1)+"T18:30:00.000";
		
		SimpleDateFormat dateFormat = new SimpleDateFormat(Constant.DATE_FORMAT_YYYY_MM_DD_T_HH_MM_SS_Z);
		try {
			finalDate = dateFormat.parse(stringDate);
		} catch (ParseException e) {
			e.printStackTrace();
		}
		return finalDate;
	}

	public static Date addMinutesToJavaUtilDate(Date date, int minutes) {
		Calendar calendar = Calendar.getInstance();
		calendar.setTime(date);
		calendar.add(Calendar.MINUTE, minutes);
		date = calendar.getTime();
		return date;
	}

}
