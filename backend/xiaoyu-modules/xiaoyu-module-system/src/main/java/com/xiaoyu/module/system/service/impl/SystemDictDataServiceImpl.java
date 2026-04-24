package com.xiaoyu.module.system.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xiaoyu.common.core.utils.XiaoYuThrowUtil;
import com.xiaoyu.module.system.entity.SystemDictData;
import com.xiaoyu.module.system.entity.SystemDictType;
import com.xiaoyu.module.system.mapper.SystemDictDataMapper;
import com.xiaoyu.module.system.mapper.SystemDictTypeMapper;
import com.xiaoyu.module.system.service.SystemDictDataService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 字典数据 Service 实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SystemDictDataServiceImpl extends ServiceImpl<SystemDictDataMapper, SystemDictData> implements SystemDictDataService {

    private final SystemDictDataMapper systemDictDataMapper;
    private final SystemDictTypeMapper systemDictTypeMapper;

    @Override
    public List<SystemDictData> getDictDataList() {
        QueryWrapper q = QueryWrapper.create().orderBy("sort", true);
        return systemDictDataMapper.selectListByQuery(q);
    }

    @Override
    public List<SystemDictData> getDictDataByTypeId(Long dictTypeId) {
        QueryWrapper q = QueryWrapper.create()
                .where("dict_type_id", dictTypeId)
                .orderBy("sort", true);
        return systemDictDataMapper.selectListByQuery(q);
    }

    @Override
    public List<SystemDictData> getDictDataByTypeCode(String dictTypeCode) {
        // 先根据编码获取字典类型
        QueryWrapper typeQ = QueryWrapper.create().where("code", dictTypeCode);
        SystemDictType dictType = systemDictTypeMapper.selectOneByQuery(typeQ);
        if (dictType == null) {
            return List.of();
        }
        
        // 再获取字典数据
        QueryWrapper q = QueryWrapper.create()
                .where("dict_type_id", dictType.getId())
                .orderBy("sort", true);
        return systemDictDataMapper.selectListByQuery(q);
    }

    @Override
    public SystemDictData getDictDataById(Long dictDataId) {
        SystemDictData dictData = systemDictDataMapper.selectOneById(dictDataId);
        XiaoYuThrowUtil.throwIfNull(dictData, "字典数据不存在");
        return dictData;
    }

    @Override
    @Transactional
    public Long createDictData(SystemDictData dictData) {
        // 检查字典类型是否存在
        SystemDictType dictType = systemDictTypeMapper.selectOneById(dictData.getDictTypeId());
        XiaoYuThrowUtil.throwIfNull(dictType, "字典类型不存在");
        
        // 检查值是否唯一
        XiaoYuThrowUtil.throwIfFalse(checkDictDataValueUnique(dictData.getValue(), 
                dictData.getDictTypeId(), null), "字典数据值已存在");
        
        systemDictDataMapper.insert(dictData);
        log.info("新增字典数据: {}，ID: {}", dictData.getLabel(), dictData.getId());
        return dictData.getId();
    }

    @Override
    @Transactional
    public void updateDictData(SystemDictData dictData) {
        SystemDictData existing = systemDictDataMapper.selectOneById(dictData.getId());
        XiaoYuThrowUtil.throwIfNull(existing, "字典数据不存在");
        
        XiaoYuThrowUtil.throwIfFalse(checkDictDataValueUnique(dictData.getValue(), 
                dictData.getDictTypeId(), dictData.getId()), "字典数据值已存在");
        
        systemDictDataMapper.update(dictData);
        log.info("修改字典数据: {}，ID: {}", dictData.getLabel(), dictData.getId());
    }

    @Override
    @Transactional
    public void deleteDictData(Long dictDataId) {
        SystemDictData dictData = systemDictDataMapper.selectOneById(dictDataId);
        XiaoYuThrowUtil.throwIfNull(dictData, "字典数据不存在");
        
        systemDictDataMapper.deleteById(dictDataId);
        log.info("删除字典数据: {}，ID: {}", dictData.getLabel(), dictDataId);
    }

    @Override
    public boolean checkDictDataValueUnique(String value, Long dictTypeId, Long dictDataId) {
        if (value == null || value.isEmpty()) {
            return true;
        }
        QueryWrapper q = QueryWrapper.create()
                .where("value", value)
                .and("dict_type_id", dictTypeId);
        List<SystemDictData> list = systemDictDataMapper.selectListByQuery(q);
        if (list.isEmpty()) {
            return true;
        }
        if (dictDataId == null) {
            return false;
        }
        return list.size() == 1 && list.get(0).getId().equals(dictDataId);
    }
}