package com.xiaoyu.module.auth;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 认证模块启动类
 */
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.xiaoyu"})
public class XiaoyuAuthApplication {

    public static void main(String[] args) {
        SpringApplication.run(XiaoyuAuthApplication.class, args);
    }
}