package com.xiaoyu.module.monitor.service;

import com.xiaoyu.module.monitor.entity.SysLog;
import java.util.List;

public interface MonitorService {
    List<SysLog> getLogPage(Integer pageNum, Integer pageSize, String module, Long userId);
    boolean deleteLog(Long id);
    boolean clearLogs();
}