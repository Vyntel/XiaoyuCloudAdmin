package com.xiaoyu.module.report.controller;

import com.xiaoyu.module.report.entity.ReportDefinition;
import com.xiaoyu.module.report.service.ReportService;
import com.xiaoyu.common.core.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

@Tag(name = "报表管理", description = "报表管理")
@RestController
@RequestMapping("/report")
@RequiredArgsConstructor
public class ReportController {

    private final ReportService reportService;

    @Operation(summary = "分页查询报表")
    @GetMapping("/page")
    public Result<List<ReportDefinition>> getReportPage(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "status", required = false) Integer status) {
        List<ReportDefinition> list = reportService.getReportPage(pageNum, pageSize, name, status);
        return Result.success(list);
    }

    @Operation(summary = "获取报表详情")
    @GetMapping("/{id}")
    public Result<ReportDefinition> getReportById(@PathVariable("id") Long id) {
        return Result.success(reportService.getReportById(id));
    }

    @Operation(summary = "创建报表")
    @PostMapping
    public Result<Long> createReport(@RequestBody ReportDefinition report) {
        Long id = reportService.createReport(report);
        return Result.success(id);
    }

    @Operation(summary = "更新报表")
    @PutMapping
    public Result<Boolean> updateReport(@RequestBody ReportDefinition report) {
        Boolean success = reportService.updateReport(report);
        return Result.success(success);
    }

    @Operation(summary = "删除报表")
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteReport(@PathVariable("id") Long id) {
        Boolean success = reportService.deleteReport(id);
        return Result.success(success);
    }

    @Operation(summary = "执行报表")
    @GetMapping("/{id}/execute")
    public Result<List<Object>> executeReport(@PathVariable("id") Long id) {
        List<Object> data = reportService.executeReport(id);
        return Result.success(data);
    }
}