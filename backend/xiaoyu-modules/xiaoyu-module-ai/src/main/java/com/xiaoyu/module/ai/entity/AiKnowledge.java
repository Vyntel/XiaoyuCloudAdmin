package com.xiaoyu.module.ai.entity;

import com.mybatisflex.annotation.Table;
import com.xiaoyu.common.core.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * AI知识库实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table("ai_knowledge")
public class AiKnowledge extends TenantEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 知识库名称
     */
    private String name;

    /**
     * 知识库描述
     */
    private String description;

    /**
     * 嵌入模型
     */
    private String embeddingModel;

    /**
     * 文档数量
     */
    private Integer documentCount;

    /**
     * 分段数量
     */
    private Integer chunkCount;

    /**
     * 状态(0-创建中,1-就绪,2-处理中)
     */
    private Integer status;

    /**
     * 处理进度
     */
    private Integer progress;

    /**
     * 处理完成时间
     */
    private Date finishTime;
}