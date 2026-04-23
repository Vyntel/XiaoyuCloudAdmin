package com.xiaoyu.module.generator.service;

import com.xiaoyu.module.generator.entity.GenTable;
import java.util.List;

public interface GeneratorService {
    List<GenTable> getTablePage(Integer pageNum, Integer pageSize, String tableName);
    GenTable getTableById(Long id);
    GenTable getTableWithColumns(Long id);
    Long createTable(GenTable table);
    boolean deleteTable(Long id);
    String generateCode(Long id);
}