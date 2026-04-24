package com.xiaoyu.module.system.controller;

import com.xiaoyu.common.core.result.Result;
import com.xiaoyu.module.system.entity.SystemTenant;
import com.xiaoyu.module.system.service.SystemTenantService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统租户 Controller
 */
@Tag(name = "系统租户", description = "系统租户管理")
@RestController
@RequestMapping("/system/tenant")
@RequiredArgsConstructor
public class SystemTenantController {

    private final SystemTenantService systemTenantService;

    @GetMapping("/list")
    @Operation(summary = "分页查询租户列表")
    public Result<List<SystemTenant>> getTenantPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            String name,
            Integer status) {
        List<SystemTenant> list = systemTenantService.getTenantPage(pageNum, pageSize, name, status);
        return Result.success(list);
    }

    @GetMapping("/enabled")
    @Operation(summary = "获取所有启用的租户")
    public Result<List<SystemTenant>> getEnabledTenants() {
        return Result.success(systemTenantService.getEnabledTenants());
    }

    @GetMapping("/{id}")
    @Operation(summary = "获取租户详情")
    public Result<SystemTenant> getTenantById(@PathVariable Long id) {
        return Result.success(systemTenantService.getTenantById(id));
    }

    @PostMapping
    @Operation(summary = "新增租户")
    public Result<Long> createTenant(@RequestBody SystemTenant tenant) {
        Long id = systemTenantService.createTenant(tenant);
        return Result.success(id);
    }

    @PutMapping
    @Operation(summary = "更新租户")
    public Result<Boolean> updateTenant(@RequestBody SystemTenant tenant) {
        return Result.success(systemTenantService.updateTenant(tenant));
    }

    @DeleteMapping("/{id}")
    @Operation(summary = "删除租户")
    public Result<Boolean> deleteTenant(@PathVariable Long id) {
        return Result.success(systemTenantService.deleteTenant(id));
    }
}