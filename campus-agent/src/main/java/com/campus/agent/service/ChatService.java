package com.campus.agent.service;

import com.campus.agent.agent.AgentDispatcher;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;

/**
 * 核心对话服务
 */
@Service
public class ChatService {

    @Autowired
    private AgentDispatcher agentDispatcher;

    @Autowired
    private FinanceService financeService;

    @Autowired
    private ChatHistoryService chatHistoryService;

    @Autowired
    private DocumentParserService documentParserService;

    @Value("${neodata.token:}")
    private String neodataToken;

    /**
     * AI对话核心方法（支持文档解析）
     * @param content 用户输入
     * @param userId  用户ID
     * @param docBase64 文档Base64编码（可选）
     * @param docName   文档名称（可选）
     * @return 对话结果
     */
    public Map<String, Object> chat(String content, Long userId, String docBase64, String docName) {
        // 如果有文档，解析内容并拼接到消息中
        if (docBase64 != null && !docBase64.isEmpty() && docName != null && !docName.isEmpty()) {
            try {
                // 解码Base64
                byte[] docBytes = java.util.Base64.getDecoder().decode(docBase64);
                // 解析文档
                String docContent = documentParserService.parseDocument(docBytes, docName);
                if (docContent != null && !docContent.isEmpty()) {
                    // 将文档内容拼接到用户消息
                    content = content == null ? "" : content;
                    content = docContent + "\n\n【用户问题】" + content;
                }
            } catch (Exception e) {
                e.printStackTrace();
                // 解析失败不影响对话
            }
        }

        // 先通过 AgentDispatcher 调度
        Map<String, Object> result = agentDispatcher.dispatch(content, userId);

        // 如果是金融查询，执行实际的查询
        if ("FINANCE_QUERY".equals(result.get("intent"))) {
            if (neodataToken != null && !neodataToken.isEmpty()) {
                // 执行金融数据查询
                String financeResult = financeService.query(content, neodataToken);
                result.put("reply", financeResult);
                // 保存金融查询结果
                chatHistoryService.saveMessage(userId, "assistant", financeResult);
            } else {
                result.put("reply", "💡 金融数据查询功能正在配置中，请联系管理员设置 NeoData Token。");
                chatHistoryService.saveMessage(userId, "assistant", "💡 金融数据查询功能正在配置中，请联系管理员设置 NeoData Token。");
            }
        }

        return result;
    }

    /**
     * AI对话核心方法（兼容旧接口）
     * @param content 用户输入
     * @param userId  用户ID
     * @return 对话结果
     */
    public Map<String, Object> chat(String content, Long userId) {
        return chat(content, userId, null, null);
    }
}
