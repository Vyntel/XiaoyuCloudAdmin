package com.xiaoyu.module.workflow.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xiaoyu.module.workflow.entity.WorkflowDefinition;
import com.xiaoyu.module.workflow.mapper.WorkflowDefinitionMapper;
import com.xiaoyu.module.workflow.service.WorkflowDefinitionService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkflowDefinitionServiceImpl extends ServiceImpl<WorkflowDefinitionMapper, WorkflowDefinition> implements WorkflowDefinitionService {

    private final WorkflowDefinitionMapper workflowDefinitionMapper;

    @Override
    public List<WorkflowDefinition> getDefinitionPage(Integer pageNum, Integer pageSize, String name, String category, Integer status) {
        QueryWrapper wrapper = QueryWrapper.create();
        if (name != null && !name.isEmpty()) {
            wrapper.and("name like {0}", "%" + name + "%");
        }
        if (category != null && !category.isEmpty()) {
            wrapper.and("category = {0}", category);
        }
        if (status != null) {
            wrapper.and("status = {0}", status);
        }
        wrapper.orderBy("create_time", false);
        return workflowDefinitionMapper.selectListByQuery(wrapper);
    }

    @Override
    public WorkflowDefinition getDefinitionById(Long id) {
        return workflowDefinitionMapper.selectOneById(id);
    }

    @Override
    public Long createDefinition(WorkflowDefinition definition) {
        workflowDefinitionMapper.insert(definition, true);
        return definition.getId();
    }

    @Override
    public boolean updateDefinition(WorkflowDefinition definition) {
        return workflowDefinitionMapper.update(definition) > 0;
    }

    @Override
    public boolean deleteDefinition(Long id) {
        return workflowDefinitionMapper.deleteById(id) > 0;
    }

    @Override
    public boolean deployDefinition(Long id) {
        WorkflowDefinition definition = workflowDefinitionMapper.selectOneById(id);
        if (definition != null) {
            definition.setStatus(1);
            definition.setDeployTime(new java.util.Date());
            return workflowDefinitionMapper.update(definition) > 0;
        }
        return false;
    }

    @Override
    public boolean suspendDefinition(Long id) {
        WorkflowDefinition definition = workflowDefinitionMapper.selectOneById(id);
        if (definition != null) {
            definition.setStatus(2);
            return workflowDefinitionMapper.update(definition) > 0;
        }
        return false;
    }

    @Override
    public boolean activateDefinition(Long id) {
        WorkflowDefinition definition = workflowDefinitionMapper.selectOneById(id);
        if (definition != null) {
            definition.setStatus(1);
            return workflowDefinitionMapper.update(definition) > 0;
        }
        return false;
    }

    @Override
    public List<WorkflowDefinition> getDeployedList() {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.where("status = 1");
        wrapper.orderBy("deploy_time", false);
        return workflowDefinitionMapper.selectListByQuery(wrapper);
    }
}