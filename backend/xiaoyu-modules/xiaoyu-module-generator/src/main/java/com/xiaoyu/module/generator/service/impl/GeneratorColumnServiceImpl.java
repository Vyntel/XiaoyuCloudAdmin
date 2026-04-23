package com.xiaoyu.module.generator.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xiaoyu.module.generator.entity.GenTable;
import com.xiaoyu.module.generator.entity.GenTableColumn;
import com.xiaoyu.module.generator.mapper.GenTableColumnMapper;
import com.xiaoyu.module.generator.mapper.GenTableMapper;
import com.xiaoyu.module.generator.service.GeneratorColumnService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeneratorColumnServiceImpl extends ServiceImpl<GenTableColumnMapper, GenTableColumn> implements GeneratorColumnService {
    private final GenTableColumnMapper genTableColumnMapper;
    private final GenTableMapper genTableMapper;

    @Override
    public List<GenTableColumn> getColumnsByTableId(Long tableId) {
        QueryWrapper wrapper = QueryWrapper.create()
                .where("table_id", tableId)
                .orderBy("sort");
        return genTableColumnMapper.selectListByQuery(wrapper);
    }

    @Override
    public boolean deleteColumnsByTableId(Long tableId) {
        QueryWrapper wrapper = QueryWrapper.create()
                .where("table_id", tableId);
        return genTableColumnMapper.deleteByQuery(wrapper) > 0;
    }

    @Override
    public int batchInsert(List<GenTableColumn> columns) {
        int count = 0;
        for (int i = 0; i < columns.size(); i++) {
            genTableColumnMapper.insert(columns.get(i), true);
            count++;
        }
        return count;
    }

    @Override
    public GenTable getTableWithColumns(Long id) {
        GenTable table = genTableMapper.selectOneById(id);
        if (table != null) {
            List<GenTableColumn> columns = getColumnsByTableId(id);
            table.setColumns(columns);
        }
        return table;
    }
}