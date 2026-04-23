package com.xiaoyu.module.system.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xiaoyu.common.core.utils.XiaoYuThrowUtil;
import com.xiaoyu.module.system.entity.SystemRole;
import com.xiaoyu.module.system.entity.SystemRoleMenu;
import com.xiaoyu.module.system.mapper.SystemRoleMapper;
import com.xiaoyu.module.system.mapper.SystemRoleMenuMapper;
import com.xiaoyu.module.system.service.SystemRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;
import java.util.stream.Collectors;

/**
 * 角色 Service 实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SystemRoleServiceImpl extends ServiceImpl<SystemRoleMapper, SystemRole> implements SystemRoleService {

    private final SystemRoleMapper systemRoleMapper;
    private final SystemRoleMenuMapper systemRoleMenuMapper;

    @Override
    public List<SystemRole> getRolePage(Integer pageNum, Integer pageSize, String name, String code, Integer status) {
        QueryWrapper q = QueryWrapper.create()
                .like("name",name)
                .like("code",code)
                .eq("status",status)
                .orderBy("level", true);
        Page<SystemRole> page = systemRoleMapper.paginate(pageNum, pageSize, q);
        return page.getRecords();
    }

    @Override
    public SystemRole getRoleById(Long roleId) {
        SystemRole role = systemRoleMapper.selectOneById(roleId);
        XiaoYuThrowUtil.throwIfNull(role, "角色不存在");
        return role;
    }

    @Override
    @Transactional
    public Long createRole(SystemRole role) {
        XiaoYuThrowUtil.throwIfFalse(checkRoleCodeUnique(role.getCode(), null), "角色编码已存在");
        XiaoYuThrowUtil.throwIfFalse(checkRoleNameUnique(role.getName(), null), "角色名称已存在");
        systemRoleMapper.insert(role);
        return role.getId();
    }

    @Override
    @Transactional
    public void updateRole(SystemRole role) {
        XiaoYuThrowUtil.throwIfNull(systemRoleMapper.selectOneById(role.getId()), "角色不存在");
        systemRoleMapper.update(role);
    }

    @Override
    @Transactional
    public void deleteRole(Long roleId) {
        SystemRole role = systemRoleMapper.selectOneById(roleId);
        XiaoYuThrowUtil.throwIfNull(role, "角色不存在");
        // 删除角色菜单关联
        QueryWrapper q = QueryWrapper.create().where("role_id", roleId);
        systemRoleMenuMapper.deleteByQuery(q);
        // 逻辑删除角色
        systemRoleMapper.deleteById(roleId);
    }

    @Override
    @Transactional
    public void assignMenus(Long roleId, List<Long> menuIds) {
        XiaoYuThrowUtil.throwIfNull(systemRoleMapper.selectOneById(roleId), "角色不存在");
        // 删除旧关联
        QueryWrapper q = QueryWrapper.create().where("role_id", roleId);
        systemRoleMenuMapper.deleteByQuery(q);
        // 插入新关联
        for (Long menuId : menuIds) {
            SystemRoleMenu rm = new SystemRoleMenu();
            rm.setRoleId(roleId);
            rm.setMenuId(menuId);
            systemRoleMenuMapper.insert(rm);
        }
        log.info("角色ID: {} 菜单分配完成，共 {} 个菜单", roleId, menuIds.size());
    }

    @Override
    public List<Long> getRoleMenuIds(Long roleId) {
        QueryWrapper q = QueryWrapper.create().where("role_id", roleId);
        List<SystemRoleMenu> list = systemRoleMenuMapper.selectListByQuery(q);
        return list.stream().map(SystemRoleMenu::getMenuId).collect(Collectors.toList());
    }

    @Override
    public SystemRole selectByRoleCode(String roleCode) {
        QueryWrapper q = QueryWrapper.create().where("code", roleCode);
        return systemRoleMapper.selectOneByQuery(q);
    }

    @Override
    public SystemRole selectByRoleName(String roleName) {
        QueryWrapper q = QueryWrapper.create().where("name", roleName);
        return systemRoleMapper.selectOneByQuery(q);
    }

    @Override
    public boolean checkRoleCodeUnique(String roleCode, Long roleId) {
        QueryWrapper q = QueryWrapper.create().where("code", roleCode);
        List<SystemRole> list = systemRoleMapper.selectListByQuery(q);
        if (list.isEmpty()) {
            return true;
        }
        if (roleId == null) {
            return false;
        }
        return list.size() == 1 && list.get(0).getId().equals(roleId);
    }

    @Override
    public boolean checkRoleNameUnique(String roleName, Long roleId) {
        QueryWrapper q = QueryWrapper.create().where("name", roleName);
        List<SystemRole> list = systemRoleMapper.selectListByQuery(q);
        if (list.isEmpty()) {
            return true;
        }
        if (roleId == null) {
            return false;
        }
        return list.size() == 1 && list.get(0).getId().equals(roleId);
    }

    @Override
    public List<SystemRole> selectByStatus(Integer status) {
        QueryWrapper q = QueryWrapper.create().where("status", status).orderBy("level", true);
        return systemRoleMapper.selectListByQuery(q);
    }

    @Override
    public List<SystemRole> getRoleList() {
        QueryWrapper q = QueryWrapper.create().orderBy("level", true);
        return systemRoleMapper.selectListByQuery(q);
    }

    @Override
    public void updateRoleStatus(Long roleId, Integer status) {
        SystemRole role = systemRoleMapper.selectOneById(roleId);
        XiaoYuThrowUtil.throwIfNull(role, "角色不存在");
        role.setStatus(status);
        systemRoleMapper.update(role);
    }
}