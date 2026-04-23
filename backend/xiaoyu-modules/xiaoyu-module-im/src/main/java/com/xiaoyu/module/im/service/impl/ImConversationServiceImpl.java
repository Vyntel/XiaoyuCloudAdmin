package com.xiaoyu.module.im.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xiaoyu.module.im.entity.ImConversation;
import com.xiaoyu.module.im.mapper.ImConversationMapper;
import com.xiaoyu.module.im.service.ImConversationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImConversationServiceImpl extends ServiceImpl<ImConversationMapper, ImConversation> implements ImConversationService {

    private final ImConversationMapper imConversationMapper;

    @Override
    public List<ImConversation> getUserConversations(Long userId) {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.where("1=1");
        wrapper.orderBy("status", true);
        wrapper.orderBy("last_message_time", false);
        return imConversationMapper.selectListByQuery(wrapper);
    }

    @Override
    public ImConversation getConversationById(Long id) {
        return imConversationMapper.selectOneById(id);
    }

    @Override
    public ImConversation getConversationByUsers(Long userId1, Long userId2) {
        return null;
    }

    @Override
    public Long createConversation(ImConversation conversation) {
        imConversationMapper.insert(conversation, true);
        return conversation.getId();
    }

    @Override
    public boolean updateConversation(ImConversation conversation) {
        return imConversationMapper.update(conversation) > 0;
    }

    @Override
    public boolean deleteConversation(Long id) {
        return imConversationMapper.deleteById(id) > 0;
    }

    @Override
    public boolean setTop(Long id, boolean top) {
        ImConversation conversation = imConversationMapper.selectOneById(id);
        if (conversation != null) {
            conversation.setStatus(top ? 1 : 0);
            return imConversationMapper.update(conversation) > 0;
        }
        return false;
    }

    @Override
    public boolean setMute(Long id, boolean mute) {
        ImConversation conversation = imConversationMapper.selectOneById(id);
        if (conversation != null) {
            conversation.setStatus(mute ? 2 : 0);
            return imConversationMapper.update(conversation) > 0;
        }
        return false;
    }

    @Override
    public boolean markRead(Long id) {
        ImConversation conversation = imConversationMapper.selectOneById(id);
        if (conversation != null) {
            conversation.setUnreadCount(0);
            return imConversationMapper.update(conversation) > 0;
        }
        return false;
    }
}