package com.xiaoyu.module.file.controller;

import com.xiaoyu.common.core.result.Result;
import com.xiaoyu.module.file.entity.FileFolder;
import com.xiaoyu.module.file.service.FileFolderService;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 文件夹 Controller
 */
@Tag(name = "文件夹管理")
@RestController
@RequestMapping("/file/folder")
@RequiredArgsConstructor
public class FileFolderController {

    private final FileFolderService fileFolderService;

    /**
     * 获取文件夹树
     */
    @GetMapping("/tree")
    @Operation(summary = "获取文件夹树")
    public Result<List<FileFolder>> getFolderTree(@RequestParam(required = false) Long parentId) {
        List<FileFolder> list = fileFolderService.getFolderTree(parentId);
        return Result.success(list);
    }

    /**
     * 获取文件夹详情
     */
    @GetMapping("/{id}")
    @Operation(summary = "获取文件夹详情")
    public Result<FileFolder> getFolderById(@PathVariable Long id) {
        FileFolder folder = fileFolderService.getFolderById(id);
        return Result.success(folder);
    }

    /**
     * 创建文件夹
     */
    @PostMapping
    @Operation(summary = "创建文件夹")
    public Result<Long> createFolder(@RequestBody FileFolder folder) {
        Long id = fileFolderService.createFolder(folder);
        return Result.success(id);
    }

    /**
     * 更新文件夹
     */
    @PutMapping
    @Operation(summary = "更新文件夹")
    public Result<Boolean> updateFolder(@RequestBody FileFolder folder) {
        Boolean success = fileFolderService.updateFolder(folder);
        return Result.success(success);
    }

    /**
     * 删除文件夹
     */
    @DeleteMapping("/{id}")
    @Operation(summary = "删除文件夹")
    public Result<Boolean> deleteFolder(@PathVariable Long id) {
        Boolean success = fileFolderService.deleteFolder(id);
        return Result.success(success);
    }
}