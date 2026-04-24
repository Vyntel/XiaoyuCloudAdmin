package com.xiaoyu.module.generator;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 代码生成模块启动类
 */
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.xiaoyu"})
public class XiaoyuGeneratorApplication {

    public static void main(String[] args) {
        SpringApplication.run(XiaoyuGeneratorApplication.class, args);
    }
}