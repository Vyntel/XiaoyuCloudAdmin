package com.xiaoyu.module.ai.service;

import com.mybatisflex.core.service.IService;
import com.xiaoyu.module.ai.entity.AiModel;

import java.util.List;

/**
 * AI模型 Service
 */
public interface AiModelService extends IService<AiModel> {

    /**
     * 获取启用的模型列表
     */
    List<AiModel> getEnabledModels();

    /**
     * 根据编码获取模型
     */
    AiModel getModelByCode(String code);

    /**
     * 创建模型
     */
    Long createModel(AiModel model);

    /**
     * 更新模型
     */
    boolean updateModel(AiModel model);

    /**
     * 删除模型
     */
    boolean deleteModel(Long id);

    /**
     * 分页查询模型列表
     */
    List<AiModel> getModelPage(Integer pageNum, Integer pageSize, String name, String provider, Integer status);

    /**
     * 根据ID获取模型
     */
    AiModel getModelById(Long id);

    /**
     * 修改模型状态
     */
    boolean updateModelStatus(Long id, Integer status);
}