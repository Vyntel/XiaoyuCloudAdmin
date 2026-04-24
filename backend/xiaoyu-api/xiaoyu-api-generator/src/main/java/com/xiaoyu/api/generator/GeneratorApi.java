package com.xiaoyu.api.generator;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * 代码生成模块Feign接口
 */
@FeignClient(name = "xiaoyu-module-generator", contextId = "generatorApi")
public interface GeneratorApi {

    @GetMapping("/generator/tables")
    String getTables();

    @GetMapping("/generator/code")
    String generateCode(@RequestParam("tableName") String tableName);

    @PostMapping("/generator/download")
    String downloadCode(@RequestBody String tableNames);
}