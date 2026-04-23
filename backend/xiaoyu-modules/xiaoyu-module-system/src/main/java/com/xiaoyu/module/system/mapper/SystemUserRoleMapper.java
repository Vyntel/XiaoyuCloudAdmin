package com.xiaoyu.module.system.mapper;

import com.mybatisflex.core.BaseMapper;
import com.xiaoyu.module.system.entity.SystemUserRole;
import org.apache.ibatis.annotations.Mapper;

/**
 * 用户-角色关联 Mapper
 */
@Mapper
public interface SystemUserRoleMapper extends BaseMapper<SystemUserRole> {
}