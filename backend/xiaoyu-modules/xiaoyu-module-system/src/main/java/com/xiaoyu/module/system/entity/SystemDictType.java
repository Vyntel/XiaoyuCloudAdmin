package com.xiaoyu.module.system.entity;

import com.mybatisflex.annotation.Table;
import com.xiaoyu.common.core.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典类型实体
 * 对应表: sys_dict_type
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table("sys_dict_type")
public class SystemDictType extends TenantEntity {

    private static final long serialVersionUID = 1L;

    /** 字典名称 */
    private String name;

    /** 字典编码 */
    private String code;
}