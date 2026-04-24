package com.xiaoyu.module.system.service;

import com.mybatisflex.core.service.IService;
import com.xiaoyu.module.system.entity.SystemMenu;

import java.util.List;

/**
 * 菜单Service接口
 */
public interface SystemMenuService extends IService<SystemMenu> {

    /**
     * 获取菜单树
     */
    List<SystemMenu> getMenuTree();

    /**
     * 获取所有菜单列表
     */
    List<SystemMenu> getMenuList();

    /**
     * 根据ID获取菜单
     */
    SystemMenu getMenuById(Long menuId);

    /**
     * 获取子菜单列表
     */
    List<SystemMenu> getMenuChildren(Long menuId);

    /**
     * 获取路由菜单树（用于动态路由）
     */
    List<SystemMenu> getRoutes();

    /**
     * 新增菜单
     */
    Long createMenu(SystemMenu menu);

    /**
     * 修改菜单
     */
    void updateMenu(SystemMenu menu);

    /**
     * 删除菜单
     */
    void deleteMenu(Long menuId);

    /**
     * 检查菜单名称是否唯一
     */
    boolean checkMenuNameUnique(String menuName, Long parentId, Long menuId);

    /**
     * 检查路由地址是否唯一
     */
    boolean checkPathUnique(String path, Long parentId, Long menuId);

    /**
     * 检查权限标识是否唯一
     */
    boolean checkPermUnique(String perm, Long menuId);
}