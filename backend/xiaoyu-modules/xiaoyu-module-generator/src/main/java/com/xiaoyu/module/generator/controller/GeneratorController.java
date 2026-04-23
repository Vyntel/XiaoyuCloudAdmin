package com.xiaoyu.module.generator.controller;

import com.xiaoyu.module.generator.entity.GenTable;
import com.xiaoyu.module.generator.service.GeneratorService;
import com.xiaoyu.common.core.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "代码生成", description = "代码生成管理")
@RestController
@RequestMapping("/generator")
@RequiredArgsConstructor
public class GeneratorController {
    private final GeneratorService generatorService;

    @Operation(summary = "分页查询表")
    @GetMapping("/table/page")
    public Result<List<GenTable>> getTablePage(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "tableName", required = false) String tableName) {
        List<GenTable> list = generatorService.getTablePage(pageNum, pageSize, tableName);
        return Result.success(list);
    }

    @Operation(summary = "获取表详情")
    @GetMapping("/table/{id}")
    public Result<GenTable> getTableById(@PathVariable("id") Long id) {
        return Result.success(generatorService.getTableById(id));
    }

    @Operation(summary = "创建表")
    @PostMapping("/table")
    public Result<Long> createTable(@RequestBody GenTable table) {
        Long id = generatorService.createTable(table);
        return Result.success(id);
    }

    @Operation(summary = "删除表")
    @DeleteMapping("/table/{id}")
    public Result<Boolean> deleteTable(@PathVariable("id") Long id) {
        return Result.success(generatorService.deleteTable(id));
    }

    @Operation(summary = "生成代码")
    @PostMapping("/code/{id}")
    public Result<String> generateCode(@PathVariable("id") Long id) {
        String code = generatorService.generateCode(id);
        return Result.success(code);
    }
}