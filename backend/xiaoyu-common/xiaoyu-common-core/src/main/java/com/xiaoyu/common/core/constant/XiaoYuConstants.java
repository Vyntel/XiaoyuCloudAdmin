package com.xiaoyu.common.core.constant;

/**
 * 小羽框架常量
 */
public interface XiaoYuConstants {

    /**
     * 超级管理员ID
     */
    Long SUPER_ADMIN_ID = 1L;

    /**
     * 超级管理员角色编码
     */
    String SUPER_ADMIN_ROLE_CODE = "super_admin";

    /**
     * 租户ID请求头
     */
    String TENANT_ID_HEADER = "X-Tenant-Id";

    /**
     * 用户ID请求头
     */
    String USER_ID_HEADER = "X-User-Id";

    /**
     * 用户名请求头
     */
    String USERNAME_HEADER = "X-Username";

    /**
     * 角色ID请求头
     */
    String ROLE_IDS_HEADER = "X-Role-Ids";

    /**
     * 角色编码请求头
     */
    String ROLE_CODES_HEADER = "X-Role-Codes";

    /**
     * 数据权限请求头
     */
    String DATA_SCOPE_HEADER = "X-Data-Scope";

    /**
     * 请求ID
     */
    String REQUEST_ID_HEADER = "X-Request-Id";

    /**
     * 默认租户ID
     */
    Long DEFAULT_TENANT_ID = 1L;

    /**
     * 验证码有效期（分钟）
     */
    int CAPTCHA_EXPIRE_MINUTES = 5;

    /**
     * Token有效期（小时）
     */
    int TOKEN_EXPIRE_HOURS = 24;

    /**
     * 刷新Token有效期（小时）
     */
    int REFRESH_TOKEN_EXPIRE_HOURS = 7 * 24;

    /**
     * 登录失败最大次数
     */
    int LOGIN_FAIL_MAX_COUNT = 5;

    /**
     * 登录失败锁定时间（分钟）
     */
    int LOGIN_FAIL_LOCK_MINUTES = 30;
}
