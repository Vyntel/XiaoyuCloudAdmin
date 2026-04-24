package com.xiaoyu.module.workflow.entity;

import com.mybatisflex.annotation.Table;
import com.xiaoyu.common.core.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 流程定义实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table("wf_definition")
public class WorkflowDefinition extends TenantEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 流程Key
     */
    private String processKey;

    /**
     * 流程名称
     */
    private String name;

    /**
     * 流程描述
     */
    private String description;

    /**
     * 流程分类
     */
    private String category;

    /**
     * BPMN XML内容
     */
    private String bpmnXml;

    /**
     * 流程图KEY
     */
    private String diagramKey;

    /**
     * 版本号
     */
    private Integer version;

    /**
     * 状态(0-草稿,1-已部署,2-挂起)
     */
    private Integer status;

    /**
     * Flowable流程定义ID
     */
    private String flowableDefinitionId;

    /**
     * 发布时间
     */
    private Date deployTime;
}