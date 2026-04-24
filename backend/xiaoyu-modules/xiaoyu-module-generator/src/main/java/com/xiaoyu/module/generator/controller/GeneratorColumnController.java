package com.xiaoyu.module.generator.controller;

import com.xiaoyu.module.generator.entity.GenTableColumn;
import com.xiaoyu.module.generator.service.GeneratorColumnService;
import com.xiaoyu.common.core.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "代码生成字段", description = "代码生成字段管理")
@RestController
@RequestMapping("/generator/column")
@RequiredArgsConstructor
public class GeneratorColumnController {
    private final GeneratorColumnService generatorColumnService;

    @Operation(summary = "获取表字段列表")
    @GetMapping("/{tableId}")
    public Result<List<GenTableColumn>> getColumnsByTableId(@PathVariable("tableId") Long tableId) {
        List<GenTableColumn> list = generatorColumnService.getColumnsByTableId(tableId);
        return Result.success(list);
    }

    @Operation(summary = "删除表字段")
    @DeleteMapping("/{tableId}")
    public Result<Boolean> deleteColumnsByTableId(@PathVariable("tableId") Long tableId) {
        Boolean result = generatorColumnService.deleteColumnsByTableId(tableId);
        return Result.success(result);
    }

    @Operation(summary = "批量插入字段")
    @PostMapping("/batch")
    public Result<Integer> batchInsert(@RequestBody List<GenTableColumn> columns) {
        int count = generatorColumnService.batchInsert(columns);
        return Result.success(count);
    }

    @Operation(summary = "更新字段")
    @PutMapping("/{id}")
    public Result<Boolean> updateColumn(@PathVariable("id") Long id, @RequestBody GenTableColumn column) {
        column.setId(id);
        Boolean result = generatorColumnService.updateColumn(column);
        return Result.success(result);
    }

    @Operation(summary = "删除字段")
    @DeleteMapping("/single/{id}")
    public Result<Boolean> deleteColumn(@PathVariable("id") Long id) {
        Boolean result = generatorColumnService.deleteColumn(id);
        return Result.success(result);
    }
}