package com.xiaoyu.module.system.entity;

import com.mybatisflex.annotation.Table;
import com.mybatisflex.annotation.Column;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 角色-菜单关联实体
 * 对应表: sys_role_menu
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table("sys_role_menu")
public class SystemRoleMenu {

    private static final long serialVersionUID = 1L;

    /** 角色ID */
    private Long roleId;

    /** 菜单ID */
    private Long menuId;

    /** 创建时间 */
    @Column(onInsertValue = "now()")
    private Date createTime;
}