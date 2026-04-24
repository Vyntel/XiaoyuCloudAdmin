package com.xiaoyu.module.report;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 报表模块启动类
 */
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.xiaoyu"})
public class XiaoyuReportApplication {

    public static void main(String[] args) {
        SpringApplication.run(XiaoyuReportApplication.class, args);
    }
}