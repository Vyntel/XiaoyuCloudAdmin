package com.xiaoyu.common.core.annotation;

import java.lang.annotation.*;

/**
 * 权限校验注解
 * 用于标注需要权限校验的方法
 * 
 * 使用方式:
 * @SaCheckPermission("system:user:add")
 * public Result<Void> createUser(...) {}
 */
@Target({ElementType.METHOD, ElementType.TYPE})
@Retention(RetentionPolicy.RUNTIME)
@Documented
public @interface SaCheckPermission {

    /**
     * 权限标识列表
     * 支持多个权限，任一匹配即可访问
     */
    String[] value() default {};

    /**
     * 权限标识
     * 与value相同，简洁写法
     */
    String code() default "";

    /**
     * 逻辑运算符
     * AND: 所有权限都需要
     * OR: 任一权限即可（默认）
     */
    Logical logical() default Logical.OR;

    /**
     * 登录用户必填校验
     */
    boolean requiredLogin() default true;

    /**
     * 逻辑运算符
     */
    enum Logical {
        /** 所有权限都需要 */
        AND,
        /** 任一权限即可 */
        OR
    }
}