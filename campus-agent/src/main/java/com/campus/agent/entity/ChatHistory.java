package com.campus.agent.entity;

import com.baomidou.mybatisplus.annotation.IdType;
import com.baomidou.mybatisplus.annotation.TableId;
import com.baomidou.mybatisplus.annotation.TableName;
import lombok.Data;

import java.time.LocalDateTime;

/**
 * 多轮对话历史记录实体
 * 存储用户与AI的对话历史，支持上下文记忆
 */
@Data
@TableName("chat_history")
public class ChatHistory {

    @TableId(type = IdType.AUTO)
    private Long id; // 记录ID

    private Long userId; // 用户ID

    private String role; // 角色：user / assistant

    private String content; // 消息内容

    private LocalDateTime createTime; // 创建时间
}
