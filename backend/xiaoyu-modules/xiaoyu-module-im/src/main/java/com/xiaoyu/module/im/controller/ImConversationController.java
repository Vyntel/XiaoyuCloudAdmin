package com.xiaoyu.module.im.controller;

import com.xiaoyu.module.im.entity.ImConversation;
import com.xiaoyu.module.im.service.ImConversationService;
import com.xiaoyu.common.core.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * IM会话 Controller
 */
@Tag(name = "IM会话", description = "IM会话管理")
@RestController
@RequestMapping("/im/conversation")
@RequiredArgsConstructor
public class ImConversationController {

    private final ImConversationService imConversationService;

    @Operation(summary = "获取用户会话列表")
    @GetMapping("/list")
    public Result<List<ImConversation>> getUserConversations(@RequestParam("userId") Long userId) {
        List<ImConversation> list = imConversationService.getUserConversations(userId);
        return Result.success(list);
    }

    @Operation(summary = "获取会话详情")
    @GetMapping("/{id}")
    public Result<ImConversation> getConversationById(@PathVariable("id") Long id) {
        return Result.success(imConversationService.getConversationById(id));
    }

    @Operation(summary = "创建会话")
    @PostMapping
    public Result<Long> createConversation(@RequestBody ImConversation conversation) {
        Long id = imConversationService.createConversation(conversation);
        return Result.success(id);
    }

    @Operation(summary = "更新会话")
    @PutMapping
    public Result<Boolean> updateConversation(@RequestBody ImConversation conversation) {
        Boolean success = imConversationService.updateConversation(conversation);
        return Result.success(success);
    }

    @Operation(summary = "删除会话")
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteConversation(@PathVariable("id") Long id) {
        Boolean success = imConversationService.deleteConversation(id);
        return Result.success(success);
    }

    @Operation(summary = "置顶会话")
    @PostMapping("/{id}/top")
    public Result<Boolean> setTop(@PathVariable("id") Long id, @RequestParam("top") boolean top) {
        Boolean success = imConversationService.setTop(id, top);
        return Result.success(success);
    }

    @Operation(summary = "免打扰")
    @PostMapping("/{id}/mute")
    public Result<Boolean> setMute(@PathVariable("id") Long id, @RequestParam("mute") boolean mute) {
        Boolean success = imConversationService.setMute(id, mute);
        return Result.success(success);
    }

    @Operation(summary = "标记已读")
    @PostMapping("/{id}/read")
    public Result<Boolean> markRead(@PathVariable("id") Long id) {
        Boolean success = imConversationService.markRead(id);
        return Result.success(success);
    }
}