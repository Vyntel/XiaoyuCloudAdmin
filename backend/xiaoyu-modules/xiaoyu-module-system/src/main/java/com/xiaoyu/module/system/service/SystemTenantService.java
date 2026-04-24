package com.xiaoyu.module.system.service;

import com.mybatisflex.core.service.IService;
import com.xiaoyu.module.system.entity.SystemTenant;

import java.util.List;

/**
 * 系统租户 Service
 */
public interface SystemTenantService extends IService<SystemTenant> {

    /**
     * 分页查询租户列表
     */
    List<SystemTenant> getTenantPage(Integer pageNum, Integer pageSize, String name, Integer status);

    /**
     * 获取所有启用的租户列表
     */
    List<SystemTenant> getEnabledTenants();

    /**
     * 根据ID获取租户信息
     */
    SystemTenant getTenantById(Long id);

    /**
     * 新增租户
     */
    Long createTenant(SystemTenant tenant);

    /**
     * 更新租户
     */
    boolean updateTenant(SystemTenant tenant);

    /**
     * 删除租户
     */
    boolean deleteTenant(Long id);
}