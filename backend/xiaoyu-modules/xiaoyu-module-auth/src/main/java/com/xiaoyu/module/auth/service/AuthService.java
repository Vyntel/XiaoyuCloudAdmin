package com.xiaoyu.module.auth.service;

import com.xiaoyu.api.auth.vo.LoginVO;

/**
 * 认证 Service
 */
public interface AuthService {

    /**
     * 用户登录
     */
    LoginVO login(String username, String password, String captchaId, String captchaCode, Boolean rememberMe);

    /**
     * 用户登出
     */
    void logout();

    /**
     * 用户注册
     */
    Long register(String username, String nickname, String password, String phone, String email);

    /**
     * 刷新Token
     */
    LoginVO refreshToken(String refreshToken);

    /**
     * 获取验证码
     */
    String getCaptcha(String captchaId);

    /**
     * 发送短信验证码
     */
    void sendSmsCode(String phone);

    /**
     * 发送邮箱验证码
     */
    void sendEmailCode(String email);
}