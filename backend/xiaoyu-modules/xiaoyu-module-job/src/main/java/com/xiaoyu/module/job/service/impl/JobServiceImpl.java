package com.xiaoyu.module.job.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xiaoyu.module.job.entity.SysJob;
import com.xiaoyu.module.job.mapper.SysJobMapper;
import com.xiaoyu.module.job.service.JobService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class JobServiceImpl extends ServiceImpl<SysJobMapper, SysJob> implements JobService {
    private final SysJobMapper sysJobMapper;

    @Override
    public List<SysJob> getJobPage(Integer pageNum, Integer pageSize, String jobName, String jobGroup, Integer status) {
        QueryWrapper wrapper = QueryWrapper.create();
        if (jobName != null && !jobName.isEmpty()) {
            wrapper.and("job_name like {0}", "%" + jobName + "%");
        }
        if (jobGroup != null && !jobGroup.isEmpty()) {
            wrapper.and("job_group = {0}", jobGroup);
        }
        if (status != null) {
            wrapper.and("status = {0}", status);
        }
        wrapper.orderBy("create_time", false);
        return sysJobMapper.selectListByQuery(wrapper);
    }

    @Override
    public SysJob getJobById(Long id) {
        return sysJobMapper.selectOneById(id);
    }

    @Override
    public Long createJob(SysJob job) {
        job.setStatus(0);
        sysJobMapper.insert(job, true);
        return job.getId();
    }

    @Override
    public boolean updateJob(SysJob job) {
        return sysJobMapper.update(job) > 0;
    }

    @Override
    public boolean deleteJob(Long id) {
        return sysJobMapper.deleteById(id) > 0;
    }

    @Override
    public boolean runJob(Long id) {
        SysJob job = sysJobMapper.selectOneById(id);
        if (job != null) {
            job.setStatus(1);
            return sysJobMapper.update(job) > 0;
        }
        return false;
    }

    @Override
    public boolean stopJob(Long id) {
        SysJob job = sysJobMapper.selectOneById(id);
        if (job != null) {
            job.setStatus(0);
            return sysJobMapper.update(job) > 0;
        }
        return false;
    }
}