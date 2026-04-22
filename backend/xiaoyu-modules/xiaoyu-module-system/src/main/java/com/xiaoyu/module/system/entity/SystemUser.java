package com.xiaoyu.module.system.entity;

import com.mybatisflex.annotation.Id;
import com.mybatisflex.annotation.Table;
import lombok.Data;

import java.io.Serializable;
import java.util.Date;

/**
 * 系统用户实体
 */
@Data
@Table("sys_user")
public class SystemUser implements Serializable {

    private static final long serialVersionUID = 1L;

    @Id
    private Long userId;
    private String username;
    private String password;
    private String nickname;
    private String email;
    private String phone;
    private String avatar;
    private Integer sex;
    private Long deptId;
    private Integer status;
    private Long tenantId;

    private Date createTime;
    private Date updateTime;

    private String remark;
}
