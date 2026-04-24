package com.xiaoyu.api.job;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 定时任务模块Feign接口
 */
@FeignClient(name = "xiaoyu-module-job", contextId = "jobApi")
public interface JobApi {

    @GetMapping("/job/list")
    String getJobs();

    @PostMapping("/job/add")
    String addJob(@RequestBody String job);

    @PostMapping("/job/execute")
    String executeJob(@RequestParam("jobId") Long jobId);

    @DeleteMapping("/job/delete")
    String deleteJob(@RequestParam("jobId") Long jobId);
}