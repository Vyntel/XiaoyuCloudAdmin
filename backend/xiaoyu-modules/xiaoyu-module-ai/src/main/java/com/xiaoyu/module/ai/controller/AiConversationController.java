package com.xiaoyu.module.ai.controller;

import com.xiaoyu.module.ai.entity.AiConversation;
import com.xiaoyu.module.ai.service.AiConversationService;
import com.xiaoyu.common.core.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AI会话 Controller
 */
@Tag(name = "AI会话", description = "AI会话管理")
@RestController
@RequestMapping("/ai/conversation")
@RequiredArgsConstructor
public class AiConversationController {

    private final AiConversationService aiConversationService;

    @Operation(summary = "分页查询会话列表")
    @GetMapping("/page")
    public Result<List<AiConversation>> getConversationPage(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "userId", required = false) Long userId,
            @RequestParam(value = "title", required = false) String title,
            @RequestParam(value = "status", required = false) Integer status) {
        List<AiConversation> list = aiConversationService.getConversationPage(pageNum, pageSize, userId, title, status);
        return Result.success(list);
    }

    @Operation(summary = "获取会话详情")
    @GetMapping("/{id}")
    public Result<AiConversation> getConversationById(@PathVariable("id") Long id) {
        return Result.success(aiConversationService.getConversationById(id));
    }

    @Operation(summary = "创建会话")
    @PostMapping
    public Result<Long> createConversation(@RequestBody AiConversation conversation) {
        Long id = aiConversationService.createConversation(conversation);
        return Result.success(id);
    }

    @Operation(summary = "更新会话")
    @PutMapping
    public Result<Boolean> updateConversation(@RequestBody AiConversation conversation) {
        Boolean success = aiConversationService.updateConversation(conversation);
        return Result.success(success);
    }

    @Operation(summary = "删除会话")
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteConversation(@PathVariable("id") Long id) {
        Boolean success = aiConversationService.deleteConversation(id);
        return Result.success(success);
    }

    @Operation(summary = "获取用户会话列表")
    @GetMapping("/user/{userId}")
    public Result<List<AiConversation>> getUserConversations(@PathVariable("userId") Long userId) {
        List<AiConversation> list = aiConversationService.getUserConversations(userId);
        return Result.success(list);
    }
}