package com.xiaoyu.module.im.entity;

import com.mybatisflex.annotation.Table;
import com.xiaoyu.common.core.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * IM群组实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table("im_group")
public class ImGroup extends TenantEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 群名称
     */
    private String name;

    /**
     * 群头像
     */
    private String avatar;

    /**
     * 群主ID
     */
    private Long ownerId;

    /**
     * 群公告
     */
    private String announcement;

    /**
     * 群描述
     */
    private String description;

    /**
     * 成员数量
     */
    private Integer memberCount;

    /**
     * 是否全员群(0-否,1-是)
     */
    private Integer isAll;

    /**
     * 加群方式(0-允许所有人,1-需要验证,2-禁止加入)
     */
    private Integer joinType;

    /**
     * 状态(0-正常,1-解散)
     */
    private Integer status;
}