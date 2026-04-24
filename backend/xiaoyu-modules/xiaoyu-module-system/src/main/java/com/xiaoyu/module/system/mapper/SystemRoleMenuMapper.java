package com.xiaoyu.module.system.mapper;

import com.mybatisflex.core.BaseMapper;
import com.xiaoyu.module.system.entity.SystemRoleMenu;
import org.apache.ibatis.annotations.Mapper;

/**
 * 角色-菜单关联 Mapper
 */
@Mapper
public interface SystemRoleMenuMapper extends BaseMapper<SystemRoleMenu> {
}