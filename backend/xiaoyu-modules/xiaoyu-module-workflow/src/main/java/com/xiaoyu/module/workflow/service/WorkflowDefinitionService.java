package com.xiaoyu.module.workflow.service;

import com.xiaoyu.module.workflow.entity.WorkflowDefinition;
import java.util.List;

public interface WorkflowDefinitionService {

    List<WorkflowDefinition> getDefinitionPage(Integer pageNum, Integer pageSize, String name, String category, Integer status);

    WorkflowDefinition getDefinitionById(Long id);

    Long createDefinition(WorkflowDefinition definition);

    boolean updateDefinition(WorkflowDefinition definition);

    boolean deleteDefinition(Long id);

    boolean deployDefinition(Long id);

    boolean suspendDefinition(Long id);

    boolean activateDefinition(Long id);

    List<WorkflowDefinition> getDeployedList();
}