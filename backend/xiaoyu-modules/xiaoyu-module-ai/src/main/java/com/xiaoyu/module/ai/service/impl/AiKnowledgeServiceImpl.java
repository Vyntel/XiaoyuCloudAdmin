package com.xiaoyu.module.ai.service.impl;

import com.mybatisflex.core.query.QueryWrapper;
import com.mybatisflex.spring.service.impl.ServiceImpl;
import com.xiaoyu.module.ai.entity.AiKnowledge;
import com.xiaoyu.module.ai.mapper.AiKnowledgeMapper;
import com.xiaoyu.module.ai.service.AiKnowledgeService;
import lombok.RequiredArgsConstructor;
import lombok.extern.slf4j.Slf4j;
import org.springframework.stereotype.Service;

import java.util.List;

@Slf4j
@Service
@RequiredArgsConstructor
public class AiKnowledgeServiceImpl extends ServiceImpl<AiKnowledgeMapper, AiKnowledge> implements AiKnowledgeService {

    private final AiKnowledgeMapper aiKnowledgeMapper;

    @Override
    public List<AiKnowledge> getKnowledgePage(Integer pageNum, Integer pageSize, String name, Integer status) {
        QueryWrapper wrapper = QueryWrapper.create();
        if (name != null && !name.isEmpty()) {
            wrapper.and("name like {0}", "%" + name + "%");
        }
        if (status != null) {
            wrapper.and("status = {0}", status);
        }
        wrapper.orderBy("created_time", false);
        return aiKnowledgeMapper.selectListByQuery(wrapper);
    }

    @Override
    public AiKnowledge getKnowledgeById(Long id) {
        return aiKnowledgeMapper.selectOneById(id);
    }

    @Override
    public Long createKnowledge(AiKnowledge knowledge) {
        aiKnowledgeMapper.insert(knowledge, true);
        return knowledge.getId();
    }

    @Override
    public boolean updateKnowledge(AiKnowledge knowledge) {
        return aiKnowledgeMapper.update(knowledge) > 0;
    }

    @Override
    public boolean deleteKnowledge(Long id) {
        return aiKnowledgeMapper.deleteById(id) > 0;
    }

    @Override
    public List<AiKnowledge> getEnabledList() {
        QueryWrapper wrapper = QueryWrapper.create();
        wrapper.where("status = 0");
        wrapper.orderBy("created_time", false);
        return aiKnowledgeMapper.selectListByQuery(wrapper);
    }
}