package com.xiaoyu.module.ai.service;

import com.xiaoyu.module.ai.entity.AiConversation;
import com.xiaoyu.common.core.result.PageResult;
import java.util.List;

public interface AiConversationService {

    List<AiConversation> getConversationPage(Integer pageNum, Integer pageSize, Long userId, String title, Integer status);

    AiConversation getConversationById(Long id);

    Long createConversation(AiConversation conversation);

    boolean updateConversation(AiConversation conversation);

    boolean deleteConversation(Long id);

    List<AiConversation> getUserConversations(Long userId);
}