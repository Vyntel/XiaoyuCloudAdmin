package com.xiaoyu.module.workflow.entity;

import com.mybatisflex.annotation.Table;
import com.xiaoyu.common.core.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 流程实例实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table("wf_instance")
public class WorkflowInstance extends TenantEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 流程定义ID
     */
    private Long definitionId;

    /**
     * 流程Key
     */
    private String processKey;

    /**
     * 流程名称
     */
    private String name;

    /**
     * 发起人ID
     */
    private Long startUserId;

    /**
     * 发起人名称
     */
    private String startUserName;

    /**
     * 当前节点Key
     */
    private String currentNodeKey;

    /**
     * 当前节点名称
     */
    private String currentNodeName;

    /**
     * 表单数据ID
     */
    private Long formDataId;

    /**
     * Flowable流程实例ID
     */
    private String flowableInstanceId;

    /**
     * 状态(0-进行中,1-已完成,2-已取消,3-已驳回)
     */
    private Integer status;

    /**
     * 开始时间
     */
    private Date startTime;

    /**
     * 结束时间
     */
    private Date endTime;

    /**
     * 耗时(秒)
     */
    private Long duration;
}