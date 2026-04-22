package com.xiaoyu.module.system.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import com.xiaoyu.common.core.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统岗位实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table("sys_post")
public class SystemPost extends TenantEntity {

    private static final long serialVersionUID = 1L;

    @Id
    private Long postId;
    private String postCode;
    private String postName;
    private Integer postSort;
}