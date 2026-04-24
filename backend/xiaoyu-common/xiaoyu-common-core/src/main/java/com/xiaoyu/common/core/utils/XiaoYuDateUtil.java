package com.xiaoyu.common.core.utils;

import cn.hutool.core.date.LocalDateTimeUtil;

import java.time.LocalDate;
import java.time.LocalDateTime;
import java.time.format.DateTimeFormatter;
import java.util.Date;

/**
 * Date工具类
 * 基于 Hutool 封装，提供常用的日期操作功能
 * 使用XiaoYu前缀避免与Hutool冲突
 *
 * @author xiaoyu
 */
public class XiaoYuDateUtil {

    /**
     * 默认日期格式
     */
    public static final String PATTERN_DEFAULT = "yyyy-MM-dd HH:mm:ss";
    public static final String PATTERN_DATE = "yyyy-MM-dd";
    public static final String PATTERN_TIME = "HH:mm:ss";
    public static final String PATTERN_MONTH = "yyyy-MM";
    public static final String PATTERN_YEAR = "yyyy";
    public static final String PATTERN_DATETIME_MINI = "yyyyMMddHHmmss";
    public static final String PATTERN_DATE_MINI = "yyyyMMdd";

    /**
     * 默认 DateTimeFormatter
     */
    public static final DateTimeFormatter FORMATTER_DEFAULT = DateTimeFormatter.ofPattern(PATTERN_DEFAULT);
    public static final DateTimeFormatter FORMATTER_DATE = DateTimeFormatter.ofPattern(PATTERN_DATE);
    public static final DateTimeFormatter FORMATTER_TIME = DateTimeFormatter.ofPattern(PATTERN_TIME);
    public static final DateTimeFormatter FORMATTER_MONTH = DateTimeFormatter.ofPattern(PATTERN_MONTH);
    public static final DateTimeFormatter FORMATTER_YEAR = DateTimeFormatter.ofPattern(PATTERN_YEAR);
    public static final DateTimeFormatter FORMATTER_DATETIME_MINI = DateTimeFormatter.ofPattern(PATTERN_DATETIME_MINI);
    public static final DateTimeFormatter FORMATTER_DATE_MINI = DateTimeFormatter.ofPattern(PATTERN_DATE_MINI);

    /**
     * 格式化日期时间
     */
    public static String format(LocalDateTime dateTime) {
        return format(dateTime, PATTERN_DEFAULT);
    }

    /**
     * 格式化日期时间
     */
    public static String format(LocalDateTime dateTime, String pattern) {
        if (dateTime == null) {
            return null;
        }
        return dateTime.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 格式化日期
     */
    public static String format(LocalDate date) {
        return format(date, PATTERN_DATE);
    }

    /**
     * 格式化日期
     */
    public static String format(LocalDate date, String pattern) {
        if (date == null) {
            return null;
        }
        return date.format(DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 解析日期时间字符串
     */
    public static LocalDateTime parseDateTime(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }
        return LocalDateTime.parse(dateStr, FORMATTER_DEFAULT);
    }

    /**
     * 解析日期时间字符串
     */
    public static LocalDateTime parseDateTime(String dateStr, String pattern) {
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }
        return LocalDateTime.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 解析日期字符串
     */
    public static LocalDate parseDate(String dateStr) {
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }
        return LocalDate.parse(dateStr, FORMATTER_DATE);
    }

    /**
     * 解析日期字符串
     */
    public static LocalDate parseDate(String dateStr, String pattern) {
        if (dateStr == null || dateStr.isEmpty()) {
            return null;
        }
        return LocalDate.parse(dateStr, DateTimeFormatter.ofPattern(pattern));
    }

    /**
     * 获取当前日期时间字符串
     */
    public static String now() {
        return format(LocalDateTime.now());
    }

    /**
     * 获取当前日期字符串
     */
    public static String today() {
        return format(LocalDate.now());
    }

    /**
     * Date转LocalDateTime
     */
    public static LocalDateTime toLocalDateTime(Date date) {
        if (date == null) {
            return null;
        }
        return LocalDateTimeUtil.of(date);
    }

    /**
     * LocalDateTime转Date
     */
    public static Date toDate(LocalDateTime dateTime) {
        if (dateTime == null) {
            return null;
        }
        return Date.from(dateTime.atZone(java.time.ZoneId.systemDefault()).toInstant());
    }

    /**
     * LocalDate转Date
     */
    public static Date toDate(LocalDate date) {
        if (date == null) {
            return null;
        }
        return Date.from(date.atStartOfDay(java.time.ZoneId.systemDefault()).toInstant());
    }

    /**
     * 判断日期是否在范围内
     */
    public static boolean isBetween(LocalDateTime date, LocalDateTime start, LocalDateTime end) {
        if (date == null || start == null || end == null) {
            return false;
        }
        return date.isAfter(start) && date.isBefore(end);
    }

    /**
     * 判断日期是否在范围内（包含边界）
     */
    public static boolean isBetweenOrEquals(LocalDateTime date, LocalDateTime start, LocalDateTime end) {
        if (date == null || start == null || end == null) {
            return false;
        }
        return !date.isBefore(start) && !date.isAfter(end);
    }

    /**
     * 获取年龄
     */
    public static int getAge(LocalDate birthDay) {
        if (birthDay == null) {
            return 0;
        }
        LocalDate now = LocalDate.now();
        int age = now.getYear() - birthDay.getYear();
        if (now.getDayOfYear() < birthDay.getDayOfYear()) {
            age--;
        }
        return age;
    }

    /**
     * 判断是否为今天
     */
    public static boolean isToday(LocalDate date) {
        if (date == null) {
            return false;
        }
        return LocalDate.now().equals(date);
    }

    /**
     * 判断是否为今天
     */
    public static boolean isToday(LocalDateTime dateTime) {
        if (dateTime == null) {
            return false;
        }
        return LocalDate.now().equals(dateTime.toLocalDate());
    }

    /**
     * 获取一天的开始时间
     */
    public static LocalDateTime getDayStart(LocalDate date) {
        if (date == null) {
            return null;
        }
        return date.atStartOfDay();
    }

    /**
     * 获取一天的结束时间
     */
    public static LocalDateTime getDayEnd(LocalDate date) {
        if (date == null) {
            return null;
        }
        return date.atTime(23, 59, 59, 999999999);
    }

    /**
     * 转换为时间戳（毫秒）
     */
    public static long toEpochMilli(LocalDateTime dateTime) {
        if (dateTime == null) {
            return 0;
        }
        return dateTime.atZone(java.time.ZoneId.systemDefault()).toInstant().toEpochMilli();
    }

    /**
     * 从时间戳转换为LocalDateTime
     */
    public static LocalDateTime fromEpochMilli(long epochMilli) {
        return LocalDateTime.ofInstant(java.time.Instant.ofEpochMilli(epochMilli), java.time.ZoneId.systemDefault());
    }
}