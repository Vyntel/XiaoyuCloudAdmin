package com.xiaoyu.module.auth.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import com.xiaoyu.api.auth.vo.LoginVO;
import com.xiaoyu.module.auth.service.AuthService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.UUID;

/**
 * 认证 Service 实现
 */
@Slf4j
@Service
public class AuthServiceImpl implements AuthService {

    /**
     * 用户登录
     */
    @Override
    public LoginVO login(String username, String password, String captchaId, String captchaCode, Boolean rememberMe) {
        // TODO: 实现验证码校验
        // TODO: 实现密码校验
        
        // 使用 Sa-Token 登录
        StpUtil.login(username);
        String token = StpUtil.getTokenValue();
        
        // 构建返回结果
        LoginVO loginVO = new LoginVO();
        loginVO.setAccessToken(token);
        loginVO.setRefreshToken(token); // TODO: 生成独立的 refresh token
        loginVO.setTokenType("Bearer");
        loginVO.setExpiresIn(StpUtil.getTokenTimeout());
        loginVO.setUsername(username);
        // TODO: 设置用户其他信息
        
        log.info("用户登录成功: {}", username);
        return loginVO;
    }

    /**
     * 用户登出
     */
    @Override
    public void logout() {
        StpUtil.logout();
        log.info("用户登出成功");
    }

    /**
     * 用户注册
     */
    @Override
    public Long register(String username, String nickname, String password, String phone, String email) {
        // TODO: 实现用户注册逻辑
        // 1. 检查用户名是否存在
        // 2. 加密密码
        // 3. 保存用户信息
        // 4. 返回用户ID
        
        log.info("用户注册: {}", username);
        return 1L; // TODO: 返回实际用户ID
    }

    /**
     * 刷新Token
     */
    @Override
    public LoginVO refreshToken(String refreshToken) {
        // TODO: 实现 token 刷新逻辑
        LoginVO loginVO = new LoginVO();
        loginVO.setAccessToken(refreshToken);
        loginVO.setTokenType("Bearer");
        return loginVO;
    }

    /**
     * 获取验证码
     */
    @Override
    public String getCaptcha(String captchaId) {
        // TODO: 实现验证码生成逻辑
        // 返回验证码图片的 base64 编码
        return "data:image/png;base64,iVBORw0KGgoAAAANSUhEUgAAAAEAAAABCAYAAAAfFcSJAAAADUlEQVR42mNk+M9QDwADhgGAWjR9awAAAABJRU5ErkJggg==";
    }

    /**
     * 发送短信验证码
     */
    @Override
    public void sendSmsCode(String phone) {
        // TODO: 实现短信验证码发送逻辑
        log.info("发送短信验证码到: {}", phone);
    }

    /**
     * 发送邮箱验证码
     */
    @Override
    public void sendEmailCode(String email) {
        // TODO: 实现邮箱验证码发送逻辑
        log.info("发送邮箱验证码到: {}", email);
    }
}
