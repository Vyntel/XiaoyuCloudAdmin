package com.xiaoyu.module.system;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 系统模块启动类
 */
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.xiaoyu"})
public class XiaoyuSystemApplication {

    public static void main(String[] args) {
        SpringApplication.run(XiaoyuSystemApplication.class, args);
    }
}