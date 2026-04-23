package com.xiaoyu.module.im.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xiaoyu.module.im.entity.ImMessage;
import com.xiaoyu.module.im.mapper.ImMessageMapper;
import com.xiaoyu.module.im.service.ImMessageService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ImMessageServiceImpl extends ServiceImpl<ImMessageMapper, ImMessage> implements ImMessageService {

    private final ImMessageMapper imMessageMapper;

    @Override
    public List<ImMessage> getMessages(Long conversationId, Long beforeId, Integer limit) {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.where("conversation_id = {0}", conversationId);
        if (beforeId != null) {
            wrapper.and("id < {0}", beforeId);
        }
        wrapper.orderBy("send_time", false);
        if (limit != null && limit > 0) {
            wrapper.limit(limit);
        }
        return imMessageMapper.selectListByQuery(wrapper);
    }

    @Override
    public ImMessage getMessageById(Long id) {
        return imMessageMapper.selectOneById(id);
    }

    @Override
    public Long sendMessage(ImMessage message) {
        message.setSendTime(new Date());
        message.setStatus(0);
        imMessageMapper.insert(message, true);
        return message.getId();
    }

    @Override
    public boolean recallMessage(Long id) {
        ImMessage message = imMessageMapper.selectOneById(id);
        if (message != null) {
            message.setStatus(1);
            return imMessageMapper.update(message) > 0;
        }
        return false;
    }

    @Override
    public List<ImMessage> searchMessages(String keyword, Long conversationId) {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.where("content like {0}", "%" + keyword + "%");
        if (conversationId != null) {
            wrapper.and("conversation_id = {0}", conversationId);
        }
        wrapper.orderBy("send_time", false);
        return imMessageMapper.selectListByQuery(wrapper);
    }

    @Override
    public boolean deleteMessage(Long id) {
        return imMessageMapper.deleteById(id) > 0;
    }
}