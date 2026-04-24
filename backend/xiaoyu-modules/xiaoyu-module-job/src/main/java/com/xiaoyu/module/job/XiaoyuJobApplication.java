package com.xiaoyu.module.job;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;
import org.springframework.cloud.openfeign.EnableFeignClients;

/**
 * 定时任务模块启动类
 */
@EnableDiscoveryClient
@EnableFeignClients
@SpringBootApplication(scanBasePackages = {"com.xiaoyu"})
public class XiaoyuJobApplication {

    public static void main(String[] args) {
        SpringApplication.run(XiaoyuJobApplication.class, args);
    }
}