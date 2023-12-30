package cn.net.bhe.mutil;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.time.Instant;
import java.time.LocalDateTime;
import java.time.ZoneId;
import java.time.temporal.ChronoUnit;
import java.util.Date;

public final class DtUtils {

    public static final String YYYY = "yyyy";
    public static final String YYYY_MM = "yyyy-MM";
    public static final String YYYY_MM_DD = "yyyy-MM-dd";
    public static final String YYYY_MM_DD_HH24_MI_SS = "yyyy-MM-dd HH:mm:ss";
    public static final String YYYY_MM_DD_HH24_MI_SS_SSSSSS = "yyyy-MM-dd HH:mm:ss.SSSSSS";
    public static final String YYYYMMDDHH24MISSSSS = "yyyyMMddHHmmssSSS";

    public static String ts17() {
        return format(date(), YYYYMMDDHH24MISSSSS);
    }

    public static Date date() {
        return new Date();
    }

    public static String format() {
        return format(date(), YYYY_MM_DD_HH24_MI_SS);
    }

    public static String format(Date date) {
        return format(date, YYYY_MM_DD_HH24_MI_SS);
    }

    public static String format(Date date, String format) {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.format(date);
    }

    public static Date parse(String dateStr) throws ParseException {
        return parse(dateStr, YYYY_MM_DD_HH24_MI_SS);
    }

    public static Date parse(String dateStr, String format) throws ParseException {
        SimpleDateFormat dateFormat = new SimpleDateFormat(format);
        return dateFormat.parse(dateStr);
    }

    public static Date add(Date date, ChronoUnit unit, long delta) {
        Instant instant = date.toInstant();
        LocalDateTime localDateTime = instant.atZone(ZoneId.systemDefault()).toLocalDateTime();
        LocalDateTime resLocalDateTime = localDateTime.plus(delta, unit);
        return Date.from(resLocalDateTime.atZone(ZoneId.systemDefault()).toInstant());
    }

    public static Date addSeconds(Date date, long delta) {
        return add(date, ChronoUnit.SECONDS, delta);
    }

    public static Date addMinutes(Date date, long delta) {
        return add(date, ChronoUnit.MINUTES, delta);
    }

    public static Date addDays(Date date, long delta) {
        return add(date, ChronoUnit.DAYS, delta);
    }

}
