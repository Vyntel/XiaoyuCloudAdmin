package com.xiaoyu.api.ai;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * AI模块Feign接口
 */
@FeignClient(name = "xiaoyu-module-ai", contextId = "aiApi")
public interface AiApi {

    @PostMapping("/ai/chat")
    String chat(@RequestBody String prompt);

    @GetMapping("/ai/models")
    String getModels();

    @PostMapping("/ai/image/generate")
    String generateImage(@RequestBody String prompt);
}