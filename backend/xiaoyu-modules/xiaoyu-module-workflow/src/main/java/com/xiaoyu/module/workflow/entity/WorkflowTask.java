package com.xiaoyu.module.workflow.entity;

import com.mybatisflex.annotation.Table;
import com.xiaoyu.common.core.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 流程任务实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table("wf_task")
public class WorkflowTask extends TenantEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 流程实例ID
     */
    private Long instanceId;

    /**
     * 流程定义ID
     */
    private Long definitionId;

    /**
     * 任务名称
     */
    private String taskName;

    /**
     * 任务Key
     */
    private String taskKey;

    /**
     * 办理人ID
     */
    private Long assigneeId;

    /**
     * 办理人名称
     */
    private String assigneeName;

    /**
     * 候选用户ID列表(JSON)
     */
    private String candidateUsers;

    /**
     * 候选角色ID列表(JSON)
     */
    private String candidateRoles;

    /**
     * 表单数据ID
     */
    private Long formDataId;

    /**
     * Flowable任务ID
     */
    private String flowableTaskId;

    /**
     * 任务类型(0-正常,1-转办,2-委托,3-加签)
     */
    private Integer taskType;

    /**
     * 状态(0-待处理,1-已完成,2-已取消)
     */
    private Integer status;

    /**
     * 优先级(0-普通,1-紧急,2-加急)
     */
    private Integer priority;

    /**
     * 创建时间
     */
    private Date createTime;

    /**
     * 完成时间
     */
    private Date completeTime;
}