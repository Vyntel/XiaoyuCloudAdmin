package com.xiaoyu.common.core.service;

import com.xiaoyu.common.core.entity.BaseEntity;
import com.xiaoyu.common.core.mapper.IBaseMapper;
import com.mybatisflex.core.query.QueryWrapper;

import java.util.Collection;
import java.util.List;

/**
 * 基础 Service 接口
 * 所有业务 Service 接口应继承此接口
 *
 * @param <M> Mapper 类型
 * @param <T> 实体类型（继承自 BaseEntity）
 * @author xiaoyu
 */
public interface IBaseService<M extends IBaseMapper<T>, T extends BaseEntity> {

    /** 获取 Mapper */
    M getMapper();

    /** 根据 ID 查询 */
    default T getById(Long id) { return getMapper().selectOneById(id); }

    /** 根据 ID 列表查询 */
    default List<T> listByIds(Collection<Long> ids) { return getMapper().selectListByIds(ids); }

    /** 查询所有 */
    default List<T> list() { return getMapper().selectAll(); }

    /** 根据条件查询单条 */
    default T getOne(QueryWrapper q) { return q == null ? null : getMapper().selectOneByQuery(q); }

    /** 根据条件查询列表 */
    default List<T> list(QueryWrapper q) { return q == null ? getMapper().selectAll() : getMapper().selectListByQuery(q); }

    /** 分页查询 */
    default List<T> page(int pageNum, int pageSize, QueryWrapper q) {
        int offset = (pageNum - 1) * pageSize;
        QueryWrapper query = q == null ? QueryWrapper.create() : q;
        query.limit(pageSize).offset(offset);
        return getMapper().selectListByQuery(query);
    }

    /** 统计数量 */
    default long count() { return getMapper().selectCountByQuery(null); }

    /** 根据条件统计数量 */
    default long count(QueryWrapper q) { return q == null ? getMapper().selectCountByQuery(null) : getMapper().selectCountByQuery(q); }

    /** 判断是否存在 */
    default boolean existsById(Long id) { return getById(id) != null; }

    /** 判断是否存在 */
    default boolean exists(QueryWrapper q) { return count(q) > 0; }

    /** 保存实体 */
    default boolean save(T entity) { return getMapper().insert(entity) > 0; }

    /** 更新实体 */
    default boolean updateById(T entity) { return getMapper().update(entity) > 0; }

    /** 删除实体 */
    default boolean removeById(Long id) { return getMapper().deleteById(id) > 0; }

    /** 根据条件删除 */
    default int remove(QueryWrapper q) { return getMapper().deleteByQuery(q); }
}