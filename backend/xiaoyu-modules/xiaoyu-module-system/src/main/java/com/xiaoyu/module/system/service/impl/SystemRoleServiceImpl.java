package com.xiaoyu.module.system.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.xiaoyu.common.core.exception.ServiceException;
import com.xiaoyu.common.core.service.impl.BaseServiceImpl;
import com.xiaoyu.module.system.entity.SystemRole;
import com.xiaoyu.module.system.mapper.SystemRoleMapper;
import com.xiaoyu.module.system.service.SystemRoleService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

/**
 * 角色Service实现类
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SystemRoleServiceImpl extends BaseServiceImpl<SystemRoleMapper, SystemRole> implements SystemRoleService {

    private final SystemRoleMapper systemRoleMapper;

    @Override
    protected SystemRoleMapper getMapperDelegate() {
        return systemRoleMapper;
    }

    @Override
    public SystemRole selectByRoleCode(String roleCode) {
        if (roleCode == null || roleCode.trim().isEmpty()) {
            throw new ServiceException("角色编码不能为空");
        }
        QueryWrapper queryWrapper = QueryWrapper.create()
            .where("role_code", roleCode.trim());
        return getMapper().selectOneByQuery(queryWrapper);
    }

    @Override
    public SystemRole selectByRoleName(String roleName) {
        if (roleName == null || roleName.trim().isEmpty()) {
            throw new ServiceException("角色名称不能为空");
        }
        QueryWrapper queryWrapper = QueryWrapper.create()
            .where("role_name", roleName.trim());
        return getMapper().selectOneByQuery(queryWrapper);
    }

    @Override
    public boolean checkRoleCodeUnique(String roleCode, Long roleId) {
        if (roleCode == null || roleCode.trim().isEmpty()) {
            return false;
        }
        QueryWrapper queryWrapper = QueryWrapper.create()
            .where("role_code", roleCode.trim());
        if (roleId != null) {
            queryWrapper.and("role_id", roleId);
        }
        return getMapper().selectOneByQuery(queryWrapper) == null;
    }

    @Override
    public boolean checkRoleNameUnique(String roleName, Long roleId) {
        if (roleName == null || roleName.trim().isEmpty()) {
            return false;
        }
        QueryWrapper queryWrapper = QueryWrapper.create()
            .where("role_name", roleName.trim());
        if (roleId != null) {
            queryWrapper.and("role_id", roleId);
        }
        return getMapper().selectOneByQuery(queryWrapper) == null;
    }

    @Override
    public List<SystemRole> selectByStatus(Integer status) {
        if (status == null) {
            throw new ServiceException("状态不能为空");
        }
        QueryWrapper queryWrapper = QueryWrapper.create()
            .where("status", status)
            .orderBy("role_sort", true);
        return getMapper().selectListByQuery(queryWrapper);
    }
}