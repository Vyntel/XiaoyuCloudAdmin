package com.xiaoyu.common.swagger;

import io.swagger.v3.oas.models.OpenAPI;
import io.swagger.v3.oas.models.info.Contact;
import io.swagger.v3.oas.models.info.Info;
import io.swagger.v3.oas.models.info.License;
import lombok.extern.slf4j.Slf4j;
import org.springframework.boot.autoconfigure.AutoConfiguration;
import org.springframework.boot.autoconfigure.condition.ConditionalOnProperty;
import org.springframework.boot.context.properties.EnableConfigurationProperties;
import org.springframework.context.annotation.Bean;

/**
 * Swagger 自动配置类
 * 基于 YAML 配置文件加载
 * 使用 springdoc-openapi 实现
 */
@Slf4j
@AutoConfiguration
@EnableConfigurationProperties(SwaggerProperties.class)
@ConditionalOnProperty(prefix = "xiaoyu.swagger", name = "enabled", havingValue = "true", matchIfMissing = true)
public class SwaggerAutoConfiguration {

    public SwaggerAutoConfiguration(SwaggerProperties swaggerProperties) {
        log.info("╔═════════════════════════════════���═════════════════════════════╗");
        log.info("║          小羽快速开发框架 - Swagger 配置已加载                 ║");
        log.info("║  Swagger Enabled: {}                                      ║", swaggerProperties.isEnabled());
        log.info("║  API Title: {}                                             ║", swaggerProperties.getTitle());
        log.info("╚═══════════════════════════════════════════════════════════════╝");
    }

    /**
     * 默认 API 文档信息
     * 配置全局 OpenAPI Bean，springdoc 会自动读取并生成 Swagger UI
     */
    @Bean
    public OpenAPI customOpenAPI(SwaggerProperties swaggerProperties) {
        Info info = new Info()
                .title(swaggerProperties.getTitle())
                .description(swaggerProperties.getDescription())
                .version(swaggerProperties.getVersion())
                .contact(new Contact()
                        .name("xiaoyu")
                        .url("https://github.com/xiaoyu"))
                .license(new License()
                        .name("Apache 2.0")
                        .url("https://www.apache.org/licenses/LICENSE-2.0"));

        return new OpenAPI().info(info);
    }
}