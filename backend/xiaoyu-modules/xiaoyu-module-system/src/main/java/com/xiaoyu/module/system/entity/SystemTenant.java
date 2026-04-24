package com.xiaoyu.module.system.entity;

import com.mybatisflex.annotation.Table;
import com.xiaoyu.common.core.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统租户表实体类
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table("sys_tenant")
public class SystemTenant extends TenantEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 租户名称
     */
    private String name;

    /**
     * 租户编码
     */
    private String code;

    /**
     * 租户域名
     */
    private String domain;

    /**
     * 租户logo
     */
    private String logo;

    /**
     * 租户简介
     */
    private String description;

    /**
     * 套餐ID
     */
    private Long packageId;

    /**
     * 账号数量限制
     */
    private Integer accountLimit;

    /**
     * 空间存储限制(MB)
     */
    private Long storageLimit;

    /**
     * 到期时间
     */
    private Date expireTime;
}