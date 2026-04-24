package com.xiaoyu.module.generator.entity;

import com.mybatisflex.annotation.Table;
import com.xiaoyu.common.core.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@Table("gen_table_column")
public class GenTableColumn extends TenantEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private Long tableId;
    private String columnName;
    private String columnComment;
    private String columnType;
    private String javaType;
    private String javaField;
    private Integer isPk;
    private Integer isIncrement;
    private Integer isRequired;
    private Integer isInsert;
    private Integer isEdit;
    private Integer isList;
    private Integer isQuery;
    private String queryType;
    private String htmlType;
    private String dictType;
    private Integer sort;
}