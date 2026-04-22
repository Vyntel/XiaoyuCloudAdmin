package com.xiaoyu.common.core.service.impl;

import com.xiaoyu.common.core.entity.BaseEntity;
import com.xiaoyu.common.core.mapper.IBaseMapper;
import com.xiaoyu.common.core.service.IBaseService;
import lombok.extern.slf4j.Slf4j;

/**
 * 基础 Service 实现类
 * 所有业务 ServiceImpl 应继承此类
 *
 * @param <M> Mapper 类型
 * @param <T> 实体类型（继承自 BaseEntity）
 * @author xiaoyu
 */
@Slf4j
public abstract class BaseServiceImpl<M extends IBaseMapper<T>, T extends BaseEntity> implements IBaseService<M, T> {

    @Override
    public M getMapper() {
        return getMapperDelegate();
    }

    /**
     * 子类实现此方法提供具体的 Mapper 实例
     */
    protected abstract M getMapperDelegate();
}