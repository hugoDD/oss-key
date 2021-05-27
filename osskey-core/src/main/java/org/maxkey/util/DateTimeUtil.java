package org.maxkey.util;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.ChronoUnit;
import java.time.temporal.TemporalAdjusters;

/**
 * 时间工具类(包括LocalDate,LocalDateTime,Date)
 *
 * @author wangJk
 * @date 2018年6月13日
 */
public class DateTimeUtil {
    /**
     * yyyy-MM-dd
     */
    public static final String DAY_FORMAT = "yyyy-MM-dd";
    /**
     * yyyy-MM-dd HH:mm:ss
     */
    public static final String FULL_FORMAT = "yyyy-MM-dd HH:mm:ss";

    /**
     * 获得当前时间的yyyy-MM-dd格式字符串
     *
     * @return String
     */
    public static String getCurrentDate() {
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd");
        LocalDate today = LocalDate.now();
        String nowDate = today.format(df);
        return nowDate;
    }

    /**
     * LocalDate转化为指定格式字符串
     *
     * @param fromDate
     * @param dateFormat
     * @return
     */
    public static String getLocalDate(LocalDate fromDate, String dateFormat) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(dateFormat);
        if (fromDate != null) {
            String dateStr = fromDate.format(df);
            return dateStr;
        }
        return null;

    }

    public static String getLocalDateTime(LocalDateTime fromDateTime, String dateTimeFotmat) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(dateTimeFotmat);
        if (fromDateTime != null) {
            String localTime = fromDateTime.format(df);
            return localTime;
        }
        return null;

    }

    /**
     * 获得一年后的日期格式字符串
     */
    public static String getOneYearLaterDate(String beginDate, String dateFormat) {
        LocalDate fromDate = fromString2LocalDate(beginDate, dateFormat);
        if (fromDate != null) {
            LocalDate toDate = fromDate.plus(1, ChronoUnit.YEARS);
            return getLocalDate(toDate, dateFormat);
        }
        return null;

    }

    /**
     * 时间格式字符串转化为指定格式的时间
     *
     * @param beginDate
     * @param dateFormat
     * @return
     */
    public static LocalDate fromString2LocalDate(String beginDate, String dateFormat) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(dateFormat);
        try {
            LocalDate fromDate = LocalDate.parse(beginDate, df);
            return fromDate;
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * 时间格式字符串转化为指定格式的时间
     *
     * @param beginDateTime
     * @param dateFormat
     * @return
     */
    public static LocalDateTime fromString2LocalDateTime(String beginDateTime, String dateFormat) {
        DateTimeFormatter df = DateTimeFormatter.ofPattern(dateFormat);
        try {
            LocalDateTime fromDateTime = LocalDateTime.parse(beginDateTime, df);
            return fromDateTime;
        } catch (Exception e) {
            return null;
        }

    }

    /**
     * 获得毫秒数
     *
     * @param localDateTime
     * @return
     */
    public static long getTimestampOfDateTime(LocalDateTime localDateTime) {
        ZoneId zone = ZoneId.systemDefault();
        Instant instant = localDateTime.atZone(zone).toInstant();
        return instant.toEpochMilli();
    }


    /**
     * 根据出生日期(yyyy-MM-dd)字符串计算年龄
     *
     * @param birthDay
     * @return
     */
    public static Integer getAgeByBirthDay(String birthDay) {
        LocalDate birthDate = fromString2LocalDate(birthDay, DAY_FORMAT);
        LocalDate currentDate = LocalDate.now();
        if (birthDate != null) {
            //判断birthDay是否合法
            if (currentDate.isBefore(birthDate)) {
                return 0;
            } else {
                int age = birthDate.until(currentDate).getYears();
                return age;
            }

        } else {
            return null;
        }

    }

    /**
     * Long类型时间戳转化为LocalDateTime
     *
     * @param dateTimeLong
     * @return
     */
    public static LocalDateTime fromLong2LocalDateTime(Long dateTimeLong) {
        LocalDateTime dateTime = LocalDateTime.ofInstant(Instant.ofEpochMilli(dateTimeLong), ZoneId.systemDefault());
        return dateTime;
    }

    /**
     * 获取本月第一天
     *
     * @return
     * @author wangjk
     * @date 2019年6月12日
     */
    public static LocalDate getFirstDayOfCurrentMonth() {
        LocalDate currentDay = LocalDate.now();
        return currentDay.with(TemporalAdjusters.firstDayOfMonth());
    }

    /**
     * 获取本月最后一天
     *
     * @return
     * @author wangjk
     * @date 2019年6月12日
     */
    public static LocalDate getLastDayOfCurrentMonth() {
        LocalDate currentDay = LocalDate.now();
        return currentDay.with(TemporalAdjusters.lastDayOfMonth());
    }

    /**
     * 获取当天开始时间 2019-06-12 00:00:00
     *
     * @return
     * @author wangjk
     * @date 2019年6月12日
     */
    public static LocalDateTime getTodayBeginTime() {
        LocalDate currentDay = LocalDate.now();
        return LocalDateTime.of(currentDay, LocalTime.MIN);
    }

    /**
     * 获取当天结束时间 2019-06-12 23:59:59
     *
     * @return
     * @author wangjk
     * @date 2019年6月12日
     */
    public static LocalDateTime getTodayEndTime() {
        LocalDate currentDay = LocalDate.now();
        return LocalDateTime.of(currentDay, LocalTime.MAX);
    }

    /**
     * 获取本周开始时间 2019-06-10 00:00:00
     *
     * @return
     * @author wangjk
     * @date 2019年6月12日
     */
    public static LocalDateTime getWeekBeginTime(boolean startSunDayOfWeek) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        if (startSunDayOfWeek) {
            return currentDateTime.minusWeeks(1).with(DayOfWeek.SUNDAY)
                    .withHour(0).withMinute(0).withSecond(0).withNano(0);
        }
        return currentDateTime.with(DayOfWeek.MONDAY)
                .withHour(0).withMinute(0).withSecond(0).withNano(0);
    }

    /**
     * 获取本周开始时间 2019-06-10 00:00:00
     *
     * @return
     * @author wangjk
     * @date 2019年6月12日
     */
    public static String getWeekBeginTimeString(boolean startSunDayOfWeek) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        if (startSunDayOfWeek) {
            LocalDateTime weekBeginDateTime = currentDateTime.minusWeeks(1).with(DayOfWeek.SUNDAY)
                    .withHour(0).withMinute(0).withSecond(0).withNano(0);
            return getLocalDateTime(weekBeginDateTime, FULL_FORMAT);
        } else {
            LocalDateTime weekBeginDateTime = currentDateTime.with(DayOfWeek.MONDAY)
                    .withHour(0).withMinute(0).withSecond(0).withNano(0);
            return getLocalDateTime(weekBeginDateTime, FULL_FORMAT);
        }

    }

    /**
     * 获取本周结束时间 2019-06-16 23:59:59
     *
     * @return
     * @author wangjk
     * @date 2019年6月12日
     */
    public static LocalDateTime getWeekEndTime(boolean startSunDayOfWeek) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        if (startSunDayOfWeek) {
            return currentDateTime.with(DayOfWeek.SATURDAY)
                    .withHour(23).withMinute(59).withSecond(59).withNano(999999999);
        } else {
            return currentDateTime.with(DayOfWeek.SUNDAY)
                    .withHour(23).withMinute(59).withSecond(59).withNano(999999999);
        }
    }

    /**
     * 获取本周结束时间字符串 2019-06-16 23:59:59
     *
     * @return
     * @author wangjk
     * @date 2019年6月12日
     */
    public static String getWeekEndTimeString(boolean startSunDayOfWeek) {
        LocalDateTime currentDateTime = LocalDateTime.now();
        if(startSunDayOfWeek){
            LocalDateTime weekEndDateTime = currentDateTime.with(DayOfWeek.SATURDAY)
                    .withHour(23).withMinute(59).withSecond(59).withNano(999999999);
            return getLocalDateTime(weekEndDateTime, FULL_FORMAT);
        }else {
            LocalDateTime weekEndDateTime = currentDateTime.with(DayOfWeek.SUNDAY)
                    .withHour(23).withMinute(59).withSecond(59).withNano(999999999);
            return getLocalDateTime(weekEndDateTime, FULL_FORMAT);
        }
    }

}
