package com.xiaoyu.module.auth.controller;

import com.xiaoyu.api.auth.dto.LoginDTO;
import com.xiaoyu.api.auth.dto.RegisterDTO;
import com.xiaoyu.api.auth.vo.LoginVO;
import com.xiaoyu.common.core.result.Result;
import com.xiaoyu.module.auth.service.AuthService;
import jakarta.validation.Valid;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

/**
 * 认证 Controller
 */
@RestController
@RequestMapping("/auth")
@RequiredArgsConstructor
public class AuthController {

    private final AuthService authService;

    /**
     * 用户登录
     */
    @PostMapping("/login")
    public Result<LoginVO> login(@Valid @RequestBody LoginDTO loginDTO) {
        return Result.success(authService.login(
                loginDTO.getUsername(),
                loginDTO.getPassword(),
                loginDTO.getCaptchaId(),
                loginDTO.getCaptchaCode(),
                loginDTO.getRememberMe()
        ));
    }

    /**
     * 用户登出
     */
    @PostMapping("/logout")
    public Result<Void> logout() {
        authService.logout();
        return Result.success();
    }

    /**
     * 用户注册
     */
    @PostMapping("/register")
    public Result<Long> register(@Valid @RequestBody RegisterDTO registerDTO) {
        return Result.success(authService.register(
                registerDTO.getUsername(),
                registerDTO.getNickname(),
                registerDTO.getPassword(),
                registerDTO.getPhone(),
                registerDTO.getEmail()
        ));
    }

    /**
     * 刷新Token
     */
    @PostMapping("/refresh-token")
    public Result<LoginVO> refreshToken(@RequestHeader("Authorization") String authorization) {
        String token = authorization.replace("Bearer ", "");
        return Result.success(authService.refreshToken(token));
    }

    /**
     * 获取验证码
     */
    @GetMapping("/captcha")
    public Result<String> getCaptcha(@RequestParam("captchaId") String captchaId) {
        return Result.success(authService.getCaptcha(captchaId));
    }

    /**
     * 发送短信验证码
     */
    @GetMapping("/sms/{phone}")
    public Result<Void> sendSmsCode(@PathVariable("phone") String phone) {
        authService.sendSmsCode(phone);
        return Result.success();
    }

    /**
     * 发送邮箱验证码
     */
    @GetMapping("/email/{email}")
    public Result<Void> sendEmailCode(@PathVariable("email") String email) {
        authService.sendEmailCode(email);
        return Result.success();
    }
}