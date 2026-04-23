package com.xiaoyu.module.system.entity;

import com.mybatisflex.annotation.Table;
import com.mybatisflex.annotation.Column;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.util.Date;

/**
 * 用户-角色关联实体
 * 对应表: sys_user_role
 */
@Data
@EqualsAndHashCode(callSuper = false)
@Table("sys_user_role")
public class SystemUserRole {

    private static final long serialVersionUID = 1L;

    /** 用户ID */
    private Long userId;

    /** 角色ID */
    private Long roleId;

    /** 创建时间 */
    @Column(onInsertValue = "now()")
    private Date createTime;
}