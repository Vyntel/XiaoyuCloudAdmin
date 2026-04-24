package com.xiaoyu.module.workflow;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 工作流模块启动类
 */
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.xiaoyu"})
public class XiaoyuWorkflowApplication {

    public static void main(String[] args) {
        SpringApplication.run(XiaoyuWorkflowApplication.class, args);
    }
}