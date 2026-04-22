package com.xiaoyu.common.security.config;

import lombok.Data;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.ConfigurationProperties;
import org.springframework.context.annotation.Configuration;
import org.springframework.web.servlet.config.annotation.InterceptorRegistry;
import org.springframework.web.servlet.config.annotation.WebMvcConfigurer;

import java.util.ArrayList;
import java.util.List;

/**
 * Sa-Token 安全认证配置
 */
@Data
@Slf4j
@Configuration
@ConditionalOnProperty(prefix = "xiaoyu.security", name = "enabled", havingValue = "true", matchIfMissing = true)
@ConfigurationProperties(prefix = "xiaoyu.security")
public class SaTokenConfiguration implements WebMvcConfigurer {

    /**
     * 排除路径列表 ignore-urls
     */
    private List<String> ignoreUrls = new ArrayList<>();

    @Override
    public void addInterceptors(InterceptorRegistry registry) {
        List<String> excludeList = new ArrayList<>();
        if (ignoreUrls != null && !ignoreUrls.isEmpty()) {
            excludeList.addAll(ignoreUrls);
        } else {
            // 默认排除路径
            excludeList.add("/login");
            excludeList.add("/logout");
            excludeList.add("/captcha");
            excludeList.add("/health");
            excludeList.add("/actuator/**");
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