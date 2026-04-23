package com.xiaoyu.module.job.controller;

import com.xiaoyu.module.job.entity.SysJob;
import com.xiaoyu.module.job.service.JobService;
import com.xiaoyu.common.core.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import java.util.List;

@Tag(name = "定时任务", description = "定时任务管理")
@RestController
@RequestMapping("/job")
@RequiredArgsConstructor
public class JobController {
    private final JobService jobService;

    @Operation(summary = "分页查询任务")
    @GetMapping("/page")
    public Result<List<SysJob>> getJobPage(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "jobName", required = false) String jobName,
            @RequestParam(value = "jobGroup", required = false) String jobGroup,
            @RequestParam(value = "status", required = false) Integer status) {
        List<SysJob> list = jobService.getJobPage(pageNum, pageSize, jobName, jobGroup, status);
        return Result.success(list);
    }

    @Operation(summary = "获取任务详情")
    @GetMapping("/{id}")
    public Result<SysJob> getJobById(@PathVariable("id") Long id) {
        return Result.success(jobService.getJobById(id));
    }

    @Operation(summary = "创建任务")
    @PostMapping
    public Result<Long> createJob(@RequestBody SysJob job) {
        return Result.success(jobService.createJob(job));
    }

    @Operation(summary = "更新任务")
    @PutMapping
    public Result<Boolean> updateJob(@RequestBody SysJob job) {
        return Result.success(jobService.updateJob(job));
    }

    @Operation(summary = "删除任务")
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteJob(@PathVariable("id") Long id) {
        return Result.success(jobService.deleteJob(id));
    }

    @Operation(summary = "执行任务")
    @PostMapping("/{id}/run")
    public Result<Boolean> runJob(@PathVariable("id") Long id) {
        return Result.success(jobService.runJob(id));
    }

    @Operation(summary = "停止任务")
    @PostMapping("/{id}/stop")
    public Result<Boolean> stopJob(@PathVariable("id") Long id) {
        return Result.success(jobService.stopJob(id));
    }
}