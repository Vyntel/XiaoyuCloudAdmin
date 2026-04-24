package com.xiaoyu.module.ai.entity;

import com.mybatisflex.annotation.Table;
import com.xiaoyu.common.core.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.math.BigDecimal;

/**
 * AI模型配置实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table("ai_model")
public class AiModel extends TenantEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 模型名称
     */
    private String name;

    /**
     * 模型编码
     */
    private String code;

    /**
     * 提供商(openai/aliyun/zhipu/moonshot)
     */
    private String provider;

    /**
     * API地址
     */
    private String apiUrl;

    /**
     * API Key(加密存储)
     */
    private String apiKey;

    /**
     * 模型类型
     */
    private String modelType;

    /**
     * 默认温度
     */
    private BigDecimal temperature;

    /**
     * 最大Tokens
     */
    private Integer maxTokens;

    /**
     * 支持方式
     */
    private String capabilities;

    /**
     * 价格(每千tokens)
     */
    private BigDecimal price;

    /**
     * 状态(0-禁用,1-启用)
     */
    private Integer status;

    /**
     * 排序
     */
    private Integer sort;

    /**
     * 备注
     */
    private String remark;
}