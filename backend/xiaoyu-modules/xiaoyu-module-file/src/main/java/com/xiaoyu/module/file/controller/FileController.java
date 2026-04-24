package com.xiaoyu.module.file.controller;

import com.xiaoyu.module.file.entity.FileInfo;
import com.xiaoyu.module.file.service.FileService;
import com.xiaoyu.common.core.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

import java.util.List;

/**
 * 文件管理 Controller
 */
@Tag(name = "文件管理", description = "文件管理")
@RestController
@RequestMapping("/file")
@RequiredArgsConstructor
public class FileController {

    private final FileService fileService;

    @Operation(summary = "分页查询文件列表")
    @GetMapping("/page")
    public Result<List<FileInfo>> getFilePage(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "fileName", required = false) String fileName,
            @RequestParam(value = "folderId", required = false) Long folderId,
            @RequestParam(value = "storageType", required = false) String storageType) {
        List<FileInfo> list = fileService.getFilePage(pageNum, pageSize, fileName, folderId, storageType);
        return Result.success(list);
    }

    @Operation(summary = "获取文件详情")
    @GetMapping("/{id}")
    public Result<FileInfo> getFileById(@PathVariable("id") Long id) {
        return Result.success(fileService.getFileById(id));
    }

    @Operation(summary = "上传文件")
    @PostMapping("/upload")
    public Result<Long> uploadFile(@RequestParam("file") MultipartFile file) {
        FileInfo fileInfo = new FileInfo();
        fileInfo.setFileName(file.getOriginalFilename());
        fileInfo.setFileSize(file.getSize());
        fileInfo.setMimeType(file.getContentType());
        Long id = fileService.uploadFile(fileInfo);
        return Result.success(id);
    }

    @Operation(summary = "删除文件")
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteFile(@PathVariable("id") Long id) {
        Boolean success = fileService.deleteFile(id);
        return Result.success(success);
    }

    @Operation(summary = "批量删除文件")
    @PostMapping("/batch-delete")
    public Result<Boolean> batchDelete(@RequestBody List<Long> ids) {
        Boolean success = fileService.batchDelete(ids);
        return Result.success(success);
    }

    @Operation(summary = "更新文件信息")
    @PutMapping
    public Result<Boolean> updateFile(@RequestBody FileInfo fileInfo) {
        Boolean success = fileService.updateFile(fileInfo);
        return Result.success(success);
    }

    @Operation(summary = "获取文件URL")
    @GetMapping("/{id}/url")
    public Result<String> getFileUrl(@PathVariable("id") Long id) {
        String url = fileService.getFileUrl(id);
        return Result.success(url);
    }

    @Operation(summary = "复制文件")
    @PostMapping("/{id}/copy")
    public Result<Long> copyFile(@PathVariable("id") Long id, @RequestParam("targetFolderId") Long targetFolderId) {
        Long newId = fileService.copyFile(id, targetFolderId);
        return Result.success(newId);
    }
}