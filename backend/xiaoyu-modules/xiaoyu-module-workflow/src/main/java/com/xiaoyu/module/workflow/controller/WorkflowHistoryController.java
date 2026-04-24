package com.xiaoyu.module.workflow.controller;

import com.xiaoyu.common.core.result.Result;
import com.xiaoyu.module.workflow.entity.WorkflowHistory;
import com.xiaoyu.module.workflow.service.WorkflowHistoryService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 流程历史 Controller
 */
@Tag(name = "流程历史管理")
@RestController
@RequestMapping("/workflow/history")
@RequiredArgsConstructor
public class WorkflowHistoryController {

    private final WorkflowHistoryService workflowHistoryService;

    /**
     * 获取流程历史列表
     */
    @GetMapping("/list")
    @Operation(summary = "获取流程历史列表")
    public Result<List<WorkflowHistory>> getHistoryList(
            @RequestParam(required = false) Long instanceId,
            @RequestParam(required = false) Long operateUserId,
            @RequestParam(required = false) Integer operateType
    ) {
        List<WorkflowHistory> list = workflowHistoryService.getHistoryList(instanceId, operateUserId, operateType);
        return Result.success(list);
    }

    /**
     * 获取流程历史详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "获取流程历史详情")
    public Result<WorkflowHistory> getHistoryById(@PathVariable Long id) {
        WorkflowHistory history = workflowHistoryService.getHistoryById(id);
        return Result.success(history);
    }

    /**
     * 获取流程实例的所有历史记录
     */
    @GetMapping("/instance/{instanceId}")
    @Operation(summary = "获取流程实例的所有历史记录")
    public Result<List<WorkflowHistory>> getHistoryByInstanceId(@PathVariable Long instanceId) {
        List<WorkflowHistory> list = workflowHistoryService.getHistoryByInstanceId(instanceId);
        return Result.success(list);
    }
}