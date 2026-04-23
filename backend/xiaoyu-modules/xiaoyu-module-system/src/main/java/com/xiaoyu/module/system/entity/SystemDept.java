package com.xiaoyu.module.system.entity;

import com.mybatisflex.annotation.Table;
import com.mybatisflex.annotation.Column;
import com.xiaoyu.common.core.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统部门实体
 * 对应表: sys_dept
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table("sys_dept")
public class SystemDept extends TenantEntity {

    private static final long serialVersionUID = 1L;

    /** 上级部门ID，0表示顶级 */
    private Long parentId;

    /** 父级ID路径，如"0,1,2" */
    @Column(isLarge = true)
    private String parentIdPath;

    /** 部门层级 */
    private Integer level;

    /** 部门名称 */
    private String name;

    /** 部门编码 */
    private String code;

    /** 负责人用户ID */
    private Long leaderUserId;

    /** 负责人用户名 */
    private String leaderUserName;

    /** 联系电话 */
    private String phone;

    /** 邮箱 */
    private String email;

    /** 排序 */
    private Integer sort;
}