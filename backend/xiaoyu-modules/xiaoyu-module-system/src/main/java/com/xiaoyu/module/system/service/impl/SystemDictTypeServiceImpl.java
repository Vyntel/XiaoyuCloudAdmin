package com.xiaoyu.module.system.service.impl;

import com.mybatisflex.core.paginate.Page;
import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xiaoyu.common.core.utils.XiaoYuThrowUtil;
import com.xiaoyu.module.system.entity.SystemDictType;
import com.xiaoyu.module.system.entity.SystemDictData;
import com.xiaoyu.module.system.mapper.SystemDictTypeMapper;
import com.xiaoyu.module.system.mapper.SystemDictDataMapper;
import com.xiaoyu.module.system.service.SystemDictTypeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;

import java.util.List;

/**
 * 字典类型 Service 实现
 */
@Slf4j
@Service
@RequiredArgsConstructor
public class SystemDictTypeServiceImpl extends ServiceImpl<SystemDictTypeMapper, SystemDictType> implements SystemDictTypeService {

    private final SystemDictTypeMapper systemDictTypeMapper;
    private final SystemDictDataMapper systemDictDataMapper;

    @Override
    public List<SystemDictType> getDictTypeList() {
        QueryWrapper q = QueryWrapper.create().orderBy("sort", true);
        return systemDictTypeMapper.selectListByQuery(q);
    }

    @Override
    public List<SystemDictType> getDictTypePage(Integer pageNum, Integer pageSize, String name, String code, Integer status) {
        QueryWrapper q = QueryWrapper.create()
                .like("name", name)
                .like("code", code)
                .eq("status", status)
                .orderBy("sort", true);
        Page<SystemDictType> page = systemDictTypeMapper.paginate(pageNum, pageSize, q);
        return page.getRecords();
    }

    @Override
    public SystemDictType getDictTypeById(Long dictId) {
        SystemDictType dictType = systemDictTypeMapper.selectOneById(dictId);
        XiaoYuThrowUtil.throwIfNull(dictType, "字典类型不存在");
        return dictType;
    }

    @Override
    @Transactional
    public Long createDictType(SystemDictType dictType) {
        XiaoYuThrowUtil.throwIfFalse(checkDictTypeCodeUnique(dictType.getCode(), null), 
                "字典类型编码已存在");
        systemDictTypeMapper.insert(dictType);
        log.info("新增字典类型: {}，ID: {}", dictType.getName(), dictType.getId());
        return dictType.getId();
    }

    @Override
    @Transactional
    public void updateDictType(SystemDictType dictType) {
        SystemDictType existing = systemDictTypeMapper.selectOneById(dictType.getId());
        XiaoYuThrowUtil.throwIfNull(existing, "字典类型不存在");
        
        XiaoYuThrowUtil.throwIfFalse(checkDictTypeCodeUnique(dictType.getCode(), dictType.getId()), 
                "字典类型编码已存在");
        
        systemDictTypeMapper.update(dictType);
        log.info("修改字典类型: {}，ID: {}", dictType.getName(), dictType.getId());
    }

    @Override
    @Transactional
    public void deleteDictType(Long dictId) {
        SystemDictType dictType = systemDictTypeMapper.selectOneById(dictId);
        XiaoYuThrowUtil.throwIfNull(dictType, "字典类型不存在");
        
        // 检查是否有字典数据
        QueryWrapper q = QueryWrapper.create().where("dict_type_id", dictId);
        List<SystemDictData> dictDataList = systemDictDataMapper.selectListByQuery(q);
        XiaoYuThrowUtil.throwIf(!dictDataList.isEmpty(), "存在字典数据，不可删除");
        
        systemDictTypeMapper.deleteById(dictId);
        log.info("删除字典类型: {}，ID: {}", dictType.getName(), dictId);
    }

    @Override
    public boolean checkDictTypeCodeUnique(String code, Long dictId) {
        if (code == null || code.isEmpty()) {
            return true;
        }
        QueryWrapper q = QueryWrapper.create().where("code", code);
        List<SystemDictType> list = systemDictTypeMapper.selectListByQuery(q);
        if (list.isEmpty()) {
            return true;
        }
        if (dictId == null) {
            return false;
        }
        return list.size() == 1 && list.get(0).getId().equals(dictId);
    }
}