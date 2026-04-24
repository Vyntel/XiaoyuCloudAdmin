package com.xiaoyu.module.generator.service;

import com.xiaoyu.module.generator.entity.GenTable;
import com.xiaoyu.module.generator.entity.GenTableColumn;
import java.util.List;

public interface GeneratorColumnService {
    List<GenTableColumn> getColumnsByTableId(Long tableId);
    boolean deleteColumnsByTableId(Long tableId);
    boolean deleteColumn(Long id);
    int batchInsert(List<GenTableColumn> columns);
    boolean updateColumn(GenTableColumn column);
    GenTable getTableWithColumns(Long id);
}