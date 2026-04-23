package com.xiaoyu.module.system.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xiaoyu.module.system.entity.SystemTenant;
import com.xiaoyu.module.system.mapper.SystemTenantMapper;
import com.xiaoyu.module.system.service.SystemTenantService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SystemTenantServiceImpl extends ServiceImpl<SystemTenantMapper, SystemTenant> implements SystemTenantService {

    private final SystemTenantMapper systemTenantMapper;

    @Override
    public List<SystemTenant> getTenantPage(Integer pageNum, Integer pageSize, String name, Integer status) {
        QueryWrapper wrapper = QueryWrapper.create();
        if (name != null && !name.isEmpty()) {
            wrapper.where("name like {0}", "%" + name + "%");
        }
        if (status != null) {
            wrapper.and("status = {0}", status);
        }
        wrapper.orderBy("id", false);
        Page<SystemTenant> page = systemTenantMapper.paginate(pageNum, pageSize, wrapper);
        return page.getRecords();
    }

    @Override
    public List<SystemTenant> getEnabledTenants() {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.where("status = 0");
        wrapper.orderBy("sort", true);
        return systemTenantMapper.selectListByQuery(wrapper);
    }

    @Override
    public SystemTenant getTenantById(Long id) {
        return systemTenantMapper.selectOneById(id);
    }

    @Override
    public Long createTenant(SystemTenant tenant) {
        systemTenantMapper.insert(tenant, true);
        return tenant.getId();
    }

    @Override
    public boolean updateTenant(SystemTenant tenant) {
        return systemTenantMapper.update(tenant) > 0;
    }

    @Override
    public boolean deleteTenant(Long id) {
        return systemTenantMapper.deleteById(id) > 0;
    }
}