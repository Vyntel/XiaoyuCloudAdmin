package com.xiaoyu.module.file;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 文件模块启动类
 */
@EnableDiscoveryClient
@SpringBootApplication(scanBasePackages = {"com.xiaoyu"})
public class XiaoyuFileApplication {

    public static void main(String[] args) {
        SpringApplication.run(XiaoyuFileApplication.class, args);
    }
}