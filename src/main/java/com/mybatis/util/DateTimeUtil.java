package com.mybatis.util;

import org.joda.time.format.DateTimeFormat;

import java.util.Date;
import java.util.Locale;

/**
 * @author wangkun
 * 基于joda-time的时间工具类
 */
public class DateTimeUtil {

    public static final String FORMATTER_YYYY_MM_DD_HH_MM_SS = "yyyy-MM-dd HH:mm:ss";

    public static final String FORMATTER_YYYY_MM_DD = "yyyy-MM-dd";

    public static final String FORMATTER_YYYYMMDD = "yyyyMMdd";

    public static final String FORMATTER_HH_MM_SS = "HH:mm:ss";


    /**
     * 1秒钟的毫秒数
     */
    public static final long MILLIS_PER_SECOND = 1000;

    /**
     * 1分钟的毫秒数
     */
    public static final long MILLIS_PER_MINUTE = 60 * MILLIS_PER_SECOND;

    /**
     * 1小时的毫秒数
     */
    public static final long MILLIS_PER_HOUR = 60 * MILLIS_PER_MINUTE;

    /**
     * 1天的毫秒数
     */
    public static final long MILLIS_PER_DAY = 24 * MILLIS_PER_HOUR;


    /**
     * 格式化指定时间日期，yyyy-MM-dd
     *
     * @param date
     * @return
     */
    public static String formatDate(Date date) {
        if (date == null) {
            throw new NullPointerException();
        }
        LocalDate localDate = new LocalDate(date);
        return localDate.toString(FORMATTER_YYYY_MM_DD);
    }

    /**
     * 获取指定时间的一周第几天
     *
     * @param date
     * @return
     */
    public static int getDayOfWeek(Date date) {
        if (date == null) {
            throw new NullPointerException();
        }
        LocalDate localDate = new LocalDate(date);
        return localDate.getDayOfWeek();

    }


    /**
     * 获取当前时间
     *
     * @return
     */
    public static Date getCurrentDateTime() {
        return new Date();
    }


    /**
     * 获取当前日期加上指定天数后的时间
     *
     * @param days
     * @return
     */
    public static Date pulsDays(int days) {
        LocalDateTime localDateTime = LocalDateTime.now();
        return localDateTime.plusDays(days).toDate();
    }


    /**
     * 获取两个日期相差的天数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static long getBetweenDays(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new NullPointerException();
        }
        return Math.abs(date1.getTime() - date2.getTime()) / MILLIS_PER_DAY;
    }


    /**
     * 判断两个日期是否是同一天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isSameDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            throw new NullPointerException();
        }
        return new LocalDate(date1).isEqual(new LocalDate(date2));
    }

    /**
     * 获取指定时间加上几个月后的时间
     *
     * @param date
     * @param months
     * @return
     */
    public static Date addMonths(Date date, int months) {
        if (date == null) {
            throw new NullPointerException();
        }
        LocalDateTime localDateTime = new LocalDateTime(date);
        return localDateTime.plusMonths(months).toDate();
    }

    /**
     * 获取指定时间加上几天后的时间
     * @param date
     * @param days
     * @return
     */
    public static Date addDays(Date date, int days) {
        if (date == null) {
            throw new NullPointerException();
        }
        LocalDateTime localDateTime = new LocalDateTime(date);
        return localDateTime.plusDays(days).toDate();
    }

    /**
     * 获取当前日期,格式yyyyMMdd
     *
     * @return
     */
    public static String now() {
        return new LocalDate().toString(FORMATTER_YYYYMMDD);
    }


    /**
     * 获取当天日期的最后时间
     *
     * @return
     */
    public static Date getEndOfCurrentDay() {
        DateTime dateTime = DateTime.now();
        return dateTime.millisOfDay().withMaximumValue().toDate();
    }


    /**
     * 获取当天日期的开始时间
     *
     * @param date
     * @return
     */
    public static Date getStartOfDay(Date date) {
        DateTime dateTime = new DateTime(date);
        return dateTime.millisOfDay().withMinimumValue().toDate();
    }


    /**
     * 获取指定两个之日相差的年数
     *
     * @param date1
     * @param date2
     * @return
     */
    public static int getBetweenYears(Date date1, Date date2) {
        return Math.abs(Years.yearsBetween(new DateTime(date1), new DateTime(date2)).getYears());
    }


    /**
     * 把yyyy-MM-dd HH:mm:ss格式的字符串转换成Date类型
     *
     * @param dateTime
     * @return
     */
    public static Date parseDateTime(String dateTime) {
        try {
            return DateTime.parse(dateTime, DateTimeFormat.forPattern(FORMATTER_YYYY_MM_DD_HH_MM_SS)).toDate();
        } catch (Exception e) {
            return null;
        }

    }


    /**
     * 把yyyy-MM-dd格式的字符串转换成Date类型
     *
     * @param date
     * @return
     */
    public static Date parseDate(String date) {
        try {
            return DateTime.parse(date, DateTimeFormat.forPattern(FORMATTER_YYYY_MM_DD)).toDate();
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 获取当前时间,HH:mm:ss
     *
     * @return
     */
    public static String getNowTime() {
        LocalTime localTime = LocalTime.now();
        return localTime.toString(FORMATTER_HH_MM_SS);
    }

    /**
     * 获取昨天时间
     *
     * @return
     */
    public static Date getYesterday() {
        DateTime dateTime = DateTime.now();
        return dateTime.minusDays(1).toDate();
    }


    /**
     * 获取指定时间在当月中是第几天
     *
     * @param date
     * @return
     */
    public static int getDayOfMonth(Date date) {
        DateTime dateTime = new DateTime(date);
        return dateTime.dayOfMonth().get();
    }

    /**
     * 获取指定时间的月份总共有多少天
     *
     * @param date
     * @return
     */
    public static int getDaysOfMonth(Date date) {
        DateTime dateTime = new DateTime(date);
        return dateTime.dayOfMonth().getMaximumValue();
    }

    /**
     * 获取给定时间是星期几
     *
     * @param date
     * @return
     */
    public static String getDayOfWeekText(Date date) {
        DateTime dateTime = new DateTime(date);
        return dateTime.dayOfWeek().getAsShortText(Locale.CHINESE);
    }

    /**
     * 判断今天是否是生日
     *
     * @param monthOfDay
     * @param dayOfMonth
     * @return
     */
    public static boolean isBirthDay(int monthOfDay, int dayOfMonth) {
        MonthDay monthDay = new MonthDay(monthOfDay, dayOfMonth);
        MonthDay monthDayNow = MonthDay.now();
        return monthDay.equals(monthDayNow);
    }

    /**
     * 判断给定日期是否是闰年
     *
     * @param date
     * @return
     */
    public static boolean isLeapYear(Date date) {
        LocalDate localDate = new LocalDate(date);
        return localDate.year().isLeap();
    }

    /**
     * 获取距离指定纪念日还有多少天
     * @param monthOfYear
     * @param dayOfMonth
     * @return
     */
    public static int distanceDays(int monthOfYear, int dayOfMonth) {
        LocalDate localDate = LocalDate.now();
        localDate = localDate.withMonthOfYear(monthOfYear).withDayOfMonth(dayOfMonth);

        LocalDate currentDate = LocalDate.now();
        /**
         * 如localDate = 2019-05-12, currentDate = 2019-05-11
         * localDate.isBefore(currentDate) 返回false
         */
        if (localDate.isAfter(currentDate)) {
            return getBetweenDays(localDate, currentDate);
        } else if (localDate.isBefore(currentDate)) {
            localDate = localDate.withYear(localDate.getYear() + 1);
            return getBetweenDays(localDate, currentDate);
        } else {
            return 0;
        }
    }

    public static int getBetweenDays(LocalDate localDate, LocalDate otherLocalDate) {
        if (localDate == null || otherLocalDate == null) {
            throw new NullPointerException();
        }
        return Math.abs(Days.daysBetween(localDate, otherLocalDate).getDays());
    }
}