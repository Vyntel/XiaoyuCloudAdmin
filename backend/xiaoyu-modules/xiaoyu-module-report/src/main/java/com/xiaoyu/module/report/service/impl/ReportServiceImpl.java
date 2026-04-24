package com.xiaoyu.module.report.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xiaoyu.module.report.entity.ReportDefinition;
import com.xiaoyu.module.report.mapper.ReportDefinitionMapper;
import com.xiaoyu.module.report.service.ReportService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class ReportServiceImpl extends ServiceImpl<ReportDefinitionMapper, ReportDefinition> implements ReportService {

    private final ReportDefinitionMapper reportDefinitionMapper;

    @Override
    public List<ReportDefinition> getReportPage(Integer pageNum, Integer pageSize, String name, Integer status) {
        QueryWrapper wrapper = QueryWrapper.create();
        if (name != null && !name.isEmpty()) {
            wrapper.and("name like {0}", "%" + name + "%");
        }
        if (status != null) {
            wrapper.and("status = {0}", status);
        }
        wrapper.orderBy("create_time", false);
        return reportDefinitionMapper.selectListByQuery(wrapper);
    }

    @Override
    public ReportDefinition getReportById(Long id) {
        return reportDefinitionMapper.selectOneById(id);
    }

    @Override
    public Long createReport(ReportDefinition report) {
        report.setStatus(0);
        reportDefinitionMapper.insert(report, true);
        return report.getId();
    }

    @Override
    public boolean updateReport(ReportDefinition report) {
        return reportDefinitionMapper.update(report) > 0;
    }

    @Override
    public boolean deleteReport(Long id) {
        return reportDefinitionMapper.deleteById(id) > 0;
    }

    @Override
    public List<Object> executeReport(Long id) {
        return new ArrayList<>();
    }
}