package com.xiaoyu.module.ai.controller;

import com.xiaoyu.module.ai.entity.AiKnowledge;
import com.xiaoyu.module.ai.service.AiKnowledgeService;
import com.xiaoyu.common.core.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * AI知识库 Controller
 */
@Tag(name = "AI知识库", description = "AI知识库管理")
@RestController
@RequestMapping("/ai/knowledge")
@RequiredArgsConstructor
public class AiKnowledgeController {

    private final AiKnowledgeService aiKnowledgeService;

    @Operation(summary = "分页查询知识库列表")
    @GetMapping("/page")
    public Result<List<AiKnowledge>> getKnowledgePage(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "status", required = false) Integer status) {
        List<AiKnowledge> list = aiKnowledgeService.getKnowledgePage(pageNum, pageSize, name, status);
        return Result.success(list);
    }

    @Operation(summary = "获取知识库详情")
    @GetMapping("/{id}")
    public Result<AiKnowledge> getKnowledgeById(@PathVariable("id") Long id) {
        return Result.success(aiKnowledgeService.getKnowledgeById(id));
    }

    @Operation(summary = "创建知识库")
    @PostMapping
    public Result<Long> createKnowledge(@RequestBody AiKnowledge knowledge) {
        Long id = aiKnowledgeService.createKnowledge(knowledge);
        return Result.success(id);
    }

    @Operation(summary = "更新知识库")
    @PutMapping
    public Result<Boolean> updateKnowledge(@RequestBody AiKnowledge knowledge) {
        Boolean success = aiKnowledgeService.updateKnowledge(knowledge);
        return Result.success(success);
    }

    @Operation(summary = "删除知识库")
    @DeleteMapping("/{id}")
    public Result<Boolean> deleteKnowledge(@PathVariable("id") Long id) {
        Boolean success = aiKnowledgeService.deleteKnowledge(id);
        return Result.success(success);
    }

    @Operation(summary = "获取启用的知识库列表")
    @GetMapping("/enabled")
    public Result<List<AiKnowledge>> getEnabledList() {
        List<AiKnowledge> list = aiKnowledgeService.getEnabledList();
        return Result.success(list);
    }
}