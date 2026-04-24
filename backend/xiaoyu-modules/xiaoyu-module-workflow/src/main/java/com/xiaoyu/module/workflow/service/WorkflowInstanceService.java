package com.xiaoyu.module.workflow.service;

import com.xiaoyu.module.workflow.entity.WorkflowInstance;
import java.util.List;

public interface WorkflowInstanceService {

    List<WorkflowInstance> getInstancePage(Integer pageNum, Integer pageSize, String name, Integer status, Long startUserId);

    WorkflowInstance getInstanceById(Long id);

    Long startInstance(Long definitionId, Long formDataId);

    boolean cancelInstance(Long id);

    boolean terminateInstance(Long id);

    List<WorkflowInstance> getMyInstances(Long userId);
}