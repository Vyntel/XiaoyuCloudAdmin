package com.xiaoyu.module.ai.service;

import com.xiaoyu.module.ai.entity.AiKnowledge;
import java.util.List;

public interface AiKnowledgeService {

    List<AiKnowledge> getKnowledgePage(Integer pageNum, Integer pageSize, String name, Integer status);

    AiKnowledge getKnowledgeById(Long id);

    Long createKnowledge(AiKnowledge knowledge);

    boolean updateKnowledge(AiKnowledge knowledge);

    boolean deleteKnowledge(Long id);

    List<AiKnowledge> getEnabledList();
}