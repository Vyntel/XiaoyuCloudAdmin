package com.xiaoyu.module.system.service;

import com.mybatisflex.core.BaseMapper;
import com.mybatisflex.core.query.QueryWrapper;

import java.io.Serializable;
import java.util.Collection;
import java.util.List;

/**
 * 基础 Service 接口
 *
 * @param <M> Mapper 类型
 * @param <T> 实体类型
 * @param <ID> ID 类型
 * @author xiaoyu
 */
public interface IBaseService<M extends BaseMapper<T>, T, ID extends Serializable> {

    /** 获取 Mapper */
    M getMapper();

    /** 根据 ID 查询 */
    default T getById(ID id) { return getMapper().selectOneById(id); }

    /** 根据 ID 列表查询 */
    default List<T> listByIds(Collection<ID> ids) { return getMapper().selectListByIds(ids); }

    /** 查询所有 */
    default List<T> list() { return getMapper().selectAll(); }

    /** 根据条件查询单条 */
    default T getOne(QueryWrapper q) { return q == null ? null : getMapper().selectOneByQuery(q); }

    /** 根据条件查询列表 */
    default List<T> list(QueryWrapper q) { return q == null ? getMapper().selectAll() : getMapper().selectListByQuery(q); }

    /** 统计数量 */
    default long count() { return getMapper().selectCountByQuery(null); }

    /** 根据条件统计数量 */
    default long count(QueryWrapper q) { return q == null ? getMapper().selectCountByQuery(null) : getMapper().selectCountByQuery(q); }

    /** 保存实体 */
    default boolean save(T entity) { return getMapper().insert(entity) > 0; }

    /** 更新实体 */
    default boolean updateById(T entity) { return getMapper().update(entity) > 0; }

    /** 删除实体 */
    default boolean removeById(ID id) { return getMapper().deleteById(id) > 0; }

    /** 根据条件删除 */
    default int remove(QueryWrapper q) { return getMapper().deleteByQuery(q); }
}