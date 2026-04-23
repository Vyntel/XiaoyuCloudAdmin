package com.xiaoyu.module.system.controller;

import com.mybatisflex.core.service.IService;
import com.xiaoyu.common.core.result.Result;
import com.xiaoyu.module.system.entity.SystemDictType;
import com.xiaoyu.module.system.service.SystemDictTypeService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统字典类型 Controller
 */
@Tag(name = "系统字典类型", description = "系统字典类型管理")
@RestController
@RequestMapping("/system/dict-type")
@RequiredArgsConstructor
public class SystemDictTypeController {

    private final SystemDictTypeService systemDictTypeService;

    /**
     * 获取字典类型列表
     */
    @Operation(summary = "获取字典类型列表")
    @GetMapping("/list")
    public Result<List<SystemDictType>> getDictTypeList() {
        return Result.success(systemDictTypeService.getDictTypeList());
    }

    /**
     * 分页查询字典类型
     */
    @Operation(summary = "分页查询字典类型")
    @GetMapping("/page")
    public Result<List<SystemDictType>> getDictTypePage(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "code", required = false) String code,
            @RequestParam(value = "status", required = false) Integer status) {
        return Result.success(systemDictTypeService.getDictTypePage(pageNum, pageSize, name, code, status));
    }

    /**
     * 获取字典类型详情
     */
    @Operation(summary = "获取字典类型详情")
    @GetMapping("/{dictId}")
    public Result<SystemDictType> getDictTypeById(@PathVariable("dictId") Long dictId) {
        return Result.success(systemDictTypeService.getDictTypeById(dictId));
    }

    /**
     * 新增字典类型
     */
    @Operation(summary = "新增字典类型")
    @PostMapping
    public Result<Long> createDictType(@RequestBody SystemDictType dictType) {
        return Result.success(systemDictTypeService.createDictType(dictType));
    }

    /**
     * 修改字典类型
     */
    @Operation(summary = "修改字典类型")
    @PutMapping
    public Result<Void> updateDictType(@RequestBody SystemDictType dictType) {
        systemDictTypeService.updateDictType(dictType);
        return Result.success();
    }

    /**
     * 删除字典类型
     */
    @Operation(summary = "删除字典类型")
    @DeleteMapping("/{dictId}")
    public Result<Void> deleteDictType(@PathVariable("dictId") Long dictId) {
        systemDictTypeService.deleteDictType(dictId);
        return Result.success();
    }
}