package com.xiaoyu.module.system.entity;

import com.mybatisflex.annotation.Table;
import lombok.Data;

/**
 * 用户和岗位关联实体
 */
@Data
@Table("sys_user_post")
public class SystemUserPost {

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 岗位ID
     */
    private Long postId;
}