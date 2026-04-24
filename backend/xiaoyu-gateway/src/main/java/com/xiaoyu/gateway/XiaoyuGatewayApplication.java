package com.xiaoyu.gateway;

import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;
import org.springframework.cloud.client.discovery.EnableDiscoveryClient;

/**
 * 网关启动类
 */
@EnableDiscoveryClient
@SpringBootApplication
public class XiaoyuGatewayApplication {

    public static void main(String[] args) {
        SpringApplication.run(XiaoyuGatewayApplication.class, args);
    }
}