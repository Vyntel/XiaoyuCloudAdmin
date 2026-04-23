package com.xiaoyu.module.ai.controller;

import com.xiaoyu.module.ai.entity.AiModel;
import com.xiaoyu.module.ai.service.AiModelService;
import com.xiaoyu.common.core.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AI模型 Controller
 */
@Tag(name = "AI模型管理", description = "AI模型配置管理")
@RestController
@RequestMapping("/ai/model")
@RequiredArgsConstructor
public class AiModelController {

    private final AiModelService aiModelService;

    @Operation(summary = "分页查询模型列表")
    @GetMapping("/page")
    public Result<List<AiModel>> getModelPage(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "provider", required = false) String provider,
            @RequestParam(value = "status", required = false) Integer status) {
        List<AiModel> list = aiModelService.getModelPage(pageNum, pageSize, name, provider, status);
        return Result.success(list);
    }

    @Operation(summary = "获取所有启用模型列表")
    @GetMapping("/list")
    public Result<List<AiModel>> getEnabledModels() {
        return Result.success(aiModelService.getEnabledModels());
    }

    @Operation(summary = "获取模型详情")
    @GetMapping("/{id}")
    public Result<AiModel> getModelById(@PathVariable("id") Long id) {
        return Result.success(aiModelService.getModelById(id));
    }

    @Operation(summary = "根据编码获取模型")
    @GetMapping("/code/{code}")
    public Result<AiModel> getModelByCode(@PathVariable("code") String code) {
        return Result.success(aiModelService.getModelByCode(code));
    }

    @Operation(summary = "创建模型")
    @PostMapping
    public Result<Long> createModel(@RequestBody AiModel model) {
        return Result.success(aiModelService.createModel(model));
    }

    @Operation(summary = "更新模型")
    @PutMapping
    public Result<Boolean> updateModel(@RequestBody AiModel model) {
        return Result.success(aiModelService.updateModel(model));
    }

    @Operation(summary = "修改模型状态")
    @PutMapping("/{id}/status")
    public Result<Boolean> updateModelStatus(
            @PathVariable("id") Long id,
            @RequestParam("status") Integer status) {
        return Result.success(aiModelService.updateModelStatus(id, status));
    }

    @Operation(summary = "删除模型")
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteModel(@PathVariable("id") Long id) {
        return Result.success(aiModelService.deleteModel(id));
    }
}