package com.xiaoyu.module.im.service;

import com.xiaoyu.module.im.entity.ImConversation;
import java.util.List;

public interface ImConversationService {

    List<ImConversation> getUserConversations(Long userId);

    ImConversation getConversationById(Long id);

    ImConversation getConversationByUsers(Long userId1, Long userId2);

    Long createConversation(ImConversation conversation);

    boolean updateConversation(ImConversation conversation);

    boolean deleteConversation(Long id);

    boolean setTop(Long id, boolean top);

    boolean setMute(Long id, boolean mute);

    boolean markRead(Long id);
}