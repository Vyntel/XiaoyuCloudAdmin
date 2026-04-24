package com.xiaoyu.common.core.utils;

import cn.hutool.core.bean.BeanUtil;
import cn.hutool.core.bean.copier.CopyOptions;
import com.alibaba.fastjson2.JSON;
import com.alibaba.fastjson2.JSONObject;

import java.util.List;
import java.util.Map;
import java.util.function.Supplier;

/**
 * Bean工具类
 * 基于 Hutool 封装，提供常用的Bean操作功能
 * 使用XiaoYu前缀避免与Hutool冲突
 *
 * @author xiaoyu
 */
public class XiaoYuBeanUtil {

    /**
     * 对象转Map
     */
    public static Map<String, Object> toMap(Object bean) {
        if (bean == null) {
            return null;
        }
        return BeanUtil.beanToMap(bean);
    }

    /**
     * Map转对象
     */
    public static <T> T toBean(Map<String, Object> map, Class<T> clazz) {
        if (map == null || map.isEmpty()) {
            return null;
        }
        return BeanUtil.mapToBean(map, clazz, false, CopyOptions.create());
    }

    /**
     * 复制对象属性
     */
    public static void copyProps(Object source, Object target) {
        if (source == null || target == null) {
            return;
        }
        BeanUtil.copyProperties(source, target, CopyOptions.create());
    }

    /**
     * 复制对象并返回新对象
     */
    public static <T> T copy(Object source, Class<T> clazz) {
        if (source == null) {
            return null;
        }
        try {
            T target = clazz.getDeclaredConstructor().newInstance();
            copyProps(source, target);
            return target;
        } catch (Exception e) {
            return null;
        }
    }

    /**
     * 复制列表对象
     */
    public static <T, S> List<T> copyList(List<S> sourceList, Class<T> clazz) {
        if (sourceList == null || sourceList.isEmpty()) {
            return null;
        }
        return BeanUtil.copyToList(sourceList, clazz, CopyOptions.create());
    }

    /**
     * JSON转对象
     */
    public static <T> T fromJson(String json, Class<T> clazz) {
        if (json == null || json.isEmpty()) {
            return null;
        }
        return JSON.parseObject(json, clazz);
    }

    /**
     * 对象转JSON
     */
    public static String toJson(Object bean) {
        if (bean == null) {
            return null;
        }
        return JSON.toJSONString(bean);
    }

    /**
     * 对象转JSONObject
     */
    public static JSONObject toJsonObject(Object bean) {
        if (bean == null) {
            return null;
        }
        return JSONObject.from(bean);
    }

    /**
     * 创建并复制对象
     */
    public static <T> T copyAndCreate(Object source, Supplier<T> supplier) {
        if (source == null) {
            return null;
        }
        T target = supplier.get();
        copyProps(source, target);
        return target;
    }

    /**
     * 判断对象是否为空
     */
    public static boolean isEmpty(Object bean) {
        return bean == null;
    }

    /**
     * 判断对象是否不为空
     */
    public static boolean isNotEmpty(Object bean) {
        return !isEmpty(bean);
    }
}