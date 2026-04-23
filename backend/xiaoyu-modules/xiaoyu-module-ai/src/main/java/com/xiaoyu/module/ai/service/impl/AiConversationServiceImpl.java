package com.xiaoyu.module.ai.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xiaoyu.module.ai.entity.AiConversation;
import com.xiaoyu.module.ai.mapper.AiConversationMapper;
import com.xiaoyu.module.ai.service.AiConversationService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiConversationServiceImpl extends ServiceImpl<AiConversationMapper, AiConversation> implements AiConversationService {

    private final AiConversationMapper aiConversationMapper;

    @Override
    public List<AiConversation> getConversationPage(Integer pageNum, Integer pageSize, Long userId, String title, Integer status) {
        QueryWrapper wrapper = QueryWrapper.create();
        if (userId != null) {
            wrapper.where("user_id = {0}", userId);
        }
        if (title != null && !title.isEmpty()) {
            wrapper.and("title like {0}", "%" + title + "%");
        }
        if (status != null) {
            wrapper.and("status = {0}", status);
        }
        wrapper.orderByDesc("created_time");
        return aiConversationMapper.selectListByQuery(wrapper);
    }

    @Override
    public AiConversation getConversationById(Long id) {
        return aiConversationMapper.selectOneById(id);
    }

    @Override
    public Long createConversation(AiConversation conversation) {
        aiConversationMapper.insert(conversation, true);
        return conversation.getId();
    }

    @Override
    public boolean updateConversation(AiConversation conversation) {
        return aiConversationMapper.update(conversation) > 0;
    }

    @Override
    public boolean deleteConversation(Long id) {
        return aiConversationMapper.deleteById(id) > 0;
    }

    @Override
    public List<AiConversation> getUserConversations(Long userId) {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.where("user_id = {0}", userId);
        wrapper.orderByDesc("created_time");
        return aiConversationMapper.selectListByQuery(wrapper);
    }
}