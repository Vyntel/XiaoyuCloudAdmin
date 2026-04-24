package com.xiaoyu.module.workflow.controller;

import com.xiaoyu.module.workflow.entity.WorkflowInstance;
import com.xiaoyu.module.workflow.service.WorkflowInstanceService;
import com.xiaoyu.common.core.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 流程实例 Controller
 */
@Tag(name = "流程实例", description = "流程实例管理")
@RestController
@RequestMapping("/workflow/instance")
@RequiredArgsConstructor
public class WorkflowInstanceController {

    private final WorkflowInstanceService workflowInstanceService;

    @Operation(summary = "分页查询流程实例")
    @GetMapping("/page")
    public Result<List<WorkflowInstance>> getInstancePage(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "status", required = false) Integer status,
            @RequestParam(value = "startUserId", required = false) Long startUserId) {
        List<WorkflowInstance> list = workflowInstanceService.getInstancePage(pageNum, pageSize, name, status, startUserId);
        return Result.success(list);
    }

    @Operation(summary = "获取流程实例详情")
    @GetMapping("/{id}")
    public Result<WorkflowInstance> getInstanceById(@PathVariable("id") Long id) {
        return Result.success(workflowInstanceService.getInstanceById(id));
    }

    @Operation(summary = "发起流程")
    @PostMapping("/start")
    public Result<Long> startInstance(
            @RequestParam("definitionId") Long definitionId,
            @RequestParam(value = "formDataId", required = false) Long formDataId) {
        Long id = workflowInstanceService.startInstance(definitionId, formDataId);
        return Result.success(id);
    }

    @Operation(summary = "取消流程")
    @PostMapping("/{id}/cancel")
    public Result<Boolean> cancelInstance(@PathVariable("id") Long id) {
        Boolean success = workflowInstanceService.cancelInstance(id);
        return Result.success(success);
    }

    @Operation(summary = "终止流程")
    @PostMapping("/{id}/terminate")
    public Result<Boolean> terminateInstance(@PathVariable("id") Long id) {
        Boolean success = workflowInstanceService.terminateInstance(id);
        return Result.success(success);
    }

    @Operation(summary = "获取我的流程")
    @GetMapping("/my")
    public Result<List<WorkflowInstance>> getMyInstances(@RequestParam("userId") Long userId) {
        List<WorkflowInstance> list = workflowInstanceService.getMyInstances(userId);
        return Result.success(list);
    }
}