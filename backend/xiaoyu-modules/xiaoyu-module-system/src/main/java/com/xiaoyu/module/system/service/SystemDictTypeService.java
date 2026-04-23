package com.xiaoyu.module.system.service;

import com.mybatisflex.core.service.IService;
import com.xiaoyu.module.system.entity.SystemDictType;

import java.util.List;

/**
 * 字典类型Service接口
 */
public interface SystemDictTypeService extends IService<SystemDictType> {

    /**
     * 获取字典类型列表
     */
    List<SystemDictType> getDictTypeList();

    /**
     * 分页查询字典类型
     */
    List<SystemDictType> getDictTypePage(Integer pageNum, Integer pageSize, String name, String code, Integer status);

    /**
     * 根据ID获取字典类型
     */
    SystemDictType getDictTypeById(Long dictId);

    /**
     * 新增字典类型
     */
    Long createDictType(SystemDictType dictType);

    /**
     * 修改字典类型
     */
    void updateDictType(SystemDictType dictType);

    /**
     * 删除字典类型
     */
    void deleteDictType(Long dictId);

    /**
     * 检查字典类型编码是否唯一
     */
    boolean checkDictTypeCodeUnique(String code, Long dictId);
}