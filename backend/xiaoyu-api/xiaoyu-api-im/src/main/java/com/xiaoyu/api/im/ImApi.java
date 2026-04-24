package com.xiaoyu.api.im;

import org.springframework.cloud.openfeign.FeignClient;
import org.springframework.web.bind.annotation.*;

/**
 * IM模块Feign接口
 */
@FeignClient(name = "xiaoyu-module-im", contextId = "imApi")
public interface ImApi {

    @GetMapping("/im/channels")
    String getChannels(@RequestParam("userId") Long userId);

    @GetMapping("/im/messages")
    String getMessages(@RequestParam("channelId") Long channelId,
                    @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
                    @RequestParam(value = "pageSize", defaultValue = "20") Integer pageSize);

    @PostMapping("/im/send")
    String sendMessage(@RequestBody String message);
}