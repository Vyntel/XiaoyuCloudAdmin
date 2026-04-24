package com.xiaoyu.module.workflow.controller;

import com.xiaoyu.module.workflow.entity.WorkflowDefinition;
import com.xiaoyu.module.workflow.service.WorkflowDefinitionService;
import com.xiaoyu.common.core.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 流程定义 Controller
 */
@Tag(name = "流程定义", description = "流程定义管理")
@RestController
@RequestMapping("/workflow/definition")
@RequiredArgsConstructor
public class WorkflowDefinitionController {

    private final WorkflowDefinitionService workflowDefinitionService;

    @Operation(summary = "分页查询流程定义")
    @GetMapping("/page")
    public Result<List<WorkflowDefinition>> getDefinitionPage(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "category", required = false) String category,
            @RequestParam(value = "status", required = false) Integer status) {
        List<WorkflowDefinition> list = workflowDefinitionService.getDefinitionPage(pageNum, pageSize, name, category, status);
        return Result.success(list);
    }

    @Operation(summary = "获取流程定义详情")
    @GetMapping("/{id}")
    public Result<WorkflowDefinition> getDefinitionById(@PathVariable("id") Long id) {
        return Result.success(workflowDefinitionService.getDefinitionById(id));
    }

    @Operation(summary = "创建流程定义")
    @PostMapping
    public Result<Long> createDefinition(@RequestBody WorkflowDefinition definition) {
        Long id = workflowDefinitionService.createDefinition(definition);
        return Result.success(id);
    }

    @Operation(summary = "更新流程定义")
    @PutMapping
    public Result<Boolean> updateDefinition(@RequestBody WorkflowDefinition definition) {
        Boolean success = workflowDefinitionService.updateDefinition(definition);
        return Result.success(success);
    }

    @Operation(summary = "删除流程定义")
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteDefinition(@PathVariable("id") Long id) {
        Boolean success = workflowDefinitionService.deleteDefinition(id);
        return Result.success(success);
    }

    @Operation(summary = "部署流程定义")
    @PostMapping("/{id}/deploy")
    public Result<Boolean> deployDefinition(@PathVariable("id") Long id) {
        Boolean success = workflowDefinitionService.deployDefinition(id);
        return Result.success(success);
    }

    @Operation(summary = "挂起流程定义")
    @PostMapping("/{id}/suspend")
    public Result<Boolean> suspendDefinition(@PathVariable("id") Long id) {
        Boolean success = workflowDefinitionService.suspendDefinition(id);
        return Result.success(success);
    }

    @Operation(summary = "激活流程定义")
    @PostMapping("/{id}/activate")
    public Result<Boolean> activateDefinition(@PathVariable("id") Long id) {
        Boolean success = workflowDefinitionService.activateDefinition(id);
        return Result.success(success);
    }

    @Operation(summary = "获取已部署流程列表")
    @GetMapping("/deployed")
    public Result<List<WorkflowDefinition>> getDeployedList() {
        List<WorkflowDefinition> list = workflowDefinitionService.getDeployedList();
        return Result.success(list);
    }
}