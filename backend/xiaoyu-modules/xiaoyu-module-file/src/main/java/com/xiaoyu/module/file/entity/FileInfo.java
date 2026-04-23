package com.xiaoyu.module.file.entity;

import com.mybatisflex.annotation.Table;
import com.xiaoyu.common.core.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;
import java.util.Date;

/**
 * 文件信息实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table("file_info")
public class FileInfo extends TenantEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文件名称
     */
    private String fileName;

    /**
     * 文件路径
     */
    private String filePath;

    /**
     * 文件URL
     */
    private String fileUrl;

    /**
     * 文件大小(字节)
     */
    private Long fileSize;

    /**
     * 文件类型
     */
    private String fileType;

    /**
     * MIME类型
     */
    private String mimeType;

    /**
     * 存储引擎(local/minio/oss/cos)
     */
    private String storageType;

    /**
     * 存储桶/目录
     */
    private String bucketName;

    /**
     * 唯一标识符
     */
    private String fileKey;

    /**
     * 上传者ID
     */
    private Long uploadUserId;

    /**
     * 上传者名称
     */
    private String uploadUserName;

    /**
     * 文件夹ID
     */
    private Long folderId;

    /**
     * 状态(0-正常,1-删除)
     */
    private Integer status;

    /**
     * 上传时间
     */
    private Date uploadTime;
}