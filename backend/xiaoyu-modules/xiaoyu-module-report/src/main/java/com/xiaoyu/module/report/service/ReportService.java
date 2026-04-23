package com.xiaoyu.module.report.service;

import com.xiaoyu.module.report.entity.ReportDefinition;
import java.util.List;

public interface ReportService {
    List<ReportDefinition> getReportPage(Integer pageNum, Integer pageSize, String name, Integer status);
    ReportDefinition getReportById(Long id);
    Long createReport(ReportDefinition report);
    boolean updateReport(ReportDefinition report);
    boolean deleteReport(Long id);
    List<Object> executeReport(Long id);
}