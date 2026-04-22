package com.xiaoyu.common.core.utils;

import cn.hutool.core.util.StrUtil;

/**
 * String工具类
 * 基于 Hutool 封装，提供常用的字符串操作功能
 * 使用XiaoYu前缀避免与Hutool冲突
 *
 * @author xiaoyu
 */
public class XiaoYuStrUtil {

    /**
     * 判断字符串是否为空
     */
    public static boolean isEmpty(CharSequence str) {
        return StrUtil.isEmpty(str);
    }

    /**
     * 判断字符串是否不为空
     */
    public static boolean isNotEmpty(CharSequence str) {
        return !isEmpty(str);
    }

    /**
     * 判断字符串是否为空或空白
     */
    public static boolean isBlank(CharSequence str) {
        return StrUtil.isBlank(str);
    }

    /**
     * 判断字符串是否不为空且非空白
     */
    public static boolean isNotBlank(CharSequence str) {
        return !isBlank(str);
    }

    /**
     * 字符串格式化
     */
    public static String format(CharSequence template, Object... params) {
        return StrUtil.format(template, params);
    }

    /**
     * 驼峰转下划线
     */
    public static String toUnderlineCase(CharSequence camelCase) {
        return StrUtil.toUnderlineCase(camelCase);
    }

    /**
     * 下划线转驼峰
     */
    public static String toCamelCase(CharSequence underlineCase) {
        return StrUtil.toCamelCase(underlineCase);
    }

    /**
     * 判断字符串是否包含指定字符串
     */
    public static boolean contains(CharSequence str, CharSequence searchStr) {
        if (isEmpty(str) || isEmpty(searchStr)) {
            return false;
        }
        return str.toString().contains(searchStr.toString());
    }

    /**
     * 判断字符串是否以指定字符串开头
     */
    public static boolean startsWith(CharSequence str, CharSequence prefix) {
        if (isEmpty(str) || isEmpty(prefix)) {
            return false;
        }
        return str.toString().startsWith(prefix.toString());
    }

    /**
     * 判断字符串是否以指定字符串结尾
     */
    public static boolean endsWith(CharSequence str, CharSequence suffix) {
        if (isEmpty(str) || isEmpty(suffix)) {
            return false;
        }
        return str.toString().endsWith(suffix.toString());
    }

    /**
     * 截取字符串
     */
    public static String substring(CharSequence str, int start, int end) {
        if (isEmpty(str)) {
            return "";
        }
        return StrUtil.sub(str, start, end);
    }

    /**
     * 字符串脱敏
     */
    public static String desensitize(CharSequence str, int prefix, int suffix) {
        if (isEmpty(str)) {
            return "";
        }
        String s = str.toString();
        if (s.length() <= prefix + suffix) {
            return s;
        }
        String prefixStr = StrUtil.sub(s, 0, prefix);
        String suffixStr = StrUtil.sub(s, s.length() - suffix, s.length());
        return prefixStr + "*".repeat(Math.max(1, s.length() - prefix - suffix)) + suffixStr;
    }

    /**
     * 手机号脱敏
     */
    public static String desensitizePhone(CharSequence phone) {
        return desensitize(phone, 3, 4);
    }

    /**
     * 身份证号脱敏
     */
    public static String desensitizeIdCard(CharSequence idCard) {
        return desensitize(idCard, 4, 4);
    }

    /**
     * 邮箱脱敏
     */
    public static String desensitizeEmail(CharSequence email) {
        if (isEmpty(email)) {
            return "";
        }
        String s = email.toString();
        int atIndex = s.indexOf("@");
        if (atIndex <= 0) {
            return s;
        }
        String prefix = s.substring(0, atIndex);
        String suffix = s.substring(atIndex);
        if (prefix.length() <= 2) {
            return prefix.charAt(0) + "***" + suffix;
        }
        return prefix.charAt(0) + "***" + prefix.charAt(prefix.length() - 1) + suffix;
    }

    /**
     * 获取安全的字符串
     */
    public static String getOrDefault(CharSequence str, CharSequence defaultStr) {
        return isEmpty(str) ? (defaultStr == null ? "" : defaultStr.toString()) : str.toString();
    }
}