package com.xiaoyu.common.core.entity;

import com.mybatisflex.annotation.Column;
import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.KeyType;
import com.mybatisflex.core.keygen.KeyGenerators;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础实体类 - 包含通用字段
 */
@Data
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 主键ID
     */
    @Id(keyType = KeyType.Generator, value = KeyGenerators.flexId)
    protected Long id;

    /**
     * 创建时间
     */
    @Column(onInsertValue = "now()")
    protected Date createTime;

    /**
     * 更新时间
     */
    @Column(onUpdateValue = "now()")
    protected Date updateTime;

    /**
     * 逻辑删除（0-未删除，1-已删除）
     */
    @Column(isLogicDelete = true)
    protected Integer deleted;

    /**
     * 备注
     */
    protected String remark;
}