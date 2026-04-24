package com.xiaoyu.module.im.entity;

import com.mybatisflex.annotation.Table;
import com.xiaoyu.common.core.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * IM群组成员实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table("im_group_member")
public class ImGroupMember extends TenantEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 群组ID
     */
    private Long groupId;

    /**
     * 用户ID
     */
    private Long userId;

    /**
     * 用户名称
     */
    private String userName;

    /**
     * 用户头像
     */
    private String userAvatar;

    /**
     * 群昵称
     */
    private String nickname;

    /**
     * 角色(0-成员,1-管理员,2-群主)
     */
    private Integer role;

    /**
     * 入群时间
     */
    private Date joinTime;

    /**
     * 最后发言时间
     */
    private Date lastSpeakTime;

    /**
     * 禁言结束时间
     */
    private Date muteEndTime;

    /**
     * 状态(0-正常,1-退群,2-踢出)
     */
    private Integer status;
}