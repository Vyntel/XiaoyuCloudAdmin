package com.xiaoyu.common.core.entity;

import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 租户实体基类 - 包含租户相关字段
 */
@Data
@EqualsAndHashCode(callSuper = true)
public abstract class TenantEntity extends BaseEntity {

    private static final long serialVersionUID = 1L;

    /** 租户ID */
    protected Long tenantId;

    /** 状态（0-正常，1-禁用） */
    protected Integer status;
}