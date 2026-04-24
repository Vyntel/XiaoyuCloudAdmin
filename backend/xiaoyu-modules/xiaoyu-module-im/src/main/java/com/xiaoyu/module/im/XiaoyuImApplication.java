package com.xiaoyu.module.im;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * IM模块启动类
 */
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.xiaoyu"})
public class XiaoyuImApplication {

    public static void main(String[] args) {
        SpringApplication.run(XiaoyuImApplication.class, args);
    }
}