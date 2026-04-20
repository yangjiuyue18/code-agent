package com.campus.agent.agent;

import com.campus.agent.entity.Knowledge;
import com.campus.agent.mapper.KnowledgeMapper;
import com.campus.agent.service.KnowledgeService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 生活服务Agent：食堂、快递、报修、校园电话等
 */
@Component
public class LifeAgent {

    @Autowired
    private KnowledgeMapper knowledgeMapper;
    @Autowired
    private KnowledgeService knowledgeService;

    /**
     * 查询生活服务信息
     * @param keywords 关键词
     * @return 知识库答案
     */
    public Knowledge queryLifeService(String keywords) {return knowledgeService.queryKnowledge(keywords);
    }
}

