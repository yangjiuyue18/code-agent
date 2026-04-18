package com.campus.agent.mapper;

import com.baomidou.mybatisplus.core.mapper.BaseMapper;
import com.campus.agent.entity.ChatHistory;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.apache.ibatis.annotations.Select;

import java.util.List;

/**
 * 多轮对话历史 Mapper
 */
@Mapper
public interface ChatHistoryMapper extends BaseMapper<ChatHistory> {

    /**
     * 查询用户最近的对话历史（按时间升序，用于拼装上下文）
     * @param userId 用户ID
     * @param limit 最近N轮（每轮含user+assistant共2条，取N*2条）
     * @return 对话历史列表
     */
    @Select("SELECT * FROM chat_history WHERE user_id = #{userId} ORDER BY create_time ASC LIMIT #{limit}")
    List<ChatHistory> selectRecentByUserId(@Param("userId") Long userId, @Param("limit") int limit);

    /**
     * 删除用户全部对话历史（用于清空对话）
     * @param userId 用户ID
     */
    void deleteByUserId(@Param("userId") Long userId);
}
