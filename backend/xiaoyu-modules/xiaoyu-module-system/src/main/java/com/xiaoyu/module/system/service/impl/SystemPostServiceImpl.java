package com.xiaoyu.module.system.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xiaoyu.module.system.entity.SystemPost;
import com.xiaoyu.module.system.mapper.SystemPostMapper;
import com.xiaoyu.module.system.service.SystemPostService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SystemPostServiceImpl extends ServiceImpl<SystemPostMapper, SystemPost> implements SystemPostService {
   private final SystemPostMapper systemPostMapper;
}