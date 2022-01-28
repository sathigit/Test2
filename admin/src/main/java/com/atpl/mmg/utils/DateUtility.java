package com.atpl.mmg.utils;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.LocalDate;
import java.time.Month;
import java.time.format.DateTimeFormatter;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.GregorianCalendar;
import java.util.List;

import org.joda.time.DateTime;
import org.joda.time.DateTimeZone;
import org.joda.time.Days;
import org.joda.time.Instant;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import com.atpl.mmg.constant.Constants;
import com.atpl.mmg.model.dashboard.ReportDashboard;

public class DateUtility {
	protected static Logger logger = LoggerFactory.getLogger(DateUtility.class);
	public static final String DATE_FORMAT_DDMMYYYY = "ddMMyyyy";
	public static final String DATE_FORMAT_DD_MMM_YYYY = "dd-MMM-yyyy";
	public static final String DATE_FORMAT_DD_MM_YYYY = "dd/MM/yyyy";
	public static final String DATE_FORMAT_YYYYMMDDHHMMSS = "yyyyMMddHHmmss";
	public static final String DATE_FORMAT_YYYY_MM_DD_HHMMSS = "yyyy-MM-dd HH:mm:ss";
	public static final String DATE_FORMAT_DD_MMM_YYYY_HHMMSS = "dd-MMM-yyyy HH:mm:ss";
	public static final String DATE_FORMAT_DD_MM_YYYY_HHMMSS = "dd-MM-yyyy HH:mm:ss aa";
	public static final String DATE_FORMAT_YYYY_MM_DD = "yyyy-MM-dd";

	private DateUtility() {

	}

	public static boolean isThisDateValid(String dateToValidate, String dateFromat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
		sdf.setLenient(false);
		try {
			sdf.parse(dateToValidate);
		} catch (Exception e) {
			logger.error("isThisDateValid:Error:", e);
			return false;
		}
		return true;
	}

	public static Date getDateByString(String dateToValidate, String dateFromat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
		sdf.setLenient(false);
		try {
			return sdf.parse(dateToValidate);
		} catch (Exception e) {
			logger.error("getDateByString:Error:", e);
		}
		return new Date();
	}

	public static String getDateByStringFormat(Date date, String dateFromat) {
		SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
		sdf.setLenient(false);
		try {
			return sdf.format(date);
		} catch (Exception e) {
			logger.error("getDateByStringFormat:Error:", e);
		}
		return "";
	}

	public static boolean startDateGtEndDate(String satrtDate, String endDate, String dateFromat) {
		try {
			SimpleDateFormat sdf = new SimpleDateFormat(dateFromat);
			sdf.setLenient(false);
			Date sDate = sdf.parse(satrtDate);
			Date eDate = sdf.parse(endDate);
			return startDateGtEndDate(sDate, eDate);
		} catch (Exception e) {
			logger.error("startDateGtEndDate:Error:", e);
			return false;
		}
	}

	public static boolean startDateGtEndDate(Date startDate, Date endDate) {
		if (startDate.after(endDate)) {
			return true;
		}
		return false;
	}

	public static boolean dateBeforeToday(Date fromDate) {
		if (fromDate.before(dateToEOD(new Date()))) {
			return true;
		}
		return false;
	}

	public static Date dateToremoveTime(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 0);
		cal.set(Calendar.MINUTE, 0);
		cal.set(Calendar.SECOND, 0);
		cal.set(Calendar.MILLISECOND, 0);
		return cal.getTime();
	}

	public static Date dateToEOD(Date date) {
		Calendar cal = Calendar.getInstance();
		cal.setTime(date);
		cal.set(Calendar.HOUR_OF_DAY, 23);
		cal.set(Calendar.MINUTE, 59);
		cal.set(Calendar.SECOND, 59);
		cal.set(Calendar.MILLISECOND, 999);
		return cal.getTime();
	}

	public static Date getYesterdayDate() {
		DateTime today = new DateTime(DateUtility.dateToEOD(new Date()));
		return today.minusDays(1).toDate();
	}

	public static Date getDayBeforeYesterdayDate() {
		DateTime today = new DateTime(DateUtility.dateToEOD(new Date()));
		return today.minusDays(2).toDate();
	}

	public static String getWeekStartDate(int year, int week) {
		Calendar calendar = getCalendarForWeek(year, week);
		return getDateByStringFormat(calendar.getTime(), DATE_FORMAT_DDMMYYYY);
	}

	public static String getWeekEndDate(int year, int week) {
		Calendar calendar = getCalendarForWeek(year, week);
		calendar.add(Calendar.DATE, 6);
		return getDateByStringFormat(calendar.getTime(), DATE_FORMAT_DDMMYYYY);
	}

	public static String getMonthStartDate(int year, int month) {
		Calendar calendar = getCalendarForMonth(year, month);
		calendar.add(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH) - 1);
		return getDateByStringFormat(calendar.getTime(), DATE_FORMAT_DDMMYYYY);
	}

	public static String getMonthEndDate(int year, int month) {
		Calendar calendar = getCalendarForMonth(year, month);
		calendar.add(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH) - 1);
		return getDateByStringFormat(calendar.getTime(), DATE_FORMAT_DDMMYYYY);
	}

	private static Calendar getCalendarForWeek(int year, int week) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.setMinimalDaysInFirstWeek(4);
		calendar.setFirstDayOfWeek(Calendar.MONDAY);
		calendar.set(Calendar.WEEK_OF_YEAR, week);
		calendar.set(Calendar.YEAR, year);
		return calendar;
	}

	private static Calendar getCalendarForMonth(int year, int month) {
		Calendar calendar = Calendar.getInstance();
		calendar.clear();
		calendar.set(Calendar.MONTH, month);
		calendar.set(Calendar.YEAR, year);
		calendar.set(Calendar.DATE, 1);
		return calendar;
	}

	public static boolean comapreDateToYesterday(Date startDate, Date endDate) throws Exception {
		DateTime sDate = new DateTime(DateUtility.dateToremoveTime(startDate));
		DateTime eDate = new DateTime(DateUtility.dateToEOD(endDate));
		DateTime yesterdaySday = new DateTime(DateUtility.dateToremoveTime(new Date()));
		yesterdaySday = yesterdaySday.minusDays(1);
		DateTime yesterdayEday = new DateTime(DateUtility.dateToEOD(new Date()));
		yesterdayEday = yesterdayEday.minusDays(1);
		if (sDate.equals(yesterdaySday) || eDate.equals(yesterdayEday))
			return true;
		if (sDate.isBefore(yesterdaySday) && eDate.isAfter(yesterdayEday))
			return true;
		return false;
	}

	public static boolean comapreDateToToday(Date startDate, Date endDate) throws Exception {
		DateTime sDate = new DateTime(DateUtility.dateToremoveTime(startDate));
		DateTime eDate = new DateTime(DateUtility.dateToEOD(endDate));
		DateTime todaySday = new DateTime(DateUtility.dateToremoveTime(new Date()));
		DateTime todayEday = new DateTime(DateUtility.dateToEOD(new Date()));
		if (sDate.equals(todaySday) || eDate.equals(todayEday))
			return true;
		if (sDate.isBefore(todaySday) && eDate.isAfter(todayEday))
			return true;
		return false;
	}

	public static Date getYesterday() throws Exception {
		DateTime yesterday = new DateTime(DateUtility.dateToEOD(new Date()));
		yesterday = yesterday.minusDays(1);
		return yesterday.toDate();
	}

	public static List<String> getCampaignDatesList(String fromDate, String toDate) {
		List<String> campaignDasDatas = new ArrayList<String>();
		try {
			Date campReqStartDate = DateUtility.getDateByString(fromDate, DateUtility.DATE_FORMAT_DDMMYYYY);
			DateTime startDate = new DateTime(DateUtility.dateToremoveTime(campReqStartDate));
			int nDays = getNoOfDays(fromDate, toDate);
			for (int i = 0; i <= nDays; i++) {
				campaignDasDatas.add(DateUtility.getDateByStringFormat(startDate.plusDays(i).toDate(),
						DateUtility.DATE_FORMAT_DD_MM_YYYY));
			}
		} catch (Exception e) {
			logger.error("getCampaignDates:Error : ", e);
		}
		return campaignDasDatas;
	}

	public static int getNoOfDays(String fromDate, String toDate) {
		int nDays = 0;
		try {
			Date campReqStartDate = DateUtility.getDateByString(fromDate, DateUtility.DATE_FORMAT_DDMMYYYY);
			Date campReqEndDate = DateUtility.getDateByString(toDate, DateUtility.DATE_FORMAT_DDMMYYYY);
			DateTime startDate = new DateTime(DateUtility.dateToremoveTime(campReqStartDate));
			DateTime endDate = new DateTime(DateUtility.dateToEOD(campReqEndDate));
			nDays = Days.daysBetween(startDate.withTimeAtStartOfDay(), endDate.withTimeAtStartOfDay()).getDays();
		} catch (Exception e) {
			logger.error("getNoOfDays:Error : ", e);
		}
		return nDays;
	}

	public static boolean isCurrentMonth(int year, int month) {
		Calendar now = Calendar.getInstance();
		if (now.get(Calendar.YEAR) == year && now.get(Calendar.MONTH) == month)
			return true;
		return false;
	}

	public static boolean isCurrentWeek(int year, int week) {
		Calendar now = Calendar.getInstance();
		if (now.get(Calendar.YEAR) == year && now.get(Calendar.WEEK_OF_YEAR) == week)
			return true;
		return false;
	}

	public static String dateFormater(String dateFromJSON, String expectedFormat, String oldFormat) {
		SimpleDateFormat dateFormat = new SimpleDateFormat(oldFormat);
		Date date = null;
		String convertedDate = null;
		try {
			date = dateFormat.parse(dateFromJSON);
			SimpleDateFormat simpleDateFormat = new SimpleDateFormat(expectedFormat);
			convertedDate = simpleDateFormat.format(date);
		} catch (Exception e) {
			e.printStackTrace();
		}
		return convertedDate;
	}

	public static Date setTimeZone(Date date) throws ParseException {
		return setTimeZone();
	}

	public static Date setTimeZone() {
		Instant nowUtc = Instant.now();
		DateTimeZone asiaCalcutta = DateTimeZone.forID(Constants.ZONE);
		DateTime nowAsiaSingapore = nowUtc.toDateTime(asiaCalcutta);
		Date currentDateAndTime = new Date();
		currentDateAndTime = nowAsiaSingapore.toDate();
		return currentDateAndTime;
	}

	public static ReportDashboard getStartAndEndDate(ReportDashboard reportDashboard) {
		Date startDate = new Date();
		Date endDate = new Date();
		LocalDate currentDate = LocalDate.now();
		int day = currentDate.getDayOfMonth();
		Month month = currentDate.getMonth();
		int mon = month.getValue();
		int year = currentDate.getYear();
		Calendar gc = new GregorianCalendar();
		if (reportDashboard.isMonthly()) {
			gc.set(Calendar.DAY_OF_MONTH, gc.getActualMinimum(Calendar.DAY_OF_MONTH));
			startDate = gc.getTime();
			gc.set(Calendar.DAY_OF_MONTH, gc.getActualMaximum(Calendar.DAY_OF_MONTH));
			endDate = gc.getTime();
		}
		if (reportDashboard.isYearly()) {
			gc.set(Calendar.YEAR, year);
			endDate = gc.getTime();
			gc.add(Calendar.YEAR, -1);
			startDate = gc.getTime();
		}
		if (reportDashboard.isWeekly()) {
			Calendar cal = new GregorianCalendar(year, mon, day);
			int week = cal.get(Calendar.WEEK_OF_YEAR);
			System.out.println("Week: " + week);
			Calendar calendar = Calendar.getInstance();
			cal.set(Calendar.WEEK_OF_YEAR, week);
			Date yourDate = calendar.getTime();
			cal.setTime(yourDate);
			cal.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
			startDate = cal.getTime();
			cal.add(Calendar.DATE, 6);
			cal.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
			endDate = cal.getTime();
		}
		SimpleDateFormat format = new SimpleDateFormat("yyyy-MM-dd");
		System.out.println("Calculated  start date : " + format.format(startDate));
		System.out.println("Calculated  end date : " + format.format(endDate));
		reportDashboard.setStartDate(startDate);
		reportDashboard.setEndDate(endDate);
		return reportDashboard;
	}
}
