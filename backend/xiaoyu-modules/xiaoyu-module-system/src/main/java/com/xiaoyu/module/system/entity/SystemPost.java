package com.xiaoyu.module.system.entity;

import com.mybatisflex.annotation.Table;
import com.xiaoyu.common.core.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统岗位实体
 * 对应表: sys_post
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table("sys_post")
public class SystemPost extends TenantEntity {

    private static final long serialVersionUID = 1L;

    /** 岗位编码 */
    private String postCode;

    /** 岗位名称 */
    private String postName;

    /** 显示顺序 */
    private Integer postSort;

    /** 状态（0-正常，1-禁用） */
    private Integer status;

    /** 备注 */
    private String remark;
}