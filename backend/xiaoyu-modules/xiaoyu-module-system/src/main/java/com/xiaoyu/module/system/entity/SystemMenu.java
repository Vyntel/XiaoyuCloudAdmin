package com.xiaoyu.module.system.entity;

import com.mybatisflex.annotation.Table;
import com.xiaoyu.common.core.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统菜单实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table("sys_menu")
public class SystemMenu extends TenantEntity {

    private static final long serialVersionUID = 1L;


    private String menuName;
    private Long parentId;
    private Integer orderNum;
    private String path;
    private String component;
    private Character menuType;
    private Character visible;
    private Integer status;
    private String perms;
    private String icon;
}