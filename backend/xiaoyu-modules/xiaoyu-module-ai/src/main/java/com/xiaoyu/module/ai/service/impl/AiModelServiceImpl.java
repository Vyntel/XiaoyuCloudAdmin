package com.xiaoyu.module.ai.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xiaoyu.module.ai.entity.AiModel;
import com.xiaoyu.module.ai.mapper.AiModelMapper;
import com.xiaoyu.module.ai.service.AiModelService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiModelServiceImpl extends ServiceImpl<AiModelMapper, AiModel> implements AiModelService {

    private final AiModelMapper aiModelMapper;

    @Override
    public List<AiModel> getEnabledModels() {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.where("status = 0");
        wrapper.orderBy("sort", true);
        return aiModelMapper.selectListByQuery(wrapper);
    }

    @Override
    public AiModel getModelByCode(String code) {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.where("code = {0}", code);
        wrapper.and("status = 0");
        return aiModelMapper.selectOneByQuery(wrapper);
    }

    @Override
    public Long createModel(AiModel model) {
        aiModelMapper.insert(model, true);
        return model.getId();
    }

    @Override
    public boolean updateModel(AiModel model) {
        return aiModelMapper.update(model) > 0;
    }

    @Override
    public boolean deleteModel(Long id) {
        return aiModelMapper.deleteById(id) > 0;
    }
}