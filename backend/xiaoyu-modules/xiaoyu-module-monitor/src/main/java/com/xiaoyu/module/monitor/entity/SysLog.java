package com.xiaoyu.module.monitor.entity;

import com.mybatisflex.annotation.Table;
import com.xiaoyu.common.core.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;
import java.io.Serializable;

@Data
@EqualsAndHashCode(callSuper = true)
@Table("sys_log")
public class SysLog extends TenantEntity implements Serializable {
    private static final long serialVersionUID = 1L;
    private String module;
    private String bizType;
    private Long userId;
    private String userName;
    private String method;
    private String url;
    private String ip;
    private String location;
    private String params;
    private String result;
    private Integer status;
    private Long duration;
    private String errorMsg;
    private String traceId;
}