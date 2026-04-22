package com.xiaoyu.module.system.service.impl;

import com.mybatisflex.core.BaseMapper;
import com.xiaoyu.module.system.service.IBaseService;
import lombok.extern.slf4j.Slf4j;

import java.io.Serializable;

/**
 * 基础 Service 实现类
 *
 * @param <M> Mapper 类型
 * @param <T> 实体类型
 * @param <ID> ID 类型
 * @author xiaoyu
 */
@Slf4j
public abstract class BaseServiceImpl<M extends BaseMapper<T>, T, ID extends Serializable> implements IBaseService<M, T, ID> {
    
    @Override
    public M getMapper() {
        return getMapperDelegate();
    }
    
    protected abstract M getMapperDelegate();
}