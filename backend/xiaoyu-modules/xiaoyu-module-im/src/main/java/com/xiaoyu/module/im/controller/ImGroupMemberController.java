package com.xiaoyu.module.im.controller;

import com.mybatisflex.core.query.QueryWrapper;
import com.xiaoyu.module.im.entity.ImGroup;
import com.xiaoyu.module.im.entity.ImGroupMember;
import com.xiaoyu.module.im.mapper.ImGroupMemberMapper;
import com.xiaoyu.module.im.service.ImGroupService;
import com.xiaoyu.common.core.result.Result;
import io.swagger.v3.oas.annotations.Operation;
import io.swagger.v3.oas.annotations.tags.Tag;
import lombok.RequiredArgsConstructor;
import org.springframework.web.bind.annotation.*;

import java.util.Date;
import java.util.List;

@Tag(name = "IM群组成员", description = "IM群组成员管理")
@RestController
@RequestMapping("/im/group-member")
@RequiredArgsConstructor
public class ImGroupMemberController {

    private final ImGroupMemberMapper imGroupMemberMapper;
    private final ImGroupService imGroupService;

    @Operation(summary = "获取群组成员列表")
    @GetMapping("/{groupId}")
    public Result<List<ImGroupMember>> getGroupMembers(@PathVariable("groupId") Long groupId) {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.where("group_id = {0}", groupId);
        wrapper.and("status = 0");
        wrapper.orderBy("join_time", true);
        List<ImGroupMember> list = imGroupMemberMapper.selectListByQuery(wrapper);
        return Result.success(list);
    }

    @Operation(summary = "禁言成员")
    @PutMapping("/{memberId}/mute")
    public Result<Boolean> muteMember(@PathVariable("memberId") Long memberId, @RequestParam("muteEndTime") Date muteEndTime) {
        Boolean success = imGroupService.muteMember(memberId, muteEndTime);
        return Result.success(success);
    }

    @Operation(summary = "取消禁言")
    @PutMapping("/{memberId}/unmute")
    public Result<Boolean> unmuteMember(@PathVariable("memberId") Long memberId) {
        Boolean success = imGroupService.unmuteMember(memberId);
        return Result.success(success);
    }

    @Operation(summary = "修改群昵称")
    @PutMapping("/{memberId}/nickname")
    public Result<Boolean> updateNickname(@PathVariable("memberId") Long memberId, @RequestParam("nickname") String nickname) {
        Boolean success = imGroupService.updateMemberNickname(memberId, nickname);
        return Result.success(success);
    }

    @Operation(summary = "获取用户加入的群组列表")
    @GetMapping("/user/{userId}/groups")
    public Result<List<ImGroup>> getUserGroups(@PathVariable("userId") Long userId) {
        List<ImGroup> list = imGroupService.getUserGroups(userId);
        return Result.success(list);
    }

    @Operation(summary = "添加群成员")
    @PostMapping
    public Result<Long> addMember(@RequestBody ImGroupMember member) {
        imGroupMemberMapper.insert(member, true);
        return Result.success(member.getId());
    }

    @Operation(summary = "移除群成员")
    @DeleteMapping("/{memberId}")
    public Result<Boolean> removeMember(@PathVariable("memberId") Long memberId) {
        int rows = imGroupMemberMapper.deleteById(memberId);
        return Result.success(rows > 0);
    }
}
