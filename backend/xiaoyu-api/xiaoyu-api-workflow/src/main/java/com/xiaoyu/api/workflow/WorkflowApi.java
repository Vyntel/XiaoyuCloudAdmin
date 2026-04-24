package com.xiaoyu.api.workflow;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 工作流模块Feign接口
 */
@FeignClient(name = "xiaoyu-module-workflow", contextId = "workflowApi")
public interface WorkflowApi {

    @GetMapping("/workflow/definitions")
    String getDefinitions();

    @PostMapping("/workflow/start")
    String start(@RequestBody String processKey);

    @GetMapping("/workflow/tasks")
    String getTasks(@RequestParam("userId") Long userId);

    @PostMapping("/workflow/complete")
    String completeTask(@RequestBody String taskId);
}