package com.xiaoyu.module.monitor.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xiaoyu.module.monitor.entity.SysLog;
import com.xiaoyu.module.monitor.mapper.SysLogMapper;
import com.xiaoyu.module.monitor.service.MonitorService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class MonitorServiceImpl extends ServiceImpl<SysLogMapper, SysLog> implements MonitorService {
    private final SysLogMapper sysLogMapper;

    @Override
    public List<SysLog> getLogPage(Integer pageNum, Integer pageSize, String module, Long userId) {
        QueryWrapper wrapper = QueryWrapper.create();
        if (module != null && !module.isEmpty()) {
            wrapper.and("module like {0}", "%" + module + "%");
        }
        if (userId != null) {
            wrapper.and("user_id = {0}", userId);
        }
        wrapper.orderBy("create_time", false);
        return sysLogMapper.selectListByQuery(wrapper);
    }

    @Override
    public boolean deleteLog(Long id) {
        return sysLogMapper.deleteById(id) > 0;
    }

    @Override
    public boolean clearLogs() {
        return true;
    }
}