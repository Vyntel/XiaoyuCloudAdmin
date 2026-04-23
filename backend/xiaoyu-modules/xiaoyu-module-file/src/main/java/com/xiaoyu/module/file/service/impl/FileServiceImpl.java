package com.xiaoyu.module.file.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xiaoyu.module.file.entity.FileInfo;
import com.xiaoyu.module.file.mapper.FileInfoMapper;
import com.xiaoyu.module.file.service.FileService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileServiceImpl extends ServiceImpl<FileInfoMapper, FileInfo> implements FileService {

    private final FileInfoMapper fileInfoMapper;

    @Override
    public List<FileInfo> getFilePage(Integer pageNum, Integer pageSize, String fileName, Long folderId, String storageType) {
        QueryWrapper wrapper = QueryWrapper.create();
        if (fileName != null && !fileName.isEmpty()) {
            wrapper.and("file_name like {0}", "%" + fileName + "%");
        }
        if (folderId != null) {
            wrapper.and("folder_id = {0}", folderId);
        }
        if (storageType != null && !storageType.isEmpty()) {
            wrapper.and("storage_type = {0}", storageType);
        }
        wrapper.and("status = 0");
        wrapper.orderBy("upload_time", false);
        return fileInfoMapper.selectListByQuery(wrapper);
    }

    @Override
    public FileInfo getFileById(Long id) {
        return fileInfoMapper.selectOneById(id);
    }

    @Override
    public Long uploadFile(FileInfo fileInfo) {
        fileInfo.setUploadTime(new Date());
        fileInfo.setStatus(0);
        fileInfoMapper.insert(fileInfo, true);
        return fileInfo.getId();
    }

    @Override
    public boolean deleteFile(Long id) {
        FileInfo fileInfo = fileInfoMapper.selectOneById(id);
        if (fileInfo != null) {
            fileInfo.setStatus(1);
            return fileInfoMapper.update(fileInfo) > 0;
        }
        return false;
    }

    @Override
    public boolean batchDelete(List<Long> ids) {
        for (Long id : ids) {
            deleteFile(id);
        }
        return true;
    }

    @Override
    public String getFileUrl(Long id) {
        FileInfo fileInfo = fileInfoMapper.selectOneById(id);
        return fileInfo != null ? fileInfo.getFileUrl() : null;
    }

    @Override
    public Long copyFile(Long id, Long targetFolderId) {
        FileInfo original = fileInfoMapper.selectOneById(id);
        if (original != null) {
            FileInfo copy = new FileInfo();
            copy.setFileName(original.getFileName() + "_copy");
            copy.setFilePath(original.getFilePath());
            copy.setFileUrl(original.getFileUrl());
            copy.setFileSize(original.getFileSize());
            copy.setFileType(original.getFileType());
            copy.setMimeType(original.getMimeType());
            copy.setStorageType(original.getStorageType());
            copy.setBucketName(original.getBucketName());
            copy.setFileKey(original.getFileKey() + "_copy");
            copy.setFolderId(targetFolderId);
            copy.setStatus(0);
            copy.setUploadTime(new Date());
            fileInfoMapper.insert(copy, true);
            return copy.getId();
        }
        return null;
    }
}