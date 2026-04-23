package com.xiaoyu.module.system.entity;

import com.mybatisflex.annotation.Table;
import com.mybatisflex.annotation.Column;
import com.xiaoyu.common.core.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统角色实体
 * 对应表: sys_role
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table("sys_role")
public class SystemRole extends TenantEntity {

    private static final long serialVersionUID = 1L;

    /** 角色名称 */
    private String name;

    /** 角色编码 */
    private String code;

    /** 角色描述 */
    private String description;

    /** 角色级别（用于排序） */
    private Integer level;

    /** 数据权限范围（1-全部，2-自定义，3-本部门，4-本部门及以下，5-仅本人） */
    private Integer dataScope;

    /** 自定义数据权限部门ID列表 */
    @Column(isLarge = true)
    private String dataScopeDeptIds;

    /** 菜单权限ID列表（JSON数组） */
    @Column(isLarge = true)
    private String menuIds;
}