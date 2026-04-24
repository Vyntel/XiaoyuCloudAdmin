package com.xiaoyu.module.auth.service.impl;

import cn.dev33.satoken.stp.StpUtil;
import cn.hutool.captcha.CaptchaUtil;
import cn.hutool.captcha.LineCaptcha;
import cn.hutool.core.util.IdUtil;
import cn.hutool.core.util.RandomUtil;
import cn.hutool.crypto.digest.BCrypt;
import com.xiaoyu.api.auth.vo.LoginVO;
import com.xiaoyu.api.system.api.SystemUserApi;
import com.xiaoyu.api.system.dto.UserDTO;
import com.xiaoyu.api.system.vo.UserVO;
import com.xiaoyu.common.core.result.Result;
import com.xiaoyu.module.auth.service.AuthService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.data.redis.core.StringRedisTemplate;
import org.springframework.stereotype.Service;

import java.util.concurrent.TimeUnit;

/**
 * 认证 Service 实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class AuthServiceImpl implements AuthService {

    private final SystemUserApi systemUserApi;
    private final StringRedisTemplate redisTemplate;

    /**
     * 验证码Redis Key前缀
     */
    private static final String CAPTCHA_KEY_PREFIX = "captcha:";
    /**
     * 短信验证码Redis Key前缀
     */
    private static final String SMS_CODE_KEY_PREFIX = "sms:";
    /**
     * 邮箱验证码Redis Key前缀
     */
    private static final String EMAIL_CODE_KEY_PREFIX = "email:";
    /**
     * 刷新Token Redis Key前缀
     */
    private static final String REFRESH_TOKEN_KEY_PREFIX = "refresh_token:";
    /**
     * 验证码有效期（分钟）
     */
    private static final int CAPTCHA_EXPIRE_MINUTES = 5;
    /**
     * 短信/邮箱验证码有效期（分钟）
     */
    private static final int CODE_EXPIRE_MINUTES = 10;

    /**
     * 用户登录
     */
    @Override
    public LoginVO login(String username, String password, String captchaId, String captchaCode, Boolean rememberMe) {
        // 1. 验证验证码
        validateCaptcha(captchaId, captchaCode);

        // 2. 获取用户信息（通过Feign API）
        Result<UserVO> userResult = systemUserApi.getUserByUsername(username);
        if (!userResult.isSuccess() || userResult.getData() == null) {
            throw new RuntimeException("用户名或密码错误");
        }
        UserVO userVO = userResult.getData();

        // 3. 验证密码
        if (!BCrypt.checkpw(password, userVO.getPassword())) {
            throw new RuntimeException("用户名或密码错误");
        }

        // 4. 检查用户状态
        if (userVO.getStatus() != null && userVO.getStatus() == 1) {
            throw new RuntimeException("账号已被禁用");
        }

        // 5. 使用 Sa-Token 登录
        StpUtil.login(userVO.getId(), rememberMe != null && rememberMe);
        String accessToken = StpUtil.getTokenValue();

        // 6. 生成刷新Token
        String refreshToken = generateRefreshToken(userVO.getId());

        // 7. 构建返回结果
        LoginVO loginVO = new LoginVO();
        loginVO.setAccessToken(accessToken);
        loginVO.setRefreshToken(refreshToken);
        loginVO.setTokenType("Bearer");
        loginVO.setExpiresIn(StpUtil.getTokenTimeout());
        loginVO.setUsername(userVO.getUsername());

        log.info("用户登录成功: {}", username);
        return loginVO;
    }

    /**
     * 用户登出
     */
    @Override
    public void logout() {
        Object loginId = StpUtil.getLoginId();
        StpUtil.logout();
        log.info("用户登出成功, userId: {}", loginId);
    }

    /**
     * 用户注册
     */
    @Override
    public Long register(String username, String nickname, String password, String phone, String email) {
        // 1. 检查用户名是否存在
        Result<UserVO> existResult = systemUserApi.getUserByUsername(username);
        if (existResult.isSuccess() && existResult.getData() != null) {
            throw new RuntimeException("用户名已存在");
        }

        // 2. 创建用户DTO
        UserDTO userDTO = new UserDTO();
        userDTO.setUsername(username);
        userDTO.setNickname(nickname != null ? nickname : username);
        userDTO.setPassword(BCrypt.hashpw(password));
        userDTO.setPhone(phone);
        userDTO.setEmail(email);
        userDTO.setStatus(0);

        // 3. 保存用户（通过Feign API）
        Result<Long> result = systemUserApi.createUser(userDTO);
        if (!result.isSuccess()) {
            throw new RuntimeException("用户注册失败: " + result.getMessage());
        }

        Long userId = result.getData();
        log.info("用户注册成功: {}, userId: {}", username, userId);
        return userId;
    }

    /**
     * 刷新Token
     */
    @Override
    public LoginVO refreshToken(String refreshToken) {
        // 1. 验证刷新Token
        String userIdStr = redisTemplate.opsForValue().get(REFRESH_TOKEN_KEY_PREFIX + refreshToken);
        if (userIdStr == null) {
            throw new RuntimeException("刷新Token已失效");
        }

        Long userId = Long.parseLong(userIdStr);

        // 2. 删除旧刷新Token
        redisTemplate.delete(REFRESH_TOKEN_KEY_PREFIX + refreshToken);

        // 3. 生成新Token
        StpUtil.login(userId);
        String newAccessToken = StpUtil.getTokenValue();
        String newRefreshToken = generateRefreshToken(userId);

        // 4. 返回结果
        LoginVO loginVO = new LoginVO();
        loginVO.setAccessToken(newAccessToken);
        loginVO.setRefreshToken(newRefreshToken);
        loginVO.setTokenType("Bearer");
        loginVO.setExpiresIn(StpUtil.getTokenTimeout());

        log.info("Token刷新成功, userId: {}", userId);
        return loginVO;
    }

    /**
     * 获取验证码
     */
    @Override
    public String getCaptcha(String captchaId) {
        // 生成图形验证码
        LineCaptcha captcha = CaptchaUtil.createLineCaptcha(120, 40, 4, 30);
        String code = captcha.getCode();
        String imageBase64 = captcha.getImageBase64();

        // 生成新的验证码ID
        String realCaptchaId = captchaId != null && !captchaId.isEmpty() ? captchaId : IdUtil.simpleUUID();

        // 存储到Redis
        redisTemplate.opsForValue().set(
            CAPTCHA_KEY_PREFIX + realCaptchaId,
            code.toLowerCase(),
            CAPTCHA_EXPIRE_MINUTES,
            TimeUnit.MINUTES
        );

        log.debug("生成验证码: {}", realCaptchaId);
        // 返回验证码图片和ID
        return imageBase64 + "|" + realCaptchaId;
    }

    /**
     * 发送短信验证码
     */
    @Override
    public void sendSmsCode(String phone) {
        // 生成6位数字验证码
        String code = RandomUtil.randomNumbers(6);

        // 存储到Redis
        redisTemplate.opsForValue().set(
            SMS_CODE_KEY_PREFIX + phone,
            code,
            CODE_EXPIRE_MINUTES,
            TimeUnit.MINUTES
        );

        // TODO: 集成短信发送服务
        log.info("发送短信验证码到: {}, code: {}", phone, code);
    }

    /**
     * 发送邮箱验证码
     */
    @Override
    public void sendEmailCode(String email) {
        // 生成6位数字验证码
        String code = RandomUtil.randomNumbers(6);

        // 存储到Redis
        redisTemplate.opsForValue().set(
            EMAIL_CODE_KEY_PREFIX + email,
            code,
            CODE_EXPIRE_MINUTES,
            TimeUnit.MINUTES
        );

        // TODO: 集成邮件发送服务
        log.info("发送邮箱验证码到: {}, code: {}", email, code);
    }

    /**
     * 验证验证码
     */
    private void validateCaptcha(String captchaId, String captchaCode) {
        if (captchaId == null || captchaId.isEmpty()) {
            throw new RuntimeException("请输入验证码");
        }
        if (captchaCode == null || captchaCode.isEmpty()) {
            throw new RuntimeException("请输入验证码");
        }

        String code = redisTemplate.opsForValue().get(CAPTCHA_KEY_PREFIX + captchaId);
        if (code == null) {
            throw new RuntimeException("验证码已失效");
        }
        if (!code.equalsIgnoreCase(captchaCode)) {
            // 验证失败后删除验证码
            redisTemplate.delete(CAPTCHA_KEY_PREFIX + captchaId);
            throw new RuntimeException("验证码错误");
        }

        // 验证成功后删除验证码
        redisTemplate.delete(CAPTCHA_KEY_PREFIX + captchaId);
    }

    /**
     * 生成刷新Token
     */
    private String generateRefreshToken(Long userId) {
        String refreshToken = IdUtil.simpleUUID();
        redisTemplate.opsForValue().set(
            REFRESH_TOKEN_KEY_PREFIX + refreshToken,
            userId.toString(),
            7,
            TimeUnit.DAYS
        );
        return refreshToken;
    }
}
