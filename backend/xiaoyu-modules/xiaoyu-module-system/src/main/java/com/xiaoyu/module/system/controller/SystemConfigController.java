package com.xiaoyu.module.system.controller;

import com.xiaoyu.common.core.result.Result;
import com.xiaoyu.module.system.entity.SystemConfig;
import com.xiaoyu.module.system.service.SystemConfigService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;
import java.util.Map;

/**
 * 系统配置 Controller
 */
@Tag(name = "系统配置", description = "系统参数配置管理")
@RestController
@RequestMapping("/system/config")
@RequiredArgsConstructor
public class SystemConfigController {

    private final SystemConfigService systemConfigService;

    /**
     * 获取配置列表
     */
    @Operation(summary = "获取配置列表")
    @GetMapping("/list")
    public Result<List<SystemConfig>> getConfigList() {
        return Result.success(systemConfigService.getConfigList());
    }

    /**
     * 获取配置详情
     */
    @Operation(summary = "获取配置详情")
    @GetMapping("/{configId}")
    public Result<SystemConfig> getConfigById(@PathVariable("configId") Long configId) {
        return Result.success(systemConfigService.getConfigById(configId));
    }

    /**
     * 根据键名获取配置值
     */
    @Operation(summary = "根据键名获取配置值")
    @GetMapping("/key/{configKey}")
    public Result<String> getConfigByKey(@PathVariable("configKey") String configKey) {
        return Result.success(systemConfigService.getConfigValue(configKey));
    }

    /**
     * 新增配置
     */
    @Operation(summary = "新增配置")
    @PostMapping
    public Result<Long> createConfig(@RequestBody SystemConfig config) {
        return Result.success(systemConfigService.createConfig(config));
    }

    /**
     * 修改配置
     */
    @Operation(summary = "修改配置")
    @PutMapping
    public Result<Void> updateConfig(@RequestBody SystemConfig config) {
        systemConfigService.updateConfig(config);
        return Result.success();
    }

    /**
     * 删除配置
     */
    @Operation(summary = "删除配置")
    @DeleteMapping("/{configId}")
    public Result<Void> deleteConfig(@PathVariable("configId") Long configId) {
        systemConfigService.deleteConfig(configId);
        return Result.success();
    }

    /**
     * 刷新缓存
     */
    @Operation(summary = "刷新配置缓存")
    @DeleteMapping("/cache/refresh")
    public Result<Void> refreshCache() {
        systemConfigService.refreshCache();
        return Result.success();
    }
}