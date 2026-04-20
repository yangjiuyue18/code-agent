package com.campus.agent.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.campus.agent.entity.Knowledge;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Select;

import java.util.List;

@Mapper
public interface KnowledgeMapper extends BaseMapper<Knowledge> {
    // 模糊查询知识库（匹配问题）
    @Select("select * from knowledge where question like concat('%',#{question},'%') limit 1")
    Knowledge selectByQuestionLike(String question);
}
