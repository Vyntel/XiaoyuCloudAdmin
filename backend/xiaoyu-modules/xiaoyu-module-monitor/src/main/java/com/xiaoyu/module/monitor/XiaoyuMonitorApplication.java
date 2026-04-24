package com.xiaoyu.module.monitor;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 监控模块启动类
 */
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.xiaoyu"})
public class XiaoyuMonitorApplication {

    public static void main(String[] args) {
        SpringApplication.run(XiaoyuMonitorApplication.class, args);
    }
}