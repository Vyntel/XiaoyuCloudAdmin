package com.xiaoyu.module.file.service;

import com.xiaoyu.module.file.entity.FileInfo;
import java.util.List;

public interface FileService {

    List<FileInfo> getFilePage(Integer pageNum, Integer pageSize, String fileName, Long folderId, String storageType);

    FileInfo getFileById(Long id);

    Long uploadFile(FileInfo fileInfo);

    boolean deleteFile(Long id);

    boolean batchDelete(List<Long> ids);

    String getFileUrl(Long id);

    Long copyFile(Long id, Long targetFolderId);
}