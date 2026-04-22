package com.xiaoyu.api.system.dto;

import jakarta.validation.constraints.Email;
import jakarta.validation.constraints.NotBlank;
import jakarta.validation.constraints.Size;
import lombok.Data;

import java.io.Serializable;
import java.util.List;

/**
 * 用户DTO
 */
@Data
public class UserDTO implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 用户ID
     */
    private Long id;

    /**
     * 用户名
     */
    @NotBlank(message = "用户名不能为空")
    @Size(min = 2, max = 50, message = "用户名长度必须在2-50之间")
    private String username;

    /**
     * 昵称
     */
    @Size(max = 50, message = "昵称长度不能超过50")
    private String nickname;

    /**
     * 邮箱
     */
    @Email(message = "邮箱格式不正确")
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
     * 密码
     */
    @Size(min = 6, max = 100, message = "密码长度必须在6-100之间")
    private String password;

    /**
     * 部门ID
     */
    private Long deptId;

    /**
     * 岗位ID列表
     */
    private List<Long> postIds;

    /**
     * 角色ID列表
     */
    private List<Long> roleIds;

    /**
     * 状态（0-正常，1-禁用）
     */
    private Integer status;

    /**
     * 备注
     */
    private String remark;
}
