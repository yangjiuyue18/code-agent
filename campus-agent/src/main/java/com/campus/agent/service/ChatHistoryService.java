package com.campus.agent.service;

import com.campus.agent.entity.ChatHistory;
import com.campus.agent.mapper.ChatHistoryMapper;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.time.LocalDateTime;
import java.util.List;
import java.util.stream.Collectors;

/**
 * 多轮对话历史服务
 * 负责存取用户的对话历史记录
 */
@Service
public class ChatHistoryService {

    private static final int MAX_HISTORY_TURNS = 10; // 默认保留最近10轮对话

    @Autowired
    private ChatHistoryMapper chatHistoryMapper;

    /**
     * 加载用户最近的对话历史（转为 QwenService 可用的消息格式）
     * @param userId 用户ID
     * @return 消息历史列表，每条为 {role, content}
     */
    public List<java.util.Map<String, String>> loadHistoryForQwen(Long userId) {
        return loadHistoryForQwen(userId, MAX_HISTORY_TURNS);
    }

    /**
     * 加载指定轮数的对话历史
     * @param userId 用户ID
     * @param turns 轮数（每轮含 user + assistant 共2条）
     * @return 消息历史列表
     */
    public List<java.util.Map<String, String>> loadHistoryForQwen(Long userId, int turns) {
        // 查询最近 turns 轮 * 2条（每轮user+assistant）
        List<ChatHistory> history = chatHistoryMapper.selectRecentByUserId(userId, turns * 2);
        return history.stream()
                .map(h -> {
                    java.util.Map<String, String> msg = new java.util.HashMap<>();
                    msg.put("role", h.getRole());
                    msg.put("content", h.getContent());
                    return msg;
                })
                .collect(Collectors.toList());
    }

    /**
     * 保存单条对话记录
     * @param userId 用户ID
     * @param role 角色（user / assistant）
     * @param content 消息内容
     */
    public void saveMessage(Long userId, String role, String content) {
        ChatHistory record = new ChatHistory();
        record.setUserId(userId);
        record.setRole(role);
        record.setContent(content);
        record.setCreateTime(LocalDateTime.now());
        chatHistoryMapper.insert(record);
    }

    /**
     * 保存一对对话（用户消息 + AI回复）
     * @param userId 用户ID
     * @param userContent 用户消息
     * @param assistantContent AI回复
     */
    public void saveConversation(Long userId, String userContent, String assistantContent) {
        saveMessage(userId, "user", userContent);
        saveMessage(userId, "assistant", assistantContent);
    }

    /**
     * 清空用户对话历史
     * @param userId 用户ID
     */
    public void clearHistory(Long userId) {
        chatHistoryMapper.deleteByUserId(userId);
    }

    /**
     * 导出用户对话历史为 Markdown 格式
     * @param userId 用户ID
     * @return Markdown 格式的对话记录
     */
    public String exportHistoryAsMarkdown(Long userId) {
        List<ChatHistory> history = chatHistoryMapper.selectRecentByUserId(userId, 1000);
        StringBuilder md = new StringBuilder();
        md.append("# 📝 校园助手聊天记录\n\n");
        md.append("> 导出时间：").append(java.time.LocalDateTime.now().toString().replace("T", " ")).append("\n\n");
        md.append("---\n\n");

        for (ChatHistory msg : history) {
            String role = "user".equals(msg.getRole()) ? "👤 用户" : "🤖 AI助手";
            String time = msg.getCreateTime().toString().replace("T", " ");
            md.append("### ").append(role).append("\n");
            md.append("*").append(time).append("*\n\n");
            md.append(msg.getContent()).append("\n\n");
            md.append("---\n\n");
        }

        return md.toString();
    }

    /**
     * 导出用户对话历史为纯文本格式
     * @param userId 用户ID
     * @return 纯文本格式的对话记录
     */
    public String exportHistoryAsText(Long userId) {
        List<ChatHistory> history = chatHistoryMapper.selectRecentByUserId(userId, 1000);
        StringBuilder text = new StringBuilder();
        text.append("=====================================\n");
        text.append("         校园助手聊天记录\n");
        text.append("=====================================\n");
        text.append("导出时间：").append(java.time.LocalDateTime.now().toString().replace("T", " ")).append("\n\n");

        for (ChatHistory msg : history) {
            String role = "user".equals(msg.getRole()) ? "【用户】" : "【AI助手】";
            String time = msg.getCreateTime().toString().replace("T", " ");
            text.append(role).append(" [").append(time).append("]\n");
            text.append(msg.getContent()).append("\n\n");
            text.append("-------------------------------------\n\n");
        }

        return text.toString();
    }
}
