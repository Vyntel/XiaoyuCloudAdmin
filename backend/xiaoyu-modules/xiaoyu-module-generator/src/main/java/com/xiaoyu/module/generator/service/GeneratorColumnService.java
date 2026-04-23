package com.xiaoyu.module.generator.service;

import com.xiaoyu.module.generator.entity.GenTable;
import com.xiaoyu.module.generator.entity.GenTableColumn;
import java.util.List;

public interface GeneratorColumnService {
    List<GenTableColumn> getColumnsByTableId(Long tableId);
    boolean deleteColumnsByTableId(Long tableId);
    int batchInsert(List<GenTableColumn> columns);
    GenTable getTableWithColumns(Long id);
}