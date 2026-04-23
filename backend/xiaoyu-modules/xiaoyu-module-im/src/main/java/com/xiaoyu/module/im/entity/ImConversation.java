package com.xiaoyu.module.im.entity;

import com.mybatisflex.annotation.Table;
import com.xiaoyu.common.core.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * IM用户会话实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table("im_conversation")
public class ImConversation extends TenantEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 会话类型(0-单聊,1-群聊)
     */
    private Integer type;

    /**
     * 会话名称
     */
    private String name;

    /**
     * 群主ID(群聊时)
     */
    private Long ownerId;

    /**
     * 头像
     */
    private String avatar;

    /**
     * 最后消息ID
     */
    private Long lastMessageId;

    /**
     * 最后消息时间
     */
    private Date lastMessageTime;

    /**
     * 未读消息数
     */
    private Integer unreadCount;

    /**
     * 状态(0-正常,1-置顶,2-免打扰)
     */
    private Integer status;
}