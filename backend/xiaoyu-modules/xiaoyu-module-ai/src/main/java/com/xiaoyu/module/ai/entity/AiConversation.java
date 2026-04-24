package com.xiaoyu.module.ai.entity;

import com.mybatisflex.annotation.Table;
import com.xiaoyu.common.core.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * AI会话实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table("ai_conversation")
public class AiConversation extends TenantEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 会话标题
     */
    private String title;

    /**
     * AI模型编码
     */
    private String modelCode;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 知识库ID
     */
    private Long knowledgeId;

    /**
     * 会话状态(0-正常,1-已归档)
     */
    private Integer status;

    /**
     * 最后消息时间
     */
    private Date lastMessageTime;

    /**
     * 消息数量
     */
    private Integer messageCount;
}