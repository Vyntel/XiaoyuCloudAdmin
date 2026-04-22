package com.xiaoyu.api.auth.api;

import com.xiaoyu.api.auth.dto.LoginDTO;
import com.xiaoyu.api.auth.dto.RegisterDTO;
import com.xiaoyu.api.auth.vo.LoginVO;
import com.xiaoyu.api.auth.fallback.AuthApiFallback;
import com.xiaoyu.common.core.result.Result;
import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 认证授权 Feign API
 */
@FeignClient(
        name = "xiaoyu-auth",
        path = "/auth",
        fallback = AuthApiFallback.class
)
public interface AuthApi {

    /**
     * 用户登录
     */
    @PostMapping("/login")
    Result<LoginVO> login(@RequestBody LoginDTO loginDTO);

    /**
     * 用户登出
     */
    @PostMapping("/logout")
    Result<Void> logout();

    /**
     * 用户注册
     */
    @PostMapping("/register")
    Result<Long> register(@RequestBody RegisterDTO registerDTO);

    /**
     * 刷新Token
     */
    @PostMapping("/refresh-token")
    Result<LoginVO> refreshToken(@RequestHeader("Authorization") String authorization);

    /**
     * 获取验证码
     */
    @GetMapping("/captcha")
    Result<String> getCaptcha(@RequestParam("captchaId") String captchaId);

    /**
     * 发送短信验证码
     */
    @GetMapping("/sms/{phone}")
    Result<Void> sendSmsCode(@PathVariable("phone") String phone);

    /**
     * 发送邮箱验证码
     */
    @GetMapping("/email/{email}")
    Result<Void> sendEmailCode(@PathVariable("email") String email);
}