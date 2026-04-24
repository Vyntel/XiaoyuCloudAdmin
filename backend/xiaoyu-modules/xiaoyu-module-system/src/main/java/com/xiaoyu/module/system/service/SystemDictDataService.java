package com.xiaoyu.module.system.service;

import com.mybatisflex.core.service.IService;
import com.xiaoyu.module.system.entity.SystemDictData;

import java.util.List;

/**
 * 字典数据Service接口
 */
public interface SystemDictDataService extends IService<SystemDictData> {

    /**
     * 获取字典数据列表
     */
    List<SystemDictData> getDictDataList();

    /**
     * 根据字典类型ID获取字典数据
     */
    List<SystemDictData> getDictDataByTypeId(Long dictTypeId);

    /**
     * 根据字典类型编码获取字典数据
     */
    List<SystemDictData> getDictDataByTypeCode(String dictTypeCode);

    /**
     * 根据ID获取字典数据
     */
    SystemDictData getDictDataById(Long dictDataId);

    /**
     * 新增字典数据
     */
    Long createDictData(SystemDictData dictData);

    /**
     * 修改字典数据
     */
    void updateDictData(SystemDictData dictData);

    /**
     * 删除字典数据
     */
    void deleteDictData(Long dictDataId);

    /**
     * 检查字典数据值是否唯一
     */
    boolean checkDictDataValueUnique(String value, Long dictTypeId, Long dictDataId);
}