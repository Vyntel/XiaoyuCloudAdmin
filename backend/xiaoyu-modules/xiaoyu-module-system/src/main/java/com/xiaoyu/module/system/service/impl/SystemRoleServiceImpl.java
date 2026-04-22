package com.xiaoyu.module.system.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.xiaoyu.common.core.exception.ServiceException;
import com.xiaoyu.module.system.entity.SystemRole;
import com.xiaoyu.module.system.mapper.SystemRoleMapper;
import com.xiaoyu.module.system.service.SystemRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class SystemRoleServiceImpl implements SystemRoleService {

    private final SystemRoleMapper systemRoleMapper;

    @Override
    public SystemRole selectByRoleCode(String roleCode) {
        if (roleCode == null || roleCode.trim().isEmpty()) throw new ServiceException("角色编码不能为空");
        QueryWrapper q = QueryWrapper.create().where("role_code", roleCode.trim());
        return systemRoleMapper.selectOneByQuery(q);
    }

    @Override
    public SystemRole selectByRoleName(String roleName) {
        if (roleName == null || roleName.trim().isEmpty()) throw new ServiceException("角色名称不能为空");
        QueryWrapper q = QueryWrapper.create().where("role_name", roleName.trim());
        return systemRoleMapper.selectOneByQuery(q);
    }

    @Override
    public boolean checkRoleCodeUnique(String roleCode, Long roleId) {
        if (roleCode == null || roleCode.trim().isEmpty()) return false;
        QueryWrapper q = QueryWrapper.create().where("role_code", roleCode.trim());
        if (roleId != null) q.and("role_id", roleId);
        return systemRoleMapper.selectOneByQuery(q) == null;
    }

    @Override
    public boolean checkRoleNameUnique(String roleName, Long roleId) {
        if (roleName == null || roleName.trim().isEmpty()) return false;
        QueryWrapper q = QueryWrapper.create().where("role_name", roleName.trim());
        if (roleId != null) q.and("role_id", roleId);
        return systemRoleMapper.selectOneByQuery(q) == null;
    }

    @Override
    public List<SystemRole> selectByStatus(Integer status) {
        if (status == null) throw new ServiceException("状态不能为空");
        QueryWrapper q = QueryWrapper.create().where("status", status).orderBy("role_sort", true);
        return systemRoleMapper.selectListByQuery(q);
    }
}