package com.xiaoyu.module.ai.service;

import com.xiaoyu.module.ai.entity.AiMessage;
import java.util.List;

public interface AiMessageService {

    List<AiMessage> getMessagesByConversationId(Long conversationId);

    AiMessage getMessageById(Long id);

    Long createMessage(AiMessage message);

    boolean deleteMessage(Long id);

    List<AiMessage> getConversationHistory(Long conversationId, Integer limit);
}