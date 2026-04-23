package com.xiaoyu.module.system.controller;

import com.xiaoyu.common.core.result.Result;
import com.xiaoyu.module.system.entity.SystemRole;
import com.xiaoyu.module.system.service.SystemRoleService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.Parameter;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统角色 Controller
 */
@Tag(name = "系统角色", description = "系统角色管理")
@RestController
@RequestMapping("/system/role")
@RequiredArgsConstructor
public class SystemRoleController {

    private final SystemRoleService systemRoleService;

    /**
     * 获取所有角色列表
     */
    @Operation(summary = "获取所有角色列表")
    @GetMapping("/list")
    public Result<List<SystemRole>> getRoleList() {
        return Result.success(systemRoleService.getRoleList());
    }

    /**
     * 分页查询角色列表
     */
    @Operation(summary = "分页查询角色列表")
    @GetMapping("/page")
    public Result<List<SystemRole>> getRolePage(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "roleName", required = false) String roleName,
            @RequestParam(value = "roleKey", required = false) String roleKey,
            @RequestParam(value = "status", required = false) Integer status) {
        return Result.success(systemRoleService.getRolePage(pageNum, pageSize, roleName, roleKey, status));
    }

    /**
     * 获取角色详情
     */
    @Operation(summary = "获取角色详情")
    @GetMapping("/{roleId}")
    public Result<SystemRole> getRoleById(@PathVariable("roleId") Long roleId) {
        return Result.success(systemRoleService.getRoleById(roleId));
    }

    /**
     * 新增角色
     */
    @Operation(summary = "新增角色")
    @PostMapping
    public Result<Long> createRole(@RequestBody SystemRole role) {
        return Result.success(systemRoleService.createRole(role));
    }

    /**
     * 修改角色
     */
    @Operation(summary = "修改角色")
    @PutMapping
    public Result<Void> updateRole(@RequestBody SystemRole role) {
        systemRoleService.updateRole(role);
        return Result.success();
    }

    /**
     * 删除角色
     */
    @Operation(summary = "删除角色")
    @DeleteMapping("/{roleId}")
    public Result<Void> deleteRole(@PathVariable("roleId") Long roleId) {
        systemRoleService.deleteRole(roleId);
        return Result.success();
    }

    /**
     * 修改角色状态
     */
    @Operation(summary = "修改角色状态")
    @PutMapping("/{roleId}/status")
    public Result<Void> updateRoleStatus(
            @PathVariable("roleId") Long roleId,
            @RequestParam("status") Integer status) {
        systemRoleService.updateRoleStatus(roleId, status);
        return Result.success();
    }

    /**
     * 分配菜单权限
     */
    @Operation(summary = "分配菜单权限")
    @PutMapping("/{roleId}/menus")
    public Result<Void> assignMenus(
            @PathVariable("roleId") Long roleId,
            @RequestBody List<Long> menuIds) {
        systemRoleService.assignMenus(roleId, menuIds);
        return Result.success();
    }

    /**
     * 获取角色菜单ID列表
     */
    @Operation(summary = "获取角色菜单ID列表")
    @GetMapping("/{roleId}/menu-ids")
    public Result<List<Long>> getRoleMenuIds(@PathVariable("roleId") Long roleId) {
        return Result.success(systemRoleService.getRoleMenuIds(roleId));
    }
}