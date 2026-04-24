package com.xiaoyu.module.workflow.service;

import com.xiaoyu.module.workflow.entity.WorkflowHistory;

import java.util.List;

/**
 * 流程历史 Service
 */
public interface WorkflowHistoryService {

    /**
     * 获取流程历史列表
     */
    List<WorkflowHistory> getHistoryList(Long instanceId, Long operateUserId, Integer operateType);

    /**
     * 获取流程历史详情
     */
    WorkflowHistory getHistoryById(Long id);

    /**
     * 获取流程实例的所有历史记录
     */
    List<WorkflowHistory> getHistoryByInstanceId(Long instanceId);
}