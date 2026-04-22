package com.xiaoyu.module.system.mapper;

import com.mybatisflex.core.BaseMapper;

/**
 * 基础 Mapper 接口
 * 继承自 MyBatis-Flex 的 BaseMapper
 *
 * @param <T> 实体类型
 * @author xiaoyu
 */
public interface IBaseMapper<T> extends BaseMapper<T> {
    // BaseMapper 已提供基本 CRUD 方法
}
