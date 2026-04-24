package com.xiaoyu.module.system.entity;

import com.mybatisflex.annotation.Table;
import com.mybatisflex.annotation.Column;
import com.xiaoyu.common.core.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统参数配置实体
 * 对应表: sys_config
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table("sys_config")
public class SystemConfig extends TenantEntity {

    private static final long serialVersionUID = 1L;

    /** 参数名称 */
    private String name;

    /** 参数键名 */
    private String configKey;

    /** 参数值 */
    @Column(isLarge = true)
    private String configValue;

    /** 参数类型（String/Number/Boolean/JSON） */
    private String configType;

    /** 是否内置（0-否，1-是） */
    private Integer isBuiltin;
}