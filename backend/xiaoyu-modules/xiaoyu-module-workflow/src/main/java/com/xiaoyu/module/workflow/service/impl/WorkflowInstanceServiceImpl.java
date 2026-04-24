package com.xiaoyu.module.workflow.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xiaoyu.module.workflow.entity.WorkflowDefinition;
import com.xiaoyu.module.workflow.entity.WorkflowInstance;
import com.xiaoyu.module.workflow.mapper.WorkflowDefinitionMapper;
import com.xiaoyu.module.workflow.mapper.WorkflowInstanceMapper;
import com.xiaoyu.module.workflow.service.WorkflowInstanceService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkflowInstanceServiceImpl extends ServiceImpl<WorkflowInstanceMapper, WorkflowInstance> implements WorkflowInstanceService {

    private final WorkflowInstanceMapper workflowInstanceMapper;
    private final WorkflowDefinitionMapper workflowDefinitionMapper;

    @Override
    public List<WorkflowInstance> getInstancePage(Integer pageNum, Integer pageSize, String name, Integer status, Long startUserId) {
        QueryWrapper wrapper = QueryWrapper.create();
        if (name != null && !name.isEmpty()) {
            wrapper.and("name like {0}", "%" + name + "%");
        }
        if (status != null) {
            wrapper.and("status = {0}", status);
        }
        if (startUserId != null) {
            wrapper.and("start_user_id = {0}", startUserId);
        }
        wrapper.orderBy("create_time", false);
        return workflowInstanceMapper.selectListByQuery(wrapper);
    }

    @Override
    public WorkflowInstance getInstanceById(Long id) {
        return workflowInstanceMapper.selectOneById(id);
    }

    @Override
    public Long startInstance(Long definitionId, Long formDataId) {
        WorkflowDefinition definition = workflowDefinitionMapper.selectOneById(definitionId);
        if (definition == null || definition.getStatus() != 1) {
            return null;
        }

        WorkflowInstance instance = new WorkflowInstance();
        instance.setDefinitionId(definitionId);
        instance.setProcessKey(definition.getProcessKey());
        instance.setName(definition.getName());
        instance.setStatus(0);
        instance.setStartTime(new Date());
        workflowInstanceMapper.insert(instance, true);
        return instance.getId();
    }

    @Override
    public boolean cancelInstance(Long id) {
        WorkflowInstance instance = workflowInstanceMapper.selectOneById(id);
        if (instance != null) {
            instance.setStatus(2);
            instance.setEndTime(new Date());
            return workflowInstanceMapper.update(instance) > 0;
        }
        return false;
    }

    @Override
    public boolean terminateInstance(Long id) {
        WorkflowInstance instance = workflowInstanceMapper.selectOneById(id);
        if (instance != null) {
            instance.setStatus(3);
            instance.setEndTime(new Date());
            return workflowInstanceMapper.update(instance) > 0;
        }
        return false;
    }

    @Override
    public List<WorkflowInstance> getMyInstances(Long userId) {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.where("start_user_id = {0}", userId);
        wrapper.orderBy("create_time", false);
        return workflowInstanceMapper.selectListByQuery(wrapper);
    }
}