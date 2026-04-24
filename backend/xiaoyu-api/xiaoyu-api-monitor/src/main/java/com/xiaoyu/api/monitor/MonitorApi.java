package com.xiaoyu.api.monitor;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 监控模块Feign接口
 */
@FeignClient(name = "xiaoyu-module-monitor", contextId = "monitorApi")
public interface MonitorApi {

    @GetMapping("/monitor/server")
    String getServerInfo();

    @GetMapping("/monitor/redis")
    String getRedisInfo();

    @GetMapping("/monitor/druid")
    String getDruidStat();
}