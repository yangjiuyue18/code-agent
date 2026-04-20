package com.campus.agent.agent;

import com.campus.agent.entity.Knowledge;
import com.campus.agent.mapper.KnowledgeMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

/**
 * 校园资讯Agent：校园通知、校历、常见问题
 */
@Component
public class InfoAgent {

    @Autowired
    private KnowledgeMapper knowledgeMapper;

    /**
     * 查询校园资讯
     * @param keywords 关键词
     * @return 知识库答案
     */
    public Knowledge queryInfo(String keywords) {
        return knowledgeMapper.selectByQuestionLike(keywords);
    }
}

