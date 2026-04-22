package com.xiaoyu.common.core.entity;

import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础实体类 - 包含通用字段
 */
@Data
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /** 创建时间 */
    protected Date createTime;

    /** 更新时间 */
    protected Date updateTime;

    /** 备注 */
    protected String remark;
}