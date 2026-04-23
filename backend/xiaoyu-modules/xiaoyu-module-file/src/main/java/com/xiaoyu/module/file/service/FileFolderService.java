package com.xiaoyu.module.file.service;

import com.xiaoyu.module.file.entity.FileFolder;
import java.util.List;

public interface FileFolderService {

    List<FileFolder> getFolderTree(Long parentId);

    FileFolder getFolderById(Long id);

    Long createFolder(FileFolder folder);

    boolean updateFolder(FileFolder folder);

    boolean deleteFolder(Long id);
}