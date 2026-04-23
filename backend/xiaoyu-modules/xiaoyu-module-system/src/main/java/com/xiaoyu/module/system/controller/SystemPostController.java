package com.xiaoyu.module.system.controller;

import com.xiaoyu.common.core.result.Result;
import com.xiaoyu.module.system.entity.SystemPost;
import com.xiaoyu.module.system.service.SystemPostService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 系统岗位 Controller
 */
@Tag(name = "系统岗位", description = "系统岗位管理")
@RestController
@RequestMapping("/system/post")
@RequiredArgsConstructor
public class SystemPostController {

    private final SystemPostService systemPostService;

    /**
     * 获取岗位分页列表
     */
    @Operation(summary = "获取岗位分页列表")
    @GetMapping("/page")
    public Result<List<SystemPost>> getPostPage(
            @RequestParam(defaultValue = "1") Integer pageNum,
            @RequestParam(defaultValue = "10") Integer pageSize,
            @RequestParam(required = false) String postName,
            @RequestParam(required = false) Integer status) {
        return Result.success(systemPostService.getPostPage(pageNum, pageSize, postName, status));
    }

    /**
     * 获取所有岗位列表
     */
    @Operation(summary = "获取所有岗位列表")
    @GetMapping("/list")
    public Result<List<SystemPost>> getAllPosts() {
        return Result.success(systemPostService.getAllPosts());
    }

    /**
     * 获取岗位详情
     */
    @Operation(summary = "获取岗位详情")
    @GetMapping("/{postId}")
    public Result<SystemPost> getPostById(@PathVariable("postId") Long postId) {
        return Result.success(systemPostService.getPostById(postId));
    }

    /**
     * 新增岗位
     */
    @Operation(summary = "新增岗位")
    @PostMapping
    public Result<Long> createPost(@RequestBody SystemPost post) {
        return Result.success(systemPostService.createPost(post));
    }

    /**
     * 修改岗位
     */
    @Operation(summary = "修改岗位")
    @PutMapping
    public Result<Void> updatePost(@RequestBody SystemPost post) {
        systemPostService.updatePost(post);
        return Result.success();
    }

    /**
     * 删除岗位
     */
    @Operation(summary = "删除岗位")
    @DeleteMapping("/{postId}")
    public Result<Void> deletePost(@PathVariable("postId") Long postId) {
        systemPostService.deletePost(postId);
        return Result.success();
    }
}