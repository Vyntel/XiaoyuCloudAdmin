package com.xiaoyu.module.system.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import com.xiaoyu.common.core.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

/**
 * 系统用户实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table("sys_user")
public class SystemUser extends TenantEntity {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID (主键)
     */
    @Id
    private Long userId;

    /**
     * 用户名
     */
    private String username;

    /**
     * 密码
     */
    private String password;

    /**
     * 昵称
     */
    private String nickname;

    /**
     * 邮箱
     */
    private String email;

    /**
     * 手机号
     */
    private String phone;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 性别（0-男，1-女，2-未知）
     */
    private Integer sex;

    /**
     * 部门ID
     */
    private Long deptId;
}