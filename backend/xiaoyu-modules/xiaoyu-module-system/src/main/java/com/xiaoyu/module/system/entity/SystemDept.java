package com.xiaoyu.module.system.entity;

import com.mybatisflex.annotation.Table;
import com.xiaoyu.common.core.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统部门实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table("sys_dept")
public class SystemDept extends TenantEntity {

    private static final long serialVersionUID = 1L;

    private Long parentId;
    private String deptName;
    private String deptCode;
    private Long leaderUserId;
    private String phone;
    private String email;
    private Integer sort;
}