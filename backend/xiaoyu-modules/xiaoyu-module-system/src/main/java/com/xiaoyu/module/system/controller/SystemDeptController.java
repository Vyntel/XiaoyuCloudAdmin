package com.xiaoyu.module.system.controller;

import com.xiaoyu.common.core.result.Result;
import com.xiaoyu.module.system.entity.SystemDept;
import com.xiaoyu.module.system.service.SystemDeptService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统部门 Controller
 */
@Tag(name = "系统部门", description = "系统部门管理")
@RestController
@RequestMapping("/system/dept")
@RequiredArgsConstructor
public class SystemDeptController {

    private final SystemDeptService systemDeptService;

    /**
     * 获取部门树
     */
    @Operation(summary = "获取部门树")
    @GetMapping("/tree")
    public Result<List<SystemDept>> getDeptTree() {
        return Result.success(systemDeptService.getDeptTree());
    }

    /**
     * 获取所有部门列表
     */
    @Operation(summary = "获取所有部门列表")
    @GetMapping("/list")
    public Result<List<SystemDept>> getDeptList() {
        return Result.success(systemDeptService.getDeptList());
    }

    /**
     * 获取部门详情
     */
    @Operation(summary = "获取部门详情")
    @GetMapping("/{deptId}")
    public Result<SystemDept> getDeptById(@PathVariable("deptId") Long deptId) {
        return Result.success(systemDeptService.getDeptById(deptId));
    }

    /**
     * 获取子部门列表
     */
    @Operation(summary = "获取子部门列表")
    @GetMapping("/{deptId}/children")
    public Result<List<SystemDept>> getDeptChildren(@PathVariable("deptId") Long deptId) {
        return Result.success(systemDeptService.getDeptChildren(deptId));
    }

    /**
     * 新增部门
     */
    @Operation(summary = "新增部门")
    @PostMapping
    public Result<Long> createDept(@RequestBody SystemDept dept) {
        return Result.success(systemDeptService.createDept(dept));
    }

    /**
     * 修改部门
     */
    @Operation(summary = "修改部门")
    @PutMapping
    public Result<Void> updateDept(@RequestBody SystemDept dept) {
        systemDeptService.updateDept(dept);
        return Result.success();
    }

    /**
     * 删除部门
     */
    @Operation(summary = "删除部门")
    @DeleteMapping("/{deptId}")
    public Result<Void> deleteDept(@PathVariable("deptId") Long deptId) {
        systemDeptService.deleteDept(deptId);
        return Result.success();
    }
}