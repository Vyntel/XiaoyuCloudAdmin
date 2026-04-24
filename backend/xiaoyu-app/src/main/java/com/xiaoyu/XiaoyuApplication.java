package com.xiaoyu;

import org.mybatis.spring.annotation.MapperScan;
import org.springframework.boot.SpringApplication;
import org.springframework.boot.autoconfigure.SpringBootApplication;

/**
 * 小羽快速开发框架启动类
 */
@SpringBootApplication
@MapperScan("com.xiaoyu.**.mapper")
public class XiaoyuApplication {

    public static void main(String[] args) {
        SpringApplication.run(XiaoyuApplication.class, args);
        printBanner();
    }

    /**
     * 打印Banner
     */
    private static void printBanner() {
        String banner = """
                
                ╔═══════════════════════════════════════════════════════════════════╗
                ║                                                                   ║
                ║   _ _                 _                                           ║
                ║  | (_)_ __ ___  _   _| |_ ___  _ __ ___   __ _ _   _              ║
                ║  | | | '_ ` _ \\| | | | __/ _ \\| '_ ` _ \\ / _` | | | |            ║
                ║  | | | | | | | | |_| | || (_) | | | | | | (_| | |_| |               ║
                ║  |_|_|_| |_| |_|\\__,_|\\__\\___/|_| |_| |_|\\__,_|\\__, |        ║
                ║                                                |___/                ║
                ║                                                                   ║
                ║           小羽快速开发框架 v1.0.0 启动成功                            ║
                ║           Java 21 + Spring Boot + Spring Cloud                    ║
                ║                                                                   ║
                ║                                                                   ║
                ╚═══════════════════════════════════════════════════════════════════╝
                """;
        System.out.println(banner);
    }
}
