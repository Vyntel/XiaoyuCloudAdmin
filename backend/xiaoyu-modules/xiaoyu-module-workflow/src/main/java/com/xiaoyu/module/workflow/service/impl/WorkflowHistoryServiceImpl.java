package com.xiaoyu.module.workflow.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xiaoyu.module.workflow.entity.WorkflowHistory;
import com.xiaoyu.module.workflow.mapper.WorkflowHistoryMapper;
import com.xiaoyu.module.workflow.service.WorkflowHistoryService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkflowHistoryServiceImpl extends ServiceImpl<WorkflowHistoryMapper, WorkflowHistory> implements WorkflowHistoryService {

    private final WorkflowHistoryMapper workflowHistoryMapper;

    @Override
    public List<WorkflowHistory> getHistoryList(Long instanceId, Long operateUserId, Integer operateType) {
        QueryWrapper wrapper = QueryWrapper.create();
        if (instanceId != null) {
            wrapper.where("instance_id", instanceId);
        }
        if (operateUserId != null) {
            wrapper.and("operate_user_id", operateUserId);
        }
        if (operateType != null) {
            wrapper.and("operate_type", operateType);
        }
        wrapper.and("deleted", 0);
        wrapper.orderBy("operate_time", false);
        
        return workflowHistoryMapper.selectListByQuery(wrapper);
    }

    @Override
    public WorkflowHistory getHistoryById(Long id) {
        return workflowHistoryMapper.selectOneById(id);
    }

    @Override
    public List<WorkflowHistory> getHistoryByInstanceId(Long instanceId) {
        QueryWrapper wrapper = QueryWrapper.create()
                .where("instance_id", instanceId)
                .and("deleted", 0)
                .orderBy("operate_time", false);
        return workflowHistoryMapper.selectListByQuery(wrapper);
    }
}