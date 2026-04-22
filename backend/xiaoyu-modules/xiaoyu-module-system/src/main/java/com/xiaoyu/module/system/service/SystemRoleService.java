package com.xiaoyu.module.system.service;

import com.xiaoyu.common.core.service.IBaseService;
import com.xiaoyu.module.system.entity.SystemRole;
import com.xiaoyu.module.system.mapper.SystemRoleMapper;

import java.util.List;

/**
 * 角色Service接口
 */
public interface SystemRoleService extends IBaseService<SystemRoleMapper, SystemRole> {

    /**
     * 根据角色编码查询角色
     */
    SystemRole selectByRoleCode(String roleCode);

    /**
     * 根据角色名称查询角色
     */
    SystemRole selectByRoleName(String roleName);

    /**
     * 检查角色编码是否唯一
     */
    boolean checkRoleCodeUnique(String roleCode, Long roleId);

    /**
     * 检查角色名称是否唯一
     */
    boolean checkRoleNameUnique(String roleName, Long roleId);

    /**
     * 根据状态查询角色列表
     */
    List<SystemRole> selectByStatus(Integer status);
}