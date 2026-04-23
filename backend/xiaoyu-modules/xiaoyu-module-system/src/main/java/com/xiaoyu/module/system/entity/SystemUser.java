package com.xiaoyu.module.system.entity;

import com.mybatisflex.annotation.Table;
import com.mybatisflex.annotation.Column;
import com.xiaoyu.common.core.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统用户实体
 * 对应表: sys_user
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table("sys_user")
public class SystemUser extends TenantEntity {

    private static final long serialVersionUID = 1L;

    /** 用户名 */
    private String username;

    /** 密码 */
    private String password;

    /** 昵称 */
    private String nickname;

    /** 邮箱 */
    private String email;

    /** 手机号 */
    private String phone;

    /** 头像URL */
    private String avatar;

    /** 性别（0-未知，1-男，2-女） */
    private Integer sex;

    /** 部门ID */
    private Long deptId;

    /** 岗位ID列表（逗号分隔） */
    @Column(isLarge = true)
    private String postIds;
}