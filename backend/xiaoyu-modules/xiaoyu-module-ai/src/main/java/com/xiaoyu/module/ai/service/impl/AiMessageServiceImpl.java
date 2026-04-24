package com.xiaoyu.module.ai.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xiaoyu.module.ai.entity.AiMessage;
import com.xiaoyu.module.ai.mapper.AiMessageMapper;
import com.xiaoyu.module.ai.service.AiMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiMessageServiceImpl extends ServiceImpl<AiMessageMapper, AiMessage> implements AiMessageService {

    private final AiMessageMapper aiMessageMapper;

    @Override
    public List<AiMessage> getMessagesByConversationId(Long conversationId) {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.where("conversation_id = {0}", conversationId);
        wrapper.orderBy("created_time", true);
        return aiMessageMapper.selectListByQuery(wrapper);
    }

    @Override
    public AiMessage getMessageById(Long id) {
        return aiMessageMapper.selectOneById(id);
    }

    @Override
    public Long createMessage(AiMessage message) {
        aiMessageMapper.insert(message, true);
        return message.getId();
    }

    @Override
    public boolean deleteMessage(Long id) {
        return aiMessageMapper.deleteById(id) > 0;
    }

    @Override
    public List<AiMessage> getConversationHistory(Long conversationId, Integer limit) {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.where("conversation_id = {0}", conversationId);
        wrapper.orderBy("created_time", false);
        if (limit != null && limit > 0) {
            wrapper.limit(limit);
        }
        return aiMessageMapper.selectListByQuery(wrapper);
    }
}