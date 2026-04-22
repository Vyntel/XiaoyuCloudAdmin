package com.xiaoyu.module.system.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.xiaoyu.module.system.entity.SystemUser;
import org.apache.ibatis.annotations.Mapper;

/**
 * 系统用户 Mapper
 */
@Mapper
public interface SystemUserMapper extends BaseMapper<SystemUser> {
}