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
}