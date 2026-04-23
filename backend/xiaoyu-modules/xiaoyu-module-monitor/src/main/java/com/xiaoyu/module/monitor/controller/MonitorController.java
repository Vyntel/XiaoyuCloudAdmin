package com.xiaoyu.module.monitor.controller;

import com.xiaoyu.module.monitor.entity.SysLog;
import com.xiaoyu.module.monitor.service.MonitorService;
import com.xiaoyu.common.core.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "系统监控", description = "系统监控管理")
@RestController
@RequestMapping("/monitor")
@RequiredArgsConstructor
public class MonitorController {
    private final MonitorService monitorService;

    @Operation(summary = "分页查询日志")
    @GetMapping("/log/page")
    public Result<List<SysLog>> getLogPage(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "module", required = false) String module,
            @RequestParam(value = "userId", required = false) Long userId) {
        List<SysLog> list = monitorService.getLogPage(pageNum, pageSize, module, userId);
        return Result.success(list);
    }

    @Operation(summary = "删除日志")
    @DeleteMapping("/log/{id}")
    public Result<Boolean> deleteLog(@PathVariable("id") Long id) {
        return Result.success(monitorService.deleteLog(id));
    }

    @Operation(summary = "清空日志")
    @DeleteMapping("/log/clear")
    public Result<Boolean> clearLogs() {
        return Result.success(monitorService.clearLogs());
    }
}