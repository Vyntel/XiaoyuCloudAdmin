package com.xiaoyu.common.security.config;

import cn.dev33.satoken.interceptor.SaInterceptor;
import cn.dev33.satoken.stp.StpUtil;
import lombok.extern.slf4j.Slf4j;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

@Slf4j
@Configuration
public class SaTokenConfiguration implements WebMvcConfigurer {

    public SaTokenConfiguration() {
        log.info("╔═══════════════════════════════════════════════════════════════╗");
        log.info("║          小羽快速开发框架 - 安全认证模块已启动                      ║");
        log.info("╚═══════════════════════════════════════════════════════════════╝");
    }

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        registry.addInterceptor(new SaInterceptor(handle -> StpUtil.checkLogin()))
                .addPathPatterns("/**")
                .excludePathPatterns("/login", "/logout", "/captcha", "/health", "/actuator/**");
    }
}