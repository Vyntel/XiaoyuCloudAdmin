package com.xiaoyu.module.im.controller;

import com.xiaoyu.module.im.entity.ImGroup;
import com.xiaoyu.module.im.entity.ImGroupMember;
import com.xiaoyu.module.im.service.ImGroupService;
import com.xiaoyu.common.core.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * IM群组 Controller
 */
@Tag(name = "IM群组", description = "IM群组管理")
@RestController
@RequestMapping("/im/group")
@RequiredArgsConstructor
public class ImGroupController {

    private final ImGroupService imGroupService;

    @Operation(summary = "分页查询群组")
    @GetMapping("/page")
    public Result<List<ImGroup>> getGroupPage(
            @RequestParam(value = "pageNum", defaultValue = "1") Integer pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") Integer pageSize,
            @RequestParam(value = "name", required = false) String name,
            @RequestParam(value = "status", required = false) Integer status) {
        List<ImGroup> list = imGroupService.getGroupPage(pageNum, pageSize, name, status);
        return Result.success(list);
    }

    @Operation(summary = "获取群组详情")
    @GetMapping("/{id}")
    public Result<ImGroup> getGroupById(@PathVariable("id") Long id) {
        return Result.success(imGroupService.getGroupById(id));
    }

    @Operation(summary = "创建群组")
    @PostMapping
    public Result<Long> createGroup(@RequestBody ImGroup group) {
        Long id = imGroupService.createGroup(group);
        return Result.success(id);
    }

    @Operation(summary = "更新群组")
    @PutMapping
    public Result<Boolean> updateGroup(@RequestBody ImGroup group) {
        Boolean success = imGroupService.updateGroup(group);
        return Result.success(success);
    }

    @Operation(summary = "解散群组")
    @DeleteMapping("/{id}")
    public Result<Boolean> dissolveGroup(@PathVariable("id") Long id) {
        Boolean success = imGroupService.dissolveGroup(id);
        return Result.success(success);
    }

    @Operation(summary = "加入群组")
    @PostMapping("/{id}/join")
    public Result<Boolean> joinGroup(@PathVariable("id") Long groupId, @RequestParam("userId") Long userId) {
        Boolean success = imGroupService.joinGroup(groupId, userId);
        return Result.success(success);
    }

    @Operation(summary = "退群")
    @PostMapping("/{id}/leave")
    public Result<Boolean> leaveGroup(@PathVariable("id") Long groupId, @RequestParam("userId") Long userId) {
        Boolean success = imGroupService.leaveGroup(groupId, userId);
        return Result.success(success);
    }

    @Operation(summary = "踢出成员")
    @PostMapping("/{id}/remove")
    public Result<Boolean> removeMember(@PathVariable("id") Long groupId, @RequestParam("userId") Long userId) {
        Boolean success = imGroupService.removeMember(groupId, userId);
        return Result.success(success);
    }

    @Operation(summary = "设置管理员")
    @PostMapping("/{id}/admin")
    public Result<Boolean> setAdmin(@PathVariable("id") Long groupId, @RequestParam("userId") Long userId, @RequestParam("isAdmin") boolean isAdmin) {
        Boolean success = imGroupService.setAdmin(groupId, userId, isAdmin);
        return Result.success(success);
    }

    @Operation(summary = "获取群成员")
    @GetMapping("/{id}/members")
    public Result<List<ImGroupMember>> getGroupMembers(@PathVariable("id") Long groupId) {
        List<ImGroupMember> list = imGroupService.getGroupMembers(groupId);
        return Result.success(list);
    }

    @Operation(summary = "获取用户群组")
    @GetMapping("/user/{userId}")
    public Result<List<ImGroup>> getUserGroups(@PathVariable("userId") Long userId) {
        List<ImGroup> list = imGroupService.getUserGroups(userId);
        return Result.success(list);
    }
}