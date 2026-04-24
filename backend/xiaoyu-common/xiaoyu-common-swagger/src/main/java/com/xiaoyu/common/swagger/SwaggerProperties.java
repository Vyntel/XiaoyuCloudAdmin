package com.xiaoyu.common.swagger;

import lombok.Data;
import org.springframework.boot.context.properties.ConfigurationProperties;

import java.util.ArrayList;
import java.util.List;

/**
 * Swagger 配置属性
 * 支持 YAML 配置文件加载
 */
@Data
@ConfigurationProperties(prefix = "xiaoyu.swagger")
public class SwaggerProperties {

    /**
     * 是否启用 Swagger
     */
    private boolean enabled = true;

    /**
     * API 标题
     */
    private String title = "小羽快速开发框架 API";

    /**
     * API 描述
     */
    private String description = "小羽快速开发框架 API 文档";

    /**
     * API 版本
     */
    private String version = "1.0.0";

    /**
     * API 分组列表
     */
    private List<GroupProperties> groups = new ArrayList<>();

    /**
     * API 分组配置
     */
    @Data
    public static class GroupProperties {

        /**
         * 分组名称
         */
        private String name;

        /**
         * 分组标题
         */
        private String title;

        /**
         * 分组描述
         */
        private String description;

        /**
         * 包扫描路径
         */
        private String packageScan;
    }
}