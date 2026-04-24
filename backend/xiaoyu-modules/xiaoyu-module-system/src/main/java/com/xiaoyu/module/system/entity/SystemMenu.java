package com.xiaoyu.module.system.entity;

import com.mybatisflex.annotation.Table;
import com.mybatisflex.annotation.Column;
import com.xiaoyu.common.core.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统菜单实体
 * 对应表: sys_menu
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table("sys_menu")
public class SystemMenu extends TenantEntity {

    private static final long serialVersionUID = 1L;

    /** 上级菜单ID，0表示顶级 */
    private Long parentId;

    /** 父级ID路径，如"0,1,2" */
    @Column(isLarge = true)
    private String parentIdPath;

    /** 菜单类型：C-目录，M-菜单，B-按钮 */
    private String menuType;

    /** 菜单名称 */
    private String name;

    /** 权限标识，如system:user:add */
    private String permission;

    /** 路由地址 */
    private String path;

    /** 组件路径 */
    private String component;

    /** 图标 */
    private String icon;

    /** 是否显示（0-显示，1-隐藏） */
    private Integer isShow;

    /** 是否缓存（0-缓存，1-禁用） */
    private Integer isKeepAlive;

    /** 是否外链（0-否，1-是） */
    private Integer isFrame;

    /** 排序 */
    private Integer sort;
}