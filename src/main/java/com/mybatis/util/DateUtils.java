package com.mybatis.util;

import java.time.Instant;
import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.MonthDay;
import java.time.YearMonth;
import java.time.ZoneId;
import java.time.format.DateTimeFormatter;
import java.util.Date;

public class DateUtils {

    private DateUtils() {

    }

    /**
     * 获取当前日期，格式为yyyy-MM-dd
     *
     * @return
     */
    public static String getNowDate() {
        LocalDate localDate = LocalDate.now();
        return localDate.toString();
    }


    /**
     * 比较两个日期是否是同一天
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isEqualsDay(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = date1.toInstant();
        Instant instant1 = date2.toInstant();
        return LocalDateTime.ofInstant(instant, zone).toLocalDate().equals(LocalDateTime.ofInstant(instant1, zone).toLocalDate());

    }


    /**
     * 判断是否是闰年
     *
     * @param year
     * @return
     */
    public static boolean isLeapYear(int year) {
        if (year <= 0) {
            throw new IllegalArgumentException();
        }
        return LocalDate.ofYearDay(year, 1).isLeapYear();
    }

    /**
     * 判断是否是重复日期，比如用来检查生日，每月的账单等
     *
     * @param month
     * @param dayOfMonth
     * @return
     */
    public static boolean isRepeatDay(int month, int dayOfMonth) {
        if (month <= 0 || month > 12) {
            throw new IllegalArgumentException();
        }
        if (dayOfMonth <= 0 || dayOfMonth > 31) {
            throw new IllegalArgumentException();
        }
        LocalDate dateOfBirth = LocalDate.of(1990, month, dayOfMonth);
        LocalDate now = LocalDate.now();
        MonthDay birthDay = MonthDay.of(dateOfBirth.getMonth(), dateOfBirth.getDayOfMonth());
        MonthDay currentMonthDay = MonthDay.from(now);
        return currentMonthDay.equals(birthDay);
    }


    /**
     * 获取指定天数之前或之后的日期，格式yyyy-MM-dd
     *
     * @param daysToAdd
     * @return
     */
    public static String getDatePlusDay(long daysToAdd) {
        LocalDate now = LocalDate.now();
        return now.plusDays(daysToAdd).toString();
    }


    /**
     * 判断date1是否在date2之前
     *
     * @param date1
     * @param date2
     * @return
     */
    public static boolean isBefore(Date date1, Date date2) {
        if (date1 == null || date2 == null) {
            return false;
        }
        Instant instant1 = date1.toInstant();
        Instant instant2 = date2.toInstant();
        ZoneId zoneId = ZoneId.systemDefault();
        return LocalDateTime.ofInstant(instant1, zoneId).toLocalDate().isBefore(LocalDateTime.ofInstant(instant2, zoneId).toLocalDate());
    }

    /**
     * 获取当前月份的总天数
     * @return
     */
    public static int getCurrentMonthDays() {
        YearMonth yearMonth = YearMonth.now();
        return yearMonth.lengthOfMonth();
    }

    /**
     * 获取指定年，月份的总天数
     * @param month
     * @return
     */
    public static int getMonthDays(int year, int month) {
        if (year < 0) {
            throw new IllegalArgumentException();
        }
        if (month <= 0 || month > 12) {
            throw new IllegalArgumentException();
        }
        YearMonth yearMonth = YearMonth.of(year, month);
        return yearMonth.lengthOfMonth();
    }


    /**
     * 获取当前时间,格式yyyy-MM-dd HH:mm:ss
     * @return
     */
    public static String getCurrentDateTime() {
        LocalDateTime dateTime = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        return formatter.format(dateTime);
    }

    /**
     * 获取当前秒数
     * @return
     */
    public static long getCurrentSeconds() {
        return System.currentTimeMillis() / 1000;
    }

    public static void main(String[] args) {
        System.out.println(getNowDate());
        System.out.println(isEqualsDay(new Date(), new Date()));
        System.out.println(isLeapYear(2004));
        System.out.println(isRepeatDay(9, 12));
        System.out.println(isRepeatDay(9, 8));
        System.out.println(getDatePlusDay(1));
        System.out.println(getDatePlusDay(-1));
        System.out.println(isBefore(new Date(999999), new Date()));
        System.out.println(getCurrentMonthDays());
        System.out.println(getMonthDays(1992, 2));
        System.out.println(getCurrentSeconds());
        System.out.println(getCurrentDateTime());
    }
}
