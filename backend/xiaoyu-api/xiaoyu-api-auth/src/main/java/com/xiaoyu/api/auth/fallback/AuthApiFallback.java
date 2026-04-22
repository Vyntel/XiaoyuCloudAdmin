package com.xiaoyu.api.auth.fallback;

import com.xiaoyu.api.auth.api.AuthApi;
import com.xiaoyu.api.auth.dto.LoginDTO;
import com.xiaoyu.api.auth.dto.RegisterDTO;
import com.xiaoyu.api.auth.vo.LoginVO;
import com.xiaoyu.common.core.result.Result;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Component;

/**
 * 认证授权 API 降级实现
 */
@Slf4j
@Component
public class AuthApiFallback implements AuthApi {

    @Override
    public Result<LoginVO> login(LoginDTO loginDTO) {
        log.warn("AuthApi.login 调用降级, username: {}", loginDTO.getUsername());
        return Result.fail("服务降级：认证服务不可用");
    }

    @Override
    public Result<Void> logout() {
        log.warn("AuthApi.logout 调用降级");
        return Result.fail("服务降级：认证服务不可用");
    }

    @Override
    public Result<Long> register(RegisterDTO registerDTO) {
        log.warn("AuthApi.register 调用降级, username: {}", registerDTO.getUsername());
        return Result.fail("服务降级：认证服务不可用");
    }

    @Override
    public Result<LoginVO> refreshToken(String authorization) {
        log.warn("AuthApi.refreshToken 调用降级");
        return Result.fail("服务降级：认证服务不可用");
    }

    @Override
    public Result<String> getCaptcha(String captchaId) {
        log.warn("AuthApi.getCaptcha 调用降级, captchaId: {}", captchaId);
        return Result.fail("服务降级：认证服务不可用");
    }

    @Override
    public Result<Void> sendSmsCode(String phone) {
        log.warn("AuthApi.sendSmsCode 调用降级, phone: {}", phone);
        return Result.fail("服务降级：认证服务不可用");
    }

    @Override
    public Result<Void> sendEmailCode(String email) {
        log.warn("AuthApi.sendEmailCode 调用降级, email: {}", email);
        return Result.fail("服务降级：认证服务不可用");
    }
}
