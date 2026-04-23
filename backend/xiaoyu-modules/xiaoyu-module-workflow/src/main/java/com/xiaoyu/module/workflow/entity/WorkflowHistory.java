package com.xiaoyu.module.workflow.entity;

import com.mybatisflex.annotation.Table;
import com.xiaoyu.common.core.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 流程历史实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table("wf_history")
public class WorkflowHistory extends TenantEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 流程实例ID
     */
    private Long instanceId;

    /**
     * 任务ID
     */
    private Long taskId;

    /**
     * 流程定义ID
     */
    private Long definitionId;

    /**
     * 流程定义版本
     */
    private Integer definitionVersion;

    /**
     * 节点Key
     */
    private String nodeKey;

    /**
     * 节点名称
     */
    private String nodeName;

    /**
     * 操作类型(0-提交,1-同意,2-拒绝,3-转办,4-委托,5-退回,6-撤回,7-终止)
     */
    private Integer operateType;

    /**
     * 操作人ID
     */
    private Long operateUserId;

    /**
     * 操作人名称
     */
    private String operateUserName;

    /**
     * 审批意见
     */
    private String comment;

    /**
     * 操作时间
     */
    private Date operateTime;

    /**
     * 流程实例状态
     */
    private Integer instanceStatus;
}