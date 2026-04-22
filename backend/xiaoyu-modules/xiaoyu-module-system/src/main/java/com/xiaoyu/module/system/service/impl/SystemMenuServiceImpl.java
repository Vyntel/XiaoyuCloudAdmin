package com.xiaoyu.module.system.service.impl;

import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xiaoyu.module.system.entity.SystemMenu;
import com.xiaoyu.module.system.mapper.SystemMenuMapper;
import com.xiaoyu.module.system.service.SystemMenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SystemMenuServiceImpl extends ServiceImpl<SystemMenuMapper, SystemMenu> implements SystemMenuService {

    private final SystemMenuMapper systemMenuMapper;
}