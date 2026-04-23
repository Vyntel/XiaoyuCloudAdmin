package com.xiaoyu.module.generator.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xiaoyu.module.generator.entity.GenTable;
import com.xiaoyu.module.generator.mapper.GenTableMapper;
import com.xiaoyu.module.generator.service.GeneratorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class GeneratorServiceImpl extends ServiceImpl<GenTableMapper, GenTable> implements GeneratorService {
    private final GenTableMapper genTableMapper;

    @Override
    public List<GenTable> getTablePage(Integer pageNum, Integer pageSize, String tableName) {
        QueryWrapper wrapper = QueryWrapper.create();
        if (tableName != null && !tableName.isEmpty()) {
            wrapper.and("table_name like {0}", "%" + tableName + "%");
        }
        wrapper.orderBy("create_time", false);
        return genTableMapper.selectListByQuery(wrapper);
    }

    @Override
    public GenTable getTableById(Long id) {
        return genTableMapper.selectOneById(id);
    }

    @Override
    public Long createTable(GenTable table) {
        table.setStatus(0);
        genTableMapper.insert(table, true);
        return table.getId();
    }

    @Override
    public boolean deleteTable(Long id) {
        return genTableMapper.deleteById(id) > 0;
    }

    @Override
    public String generateCode(Long id) {
        return "code generated";
    }
}