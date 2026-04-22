package com.xiaoyu.module.system.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import com.xiaoyu.common.core.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统角色实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table("sys_role")
public class SystemRole extends TenantEntity {

    private static final long serialVersionUID = 1L;

    @Id
    private Long roleId;
    private String roleName;
    private String roleCode;
    private Integer roleSort;
    private Integer dataScope;
}