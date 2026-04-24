package com.xiaoyu.module.file.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xiaoyu.module.file.entity.FileFolder;
import com.xiaoyu.module.file.mapper.FileFolderMapper;
import com.xiaoyu.module.file.service.FileFolderService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class FileFolderServiceImpl extends ServiceImpl<FileFolderMapper, FileFolder> implements FileFolderService {

    private final FileFolderMapper fileFolderMapper;

    @Override
    public List<FileFolder> getFolderTree(Long parentId) {
        QueryWrapper wrapper = QueryWrapper.create();
        if (parentId != null) {
            wrapper.where("parent_id = {0}", parentId);
        } else {
            wrapper.where("parent_id = 0");
        }
        wrapper.and("status = 0");
        wrapper.orderBy("name", true);
        return fileFolderMapper.selectListByQuery(wrapper);
    }

    @Override
    public FileFolder getFolderById(Long id) {
        return fileFolderMapper.selectOneById(id);
    }

    @Override
    public Long createFolder(FileFolder folder) {
        folder.setStatus(0);
        folder.setFileCount(0);
        fileFolderMapper.insert(folder, true);
        return folder.getId();
    }

    @Override
    public boolean updateFolder(FileFolder folder) {
        return fileFolderMapper.update(folder) > 0;
    }

    @Override
    public boolean deleteFolder(Long id) {
        FileFolder folder = fileFolderMapper.selectOneById(id);
        if (folder != null) {
            folder.setStatus(1);
            return fileFolderMapper.update(folder) > 0;
        }
        return false;
    }
}