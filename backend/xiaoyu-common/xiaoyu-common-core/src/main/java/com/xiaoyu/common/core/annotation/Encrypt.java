package com.xiaoyu.common.core.annotation;

import java.lang.annotation.*;

/**
 * 加密注解
 */
@Target({ElementType.FIELD, ElementType.PARAMETER})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface Encrypt {

    /**
     * 加密类型
     */
    String type() default "AES";
}