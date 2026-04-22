package com.xiaoyu.common.core.utils;

import com.xiaoyu.common.core.exception.ServiceException;

/**
 * 断言工具类
 * 用于参数校验和业务异常抛出，基于 Hutool 和 ServiceException 封装
 */
public class XiaoYuThrowUtil {

    /**
     * 抛出业务异常
     */
    public static void throwEx(String message) {
        throw new ServiceException(message);
    }

    /**
     * 抛出业务异常
     */
    public static void throwEx(Integer code, String message) {
        throw new ServiceException(code, message);
    }

    /**
     * 抛出业务异常
     */
    public static void throwEx(String message, Throwable cause) {
        throw new ServiceException(message, cause);
    }

    /**
     * 条件为true时抛出业务异常
     */
    public static void throwIf(boolean condition, String message) {
        if (condition) {
            throw new ServiceException(message);
        }
    }

    /**
     * 条件为true时抛出业务异常
     */
    public static void throwIf(boolean condition, Integer code, String message) {
        if (condition) {
            throw new ServiceException(code, message);
        }
    }

    /**
     * 条件为false时抛出业务异常
     */
    public static void throwIfFalse(boolean condition, String message) {
        if (!condition) {
            throw new ServiceException(message);
        }
    }

    /**
     * 条件为false时抛出业务异常
     */
    public static void throwIfFalse(boolean condition, Integer code, String message) {
        if (!condition) {
            throw new ServiceException(code, message);
        }
    }

    /**
     * 对象为null时抛出业务异常
     */
    public static void throwIfNull(Object obj, String message) {
        if (obj == null) {
            throw new ServiceException(message);
        }
    }

    /**
     * 对象为null时抛出业务异常
     */
    public static void throwIfNull(Object obj, Integer code, String message) {
        if (obj == null) {
            throw new ServiceException(code, message);
        }
    }

    /**
     * 对象不为null时抛出业务异常
     */
    public static void throwIfNotNull(Object obj, String message) {
        if (obj != null) {
            throw new ServiceException(message);
        }
    }

    /**
     * 字符串为空时抛出业务异常
     */
    public static void throwIfEmpty(CharSequence str, String message) {
        if (XiaoYuStrUtil.isEmpty(str)) {
            throw new ServiceException(message);
        }
    }

    /**
     * 字符串为空时抛出业务异常
     */
    public static void throwIfEmpty(CharSequence str, Integer code, String message) {
        if (XiaoYuStrUtil.isEmpty(str)) {
            throw new ServiceException(code, message);
        }
    }

    /**
     * 字符串不为空时抛出业务异常
     */
    public static void throwIfNotEmpty(CharSequence str, String message) {
        if (XiaoYuStrUtil.isNotEmpty(str)) {
            throw new ServiceException(message);
        }
    }

    /**
     * 字符串为空白时抛出业务异常
     */
    public static void throwIfBlank(CharSequence str, String message) {
        if (XiaoYuStrUtil.isBlank(str)) {
            throw new ServiceException(message);
        }
    }

    /**
     * 字符串为空白时抛出业务异常
     */
    public static void throwIfBlank(CharSequence str, Integer code, String message) {
        if (XiaoYuStrUtil.isBlank(str)) {
            throw new ServiceException(code, message);
        }
    }

    /**
     * 字符串不为空白时抛出业务异常
     */
    public static void throwIfNotBlank(CharSequence str, String message) {
        if (XiaoYuStrUtil.isNotBlank(str)) {
            throw new ServiceException(message);
        }
    }
}