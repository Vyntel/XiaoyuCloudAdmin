package com.xiaoyu.common.core.entity;

import com.mybatisflex.annotation.Column;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 基础实体类
 * 包含通用字段：createTime, updateTime, remark
 * 每个实体定义自己的主键 ID 字段
 *
 * @author xiaoyu
 */
@Data
public abstract class BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

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
     * 备注
     */
    protected String remark;
}