package com.xiaoyu.module.system.entity;

import com.mybatisflex.annotation.Table;
import com.xiaoyu.common.core.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 字典数据实体
 * 对应表: sys_dict_data
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table("sys_dict_data")
public class SystemDictData extends TenantEntity {

    private static final long serialVersionUID = 1L;

    /** 字典类型ID */
    private Long dictTypeId;

    /** 字典标签 */
    private String label;

    /** 字典值 */
    private String value;

    /** 排序 */
    private Integer sort;

    /** 样式（CSS类名） */
    private String cssClass;

    /** 列表状态（0-正常，1-禁用） */
    private Integer listStatus;

    /** 默认状态（0-否，1-是） */
    private Integer isDefault;
}