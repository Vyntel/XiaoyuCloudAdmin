package com.xiaoyu.module.workflow.service;

import com.xiaoyu.module.workflow.entity.WorkflowTask;
import java.util.List;

public interface WorkflowTaskService {

    List<WorkflowTask> getTodoTasks(Integer pageNum, Integer pageSize, Long assigneeId, String taskName);

    List<WorkflowTask> getDoneTasks(Integer pageNum, Integer pageSize, Long assigneeId);

    WorkflowTask getTaskById(Long id);

    boolean completeTask(Long id, String comment);

    boolean rejectTask(Long id, String comment);

    boolean transferTask(Long id, Long targetUserId);

    boolean delegateTask(Long id, Long delegateUserId);

    boolean returnTask(Long id);
}