package com.xiaoyu.module.system.controller;

import com.xiaoyu.common.core.result.Result;
import com.xiaoyu.module.system.entity.SystemDictData;
import com.xiaoyu.module.system.service.SystemDictDataService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统字典数据 Controller
 */
@Tag(name = "系统字典数据", description = "系统字典数据管理")
@RestController
@RequestMapping("/system/dict-data")
@RequiredArgsConstructor
public class SystemDictDataController {

    private final SystemDictDataService systemDictDataService;

    /**
     * 获取字典数据列表
     */
    @Operation(summary = "获取字典数据列表")
    @GetMapping("/list")
    public Result<List<SystemDictData>> getDictDataList() {
        return Result.success(systemDictDataService.getDictDataList());
    }

    /**
     * 根据字典类型ID获取字典数据
     */
    @Operation(summary = "根据字典类型ID获取字典数据")
    @GetMapping("/type/{dictTypeId}")
    public Result<List<SystemDictData>> getDictDataByTypeId(@PathVariable("dictTypeId") Long dictTypeId) {
        return Result.success(systemDictDataService.getDictDataByTypeId(dictTypeId));
    }

    /**
     * 根据字典类型编码获取字典数据
     */
    @Operation(summary = "根据字典类型编码获取字典数据")
    @GetMapping("/type/code/{dictTypeCode}")
    public Result<List<SystemDictData>> getDictDataByTypeCode(@PathVariable("dictTypeCode") String dictTypeCode) {
        return Result.success(systemDictDataService.getDictDataByTypeCode(dictTypeCode));
    }

    /**
     * 获取字典数据详情
     */
    @Operation(summary = "获取字典数据详情")
    @GetMapping("/{dictDataId}")
    public Result<SystemDictData> getDictDataById(@PathVariable("dictDataId") Long dictDataId) {
        return Result.success(systemDictDataService.getDictDataById(dictDataId));
    }

    /**
     * 新增字典数据
     */
    @Operation(summary = "新增字典数据")
    @PostMapping
    public Result<Long> createDictData(@RequestBody SystemDictData dictData) {
        return Result.success(systemDictDataService.createDictData(dictData));
    }

    /**
     * 修改字典数据
     */
    @Operation(summary = "修改字典数据")
    @PutMapping
    public Result<Void> updateDictData(@RequestBody SystemDictData dictData) {
        systemDictDataService.updateDictData(dictData);
        return Result.success();
    }

    /**
     * 删除字典数据
     */
    @Operation(summary = "删除字典数据")
    @DeleteMapping("/{dictDataId}")
    public Result<Void> deleteDictData(@PathVariable("dictDataId") Long dictDataId) {
        systemDictDataService.deleteDictData(dictDataId);
        return Result.success();
    }
}