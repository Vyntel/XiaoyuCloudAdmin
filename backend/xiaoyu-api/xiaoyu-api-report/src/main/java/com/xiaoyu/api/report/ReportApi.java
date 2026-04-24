package com.xiaoyu.api.report;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 报表模块Feign接口
 */
@FeignClient(name = "xiaoyu-module-report", contextId = "reportApi")
public interface ReportApi {

    @GetMapping("/report/templates")
    String getTemplates();

    @PostMapping("/report/preview")
    String preview(@RequestBody String templateId);

    @PostMapping("/report/export")
    String exportReport(@RequestBody String templateId);
}