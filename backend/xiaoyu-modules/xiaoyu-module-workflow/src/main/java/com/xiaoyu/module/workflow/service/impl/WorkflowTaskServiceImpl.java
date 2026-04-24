package com.xiaoyu.module.workflow.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xiaoyu.module.workflow.entity.WorkflowHistory;
import com.xiaoyu.module.workflow.entity.WorkflowTask;
import com.xiaoyu.module.workflow.mapper.WorkflowTaskMapper;
import com.xiaoyu.module.workflow.service.WorkflowTaskService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class WorkflowTaskServiceImpl extends ServiceImpl<WorkflowTaskMapper, WorkflowTask> implements WorkflowTaskService {

    private final WorkflowTaskMapper workflowTaskMapper;

    @Override
    public List<WorkflowTask> getTodoTasks(Integer pageNum, Integer pageSize, Long assigneeId, String taskName) {
        QueryWrapper wrapper = QueryWrapper.create();
        if (assigneeId != null) {
            wrapper.and("assignee_id = {0}", assigneeId);
        }
        if (taskName != null && !taskName.isEmpty()) {
            wrapper.and("task_name like {0}", "%" + taskName + "%");
        }
        wrapper.and("status = 0");
        wrapper.orderBy("create_time", false);
        return workflowTaskMapper.selectListByQuery(wrapper);
    }

    @Override
    public List<WorkflowTask> getDoneTasks(Integer pageNum, Integer pageSize, Long assigneeId) {
        QueryWrapper wrapper = QueryWrapper.create();
        if (assigneeId != null) {
            wrapper.and("assignee_id = {0}", assigneeId);
        }
        wrapper.and("status = 1");
        wrapper.orderBy("complete_time", false);
        return workflowTaskMapper.selectListByQuery(wrapper);
    }

    @Override
    public WorkflowTask getTaskById(Long id) {
        return workflowTaskMapper.selectOneById(id);
    }

    @Override
    public boolean completeTask(Long id, String comment) {
        WorkflowTask task = workflowTaskMapper.selectOneById(id);
        if (task != null && task.getStatus() == 0) {
            task.setStatus(1);
            task.setCompleteTime(new Date());
            return workflowTaskMapper.update(task) > 0;
        }
        return false;
    }

    @Override
    public boolean rejectTask(Long id, String comment) {
        WorkflowTask task = workflowTaskMapper.selectOneById(id);
        if (task != null && task.getStatus() == 0) {
            task.setStatus(2);
            task.setCompleteTime(new Date());
            return workflowTaskMapper.update(task) > 0;
        }
        return false;
    }

    @Override
    public boolean transferTask(Long id, Long targetUserId) {
        WorkflowTask task = workflowTaskMapper.selectOneById(id);
        if (task != null && task.getStatus() == 0) {
            task.setAssigneeId(targetUserId);
            task.setTaskType(1);
            return workflowTaskMapper.update(task) > 0;
        }
        return false;
    }

    @Override
    public boolean delegateTask(Long id, Long delegateUserId) {
        WorkflowTask task = workflowTaskMapper.selectOneById(id);
        if (task != null && task.getStatus() == 0) {
            task.setAssigneeId(delegateUserId);
            task.setTaskType(2);
            return workflowTaskMapper.update(task) > 0;
        }
        return false;
    }

    @Override
    public boolean returnTask(Long id) {
        return rejectTask(id, "退回");
    }
}