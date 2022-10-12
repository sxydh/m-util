package cn.net.bhe.util;

import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

public class BateUtils extends org.apache.commons.lang3.time.DateUtils {

    public static String YYYY = "yyyy";

    public static String YYYY_MM = "yyyy-MM";

    public static String YYYY_MM_DD = "yyyy-MM-dd";

    public static String YYYYMMDDHHMMSS = "yyyyMMddHHmmss";

    public static String YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static String date2str(Date date, String format) {
        return new SimpleDateFormat(format).format(date);
    }

    public static String startDateOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.MONDAY);
        return date2str(calendar.getTime(), YYYY_MM_DD);
    }

    public static String endDateOfWeek(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.setFirstDayOfWeek(Calendar.MONDAY);
        calendar.set(Calendar.DAY_OF_WEEK, Calendar.SUNDAY);
        return date2str(calendar.getTime(), YYYY_MM_DD);
    }

    public static String startDateOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMinimum(Calendar.DAY_OF_MONTH));
        return date2str(calendar.getTime(), YYYY_MM_DD);
    }

    public static String endDateOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DAY_OF_MONTH, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        return date2str(calendar.getTime(), YYYY_MM_DD);
    }

    public static String startDateOfYear(Date date) {
        String dateStr = date2str(date, YYYY_MM_DD);
        return dateStr.substring(0, 4) + "-01-01";
    }

    public static String endDateOfYear(Date date) {
        String dateStr = date2str(date, YYYY_MM_DD);
        return dateStr.substring(0, 4) + "-12-31";
    }

}
