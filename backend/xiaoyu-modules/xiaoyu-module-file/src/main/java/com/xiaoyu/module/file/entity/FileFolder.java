package com.xiaoyu.module.file.entity;

import com.mybatisflex.annotation.Table;
import com.xiaoyu.common.core.entity.TenantEntity;
import lombok.Data;
import lombok.EqualsAndHashCode;

import java.io.Serializable;

/**
 * 文件夹实体
 */
@Data
@EqualsAndHashCode(callSuper = true)
@Table("file_folder")
public class FileFolder extends TenantEntity implements Serializable {

    private static final long serialVersionUID = 1L;

    /**
     * 文件夹名称
     */
    private String name;

    /**
     * 父文件夹ID
     */
    private Long parentId;

    /**
     * 父级路径
     */
    private String parentPath;

    /**
     * 存储类型
     */
    private String storageType;

    /**
     * 文件数量
     */
    private Integer fileCount;

    /**
     * 状态(0-正常,1-删除)
     */
    private Integer status;
}