package com.xiaoyu.module.system.service;

import com.mybatisflex.core.service.IService;
import com.xiaoyu.module.system.entity.SystemRole;

import java.util.List;

/**
 * 角色Service接口
 */
public interface SystemRoleService extends IService<SystemRole> {

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

    /**
     * 分页查询角色列表
     */
    List<SystemRole> getRolePage(Integer pageNum, Integer pageSize, String name, String code, Integer status);

    /**
     * 根据ID获取角色
     */
    SystemRole getRoleById(Long roleId);

    /**
     * 新增角色
     */
    Long createRole(SystemRole role);

    /**
     * 修改角色
     */
    void updateRole(SystemRole role);

    /**
     * 删除角色
     */
    void deleteRole(Long roleId);

    /**
     * 分配菜单权限
     */
    void assignMenus(Long roleId, List<Long> menuIds);

    /**
     * 获取角色菜单ID列表
     */
    List<Long> getRoleMenuIds(Long roleId);

    /**
     * 获取所有角色列表
     */
    List<SystemRole> getRoleList();

    /**
     * 修改角色状态
     */
    void updateRoleStatus(Long roleId, Integer status);
}