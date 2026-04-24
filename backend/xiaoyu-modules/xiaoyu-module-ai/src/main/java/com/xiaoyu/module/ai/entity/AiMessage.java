package com.xiaoyu.module.ai.entity;

import com.mybatisflex.annotation.Table;
import com.xiaoyu.common.core.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * AI消息实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table("ai_message")
public class AiMessage extends TenantEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 会话ID
     */
    private Long conversationId;

    /**
     * 角色(user/assistant/system)
     */
    private String role;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 模型编码
     */
    private String modelCode;

    /**
     * tokens数量
     */
    private Integer tokens;

    /**
     * 是否收藏(0-否,1-是)
     */
    private Integer isFavorite;
}