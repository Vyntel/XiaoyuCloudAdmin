package com.xiaoyu.module.job.entity;

import com.mybatisflex.annotation.Table;
import com.xiaoyu.common.core.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@Table("sys_job")
public class SysJob extends TenantEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private String jobName;
    private String jobGroup;
    private String jobHandler;
    private String cronExpression;
    private Integer misfirePolicy;
    private Integer concurrent;
    private String paramJson;
    private Integer status;
    private String description;
}