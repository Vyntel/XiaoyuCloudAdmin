package com.xiaoyu.module.system.controller;

import com.xiaoyu.common.core.result.Result;
import com.xiaoyu.module.system.entity.SystemMenu;
import com.xiaoyu.module.system.service.SystemMenuService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统菜单 Controller
 */
@Tag(name = "系统菜单", description = "系统菜单管理")
@RestController
@RequestMapping("/system/menu")
@RequiredArgsConstructor
public class SystemMenuController {

    private final SystemMenuService systemMenuService;

    /**
     * 获取菜单树
     */
    @Operation(summary = "获取菜单树")
    @GetMapping("/tree")
    public Result<List<SystemMenu>> getMenuTree() {
        return Result.success(systemMenuService.getMenuTree());
    }

    /**
     * 获取所有菜单列表
     */
    @Operation(summary = "获取所有菜单列表")
    @GetMapping("/list")
    public Result<List<SystemMenu>> getMenuList() {
        return Result.success(systemMenuService.getMenuList());
    }

    /**
     * 获取菜单详情
     */
    @Operation(summary = "获取菜单详情")
    @GetMapping("/{menuId}")
    public Result<SystemMenu> getMenuById(@PathVariable("menuId") Long menuId) {
        return Result.success(systemMenuService.getMenuById(menuId));
    }

    /**
     * 获取子菜单列表
     */
    @Operation(summary = "获取子菜单列表")
    @GetMapping("/{menuId}/children")
    public Result<List<SystemMenu>> getMenuChildren(@PathVariable("menuId") Long menuId) {
        return Result.success(systemMenuService.getMenuChildren(menuId));
    }

    /**
     * 获取路由菜单树（用于动态路由）
     */
    @Operation(summary = "获取路由菜单树")
    @GetMapping("/routes")
    public Result<List<SystemMenu>> getRoutes() {
        return Result.success(systemMenuService.getRoutes());
    }

    /**
     * 新增菜单
     */
    @Operation(summary = "新增菜单")
    @PostMapping
    public Result<Long> createMenu(@RequestBody SystemMenu menu) {
        return Result.success(systemMenuService.createMenu(menu));
    }

    /**
     * 修改菜单
     */
    @Operation(summary = "修改菜单")
    @PutMapping
    public Result<Void> updateMenu(@RequestBody SystemMenu menu) {
        systemMenuService.updateMenu(menu);
        return Result.success();
    }

    /**
     * 删除菜单
     */
    @Operation(summary = "删除菜单")
    @DeleteMapping("/{menuId}")
    public Result<Void> deleteMenu(@PathVariable("menuId") Long menuId) {
        systemMenuService.deleteMenu(menuId);
        return Result.success();
    }
}