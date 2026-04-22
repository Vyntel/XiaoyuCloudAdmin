package com.xiaoyu.common.core.controller;

import com.mybatisflex.core.query.QueryWrapper;
import com.xiaoyu.common.core.entity.BaseEntity;
import com.xiaoyu.common.core.result.PageResult;
import com.xiaoyu.common.core.result.Result;
import com.xiaoyu.common.core.result.ResultCode;
import com.xiaoyu.common.core.service.IBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.util.List;

/**
 * 基础 Controller
 * 所有业务 Controller 应继承此类
 *
 * @param <S> Service 类型
 * @param <T> 实体类型（继承自 BaseEntity）
 * @author xiaoyu
 */
@Slf4j
public abstract class BaseController<S extends IBaseService<?, T>, T extends BaseEntity> {

    protected S service;

    /**
     * 获取 Service
     */
    protected abstract S getService();

    /**
     * 获取分页结果包装类
     */
    protected abstract PageResult<T> getPageResult(List<T> list, long total);

    /**
     * 根据 ID 查询
     */
    @GetMapping("/{id}")
    public Result<T> getById(@PathVariable("id") Long id) {
        T entity = getService().getById(id);
        if (entity == null) {
            return Result.fail(ResultCode.NOT_FOUND);
        }
        return Result.success(entity);
    }

    /**
     * 查询所有
     */
    @GetMapping("/list")
    public Result<List<T>> list() {
        return Result.success(getService().list());
    }

    /**
     * 分页查询
     */
    @GetMapping("/page")
    public Result<PageResult<T>> page(
            @RequestParam(value = "pageNum", defaultValue = "1") int pageNum,
            @RequestParam(value = "pageSize", defaultValue = "10") int pageSize,
            @RequestParam(value = "query", required = false) String queryJson) {
        QueryWrapper q = buildQueryWrapper(queryJson);
        List<T> list = getService().page(pageNum, pageSize, q);
        long total = getService().count(q);
        return Result.success(getPageResult(list, total));
    }

    /**
     * 统计数量
     */
    @GetMapping("/count")
    public Result<Long> count(
            @RequestParam(value = "query", required = false) String queryJson) {
        QueryWrapper q = buildQueryWrapper(queryJson);
        return Result.success(getService().count(q));
    }

    /**
     * 判断是否存在
     */
    @GetMapping("/exists/{id}")
    public Result<Boolean> exists(@PathVariable("id") Long id) {
        return Result.success(getService().existsById(id));
    }

    /**
     * 新增
     */
    @PostMapping
    public Result<Boolean> save(@RequestBody T entity) {
        return Result.success(getService().save(entity));
    }

    /**
     * 修改
     */
    @PutMapping
    public Result<Boolean> update(@RequestBody T entity) {
        return Result.success(getService().updateById(entity));
    }

    /**
     * 删除
     */
    @DeleteMapping("/{id}")
    public Result<Boolean> remove(@PathVariable("id") Long id) {
        return Result.success(getService().removeById(id));
    }

    /**
     * 根据条件构建 QueryWrapper
     * 子类可重写此方法实现自定义查询条件
     */
    protected QueryWrapper buildQueryWrapper(String queryJson) {
        QueryWrapper q = QueryWrapper.create();
        // TODO: 可扩展从 JSON 解析查询条件
        return q;
    }
}