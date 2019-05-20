package sg.edu.nus.javalapsteam9.util;

import java.time.LocalDate;
import java.time.temporal.ChronoUnit;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.TimeZone;

import sg.edu.nus.javalapsteam9.model.PublicHoliday;

public final class Util {
	
	public static final String TEST_EMP_ID = "emp1";
	
	public static Date now() {
		return getInstance().getTime();
	}
	
	private static Calendar getInstance() {
		return Calendar.getInstance(getTimeZone());
	}
	
	private static TimeZone getTimeZone() {
		return TimeZone.getTimeZone("UTC");
	}
	
	public static Date getUtcDate(Date date) {
		Calendar cal = getInstance();
		cal.setTime(date);
		cal.add(Calendar.HOUR_OF_DAY, 8);
		return cal.getTime();
	}
	
	private static LocalDate parseDateToLocalDate(Date date) {
		Calendar cal = getInstance();
		cal.setTime(date);
		return LocalDate.of(cal.get(Calendar.YEAR), cal.get(Calendar.MONTH) + 1, cal.get(Calendar.DAY_OF_MONTH));
	}
	
	private static Date parseLocalDateToDate(LocalDate localDate) {
		Calendar cal = getInstance();
		cal.add(Calendar.YEAR, localDate.getYear());
		cal.add(Calendar.MONTH, localDate.getMonthValue() - 1);
		cal.add(Calendar.DAY_OF_MONTH, localDate.getDayOfMonth());
		return cal.getTime();
	}
	
	public static Boolean isValidStartDate(Date date) {
		LocalDate locdate = parseDateToLocalDate(date);
		LocalDate today = parseDateToLocalDate(now());
		return !locdate.isBefore(today);
	}
	
	public static Boolean isValidEndDate(Date startDate, Date endDate) {
		LocalDate startdate = parseDateToLocalDate(startDate);
		LocalDate enddate = parseDateToLocalDate(endDate);
		return (startdate.isBefore(enddate) || startdate.isEqual(enddate));
	}
	
	public static long calculatePeriodBetweenDates(Date startDate, Date endDate) {
		LocalDate startdate = parseDateToLocalDate(startDate);
		LocalDate enddate = parseDateToLocalDate(endDate);
		return ChronoUnit.DAYS.between(startdate, enddate);
	}

	public static Long calculatePeriodBetweenDatesExcludeHolidays(Date startDate, Date endDate, List<PublicHoliday> holidays) {

		Long days = 0L;
		LocalDate startdate = parseDateToLocalDate(startDate);
		LocalDate enddate = parseDateToLocalDate(endDate).plusDays(1);
		for (; startdate.isBefore(enddate); startdate = startdate.plusDays(1)) {
			switch (startdate.getDayOfWeek()) {
			case SATURDAY:
				startdate = startdate.plusDays(1);
				break;
			case SUNDAY:
				break;
			default:
				if(isPublicHoliday(startdate, holidays))
					break;
				++days;
			}
		}

		return days;
	}
	
	private static Boolean isPublicHoliday(final LocalDate date, List<PublicHoliday> holidays) {
		PublicHoliday holiday = holidays.stream().filter(h -> h.getDate().equals(parseLocalDateToDate(date)))
				.findFirst().orElse(null);
		return (holiday != null);

	}
	
}
