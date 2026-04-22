package com.xiaoyu.common.core.result;

import lombok.Getter;

/**
 * 结果码枚举
 */
@Getter
public enum ResultCode {

    // 成功
    SUCCESS(200, "操作成功"),

    // 客户端错误 4xx
    FAIL(400, "操作失败"),
    PARAM_ERROR(400, "参数错误"),
    UNAUTHORIZED(401, "未授权"),
    FORBIDDEN(403, "禁止访问"),
    NOT_FOUND(404, "资源不存在"),
    METHOD_NOT_ALLOWED(405, "请求方法不支持"),

    // 服务端错误 5xx
    INTERNAL_SERVER_ERROR(500, "服务器内部错误"),
    SERVICE_UNAVAILABLE(503, "服务不可用"),

    // 业务错误 1xxx
    USER_NOT_FOUND(1001, "用户不存在"),
    USER_PASSWORD_ERROR(1002, "用户名或密码错误"),
    USER_DISABLED(1003, "用户已被禁用"),
    USER_LOCKED(1004, "用户已被锁定"),
    USER_EXISTS(1005, "用户已存在"),
    ROLE_NOT_FOUND(2001, "角色不存在"),
    ROLE_DISABLED(2002, "角色已被禁用"),
    DEPT_NOT_FOUND(3001, "部门不存在"),
    MENU_NOT_FOUND(4001, "菜单不存在"),
    TENANT_NOT_FOUND(5001, "租户不存在"),
    TENANT_DISABLED(5002, "租户已被禁用"),

    // 验证码错误
    CAPTCHA_ERROR(6001, "验证码错误"),
    CAPTCHA_EXPIRED(6002, "验证码已过期"),
    CAPTCHA_NOT_MATCH(6003, "验证码不匹配"),

    // 认证错误
    TOKEN_EXPIRED(7001, "Token已过期"),
    TOKEN_INVALID(7002, "Token无效"),
    TOKEN_NOT_FOUND(7003, "Token不存在"),

    // 文件错误
    FILE_UPLOAD_ERROR(8001, "文件上传失败"),
    FILE_NOT_FOUND(8002, "文件不存在"),
    FILE_TYPE_NOT_ALLOWED(8003, "文件类型不允许"),
    FILE_SIZE_EXCEEDED(8004, "文件大小超出限制");

    private final Integer code;
    private final String message;

    ResultCode(Integer code, String message) {
        this.code = code;
        this.message = message;
    }
}
