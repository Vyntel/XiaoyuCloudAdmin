package com.xiaoyu.module.im.entity;

import com.mybatisflex.annotation.Table;
import com.xiaoyu.common.core.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * IM消息实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table("im_message")
public class ImMessage extends TenantEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 会话ID
     */
    private Long conversationId;

    /**
     * 发送者ID
     */
    private Long senderId;

    /**
     * 发送者名称
     */
    private String senderName;

    /**
     * 发送者头像
     */
    private String senderAvatar;

    /**
     * 消息类型(0-文本,1-图片,2-文件,3-语音,4-视频)
     */
    private Integer msgType;

    /**
     * 消息内容
     */
    private String content;

    /**
     * 引用消息ID
     */
    private Long quoteId;

    /**
     * 附件URL
     */
    private String attachmentUrl;

    /**
     * 附件名称
     */
    private String attachmentName;

    /**
     * 附件大小
     */
    private Long attachmentSize;

    /**
     * 状态(0-正常,1-已撤回)
     */
    private Integer status;

    /**
     * 发送时间
     */
    private Date sendTime;
}