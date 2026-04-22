package com.xiaoyu.module.system.service.impl;

import com.xiaoyu.common.core.service.impl.BaseServiceImpl;
import com.xiaoyu.module.system.entity.SystemMenu;
import com.xiaoyu.module.system.mapper.SystemMenuMapper;
import com.xiaoyu.module.system.service.SystemMenuService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

@Slf4j
@Service
@RequiredArgsConstructor
public class SystemMenuServiceImpl extends BaseServiceImpl<SystemMenuMapper, SystemMenu> implements SystemMenuService {

    private final SystemMenuMapper systemMenuMapper;

    @Override
    protected SystemMenuMapper getMapperDelegate() {
        return systemMenuMapper;
    }
}