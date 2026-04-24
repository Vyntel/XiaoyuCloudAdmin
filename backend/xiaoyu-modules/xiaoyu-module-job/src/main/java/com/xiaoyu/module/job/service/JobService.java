package com.xiaoyu.module.job.service;

import com.xiaoyu.module.job.entity.SysJob;
import java.util.List;

public interface JobService {
    List<SysJob> getJobPage(Integer pageNum, Integer pageSize, String jobName, String jobGroup, Integer status);
    SysJob getJobById(Long id);
    Long createJob(SysJob job);
    boolean updateJob(SysJob job);
    boolean deleteJob(Long id);
    boolean runJob(Long id);
    boolean stopJob(Long id);
}