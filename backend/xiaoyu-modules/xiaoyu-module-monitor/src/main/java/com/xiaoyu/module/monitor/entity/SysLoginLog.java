package com.xiaoyu.module.monitor.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 登录日志实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table("sys_login_log")
public class SysLoginLog extends com.xiaoyu.common.core.entity.BaseEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * IP地址
     */
    private String ip;

    /**
     * 登录地点
     */
    private String location;

    /**
     * 浏览器
     */
    private String browser;

    /**
     * 操作系统
     */
    private String os;

    /**
     * 登录状态(0-成功,1-失败)
     */
    private Integer status;

    /**
     * 提示消息
     */
    private String msg;

    /**
     * 登录时间
     */
    private Date loginTime;
}