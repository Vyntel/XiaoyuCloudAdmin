package com.xiaoyu.module.im.controller;

import com.xiaoyu.module.im.entity.ImMessage;
import com.xiaoyu.module.im.service.ImMessageService;
import com.xiaoyu.common.core.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * IM消息 Controller
 */
@Tag(name = "IM消息", description = "IM消息管理")
@RestController
@RequestMapping("/im/message")
@RequiredArgsConstructor
public class ImMessageController {

    private final ImMessageService imMessageService;

    @Operation(summary = "获取消息列表")
    @GetMapping("/list")
    public Result<List<ImMessage>> getMessages(
            @RequestParam("conversationId") Long conversationId,
            @RequestParam(value = "beforeId", required = false) Long beforeId,
            @RequestParam(value = "limit", defaultValue = "20") Integer limit) {
        List<ImMessage> list = imMessageService.getMessages(conversationId, beforeId, limit);
        return Result.success(list);
    }

    @Operation(summary = "获取消息详情")
    @GetMapping("/{id}")
    public Result<ImMessage> getMessageById(@PathVariable("id") Long id) {
        return Result.success(imMessageService.getMessageById(id));
    }

    @Operation(summary = "发送消息")
    @PostMapping
    public Result<Long> sendMessage(@RequestBody ImMessage message) {
        Long id = imMessageService.sendMessage(message);
        return Result.success(id);
    }

    @Operation(summary = "撤回消息")
    @PostMapping("/{id}/recall")
    public Result<Boolean> recallMessage(@PathVariable("id") Long id) {
        Boolean success = imMessageService.recallMessage(id);
        return Result.success(success);
    }

    @Operation(summary = "搜索消息")
    @GetMapping("/search")
    public Result<List<ImMessage>> searchMessages(
            @RequestParam("keyword") String keyword,
            @RequestParam(value = "conversationId", required = false) Long conversationId) {
        List<ImMessage> list = imMessageService.searchMessages(keyword, conversationId);
        return Result.success(list);
    }

    @Operation(summary = "删除消息")
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteMessage(@PathVariable("id") Long id) {
        Boolean success = imMessageService.deleteMessage(id);
        return Result.success(success);
    }
}