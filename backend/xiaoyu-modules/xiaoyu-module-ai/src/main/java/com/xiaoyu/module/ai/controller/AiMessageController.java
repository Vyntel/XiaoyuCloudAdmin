package com.xiaoyu.module.ai.controller;

import com.xiaoyu.module.ai.entity.AiMessage;
import com.xiaoyu.module.ai.service.AiMessageService;
import com.xiaoyu.common.core.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AI消息 Controller
 */
@Tag(name = "AI消息", description = "AI消息管理")
@RestController
@RequestMapping("/ai/message")
@RequiredArgsConstructor
public class AiMessageController {

    private final AiMessageService aiMessageService;

    @Operation(summary = "获取会话消息列表")
    @GetMapping("/conversation/{conversationId}")
    public Result<List<AiMessage>> getMessagesByConversationId(@PathVariable("conversationId") Long conversationId) {
        List<AiMessage> list = aiMessageService.getMessagesByConversationId(conversationId);
        return Result.success(list);
    }

    @Operation(summary = "获取消息详情")
    @GetMapping("/{id}")
    public Result<AiMessage> getMessageById(@PathVariable("id") Long id) {
        return Result.success(aiMessageService.getMessageById(id));
    }

    @Operation(summary = "创建消息")
    @PostMapping
    public Result<Long> createMessage(@RequestBody AiMessage message) {
        Long id = aiMessageService.createMessage(message);
        return Result.success(id);
    }

    @Operation(summary = "删除消息")
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteMessage(@PathVariable("id") Long id) {
        Boolean success = aiMessageService.deleteMessage(id);
        return Result.success(success);
    }

    @Operation(summary = "获取会话历史")
    @GetMapping("/history/{conversationId}")
    public Result<List<AiMessage>> getConversationHistory(
            @PathVariable("conversationId") Long conversationId,
            @RequestParam(value = "limit", required = false) Integer limit) {
        List<AiMessage> list = aiMessageService.getConversationHistory(conversationId, limit);
        return Result.success(list);
    }
}