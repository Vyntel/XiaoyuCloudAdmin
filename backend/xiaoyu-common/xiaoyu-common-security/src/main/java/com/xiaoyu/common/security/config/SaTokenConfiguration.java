package com.xiaoyu.common.security.config;

import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * Sa-Token 安全认证配置
 */
@Slf4j
@Configuration
@ConfigurationProperties(prefix = "sa-token")
public class SaTokenConfiguration implements WebMvcConfigurer {

    /**
     * 排除路径列表
     */
    private List<String> excludes = new ArrayList<>();

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludeList = new ArrayList<>();
        if (excludes != null && !excludes.isEmpty()) {
            excludeList.addAll(excludes);
        } else {
            // 默认排除路径
            excludeList.add("/login");
            excludeList.add("/logout");
            excludeList.add("/captcha");
            excludeList.add("/health");
            excludeList.add("/actuator/**");
            excludeList.add("/doc.html");
            excludeList.add("/swagger-ui.html");
            excludeList.add("/swagger-ui/**");
            excludeList.add("/favicon.ico");
        }

        registry.addInterceptor(new cn.dev33.satoken.interceptor.SaInterceptor(handle -> cn.dev33.satoken.stp.StpUtil.checkLogin()))
                .addPathPatterns("/**")
                .excludePathPatterns(excludeList.toArray(new String[0]));

        log.info("╔═══════════════════════════════════════════════════════════════╗");
        log.info("║          小羽快速开发框架 - 安全认证模块已启动                      ║");
        log.info("║  排除路径: {}  ║", excludeList);
        log.info("╚═══════════════════════════════════════════════════════════════╝");
    }
}