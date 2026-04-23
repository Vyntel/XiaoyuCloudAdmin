package com.xiaoyu.module.workflow.controller;

import com.xiaoyu.module.workflow.entity.WorkflowTask;
import com.xiaoyu.module.workflow.service.WorkflowTaskService;
import com.xiaoyu.common.core.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 流程任务 Controller
 */
@Tag(name = "流程任务", description = "流程任务管理")
@RestController
@RequestMapping("/workflow/task")
@RequiredArgsConstructor
public class WorkflowTaskController {

    private final WorkflowTaskService workflowTaskService;

    @Operation(summary = "获取待办任务")
    @GetMapping("/todo")
    public Result<List<WorkflowTask>> getTodoTasks(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "assigneeId", required = false) Long assigneeId,
            @RequestParam(value = "taskName", required = false) String taskName) {
        List<WorkflowTask> list = workflowTaskService.getTodoTasks(pageNum, pageSize, assigneeId, taskName);
        return Result.success(list);
    }

    @Operation(summary = "获取已办任务")
    @GetMapping("/done")
    public Result<List<WorkflowTask>> getDoneTasks(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "assigneeId", required = false) Long assigneeId) {
        List<WorkflowTask> list = workflowTaskService.getDoneTasks(pageNum, pageSize, assigneeId);
        return Result.success(list);
    }

    @Operation(summary = "获取任务详情")
    @GetMapping("/{id}")
    public Result<WorkflowTask> getTaskById(@PathVariable("id") Long id) {
        return Result.success(workflowTaskService.getTaskById(id));
    }

    @Operation(summary = "完成任务")
    @PostMapping("/{id}/complete")
    public Result<Boolean> completeTask(
            @PathVariable("id") Long id,
            @RequestParam(value = "comment", required = false) String comment) {
        Boolean success = workflowTaskService.completeTask(id, comment);
        return Result.success(success);
    }

    @Operation(summary = "拒绝任务")
    @PostMapping("/{id}/reject")
    public Result<Boolean> rejectTask(
            @PathVariable("id") Long id,
            @RequestParam(value = "comment", required = false) String comment) {
        Boolean success = workflowTaskService.rejectTask(id, comment);
        return Result.success(success);
    }

    @Operation(summary = "转办任务")
    @PostMapping("/{id}/transfer")
    public Result<Boolean> transferTask(
            @PathVariable("id") Long id,
            @RequestParam("targetUserId") Long targetUserId) {
        Boolean success = workflowTaskService.transferTask(id, targetUserId);
        return Result.success(success);
    }

    @Operation(summary = "委托任务")
    @PostMapping("/{id}/delegate")
    public Result<Boolean> delegateTask(
            @PathVariable("id") Long id,
            @RequestParam("delegateUserId") Long delegateUserId) {
        Boolean success = workflowTaskService.delegateTask(id, delegateUserId);
        return Result.success(success);
    }

    @Operation(summary = "退回任务")
    @PostMapping("/{id}/return")
    public Result<Boolean> returnTask(@PathVariable("id") Long id) {
        Boolean success = workflowTaskService.returnTask(id);
        return Result.success(success);
    }
}