package com.xiaoyu.module.system.controller;

import com.mybatisflex.core.query.QueryWrapper;
import com.xiaoyu.common.core.result.Result;
import com.xiaoyu.common.core.result.ResultCode;
import com.xiaoyu.module.system.service.IBaseService;
import lombok.extern.slf4j.Slf4j;
import org.springframework.web.bind.annotation.*;

import java.io.Serializable;
import java.util.List;

/**
 * 基础 Controller
 *
 * @param <S> Service 类型
 * @param <T> 实体类型
 * @param <ID> ID 类型
 * @author xiaoyu
 */
@Slf4j
public abstract class BaseController<S extends IBaseService<?, T, ID>, T, ID extends Serializable> {

    protected abstract S getService();

    @GetMapping("/{id}")
    public Result<T> getById(@PathVariable ID id) {
        T entity = getService().getById(id);
        if (entity == null) return Result.fail(ResultCode.NOT_FOUND);
        return Result.success(entity);
    }

    @GetMapping("/list")
    public Result<List<T>> list() {
        return Result.success(getService().list());
    }

    @GetMapping("/count")
    public Result<Long> count() {
        return Result.success(getService().count());
    }

    @PostMapping
    public Result<Boolean> save(@RequestBody T entity) {
        return Result.success(getService().save(entity));
    }

    @PutMapping
    public Result<Boolean> update(@RequestBody T entity) {
        return Result.success(getService().updateById(entity));
    }

    @DeleteMapping("/{id}")
    public Result<Boolean> remove(@PathVariable ID id) {
        return Result.success(getService().removeById(id));
    }
}