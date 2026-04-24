package com.xiaoyu.api.file;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;
import org.springframework.web.multipart.MultipartFile;

/**
 * 文件模块Feign接口
 */
@FeignClient(name = "xiaoyu-module-file", contextId = "fileApi")
public interface FileApi {

    @PostMapping("/file/upload")
    String upload(@RequestPart("file") MultipartFile file);

    @GetMapping("/file/info")
    String getFileInfo(@RequestParam("fileId") Long fileId);

    @DeleteMapping("/file/delete")
    String deleteFile(@RequestParam("fileId") Long fileId);
}