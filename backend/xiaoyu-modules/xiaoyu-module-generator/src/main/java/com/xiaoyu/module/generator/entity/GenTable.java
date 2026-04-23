package com.xiaoyu.module.generator.entity;

import com.mybatisflex.annotation.Table;
import com.xiaoyu.common.core.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;
import java.util.List;

@Data
@EqualsAndHashCode(callSuper = true)
@Table("gen_table")
public class GenTable extends TenantEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private String tableName;
    private String tableComment;
    private String className;
    private String packageName;
    private String moduleName;
    private String businessName;
    private String functionName;
    private String functionAuthor;
    private String templatePath;
    private Integer status;
    private List<GenTableColumn> columns;
}