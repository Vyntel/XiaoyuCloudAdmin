package com.xiaoyu.module.im.service;

import com.xiaoyu.module.im.entity.ImMessage;
import java.util.List;

public interface ImMessageService {

    List<ImMessage> getMessages(Long conversationId, Long beforeId, Integer limit);

    ImMessage getMessageById(Long id);

    Long sendMessage(ImMessage message);

    boolean recallMessage(Long id);

    List<ImMessage> searchMessages(String keyword, Long conversationId);

    boolean deleteMessage(Long id);
}