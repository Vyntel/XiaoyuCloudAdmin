package com.xiaoyu.module.system.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xiaoyu.module.system.entity.SystemDept;
import com.xiaoyu.module.system.mapper.SystemDeptMapper;
import com.xiaoyu.module.system.service.SystemDeptService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SystemDeptServiceImpl extends ServiceImpl<SystemDeptMapper, SystemDept> implements SystemDeptService {

    private final SystemDeptMapper systemDeptMapper;
}