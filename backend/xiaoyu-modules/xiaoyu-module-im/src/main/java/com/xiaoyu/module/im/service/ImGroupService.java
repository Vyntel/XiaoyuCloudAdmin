package com.xiaoyu.module.im.service;

import com.xiaoyu.module.im.entity.ImGroup;
import com.xiaoyu.module.im.entity.ImGroupMember;

import java.util.Date;
import java.util.List;

public interface ImGroupService {

    List<ImGroup> getGroupPage(Integer pageNum, Integer pageSize, String name, Integer status);

    ImGroup getGroupById(Long id);

    Long createGroup(ImGroup group);

    boolean updateGroup(ImGroup group);

    boolean dissolveGroup(Long id);

    boolean joinGroup(Long groupId, Long userId);

    boolean leaveGroup(Long groupId, Long userId);

    boolean removeMember(Long groupId, Long userId);

    boolean setAdmin(Long groupId, Long userId, boolean isAdmin);

    List<ImGroupMember> getGroupMembers(Long groupId);

    List<ImGroup> getUserGroups(Long userId);

    boolean muteMember(Long memberId, Date muteEndTime);

    boolean unmuteMember(Long memberId);

    boolean updateMemberNickname(Long memberId, String nickname);
}