package com.xiaoyu.module.im.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xiaoyu.module.im.entity.ImGroup;
import com.xiaoyu.module.im.entity.ImGroupMember;
import com.xiaoyu.module.im.mapper.ImGroupMapper;
import com.xiaoyu.module.im.mapper.ImGroupMemberMapper;
import com.xiaoyu.module.im.service.ImGroupService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImGroupServiceImpl extends ServiceImpl<ImGroupMapper, ImGroup> implements ImGroupService {

    private final ImGroupMapper imGroupMapper;
    private final ImGroupMemberMapper imGroupMemberMapper;

    @Override
    public List<ImGroup> getGroupPage(Integer pageNum, Integer pageSize, String name, Integer status) {
        QueryWrapper wrapper = QueryWrapper.create();
        if (name != null && !name.isEmpty()) {
            wrapper.and("name like {0}", "%" + name + "%");
        }
        if (status != null) {
            wrapper.and("status = {0}", status);
        }
        wrapper.orderBy("create_time", false);
        return imGroupMapper.selectListByQuery(wrapper);
    }

    @Override
    public ImGroup getGroupById(Long id) {
        return imGroupMapper.selectOneById(id);
    }

    @Override
    public Long createGroup(ImGroup group) {
        group.setStatus(0);
        group.setMemberCount(1);
        imGroupMapper.insert(group, true);
        return group.getId();
    }

    @Override
    public boolean updateGroup(ImGroup group) {
        return imGroupMapper.update(group) > 0;
    }

    @Override
    public boolean dissolveGroup(Long id) {
        ImGroup group = imGroupMapper.selectOneById(id);
        if (group != null) {
            group.setStatus(1);
            return imGroupMapper.update(group) > 0;
        }
        return false;
    }

    @Override
    public boolean joinGroup(Long groupId, Long userId) {
        ImGroupMember member = new ImGroupMember();
        member.setGroupId(groupId);
        member.setUserId(userId);
        member.setRole(0);
        member.setJoinTime(new Date());
        member.setStatus(0);
        imGroupMemberMapper.insert(member, true);

        ImGroup group = imGroupMapper.selectOneById(groupId);
        if (group != null) {
            group.setMemberCount(group.getMemberCount() + 1);
            imGroupMapper.update(group);
        }
        return true;
    }

    @Override
    public boolean leaveGroup(Long groupId, Long userId) {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.where("group_id = {0}", groupId);
        wrapper.and("user_id = {0}", userId);
        imGroupMemberMapper.deleteByQuery(wrapper);

        ImGroup group = imGroupMapper.selectOneById(groupId);
        if (group != null && group.getMemberCount() > 0) {
            group.setMemberCount(group.getMemberCount() - 1);
            imGroupMapper.update(group);
        }
        return true;
    }

    @Override
    public boolean removeMember(Long groupId, Long userId) {
        return leaveGroup(groupId, userId);
    }

    @Override
    public boolean setAdmin(Long groupId, Long userId, boolean isAdmin) {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.where("group_id = {0}", groupId);
        wrapper.and("user_id = {0}", userId);
        ImGroupMember member = imGroupMemberMapper.selectOneByQuery(wrapper);
        if (member != null) {
            member.setRole(isAdmin ? 1 : 0);
            return imGroupMemberMapper.update(member) > 0;
        }
        return false;
    }

    @Override
    public List<ImGroupMember> getGroupMembers(Long groupId) {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.where("group_id = {0}", groupId);
        wrapper.and("status = 0");
        wrapper.orderBy("join_time", true);
        return imGroupMemberMapper.selectListByQuery(wrapper);
    }

    @Override
    public List<ImGroup> getUserGroups(Long userId) {
        QueryWrapper memberWrapper = QueryWrapper.create();
        memberWrapper.where("user_id = {0}", userId);
        memberWrapper.and("status = 0");
        List<ImGroupMember> members = imGroupMemberMapper.selectListByQuery(memberWrapper);

        return members.stream().map(m -> imGroupMapper.selectOneById(m.getGroupId())).toList();
    }

    @Override
    public boolean muteMember(Long memberId, Date muteEndTime) {
        ImGroupMember member = imGroupMemberMapper.selectOneById(memberId);
        if (member != null) {
            member.setMuteEndTime(muteEndTime);
            return imGroupMemberMapper.update(member) > 0;
        }
        return false;
    }

    @Override
    public boolean unmuteMember(Long memberId) {
        ImGroupMember member = imGroupMemberMapper.selectOneById(memberId);
        if (member != null) {
            member.setMuteEndTime(null);
            return imGroupMemberMapper.update(member) > 0;
        }
        return false;
    }

    @Override
    public boolean updateMemberNickname(Long memberId, String nickname) {
        ImGroupMember member = imGroupMemberMapper.selectOneById(memberId);
        if (member != null) {
            member.setNickname(nickname);
            return imGroupMemberMapper.update(member) > 0;
        }
        return false;
    }
}