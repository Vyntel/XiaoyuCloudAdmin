package com.xiaoyu.module.system.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xiaoyu.common.core.utils.XiaoYuThrowUtil;
import com.xiaoyu.module.system.entity.SystemMenu;
import com.xiaoyu.module.system.entity.SystemRoleMenu;
import com.xiaoyu.module.system.mapper.SystemMenuMapper;
import com.xiaoyu.module.system.mapper.SystemRoleMenuMapper;
import com.xiaoyu.module.system.service.SystemMenuService;
import cn.dev33.satoken.stp.StpUtil;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.ArrayList;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 菜单 Service 实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SystemMenuServiceImpl extends ServiceImpl<SystemMenuMapper, SystemMenu> implements SystemMenuService {

    private final SystemMenuMapper systemMenuMapper;
    private final SystemRoleMenuMapper systemRoleMenuMapper;

    @Override
    public List<SystemMenu> getMenuTree() {
        QueryWrapper q = QueryWrapper.create().orderBy("sort", true);
        List<SystemMenu> allMenus = systemMenuMapper.selectListByQuery(q);
        return buildMenuTree(0L, allMenus);
    }

    @Override
    public List<SystemMenu> getMenuList() {
        QueryWrapper q = QueryWrapper.create().orderBy("sort", true);
        return systemMenuMapper.selectListByQuery(q);
    }

    @Override
    public SystemMenu getMenuById(Long menuId) {
        SystemMenu menu = systemMenuMapper.selectOneById(menuId);
        XiaoYuThrowUtil.throwIfNull(menu, "菜单不存在");
        return menu;
    }

    @Override
    public List<SystemMenu> getMenuChildren(Long menuId) {
        QueryWrapper q = QueryWrapper.create()
                .where("parent_id", menuId)
                .orderBy("sort", true);
        return systemMenuMapper.selectListByQuery(q);
    }

    @Override
    public List<SystemMenu> getRoutes() {
        // 超级管理员获取所有菜单
        QueryWrapper q = QueryWrapper.create()
                .ne("menu_type", "F") // 排除按钮
                .eq("is_show", 0)     // 只获取可见的
                .orderBy("sort", true);
        
        List<SystemMenu> allMenus = systemMenuMapper.selectListByQuery(q);
        
        return buildMenuTree(0L, allMenus);
    }

    @Override
    @Transactional
    public Long createMenu(SystemMenu menu) {
        // 检查菜单名称唯一性
        XiaoYuThrowUtil.throwIfFalse(checkMenuNameUnique(menu.getName(), 
                menu.getParentId(), null), "菜单名称已存在");
        
        // 菜单类型需要检查路由唯一性
        if (menu.getMenuType() != null && !menu.getMenuType().equals("F")) {
            XiaoYuThrowUtil.throwIfFalse(checkPathUnique(menu.getPath(), 
                    menu.getParentId(), null), "路由地址已存在");
        }
        
        // 按钮类型需要检查权限标识唯一性
        if (menu.getMenuType() != null && menu.getMenuType().equals("F")) {
            XiaoYuThrowUtil.throwIfFalse(checkPermUnique(menu.getPermission(), null), 
                    "权限标识已存在");
        }
        
        systemMenuMapper.insert(menu);
        log.info("新增菜单: {}，ID: {}", menu.getName(), menu.getId());
        return menu.getId();
    }

    @Override
    @Transactional
    public void updateMenu(SystemMenu menu) {
        SystemMenu existing = systemMenuMapper.selectOneById(menu.getId());
        XiaoYuThrowUtil.throwIfNull(existing, "菜单不存在");
        
        // 检查菜单名称唯一性
        XiaoYuThrowUtil.throwIfFalse(checkMenuNameUnique(menu.getName(), 
                menu.getParentId(), menu.getId()), "菜单名称已存在");
        
        systemMenuMapper.update(menu);
        log.info("修改菜单: {}，ID: {}", menu.getName(), menu.getId());
    }

    @Override
    @Transactional
    public void deleteMenu(Long menuId) {
        SystemMenu menu = systemMenuMapper.selectOneById(menuId);
        XiaoYuThrowUtil.throwIfNull(menu, "菜单不存在");
        
        // 检查是否有子菜单
        QueryWrapper q = QueryWrapper.create().where("parent_id", menuId);
        List<SystemMenu> children = systemMenuMapper.selectListByQuery(q);
        XiaoYuThrowUtil.throwIf(!children.isEmpty(), "存在下级菜单，不可删除");
        
        // 删除角色菜单关联
        QueryWrapper roleQ = QueryWrapper.create().where("menu_id", menuId);
        systemRoleMenuMapper.deleteByQuery(roleQ);
        
        // 删除菜单
        systemMenuMapper.deleteById(menuId);
        log.info("删除菜单: {}，ID: {}", menu.getName(), menuId);
    }

    @Override
    public boolean checkMenuNameUnique(String menuName, Long parentId, Long menuId) {
        if (menuName == null || menuName.isEmpty()) {
            return true;
        }
        QueryWrapper q = QueryWrapper.create()
                .where("name", menuName)
                .and("parent_id", parentId);
        List<SystemMenu> list = systemMenuMapper.selectListByQuery(q);
        if (list.isEmpty()) {
            return true;
        }
        if (menuId == null) {
            return false;
        }
        return list.size() == 1 && list.get(0).getId().equals(menuId);
    }

    @Override
    public boolean checkPathUnique(String path, Long parentId, Long menuId) {
        if (path == null || path.isEmpty()) {
            return true;
        }
        QueryWrapper q = QueryWrapper.create()
                .where("path", path)
                .and("parent_id", parentId);
        List<SystemMenu> list = systemMenuMapper.selectListByQuery(q);
        if (list.isEmpty()) {
            return true;
        }
        if (menuId == null) {
            return false;
        }
        return list.size() == 1 && list.get(0).getId().equals(menuId);
    }

    @Override
    public boolean checkPermUnique(String perm, Long menuId) {
        if (perm == null || perm.isEmpty()) {
            return true;
        }
        QueryWrapper q = QueryWrapper.create().where("permission", perm);
        List<SystemMenu> list = systemMenuMapper.selectListByQuery(q);
        if (list.isEmpty()) {
            return true;
        }
        if (menuId == null) {
            return false;
        }
        return list.size() == 1 && list.get(0).getId().equals(menuId);
    }

    /**
     * 构建菜单树
     */
    private List<SystemMenu> buildMenuTree(Long parentId, List<SystemMenu> allMenus) {
        List<SystemMenu> tree = new ArrayList<>();
        for (SystemMenu menu : allMenus) {
            if (menu.getParentId().equals(parentId)) {
                tree.add(menu);
            }
        }
        return tree;
    }
}