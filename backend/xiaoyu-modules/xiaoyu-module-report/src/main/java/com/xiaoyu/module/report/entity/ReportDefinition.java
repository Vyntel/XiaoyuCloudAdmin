package com.xiaoyu.module.report.entity;

import com.mybatisflex.annotation.Table;
import com.xiaoyu.common.core.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 报表定义实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table("report_definition")
public class ReportDefinition extends TenantEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    private String name;
    private String code;
    private String description;
    private String sqlContent;
    private String configJson;
    private Integer status;
}