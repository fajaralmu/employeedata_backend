package com.fajar.employeedata.util;

import java.util.Calendar;
import java.util.Date;

public class DateUtil {

	static final String[] monthNames = new String[] {
	                                    "Januari", "Februari", "Maret", "April", "Mei", "Juni", 
	                                    "Juli", "Agustus", "September", "Oktober", "November", "Desember"};
	public static String getDateString(Date date) {
		if (null == date) return null;
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		int day = cal.get(Calendar.DAY_OF_MONTH);
		int month = cal.get(Calendar.MONTH);
		int year = cal.get(Calendar.YEAR);
		return day +" "+monthNames[month]+" "+year;
	}
}
